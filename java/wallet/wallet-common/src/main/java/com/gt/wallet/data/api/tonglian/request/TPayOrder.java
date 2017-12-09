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
@ApiModel(description="支付下单params")
public class TPayOrder {
	
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
	 * 手续费
	 */
	@ApiModelProperty(name="fee",notes="手续费",required=true)
	private Double fee;
	
	
	/**
	 * openid或userid(支付宝)
	 */
	@ApiModelProperty(name="acct",notes="openid或userid(支付宝)",required=true)
	private String acct;
	
	
	/**
	 * 前台通知地址
	 */
	@ApiModelProperty(name="frontUrl",notes="前台通知地址",required=true)
	private String frontUrl;
	
	/**
	 * 后台通知地址
	 */
//	@ApiModelProperty(name="backUrl",notes="后台通知地址",required=true)
//	private String backUrl;
//	
	/**
	 * 支付方式 1：微信 2:支付宝
	 */
	private Integer type;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 会员账户
	 */
	private String bizUserId;

	public TPayOrder(Double amount, String bizOrderNo, Double fee, String acct, String frontUrl,
			Integer type, String desc, String bizUserId) {
		super();
		this.amount = amount;
		this.bizOrderNo = bizOrderNo;
		this.fee = fee;
		this.acct = acct;
		this.frontUrl = frontUrl;
//		this.backUrl = backUrl;
		this.type = type;
		this.desc = desc;
		this.bizUserId = bizUserId;
	}
	
	
	public TPayOrder() {
		super();
	}
	
}
