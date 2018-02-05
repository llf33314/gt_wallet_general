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
public class WalletCompanyAdd {
	
	
	/**
	 * 会员id
	 */
	@ApiModelProperty(name="memberId",notes="会员id",required=true)
	private Integer memberId;
	
	/**
	 * 公司名称
	 */
	@ApiModelProperty(name="companyName",notes="公司名称",required=true)
	private String companyName;
	
	
	/**
	 * 企业地址
	 */
	@ApiModelProperty(name="companyAddress",notes="企业地址",required=true)
	private String companyAddress;
	
	
	
	/**
	 * 营业执照号
	 */
	@ApiModelProperty(name="businessLicense",notes="营业执照号",required=true)
	private String businessLicense;
	
	//private 
	
	/**
	 * 组织机构代码 
	 */
//	@ApiModelProperty(name="组织机构代码 ",notes="组织机构代码 ",required=true)
//	private String organizationCode;
	
	/**
	 *联系电话
	 */
	@ApiModelProperty(name="联系电话",notes="联系电话",required=true)
	private String telephone;
	
	
	/**
	 * 法人姓名
	 */
	@ApiModelProperty(name="法人姓名",notes="法人姓名",required=true)
	private String legalName;
	
//	/**
//	 * 法人证件类型
//	 */
//	@ApiModelProperty(name="法人证件类型",notes="法人证件类型",required=true)
//	private String identityType;
	
	
	/**
	 * 法人证件号码
	 */
	@ApiModelProperty(name="法人证件号码",notes="法人证件号码",required=true)
	private String legalIds; 
	
	
	/**
	 * 法人手机号码
	 */
	@ApiModelProperty(name="法人手机号码",notes="法人手机号码")
	private String legalPhone; 
	
	
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
	
	
	
	/**
	 * 企业对公账户
	 */
	@ApiModelProperty(name="企业对公账户",notes="企业对公账户",required=true)
	private String accountNo;
	
	
	/**
	 * 开户银行名称
	 */
	@ApiModelProperty(name="开户银行名称",notes="开户银行名称",required=true)
	private String parentBankName;
	
	
	
	/**
	 * 开户行地区代码
	 */
	@ApiModelProperty(name="开户行地区代码",notes="开户行地区代码",required=true)
	private String bankCityNo;
	
	
	
	/**
	 * 开户行支行名称
	 */
	@ApiModelProperty(name="开户行支行名称",notes="开户行支行名称",required=true)
	private String bankName;
	
	
	
	/**
	 * 支付行号
	 */
	@ApiModelProperty(name="支付行号",notes="支付行号",required=true)
	private String unionBank;
	
	
	
//	/**
//	 * 营业执照url
//	 */
//	@ApiModelProperty(name="营业执照url",notes="营业执照url",required=true)
//	private String doBusinessUrl;
//	
//	/**
//	 * 身份证正面
//	 */
//	@ApiModelProperty(name="身份证正面",notes="身份证正面",required=true)
//	private String identitycardUrl1;
//	
//	/**
//	 * 身份证反面
//	 */
//	@ApiModelProperty(name="身份证反面",notes="身份证反面",required=true)
//	private String identitycardUrl2;
//	
//	/**
//	 * 许可证url
//	 */
//	@ApiModelProperty(name="许可证url",notes="许可证url",required=true)
//	private String licenseUrl;


	public WalletCompanyAdd(Integer memberId, String companyName, String companyAddress, String businessLicense, String telephone, String legalName, String legalIds,
			String legalPhone, String accountNo, String parentBankName,
			String bankName, String unionBank) {
		super();
		this.memberId = memberId;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.businessLicense = businessLicense;
		this.telephone = telephone;
		this.legalName = legalName;
		this.legalIds = legalIds;
		this.legalPhone = legalPhone;
		this.accountNo = accountNo;
		this.parentBankName = parentBankName;
		this.bankName = bankName;
		this.unionBank = unionBank;
	}
	
	public WalletCompanyAdd() {
		super();
	}
}
