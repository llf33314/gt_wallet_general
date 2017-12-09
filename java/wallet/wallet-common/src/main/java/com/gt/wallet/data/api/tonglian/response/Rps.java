package com.gt.wallet.data.api.tonglian.response;

import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月7日 下午4:07:10 
* 类说明 
*/
@Data
public class Rps<T> {
	
	private String service;
	
	
	private String method;
	
	
	private T returnValue ;

}
