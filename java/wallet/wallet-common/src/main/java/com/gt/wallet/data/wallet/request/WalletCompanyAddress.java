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
@ApiModel(description="企业会员修改地址")
public class WalletCompanyAddress {
	
	
	/**
	 * 会员id
	 */
	@ApiModelProperty(name="memberId",notes="会员id",required=true)
	private Integer memberId;
	
	
	/**
	 * 企业地址
	 */
	@ApiModelProperty(name="companyAddress",notes="企业地址",required=true)
	private String companyAddress;
	

	
	/**
     * 省份
     */
	@ApiModelProperty(name="省份",notes="省份code")
	private String province;
	
	
    /**
     * 县市
     */
	@ApiModelProperty(name="县市",notes="县市code")
	private String area;
	
	
	
	public WalletCompanyAddress() {
		super();
	}



	public WalletCompanyAddress(Integer memberId, String companyAddress, String province, String area) {
		super();
		this.memberId = memberId;
		this.companyAddress = companyAddress;
		this.province = province;
		this.area = area;
	}
}
