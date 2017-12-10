package com.gt.wallet.service.impl.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.util.DateTimeKitUtils;
import com.gt.api.util.HttpClienUtils;
import com.gt.api.util.MD5Utils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.constant.WalletConstants;
import com.gt.wallet.constant.WalletLogConstants;
import com.gt.wallet.data.api.tonglian.request.TPayOrder;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.data.wallet.request.SearchPayOrderPage;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletApiLog;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletPayOrder;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.mapper.order.WalletPayOrderMapper;
import com.gt.wallet.service.log.WalletApiLogService;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.DateTimeKit;
import com.gt.wallet.utils.MyPageUtil;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 钱包支付记录 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletPayOrderServiceImpl extends BaseServiceImpl<WalletPayOrderMapper, WalletPayOrder> implements WalletPayOrderService {
	
	@Autowired
	private WalletMemberMapper walletMemberMapper;
	
	@Autowired
	private WalletApiLogService walletApiLogService;
	
	@Autowired
	private WalletPayOrderMapper walletPayOrderMapper;
	

//	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<com.alibaba.fastjson.JSONObject> applyDeposit(PayOrder payOrder)throws Exception {
		log.info(CommonUtil.format("start applyDeposit api:%s",JsonUtil.toJSONString(payOrder)));
		ServerResponse<WalletPayOrder> serverResponseOrder=findByOrderNo(payOrder.getBizOrderNo());
		WalletPayOrder walletPayOrder=null;
		if(!ServerResponse.judgeSuccess(serverResponseOrder)){
			 walletPayOrder=new  WalletPayOrder();
		}else{
			walletPayOrder=serverResponseOrder.getData();
			if(walletPayOrder.getStatus().equals("success")){//已支付
				throw new BusinessException(WalletResponseEnums.PAY_SUCCESS);
			}
		}
		WalletMember params=new WalletMember();
		params.setMemberClass(1);
		params.setMemberId(payOrder.getBusId());
		WalletMember walletMember=walletMemberMapper.selectOne(params);
		if(CommonUtil.isEmpty(walletMember)){
			throw new BusinessException("先请注册多粉钱包会员");
		}
		if(walletMember.getStatus()!=3){//正常状态
			throw new BusinessException("多粉钱包会员账号异常，请联系管理员");
		}
		String format="yyyyMMddHHmmss";
		Date currentTime=DateTimeKitUtils.getNow();
		String md5=MD5Utils.getSmallMD5( DateTimeKit.format(currentTime, format));
		String submitNo=md5+"__"+payOrder.getBizOrderNo();
		payOrder.setSubmitNo(submitNo);
		TPayOrder tPayOrder=new TPayOrder(payOrder.getAmount(),submitNo, walletMember.getFeePercent()*payOrder.getAmount(), payOrder.getAcct(), payOrder.getFrontUrl(), payOrder.getType(), payOrder.getDesc(), walletMember.getMemberNum());
		/************通联下单************/
		ServerResponse<com.alibaba.fastjson.JSONObject> serverResponse=YunSoaMemberUtil.applyDeposit(tPayOrder);
		/************通联下单************/
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		/************记录日志************/
		walletApiLogService.save(JsonUtil.toJSONString(tPayOrder), serverResponse, walletMember.getMemberId(), payOrder.getBackUrl(),submitNo,WalletLogConstants.LOG_PAY);
		/************记录日志************/
		if(ServerResponse.judgeSuccess(serverResponse)){//临时订单入库
			ServerResponse<?> response=save(payOrder);
			if(!ServerResponse.judgeSuccess(response)){
				return ServerResponse.createByErrorCodeMessage(response.getCode(), response.getMsg());
			}
		}
		log.info("end applyDeposit api:%s"+JsonUtil.toJSONString(serverResponse));
		return serverResponse;
	}


	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> save(PayOrder payOrder) throws Exception {
		log.info(CommonUtil.format("start applyDeposit api:%s",JsonUtil.toJSONString(payOrder)));
		WalletMember params=new WalletMember();
		params.setMemberClass(1);
		params.setMemberId(payOrder.getBusId());
		WalletMember walletMember=walletMemberMapper.selectOne(params);
		if(CommonUtil.isEmpty(walletMember)){
			throw new BusinessException("先请注册多粉钱包会员");
		}
		if(walletMember.getStatus()!=3){//正常状态
			throw new BusinessException("多粉钱包会员账号异常，请联系管理员");
		}
		ServerResponse<WalletPayOrder> serverResponse=findByOrderNo(payOrder.getBizOrderNo());
		WalletPayOrder walletPayOrder=null;
		if(!ServerResponse.judgeSuccess(serverResponse)){
			 walletPayOrder=new  WalletPayOrder();
		}else{
			walletPayOrder=serverResponse.getData();
			if(walletPayOrder.getStatus().equals("success")){//已支付
				throw new BusinessException(WalletResponseEnums.PAY_SUCCESS);
			}
		}
		Integer count=0;
		if(CommonUtil.isEmpty(walletPayOrder.getId())){//新增
			String desc=payOrder.getDesc();
			walletPayOrder.setAmount(BigDecimal.valueOf(payOrder.getAmount()));
			walletPayOrder.setBusId(walletMember.getMemberId());
			walletPayOrder.setSysOrderNo(payOrder.getBizOrderNo());
			walletPayOrder.setFee(BigDecimal.valueOf(walletMember.getFeePercent()*payOrder.getAmount()));
			walletPayOrder.setIndustryCode(CommonUtil.toInteger(WalletConstants.INDUSTRYCODE));
			walletPayOrder.setIndustryName(WalletConstants.INDUSTRYNAME);
			walletPayOrder.setVisitSource(1);
			walletPayOrder.setGoodsSummary(desc);
			walletPayOrder.setStatus("pending");
			walletPayOrder.setPayFailMessage("支付中");
			walletPayOrder.setAcct(payOrder.getAcct());
			walletPayOrder.setPayType(payOrder.getType());
			walletPayOrder.setWMemberId(walletMember.getId());
			count=walletPayOrderMapper.insert(walletPayOrder);
		}else{//修改
			walletPayOrder.setSubmitNo(payOrder.getSubmitNo());
			count=walletPayOrderMapper.updateById(walletPayOrder);
		}
		log.info("count:"+count);
		if(count==1){
			return ServerResponse.createBySuccess();
		}else{
			return ServerResponse.createByError();
		}
	}


	@Override
	public ServerResponse<WalletPayOrder> findByOrderNo(String orderNo) throws Exception {
		log.info(CommonUtil.format("start findByOrderNo api:%s",orderNo));
		WalletPayOrder entity=new WalletPayOrder();
		entity.setSysOrderNo(orderNo);
		WalletPayOrder walletPayOrder=walletPayOrderMapper.selectOne(entity);
		if(CommonUtil.isEmpty(walletPayOrder)){
			return ServerResponse.createByErrorCode(WalletResponseEnums.DATA_NULL_ERROR);
		}
		return ServerResponse.createBySuccessCodeData(walletPayOrder);
	}
	
	@Override
	public ServerResponse<WalletPayOrder> findBySubmitOrderNo(String submitOrderNo) throws Exception {
		log.info(CommonUtil.format("start findBySubmitOrderNo api:%s",submitOrderNo));
		WalletPayOrder entity=new WalletPayOrder();
		entity.setSubmitNo(submitOrderNo);
		WalletPayOrder walletPayOrder=walletPayOrderMapper.selectOne(entity);
		if(CommonUtil.isEmpty(walletPayOrder)){
			return ServerResponse.createByErrorCode(WalletResponseEnums.DATA_NULL_ERROR);
		}
		return ServerResponse.createBySuccessCodeData(walletPayOrder);
	}


	@SuppressWarnings("unchecked")
	@Override
	public ServerResponse<?> paySuccessNotify(JSONObject params)throws Exception {
		
		log.info(CommonUtil.format("start paySuccessNotify api:%s",JsonUtil.toJSONString(params)));
		JSONObject rps=params.getJSONObject("rps");
		String status=rps.getString("status");
		String payfailmessage="支付成功";
		switch (status) {
		case "OK"://成功
			status="success";
			break;
		case "pending"://进行中
			status="pending";
			payfailmessage="支付中";
			break;
		default://支付失败
			payfailmessage=CommonUtil.format("code:%s,msg:%s", rps.getString("errorCode"),rps.getString("message"));
			status="fail";
			break;
		}
		JSONObject returnValue=rps.getJSONObject("returnValue");
		String bizOrderNo=returnValue.getString("bizOrderNo");
		String orderNo=returnValue.getString("orderNo");
		String sysorderno=bizOrderNo.split("__")[1];
		ServerResponse<WalletPayOrder> serverResponse=findByOrderNo(sysorderno);
		
		/*****************************记录回调结果**********************************/
		try {
			walletApiLogService.save(JsonUtil.toJSONString(params), null, null, null, bizOrderNo,WalletLogConstants.LOG_PAYNOTITY);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("write api log error");
		}
		/*****************************记录回调结果**********************************/
		log.info(CommonUtil.format("WalletPayOrder: %s", JsonUtil.toJSONString(serverResponse)));
		if(CommonUtil.isNotEmpty(serverResponse.getData())){//
			WalletPayOrder walletPayOrder=serverResponse.getData();
			walletPayOrder.setStatus(status);
			walletPayOrder.setPayFailMessage(payfailmessage);
			walletPayOrder.setExternalNo(orderNo);
			Integer count=walletPayOrderMapper.updateById(walletPayOrder);
			
			Map<String, Object> parms=new HashMap<>();
			params.put("out_trade_no",sysorderno);
			params.put("payType",0);
			ServerResponse<WalletApiLog> response=	walletApiLogService.findById(bizOrderNo, WalletLogConstants.LOG_PAY);
			if(ServerResponse.judgeSuccess(serverResponse)){
				/*****************************通知子系统**********************************/
				Map<String,Object> result=	HttpClienUtils.reqPost(JsonUtil.toJSONString(parms), response.getData().getUrl(), Map.class);
				log.info(CommonUtil.format("result %s", JsonUtil.toJSONString(result)));
				try {
					walletApiLogService.save(JsonUtil.toJSONString(parms),ServerResponse.createByErrorCode(result), walletPayOrder.getWMemberId(), response.getData().getUrl(), bizOrderNo,WalletLogConstants.LOG_PAYNOTITYERP);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("write api log error");
				}
				/*****************************通知子系统**********************************/
			}else{
				log.error("无异步回调通知url");
			}
			if(count==1){
				return ServerResponse.createBySuccess();
			}else{
				return ServerResponse.createByErrorMessage("更新状态失败");
			}
		}else{//订单不存在
			log.error("订单不存在");
			return ServerResponse.createByErrorMessage("订单不存在");
		}
		
	}


	@Override
	public ServerResponse<MyPageUtil<WalletPayOrder>> getPage(Page<?> page,SearchPayOrderPage searchPayOrderPage) {
		log.info(CommonUtil.format("biz接口:分页查询,请求参数:%s", JsonUtil.toJSONString(page)));
		EntityWrapper<WalletPayOrder> wrapper=new EntityWrapper<WalletPayOrder>() ;
		wrapper.where("status={0}",searchPayOrderPage.getStatus());		
		wrapper.where("w_member_id={0}",searchPayOrderPage.getWmemberId());
		if(CommonUtil.isNotEmpty(searchPayOrderPage.getStartTime())&&CommonUtil.isEmpty(searchPayOrderPage.getEndTime())){
			wrapper.between("ctime", searchPayOrderPage.getStartTime(), DateTimeKit.getNowDate());
		}else if(CommonUtil.isNotEmpty(searchPayOrderPage.getEndTime())&&CommonUtil.isEmpty(searchPayOrderPage.getStartTime())){
			wrapper.where("ctime<{0}", searchPayOrderPage.getEndTime());
		}else if(CommonUtil.isNotEmpty(searchPayOrderPage.getEndTime())&&CommonUtil.isNotEmpty(searchPayOrderPage.getStartTime())){
			wrapper.between("ctime", searchPayOrderPage.getStartTime(), searchPayOrderPage.getEndTime());
		}
		Integer total=walletPayOrderMapper.selectCount(wrapper);
		if(CommonUtil.isEmpty(total)||total==0){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
		wrapper.orderBy("id", false);
		Page<WalletPayOrder> page1=new Page<WalletPayOrder>();
		page1.setCurrent(page.getCurrent());
		page1.setRecords(walletPayOrderMapper.selectPage(page1, wrapper));
		MyPageUtil<WalletPayOrder> myPageUtil=new MyPageUtil<WalletPayOrder>(page.getCurrent(), page.getSize());
		myPageUtil.setRecords(walletPayOrderMapper.selectPage(myPageUtil,wrapper),total);
//		MyPageUtil.getInit( page.getRecords().size(), page);
		log.info(CommonUtil.format("page:%s", JsonUtil.toJSONString(page)));
		return ServerResponse.createBySuccessCodeData(myPageUtil);
	}
	
	
	
}
