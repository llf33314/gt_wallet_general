package com.gt.wallet.service.impl.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gt.api.bean.session.BusUser;
import com.gt.api.dto.ResponseUtils;
import com.gt.api.util.HttpClienUtils;
import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.api.tonglian.response.CardBin;
import com.gt.wallet.data.wallet.request.CompanyUploadFile;
import com.gt.wallet.data.wallet.request.SendMail;
import com.gt.wallet.data.wallet.request.WalletCompanyAdd;
import com.gt.wallet.data.wallet.request.WalletCompanyAddress;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletCompany;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.member.WalletCompanyMapper;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.service.mail.MailService;
import com.gt.wallet.service.member.WalletCompanyService;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.WalletKeyUtil;
import com.gt.wallet.utils.WalletWebConfig;
import com.gt.wallet.utils.httpclient.WalletHttpClienUtil;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 企业会员明细 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletCompanyServiceImpl extends BaseServiceImpl<WalletCompanyMapper, WalletCompany> implements WalletCompanyService {
	
	@Autowired
	private WalletCompanyMapper walletCompanyMapper;
	
	@Autowired
	private WalletMemberService walletMemberService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private WalletMemberMapper walletMemberMapper;
	

	/**
	 * 使用事务控制
	 * REQUIRED:当前没有其他事务控制则新增一个，有则引用当前事务(事务控制)
	 * Isolation.DEFAULT：存在多个事务时，A事务读取了一条记录时，B事务将不能修改记录,防止脏读(事务隔离级别)
	 * timeout
	 * rollbackFor：指定事务回滚异常类型
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> save(WalletCompanyAdd walletCompanyAdd,BusUser busUser) throws Exception {
		log.info(CommonUtil.format("start biz save api params:%s", JsonUtil.toJSONString(walletCompanyAdd)));
		ServerResponse<Integer> serverResponse=null;
		if(CommonUtil.isEmpty(walletCompanyAdd)){
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		
		
		/*******************************拼接地址**************************************/
		Wrapper<WalletCompany> wrapper=new EntityWrapper<>();
		wrapper.where("w_member_id={0}", walletCompanyAdd.getMemberId());
		String address="";
		RequestUtils<String> requestUtils=new RequestUtils<>();
		requestUtils.setReqdata(walletCompanyAdd.getProvince());
		String path=WalletWebConfig.getHomeUrl()+"8A5DA52E/shopapi/6F6D9AD2/79B4DE7C/queryBasisByCodes.do";
		String key=WalletWebConfig.getWxmpKey();
		
		ResponseUtils<List<Map<String, Object>>> province=HttpClienUtils.reqPostUTF8(JsonUtil.toJSONString(requestUtils), path, ResponseUtils.class, key);
		if(CommonUtil.isEmpty(province)||province.getCode()!=0||CommonUtil.isEmpty(province.getData())||province.getData().size()!=1){
			log.error("biz save api fail");
			throw new BusinessException("biz save api fail");
		}
//		String pro=homeUrl+"8A5DA52E/shopapi/6F6D9AD2/79B4DE7C/queryBasisByCodes.do";
//		ResponseUtils<List<Map<String, Object>>> province=HttpClienUtils.reqGetUTF8(JsonUtil.toJSONString(requestUtils),pro, ResponseUtils.class, key);
		log.info(CommonUtil.format("biz save api province:%s", JsonUtil.toJSONString(province)));
		requestUtils=new RequestUtils<>();
		requestUtils.setReqdata(walletCompanyAdd.getArea());
		ResponseUtils<List<Map<String, Object>>> city=HttpClienUtils.reqPostUTF8(JsonUtil.toJSONString(requestUtils),path, ResponseUtils.class,key);
		if(CommonUtil.isEmpty(city)||city.getCode()!=0||CommonUtil.isEmpty(city.getData())||city.getData().size()!=1){
			log.error("biz save api fail");
			throw new BusinessException("获取地址api异常,请联系管理员");
		}
		address=CommonUtil.toString(province.getData().get(0).get("city_name"))+CommonUtil.toString(city.getData().get(0).get("city_name"))+walletCompanyAdd.getCompanyAddress();
		/*******************************拼接地址**************************************/
		/*******************************判断db记录是否异常**************************************/
		List<WalletCompany> walletCompanies=walletCompanyMapper.selectList(wrapper);
		if(CommonUtil.isEmpty(walletCompanies)||walletCompanies.size()>1){
			log.error("biz save api fail:data exception call admin");
			throw new BusinessException("biz save api fail:data exception call admin");
		}
		WalletCompany walletCompany=null;
		if(walletCompanies.size()==0){
			walletCompany=new WalletCompany();
		}else{
			walletCompany=walletCompanies.get(0);
		}
		WalletMember walletMember=walletMemberService.selectById(walletCompanyAdd.getMemberId());
		/*******************************判断db记录是否异常**************************************/
		
		/*******************************调用设置企业信息api**************************************/
//		CardBin cardBin=WalletHttpClienUtil.reqGet(walletCompanyAdd.getAccountNo(), CardBin.class);
//		log.info(CommonUtil.format("biz save api cardBin:%s", JsonUtil.toJSONString(cardBin)));
//		if(cardBin.getError_code()!=0){
//			throw new BusinessException(cardBin.getError_code(),cardBin.getReason());
//		}
	//	walletCompanyAdd.setParentBankName(cardBin.getResult().getBankname());
		ServerResponse<?> response=YunSoaMemberUtil.setCompanyInfo(walletCompanyAdd, walletMember.getMemberNum());
 		log.info(CommonUtil.format("biz save api response:%s", JsonUtil.toJSONString(response)));
		if(!ServerResponse.judgeSuccess(response)){//返回异常
			throw new BusinessException(WalletResponseEnums.API_ERROR);
		}
		/*******************************调用设置企业信息api**************************************/
		walletCompany.setAccountNo(YunSoaMemberUtil.rsaEncrypt(walletCompanyAdd.getAccountNo()));
		walletCompany.setCompanyAddress(walletCompanyAdd.getCompanyAddress());
		walletCompany.setArea(walletCompanyAdd.getArea());
		//walletCompany.setBankCtiyNo(bankCtiyNo);
//		walletCompany.setOrganizationCode(organizationCode)
		walletCompany.setBankName(walletCompanyAdd.getBankName());
		walletCompany.setBusinessLicense(walletCompanyAdd.getBusinessLicense());
		walletCompany.setCompanyName(walletCompanyAdd.getCompanyName());
//		walletCompany.setDoBusinessUrl(walletCompanyAdd.getDoBusinessUrl());
//		walletCompany.setIdentitycardUrl1(walletCompanyAdd.getIdentitycardUrl1());
//		walletCompany.setIdentitycardUrl2(walletCompanyAdd.getIdentitycardUrl2());
//		walletCompany.setLicenseUrl(walletCompanyAdd.getLicenseUrl());
		walletCompany.setIdentityType(1);
		walletCompany.setLegalIds(YunSoaMemberUtil.rsaEncrypt(walletCompanyAdd.getLegalIds()));
		walletCompany.setLegalName(walletCompanyAdd.getLegalName());
		walletCompany.setMemberNum(walletMember.getMemberNum());
		walletCompany.setParentBankName(walletCompanyAdd.getParentBankName());
		walletCompany.setProvince(walletCompanyAdd.getProvince());
		walletCompany.setTelephone(walletCompanyAdd.getTelephone());
		walletCompany.setUnionBank(walletCompanyAdd.getUnionBank());
		
		walletCompany.setLegalPhone(WalletKeyUtil.getEncString(walletCompanyAdd.getLegalPhone()));
		walletCompany.setWMemberId(walletMember.getId());
		if(CommonUtil.isEmpty(walletCompany.getId())){//新增
			walletCompanyMapper.insert(walletCompany);
		}else{//修改
			walletCompanyMapper.updateById(walletCompany);
		}
		serverResponse=ServerResponse.createBySuccess();
		log.info(CommonUtil.format("biz save api serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return serverResponse=ServerResponse.createBySuccess();
	}


	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> uploadFile(CompanyUploadFile companyUploadFile,BusUser busUser) throws Exception {
		log.info(CommonUtil.format("start biz uploadFile api companyUploadFile:%s", JsonUtil.toJSONString(companyUploadFile)));
		/*******************************发送邮件**************************************/
		Wrapper<WalletCompany> wrapper=new EntityWrapper<>();
		wrapper.where("w_member_id={0}", companyUploadFile.getMemberId());
		List<WalletCompany> walletCompanies=walletCompanyMapper.selectList(wrapper);
		if(CommonUtil.isEmpty(walletCompanies)||walletCompanies.size()==0){
			throw new BusinessException("请先注册会员");
		}
		WalletCompany walletCompany=walletCompanies.get(0);
		walletCompany.setDoBusinessUrl(companyUploadFile.getDoBusinessUrl());
		walletCompany.setIdentitycardUrl1(companyUploadFile.getIdentitycardUrl1());
		walletCompany.setIdentitycardUrl2(companyUploadFile.getIdentitycardUrl2());
		walletCompany.setLicenseUrl(companyUploadFile.getLicenseUrl());
		WalletMember walletMember=walletMemberMapper.selectById(walletCompany.getWMemberId());
		walletMemberMapper.updateById(walletMember);
		int count=walletCompanyMapper.updateById(walletCompany);
		if(count<1){
			log.error("biz uploadFile api fail:db exception");
			throw new BusinessException("uploadFile fail：db fail");
		}
		List<String> files=new ArrayList<>();
		files.add(WalletWebConfig.getPathImage()+companyUploadFile.getDoBusinessUrl().split("image/")[1]);
		files.add(WalletWebConfig.getPathImage()+companyUploadFile.getIdentitycardUrl1().split("image/")[1]);
		files.add(WalletWebConfig.getPathImage()+companyUploadFile.getIdentitycardUrl2().split("image/")[1]);
		files.add(WalletWebConfig.getPathImage()+companyUploadFile.getLicenseUrl().split("image/")[1]);
		SendMail sendMail=new SendMail("企业信息设置:"+busUser.getName(),"企业信息设置",files);
		ServerResponse<?>	mailServerResponse=mailService.sendAttachmentsMail(sendMail);
		log.info(CommonUtil.format("mailServerResponse:%s", JsonUtil.toJSONString(mailServerResponse)));
		if(!ServerResponse.judgeSuccess(mailServerResponse)){
			throw new BusinessException(mailServerResponse.getCode(),mailServerResponse.getMsg());
		}
		log.info(CommonUtil.format("biz uploadFile api serverResponse:%s", JsonUtil.toJSONString(mailServerResponse)));
		/*******************************发送邮件**************************************/
		return mailServerResponse;
	}


	@Override
	public ServerResponse<WalletCompany> findByMemberId(Integer memberId) {
		log.info(CommonUtil.format("start biz findByMemberId api params:%s", JsonUtil.toJSONString(memberId)));
		WalletCompany params=new WalletCompany();
		params.setWMemberId(memberId);
		WalletCompany walletIndividual=walletCompanyMapper.selectOne(params);
		log.info(CommonUtil.format("biz findByMemberId api walletIndividual:%s", JsonUtil.toJSONString(walletIndividual)));
		return ServerResponse.createBySuccessCodeData(walletIndividual);
	}


	/**
	 * 使用事务控制
	 * REQUIRED:当前没有其他事务控制则新增一个，有则引用当前事务(事务控制)
	 * Isolation.DEFAULT：存在多个事务时，A事务读取了一条记录时，B事务将不能修改记录,防止脏读(事务隔离级别)
	 * timeout
	 * rollbackFor：指定事务回滚异常类型
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> updateAddress(WalletCompanyAddress companyAddress,BusUser busUser) throws Exception {
		log.info(CommonUtil.format("start biz updateAddress api params:%s", JsonUtil.toJSONString(companyAddress)));
		ServerResponse<Integer> serverResponse=null;
		if(CommonUtil.isEmpty(companyAddress)){
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		/*******************************拼接地址**************************************/
		Wrapper<WalletCompany> wrapper=new EntityWrapper<>();
		wrapper.where("w_member_id={0}", companyAddress.getMemberId());
		String address="";
		RequestUtils<String> requestUtils=new RequestUtils<>();
		requestUtils.setReqdata(companyAddress.getProvince());
		String path=WalletWebConfig.getHomeUrl()+"8A5DA52E/shopapi/6F6D9AD2/79B4DE7C/queryBasisByCodes.do";
		String key=WalletWebConfig.getWxmpKey();
		
		ResponseUtils<List<Map<String, Object>>> province=HttpClienUtils.reqPostUTF8(JsonUtil.toJSONString(requestUtils), path, ResponseUtils.class, key);
		if(CommonUtil.isEmpty(province)||province.getCode()!=0||CommonUtil.isEmpty(province.getData())||province.getData().size()!=1){
			log.error("biz save api fail");
			throw new BusinessException("biz updateAddress api fail");
		}
		log.info(CommonUtil.format("biz updateAddress api province:%s", JsonUtil.toJSONString(province)));
		requestUtils=new RequestUtils<>();
		requestUtils.setReqdata(companyAddress.getArea());
		ResponseUtils<List<Map<String, Object>>> city=HttpClienUtils.reqPostUTF8(JsonUtil.toJSONString(requestUtils),path, ResponseUtils.class,key);
		if(CommonUtil.isEmpty(city)||city.getCode()!=0||CommonUtil.isEmpty(city.getData())||city.getData().size()!=1){
			log.error("biz updateAddress api fail");
			throw new BusinessException("获取地址api异常,请联系管理员");
		}
		address=CommonUtil.toString(province.getData().get(0).get("city_name"))+CommonUtil.toString(city.getData().get(0).get("city_name"))+companyAddress.getCompanyAddress();
		/*******************************拼接地址**************************************/
		/*******************************判断db记录是否异常**************************************/
		List<WalletCompany> walletCompanies=walletCompanyMapper.selectList(wrapper);
		if(CommonUtil.isEmpty(walletCompanies)||walletCompanies.size()>1){
			log.error("biz updateAddress api fail:data exception call admin");
			throw new BusinessException("biz updateAddress api fail:data exception call admin");
		}
		WalletCompany walletCompany=null;
		if(walletCompanies.size()==0){
			walletCompany=new WalletCompany();
		}else{
			walletCompany=walletCompanies.get(0);
		}
		WalletMember walletMember=walletMemberService.selectById(companyAddress.getMemberId());
		/*******************************判断db记录是否异常**************************************/
		
		walletCompany.setCompanyAddress(companyAddress.getCompanyAddress());
		walletCompany.setArea(companyAddress.getArea());
		walletCompany.setProvince(companyAddress.getProvince());
		walletCompany.setWMemberId(walletMember.getId());
		walletCompanyMapper.updateById(walletCompany);
		serverResponse=ServerResponse.createBySuccess();
		log.info(CommonUtil.format("biz updateAddress api serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return serverResponse=ServerResponse.createBySuccess();
	}
	
}
