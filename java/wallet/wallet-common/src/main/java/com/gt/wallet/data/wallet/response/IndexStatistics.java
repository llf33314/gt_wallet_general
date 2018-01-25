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
@ApiModel(description="首页统计内容")
public class IndexStatistics {
	/**
	 * 资产总额
	 */
	@ApiModelProperty(name="total",notes="资产总额",required=true)
	private double total;
	

	/**
	 * 待结算
	 */
	@ApiModelProperty(name="waitBalance",notes="待结算",required=true)
	private double waitBalance;
	
	
	/**
	 * 余额
	 */
	@ApiModelProperty(name="balance",notes="余额",required=true)
	private double balance;
}
