package com.gt.wallet.utils;

import com.alibaba.fastjson.JSONObject;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.api.util.sign.SignHttpUtils;
import com.gt.wallet.dto.ServerResponse;

import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月31日 下午4:35:28 
* 类说明 
*/
@Slf4j
public class SocketUtil {
	
	public static ServerResponse<?> sendMsg(String pushName,String pushStyle,String pushMsg){
		JSONObject obj=new JSONObject();
		obj.put("pushName", pushName);
		if(CommonUtil.isNotEmpty(pushStyle)){
			obj.put("pushStyle", pushStyle);
		}
		obj.put("pushMsg", pushMsg);
		try {
			String url="https://deeptel.com.cn/8A5DA52E/socket/getSocketApi.do";
//			String url=WalletWebConfig.getDomain()+"8A5DA52E/socket/getSocketApi.do";
			 String result=	SignHttpUtils.WxmppostByHttp(url,obj,"WXMP2017");
			 log.info("result:"+result);
			 return  JsonUtil.parseObject(result, ServerResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createByErrorMessage("socket api fail");
		}
		
	}

	public static void main(String[] args) {
		ServerResponse<?>	 serverResponse=sendMsg("36", null, "https://deeptel.com.cn/restaurant/res_start.do");
	}
	
}
