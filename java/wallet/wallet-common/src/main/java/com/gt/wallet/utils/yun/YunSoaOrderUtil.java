package com.gt.wallet.utils.yun;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import ime.service.client.SOAClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YunSoaOrderUtil {
	private static SOAClient client;

	private String soaName = "OrderService";
	private String bizUserId = "";
	//private String bankCardNo = "";
	private String accountSetNo = "";


//	@Before
//	public void before(){
//		//订单过期时间
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.HOUR, 15);
//		Date date = calendar.getTime();
//		ordErexpireDatetime = sdf.format(date);
//	}



//	//消费申请
//	public void applyConsume() {
//		try{
//			log.info("testApplyConsume start");
//
//			//支付方式
//			//账户余额
//			JSONArray accountPay = new JSONArray();
//			JSONObject accountObject1 = new JSONObject();
//			accountObject1.put("accountSetNo", accountSetNo);
//			accountObject1.put("amount", 10);
//			accountPay.put(accountObject1);
//
//			//快捷
//			JSONObject quickPay = new JSONObject();
//			quickPay.put("bankCardNo", rsaEncrypt(jjBankCardNo));
//			quickPay.put("amount", 10);
//
//			String frontUrl = "http://122.227.225.142:23661/gateway/getPayFront.jsp";
//			JSONObject gatewayPay = new JSONObject();
//			gatewayPay.put("bankCode", "vbank");  //虚拟银行，专门用于测试环境
//			gatewayPay.put("payType", 1L);
//			gatewayPay.put("bankCardNo", rsaEncrypt(jjBankCardNo));
//			gatewayPay.put("amount", 10);
//
//			//代扣
//			JSONObject daikouPay = new JSONObject();
//			daikouPay.put("bankCardNo", rsaEncrypt(jjBankCardNo));
//			daikouPay.put("amount", 10);
//
//			//组装支付方式
//			JSONObject payMethod = new JSONObject();
////			payMethod.put("QUICKPAY", quickPay);
////			payMethod.put("GATEWAY", gatewayPay);
//			payMethod.put("BALANCE", accountPay);
////			payMethod.put("DAIKOU", daikouPay);
//
//			String bizOrderNo = System.currentTimeMillis() + "xf";
//			String backUrl = "";
//			String showUrl = "";
//			String extendInfo = "this is extendInfo";
//
//			JSONArray splitRule = new JSONArray();
//
//			JSONObject splistRule1 = new JSONObject();
//			splistRule1.put("bizUserId", "");
//			splistRule1.put("amount", 6L);
//			splistRule1.put("fee", 1L);
//			splistRule1.put("remark", "");
//
////			JSONObject splistRule1 = new JSONObject();
////			splistRule1.put("bizUserId", "");
////			splistRule1.put("amount", 30L);
////			splistRule1.put("fee", 5L);
////
////			JSONArray splitRuleList1 = new JSONArray();
////			JSONObject splistRule11 = new JSONObject();
////			splistRule11.put("bizUserId", "");
////			splistRule11.put("amount", 20L);
////			splistRule11.put("fee", 10L);
////			splistRule11.put("splitRuleList", new JSONArray());
////			splitRuleList1.put(splistRule11);
////
////			splistRule1.put("splitRuleList", splitRuleList1);
////
//			splitRule.put(splistRule1);
//
//
////			JSONObject splitRule1 = new JSONObject();
////			splitRule1.put("recieverBizUserId", "");
////			splitRule1.put("amount", 100);
////			splitRule.put(splitRule1);
//
//			JSONObject param = new JSONObject();
//			param.put("payerId", bizUserId);
//			param.put("recieverId", sellerBizUserId);
//			param.put("bizOrderNo", bizOrderNo);
//			param.put("amount", 10);
//			param.put("fee", 0);
////			param.put("splitRule", splitRule);
//			param.put("frontUrl", frontUrl);
//			param.put("backUrl", backUrl);
//			param.put("showUrl", showUrl);
//			param.put("ordErexpireDatetime", ordErexpireDatetime);
//			param.put("payMethod", payMethod);
//			param.put("goodsName", "test_goodsName");
//			param.put("goodsDesc", "test_goodsDesc");
//			param.put("industryCode", industryCode);
//			param.put("industryName", industryName);
//			param.put("source", source);
//			param.put("summary", "consume");
//			param.put("extendInfo", extendInfo);
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "consumeApply", param);
//			log.info("response:" + response);
//
//			log.info("testApplyConsume end");
//		}catch(Exception e){
//			log.info("testApplyConsume error");
//			e.printStackTrace();
//		}
//	}

