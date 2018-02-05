package com.gt.wallet.data.api.tonglian.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年2月3日 下午3:08:28 
* 类说明 
*/

@Data
@ApiModel(description="托管代付")
public class TsignalAgentPaySimplify {
	
	@ApiModelProperty(name="bizOrderNo",notes="订单号",required=true)
	private String bizOrderNo;
	
	@ApiModelProperty(name="bizOrderNo",notes="订单号",required=true)
	private String bizUserId;
	
	
	@ApiModelProperty(name="amount",notes="金额",required=true)
	private double amount;
	
	@ApiModelProperty(name="summary",notes="摘要",required=true)
	private String summary;
}
