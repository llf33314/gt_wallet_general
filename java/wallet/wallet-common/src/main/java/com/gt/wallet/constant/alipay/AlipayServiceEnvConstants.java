

/**

 * Alipay.com Inc.

 * Copyright (c) 2004-2014 All Rights Reserved.

 */

package com.gt.wallet.constant.alipay;


/**
 * 支付宝服务窗环境常量（demo中常量只是参考，需要修改成自己的常量值）
 * 
 * @author taixu.zqq
 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49 taixu.zqq Exp $
 */
public class AlipayServiceEnvConstants {

    /**支付宝公钥-从支付宝生活号详情页面获取*/
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm8/6UWfGiQnAybRF843AuOSMrTuDx9itQfwqsiGK7v+pKSyLTlvNIogAvMDIRPbvscNH9UFj1kS0BlzHn9aQa9iWSGCxkKr6h8poKqeur4EnpKs3sCSRGzX7cW57Ojl7wnBW2LYH/FVCppzT5eJuI53V+jzFqHCz415j8MyL3RhkZse5gxqpGOXEXHUzkNsXHKjqWFZ5/pD/u+srEHgot8rPJdCTw28ONvc5Tf0Pf2x0AWiHJPxHXAP9nkdS7ggUUVqgYmfaqetFzoi33OjHdMf7TuCE7yxen5hY3cxzkDAn8EyGsmicmlqFFFY73687HnDRoFgNOQpCg80fxDuuvwIDAQAB";
    
    /**签名编码-视支付宝服务窗要求*/
    public static final String SIGN_CHARSET      = "GBK";

    /**字符编码-传递给支付宝的数据编码*/
    public static final String CHARSET           = "GBK";

    /**签名类型-视支付宝服务窗要求*/
    public static final String SIGN_TYPE         = "RSA2";
    
    /**开发者账号PID*/
    public static final String PARTNER           = "2088311221458081";

    /** 服务窗appId  */
    //TODO !!!! 注：该appId必须设为开发者自己的生活号id  
    public static final String APP_ID            = "2015080100195397";

    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 
    public static final String PRIVATE_KEY       = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDINka2BWTnlXu5FF9mObSZOmPzwmLcpS0GGcG";
    
    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患
    public static final String PUBLIC_KEY        = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyDZGtgVk55V7uRRfZjm0mTpj88Ji3KUtBhnBvwom0D1uGTFtegJYQj7h457feKJsgcYGyEtPnRvPht";
    /**支付宝网关*/
    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";

    /**授权访问令牌的授权类型*/
    public static final String GRANT_TYPE        = "authorization_code";
}