//	//代收申请
//	@Test
//	public void testApplyHostCollect() {
//		try{
//			log.info("testApplyHostCollect start");
//
//			String tradeCode = "1001";
//
//			//支付方式
//			//账户余额
//			JSONArray accountPay = new JSONArray();
//			JSONObject accountObject1 = new JSONObject();
//			accountObject1.put("accountSetNo", accountSetNo);
//			accountObject1.put("amount", 1);
//			accountPay.put(accountObject1);
//
//			//快捷
//			JSONObject quickPay = new JSONObject();
//			quickPay.put("bankCardNo", rsaEncrypt(jjBankCardNo));
//			quickPay.put("amount", 1);
//
//			//网关
//			String frontUrl = "";
//			JSONObject gatewayPay = new JSONObject();
//			gatewayPay.put("bankCode", "vbank");  //虚拟银行，专门用于测试
//			//gatewayPay.put("bankCardType", 1L);
//			gatewayPay.put("payType", 1L);
//			gatewayPay.put("amount", 10);
//
//			//代金券
//			JSONObject couponPay = new JSONObject();
//			couponPay.put("amount", 1);
//
//			//pos
//			JSONObject posPay = new JSONObject();
//			posPay.put("amount", 10);
//
//			//代扣
//			JSONObject daikouPay = new JSONObject();
//			daikouPay.put("bankCardNo", rsaEncrypt(jjBankCardNo));
//			daikouPay.put("amount", 10);
//
//			//组装支付方式
//			JSONObject payMethod = new JSONObject();
////			payMethod.put("QUICKPAY", quickPay);
//			payMethod.put("GATEWAY", gatewayPay);
////			payMethod.put("BALANCE", accountPay);
////			payMethod.put("COUPON", couponPay);
////			payMethod.put("POSPAY", posPay);
////			payMethod.put("DAIKOU", daikouPay);
//
//			JSONArray recieverList = new JSONArray();
//
//			JSONObject reciever1 = new JSONObject();
//			reciever1.put("bizUserId", bizUserId);
//			reciever1.put("amount", 1);
//
//			recieverList.put(reciever1);
////			JSONObject reciever1 = new JSONObject();
////			reciever1.put("bizUserId", bizUserId);
////			reciever1.put("amount", 4);
////
////			recieverList.put(reciever1);
//
//			String bizOrderNo = System.currentTimeMillis() + "ds";
//			String backUrl = "";
//			String showUrl = "";
//			String extendInfo = "this is extendInfo";
//
//			JSONObject param = new JSONObject();
//			param.put("bizOrderNo", bizOrderNo);
//			param.put("payerId", bizUserId);
//			param.put("recieverList", recieverList);
////			param.put("goodsType", 1L);
////			param.put("goodsNo", "fadf");
//			param.put("tradeCode", tradeCode);
//			param.put("amount", 10);
//			param.put("fee", 0);
//			param.put("frontUrl", frontUrl);
//			param.put("backUrl", backUrl);
//			param.put("showUrl", showUrl);
//			param.put("ordErexpireDatetime", ordErexpireDatetime);
//			param.put("payMethod", payMethod);
//			param.put("goodsName", "test_goodsName");
//			param.put("goodsDesc", "test_goodsDesc");
//			param.put("industryCode", industryCode);
//			param.put("industryName", industryName);
//			param.put("source", source);
//			param.put("summary", "consume");
//			param.put("extendInfo", extendInfo);
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "agentCollectApply", param);
//			log.info("response:" + response);
//
//			log.info("testApplyHostCollect end");
//		}catch(Exception e){
//			log.info("testApplyHostCollect error");
//			e.printStackTrace();
//		}
//	}

