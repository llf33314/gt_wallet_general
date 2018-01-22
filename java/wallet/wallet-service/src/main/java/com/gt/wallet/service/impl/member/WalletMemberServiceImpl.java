package com.gt.wallet.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.constant.WalletLogConstants;
import com.gt.wallet.data.wallet.request.SetcashbackPercent;
import com.gt.wallet.data.wallet.request.WalletSet;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletBank;
import com.gt.wallet.entity.WalletCompany;
import com.gt.wallet.entity.WalletIndividual;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.enums.WalletMsgEnums;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.service.log.WalletApiLogService;
import com.gt.wallet.service.log.WalletMessageService;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.service.member.WalletCompanyService;
import com.gt.wallet.service.member.WalletIndividualService;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.utils.BankUtil;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.IdCardUtil;
import com.gt.wallet.utils.MyPageUtil;
import com.gt.wallet.utils.PhoneUtil;
import com.gt.wallet.utils.WalletKeyUtil;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 多粉钱包会员 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletMemberServiceImpl extends BaseServiceImpl<WalletMemberMapper, WalletMember> implements WalletMemberService {
	
	@Autowired
	private WalletMemberMapper walletMemberMapper;
	
	@Autowired
	private WalletIndividualService walletIndividualService;
	
	@Autowired
	private WalletCompanyService walletCompanyService;
	
	@Autowired
	private WalletApiLogService walletApiLogService;
	
	@Autowired
	private WalletMessageService walletMessageService;
	
	
	@Autowired
	private WalletBankService walletBankService;
	
	@Autowired
	private WalletMemberService walletMemberService;
	
	
	/**
	 * 使用事务控制
	 * REQUIRED:当前没有其他事务控制则新增一个，有则引用当前事务(事务控制)
	 * Isolation.DEFAULT：存在多个事务时，A事务读取了一条记录时，B事务将不能修改记录,防止脏读(事务隔离级别)
	 * timeout
	 * rollbackFor：指定事务回滚异常类型
	 */
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<Integer> save(WalletMember walletMember) throws Exception {
		log.info(CommonUtil.format("start biz save api prams:%s", JsonUtil.toJSONString(walletMember)));
		ServerResponse<Integer> serverResponse=null;
		if(CommonUtil.isEmpty(walletMember)){
			log.error("biz save api fail:%s"+WalletResponseEnums.NULL_ERROR.getDesc());
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		if(CommonUtil.isEmpty(walletMember.getId())){//新增
			walletMemberMapper.insert(walletMember);
		}else{//修改
			walletMemberMapper.updateById(walletMember);
		}
		serverResponse=ServerResponse.createBySuccess();
		log.info(CommonUtil.format("biz save api serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return serverResponse=ServerResponse.createBySuccess();
	}


	@Override
	public ServerResponse<List<WalletMember>> findMember(Integer budId) throws Exception {
		log.info(CommonUtil.format("biz findMember api params:%s", JsonUtil.toJSONString(budId)));
		Wrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		wrapper.where("member_id={0}",budId).and("member_class={0}", 1);
		List<WalletMember> walletMembers=walletMemberMapper.selectList(wrapper);
		if(CommonUtil.isNotEmpty(walletMembers)&&walletMembers.size()>0){
			for(int i=0;i<walletMembers.size();i++){
				walletMembers.get(i).setPayPass(null);
				walletMembers.get(i).setExternalNum(null);
				if(CommonUtil.isNotEmpty(walletMembers.get(i).getPhone())){
					walletMembers.get(i).setPhone(PhoneUtil.hide(WalletKeyUtil.getDesString(walletMembers.get(i).getPhone())));
				}
				walletMembers.get(i).setMemberinfoJson(null);
				if(walletMembers.get(i).getMemberType()==3){//个人会员
					ServerResponse<WalletIndividual> serverResponse=walletIndividualService.findByMemberId(walletMembers.get(i).getId());
					if(CommonUtil.isNotEmpty(serverResponse.getData())){
						walletMembers.get(i).setWalletIndividual(serverResponse.getData());
						walletMembers.get(i).getWalletIndividual().setIdentityCardNo(IdCardUtil.hide(YunSoaMemberUtil.rsaDecrypt(walletMembers.get(i).getWalletIndividual().getIdentityCardNo())));
						walletMembers.get(i).getWalletIndividual().setIdentitycardUrl1(null);
						walletMembers.get(i).getWalletIndividual().setIdentitycardUrl2(null);
					}
				}else if(walletMembers.get(i).getMemberType()==2){//企业会员
					ServerResponse<WalletCompany> serverResponse=walletCompanyService.findByMemberId(walletMembers.get(i).getId());
					if(CommonUtil.isNotEmpty(serverResponse.getData())){
						walletMembers.get(i).setWalletCompany(serverResponse.getData());
						walletMembers.get(i).getWalletCompany().setLegalPhone(PhoneUtil.hide(WalletKeyUtil.getDesString(walletMembers.get(i).getWalletCompany().getLegalPhone())));
						walletMembers.get(i).getWalletCompany().setLegalIds(IdCardUtil.hide(YunSoaMemberUtil.rsaDecrypt(walletMembers.get(i).getWalletCompany().getLegalIds())));
						walletMembers.get(i).getWalletCompany().setAccountNo(BankUtil.hide(YunSoaMemberUtil.rsaDecrypt(walletMembers.get(i).getWalletCompany().getAccountNo())));
					}
				}
			}
			return ServerResponse.createBySuccessCodeData(walletMembers);
		}else{
			log.error("biz findMember api fail:"+WalletResponseEnums.DATA_NULL_ERROR.getDesc());
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
	}


	@Override
	public ServerResponse<Integer> isPassWallet(Integer busId) throws Exception {
		log.info(CommonUtil.format("start biz isPassWallet api params:%s", JsonUtil.toJSONString(busId)));
		Wrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		wrapper.where("member_id={0}",busId).and("member_class={0}", 1);
		List<WalletMember> walletMembers=walletMemberMapper.selectList(wrapper);
		if(CommonUtil.isNotEmpty(walletMembers)&&walletMembers.size()>0){
			return ServerResponse.createBySuccessCodeData( 0);
		}else{
			return ServerResponse.createBySuccessCodeData( 1);
		}
	}
	
	@Override
	public ServerResponse<?> isOpen(Integer busId) throws Exception {
		log.info(CommonUtil.format("start biz isOpen api params:%s", JsonUtil.toJSONString(busId)));
		Wrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		wrapper.where("member_id={0}",busId).and("member_class={0}", 1);
		List<WalletMember> walletMembers=walletMemberMapper.selectList(wrapper);
		ServerResponse<?> serverResponse=null;
		if(CommonUtil.isNotEmpty(walletMembers)&&walletMembers.size()>0){
			WalletMember walletMember=walletMembers.get(0);
			
			if(walletMember.getStatus()==3){
				ServerResponse<WalletBank> bankServerResponse=	null;
				if(walletMember.getMemberType()==2){//企业
					bankServerResponse=	walletBankService.getWalletPublicBankByMemberId(walletMember.getId());
				}else{//个人
					bankServerResponse=	walletBankService.getWalletSafeBankByMemberId(walletMember.getId());
				}
				log.error(CommonUtil.format("biz isOpen api bankServerResponse ：%s", JsonUtil.toJSONString(bankServerResponse)));
				if(!ServerResponse.judgeSuccess(bankServerResponse)||CommonUtil.isEmpty(bankServerResponse.getData())){
					serverResponse= ServerResponse.createByErrorCode(WalletResponseEnums.MEMBER_BANK_ERROR);
				}else{
					serverResponse=ServerResponse.createBySuccess();
				}
			}else{
				log.error(CommonUtil.format("biz isOpen api serverResponse ：%s", JsonUtil.toJSONString(serverResponse)));
				serverResponse= ServerResponse.createByErrorCode(WalletResponseEnums.MEMBER_STATE_ERROR);
			}
		}else{
			log.error("biz findMember api fail:"+WalletResponseEnums.MEMBER_NULL_ERROR.getDesc());
			serverResponse=ServerResponse.createByErrorCode(WalletResponseEnums.MEMBER_NULL_ERROR);
		}
		return serverResponse;
	}


	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<Integer> register(Integer memberType,String ip,Integer busId) throws Exception {
		log.info(CommonUtil.format("start biz register api params:%s,%s,%s",memberType,ip,busId));
		EntityWrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		wrapper.where("member_id={0}",busId).and("member_class=1");
//		List<WalletMember> walletMembers=walletMemberMapper.selectBySearch(wrapper);
		List<WalletMember> walletMembers=walletMemberMapper.selectList(wrapper);
		if(CommonUtil.isNotEmpty(walletMembers)&&walletMembers.size()>0){
			log.error(CommonUtil.format("biz register api fail:%s", WalletResponseEnums.EXIST_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.EXIST_ERROR);
		}
		if((memberType!=2&&memberType!=3)){
			throw new BusinessException(WalletResponseEnums.MEMBER_TYPE_ERROR);
		}
		ServerResponse<Integer> serverResponse=null;
		String memberNum="dfw"+System.currentTimeMillis();
		Integer memberClass=1;
		ServerResponse<String> response=YunSoaMemberUtil.createMember(memberNum, memberType, 2);
		if(response.getCode()!=0){
			throw new BusinessException(response.getMsg());
		}
		WalletMember walletMember=new WalletMember();
		walletMember.setMemberNum(memberNum);
		walletMember.setMemberType(memberType);
		walletMember.setMemberClass(memberClass);
		walletMember.setMemberId(busId);
		walletMember.setRegisterIp(ip);
		walletMember.setExternalNum(response.getData());
		int count=walletMemberMapper.insert(walletMember);
		if(count==1){//新增成功
			serverResponse=ServerResponse.createBySuccessCodeData(walletMember.getId());
		}else{
			log.error(CommonUtil.format("biz register api fail:%s", WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.SYSTEM_ERROR);
		}
		log.info(CommonUtil.format("biz register api serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
		return serverResponse;
	}
	
	

	
	@Override
	public ServerResponse<?> bindingPhone(WalletSet walletSet, BusUser busUser) throws Exception {
		log.info(CommonUtil.format("start biz bindingPhone api params:%s,%s", JsonUtil.toJSONString(walletSet), JsonUtil.toJSONString(busUser)));
		Wrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		wrapper.where("member_id={0}",busUser.getId()).and("member_class={0}", 1);
		List<WalletMember> walletMembers=walletMemberMapper.selectList(wrapper);
		if(CommonUtil.isNotEmpty(walletMembers)&&walletMembers.size()>0){
			WalletMember walletMember=walletMembers.get(0);
			if(walletMember.getMemberClass()==1&&walletMember.getMemberId()!=busUser.getId()){
				log.error("biz set api fail:此钱包会员不属于当前登录商家");
				throw new BusinessException("biz bindingPhone api fail:此钱包会员不属于当前登录商家");
			}
			ServerResponse<?> serverResponse=YunSoaMemberUtil.bindPhone(walletMember.getMemberNum(), walletSet.getPhone(), walletSet.getCode());
			log.info("serverResponse:"+JsonUtil.toJSONString(serverResponse));
			if(ServerResponse.judgeSuccess(serverResponse)){
				walletMember.setPhone(WalletKeyUtil.getEncString(walletSet.getPhone()));
				walletMember.setStatus(3);
				walletMemberMapper.updateById(walletMember);
				return ServerResponse.createBySuccess();

			}else{
				return serverResponse;
			}
		}else{
			log.error(CommonUtil.format("biz bindingPhone api fail:%s",WalletResponseEnums.DATA_NULL_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
	}


	
	@Override
	public ServerResponse<?> sendVerificationCode(Integer budId,String phone,Integer wmemberId,Integer verificationCodeType) throws Exception {
		log.info(CommonUtil.format("start biz sendVerificationCode api params:%s,%s,%s,%s",JsonUtil.toJSONString(budId),phone,wmemberId,verificationCodeType));
		WalletMember walletMember=walletMemberMapper.selectById(wmemberId);
		if(CommonUtil.isEmpty(walletMember)){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		if(walletMember.getMemberId()!=budId&&walletMember.getMemberType()==1){
			log.error("biz sendVerificationCode api fail: user excption");
			throw new BusinessException("账号异常");
		}
		return YunSoaMemberUtil.sendVerificationCode(walletMember.getMemberNum(), phone, verificationCodeType);
	}


	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> lockMember(Integer wmemberId) {
		log.info("start biz lockMember api params:"+wmemberId);
		WalletMember walletMember=	walletMemberMapper.selectById(wmemberId);
		if(CommonUtil.isEmpty(walletMember)){
			log.error("biz lockMember api fail:"+WalletResponseEnums.DATA_NULL_ERROR.getDesc());
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		ServerResponse<?> serverResponse=YunSoaMemberUtil.lockMember(walletMember.getMemberNum());
		if(ServerResponse.judgeSuccess(serverResponse)){
			walletMember.setStatus(-1);
			walletMemberMapper.updateById(walletMember);
		}
		try {
			walletMessageService.add(walletMember.getId(), WalletMsgEnums.MSGTYPE_USERLOCK.getCode(),"用户被锁", walletMember.getId());
		} catch (Exception e) {
			log.error("biz lockMember api fail:write msg api error");
			e.printStackTrace();
		}
		return serverResponse;
	}
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> unlockMember(Integer wmemberId) {
		log.info("start biz lockMember api prams:"+wmemberId);
		WalletMember walletMember=	walletMemberMapper.selectById(wmemberId);
		if(CommonUtil.isEmpty(walletMember)){
			log.error("biz lockMember api fail:"+WalletResponseEnums.DATA_NULL_ERROR.getDesc());
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		ServerResponse<?> serverResponse=YunSoaMemberUtil.unlockMember(walletMember.getMemberNum());
		if(ServerResponse.judgeSuccess(serverResponse)){
			ServerResponse<WalletBank> serverResponseWalletBank=	walletBankService.getWalletSafeBankByMemberId(wmemberId);
			walletMember.setStatus(3);
			if(walletMember.getIsBindingPhone()==0||!ServerResponse.judgeSuccess(serverResponseWalletBank)||CommonUtil.isEmpty(serverResponseWalletBank.getData())){//未绑定手机
				walletMember.setStatus(0);
			}else{
				if(walletMember.getMemberType()==3){//个人会员
					ServerResponse<WalletIndividual> responseWalletIndividual=walletIndividualService.findByMemberId(wmemberId);
					if(!ServerResponse.judgeSuccess(responseWalletIndividual)||CommonUtil.isEmpty(responseWalletIndividual.getData())){//对象为空
						walletMember.setStatus(0);
					}
				}else{//企业
					ServerResponse<WalletCompany> serverResponseWalletCompany=	walletCompanyService.findByMemberId(wmemberId);
					WalletCompany walletCompany=	serverResponseWalletCompany.getData();
					if(!ServerResponse.judgeSuccess(serverResponseWalletCompany)||CommonUtil.isEmpty(walletCompany)||CommonUtil.isEmpty(walletCompany.getLegalName())||CommonUtil.isEmpty(walletCompany.getDoBusinessUrl())){//对象为空
						walletMember.setStatus(0);
					}
				}
			}
			walletMemberMapper.updateById(walletMember);
		}
		try {
			walletMessageService.add(walletMember.getId(), WalletMsgEnums.MSGTYPE_USERUNLOCK.getCode(),"解锁用户", walletMember.getId());
		} catch (Exception e) {
			log.error("biz lockMember api fail:write msg api error");
			e.printStackTrace();
		}
		return serverResponse;
	}


	@Override
	public ServerResponse<MyPageUtil<WalletMember>> getPage(Page<WalletMember> page,Integer status,String phone, Integer memberType) throws Exception{
		log.info(CommonUtil.format("start biz getPage api params:%s", JsonUtil.toJSONString(page)));
		EntityWrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		if(CommonUtil.isNotEmpty(status)){
			wrapper.where("status={0}", status);			
		}
		if(CommonUtil.isNotEmpty(phone)){
			wrapper.where("phone={0}",WalletKeyUtil.getEncString(phone) );			
		}
		if(CommonUtil.isNotEmpty(memberType)&&memberType!=0){
			wrapper.where("member_type={0}", memberType);			
		}
		Integer total=walletMemberMapper.selectCount(wrapper);
		if(CommonUtil.isEmpty(total)||total==0){
			log.error("biz getPage api fail:"+WalletResponseEnums.DATA_NULL_ERROR.getDesc());
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
		wrapper.orderBy("id", false);
		Page<WalletMember> page1=new Page<WalletMember>();
		page1.setCurrent(page.getCurrent());
		page1.setRecords(walletMemberMapper.selectPage(page1, wrapper));
		MyPageUtil<WalletMember> myPageUtil=new MyPageUtil<WalletMember>(page.getCurrent(), page.getSize());
		myPageUtil.setRecords(walletMemberMapper.selectPage(myPageUtil,wrapper),total);
		for (int i=0; i<myPageUtil.getRecords().size() ;i++) {
			if(CommonUtil.isNotEmpty(myPageUtil.getRecords().get(i).getPhone())){
				log.info("myPageUtil.getRecords().get(i).getPhone():"+WalletKeyUtil.getDesString(myPageUtil.getRecords().get(i).getPhone()));
				myPageUtil.getRecords().get(i).setPhone(PhoneUtil.hide(WalletKeyUtil.getDesString(myPageUtil.getRecords().get(i).getPhone())));
				log.info("myPageUtil.getRecords().get(i):"+myPageUtil.getRecords().get(i).getPhone());
			}
		}
//		MyPageUtil.getInit( page.getRecords().size(), page);
		log.info(CommonUtil.format("page:%s", JsonUtil.toJSONString(page)));
		return ServerResponse.createBySuccessCodeData(myPageUtil);
	}


	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> reset(Integer busId, String newPhone, String verificationCode, Integer wmemberId)throws Exception {
		log.info(CommonUtil.format("start biz reset api params:%s,%s,%s,%s", busId,newPhone,verificationCode,wmemberId));
		WalletMember walletMember=	walletMemberMapper.selectById(wmemberId);
		if(walletMember.getMemberId()!=busId){
			throw new BusinessException("biz reset api fail:操作异常，此钱包会员不属于当前登录商家");
		}else if(walletMember.getStatus()!=3){
			throw new BusinessException("biz reset api fail:多粉钱包会员账号状态异常("+CommonUtil.getMemberStatusDesc(walletMember.getStatus())+")");
		}
		ServerResponse<?> serverResponse=YunSoaMemberUtil.changeBindPhone(walletMember.getMemberNum(), WalletKeyUtil.getDesString(walletMember.getPhone()), newPhone, verificationCode);
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("memberNum", walletMember.getMemberNum());
		jsonObject.put("oldPhone",WalletKeyUtil.getDesString(walletMember.getPhone()));
		jsonObject.put("newPhone",newPhone);
		jsonObject.put("verificationCode",verificationCode);
		try {
			walletApiLogService.save(JsonUtil.toJSONString(jsonObject), serverResponse, walletMember.getId(), null, null, WalletLogConstants.LOG_CHANGEBINDPHONE);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("biz reset api fail:save log api error");
		}
		if(ServerResponse.judgeSuccess(serverResponse)){
			walletMember.setPhone(YunSoaMemberUtil.rsaEncrypt(newPhone));
			Integer count=walletMemberMapper.updateById(walletMember);
			if(count==1){//成功
				return ServerResponse.createBySuccess();
			} else{//失败
				return ServerResponse.createByError();
			}
		}else{
			return serverResponse;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> setcashbackPercent(SetcashbackPercent setcashbackPercent) {
		log.info("start biz setcashbackPercent api prams:"+JsonUtil.toJSONString(setcashbackPercent));
		Integer wmemberId=setcashbackPercent.getWmemberId();
		WalletMember walletMember=	walletMemberMapper.selectById(setcashbackPercent.getWmemberId());
		if(CommonUtil.isEmpty(walletMember)){
			log.error("biz lockMember api fail:"+WalletResponseEnums.DATA_NULL_ERROR.getDesc());
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		if(setcashbackPercent.getCashbackPercent()>0.4){
			log.error("biz lockMember api fail:返现比例不超过0.4");
			throw new BusinessException("返现比例不超过0.4");
		}
		walletMember.setCashbackPercent(setcashbackPercent.getCashbackPercent());
		boolean result=	walletMemberService.updateAllColumnById(walletMember);
		ServerResponse<?> serverResponse=null;
		if(result){
			serverResponse=ServerResponse.createBySuccess();
		}else{
			serverResponse=ServerResponse.createByError();
		}
		/***********************记录操作日志****************************/
		try {
			walletApiLogService.save(JsonUtil.toJSONString(setcashbackPercent), serverResponse, wmemberId, null, null, WalletLogConstants.LOG_SETCASHBACKPERCENT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("biz lockMember api fail:write log api error");
		}
		/***********************记录操作日志****************************/
		
		return serverResponse;
	}
	
}
