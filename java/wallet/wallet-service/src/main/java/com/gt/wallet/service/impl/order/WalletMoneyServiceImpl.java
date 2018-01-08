package com.gt.wallet.service.impl.order;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.constant.WalletConstants;
import com.gt.wallet.constant.WalletLogConstants;
import com.gt.wallet.data.api.tonglian.request.TWithdrawOrder;
import com.gt.wallet.data.wallet.request.SearchPayOrderPage;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletBank;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletMoney;
import com.gt.wallet.entity.WalletQuota;
import com.gt.wallet.enums.WalletMsgEnums;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.mapper.order.WalletMoneyMapper;
import com.gt.wallet.service.log.WalletApiLogService;
import com.gt.wallet.service.log.WalletMessageService;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.service.order.WalletIndexStatisticsService;
import com.gt.wallet.service.order.WalletMoneyService;
import com.gt.wallet.utils.BankUtil;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.DateTimeKit;
import com.gt.wallet.utils.MyPageUtil;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 提现记录表 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletMoneyServiceImpl extends BaseServiceImpl<WalletMoneyMapper, WalletMoney> implements WalletMoneyService {
	
	@Autowired
	private WalletMoneyMapper walletMoneyMapper;
	
	@Autowired
	private WalletMemberService walletMemberService;
	
	@Autowired
	private WalletBankService walletBankService;
	
	@Autowired
	private WalletApiLogService walletApiLogService;
	
	@Autowired
	private WalletMemberMapper walletMemberMapper;
	
	@Autowired
	private WalletMessageService messageService;

	@Override
	public ServerResponse<MyPageUtil<WalletMoney>> getPage(Page<?> page, SearchPayOrderPage searchPayOrderPage) throws Exception{
		log.info(CommonUtil.format("start biz getPage api params:%s", JsonUtil.toJSONString(page)));
		EntityWrapper<WalletMoney> wrapper=new EntityWrapper<WalletMoney>() ;
		wrapper.where("status={0}",searchPayOrderPage.getStatus());		
		wrapper.where("w_member_id={0}",searchPayOrderPage.getWmemberId());
		if(CommonUtil.isNotEmpty(searchPayOrderPage.getStartTime())&&CommonUtil.isEmpty(searchPayOrderPage.getEndTime())){
			wrapper.between("ctime", searchPayOrderPage.getStartTime(), DateTimeKit.getNowDate());
		}else if(CommonUtil.isNotEmpty(searchPayOrderPage.getEndTime())&&CommonUtil.isEmpty(searchPayOrderPage.getStartTime())){
			wrapper.where("ctime<{0}", searchPayOrderPage.getEndTime());
		}else if(CommonUtil.isNotEmpty(searchPayOrderPage.getEndTime())&&CommonUtil.isNotEmpty(searchPayOrderPage.getStartTime())){
			wrapper.between("ctime", searchPayOrderPage.getStartTime(), searchPayOrderPage.getEndTime());
		}
		Integer total=walletMoneyMapper.selectCount(wrapper);
		if(CommonUtil.isEmpty(total)||total==0){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
		wrapper.orderBy("id", false);
		Page<WalletMoney> page1=new Page<WalletMoney>();
		page1.setCurrent(page.getCurrent());
		page1.setRecords(walletMoneyMapper.selectPage(page1, wrapper));
		MyPageUtil<WalletMoney> myPageUtil=new MyPageUtil<WalletMoney>(page.getCurrent(), page.getSize());
		myPageUtil.setRecords(walletMoneyMapper.selectPage(myPageUtil,wrapper),total);
		for(int i=0;i<myPageUtil.getRecords().size();i++){
			myPageUtil.getRecords().get(i).setBankCardNo(BankUtil.hide(YunSoaMemberUtil.rsaDecrypt(myPageUtil.getRecords().get(i).getBankCardNo())));
		}
		log.info(CommonUtil.format("biz getPage api page:%s", JsonUtil.toJSONString(page)));
		return ServerResponse.createBySuccessCodeData(myPageUtil);
	}

	@Override
	public ServerResponse<IndexStatistics> getTotal(Integer wmemberId) {
		log.info(CommonUtil.format("start biz getTotal api params:%s", JsonUtil.toJSONString(wmemberId)));
		IndexStatistics indexStatistics=walletMoneyMapper.getStatisticsByWmemberId(wmemberId);
		log.info(CommonUtil.format("biz getTotal api indexStatistics:%s", JsonUtil.toJSONString(indexStatistics)));
		return ServerResponse.createBySuccessCodeData(indexStatistics);
	}

	@Override
	public ServerResponse<Integer> withdrawApply(Integer busId, double money, Integer bankId) throws Exception{
		log.info(CommonUtil.format("start biz withdrawApply api params:%s,%s,%s",busId,money,bankId));
		if(money<1000){
			throw new BusinessException("请输入大于1000的金额");
		}
		ServerResponse<List<WalletMember>> responseMember=walletMemberService.findMember(busId);
		if(ServerResponse.judgeSuccess(responseMember)==false||responseMember.getData().size()<=0){
			log.error("biz withdrawApply api fail:请先注册多粉钱包会员");
			throw new BusinessException("请先注册多粉钱包会员");
		}
		WalletMember walletMember=responseMember.getData().get(0);
		if(walletMember.getIsBindingPhone()==0){
			log.error("biz withdrawApply api fail:请先绑定手机号码");
			throw new BusinessException("请先绑定手机号码");
		}else if(walletMember.getStatus()!=3){
			String desc="";
			switch (walletMember.getStatus()) {
			//会员状态(-2:删除,-1:锁定用户,0:创建,1:审核中,3:正常使用)
			case -2:
				desc="删除";
				break;
			case -1:
				desc="锁定用户";
				break;
			case -0:
				desc="创建中";
				break;
			default:
				break;
			}
			log.error("biz withdrawApply api fail:账号状态("+desc+")异常,禁止提现");
			throw new BusinessException("账号状态("+desc+")异常,禁止提现");
		}
		if(walletMember.getMemberType()==3&&(CommonUtil.isEmpty(walletMember.getWalletIndividual())||walletMember.getWalletIndividual().getIdentityChecked()==0)){//个人未实名认证
			log.error("biz withdrawApply api fail:请先实名认证");
			throw new BusinessException("请先实名认证");
		}else if(walletMember.getMemberType()==2&&(CommonUtil.isEmpty(walletMember.getWalletCompany()))){//企业
			log.error("biz withdrawApply api fail:请先完成企业认证");
			throw new BusinessException("请先完成企业认证");
		}
		WalletBank walletBank=	walletBankService.selectById(bankId);
		if(CommonUtil.isEmpty(walletBank)){
			log.error("biz withdrawApply api fail:银行卡不存在");
			throw new BusinessException("银行卡不存在");
		}else if(walletBank.getCardState()==0){
			log.error("biz withdrawApply api fail:银行卡无效");
			throw new BusinessException("银行卡无效");
		}else if(walletBank.getStatus()!=1){
			log.error("biz withdrawApply api fail:此银行卡还没绑定");
			throw new BusinessException("此银行卡还没绑定");
		}
		ServerResponse<IndexStatistics> serverResponseIndexStatistics=	getTotal(walletMember.getId());
		if(CommonUtil.isEmpty(serverResponseIndexStatistics)||CommonUtil.isEmpty(serverResponseIndexStatistics.getData())){
			throw new BusinessException("当前账号余额为0,不可提现!");
		}
		if(serverResponseIndexStatistics.getData().getBalance()<money){
			throw new BusinessException("当前账号余额不足,不可提现!");
		}
		String bizOrderNo="TX"+System.currentTimeMillis();
		Integer bankCardPro=0;
		if(walletBank.getCardClass()==2){
			bankCardPro=1;
		}
		String desc= "商家提现";
		TWithdrawOrder consumeOrder=new TWithdrawOrder(bizOrderNo, walletMember.getMemberNum(), money, 2.0, walletBank.getCardNo(), bankCardPro, "T0", desc);
		ServerResponse<String>  serverResponse=YunSoaMemberUtil.applyWithdraw(consumeOrder);
		log.info(CommonUtil.format("biz withdrawApply api 通联提现接口结果: %s", JsonUtil.toJSONString(serverResponse)));
		try {
			ServerResponse<?> logServerResponse=walletApiLogService.save(JsonUtil.toJSONString(consumeOrder), serverResponse, walletMember.getId(), null, bizOrderNo, WalletLogConstants.LOG_WITHDRAW);
			log.info(CommonUtil.format("biz withdrawApply api log生成结果: %s", JsonUtil.toJSONString(logServerResponse)));
		} catch (Exception e) {
			log.error("biz withdrawApply api fail:生成api日志接口异常");
			// TODO: handle exception
			e.printStackTrace();
		}
		if(ServerResponse.judgeSuccess(serverResponse)){
			WalletMoney walletMoney=new WalletMoney();
			walletMoney.setBusId(walletMember.getMemberId());
			walletMoney.setWMemberId(walletMember.getId());
			walletMoney.setSysOrderNo(bizOrderNo);
			walletMoney.setGoodsNo(bizOrderNo);
			walletMoney.setGoodsType(0);
			walletMoney.setAmount(CommonUtil.toBigDecimal(money));
			walletMoney.setFee(CommonUtil.toBigDecimal(2));
			walletMoney.setValidateType(1);
			walletMoney.setPayMethod("WITHHOLD_TLT");
			walletMoney.setIndustryCode(CommonUtil.toInteger(WalletConstants.INDUSTRYCODE));
			walletMoney.setIndustryName(WalletConstants.INDUSTRYNAME);
			walletMoney.setVisitSource(1);
			walletMoney.setGoodsSummary(desc);
			walletMoney.setStatus("pending");
			walletMoney.setWithdrawType("T0");
			walletMoney.setBankCardNo(walletBank.getCardNo());
			walletMoney.setBankCardPro(walletBank.getCardClass());
			walletMoney.setExternalOrderNo(serverResponse.getData());
			Integer count=	walletMoneyMapper.insert(walletMoney);
			if(count==1){
				log.info("success");
				return	ServerResponse.createBySuccessCodeData(walletMoney.getId());
			}else{
				log.error("biz withdrawApply api error");
				return	ServerResponse.createByError();
			}
		}else{
			
			return ServerResponse.createByErrorCodeMessage(serverResponse.getCode(), serverResponse.getMsg());
		}
	}

	@Override
	public ServerResponse<WalletMoney> getInfoBySysOrderNo(String sysOrderNo) throws Exception {
		log.info("start biz getInfoBySysOrderNo api params:%s"+sysOrderNo);
		WalletMoney params=new  WalletMoney();
		params.setSysOrderNo(sysOrderNo);
		WalletMoney walletMoney=walletMoneyMapper.selectOne(params);
		if(CommonUtil.isEmpty(walletMoney)){
			log.info("biz getInfoBySysOrderNo api error:%s"+WalletResponseEnums.DATA_NULL_ERROR.getDesc());
			return ServerResponse.createByErrorCode(WalletResponseEnums.DATA_NULL_ERROR);
		}else{
			return ServerResponse.createBySuccessCodeData(walletMoney);
		}
	}

	@Override
	public ServerResponse<?> withdrawSuccessNotify(LinkedHashMap<String,Object> params) throws Exception {
		log.info(CommonUtil.format("start biz withdrawSuccessNotify api params:%s",JsonUtil.toJSONString(params)));
		
		JSONObject rps=JsonUtil.parseObject(CommonUtil.toString(params.get("rps")), JSONObject.class);
		String status=rps.getString("status");
		String payfailmessage="提现成功";
		switch (status) {
		case "OK"://成功
			status="success";
			break;
		case "pending"://进行中
			status="pending";
			payfailmessage="提现中";
			break;
		default://支付失败
			payfailmessage=CommonUtil.format("code:%s,msg:%s", rps.getString("errorCode"),rps.getString("message"));
			status="fail";
			break;
		}
		JSONObject returnValue=rps.getJSONObject("returnValue");
		String bizOrderNo=returnValue.getString("bizOrderNo");
		String orderNo=returnValue.getString("orderNo");
		ServerResponse<WalletMoney> serverResponse=getInfoBySysOrderNo(bizOrderNo);
		
	
		/*****************************记录回调结果**********************************/
		try {
		
			walletApiLogService.save(JsonUtil.toJSONString(params), ServerResponse.createBySuccess(), serverResponse.getData().getWMemberId(), null, bizOrderNo,WalletLogConstants.LOG_WITHDRAWSUCCESSNOTIFY);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" biz withdrawSuccessNotify api fail:write api log error");
		}
		try {
			/**********************消息中心****************************/
			messageService.add(serverResponse.getData().getWMemberId(), WalletMsgEnums.MSGTYPE_TAKE_MONEY.getCode(), payfailmessage, serverResponse.getData().getId());
			/**********************消息中心****************************/
		} catch (Exception e) {
			log.error(" biz withdrawSuccessNotify api fail:write api message error");
			e.printStackTrace();
		}
		/*****************************记录回调结果**********************************/
		log.info(CommonUtil.format("WalletPayOrder: %s", JsonUtil.toJSONString(serverResponse)));
		if(CommonUtil.isNotEmpty(serverResponse.getData())){//
			WalletMoney walletPayOrder=serverResponse.getData();
			walletPayOrder.setStatus(status);
			walletPayOrder.setPayFailMessage(payfailmessage);
			walletPayOrder.setExternalOrderNo(orderNo);
			Integer count=walletMoneyMapper.updateById(walletPayOrder);
			if(count==1){
				return ServerResponse.createBySuccess();
			}else{
				return ServerResponse.createByErrorMessage("更新状态失败");
			}
		}else{//订单不存在
			log.error("biz withdrawSuccessNotify api fail:订单不存在");
			return ServerResponse.createByErrorMessage("订单不存在");
		}
		
	}

	@Override
	public ServerResponse<Integer> confirm(Integer busId, Integer id, String verificationCode,String ip) throws Exception {
		log.info(CommonUtil.format("start biz confirm api params:%s,%s,%s",busId,id,verificationCode));
		WalletMoney walletMoney=walletMoneyMapper.selectById(id);
		WalletMember walletMember=walletMemberMapper.selectById(walletMoney.getWMemberId());
		if(CommonUtil.isEmpty(walletMoney)){
			log.error("biz confirm api fail:订单不存在");
			throw new BusinessException("订单不存在");
		}
		if(CommonUtil.isEmpty(walletMember)){
			log.error("biz confirm api fail:请先注册多粉钱包会员");
			throw new BusinessException("请先注册多粉钱包会员");
		}
		if(walletMember.getMemberId()!=busId){
			log.error("biz confirm api fail:当前多粉钱包会员与当前商家不匹配");
			throw new BusinessException("当前多粉钱包会员与当前商家不匹配");
		}
		String sysOrderNo=walletMoney.getSysOrderNo();
		ServerResponse<JSONObject>  serverResponse=YunSoaMemberUtil.pay(walletMember.getMemberNum(),sysOrderNo, verificationCode, ip);
		log.info(CommonUtil.format("biz confirm api 通联提现接口结果: %s", JsonUtil.toJSONString(serverResponse)));
		try {
			ServerResponse<?> logServerResponse=walletApiLogService.save(JsonUtil.toJSONString(serverResponse), serverResponse, walletMember.getId(), null, sysOrderNo, WalletLogConstants.LOG_PAYCONFIRM);
			log.info(CommonUtil.format("log生成结果: %s", JsonUtil.toJSONString(logServerResponse)));
		} catch (Exception e) {
			log.error("biz confirm api fail:生成api日志接口异常");
			e.printStackTrace();
		}
		if(ServerResponse.judgeSuccess(serverResponse)){
			JSONObject data=serverResponse.getData();
			walletMoney.setStatus(data.getString("payStatus"));
			if(data.getString("payStatus").equals("fail")){
				walletMoney.setPayFailMessage(data.getString("payFailMessage"));
			}
			Integer count=	walletMoneyMapper.updateById(walletMoney);
			if(count==1){
				log.info("biz confirm api result:success");
				return	ServerResponse.createBySuccessCodeData(walletMoney.getId());
			}else{
				log.info("biz confirm api fail");
				return	ServerResponse.createByError();
			}
		}else{
			return ServerResponse.createByErrorCodeMessage(serverResponse.getCode(), serverResponse.getMsg());
		}
	
	}
	
}
