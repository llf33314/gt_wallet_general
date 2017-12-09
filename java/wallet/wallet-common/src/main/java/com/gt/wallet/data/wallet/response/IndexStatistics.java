package com.gt.wallet.data.wallet.response;

import io.swagger.annotations.ApiModel;
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
	private Double total;
	

	/**
	 * 待结算
	 */
	private Double waitBalance;
	
	
	/**
	 * 余额
	 */
	private Double balance;
}
