package com.gt.wallet.enums;

/**
 * 响应成功Code 类
 *
 * @author zhangmz
 * @create 2017/6/12
 */
public enum WalletResponseEnums {
	/**
	 * 操作成功
	 */
    SUCCESS( 0, "成功" ),
    
    
    
    /**
     * 系統异常
     */
    SYSTEM_ERROR( 1000, "系統异常" ),
    
    /**
     * 请求失败
     */
    ERROR( 1002, "请求失败" ),
    
    /**
     * 需要登录
     */
    NEED_LOGIN(1003, "需要登录" ),
    
    
    /**
     * 非法认证
     */
    AUTHENTICATION( 1004, "非法认证" ),
    
    /**
     * TOKEN失效
     */
    JWT_TOKEN_EXPIRED( 1005, "TOKEN失效" ),
   
    
    
    /**
     * 请求数据为空
     */
    NULL_ERROR(1006, "请求数据为空" ),
    
    /**
     * 邮箱发送异常
     */
    EMAIL_ERROR(1007, "邮箱发送异常" ),
    /**
     * 第三方接口异常
     */
    API_ERROR(1008, "第三方接口异常" ),
    /**
     * 记录为空
     */
    DATA_NULL_ERROR(1009, "记录为空" ),
	
	
	 /**
     * 记录已存在
     */
    EXIST_ERROR(1010, "记录已存在" ),
    
    /**
     * 会员类型异常
     */
    MEMBER_TYPE_ERROR(1011, "会员类型异常" ),
    
    /**
     * 缺少所需附件
     */
    ATTACHMENT_NULL_ERROR(1012, "缺少所需附件" ),
    
    /**
     * 验证码错误
     */
    CODE_ERROR(1013, "验证码为空" ),
    
    /**
     * 两次密码输入不一致
     */
    PWD_ERROR(1014, "两次密码输入不一致" ),
    
	PAY_SUCCESS(1015, "支付异常，此订单已支付" ),
	
	
	/**
	 * 账号状态异常
	 */
	MEMBER_STATE_ERROR(1016, "账号状态异常" ),
	
	
	/**
	 * 账号缺少安全手机
	 */
	MEMBER_PHONE_ERROR(1017, "账号缺少安全手机" ),
	
	
	/**
	 * 账号缺少银行卡
	 */
	MEMBER_BANK_ERROR(1018, "账号缺少银行卡" ),
	
	
	/**
	 * 还没开通多粉钱包
	 */
	MEMBER_NULL_ERROR(1019, "还没开通多粉钱包" ),
	
	
	/**
	 * 签名错误
	 */
	SIGN_ERROR(1020, "签名错误" ),
	
	/**
	 * 请求超时
	 */
	TIMEOUT_ERROR(1021, "请求超时" ),
	
	/**
	 * 请求数据存在HTML标签
	 */
	HTML_ERROR(1022, "数据异常，存在html标签" ),
	
	
	/**
	 * 敏感关键词
	 */
	SENSITIVE_ERROR(1023, "数据异常，敏感关键词" ),
	

	/**
	 * 待结算金额为0，无法划账
	 */
	TRANSFER_ERROR(1024, "待结算金额为0，无法划账" ),
	
	
	;

    private final int    code;
    private final String desc;

    WalletResponseEnums( int code, String desc ) {
	this.code = code;
	this.desc = desc;
    }

    public int getCode() {
	return code;
    }

    public String getDesc() {
	return desc;
    }
}
