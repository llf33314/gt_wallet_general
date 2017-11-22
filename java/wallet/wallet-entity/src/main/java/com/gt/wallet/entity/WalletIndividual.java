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
 * 个人会员
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_individual")
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="个人会员")
public class WalletIndividual extends Model<WalletIndividual> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(required=true,name="主键",notes="唯一键")
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
	@ApiModelProperty(name="会员账号",notes="会员账号")
	@TableField("member_num")
	private String memberNum;
    /**
     * 姓名
     */
	@ApiModelProperty(name="姓名",notes="姓名")
	private String name;
    /**
     * 地址
     */
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
    /**
     * 是否进行实名认证1:是 0:否
     */
	@TableField("identity_checked")
	@ApiModelProperty(name="是否进行实名认证1:是 0:否",notes="是否进行实名认证1:是 0:否")
	private Integer identityChecked;
    /**
     * 身份证号码
     */
	@ApiModelProperty(name="身份证号码",notes="身份证号码,显示前后4位")
	private String identityCardNo;
    /**
     * 支付失败次数
     */
	@TableField("pay_fail_amount")
	@ApiModelProperty(name="支付失败次数",notes="支付失败次数")
	private Integer payFailAmount;
    /**
     * 实名认证时间
     */
	@ApiModelProperty(name="实名认证时间",notes="实名认证时间")
	private String realNameTime;
    /**
     * 备注
     */
	@ApiModelProperty(name="备注",notes="备注")
	private String remark;
    /**
     * 访问终端1:Mobile 2:PC
     */
	@ApiModelProperty(name="访问终端1:Mobile 2:PC",notes="访问终端1:Mobile 2:PC")
	private Integer source;
	
	
	
	
	/**
	 * 身份证正面
	 */
	@TableField("identitycard_url_1")
	@ApiModelProperty(name="身份证正面",notes="身份证正面,如非业务需要不传")
	private String identitycardUrl1;
	
	/**
	 * 身份证反面
	 */
	@ApiModelProperty(name="身份证反面",notes="身份证反面,如非业务需要不传")
	@TableField("identitycard_url_2")
	private String identitycardUrl2;
	
	
	@ApiModelProperty(name="银行卡信息",notes="银行卡信息,如非业务需要不传")
	@TableField(exist=false)
	private WalletBank walletBank;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletIndividual{" +
			"id=" + id +
			", wMemberId=" + wMemberId +
			", memberNum=" + memberNum +
			", name=" + name +
			", address=" + address +
			", country=" + country +
			", province=" + province +
			", area=" + area +
			", identityChecked=" + identityChecked +
			", identityCardNo=" + identityCardNo +
			", payFailAmount=" + payFailAmount +
			", realNameTime=" + realNameTime +
			", remark=" + remark +
			", source=" + source +
			", identitycardUrl1=" + identitycardUrl1 +
			", identitycardUrl2=" + identitycardUrl2 +
			"}";
	}
	
	public WalletIndividual(){
		super();
	}
	
	
	public WalletIndividual(Integer wMemberId){
		this.wMemberId=wMemberId;
	}
}
