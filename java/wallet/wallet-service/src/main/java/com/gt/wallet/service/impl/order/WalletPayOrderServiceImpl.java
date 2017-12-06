package com.gt.wallet.service.impl.order;

import com.gt.wallet.entity.WalletApiLog;
import com.gt.wallet.entity.WalletBank;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletPayOrder;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.log.WalletApiLogMapper;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.mapper.order.WalletPayOrderMapper;
import com.gt.wallet.service.impl.member.WalletBankServiceImpl;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.api.tonglian.TPayOrder;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.dto.ServerResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	private WalletApiLogMapper walletApiLogMapper;

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<com.alibaba.fastjson.JSONObject> applyDeposit(PayOrder payOrder)throws Exception {
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
		TPayOrder tPayOrder=new TPayOrder(payOrder.getAmount(), payOrder.getBizOrderNo(), walletMember.getFeePercent()*payOrder.getAmount(), payOrder.getAcct(), payOrder.getFrontUrl(), payOrder.getType(), payOrder.getDesc(), walletMember.getMemberNum());
		ServerResponse<com.alibaba.fastjson.JSONObject> serverResponse=YunSoaMemberUtil.applyDeposit(tPayOrder);
		WalletApiLog walletApiLog=new WalletApiLog();
		walletApiLog.setMsg(serverResponse.getMsg());
		walletApiLog.setParamsJson(JsonUtil.toJSONString(tPayOrder));
		walletApiLog.setResult(JsonUtil.toJSONString(serverResponse));
		walletApiLog.setType(1);
		walletApiLog.setUrl(null);
		walletApiLog.setWMemberId(walletMember.getMemberId());
		if(ServerResponse.judgeSuccess(serverResponse)){
			walletApiLog.setStatus(serverResponse.getCode());
			
		}else{
			walletApiLog.setStatus(-1);
		}
		Integer count=walletApiLogMapper.insert(walletApiLog);
//		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
//		if(serverResponse.getCode()==0){
//			walletBank.setStatus(1);
//			walletBankMapper.updateById(walletBank);
//		}
		log.info("end applyDeposit api:%s"+JsonUtil.toJSONString(serverResponse));
		return serverResponse;
	}
	
}
