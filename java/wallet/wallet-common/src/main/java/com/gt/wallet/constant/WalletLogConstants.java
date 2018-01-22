package com.gt.wallet.constant;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月7日 下午5:42:55 
* 日志类型说明 
*/

public class WalletLogConstants {

	/**
	 * 调用通联充值下单api
	 */
	public static final Integer LOG_PAY=1000;
	
	
	/**
	 * 支付异步回调通知接收
	 */
	public static final Integer LOG_PAYNOTITY=1004;
	
	/**
	 * 支付成功回调各子系统
	 */
	public static final Integer LOG_PAYNOTITYERP=3;
	
	
	
	/**
	 * 调用通联提现api
	 */
	public static final Integer LOG_WITHDRAW=1001;
	
	
	/**
	 * 调用通联确认支付接口
	 */
	public static final Integer LOG_PAYCONFIRM=1002;
	
	
	/**
	 * 调用通联变更手机号码接口
	 */
	public static final Integer LOG_CHANGEBINDPHONE=1003;
	
	
	
	/**
	 * 提现异步回调通知接收
	 */
	public static final Integer LOG_WITHDRAWSUCCESSNOTIFY=1005;
	
	
	/**
	 * 通联退款下单
	 */
	public static final Integer LOG_REFUND=1006;
	
	/**
	 * 通联退款异步回调
	 */
	public static final Integer LOG_REFUNDSUCCESSNOTIFY=1007;
	
	
	/**
	 * 退款成功回调各子系统
	 */
	public static final Integer LOG_REFUNDNOTIFY=1008;
	
	
	/**
	 * 设置返现百分比
	 */
	public static final Integer LOG_SETCASHBACKPERCENT=1009;
}
