package com.gt.wallet.data.wallet.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月8日 上午9:44:10 
* 首页统计内容说明 
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="统计各会员待结算余额")
public class GroupStatistics {
	/**
	 * 待结算
	 */
	@ApiModelProperty(name="waitBalance",notes="待结算",required=true)
	private double waitBalance;
	
	/**
	 * 会员id
	 */
	@ApiModelProperty(name="wMemberId",notes="会员id",required=true)
	public Integer wMemberId;
}
