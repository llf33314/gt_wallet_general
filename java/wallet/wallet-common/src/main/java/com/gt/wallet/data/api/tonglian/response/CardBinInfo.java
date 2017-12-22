package com.gt.wallet.data.api.tonglian.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月21日 下午8:40:00 
* 类说明 
*/
@Data
@ApiModel(description="银行卡信息")
public class CardBinInfo {

	/**
	 * 简写
	 */
	@ApiModelProperty(name="abbreviation",notes="简写",required=true)
	private String abbreviation;
	
	
	/**
	 * 	银行电话
	 */
	@ApiModelProperty(name="servicephone",notes="银行电话",required=true)
	private String servicephone;
	
	/**
	 * 	银行卡类型
	 */
	@ApiModelProperty(name="cardtype",notes="银行卡类型",required=true)
	private String cardtype;
	
	/**
	 * bin
	 */
	@ApiModelProperty(name="cardprefixnum",notes="bin",required=true,hidden=true)
	private String  cardprefixnum;
	
	/**
	 * 银行卡号长度
	 */
	@ApiModelProperty(name="cardlength",notes="银行卡号长度",required=true,hidden=true)
	private String cardlength;
	
	
	/**
	 * 银行名称(英文)
	 */
	@ApiModelProperty(name="enbankname",notes="银行名称(英文)",required=true)
	private String enbankname;
	
	
	/**
	 * 银行卡名称
	 */
	@ApiModelProperty(name="cardname",notes="银行卡名称",required=true)
	private String cardname;
	
	/**
	 * 	发卡银行
	 */
	@ApiModelProperty(name="bankname",notes="发卡银行",required=true)
	private String bankname;
	
	/**
	 * 	银行网址
	 */
	@ApiModelProperty(name="bankurl",notes="银行网址",required=true)
	private String bankurl;
	
	/**
	 * 省份
	 */
	@ApiModelProperty(name="province",notes="省份",required=true)
	private String province;
	
	/**
	 * 城市
	 */
	@ApiModelProperty(name="city",notes="城市",required=true)
	private String city;
	/**
	 * 	银行LOGO
	 */
	@ApiModelProperty(name="bankimage",notes="银行LOGO",required=true)
	private String bankimage;
	
	/**
	 * 是否是信用卡,1为借记卡,2为信用卡
	 */
	@ApiModelProperty(name="iscreditcard",notes="是否是信用卡,1为借记卡,2为信用卡",required=true)
	private Integer iscreditcard;
	
	/**
	 * 是否符合编码规范
	 */
	@ApiModelProperty(name="isLuhn",notes="是否符合编码规范",required=true,hidden=true)
	private Boolean isLuhn;
	
	/**
	 * 起始数
	 */
	@ApiModelProperty(name="banknum",notes="起始数",required=true,hidden=true)
	private String banknum;
	
}
