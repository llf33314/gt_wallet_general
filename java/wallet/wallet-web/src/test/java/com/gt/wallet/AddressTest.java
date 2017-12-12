package com.gt.wallet;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.gt.api.dto.ResponseUtils;
import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.utils.WalletWebConfig;
import com.gt.wallet.utils.httpclient.WalletHttpClienUtil;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class AddressTest extends BasicTest {
	

    @Test
    public void sendSimpleMail() throws Exception {
    	ResponseUtils<List<Map<String, Object>>> serverResponse=null;
		RequestUtils<Integer> requestUtils=new RequestUtils<>();
		requestUtils.setReqdata(2);	
		
		serverResponse= 	WalletHttpClienUtil.reqPostUTF8(JSONObject.toJSONString(requestUtils), "http://127.0.0.1:8082/8A5DA52E/shopapi/6F6D9AD2/79B4DE7C/queryCityByLevel.do", ResponseUtils.class, WalletWebConfig.getWxmpKey());
		System.out.println(JsonUtil.toJSONString(serverResponse));
    }

}
