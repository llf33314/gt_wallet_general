package com.gt.wallet.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gt.api.bean.sign.SignBean;
import com.gt.api.bean.sign.SignEnum;
import com.gt.api.dto.ResponseUtils;
import com.gt.api.util.KeysUtil;
import com.gt.api.util.RequestUtils;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.WalletWebConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * BaseController
 *
 * @author zhangmz
 * @create 2017/7/10
 */
@Slf4j
public abstract class BaseController {
	
	public boolean  verification(HttpServletRequest request,HttpServletResponse response, RequestUtils<?> requestUtils) throws IOException{
		log.info("*******************************************签名验证*******************************************");
		// 设置返回编码和类型
		String signKey =WalletWebConfig.getWalletKey();
		//获取header中的签名
		String signStr = request.getHeader("sign");
		// 解析签名
		SignBean signBean = JSON.parseObject(signStr, SignBean.class);
		ResponseUtils<?> responseUtils=	ResponseUtils.createByError();
		// 返回签名验证信息
//		String code = SignUtils.decSign(signKey, signBean, JSONObject.toJSONString(requestUtils));
		String param = JSONObject.toJSONString(requestUtils);
		boolean result=true;
		String code = decSign(signKey, signBean, param);
		if ( SignEnum.TIME_OUT.getCode().equals( code ) ) {
			// 超过指定时间
			responseUtils=	ResponseUtils.createByErrorCodeMessage(WalletResponseEnums.TIMEOUT_ERROR.getCode(),WalletResponseEnums.TIMEOUT_ERROR.getDesc());
			log.error(CommonUtil.format("验证结果:%s", JSONObject.toJSONString(responseUtils)));
			CommonUtil.write(response, responseUtils);
			result=false;
		} else if ( SignEnum.SIGN_ERROR.getCode().equals( code ) ) {
			responseUtils=	ResponseUtils.createByErrorCodeMessage(WalletResponseEnums.SIGN_ERROR.getCode(),WalletResponseEnums.SIGN_ERROR.getDesc());
			log.error(CommonUtil.format("验证结果:%s", JSONObject.toJSONString(responseUtils)));
			// 签名验证错误
			CommonUtil.write(response, responseUtils);	
			result=false;
		} else if ( SignEnum.SERVER_ERROR.getCode().equals( code ) ) {
			responseUtils=	ResponseUtils.createByErrorCodeMessage(WalletResponseEnums.ERROR.getCode(),WalletResponseEnums.ERROR.getDesc());
			log.error(CommonUtil.format("验证结果:%s", JSONObject.toJSONString(responseUtils)));
			// 签名系统错误
			CommonUtil.write(response, responseUtils);
			result=false;
		}
		log.info("*******************************************签名验证*******************************************");
		return result;
	}
	
	
	 /**
     * 签名验证
     * @param signKey 签名密钥
     * @param signBean 签名Bean
     * @return 结果code（对应SignEnum里面的code）
     */
    public static String decSign(String signKey, SignBean signBean, String param){
        String reqTime = signBean.getTimeStamp();
        // 判断时间是否在10分钟内
        boolean timeOut = contrastTimeNow(Long.parseLong(reqTime)) > 10;
        if(timeOut){
            return SignEnum.TIME_OUT.getCode();
        }
        // 根据key+时间撮+随机数-->MD5加密
        String reqSign = signBean.getSign();
        String json="";
        try {
			json=param;
			String sign = KeysUtil.getEncString(signKey + signBean.getTimeStamp() + signBean.getRandNum() + json);
			System.out.println(reqSign);
			System.out.println(sign);
			boolean signFail = !sign.equals(reqSign);
			if(signFail){
				return SignEnum.SIGN_ERROR.getCode();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return SignEnum.SUCCESS.getCode();
    }
    
    private static long contrastTimeNow(Long paramTime){
        return contrastTime(System.currentTimeMillis(), paramTime);
    }
    
    /**
     * 对比两个时间戳相差的分钟数
     * @param bigTime 较大的时间
     * @param smallTime 较小的时间
     * @return
     */
    private static long contrastTime(Long bigTime, Long smallTime){
        return (bigTime - smallTime) / (1000 * 60);
    }


}
