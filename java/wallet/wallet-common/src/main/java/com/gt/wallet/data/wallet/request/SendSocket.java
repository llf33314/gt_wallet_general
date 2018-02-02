package com.gt.wallet.data.wallet.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月5日 下午4:12:58 
* 充值接口订单参数类说明 
*/
@Data
@ApiModel(description="消息推送")
public class SendSocket {
	
	
	/**
	 * 商家id
	 */
	@ApiModelProperty(name="busId",notes="商家id",required=true)
	private Integer busId;
	
	/**
	 * 推送路径
	 */
	@ApiModelProperty(name="sendUrl",notes="推送路径",required=false)
	private String sendUrl;
	
}
