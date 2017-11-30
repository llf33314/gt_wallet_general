package com.gt.wallet.data.wallet.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 下午6:04:58 
* 新增银行卡请求参数类说明 
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="新增银行卡")
public class WalletBankAdd {
	
	
	/**
	 * 会员id
	 */
	@ApiModelProperty(name="会员id",notes="会员id",required=true)
	private Integer memberId;
	
	/**
	 * 银行卡号
	 */
	@ApiModelProperty(name="银行卡号",notes="银行卡号",required=true)
	private String cardNo;
	
	/**
	 * 银行预留手机
	 */
	@ApiModelProperty(name="银行预留手机",notes="银行预留手机",required=true)
	private String phone;
	
	
	/**
	 * 银行卡开户人姓名
	 */
	@ApiModelProperty(name="银行卡开户人姓名",notes="必须与注册人姓名一致",required=true)
	private String bankName; 
	
	
	/**
	 * 支付行号(非直连银行卡)
	 */
	@ApiModelProperty(name="支付行号",notes="支付行号",required=false)
	private String unionBank; 
	
	/**
	 * 是否为安全卡 0：是 1：否
	 */
	@ApiModelProperty(name="是否为安全卡",notes="是否为安全卡 0：是 1：否",required=false)
	private Integer isSafeCard;
}
