package com.gt.wallet.utils;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 上午10:47:47 
* 手机号码处理工具类说明 
*/

public class PhoneUtil {
	
	/**
	 * 隐藏手机号码中间4位
	 * @param phone
	 * @return
	 */
	public static String hide(String phone){
		String str=phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
		return str;
	}
	
	
	public static void main(String[] args) {
		System.out.println(hide("13528307867"));
	}
	

}
