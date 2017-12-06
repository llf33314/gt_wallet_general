package com.gt.wallet.data.api.tonglian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月6日 下午8:41:21 
* 卡bin 信息说明 
*/
@Data
@ApiModel(description=" 卡bin信息")
public class TCardBin {
	
	
	/**
	 * 卡种(1:借记卡 2：信用卡)
	 */
	@ApiModelProperty(name="cardType",notes="卡种(1:借记卡 2：信用卡)",required=true)
	private Long cardType;

	
	/**
	 * 发卡行代码
	 */
	@ApiModelProperty(name="bankCode",notes="发卡行代码",required=true)
	private String bankCode;
	
	/**
	 * 发卡行名称
	 */
	@ApiModelProperty(name="bankName",notes="发卡行名称",required=true)
	private String bankName;
	
	/**
	 * 卡名
	 */
	@ApiModelProperty(name="cardName",notes="卡名",required=true)
	private String cardName;
	
	
	/**
	 * 卡片长度
	 */
	@ApiModelProperty(name="cardLenth",notes="卡片长度",required=true)
	private Long cardLenth;
	
	
	/**
	 * 状态（1：有效；0：无效）
	 */
	@ApiModelProperty(name="cardState",notes="状态（1：有效；0：无效）",required=true)
	private Long cardState;
	
	
	/**
	 * 卡种名称
	 */
	@ApiModelProperty(name="cardTypeLabel",notes="卡种名称",required=true)
	private String cardTypeLabel;
}
