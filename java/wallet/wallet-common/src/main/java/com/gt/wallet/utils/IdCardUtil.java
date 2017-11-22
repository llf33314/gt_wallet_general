package com.gt.wallet.utils;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 上午10:50:33 
* 身份证号码处理类说明 
*/

public class IdCardUtil {
	
	/**
	 * 隐藏手机号码中间4位
	 * @param phone
	 * @return
	 */
	public static String hide(String idCard){
		String str=idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1*****$2");;
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(hide("1111111111111111"));
	}
}
