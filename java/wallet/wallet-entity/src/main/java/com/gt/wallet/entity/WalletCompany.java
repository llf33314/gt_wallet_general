package com.gt.wallet.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业会员明细
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
@TableName("t_wallet_company")
@ApiModel(description="企业会员")
public class WalletCompany extends Model<WalletCompany> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(required=true,name="主键",notes="唯一键")
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	/**
	 * 多粉钱包会员id
	 */
	@TableField("w_member_id")
	@ApiModelProperty(name="多粉钱包会员id",notes="多粉钱包会员id")
	private Integer wMemberId;
	
	/**
	 * 会员账号
	 */
	@TableField("member_num")
	@ApiModelProperty(name="会员账号",notes="会员账号")
	private String memberNum;
    /**
     * 营业执照
     */
	@TableField("do_business_url")
	@ApiModelProperty(name="营业执照",notes="营业执照，如无业务需要不传")
	private String doBusinessUrl;
	
	/**
	 * 许可证
	 */
	@TableField("license_url")
	@ApiModelProperty(name="许可证",notes="许可证，如无业务需要不传")
	private String licenseUrl;
	
	/**
	 * 身份证正面
	 */
	@TableField("identitycard_url_1")
	@ApiModelProperty(name="身份证正面",notes="身份证正面，如无业务需要不传")
	private String identitycardUrl1;
	
	/**
	 * 身份证反面
	 */
	@TableField("identitycard_url_2")
	@ApiModelProperty(name="身份证反面",notes="身份证反面，如无业务需要不传")
	private String identitycardUrl2;
	
	/**
	 * 企业名称
	 */
	@TableField("company_name")
	@ApiModelProperty(name="企业名称",notes="企业名称")
	private String companyName;
	
	/**
	 * 地址
	 */
	@ApiModelProperty(name="地址",notes="地址")
	@TableField("company_address")
	private String companyAddress;
	
	
    /**
     * 营业执照号
     */
	@TableField("business_license")
	@ApiModelProperty(name="营业执照号",notes="营业执照号")
	private String businessLicense;
	
	/**
	 * 组织机构代码
	 */
	@TableField("organization_code")
	@ApiModelProperty(name="组织机构代码",notes="组织机构代码")
	private String organizationCode;
	
	/**
	 * 联系电话
	 */
	@ApiModelProperty(name="联系电话",notes="联系电话")
	private String telephone;
	
	/**
	 * 法人姓名
	 */
	@ApiModelProperty(name="法人姓名",notes="法人姓名")
	@TableField("legal_name")
	private String legalName;
    

	/**
	 * 证件类型
	 */
	@ApiModelProperty(name="证件类型",notes="证件类型")
	@TableField("identity_type")
	private Integer identityType;
	
	
	/**
	 * 法人证件号码
	 */
	@ApiModelProperty(name="法人证件号码",notes="法人证件号码,,如非业务需要不传")
	private String legalIds;
	
//	/**
//	 * 法人手机号码
//	 */
//	@ApiModelProperty(name="法人手机号码",notes="法人手机号码,隐藏中间账号")
//	@TableField("legal_phone")
//	private String legalPhone;
	
	/**
	 * 企业对公账户
	 */
	@ApiModelProperty(name="企业对公账户",notes="企业对公账户,显示前后4位")
	@TableField("account_no")
	private String accountNo;
	
	/**
	 * 银行名称
	 */
	@ApiModelProperty(name="银行名称",notes="银行名称")
	@TableField("parent_bank_name")
	private String parentBankName;
	
	/**
	 * 开户行地区代码
	 */
	@ApiModelProperty(name="开户行地区代码",notes="开户行地区代码")
	@TableField("bank_ctiy_no")
	private String bankCtiyNo;
	
	/**
	 * 开户行支行名
	 */
	@ApiModelProperty(name="开户行支行名",notes="开户行支行名")
	@TableField("bank_name")
	private String bankName;
   

	/**
	 * 支付行号
	 */
	@ApiModelProperty(name="支付行号",notes="支付行号")
	@TableField("union_bank")
	private String unionBank;
	
	
	@ApiModelProperty(name="地址",notes="地址")
	private String address;
    /**
     * 国家
     */
	@ApiModelProperty(name="国家",notes="国家")
	private String country;
    /**
     * 省份
     */
	@ApiModelProperty(name="省份",notes="省份")
	private String province;
    /**
     * 县市
     */
	@ApiModelProperty(name="县市",notes="县市")
	private String area;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletCompany{" +
			"id=" + id +
			", wMemberId=" + wMemberId +
			", memberNum=" + memberNum +
			", doBusinessUrl=" + doBusinessUrl +
			", licenseUrl=" + licenseUrl +
			", identitycardUrl1=" + identitycardUrl1 +
			", identitycardUrl2=" + identitycardUrl2 +
			", companyName=" + companyName +
			", companyAddress=" + companyAddress +
			", businessLicense=" + businessLicense +
			", organizationCode=" + organizationCode +
			", telephone=" + telephone +
			", legalName=" + legalName +
			", identityType=" + identityType +
			", legalIds=" + legalIds +
			", accountNo=" + accountNo +
			", parentBankName=" + parentBankName +
			", bankCtiyNo=" + bankCtiyNo +
			", bankName=" + bankName +
			", unionBank=" + unionBank +
			"}";
	}
}
