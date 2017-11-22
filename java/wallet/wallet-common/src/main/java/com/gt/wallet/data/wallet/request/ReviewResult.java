package com.gt.wallet.data.wallet.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月20日 下午3:21:21 
* 审核回调结果类说明 
*/
@Data
@ApiModel(description="审核回调结果")
public class ReviewResult {
	
	
	/**
	 * 申请记录id
	 */
	@ApiModelProperty(name="id",notes="申请记录id",required=true)
	private Integer id;
	
	/**
	 * 状态 0：创建待审核 1:审核通过 -1:审核不通过
	 */
	@ApiModelProperty(name="status",notes="状态 0：创建待审核 1:审核通过 -1:审核不通过",required=true)
	private Integer status;
	
	/**
	 * 审核描述
	 */
	@ApiModelProperty(name="审核描述",notes="审核描述",required=true)
	private String quotaDesc;

}
