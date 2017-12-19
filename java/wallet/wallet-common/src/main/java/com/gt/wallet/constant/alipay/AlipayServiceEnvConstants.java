

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
    												
    public static final String PRIVATE_KEY       = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDINka2BWTnlXu5FF9mObSZOmPzwmLcpS0GGcG/CibQPW4ZMW16AlhCPuHjnt94omyBxgbIS0+dG8+G34ZmJH5SGK9BvYpjPGrNBAWmcmT7tkD75CD68e+BDon4EUAy+Qsa8sLbeM0PGCFDhZnJpMfnkf3XbOkk5DspQbgEsdsRXaOhfeo5IYZSi17DRrXFQX2dFNB64VnRFkKX4YhVQrSwFhLXJcVPEog9r/h17ETPX04QUTF3KlGOfEyLIYej7eQPd92QQFqorxpj6NYZGJfoAVxMQkhjqgwjITN+4vvd31rnHMfJPcAgsjoqYSPtNPR8KAXzpQiAvwg8s5XSSCc5AgMBAAECggEAKtFwzfzZV63gh/STcolk1mBmf/tSqV2qA2P+y5ygMGjy4vBvOWjG92P7a3LO2D3kdtyjFrMEk9UezPmBBNdJLMWatbSTpCWHsdDqZG6SqkKQI07mvsR/NFqnpyYJVN1uQpUol0d+k3vVoVsxSK61EhFm+GuScRFelbQ4Zsii0VOUkL6nhY10mXaLFVPRfvz1zEsaPdIfrFsZKRbanUNA0tN95difpnoSGOU1XhCuSdbZZON+gbEMy6dC75Ptr+6/WsmwS0ubvg8q+uxWb/ZQKgimGTAMCAxWeFXUbtTPpZfsDL0vTiQBZIqg4o4Wp0gsbap6/DAM5nJGosC43awvAQKBgQDmp/cB9WyRo2kI2NKTv18q56I27lo+LUsRbAQJqrJDg3+nc+NDNmk3lAhNEGuo4ymhJSzLm2DkMU2rFS5ANk3WuazwZkCTkGwjFrHoUPqc9GUxm1CxgIWQTlYYRw1PW2e3e5pYAXmOWMkO6Mi9w7AYUKpDRFRS4eE9EaYWBRa+zwKBgQDeNfYZSJWAP0vAEs1u1IYuTWUJmunuFBu/tOyfnSkGZX68Kr0lxAb3eeMGg8jbuwUa4K2v/fyYrOONK+OMU2OSa9Zp28EcQGQdsVF/Yi+DIL7wy2zReqkvw55z+SCDstcrhHkoRH3GFNeJHas5761pzGNNFWWVpeUnIZEWCnh7dwKBgH57qOX4EVsjwttJGuBOVv+OGdQJjI8AA2RAWU57vllraLsIv50B31zM3mh0ErWALDnjyta0E8POjYq9iKlJnhGbrAv/5nWFAueS8Gg2z9b15e/3/Z34nkVtz6Rsgg38T7m8G/0age/fHBIC8OGux4ft5BATDjKNDxiP7wWwMLLdAoGAUSvdDjdZhVOZ1DwKFyZDLb7P3cZdFb76s/jfRx8d0VxaA1qJZbZJ5TT9HXAee0XCA6N3Twa0swDUzsjtgmEjymTiGiFQVYWgDMitQJ8ZRAk+Nq/YvFzWWMBYrXyfx/wYZakqqO78EtOiOFBCMBPBgRhMXegnB5dmMfktCqQghtkCgYEArBoej58/OLRrLIMstQUAFKfHeYoz0twqEGPJyKlEck5bSX5rIdkf3qPBfxx+y38YQ9ocWzmUKziCh+KO44i1lpXmXJdtdZ9bF0v66TL/ZA0B7Moa69uXWu6AcvRQMSHXJ7PFX9akBwsf1U8RpKN7KSpq130+IrhX0+5LyFH6HGI=";
    
    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患
    public static final String PUBLIC_KEY        = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyDZGtgVk55V7uRRfZjm0mTpj88Ji3KUtBhnBvwom0D1uGTFtegJYQj7h457feKJsgcYGyEtPnRvPht+GZiR+UhivQb2KYzxqzQQFpnJk+7ZA++Qg+vHvgQ6J+BFAMvkLGvLC23jNDxghQ4WZyaTH55H912zpJOQ7KUG4BLHbEV2joX3qOSGGUotew0a1xUF9nRTQeuFZ0RZCl+GIVUK0sBYS1yXFTxKIPa/4dexEz19OEFExdypRjnxMiyGHo+3kD3fdkEBaqK8aY+jWGRiX6AFcTEJIY6oMIyEzfuL73d9a5xzHyT3AILI6KmEj7TT0fCgF86UIgL8IPLOV0kgnOQIDAQAB";
    /**支付宝网关*/
    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";

    /**授权访问令牌的授权类型*/
    public static final String GRANT_TYPE        = "authorization_code";
}