//	//单笔代付
//	@Test
//	public void testApplyHostPay() {
//		try{
//			log.info("testApplyHostPay start");
//
//			JSONArray collectPayList = new JSONArray();
//
//			JSONObject collectPay1 = new JSONObject();
//			collectPay1.put("bizOrderNo", "1464965224042ds");
//			collectPay1.put("amount", 1);
//			collectPayList.put(collectPay1);
//
////			JSONObject collectPay2 = new JSONObject();
////			collectPay2.put("bizOrderNo", "");
////			collectPay2.put("amount", 0);
////			collectPayList.put(collectPay2);
//
//			JSONArray splitRuleList = new JSONArray();
//
//			JSONObject splistRule1 = new JSONObject();
//			splistRule1.put("bizUserId", bizUserId);
//			splistRule1.put("accountSetNo", accountSetNo);
//			splistRule1.put("amount", 4L);
//			splistRule1.put("fee", 0L);
//			splistRule1.put("remark", "aaaa");
////
////			JSONArray splitRuleList1 = new JSONArray();
////			JSONObject splistRule11 = new JSONObject();
////			splistRule11.put("bizUserId", "");
////			splistRule11.put("amount", 10L);
////			splistRule11.put("fee", 5L);
////			splistRule11.put("splitRuleList", new JSONArray());
////			splitRuleList1.put(splistRule11);
////
////			splistRule1.put("splitRuleList", splitRuleList1);
////
//			splitRuleList.put(splistRule1);
//
//			JSONObject payToBankCardInfo = new JSONObject();
//			String backUrl = "";
//			payToBankCardInfo.put("bankCardNo", rsaEncrypt(jjBankCardNo));
//			payToBankCardInfo.put("amount", 1);
//			payToBankCardInfo.put("backUrl", backUrl);
//
//			String bizOrderNo = System.currentTimeMillis() + "dbdf";
//
//			JSONObject param = new JSONObject();
//			param.put("bizOrderNo", bizOrderNo);
//			param.put("bizUserId", bizUserId);
//			param.put("collectPayList", collectPayList);
//			param.put("accountSetNo", accountSetNo);
////			param.put("payToBankCardInfo", payToBankCardInfo);
//			param.put("amount", 1L);
//			param.put("fee", 0L);
////			param.put("splitRuleList", splitRuleList);
//			//param.put("goodsType", 1L);
//			//param.put("goodsNo", "");
//			param.put("tradeCode", "2001");
//			param.put("summary", "单笔代付");
//			param.put("extendInfo", "this is extendInfo");
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "signalAgentPay", param);
//			log.info("response:" + response);
//
//			log.info("testApplyHostPay end");
//		}catch(Exception e){
//			log.info("testApplyHostPay error");
//			e.printStackTrace();
//		}
//	}
//
//	//批量代付
//	@Test
//	public void testBatchAgentPay(){
//		try{
//			log.info("testBatchAgentPay start");
//
//			JSONArray batchPayList = new JSONArray();
//
//			JSONObject pay1 = new JSONObject();
//			JSONArray collectPayList1 = new JSONArray();
//			JSONObject collectPay11 = new JSONObject();
//			collectPay11.put("bizOrderNo", "");
//			collectPay11.put("amount", 1);
//			collectPayList1.put(collectPay11);
//			JSONArray splitRuleList1 = new JSONArray();
//
//			JSONObject splistRule1 = new JSONObject();
//			splistRule1.put("bizUserId", "");
//			splistRule1.put("accountSetNo", "100002");
//			splistRule1.put("amount", 8L);
//			splistRule1.put("fee", 1L);
//
//			splitRuleList1.put(splistRule1);
//
////			JSONObject payToBankCardInfo1 = new JSONObject();
////			String backUrl1 = "";
////			payToBankCardInfo1.put("bankCardNo", rsaEncrypt(jjBankCardNo));
////			payToBankCardInfo1.put("amount", 90);
////			payToBankCardInfo1.put("backUrl", backUrl1);
//
//			pay1.put("bizOrderNo", System.currentTimeMillis() + "df1");
//			pay1.put("bizUserId", bizUserId);
//			pay1.put("collectPayList", collectPayList1);
//			pay1.put("accountSetNo", accountSetNo);
//			pay1.put("backUrl", "");
////			pay1.put("payToBankCardInfo", payToBankCardInfo1);
//			pay1.put("amount", 1);
//			pay1.put("fee", 0);
////			pay1.put("splitRuleList", splitRuleList1);
//			pay1.put("summary", "单笔代付1");
//			pay1.put("extendInfo", "this is extendInfo");
//			batchPayList.put(pay1);
//
//			JSONObject pay2 = new JSONObject();
//			JSONArray collectPayList2 = new JSONArray();
//			JSONObject collectPay21 = new JSONObject();
//			collectPay21.put("bizOrderNo", "1463584799426ds");
//			collectPay21.put("amount", 1);
//			collectPayList2.put(collectPay21);
//			JSONArray splitRuleList2 = new JSONArray();
//
//			JSONObject payToBankCardInfo2 = new JSONObject();
//			String backUrl2 = "";
//			payToBankCardInfo2.put("bankCardNo", rsaEncrypt(jjBankCardNo));
//			payToBankCardInfo2.put("amount", 100);
//			payToBankCardInfo2.put("backUrl", backUrl2);
//
//			pay2.put("bizOrderNo", System.currentTimeMillis() + "df2");
//			pay2.put("bizUserId", bizUserId);
//			pay2.put("collectPayList", collectPayList2);
//			pay2.put("accountSetNo", accountSetNo);
//
////			pay2.put("payToBankCardInfo", payToBankCardInfo2);
//			pay2.put("amount", 1);
//			pay2.put("fee", 0);
////			pay2.put("splitRuleList", splitRuleList2);
//			pay2.put("summary", "单笔代付2");
//			pay2.put("extendInfo", "this is extendInfo");
////			batchPayList.put(pay2);
//
//			String bizBatchNo = System.currentTimeMillis() + "pldf";
//
//			JSONObject param = new JSONObject();
//			param.put("bizBatchNo", bizBatchNo);
//			param.put("batchPayList", batchPayList);
//			param.put("tradeCode", "2001");
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "batchAgentPay", param);
//			log.info("response:" + response);
//
//			log.info("testBatchAgentPay end");
//		}catch(Exception e){
//			log.info("testBatchAgentPay error");
//			e.printStackTrace();
//		}
//	}

