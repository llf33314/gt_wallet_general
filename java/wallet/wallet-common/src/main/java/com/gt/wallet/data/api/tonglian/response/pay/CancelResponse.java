package com.gt.wallet.data.api.tonglian.response.pay;

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
@ApiModel(description=" 交易撤销响应")
public class CancelResponse extends PayBaseResponse {
	
	
	@ApiModelProperty(name="cusid",notes="商户号",required=true)
	private String cusid;
	
	
	@ApiModelProperty(name="appid",notes="应用 ID",required=true)
	private String appid;
	
	@ApiModelProperty(name="trxid",notes="交易单号",required=true)
	private String trxid; 
	
	
	@ApiModelProperty(name="reqsn",notes="商户交易单号",required=true)
	private String reqsn; 
	
	
	@ApiModelProperty(name="randomstr",notes="随机字符串",required=true)
	private String randomstr; 
	
	
	@ApiModelProperty(name="trxstatus",notes="交易状态",required=true)
	private String trxstatus; 
	
	
	@ApiModelProperty(name="fintime",notes="交易完成时间",required=true)
	private String fintime; 
	
	@ApiModelProperty(name="errmsg",notes="错误原因",required=true)
	private String errmsg;
	
	
	@ApiModelProperty(name="sign",notes="签名",required=true)
	private String sign;
}
