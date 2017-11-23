package com.gt.wallet.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gt.api.bean.session.BusUser;
import com.gt.api.util.MD5Utils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
import com.gt.wallet.data.wallet.request.WalletSet;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletIndividual;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.member.WalletIndividualMapper;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.service.member.WalletIndividualService;
import com.gt.wallet.utils.AttachmentUtil;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.DateTimeKit;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 个人会员 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletIndividualServiceImpl extends BaseServiceImpl<WalletIndividualMapper, WalletIndividual> implements WalletIndividualService {
	
	@Autowired
	private WalletIndividualMapper walletIndividualMapper;
	
	@Autowired
	private WalletMemberMapper walletMemberMapper;
	
	@Autowired
	private WalletBankService walletBankService;

	@Override
//	@SneakyThrows(Exception.class)
	/**
	 * 使用事务控制
	 * REQUIRED:当前没有其他事务控制则新增一个，有则引用当前事务(事务控制)
	 * Isolation.DEFAULT：存在多个事务时，A事务读取了一条记录时，B事务将不能修改记录,防止脏读(事务隔离级别)
	 * timeout
	 * rollbackFor：指定事务回滚异常类型
	 */
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerResponse<Integer> add(WalletIndividualAdd walletIndividualAdd, MultipartFile identitycardUrl1File, MultipartFile identitycardUrl2File,BusUser busUser) throws Exception{
		log.info(CommonUtil.format("biz接口:保存个人会员信息,请求参数:%s", JsonUtil.toJSONString(walletIndividualAdd)));
		//TODO 调用实名认证api、调用绑定银行卡api
		WalletMember walletMember=walletMemberMapper.selectById(walletIndividualAdd.getMemberId());
		if(CommonUtil.isEmpty(walletMember)){
			log.error(CommonUtil.format("biz接口:保存个人会员信息异常:%s",WalletResponseEnums.DATA_NULL_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		if(walletMember.getMemberClass()==1&&walletMember.getMemberId()!=busUser.getId()){
			throw new BusinessException("操作异常，此钱包会员不属于当前登录商家");
		}
		/*********************************实名认证******************************************/
		WalletIndividual walletIndividual=walletIndividualMapper.selectOne(new WalletIndividual(walletIndividualAdd.getMemberId()));
		if(CommonUtil.isEmpty(walletIndividual)){
			ServerResponse<?> responseRealName=YunSoaMemberUtil.setRealName(walletMember.getMemberNum(), walletIndividualAdd.getName(), walletIndividualAdd.getIdentityNo(), 1);
			if(responseRealName.getCode()!=0){
				throw new BusinessException(responseRealName.getCode(),responseRealName.getMsg());
			}
			walletIndividual=new WalletIndividual();
			walletIndividual.setIdentityChecked(1);
		}
		ServerResponse<String> serverResponse1=AttachmentUtil.uploadAttachment(identitycardUrl1File, busUser);
		if(serverResponse1.getCode()==0){
			String identitycard_url_1=serverResponse1.getData();
			walletIndividual.setIdentitycardUrl1(identitycard_url_1.split("upload/")[1]);
		}
		ServerResponse<String> serverResponse2=AttachmentUtil.uploadAttachment(identitycardUrl2File, busUser);
		if(serverResponse2.getCode()==0){
			String identitycard_url_2=serverResponse2.getData();
			walletIndividual.setIdentitycardUrl2(identitycard_url_2.split("upload/")[1]);
		}
		walletIndividual.setName(walletIndividualAdd.getName());
		walletIndividual.setWMemberId(walletIndividualAdd.getMemberId());
		walletIndividual.setMemberNum(walletMember.getMemberNum());
		walletIndividual.setIdentityChecked(1);
		String indeMi=YunSoaMemberUtil.rsaEncrypt(walletIndividualAdd.getIdentityNo());
		walletIndividual.setIdentityCardNo(indeMi);
		walletIndividual.setSource(1);
		try {
			ServerResponse<JSONObject> serverResponseGetMemberInfo=YunSoaMemberUtil.getMemberInfo(walletMember.getMemberNum());
			if(serverResponseGetMemberInfo.getCode()==0){
				JSONObject jsonObject=serverResponseGetMemberInfo.getData();
				String value = jsonObject.getString("memberInfo");
				JSONObject json=JsonUtil.parseObject(value, com.alibaba.fastjson.JSONObject.class);
				log.info(CommonUtil.format("个人信息：%s", value));
				walletIndividual.setRealNameTime(json.getString("realNameTime"));
				walletIndividual.setAddress(json.getString("address"));
				walletIndividual.setCountry(json.getString("country"));
				walletIndividual.setProvince(json.getString("province"));
				walletIndividual.setArea(json.getString("province"));
				walletIndividual.setRemark(json.getString("realNameTime"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(CommonUtil.isEmpty(walletIndividual.getId())||walletIndividual.getId()==0){
			walletMember.setStatus(3);
			walletIndividualMapper.insert(walletIndividual);
		}else{
			walletIndividualMapper.updateById(walletIndividual);
		}
		/*********************************实名认证******************************************/
		ServerResponse<Integer> serverResponse=null;
		
		/*********************************银行卡******************************************/
		try {
			serverResponse=walletBankService.add(walletIndividualAdd);
		}catch(BusinessException ex){
			log.error("银行卡接口异常:"+ex.getMessage());
			serverResponse=ServerResponse.createByErrorMessage(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("绑定银行卡接口异常");
			serverResponse=ServerResponse.createByErrorMessage("绑定银行卡接口异常");
		}
		/*********************************银行卡******************************************/
		
		
		
		
		log.info(CommonUtil.format("biz接口:保存个人会员信息", JsonUtil.toJSONString(serverResponse)));
		return serverResponse;
	}

	@Override
	public ServerResponse<?> set(WalletSet walletSet, BusUser busUser) throws Exception {
		// TODO Auto-generated method stub
		if(!walletSet.getPwd().equals(walletSet.getConfirm())){
			log.error(CommonUtil.format("biz接口:钱包设置异常:%s",WalletResponseEnums.PWD_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.PWD_ERROR);
		}
//		if(walletMember.getMemberClass()==1&&walletMember.getMemberId()!=busUser.getId()){
//			throw new BusinessException("操作异常，此钱包会员不属于当前登录商家");
//		}
		Wrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		wrapper.where("member_id={0}",busUser.getId()).and("member_class={0}", 1);
		List<WalletMember> walletMembers=walletMemberMapper.selectList(wrapper);
		if(CommonUtil.isNotEmpty(walletMembers)&&walletMembers.size()>0){
			WalletMember walletMember=walletMembers.get(0);
			walletMember.setPayPass(MD5Utils.getMD5(walletSet.getPwd()));
			walletMember.setSetPayPwd(1);
			walletMemberMapper.updateById(walletMember);
		}else{
			log.error(CommonUtil.format("biz接口:钱包设置异常:%s",WalletResponseEnums.DATA_NULL_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		return ServerResponse.createBySuccess();
	}
	
	

}
