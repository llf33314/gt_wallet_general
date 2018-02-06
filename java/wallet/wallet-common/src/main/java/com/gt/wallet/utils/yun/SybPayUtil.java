package com.gt.wallet.utils.yun;

import java.util.TreeMap;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.api.tonglian.request.TPayOrder;
import com.gt.wallet.data.api.tonglian.response.pay.CancelResponse;
import com.gt.wallet.data.api.tonglian.response.pay.UnitorderPayResponse;
import com.gt.wallet.data.api.tonglian.response.pay.UnitorderQueryResponse;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.WalletWebConfig;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SybPayUtil {
	public static UnitorderPayResponse pay(TPayOrder tPayOrder) throws Exception{
		//payOrder.getAmount()*100, tPayOrder.getBizOrderNo(), payOrder.getType()==1?"W01":"A01", tPayOrder.getDesc(), tPayOrder.getDesc(), payOrder.getAcct(), "", WalletWebConfig.getYunpaySuccessNotifyUrl(), "no_credit"
		HttpConnectionUtil http = new HttpConnectionUtil(WalletWebConfig.getApiUrl()+"/pay");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("cusid", WalletWebConfig.getCusid());
		params.put("appid", WalletWebConfig.getAppid());
		params.put("version", "11");
		params.put("trxamt",CommonUtil.toString(tPayOrder.getAmount()*100));
		params.put("reqsn",  tPayOrder.getBizOrderNo());
		params.put("paytype",( tPayOrder.getType()==1||tPayOrder.getType()==6)?"W01":"A01");
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("body", tPayOrder.getDesc());
		params.put("remark", tPayOrder.getDesc());
		if( tPayOrder.getType()==1||tPayOrder.getType()==2){
			params.put("acct", tPayOrder.getAcct());
		}
		if( tPayOrder.getType()==6||tPayOrder.getType()==7){
			params.put("authcode", tPayOrder.getAcct());
		}
		params.put("notify_url", WalletWebConfig.getYunpaySuccessNotifyUrl());
		params.put("limit_pay", "no_credit");
		params.put("sign", SybUtil.sign(params,WalletWebConfig.getAppkey()));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		log.info("result:"+result);
		UnitorderPayResponse unitorderPayResponse= JsonUtil.parseObject(result, UnitorderPayResponse.class);
		return unitorderPayResponse;
		
	}
	
	public static CancelResponse cancel(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(WalletWebConfig.getApiUrl()+"/cancel");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("cusid", WalletWebConfig.getCusid());
		params.put("appid", WalletWebConfig.getAppid());
		params.put("version", "11");
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("oldtrxid", oldtrxid);
		params.put("oldreqsn", oldreqsn);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("sign", SybUtil.sign(params,WalletWebConfig.getAppkey()));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		log.info("result:"+result);
		CancelResponse cancelResponse= JsonUtil.parseObject(result, CancelResponse.class);
		return cancelResponse;
	}
	
	public static CancelResponse refund(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(WalletWebConfig.getApiUrl()+"/refund");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("cusid", WalletWebConfig.getCusid());
		params.put("appid", WalletWebConfig.getAppid());
		params.put("version", "11");
		params.put("trxamt", String.valueOf(trxamt));
		params.put("reqsn", reqsn);
		params.put("oldreqsn", oldreqsn);
		params.put("oldtrxid", oldtrxid);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("sign", SybUtil.sign(params,WalletWebConfig.getAppkey()));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		log.info("result:"+result);
		CancelResponse cancelResponse= JsonUtil.parseObject(result, CancelResponse.class);
		return cancelResponse;
	}
	
	public static UnitorderQueryResponse query(String reqsn,String trxid) throws Exception{
		HttpConnectionUtil http = new HttpConnectionUtil(WalletWebConfig.getApiUrl()+"/query");
		http.init();
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("cusid", WalletWebConfig.getCusid());
		params.put("appid", WalletWebConfig.getAppid());
		params.put("version", "11");
		params.put("reqsn", reqsn);
		params.put("trxid", trxid);
		params.put("randomstr", SybUtil.getValidatecode(8));
		params.put("sign", SybUtil.sign(params,WalletWebConfig.getAppkey()));
		byte[] bys = http.postParams(params, true);
		String result = new String(bys,"UTF-8");
		log.info("result:"+result);
		UnitorderQueryResponse queryResponse= JsonUtil.parseObject(result, UnitorderQueryResponse.class);
		return queryResponse;
	}
	
	
}
