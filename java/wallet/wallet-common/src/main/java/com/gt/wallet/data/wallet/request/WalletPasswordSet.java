package com.gt.wallet.data.wallet.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月18日 下午5:49:00 
* 修改密码 类说明 
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="修改密码")
public class WalletPasswordSet {
	
	/**
	 * 会员id
	 */
	@ApiModelProperty(name="wmemberId",notes="会员id",required=true)
	private Integer wmemberId;
	
	
	/**
	 * 密码
	 */
	@ApiModelProperty(name="pwd",notes="密码",required=true)
	private String pwd;
	
	/**
	 * 确认密码
	 */
	@ApiModelProperty(name="confirm",notes="确认密码",required=true)
	private String confirm;
	
	

	
	/**
	 * 验证码
	 */
	@ApiModelProperty(name="code",notes="验证码",required=true)
	private String code;
}
