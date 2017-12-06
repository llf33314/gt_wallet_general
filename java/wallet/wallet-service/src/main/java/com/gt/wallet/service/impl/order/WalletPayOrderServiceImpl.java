package com.gt.wallet.service.impl.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.constant.WalletConstants;
import com.gt.wallet.data.api.tonglian.TPayOrder;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletPayOrder;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.mapper.order.WalletPayOrderMapper;
import com.gt.wallet.service.log.WalletApiLogService;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.utils.CommonUtil;
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
		String submitNo="wallet"+System.currentTimeMillis();
		payOrder.setSubmitNo(submitNo);
		TPayOrder tPayOrder=new TPayOrder(payOrder.getAmount(),submitNo, walletMember.getFeePercent()*payOrder.getAmount(), payOrder.getAcct(), payOrder.getFrontUrl(), payOrder.getType(), payOrder.getDesc(), walletMember.getMemberNum());
		/************通联下单************/
		ServerResponse<com.alibaba.fastjson.JSONObject> serverResponse=YunSoaMemberUtil.applyDeposit(tPayOrder);
		/************通联下单************/
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		/************记录日志************/
		walletApiLogService.save(JsonUtil.toJSONString(tPayOrder), serverResponse, walletMember.getMemberId(), payOrder.getBackUrl(),submitNo);
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
			walletPayOrder.setAcct(payOrder.getAcct());
			walletPayOrder.setPayType(payOrder.getType());
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
		WalletPayOrder entity=new WalletPayOrder();
		entity.setSysOrderNo(orderNo);
		WalletPayOrder walletPayOrder=walletPayOrderMapper.selectOne(entity);
		if(CommonUtil.isEmpty(walletPayOrder)){
			return ServerResponse.createByErrorCode(WalletResponseEnums.DATA_NULL_ERROR);
		}
		return ServerResponse.createBySuccessCodeData(walletPayOrder);
	}
	
}
