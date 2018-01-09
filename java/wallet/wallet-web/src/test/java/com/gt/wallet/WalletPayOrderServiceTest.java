package com.gt.wallet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.service.order.WalletMoneyService;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月27日 下午2:29:21 
* 类说明 
*/
@Slf4j
public class WalletPayOrderServiceTest extends BasicTest {
	
	@Autowired
	private WalletPayOrderService walletPayOrderService;
	
	@Autowired
	private WalletMoneyService moneyService;
	

	/**
	 * 充值申请
	 */
//	@Test
//	public void pay(){
//		log.info(" start test pay api");
//		try {
//			PayOrder payOrder=new PayOrder();
//			payOrder.setAcct("");
//			payOrder.setAmount(3000.0);
//			payOrder.setBizOrderNo("HY"+System.currentTimeMillis());
//			payOrder.setBusId(42);
//			payOrder.setDesc("会员线下支付");
//			payOrder.setMemberId(9527);
//			payOrder.setModel(39);
//			payOrder.setNotifyUrl("http://dfpay.yifriend.net/walletPayOrder/79B4DE7C/paySuccessNotify1");
//			payOrder.setReturnUrl(null);
//			payOrder.setSendUrl(null);
//			payOrder.setSubmitNo(null);
//			payOrder.setTakeState(1);
//			payOrder.setType(8);
//			//dfw1511408087692
//			ServerResponse<JSONObject> serverResponse=	walletPayOrderService.applyDeposit(payOrder);
//			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("test pay api error");
//		}
//		log.info(" end test pay api");
//	}
	
	@Test
	public void pay(){
		log.info(" start test pay api");
		try {
			PayOrder payOrder=new PayOrder();
			payOrder.setAcct("");
			payOrder.setAmount(100.0);
			payOrder.setBizOrderNo("HY"+System.currentTimeMillis());
			payOrder.setBusId(42);
			payOrder.setDesc("会员线下支付");
			payOrder.setMemberId(9527);
			payOrder.setModel(39);
			payOrder.setNotifyUrl("http://dfpay.yifriend.net/walletPayOrder/79B4DE7C/paySuccessNotify1");
			payOrder.setReturnUrl(null);
			payOrder.setSendUrl(null);
			payOrder.setSubmitNo(null);
			payOrder.setTakeState(1);
			payOrder.setType(8);
			//dfw1511408087692
			ServerResponse<Integer> serverResponse=	moneyService.withdrawApply(42,1000.0,12);
			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("test pay api error");
		}
		log.info(" end test pay api");
	}
}
