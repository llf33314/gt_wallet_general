package com.gt.wallet.data.api.tonglian.response;

import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月7日 下午4:04:24 
* 通联相应类类说明 
*/
@Data
public class YunResponse {
	
	/**
	 * 分配的系统编号
	 */
	private String sysid;
	
	/**
	 * 签名
	 */
	private String sign;
	
	/**
	 * 请求时间戳
	 */
	private String timestamp;
	
	/**
	 * 接口版本(现为 1.0) 
	 */
	private String v;
	
	private Rps<?> rps;

}
