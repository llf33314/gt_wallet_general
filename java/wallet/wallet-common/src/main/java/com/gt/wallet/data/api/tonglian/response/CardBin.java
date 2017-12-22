package com.gt.wallet.data.api.tonglian.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月21日 下午8:39:47 
* 类说明 
*/
@Data
@ApiModel(description="卡bin信息")
public class CardBin {
	
	private CardBinInfo result;

	
	private String reason;
	
	private String ordersign;
	
	
	private Integer error_code; 
}
