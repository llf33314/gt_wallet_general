package com.gt.wallet.data.api.tonglian.response.invoice;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月15日 下午7:06:08 
* 类说明 
*/
@Data
@ApiModel(description="结果父级")
public class BaseResult {

	
	@SuppressWarnings("rawtypes")
	private TH5InvoiceResponse Result;
}
