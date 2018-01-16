package com.gt.wallet.data.api.tonglian.response.invoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月15日 下午6:57:28 
* 类说明 
*/
@Data
@ApiModel(description="发票api响应")
public class TH5InvoiceResponse<T> {
	
	
	/**
	 * 200：成功，其他：错误
	 */
	@ApiModelProperty(name="ReturnCode",notes="200：成功，其他：错误",required=true)
	private String ReturnCode;
	
	/**
	 * 返回错误信息
	 */
	@ApiModelProperty(name="ReturnMessage",notes="返回错误信息",required=true)
	private String ReturnMessage;
	
	
	private T ReturnData;

}
