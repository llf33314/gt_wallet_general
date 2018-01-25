package com.gt.wallet.utils.httpclient;

import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.gt.api.bean.sign.SignBean;
import com.gt.api.dto.ResponseUtils;
import com.gt.api.util.HttpClienUtils;
import com.gt.api.util.KeysUtil;
import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.api.util.httpclient.LocalHttpClient;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.dto.ServerResponse;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 上午10:50:33 
*/
public class WalletHttpClienUtil {
	
	protected static Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE,ContentType.APPLICATION_JSON.toString());


	
	
	
	/**
	 * post请求
	 * @param messageJson
	 * @return
	 */
	public static  <T> T reqPost(String messageJson ,String url,Class<T> clazz){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
										.setHeader(jsonHeader)
										.setUri(url)
										.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
										.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,clazz);
	}

	

	
	/**
	 * post请求(返回乱码)
	 * @param messageJson
	 * @return
	 */
	public static  <T> T reqPostUTF8(String messageJson ,String url,Class<T> clazz){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
										.setHeader(jsonHeader)
										.setUri(url)
										.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
										.build();
		return LocalHttpClient.executeJsonResultUTF8(httpUriRequest,clazz);
	}
	/**
	 * post请求(返回乱码)
	 * @param messageJson
	 * @return
	 */
	public static  <T> T reqPostUTF81(String messageJson ,String url,Class<T> clazz){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
										.setHeader(jsonHeader)
										.setUri(url)
										.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
										.build();
		return LocalHttpClient.executeJsonResultUTF8(httpUriRequest,clazz);
	}
	

	/**
	 * post请求(返回乱码)
	 * @param messageJson
	 * @return
	 */
	/**
	 * post请求(返回乱码)
	 * @param messageJson
	 * @return
	 */
	public static  <T> T reqPostUTF8(String messageJson ,String url,Class<T> clazz,String signKey){
		SignBean signBean = null;
		try {
			String newMsg = messageJson;
			signBean = sign(signKey, newMsg);
		}catch (Exception e){
			e.printStackTrace();
		}
		HttpUriRequest httpUriRequest = RequestBuilder.post()
										.setHeader(jsonHeader)
										.setHeader("sign",com.alibaba.fastjson.JSONObject.toJSONString(signBean))
										.setUri(url)
										.setEntity(new StringEntity(messageJson,Charset.forName("utf-8")))
										.build();
		return LocalHttpClient.executeJsonResultUTF8(httpUriRequest,clazz);
	}
	
	/**
     * 签名
     * @param signKey 签名密钥
     * @return SignBean 签名类JavaBean
	 * @throws Exception 
     */
    @SuppressWarnings("static-access")
	public static SignBean sign(String signKey, String param) throws Exception{
    	KeysUtil keysUtil=new KeysUtil();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String randNum = String.valueOf((int)((Math.random()*9+1)*10000));
        String sign = keysUtil.getEncString(signKey + timeStamp + randNum + param);
        SignBean signBean = new SignBean(sign, timeStamp, randNum);
        return signBean;
    }
	
    /**
	 * post请求
	 * @param messageJson
	 * @return
	 */
	public static  <T> T reqGet(String bankcard ,Class<T> clazz){

//		AppKey：24627762     
//		AppSecret：78a81c7f07f0fca37125b9a59342fe0b
//		AppCode：0814548601264324a242e691c95e800e
		
//		AppKey：24627762
//		AppSecret：78a81c7f07f0fca37125b9a59342fe0b
//		AppCode：0814548601264324a242e691c95e800e

		
		
		String url="http://aliyun.apistore.cn/7";
//		String url="http://api43.market.alicloudapi.com/api/c43";
		String appcode="0814548601264324a242e691c95e800e";
		HttpUriRequest httpUriRequest = RequestBuilder.get()
										.setHeader(jsonHeader)
										.setHeader("Authorization", "APPCODE " + appcode)
										.setUri(url)
										.addParameter("bankcard", bankcard)
										.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,clazz);
	}
    
	public static void main(String arg[]) throws Exception{
//		RequestUtils<Integer> baseParam=new RequestUtils<Integer>();
//		
//		baseParam.setReqdata(36);
//		String ss=com.alibaba.fastjson.JSONObject.toJSONString(baseParam);
//		System.out.println(ss);
//		ResponseUtils map=reqPostUTF8( ss,"http://127.0.0.1:8440/8A5DA52E/shopapi/6F6D9AD2/79B4DE7C/queryWxShopByBusId.do",ResponseUtils.class,"WXMP2017");
//		System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(map));
	//	String url="http://api43.market.alicloudapi.com/api/c43";
//		CardBin map=reqGet("6228481139158261672", CardBin.class);
//		System.out.println(JSONObject.toJSONString(map));
//		WalletIndividualAdd walletIndividualAdd=new WalletIndividualAdd();
//		walletIndividualAdd.setBankName("1312");
//		walletIndividualAdd.setCardNo("12313");
//		walletIndividualAdd.setIdentitycardUrl1File("4113");
//		walletIndividualAdd.setIdentitycardUrl2File("131");
//		walletIndividualAdd.setIdentityNo("sfsf");
//		walletIndividualAdd.setMemberId(113);
//		walletIndividualAdd.setName("sf");
//		walletIndividualAdd.setPhone("sfsd");
//		walletIndividualAdd.setUnionBank("sfsd");
//		
		//Map s=reqPostUTF8(JsonUtil.toJSONString(walletIndividualAdd), url, Map.class);
//		System.out.println(s);
//		List<com.gt.wallet.data.api.tonglian.request.invoice.TInvoiceList> tInvoiceList	=new ArrayList<>();
//		TInvoiceList invoiceList=new TInvoiceList(1, "1000000000000000000", "货物", "", "", 1.0, 1.0, 1.0, 1.0, 1.0,  "1");
//		tInvoiceList.add(invoiceList);
//		TH5Invoice th5Invoice=new TH5Invoice("dfw1515551136943", "dffp"+System.currentTimeMillis(), 9999.0, 1.0, 1.0, tInvoiceList);
		//BaseResult baseResult=	reqPostUTF8(JsonUtil.toJSONString(tInvoiceList), "http://116.62.115.2/H5Invoice", BaseResult.class);
//		System.out.println(JsonUtil.toJSONString(baseResult));
		
		RequestUtils<PayOrder>  requestUtils=new RequestUtils<>();
		PayOrder payOrder=new  PayOrder();
		payOrder.setAcct("134529642439874448");
		payOrder.setAmount(0.01);
		payOrder.setBizOrderNo("HY"+System.currentTimeMillis());
		payOrder.setNotifyUrl("http://dfpay.yifriend.net/walletPayOrder/79B4DE7C/paySuccessNotify1");
		payOrder.setType(1);
		payOrder.setDesc("测试支付");
		payOrder.setTakeState(1);
		payOrder.setBusId(43);
		payOrder.setModel(40);
		requestUtils.setReqdata(payOrder);
		ResponseUtils<?>	response= HttpClienUtils.reqPost(JsonUtil.toJSONString(requestUtils), "http://wallet.yifriend.net:8440/8A5DA52E/memberApi/79B4DE7C/codepay", ResponseUtils.class,"WALLET2017");
		System.out.println(JsonUtil.toJSONString(response));
	//	payOrder.set
		}
}
