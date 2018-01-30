package com.gt.wallet.data.api.tonglian.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月5日 下午4:12:58 
* 充值接口订单参数类说明 
*/
@Data
@ApiModel(description="划账")
public class ApplicationTransfer {
	
	/**
	 * 订单金额
	 */
	@ApiModelProperty(name="amount",notes="订单金额",required=true)
	public Double amount;
	
	
	/**
	 * 系统订单号
	 */
	@ApiModelProperty(name="bizOrderNo",notes="系统订单号",required=true)
	private String bizOrderNo;
	
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 会员账户
	 */
	private String bizUserId;

	public ApplicationTransfer() {
		super();
	}

	public ApplicationTransfer(Double amount, String bizOrderNo, String desc, String bizUserId) {
		super();
		this.amount = amount;
		this.bizOrderNo = bizOrderNo;
		this.desc = desc;
		this.bizUserId = bizUserId;
	}
	
}