//	//强实名认证
//	@Test
//	public void testHigherCardAuthApply() {
//		try{
//			log.info("testHigherCardAuthApply start");
//
//			String bizOrderNo = System.currentTimeMillis() + "cz";
//			String backUrl = "";
//
//			String extendInfo = "this is extendInfo";
//
//			JSONObject param = new JSONObject();
//			param.put("bizOrderNo", bizOrderNo);
//			param.put("bizUserId", bizUserId);
//			param.put("accountSetNo", accountSetNo);
//			param.put("bankCardNo", rsaEncrypt(jjBankCardNo));
//			param.put("payType", 27);
////			param.put("bankCode", );
////			param.put("ordErexpireDatetime", );
////			param.put("frontUrl", ordErexpireDatetime);
//			param.put("backUrl", backUrl);
//			param.put("summary", "");
//			param.put("extendInfo", "");
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "higherCardAuthApply", param);
//			log.info("response:" + response);
//
//			log.info("testHigherCardAuthApply end");
//		}catch(Exception e){
//			log.info("testHigherCardAuthApply error");
//			e.printStackTrace();
//		}
//	}

//	//后台支付
//	@Test
//	public void testPay() {
//		try{
//			log.info("testPay start");
//
//			JSONObject param = new JSONObject();
//			param.put("bizUserId", bizUserId);
//			param.put("bizOrderNo", "");
//			param.put("tradeNo", "");
//			param.put("verificationCode", "");
//			param.put("consumerIp", "");
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "pay", param);
//			log.info("response:" + response);
//
//			log.info("testPay end");
//		}catch(Exception e){
//			log.info("testPay error");
//			e.printStackTrace();
//		}
//	}
//
//	//商品录入
//	@Test
//	public void testEntryGoods(){
//		try{
//			log.info("testEntryGoods start");
//
//			String bizGoodsNo = System.currentTimeMillis() + "splr";
//
//			//代还款信息
//			JSONArray repayInfo = new JSONArray();
//
//			JSONObject repay1 = new JSONObject();
//			repay1.put("period", 1);
//			repay1.put("amount", 1100);
//			repay1.put("interest", 100);
//			repay1.put("principal", 1000);
//			repay1.put("overplusPrincipal", 2000);
//			JSONObject repay2 = new JSONObject();
//			repay2.put("period", 2);
//			repay2.put("amount", 2200);
//			repay2.put("interest", 200);
//			repay2.put("principal", 2000);
//			repay2.put("overplusPrincipal", 0);
//
//			repayInfo.put(repay1);
//			repayInfo.put(repay2);
//
//			//标的信息
//			JSONObject goodsParams = new JSONObject();
//			goodsParams.put("totalAmount", 10000);
//			goodsParams.put("annualYield", 0.12);
//			goodsParams.put("beginDate", "2015-11-16");
//			goodsParams.put("endDate", "2015-11-17");
//			goodsParams.put("repayPeriodNumber", 12);
//			goodsParams.put("repayType", 1);
//			goodsParams.put("guaranteeType", 1);
//			goodsParams.put("repayInfo", repayInfo);
//
//			JSONObject param = new JSONObject();
//			param.put("bizUserId", bizUserId);
//			param.put("goodsType", 1);
//			param.put("bizGoodsNo", bizGoodsNo);
//			param.put("goodsName", "标的1");
//			param.put("goodsDetail", "我是标的1的详细信息。");
//			param.put("goodsParams", goodsParams);
//			param.put("showUrl", "showUrl");
//			param.put("extendInfo", "this is extendInfo");
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "entryGoods", param);
//			log.info("response:" + response);
//
//			log.info("testEntryGoods end");
//		}catch(Exception e){
//			log.info("testEntryGoods error");
//			e.printStackTrace();
//		}
//	}

	//冻结金额
	@Test
	public void testFreezeMoney(){
		try{
			log.info("testFreezeMoney start");

			String bizFreezenNo = System.currentTimeMillis() + "dj";

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("bizFreezenNo", bizFreezenNo);
			param.put("accountSetNo", accountSetNo);
			param.put("amount", 2);

			log.info("request:" + param);
			JSONObject response = client.request(soaName, "freezeMoney", param);
			log.info("response:" + response);

			log.info("testFreezeMoney end");
		}catch(Exception e){
			log.info("testFreezeMoney error");
			e.printStackTrace();
		}
	}

	//解冻金额
	@Test
	public void testUnfreezeMoney(){
		try{
			log.info("testUnfreezeMoney start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("bizFreezenNo", "");
			param.put("accountSetNo", accountSetNo);
			param.put("amount", 2);

			log.info("request:" + param);
			JSONObject response = client.request(soaName, "unfreezeMoney", param);
			log.info("response:" + response);

			log.info("testUnfreezeMoney end");
		}catch(Exception e){
			log.info("testUnfreezeMoney error");
			e.printStackTrace();
		}
	}

	//退款
	@Test
	public void testRefund(){
		try{
			log.info("testRefund start");

			String bizOrderNo = System.currentTimeMillis() + "tk";

			JSONObject refund1 = new JSONObject();
			refund1.put("bizUserId", bizUserId);
			refund1.put("amount", 10);

			JSONArray refundList = new JSONArray();
			refundList.put(refund1);

			JSONObject param = new JSONObject();
			param.put("bizOrderNo", bizOrderNo);
			param.put("oriBizOrderNo", "");
			param.put("bizUserId", bizUserId);
			param.put("refundList", refundList);
			param.put("amount", 10);
			param.put("couponAmount", 0);
			param.put("feeAmount", 0);

			log.info("request:" + param);
			JSONObject response = client.request(soaName, "refund", param);
			log.info("response:" + response);

			log.info("testRefund end");
		}catch(Exception e){
			log.info("testRefund error");
			e.printStackTrace();
		}
	}

//	//流标退款
//	@Test
//	public void testFailureBidRefund(){
//		try{
//			log.info("testFailureBidRefund start");
//
//			String bizBatchNo = System.currentTimeMillis() + "lbtk";
//
//			JSONArray batchRefundList = new JSONArray();
//
//			JSONObject batchRefund1 = new JSONObject();
//			String batchRefundNo1 = System.currentTimeMillis() + "tklb1";
//			batchRefund1.put("bizOrderNo", batchRefundNo1);
//			batchRefund1.put("oriBizOrderNo", "");
//			batchRefund1.put("summary", "aaaa");
//
//			JSONObject batchRefund2 = new JSONObject();
//			String batchRefundNo2 = System.currentTimeMillis() + "tklb2";
//			batchRefund2.put("bizOrderNo", batchRefundNo2);
//			batchRefund2.put("oriBizOrderNo", "");
//			batchRefund2.put("summary", "bbbb");
//
//			batchRefundList.put(batchRefund1);
////			batchRefundList.put(batchRefund2);
//
//			JSONObject param = new JSONObject();
//			param.put("bizBatchNo", bizBatchNo);
//			param.put("goodsType", 1);
//			param.put("goodsNo", "fadf");
//			param.put("batchRefundList", batchRefundList);
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "failureBidRefund", param);
//			log.info("response:" + response);
//
//			log.info("testFailureBidRefund end");
//		}catch(Exception e){
//			log.info("testFailureBidRefund error");
//			e.printStackTrace();
//		}
//	}

	//平台转账
	@Test
	public void testApplicationTransfer(){
		try{
			log.info("testApplicationTransfer start");

			JSONObject param = new JSONObject();
			param.put("bizTransferNo", "");
			param.put("sourceAccountSetNo", "");
			param.put("targetBizUserId", bizUserId);
			param.put("targetAccountSetNo", accountSetNo);
			param.put("amount", 1);
			param.put("remark", "平台转账");
//			param.put("extendInfo", "");

			log.info("request:" + param);
			JSONObject response = client.request(soaName, "applicationTransfer", param);
			log.info("response:" + response);

			log.info("testApplicationTransfer end");
		}catch(Exception e){
			log.info("testApplicationTransfer error");
			e.printStackTrace();
		}
	}

	//查询余额
	@Test
	public void testQueryBalance(){
		try{
			log.info("testQueryBalance start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", "");
			param.put("accountSetNo", "");

			log.info("request:" + param);
			JSONObject response = client.request(soaName, "queryBalance", param);
			log.info("response:" + response);

			log.info("testQueryBalance end");
		}catch(Exception e){
			log.info("testQueryBalance error");
			e.printStackTrace();
		}
	}

	//查询订单状态
	@Test
	public void testGetOrderDetail(){
		try{
			log.info("testGetOrderDetail start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("bizOrderNo", "");

			log.info("request:" + param);
			JSONObject response = client.request(soaName, "getOrderDetail", param);
			log.info("response:" + response);

			log.info("testGetOrderDetail end");
		}catch(Exception e){
			log.info("testGetOrderDetail error");
			e.printStackTrace();
		}
	}


	/**
	 * 查询支付详情
	 */
	@Test
	public void testQueryOrderPayDetail(){
		try{
			log.info("testQueryOrderPayDetail start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", "");
			param.put("bizOrderNo", "");

			log.info("request:" + param);
			JSONObject response = client.request(soaName, "queryOrderPayDetail", param);
			log.info("response:" + response);

			log.info("testQueryOrderPayDetail end");
		}catch(Exception e){
			log.info("testQueryOrderPayDetail error");
			e.printStackTrace();
		}
	}

	//查询收支明细
	@Test
	public void testQueryInExpDetail(){
		try{
			log.info("testQueryInExpDetail start");

			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("accountSetNo", "");
			param.put("dateStart", "2016-02-02");
			param.put("dateEnd", "2016-02-17");
			param.put("startPosition", 1);
			param.put("queryNum", 9);

			log.info("request:" + param);
			JSONObject response = client.request(soaName, "queryInExpDetail", param);
			log.info("response:" + response);

			log.info("testQueryInExpDetail end");
		}catch(Exception e){
			log.info("testQueryInExpDetail error");
			e.printStackTrace();
		}
	}

//	//跨应用转账
//	@Test
//	public void testTransferCrossApplication(){
//		try{
//			log.info("testTransferCrossApplication start");
//
//			String bizOrderNo = System.currentTimeMillis() + "kyyzz";
//
//			JSONObject param = new JSONObject();
//			param.put("bizOrderNo", bizOrderNo);
//			param.put("bizUserId", bizUserId);
//			param.put("accountSetNo", "");
//			param.put("opponentSysid", "");
//			param.put("opponentBizUserId", "");
//			param.put("opponentAccountSetNo", "");
//			param.put("amount", 1);
//			param.put("source", 1);
//			param.put("summary", "aaaa");
//			//param.put("extendInfo", null);
//
//			log.info("request:" + param);
//			JSONObject response = client.request(soaName, "transferCrossApplication", param);
//			log.info("response:" + response);
//
//			log.info("testTransferCrossApplication end");
//		}catch(Exception e){
//			log.info("testTransferCrossApplication error");
//			e.printStackTrace();
//		}
//	}

//	//RSA加密
//	public String rsaEncrypt(String signStr) throws Exception{
//		try{
//			log.info("rsaEncrypt start");
//
//			RSAUtil rsaUtil = new RSAUtil((RSAPublicKey)publicKey, (RSAPrivateKey)privateKey);
//			return rsaUtil.encrypt(signStr);
//		}catch(Exception e){
//			log.info("rsaEncrypt error");
//			e.printStackTrace();
//			throw e;
//		}
//	}
//
//	@Test
//	public void testAdjustAccount() throws Exception{
//
//		JSONObject param = new JSONObject();
//		param.put("bizNo", "");
//		param.put("sysid", "");
//		param.put("sourceMemberId", 2L);
//		param.put("sourceAccountTypeId", 1L);
//		param.put("targetMemberId", 17L);
//		param.put("targetAccountTypeId", 6L);
//		param.put("amount", 10L);
//		param.put("remark", "zz");
//
//		JSONObject response = client.request(soaName, "cancel", param);
//
//		log.info(response);
//		log.info("over");
//	}
}
