package com.gt.wallet.data.api.tonglian.response.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年2月6日 上午10:19:31 
* 类说明 
*/
@Data
@ApiModel(description="订单响应类父级")
public class PayBaseResponse {
	
	/**
	 * 返回码
	 */
	@ApiModelProperty(name="retcode",notes="返回码",required=true)
	private String retcode;
	
	
	/**
	 * 返回码说明
	 */
	@ApiModelProperty(name="retmsg",notes="返回码说明",required=true)
	private String retmsg;

}
