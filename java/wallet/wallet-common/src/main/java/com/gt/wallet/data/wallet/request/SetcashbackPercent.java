package com.gt.wallet.data.wallet.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月22日 下午4:50:42 
* 类说明 
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="返现百分比设置")
public class SetcashbackPercent {

	/**
	 * 多粉钱包会员id
	 */
	@ApiModelProperty(name="wmemberId",notes="多粉钱包会员id",required=true)
	private Integer wmemberId;
	
	
	/**
	 * 返现百分比
	 */
	@ApiModelProperty(name="cashbackPercent",notes="返现百分比",required=true)
	private Double cashbackPercent;
}
