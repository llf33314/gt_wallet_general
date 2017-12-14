package com.gt.wallet.enums;

/**
 * @author lifengxi(gt_sky@qq.com)
 * @version 创建时间：2017年12月7日 下午5:42:55 日志类型说明
 */

public enum WalletMsgEnums {

	/**
	 * 额度申请结果
	 */
	MSGTYPE_QUOTAREVIEW(1, "额度申请结果"),

	/**
	 * 提现结果
	 */
	MSGTYPE_TAKE_MONEY(2, "提现结果"),

	/**
	 * 用户锁定
	 */
	MSGTYPE_USERLOCK(3, "用户锁定"),

	/**
	 * 用户解锁
	 */
	MSGTYPE_USERUNLOCK(4, "用户解锁");

	WalletMsgEnums(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private final int code;
	private final String desc;

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
}
