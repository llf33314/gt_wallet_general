package com.gt.wallet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.api.tonglian.request.TCardBin;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.service.member.WalletCompanyService;
import com.gt.wallet.service.member.WalletIndividualService;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月27日 下午2:29:21 
* 类说明 
*/
@Slf4j
public class MemberServiceTest extends BasicTest {
	
	@Autowired
	private WalletMemberService walletMemberService;
	
	@Autowired
	private WalletCompanyService walletCompanyService; 
	
	
	@Autowired
	private WalletIndividualService walletIndividualService;
	
	
	@Autowired
	private WalletBankService walletBankService;
	
	@Test
	public void createMember(){
//		log.info(" start test createMember api");
//		try {
//			ServerResponse<Integer> serverResponse=	walletMemberService.register(2, "127.0.0.1", 35);
//			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("test createMember api error");
//		}
//		log.info(" end test createMember api");
	}
	
	
	/**
	 * 设置企业会员信息 ok
	 */
	@Test
	public void setCompanyInfo(){
//		log.info(" start test setCompanyInfo api");
//		WalletCompanyAdd walletCompanyAdd=new WalletCompanyAdd(10, "广东谷通科技有限公司", "惠城区惠州大道20号赛格大厦10层07号", "91440300335398348T", "13528307867", "盖茨", "1", "13528307867", "6228481139158261672", "中国农业银行", "", "");
//		walletCompanyAdd.setDoBusinessUrl("http://maint.deeptel.com.cn/upload/image/2/wallet/null/1511838821858.png");
//		walletCompanyAdd.setIdentitycardUrl1("http://maint.deeptel.com.cn/upload/image/2/wallet/null/1511838821858.png");
//		walletCompanyAdd.setIdentitycardUrl2("http://maint.deeptel.com.cn/upload/image/2/wallet/null/1511838821858.png");
//		walletCompanyAdd.setLicenseUrl("http://maint.deeptel.com.cn/upload/image/2/wallet/null/1511838821858.png");
//		walletCompanyAdd.setProvince("440000");
//		walletCompanyAdd.setArea("441300");
//		BusUser busUser=new BusUser();
//		busUser.setId(35);
//		busUser.setName("gt123");
//		try {
//			ServerResponse<?> serverResponse=	walletCompanyService.save(walletCompanyAdd,busUser);
//			if(serverResponse.getCode()==0){
//				serverResponse=walletBankService.addPublic(walletCompanyAdd);
//			}
//			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("test setCompanyInfo api error");
//		}
//		log.info(" end test setCompanyInfo api");
	}

	
	/**
	 * 调取api获取会员信息
	 */
	@Test
	public void getMemberInfo(){
//		log.info(" start test getMemberInfo api");
//		try {
//			ServerResponse<?> serverResponse=YunSoaMemberUtil.getMemberInfo("dfw1511774649223");
//			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("test getMemberInfo api error");
//		}
//		log.info(" end test getMemberInfo api");
		
//		  return axios.post(`${base}/walletIndividual/saveIndividual`,  window.JSON.stringify(param),{
//		      headers: {
//		        'Content-Type': 'application/json;charset=UTF-8'
//		      }
//		    }).then(res => res.data)
	}
	
	
	
	/**
	 * 查询绑定银行卡
	 */
//	@Test
//	public void queryBankCard(){
//		log.info(" start test queryBankCard api");
//		try {
//			ServerResponse<?> serverResponse=YunSoaMemberUtil.queryBankCard("kCXiZJ8rnEJxtUSkSv+IaSeCKmmjjpCaKnLzXVXWEPEO/wINZY6+Nmtqb/zNVrSa8rA920Yx3RjLusOLH9rQ+cAc1dU58/hvaXWbVKs1sFle7R/IFUuSlOuym8qG3kgLCVEQLX2IFhd10qd5GcJKEEj6MwsUt+tPjxKBdWllmhA=","dfw1511774649223");
//			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("test queryBankCard api error");
//		}
//		log.info(" end test queryBankCard api");
//	}
//	
//	
	/**
	 * 查询绑定银行卡
	 */
	@Test
	public void queryBankCard(){
		log.info(" start test queryBankCard api");
		try {
			ServerResponse<TCardBin> serverResponse=YunSoaMemberUtil.getBankCardBin("6228481139158261672");
			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("test queryBankCard api error");
		}
		log.info(" end test queryBankCard api");
	}
}
