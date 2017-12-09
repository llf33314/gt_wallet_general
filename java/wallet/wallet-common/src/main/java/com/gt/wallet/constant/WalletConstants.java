package com.gt.wallet.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义常量公用类
 * User : yangqian
 * Date : 2017/7/21 0021
 * Time : 15:54
 */
public class WalletConstants {

    /**
     * 定义session key 开头字符串
     */
    public final static String SESSION_KEY = "wallet-";

    /**
     * 定义 redis key 开头字符串
     */
    public final static String REDIS_KEY = "wallet:";

    /**
     * 定义 socke key 开头字符串
     */
    public final static String SOCKEY_KEY = "wallet-";


    /**
     * 发短信model
     */
    public static final Integer SMS_MODEL = 5;

    /** 商家账户在session key */
    public static final String SESSION_BUSINESS_KEY = "business_key";

    /** 商家微信在session key */
    public static final String SESSION_WXPUBLICUSERS_KEY = "wxPublicUsers";

    /** 会员 **/
    public static final String SESSION_MEMBER_KEY = "member";

    /** 获取商家主账号的 session key **/
    public static final String SESSION_ADMIN_KEY = "PidBusId";


    /**
     * redis 缓存时间
     */
    public static final int REDIS_SECONDS = 60 * 30;
    
    
    /**
     * 直连银行范围(对公)
     */
    public static  Map<String,String> publicBankCard=new HashMap<String, String>();
    
    /**
     * 直连银行范围(对私)
     */
    public static Map<String,String> privateBankCard=new HashMap<String, String>();
    
    /**
     * 通联行业代码
     */
    public static final String  INDUSTRYCODE="1916";
    
    /**
     * 通联行业代码名称
     */
    public static final String  INDUSTRYNAME="便捷付";
    
    static{
    	privateBankCard.put("01020000", "工商银行");
    	privateBankCard.put("01030000", "农业银行");
    	privateBankCard.put("01040000", "中国银行");
    	privateBankCard.put("01050000", "建设银行");
    	privateBankCard.put("03020000", "中信银行");
    	privateBankCard.put("03030000", "光大银行");
    	privateBankCard.put("03040000", "华夏银行");
    	privateBankCard.put("04105840", "平安银行");
    	privateBankCard.put("03080000", "招商银行");
    	privateBankCard.put("03090000", "兴业银行");
    	privateBankCard.put("03100000", "浦发银行");
    	privateBankCard.put("01000000", "邮储银行");
    	privateBankCard.put("04083320", "宁波银行");
    	privateBankCard.put("04243010", "南京银行");
    	privateBankCard.put("14385500", "农信湖南");
    	
    	
    	publicBankCard.put("中国工商银行", "中国农业银行");
    	publicBankCard.put("中国农业银行", "中国农业银行");
    	publicBankCard.put("中国建设银行", "中国建设银行");
    	publicBankCard.put("中信银行", "中信银行");
    	publicBankCard.put("平安银行", "平安银行");
    	publicBankCard.put("兴业银行", "兴业银行");
    	publicBankCard.put("南京银行", "南京银行");
    	publicBankCard.put("农信内蒙", "农信内蒙");
    	publicBankCard.put("招商银行", "招商银行");
    	
    }

}
