package com.gt.wallet.data.api.tonglian.response.invoice;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月15日 下午7:07:32 
* 类说明 
*/
@Data
@ApiModel(description="查询结果")
public class TH5InvoiceQuery {

	/**
	 * 版式文件URL
	 */
	private String InvoiceInfo ;
	
	/**
	 * 发票代码
	 */
	private String InvoiceCode;
	
	/**
	 * 发票号码
	 */
	private String InvoiceNumber;
}
