package com.gt.wallet.data.wallet.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月10日 上午9:25:20 
* 商家后台支付订单列表分页类说明 
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="消息列表分页")
public class SearchMsgPage {
	/**
	 * 钱包会员id
	 */
	@ApiModelProperty(name="wmemberId",notes="钱包会员id",required=true)
	private Integer wmemberId;
	
//	/**
//	 * 开始时间
//	 */
//	@ApiModelProperty(name="startTime",notes="开始时间",required=false)
//	private String startTime;
//	
//	/**
//	 * 结束时间
//	 */
//	@ApiModelProperty(name="endTime",notes="结束时间",required=false)
//	private String endTime;
//	
	/**
	 * 消息类型
	 */
	@ApiModelProperty(name="msgType",notes="消息类型",required=false)
	private Integer msgType;
	
//	/**
//	 * 状态
//	 */
//	@ApiModelProperty(name="status",notes="状态",required=false,hidden=true)
//	private String status="success";
//	
	
	/**
	 * 当前页
	 */
	@ApiModelProperty(name="current",notes="当前页",required=false)
	private Integer current=1;
	
	/**
	 * 显示行数
	 */
	@ApiModelProperty(name="size",notes="显示行数",required=false)
	private Integer size=10;

}
