package com.gt.wallet.data.api.tonglian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月6日 上午10:10:46 
* 退款订单params说明 
*/
@Data
@ApiModel(description="退款参数")
public class TRefundOrder {
	
	
	/**
	 * 商户订单号
	 */
	@ApiModelProperty(name="bizOrderNo",notes="商户订单号",required=true)
	private String bizOrderNo;
	
	/**
	 * 商户原订单号
	 */
	private String oriBizOrderNo;
	
	/**
	 * 多粉会员账号
	 */
	@ApiModelProperty(name="bizUserId",notes="多粉会员账号",required=true)
	private String bizUserId;
	
	
	/**
	 * 订单金额
	 */
	@ApiModelProperty(name="amount",notes="订单金额",required=true)
	private Double amount;
	
	
	/**
	 * 手续费退款金额 
	 */
	@ApiModelProperty(name="feeAmount",notes="手续费退款金额 ",required=true)
	private Double feeAmount;
	

}
