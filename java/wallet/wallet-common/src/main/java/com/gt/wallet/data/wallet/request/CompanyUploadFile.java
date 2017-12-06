package com.gt.wallet.data.wallet.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 下午6:04:58 
* 新增企业会员请求参数类说明 
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="企业会员新增")
public class CompanyUploadFile {
	
	
	/**
	 * 会员id
	 */
	@ApiModelProperty(name="memberId",notes="会员id",required=true)
	private Integer memberId;
	
	
	
	/**
	 * 营业执照url
	 */
	@ApiModelProperty(name="营业执照url",notes="营业执照url",required=true)
	private String doBusinessUrl;
	
	/**
	 * 身份证正面
	 */
	@ApiModelProperty(name="身份证正面",notes="身份证正面",required=true)
	private String identitycardUrl1;
	
	/**
	 * 身份证反面
	 */
	@ApiModelProperty(name="身份证反面",notes="身份证反面",required=true)
	private String identitycardUrl2;
	
	/**
	 * 许可证url
	 */
	@ApiModelProperty(name="许可证url",notes="许可证url",required=true)
	private String licenseUrl;

	
	public CompanyUploadFile() {
		super();
	}


	public CompanyUploadFile(Integer memberId, String doBusinessUrl, String identitycardUrl1, String identitycardUrl2,
			String licenseUrl) {
		super();
		this.memberId = memberId;
		this.doBusinessUrl = doBusinessUrl;
		this.identitycardUrl1 = identitycardUrl1;
		this.identitycardUrl2 = identitycardUrl2;
		this.licenseUrl = licenseUrl;
	}
}
