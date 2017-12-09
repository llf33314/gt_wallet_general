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
@ApiModel(description="支付下单params")
public class PayOrder {
	
	
	/**
	 * 商家id
	 */
	private Integer busId;
	
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
	@ApiModelProperty(name="backUrl",notes="后台通知地址",required=true)
	private String backUrl;
	
	/**
	 * 支付方式 1：微信 2:支付宝
	 */
	@ApiModelProperty(name="type",notes="支付方式 1：微信 2:支付宝",required=true)
	private Integer type;
	
	/**
	 * 描述
	 */
	@ApiModelProperty(name="desc",notes="描述",required=true)
	private String desc;
	
	/**
	 * 提交订单号
	 */
	@ApiModelProperty(name="submitNo",notes="提交订单号",required=true)
	private String submitNo;

	/**
	 * 是否可立即提现(1:可取 2:不可取)
	 */
	@ApiModelProperty(name="takeState",notes="是否可立即提现(1:可取 2:不可取)",required=true)
	private Integer takeState;
	
}
