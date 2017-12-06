package com.gt.wallet.data.api.tonglian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月5日 下午6:40:17 
* 提现参数类说明 
*/
@Data
@ApiModel(description="提现参数")
public class TWithdrawOrder {
	
	
	/**
	 * 商户订单号
	 */
	@ApiModelProperty(name="bizOrderNo",notes="商户订单号",required=true)
	private String bizOrderNo;
	
	
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
	 * 手续费
	 */
	@ApiModelProperty(name="fee",notes="手续费",required=true)
	private Double fee;
	
	
	/**
	 * 银行卡号
	 */
	@ApiModelProperty(name="fee",notes="手续费",required=true)
	private String bankCardNo;
	
	
	/**
	 * 银行卡/账户属性 0：个人银行卡
		1：企业对公账户
		如果不传默认为 0
	 */
	@ApiModelProperty(name="bankCardPro",notes="银行卡/账户属性 ",required=true)
	private Integer bankCardPro=0;
	
	/**
	 * 提现方式
	 */
	@ApiModelProperty(name="withdrawType",notes="提现方式",required=true)
	private String withdrawType="T0";
	
	/**
	 * 描述
	 */
	@ApiModelProperty(name="desc",notes="描述",required=true)
	private String desc;
}
