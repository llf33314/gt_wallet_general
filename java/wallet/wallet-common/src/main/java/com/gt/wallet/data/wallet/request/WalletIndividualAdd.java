package com.gt.wallet.data.wallet.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 下午6:04:58 
* 新增个人会员请求参数类说明 
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="个人会员新增")
public class WalletIndividualAdd {
	
	
	/**
	 * 会员id
	 */
	@ApiModelProperty(name="会员id",notes="会员id",required=true)
	private Integer memberId;
	
	/**
	 * 姓名
	 */
	@ApiModelProperty(name="注册人姓名",notes="注册人姓名",required=true)
	private String name;
	
	/**
	 *身份证
	 */
	@ApiModelProperty(name="身份证号码",notes="身份证号码",required=true)
	private String identityNo;
	
	
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
	 * 支付行号
	 */
	@ApiModelProperty(name="支付行号",notes="支付行号")
	private String unionBank; 
//	 
	@ApiModelProperty(name="身份证正面",notes="身份证正面")
	private String identitycardUrl1File;
	
	@ApiModelProperty(name="身份证正面",notes="身份证正面")
	private String identitycardUrl2File;
	
	/**
	 * 短信验证码
	 */
//	@ApiModelProperty(name="短信验证码",notes="短信验证码",required=true)
//	private String code;
}
