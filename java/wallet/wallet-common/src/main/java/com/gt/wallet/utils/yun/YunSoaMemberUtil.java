package com.gt.wallet.utils.yun;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.json.JSONObject;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.constant.WalletConstants;
import com.gt.wallet.data.api.tonglian.request.TCardBin;
import com.gt.wallet.data.api.tonglian.request.TPayOrder;
import com.gt.wallet.data.api.tonglian.request.TRefundOrder;
import com.gt.wallet.data.api.tonglian.request.TWithdrawOrder;
import com.gt.wallet.data.wallet.request.WalletCompanyAdd;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.DateTimeKit;
import com.gt.wallet.utils.WalletWebConfig;

import ime.service.client.SOAClient;
import ime.service.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YunSoaMemberUtil {
	private static SOAClient client;
	private static PublicKey publicKey;
	private static PrivateKey privateKey;

	//soa服务名
	private static String membersoaName = "MemberService";
	//soa服务名
	private static String ordersoaName = "OrderService";
	//借记卡卡号
	static{
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 初始化配置
	 * @throws Exception
	 */
	public static void init() throws Exception{

		String serverAddress = WalletWebConfig.getApiUrl();


		//商户号
		String sysid =WalletWebConfig.getYunApiSysid();
		//密钥密码
		String pwd =WalletWebConfig.getYunPwd();
		//证书名称
		String alias = WalletWebConfig.getYunApiAlias();
		//证书文件路径，用户指定
		String path = WalletWebConfig.getYunApiKeyPath();

		String signMethod = "SHA1WithRSA";

		try{
			privateKey = RSAUtil.loadPrivateKey(alias, path, pwd);

			publicKey = RSAUtil.loadPublicKey(alias, path, pwd);

			client = new SOAClient();
			client.setServerAddress(serverAddress);
			client.setSignKey(privateKey);
			client.setPublicKey(publicKey);
			client.setSysId(sysid);
			client.setSignMethod(signMethod);

			log.info("beforeClass success.");
		}catch(Exception e){
			log.error("beforeClass error.");
			e.printStackTrace();
		}
	}

	/**
	 * 创建会员
	 * @param bizUserId 商户系统用户标识
	 * @param memberType 会员类型 
	 * @param source 访问终端类型 
	 * @return data:云账户用户唯一标识
	 */
	public static ServerResponse<String> createMember(String bizUserId,Integer memberType,Integer source) {
		try{
			log.info("createMember start");
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("memberType",memberType);
			param.put("source", source);
			param.put("extendParam", new JSONObject());

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "createMember", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){//创建成功
				log.info("createMember end");
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				return ServerResponse.createBySuccessCodeData(CommonUtil.toString(json.getString("userId")));
			}else{
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 :%s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("testCreateMember error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	/**
	 * 发送短信验证码
	 * @param bizUserId 商户系统用户标识
	 * @param phone 手机号码
	 * @param verificationCodeType 发送类型 绑定、重置绑定手机前调用此接口（verificationCodeType=9）
	 * @return
	 */
	public static ServerResponse<?> sendVerificationCode(String bizUserId,String phone,Integer verificationCodeType){
		try{
			log.info("testSendVerificationCode start");

			JSONObject extendParams = new JSONObject();

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("phone", phone);
			param.put("verificationCodeType", 9);
			param.put("extendParams", extendParams);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "sendVerificationCode", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				log.info("testSendVerificationCode end");
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				return ServerResponse.createBySuccessCodeData(json.getString("userId"));
			}else{
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("testSendVerificationCode error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	//验证短信验证码 ok
//	public void testValidateVerificationCode(){
//		try{
//			log.info("testValidateVerificationCode start");
//
//			JSONObject param = new JSONObject();
//			param.put("bizUserId", bizUserId);
//			param.put("phone", phone);
//			param.put("verificationCodeType", 9);
//			param.put("verificationCode", "");
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "validateVerificationCode", param);
//			log.info("response:" + response);
//
//			log.info("testValidateVerificationCode end");
//		}catch(Exception e){
//			log.error("testValidateVerificationCode error");
//			e.printStackTrace();
//		}
//	}

	/**
	 * 实名认证 ok
	 * @param bizUserId 商户系统用户标识
	 * @param name 姓名
	 * @param identityNo 身份证
	 * @param identityType 证件类型 默认为1
	 * @return
	 */
	public static ServerResponse<?> setRealName(String bizUserId,String name,String identityNo,Integer identityType){
		try{
			log.info("setRealName start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("name", name);
			param.put("identityType",CommonUtil.isEmpty(identityType)?1:identityType);
			param.put("identityNo", rsaEncrypt(identityNo));

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "setRealName", param);
			log.info("response:" + response);

			String value = response.getString("signedValue");
			String sign = response.getString("sign");//30007
			log.info(CommonUtil.format("结果:%s", RSAUtil.verify(publicKey, value, sign)));
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				return ServerResponse.createBySuccess();
			}else if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("error")&&response.getString("errorCode").equals("30007")){
				return ServerResponse.createBySuccess();
			}else{
				
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.info("setRealName error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	//绑定手机 ok
	/**
	 * 
	 * @param bizUserId 商户系统用户标识
	 * @param phone 手机号码
	 * @param verificationCode 验证码
	 * @return
	 */
	public static ServerResponse<?> bindPhone(String bizUserId,String phone,String  verificationCode){
		try{
			log.info("testBindPhone start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("phone", phone);
			param.put("verificationCode",verificationCode);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "bindPhone", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				log.info("bindPhone end");
				return ServerResponse.createBySuccess();
			}else{
				log.info("bindPhone end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.info("bindPhone error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	/**
	 * 设置企业会员信息 ok
	 * @return
	 */
	public static ServerResponse<?> setCompanyInfo(WalletCompanyAdd walletCompany,String bizUserId){
		try{
			log.info("setCompanyInfo start");

			JSONObject param = new JSONObject();

			JSONObject companyBasicInfo = new JSONObject();
			companyBasicInfo.put("companyName", walletCompany.getCompanyName());
			companyBasicInfo.put("companyAddress",walletCompany.getCompanyAddress());
			companyBasicInfo.put("businessLicense",walletCompany.getBusinessLicense());
//			companyBasicInfo.put("organizationCode", walletCompany.getOrganizationCode());
			companyBasicInfo.put("telephone", walletCompany.getTelephone());
			companyBasicInfo.put("legalName", walletCompany.getLegalName());
			companyBasicInfo.put("identityType", 1);
			companyBasicInfo.put("legalIds", rsaEncrypt(walletCompany.getLegalIds()));
			companyBasicInfo.put("legalPhone", walletCompany.getLegalPhone());
			companyBasicInfo.put("accountNo", rsaEncrypt(walletCompany.getAccountNo()));
			
			companyBasicInfo.put("parentBankName", walletCompany.getParentBankName());
//			companyBasicInfo.put("bankCityNo", walletCompany.getBankCityNo());
			companyBasicInfo.put("bankName",walletCompany.getBankName());

			param.put("bizUserId", bizUserId);
			param.put("companyBasicInfo", companyBasicInfo);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "setCompanyInfo", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				if(json.getLong("result")==2){//审核成功
					log.info("setCompanyInfo end");
					return ServerResponse.createBySuccess();
					
				}else{
					log.info("setCompanyInfo end");
					return ServerResponse.createByErrorMessage(CommonUtil.format("审核失败,失败原因%s,", json.getString("failReason")));

				}
			}else if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("error")&&response.getString("errorCode").equals("30003")){
				return ServerResponse.createBySuccess();
			}else{
				log.info("setCompanyInfo end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("setCompanyInfo error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	//设置个人会员信息 ok
	public static void setMemberInfo(String bizUserId){
		try{
			log.info("setMemberInfo start");

			JSONObject param = new JSONObject();

			JSONObject userInfo = new JSONObject();
			userInfo.put("name", "");
			userInfo.put("country", "");
			userInfo.put("province", "");
			userInfo.put("area", "");
			userInfo.put("address", "");

			param.put("bizUserId", bizUserId);
			param.put("userInfo", userInfo);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "setMemberInfo", param);
			log.info("response:" + response);

			log.info("setMemberInfo end");
		}catch(Exception e){
			log.error("setMemberInfo error");
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 获取会员信息
	 * @param bizUserId 商户系统用户标识
	 * @return 会员信息
	 */
	public static ServerResponse<com.alibaba.fastjson.JSONObject>  getMemberInfo(String bizUserId){
		try{
			log.info("getMemberInfo start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "getMemberInfo", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				log.info("getMemberInfo end");
				return ServerResponse.createBySuccessCodeData(json);
			}else{
				log.info("getMemberInfo end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("getMemberInfo error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	//获取卡bin ok
	
	/**
	 * 获取卡bin ok
	 * @param cardNo
	 * @return
	 */
	public static ServerResponse<TCardBin>  getBankCardBin(String cardNo){
		try{
			log.info("getBankCardBin start");

			JSONObject param = new JSONObject();
			param.put("cardNo", rsaEncrypt(cardNo));

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "getBankCardBin", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				log.info("getBankCardBin end");
				return ServerResponse.createBySuccessCodeData(JsonUtil.parseObject(json.getString("cardBinInfo"), TCardBin.class));
			}else{
				log.info("getMemberInfo end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("getBankCardBin error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	/**
	 * 绑定银行卡申请
	 * @param walletIndividualAdd 绑定信息
	 * @param bizUserId 商户系统唯一 ID
	 * @param isSafeCard 是否为安全卡 true:是
	 * @param bankCode 银行代码
	 * @param cardType 银行卡类型 1：借记卡 2:信用卡
	 * @param cardType 银行卡类型 1：借记卡 2:信用卡
	 * @return
	 */
	public static ServerResponse<com.alibaba.fastjson.JSONObject> applyBindBankCard(WalletIndividualAdd walletIndividualAdd,String bizUserId,boolean isSafeCard,String bankCode,Long cardType){
		try{
			log.info("testApplyBindBankCard start");
			//借记卡
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("cardNo", rsaEncrypt(walletIndividualAdd.getCardNo()));
			param.put("phone", walletIndividualAdd.getPhone());
			param.put("name", walletIndividualAdd.getBankName());
			param.put("cardType", cardType);
//			param.put("bankCode",bankCode);
			param.put("identityType", 1);
//			param.put("isSafeCard", isSafeCard);
			param.put("identityNo", rsaEncrypt(walletIndividualAdd.getIdentityNo()));
			if(CommonUtil.isNotEmpty(walletIndividualAdd.getUnionBank())){
				param.put("unionBank",walletIndividualAdd.getUnionBank());
			}
			if(cardType==2){//信用卡
	//			param.put("validate", rsaEncrypt(""));
	//			param.put("cvv2", rsaEncrypt(""));
			}
			
			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "applyBindBankCard", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				log.info("applyBindBankCard end");
				return ServerResponse.createBySuccessCodeData(json);
			}else{
				log.info("applyBindBankCard end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("applyBindBankCard error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	//绑定银行卡确认
	/**
	 * 绑定银行卡确认
	 * @param walletIndividualAdd
	 */
	public static ServerResponse<?> bindBankCard(String bizUserId,String tranceNum,String transDate,String phone,String verificationCode){
		try{
			log.info("testBindBankCard start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("tranceNum", tranceNum);
			param.put("transDate", transDate);
			param.put("phone", phone);
			param.put("verificationCode", verificationCode);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "bindBankCard", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				log.info("bindBankCard end");
				return ServerResponse.createBySuccess();
			}else{
				log.info("bindBankCard end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("bindBankCard error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	/**
	 * 查询绑定银行卡
	 * @param cardNo 银行卡
	 * @param bizUserId 会员账号
	 */
	public static ServerResponse<com.alibaba.fastjson.JSONArray> queryBankCard(String cardNo,String bizUserId){
		try{
			log.info("queryBankCard start");

			//查询单张卡
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("cardNo", cardNo);

			//查询全部
//			JSONObject param = new JSONObject();
//			param.put("bizUserId", bizUserId);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "queryBankCard", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				com.alibaba.fastjson.JSONArray array=	json.getJSONArray("bindCardList");
				log.info("queryBankCard end");
				return ServerResponse.createBySuccessCodeData(array);
			}else{
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("queryBankCard error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	//解绑绑定银行卡
	public static void unbindBankCard(String bizUserId,String cardNo){
		try{
			log.info("testUnbindBankCard start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("cardNo", rsaEncrypt(cardNo));

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "unbindBankCard", param);
			log.info("response:" + response);

			log.info("testUnbindBankCard end");
		}catch(Exception e){
			log.error("testUnbindBankCard error");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param bizUserId 会员账号
	 * @param phone 原手机
	 * @param changePhone 需要变更手机
	 * @param oldVerificationCode 原手机验证码
	 * @param newVerificationCode 变更手机验证码
	 */
	public static ServerResponse<?> changeBindPhone(String bizUserId,String phone,String changePhone,String newVerificationCode){
		try{
			log.info("changeBindPhone start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("oldPhone", phone);
//			param.put("oldVerificationCode",oldVerificationCode);
			param.put("newPhone", changePhone);
			param.put("newVerificationCode", newVerificationCode);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "changeBindPhone", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				log.info("changeBindPhone end");
				return ServerResponse.createBySuccess();
			}else{
				log.info("changeBindPhone end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("changeBindPhone error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}
//	
//	/**
//	 * 
//	 * @param bizUserId 会员账号
//	 * @param phone 原手机
//	 * @param changePhone 需要变更手机
//	 * @param oldVerificationCode 原手机验证码
//	 * @param newVerificationCode 变更手机验证码
//	 */
//	public static void changeBindPhone(String bizUserId,String phone,String changePhone,String oldVerificationCode,String newVerificationCode){
//		try{
//			log.info("testChangeBindPhone start");
//			
//			JSONObject param = new JSONObject();
//			param.put("bizUserId", bizUserId);
//			param.put("oldPhone", phone);
//			param.put("oldVerificationCode", "");
//			param.put("newPhone", changePhone);
//			param.put("newVerificationCode", "");
//			
//			log.info("request:" + param);
//			JSONObject response = client.request(membersoaName, "changeBindPhone", param);
//			log.info("response:" + response);
//			
//			log.info("testChangeBindPhone end");
//		}catch(Exception e){
//			log.error("testChangeBindPhone error");
//			e.printStackTrace();
//		}
//	}

	//锁定用户 ok
	public static ServerResponse<?> lockMember(String bizUserId){
		try{
			log.info("lockMember start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "lockMember", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				return ServerResponse.createBySuccess();
			}else{
				log.info("lockMember end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}

		}catch(Exception e){
			log.error("lockMember error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	/**
	 * 解锁用户 ok
	 * @param bizUserId
	 * @return
	 */
	public static ServerResponse<?> unlockMember(String bizUserId){
		try{
			log.info("testUnlockMember start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);

			log.info("request:" + param);
			JSONObject response = client.request(membersoaName, "unlockMember", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){
				log.info("unlockMember end");
				return ServerResponse.createBySuccess();
			}else{
				log.info("unlockMember end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 : %s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.error("unlockMember error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

//	//RSA加密
//	public static void rsaEncrypt() throws Exception{
//		try{
//			log.info("rsaEncrypt start");
//
//			String str = "";
//
//			RSAUtil rsaUtil = new RSAUtil((RSAPublicKey)publicKey, (RSAPrivateKey)privateKey);
//			String encryptStr = rsaUtil.encrypt(str);
//			log.info(encryptStr);
//		}catch(Exception e){
//			log.error("rsaEncrypt error");
//			e.printStackTrace();
//			throw e;
//		}
//	}

	/**
	 * RSA加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String rsaEncrypt(String str) throws Exception{
		try{
			log.info("rsaEncrypt start");

			RSAUtil rsaUtil = new RSAUtil((RSAPublicKey)publicKey, (RSAPrivateKey)privateKey);
			String encryptStr = rsaUtil.encrypt(str);
			return encryptStr;
		}catch(Exception e){
			log.error("rsaEncrypt error");
			e.printStackTrace();
			throw e;
		}
	}

	//RSA解密
	/**
	 * RSA解密
	 * @param signStr
	 * @throws Exception
	 */
	public static String  rsaDecrypt(String signStr) throws Exception{
		try{
			log.info("rsaDecrypt start");

			RSAUtil rsaUtil = new RSAUtil((RSAPublicKey)publicKey, (RSAPrivateKey)privateKey);
			String dencryptStr = rsaUtil.dencrypt(signStr);
			log.info("解密：" + dencryptStr);
			return dencryptStr;
		}catch(Exception e){
			log.error("rsaDecrypt error");
			e.printStackTrace();
			return "";
		}
	}
	
/***********************************************************************订单类接口********************************************************************************************/
	
	/**
	 * 充值申请
	 * @param payOrder
	 * @return
	 */
	public static ServerResponse<com.alibaba.fastjson.JSONObject> applyDeposit(TPayOrder payOrder) {
		try{
			log.info("applyDeposit start:"+JsonUtil.toJSONString(payOrder));
			//支付方式
			//快捷
			String frontUrl =payOrder.getFrontUrl();
			Long amount= (long) (payOrder.getAmount()*100);
			//组装支付方式
			JSONObject payMethod = new JSONObject();
			if(payOrder.getType()==1){
				//微信js支付
				JSONObject weixin = new JSONObject();
				weixin.put("acct", payOrder.getAcct());
				weixin.put("amount", amount);
				payMethod.put("WECHAT_PUBLIC",weixin);
			}else if(payOrder.getType()==2){
				//支付宝支付
				JSONObject alipay = new JSONObject();
				alipay.put("acct", payOrder.getAcct());
				alipay.put("amount", amount);
				payMethod.put("ALIPAY_SERVICE",alipay);
			}else if(payOrder.getType()==3){
				//微信H5
				JSONObject alipay = new JSONObject();
				alipay.put("limitPay","no_credit");
				alipay.put("amount",amount);
				payMethod.put("WECHATPAY_H5",alipay);
			}else if(payOrder.getType()==4){
				//微信扫码支付(正扫)
				JSONObject alipay = new JSONObject();
				alipay.put("payType","W02");
				alipay.put("amount", amount);
				payMethod.put("SCAN_WEIXIN",alipay);
			}else if(payOrder.getType()==5){
				//支付宝扫码支付(正扫)
				JSONObject alipay = new JSONObject();
				alipay.put("payType","A01");
				alipay.put("amount", amount);
				payMethod.put("SCAN_ALIPAY",alipay);
			}else if(payOrder.getType()==6){
				//微信刷卡支付(被扫)
				JSONObject alipay = new JSONObject();
				alipay.put("payType","A01");
				alipay.put("amount", amount);
				alipay.put("authcode", payOrder.getAcct());
				payMethod.put("SCAN_ALIPAY",alipay);
			}else if(payOrder.getType()==7){
				//支付宝刷卡支付(被扫)
				JSONObject alipay = new JSONObject();
				alipay.put("payType","A01");
				alipay.put("amount", payOrder.getAmount()*100);
				alipay.put("authcode", payOrder.getAcct());
				payMethod.put("SCAN_ALIPAY",alipay);
			}else  if(payOrder.getType()==8){
				JSONObject alipay = new JSONObject();
				alipay.put("bankCardNo",rsaEncrypt("6228481234567890123"));
				alipay.put("amount",amount);
//				alipay.put("payType",WalletWebConfig.getYunBizUserId());
		//		alipay.put("amount", payOrder.getAmount()*100);
				payMethod.put("REALNAMEPAY",alipay);
			}
//			payMethod.put("GATEWAY", gatewayPay);
//			payMethod.put("DAIKOU", daikouPay);

			String backUrl =WalletWebConfig.getYunpaySuccessNotifyUrl();

			String extendInfo = "this is extendInfo";

			JSONObject param = new JSONObject();
			param.put("bizUserId", payOrder.getBizUserId());
			param.put("bizOrderNo", "ddf"+System.currentTimeMillis());
			param.put("accountSetNo", WalletWebConfig.getYunBizUserId());
			param.put("amount",amount);
			param.put("fee", 0L);
			param.put("validateType",0L);
			param.put("frontUrl", "");
			param.put("backUrl", backUrl);
			param.put("ordErexpireDatetime", "2018-01-15 23:59:59");
			param.put("payMethod", payMethod);
			param.put("goodsName", "测试商品");
			param.put("industryCode", WalletConstants.INDUSTRYCODE);
			param.put("industryName",WalletConstants.INDUSTRYNAME);
			param.put("source", 1L);
			param.put("summary",payOrder.getDesc());
			param.put("extendInfo", extendInfo);

			log.info("request:" + param);
			JSONObject response = client.request(ordersoaName, "depositApply", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){//创建成功
				log.info("applyDeposit end");
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				com.alibaba.fastjson.JSONObject payInfo=	json.getJSONObject("payInfo");
				return ServerResponse.createBySuccessCodeData(payInfo);
			}else{
				log.info("applyDeposit end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 :%s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}

		}catch(Exception e){
			log.info("applyDeposit error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	/**
	 * 充值申请
	 * @param payOrder
	 * @return
	 */
	public static ServerResponse<Integer> codepay(TPayOrder payOrder) {
		try{
			log.info("applyDeposit start:"+JsonUtil.toJSONString(payOrder));
			//支付方式
			//快捷
			String frontUrl =payOrder.getFrontUrl();
			Long amount= (long) (payOrder.getAmount()*100);
			Integer validateType=1;
			//组装支付方式
			JSONObject payMethod = new JSONObject();
			if(payOrder.getType()==1){
				//微信js支付
				JSONObject weixin = new JSONObject();
				weixin.put("acct", payOrder.getAcct());
				weixin.put("amount", amount);
				payMethod.put("WECHAT_PUBLIC",weixin);
				validateType=1;
			}else if(payOrder.getType()==2){
				//支付宝支付
				JSONObject alipay = new JSONObject();
				alipay.put("acct", payOrder.getAcct());
				alipay.put("amount", amount);
				payMethod.put("ALIPAY_SERVICE",alipay);
			}else if(payOrder.getType()==3){
				//微信H5
				JSONObject alipay = new JSONObject();
				alipay.put("limitPay","no_credit");
				alipay.put("amount",amount);
				payMethod.put("WECHATPAY_H5",alipay);
				validateType=1;
			}else if(payOrder.getType()==4){
				//微信扫码支付(正扫)
				JSONObject alipay = new JSONObject();
				alipay.put("payType","W02");
				alipay.put("amount", amount);
				payMethod.put("SCAN_WEIXIN",alipay);
				validateType=1;
			}else if(payOrder.getType()==5){
				//支付宝扫码支付(正扫)
				JSONObject alipay = new JSONObject();
				alipay.put("payType","A01");
				alipay.put("amount", amount);
				payMethod.put("SCAN_ALIPAY",alipay);
				validateType=1;
			}else if(payOrder.getType()==6){
				//微信刷卡支付(被扫)
				JSONObject alipay = new JSONObject();
				alipay.put("payType","A01");
				alipay.put("amount", amount);
				alipay.put("authcode", payOrder.getAcct());
				payMethod.put("SCAN_ALIPAY",alipay);
				validateType=0;
			}else if(payOrder.getType()==7){
				//支付宝刷卡支付(被扫)
				JSONObject alipay = new JSONObject();
				alipay.put("payType","A01");
				alipay.put("amount", payOrder.getAmount()*100);
				alipay.put("authcode", payOrder.getAcct());
				payMethod.put("SCAN_ALIPAY",alipay);
				validateType=0;
			}else  if(payOrder.getType()==8){
				validateType=1;
				JSONObject alipay = new JSONObject();
				alipay.put("bankCardNo",rsaEncrypt("6228481234567890123"));
				alipay.put("amount",amount);
//				alipay.put("payType",WalletWebConfig.getYunBizUserId());
		//		alipay.put("amount", payOrder.getAmount()*100);
				payMethod.put("REALNAMEPAY",alipay);
			}
//			payMethod.put("GATEWAY", gatewayPay);
//			payMethod.put("DAIKOU", daikouPay);

			String backUrl =WalletWebConfig.getYunpaySuccessNotifyUrl();

			String extendInfo = "this is extendInfo";

			JSONObject param = new JSONObject();
			param.put("bizUserId", payOrder.getBizUserId());
			param.put("bizOrderNo", "ddf"+System.currentTimeMillis());
			param.put("accountSetNo", WalletWebConfig.getYunBizUserId());
			param.put("amount",amount);
			param.put("fee", 0L);
			param.put("validateType",validateType);
			param.put("frontUrl", frontUrl);
			param.put("backUrl", backUrl);
			param.put("ordErexpireDatetime", "2018-01-15 23:59:59");
			param.put("payMethod", payMethod);
			param.put("goodsName", "测试商品");
			param.put("industryCode", WalletConstants.INDUSTRYCODE);
			param.put("industryName",WalletConstants.INDUSTRYNAME);
			param.put("source", 1L);
			param.put("summary",payOrder.getDesc());
			param.put("extendInfo", extendInfo);

			log.info("request:" + param);
			JSONObject response = client.request(ordersoaName, "depositApply", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){//创建成功
				log.info("applyDeposit end");
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				if(json.getString("payStatus").equals("success")){
					return ServerResponse.createBySuccessCodeData(0);
				}else if(json.getString("payStatus").equals("pending")){
					return ServerResponse.createBySuccessCodeData(1);
				}else{
					
					return ServerResponse.createByErrorCodeMessage(WalletResponseEnums.API_ERROR.getCode(), CommonUtil.format("%s",  json.getString("payFailMessage")));
				}
			}else{
				log.info("applyDeposit end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 :%s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}

		}catch(Exception e){
			log.info("applyDeposit error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}

	/**
	 * 提现申请
	 * @param consumeOrder
	 */
	public static ServerResponse<String> applyWithdraw(TWithdrawOrder consumeOrder) {
		try{
			log.info("applyWithdraw start");

		//	String extendInfo = "this is withdraw.";
			String backUrl =WalletWebConfig.getYunWithdrawSuccessNotifyUrl();

			JSONObject param = new JSONObject();
			param.put("bizUserId", consumeOrder.getBizUserId());
			param.put("bizOrderNo", consumeOrder.getBizOrderNo());
			param.put("accountSetNo", WalletWebConfig.getYunBizUserId());
			param.put("amount", consumeOrder.getAmount()*100);
			param.put("fee", consumeOrder.getFee());
			param.put("backUrl", backUrl);
		//	param.put("ordErexpireDatetime", ordErexpireDatetime);
			param.put("bankCardNo", YunSoaMemberUtil.rsaEncrypt("6228481234567890123"));
//			param.put("bankCardNo", consumeOrder.getBankCardNo());
			param.put("industryCode", WalletConstants.INDUSTRYCODE);
			param.put("industryName",WalletConstants.INDUSTRYNAME);
			param.put("source", 1);
			param.put("summary", consumeOrder.getDesc());
			param.put("withdrawType", consumeOrder.getWithdrawType());
			param.put("bankCardPro", consumeOrder.getBankCardPro());
	//		param.put("extendInfo", extendInfo);

			log.info("request:" + param);
			JSONObject response = client.request(ordersoaName, "withdrawApply", param);
			log.info("response:" + response);
			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){//创建成功
				log.info("applyWithdraw end");
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				log.info("orderNo:"+json.getString("orderNo"));
				return ServerResponse.createBySuccessCodeData(json.getString("orderNo"));
			}else{
				log.info("applyWithdraw end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 :%s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}

		}catch(Exception e){
			log.info("applyWithdraw error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}
	
	
	/**
	 * 查询余额
	 * @param bizUserId 会员账号
	 */
	public ServerResponse<com.alibaba.fastjson.JSONObject> queryBalance(String bizUserId){
		try{
			log.info("queryBalance start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("accountSetNo", WalletWebConfig.getYunBizUserId());

			log.info("request:" + param);
			JSONObject response = client.request(ordersoaName, "queryBalance", param);
			log.info("response:" + response);

			if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){//查询成功
				String value = response.getString("signedValue");
				com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				return ServerResponse.createBySuccessCodeData(json);
			}else{//接口异常
				log.info("applyWithdraw end");
				return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 :%s,描述:%s", response.getString("errorCode"), response.getString("message")));
			}
		}catch(Exception e){
			log.info("queryBalance error");
			e.printStackTrace();
			return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
		}
	}
		
		
		/**
		 * 查询订单状态
		 * @param bizUserId 会员账号
		 * @param bizOrderNo 订单号
		 */
		public ServerResponse<com.alibaba.fastjson.JSONObject> getOrderDetail(String bizUserId,String bizOrderNo){
			try{
				log.info("getOrderDetail start");
				JSONObject param = new JSONObject();
				param.put("bizUserId", bizUserId);
				param.put("bizOrderNo", "");
				log.info("request:" + param);
				JSONObject response = client.request(ordersoaName, "getOrderDetail", param);
				log.info("response:" + response);
				if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){//查询成功
					log.info("getOrderDetail end");
					String value = response.getString("signedValue");
					com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
					return ServerResponse.createBySuccessCodeData(json);
				}else{//接口异常
					log.info("getOrderDetail end");
					return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 :%s,描述:%s", response.getString("errorCode"), response.getString("message")));
				}
			}catch(Exception e){
				log.info("getOrderDetail error");
				e.printStackTrace();
				return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
			}
		}

//		
//		//退款
		public static ServerResponse<String> refund(TRefundOrder tRefundOrder ){
			try{
				log.info("refund start");

				String bizOrderNo = tRefundOrder.getBizOrderNo();

				JSONObject refund1 = new JSONObject();
				refund1.put("bizUserId", tRefundOrder.getBizUserId());
				refund1.put("amount", tRefundOrder.getAmount()-tRefundOrder.getFeeAmount());

				org.json.JSONArray refundList = new org.json.JSONArray();
				refundList.put(refund1);

				JSONObject param = new JSONObject();
				param.put("bizOrderNo", bizOrderNo);
				param.put("oriBizOrderNo", tRefundOrder.getOriBizOrderNo());
				param.put("bizUserId", tRefundOrder.getBizUserId());
				param.put("refundList", refundList);
				param.put("amount", tRefundOrder.getAmount());
				param.put("couponAmount", 0);
				param.put("feeAmount", tRefundOrder.getFeeAmount());

				log.info("request:" + param);
				JSONObject response = client.request(ordersoaName, "refund", param);
				log.info("response:" + response);
				if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){//查询成功
					log.info("refund end");
					String value = response.getString("signedValue");
					com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
					log.info("payStatus:"+json.getString("payStatus"));
					if(json.getString("payStatus").equals("success")){//成功
						return ServerResponse.createBySuccess();
					}else if(json.getString("payStatus").equals("pending")){//进行时
						return ServerResponse.createBySuccess();
					}else if(json.getString("payStatus").equals("fail")){
						return ServerResponse.createByErrorMessage("提现失败");
					}
					String  orderNo=	json.getString("orderNo");
					return ServerResponse.createBySuccessCodeData(orderNo);
				}else{//接口异常
					log.info("refund end");
					return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 :%s,描述:%s", response.getString("errorCode"), response.getString("message")));
				}
				
			}catch(Exception e){
				log.info("refund error");
				e.printStackTrace();
				return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
			}
		}
	
		/**
		 * 确认支付
		 * @param bizUserId 会员账号
		 * @param bizOrderNo 订单号
		 */
		public static ServerResponse<com.alibaba.fastjson.JSONObject> pay(String bizUserId,String bizOrderNo,String verificationCode,String consumerIp){
			try{
				log.info("pay start");
				JSONObject param = new JSONObject();
				param.put("bizUserId", bizUserId);
				param.put("bizOrderNo", bizOrderNo);
				param.put("verificationCode", verificationCode);
				param.put("consumerIp", consumerIp);
				log.info("request:" + param);
				JSONObject response = client.request(ordersoaName, "pay", param);
				log.info("response:" + response);
				if(CommonUtil.isNotEmpty(response)&&response.get("status").equals("OK")){//查询成功
					log.info("pay end");
					String value = response.getString("signedValue");
					com.alibaba.fastjson.JSONObject json=	JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
					return ServerResponse.createBySuccessCodeData(json);
				}else{//接口异常
					log.info("pay end");
					return ServerResponse.createByErrorMessage(CommonUtil.format("第三方接口异常,错误代码 :%s,描述:%s", response.getString("errorCode"), response.getString("message")));
				}
			}catch(Exception e){
				log.info("getOrderDetail error");
				e.printStackTrace();
				return ServerResponse.createByErrorCode(WalletResponseEnums.API_ERROR);
			}
		}
		
	/***********************************************************************订单类接口********************************************************************************************/
}
