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
import com.gt.api.util.MD5Utils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.constant.WalletLogConstants;
import com.gt.wallet.data.wallet.request.WalletPasswordSet;
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
		log.info(CommonUtil.format("biz接口:保存会员,请求参数:%s", JsonUtil.toJSONString(walletMember)));
		ServerResponse<Integer> serverResponse=null;
		if(CommonUtil.isEmpty(walletMember)){
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		if(CommonUtil.isEmpty(walletMember.getId())){//新增
			walletMemberMapper.insert(walletMember);
		}else{//修改
			walletMemberMapper.updateById(walletMember);
		}
		serverResponse=ServerResponse.createBySuccess();
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return serverResponse=ServerResponse.createBySuccess();
	}


	@Override
	public ServerResponse<List<WalletMember>> findMember(Integer budId) throws Exception {
		log.info(CommonUtil.format("biz接口:根据商家id查询,请求参数:%s", JsonUtil.toJSONString(budId)));
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
						walletMembers.get(i).getWalletCompany().setTelephone(PhoneUtil.hide(WalletKeyUtil.getDesString(walletMembers.get(i).getWalletCompany().getTelephone())));
						walletMembers.get(i).getWalletCompany().setLegalIds(IdCardUtil.hide(WalletKeyUtil.getDesString(walletMembers.get(i).getWalletCompany().getLegalIds())));
						walletMembers.get(i).getWalletCompany().setAccountNo(BankUtil.hide(WalletKeyUtil.getDesString(walletMembers.get(i).getWalletCompany().getAccountNo())));
					}
				}
			}
			return ServerResponse.createBySuccessCodeData(walletMembers);
		}else{
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
	}


	@Override
	public ServerResponse<Integer> isPassWallet(Integer busId) throws Exception {
		log.info(CommonUtil.format("biz接口:判断商家是否开通多粉钱包,请求参数:%s", JsonUtil.toJSONString(busId)));
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
		log.info(CommonUtil.format("biz接口:判断商家是否开通多粉钱包,请求参数:%s", JsonUtil.toJSONString(busId)));
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
				log.error(CommonUtil.format("bankServerResponse ：%s", JsonUtil.toJSONString(bankServerResponse)));
				if(!ServerResponse.judgeSuccess(bankServerResponse)||CommonUtil.isEmpty(bankServerResponse.getData())){
					serverResponse= ServerResponse.createByErrorCode(WalletResponseEnums.MEMBER_BANK_ERROR);
				}else{
					serverResponse=ServerResponse.createBySuccess();
				}
			}else{
				log.error(CommonUtil.format("serverResponse ：%s", JsonUtil.toJSONString(serverResponse)));
				serverResponse= ServerResponse.createByErrorCode(WalletResponseEnums.MEMBER_STATE_ERROR);
			}
		}else{
			serverResponse=ServerResponse.createByErrorCode(WalletResponseEnums.MEMBER_NULL_ERROR);
		}
		return serverResponse;
	}


	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<Integer> register(Integer memberType,String ip,Integer busId) throws Exception {
		log.info(CommonUtil.format("biz接口:开通会员,请求参数:%s,%s,%s",memberType,ip,busId));
		EntityWrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		wrapper.where("member_id={0}",busId).and("member_class=1");
//		List<WalletMember> walletMembers=walletMemberMapper.selectBySearch(wrapper);
		List<WalletMember> walletMembers=walletMemberMapper.selectList(wrapper);
		if(CommonUtil.isNotEmpty(walletMembers)&&walletMembers.size()>0){
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
			throw new BusinessException(WalletResponseEnums.SYSTEM_ERROR);
		}
		return serverResponse;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> setpwd(WalletPasswordSet walletPasswordSet, BusUser busUser) throws Exception {
		if(!walletPasswordSet.getCode().equals("8888")){
			log.error(CommonUtil.format("biz接口:修改密码异常:%s",WalletResponseEnums.CODE_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.CODE_ERROR);
		}
		if(!walletPasswordSet.getPwd().equals(walletPasswordSet.getConfirm())){
			log.error(CommonUtil.format("biz接口:修改密码异常:%s",WalletResponseEnums.PWD_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.PWD_ERROR);
		}
		Wrapper<WalletMember> wrapper=new EntityWrapper<WalletMember>() ;
		wrapper.where("member_id={0}",busUser.getId()).and("member_class={0}", 1);
		List<WalletMember> walletMembers=walletMemberMapper.selectList(wrapper);
		if(CommonUtil.isNotEmpty(walletMembers)&&walletMembers.size()>0){
			WalletMember walletMember=walletMembers.get(0);
			walletMember.setPayPass(MD5Utils.getMD5(walletPasswordSet.getPwd()));
			walletMemberMapper.updateById(walletMember);
		}else{
			log.error(CommonUtil.format("biz接口:修改密码异常:%s",WalletResponseEnums.DATA_NULL_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		return ServerResponse.createBySuccess();
	}


	
	@Override
	public ServerResponse<?> sendVerificationCode(Integer budId,String phone,Integer wmemberId,Integer verificationCodeType) throws Exception {
		WalletMember walletMember=walletMemberMapper.selectById(wmemberId);
		if(CommonUtil.isEmpty(walletMember)){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		if(walletMember.getMemberId()!=budId&&walletMember.getMemberType()==1){
			throw new BusinessException("账号异常");
		}
		return YunSoaMemberUtil.sendVerificationCode(walletMember.getMemberNum(), phone, verificationCodeType);
	}


	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> lockMember(Integer wmemberId) {
		log.info("biz:lockMember api:"+wmemberId);
		WalletMember walletMember=	walletMemberMapper.selectById(wmemberId);
		if(CommonUtil.isEmpty(walletMember)){
			log.error("biz:lockMember api:"+WalletResponseEnums.DATA_NULL_ERROR.getDesc());
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
			log.error("write msg api error");
			e.printStackTrace();
		}
		return serverResponse;
	}
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> unlockMember(Integer wmemberId) {
		log.info("biz:lockMember api:"+wmemberId);
		WalletMember walletMember=	walletMemberMapper.selectById(wmemberId);
		if(CommonUtil.isEmpty(walletMember)){
			log.error("biz:lockMember api:"+WalletResponseEnums.DATA_NULL_ERROR.getDesc());
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		ServerResponse<?> serverResponse=YunSoaMemberUtil.unlockMember(walletMember.getMemberNum());
		if(ServerResponse.judgeSuccess(serverResponse)){
			walletMember.setStatus(3);
			walletMemberMapper.updateById(walletMember);
		}
		try {
			walletMessageService.add(walletMember.getId(), WalletMsgEnums.MSGTYPE_USERUNLOCK.getCode(),"解锁用户", walletMember.getId());
		} catch (Exception e) {
			log.error("write msg api error");
			e.printStackTrace();
		}
		return serverResponse;
	}


	@Override
	public ServerResponse<MyPageUtil<WalletMember>> getPage(Page<WalletMember> page,Integer status,String phone, Integer memberType) throws Exception{
		log.info(CommonUtil.format("biz接口:分页查询,请求参数:%s", JsonUtil.toJSONString(page)));
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
		log.info(CommonUtil.format("biz:reset api memberId:%s,%s,%s,%s", busId,newPhone,verificationCode,wmemberId));
		WalletMember walletMember=	walletMemberMapper.selectById(wmemberId);
		if(walletMember.getMemberId()!=busId){
			throw new BusinessException("操作异常，此钱包会员不属于当前登录商家");
		}else if(walletMember.getStatus()!=3){
			throw new BusinessException("多粉钱包会员账号状态异常("+CommonUtil.getMemberStatusDesc(walletMember.getStatus())+")");
		}
		ServerResponse<?> serverResponse=YunSoaMemberUtil.changeBindPhone(walletMember.getMemberNum(), YunSoaMemberUtil.rsaDecrypt(walletMember.getPhone()), newPhone, verificationCode);
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("memberNum", walletMember.getMemberNum());
		jsonObject.put("oldPhone",YunSoaMemberUtil.rsaDecrypt(walletMember.getPhone()));
		jsonObject.put("newPhone",newPhone);
		jsonObject.put("verificationCode",verificationCode);
		try {
			walletApiLogService.save(JsonUtil.toJSONString(jsonObject), serverResponse, walletMember.getId(), null, null, WalletLogConstants.LOG_CHANGEBINDPHONE);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("save log api error");
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
	
	
}
