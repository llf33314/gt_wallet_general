package com.gt.wallet.data.api.tonglian.request.invoice;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月15日 下午6:59:32 
* 类说明 
*/
@Data
@ApiModel(description="发票查询")
public class TH5InvoiceQuery {
	

	/**
	 * 商户编号
	 */
	@ApiModelProperty(name="UserId",notes="商户编号",required=true)
	private String UserId;
	
	
	/**
	 * 发票流水号
	 */
	@ApiModelProperty(name="UserId",notes="发票流水号",required=true)
	private String InvoiceSerialNumber;
	
	
	
	/**
	 * 发票代码
	 */
	@ApiModelProperty(name="InvoiceCode",notes="合计金额",required=true)
	private String InvoiceCode;

	
	
	/**
	 * 发票号码
	 */
	@ApiModelProperty(name="InvoiceNumber",notes="发票号码",required=true)
	private String InvoiceNumber;
	
	

}
