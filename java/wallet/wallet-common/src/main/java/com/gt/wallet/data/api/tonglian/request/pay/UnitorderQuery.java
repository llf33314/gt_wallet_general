package com.gt.wallet.data.api.tonglian.request.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月21日 下午8:39:47 
* 类说明 
*/
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="下单类")
public class UnitorderQuery {

	@ApiModelProperty(name="reqsn",notes="商户退款订单号",required=true)
	private String reqsn; 
	
	
	@ApiModelProperty(name="trxid",notes="渠道平台交易单号",required=true)
	private String trxid; 
	
	
	@ApiModelProperty(name="randomstr",notes="随机生成的字符串",required=true)
	private String randomstr; 

}
