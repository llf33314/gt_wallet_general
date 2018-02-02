package com.gt.wallet.entity;

import java.io.Serializable;
import java.util.Date;

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
 * 多粉钱包会员
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_member")
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="多粉钱包会员")
public class WalletMember extends Model<WalletMember> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(required=true,name="主键",notes="唯一键")
	private Integer id;
    /**
     * (商家id或会员id)
     */
	@ApiModelProperty(name="商家id或会员id",notes="memberClass为1时表示商家id")
	@TableField("member_id")
	private Integer memberId;
    /**
     * 会员类型(3:个人会员 2:企业会员)
     */
	@ApiModelProperty(name="会员类型",notes="(会员类型(3:个人会员 2:企业会员))")
	@TableField("member_type")
	private Integer memberType;
    /**
     * 会员账号
     */
	@TableField("member_num")
	@ApiModelProperty(name="会员账号",notes="会员账号(多粉钱包)")
	private String memberNum;
    /**
     * 会员账号(第三方平台账号)
     */
	@ApiModelProperty(name="会员账号(第三方平台账号)",notes="会员账号(第三方平台账号)，如无业务需要不传")
	@TableField("external_num")
	private String externalNum;
    /**
     * 支付密码
     */
	@TableField("pay_pass")
	@ApiModelProperty(name="支付密码",notes="支付密码，如无业务需要不传")
	private String payPass;
    /**
     * 手机号码
     */
	@ApiModelProperty(name="手机号码",notes="手机号码,隐藏中间四位")
	private String phone;
    /**
     * 是否已绑定手机(0:未绑定,1:已绑定)
     */
	@TableField("is_binding_phone")
	@ApiModelProperty(name="是否已绑定手机",notes="(0:未绑定,1:已绑定)")
	private Integer isBindingPhone;
    /**
     * 创建时间
     */
	@ApiModelProperty(name="创建时间",notes="创建时间，如无业务需要不传")
	private Date ctime;
    /**
     * 会员状态(-2:删除,-1:锁定用户,0:创建,1:审核中,3:正常使用)
     */
	@ApiModelProperty(name="会员状态",notes="(-2:删除,-1:锁定用户,0:创建,1:审核中,3:正常使用)")
	private Integer status;
    /**
     * 会员信息
     */
	@TableField("memberinfo_json")
	@ApiModelProperty(name="会员信息",notes="会员信息，如无业务需要不传")
	private String memberinfoJson;
    /**
     * 内部会员类型(1：商家id，2：会员id)
     */
	@ApiModelProperty(name="内部会员类型",notes="(1：商家id，2：会员id)")
	@TableField("member_class")
	private Integer memberClass;

	
	/**
	 * 个人会员相关信息(会员类型=1))
	 */
	@TableField(exist = false)
	@ApiModelProperty(name="个人会员相关信息",notes="个人会员相关信息,(会员类型=1时有))，如无业务需要不传")
	private WalletIndividual walletIndividual;
	
	
	/**
	 * 企业会员相关信息(会员类型=2)
	 */
	@ApiModelProperty(name="企业会员相关信息",notes="企业会员相关信息,(会员类型=2时有值))，如无业务需要不传")
	@TableField(exist = false)
	private WalletCompany walletCompany;
	
	
	
	@TableField("set_pay_pwd")
	@ApiModelProperty(name="是否已设置支付密码1:是 0:否",notes="是否已设置支付密码1:是 0:否")
	private Integer setPayPwd;
	
	
	@TableField("registerIp")
	@ApiModelProperty(name="创建ip",notes="创建ip，如无业务需要不传")
	private String registerIp;
	
	/**
	 * 手续费百分比
	 */
	@TableField("fee_percent")
	private Double feePercent;

	
	/**
	 * 提现额度
	 */
	@ApiModelProperty(name="withdrawQuota",notes="提现额度")
	@TableField("withdraw_quota")
	private Double withdrawQuota;
	
	/**
	 * 返现百分比
	 */
	@ApiModelProperty(name="withdrawQuota",notes="返现百分比")
	@TableField("cashback_percent")
	private Double cashbackPercent;
	
	
	/**
	 * 失败原因
	 */
	@ApiModelProperty(name="failReason",notes="失败原因")
	@TableField("fail_reason")
	private String failReason;
	
	
	/**
	 * 余额
	 */
	@ApiModelProperty(name="wBalance",notes="余额")
	@TableField("w_balance")
	private Double wBalance;
	
	
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletMember{" +
			"id=" + id +
			", memberId=" + memberId +
			", memberType=" + memberType +
			", memberNum=" + memberNum +
			", externalNum=" + externalNum +
			", payPass=" + payPass +
			", phone=" + phone +
			", isBindingPhone=" + isBindingPhone +
			", ctime=" + ctime +
			", status=" + status +
			", memberinfoJson=" + memberinfoJson +
			", memberClass=" + memberClass +
			", setPayPwd=" + setPayPwd +
			", registerIp=" + registerIp +
			"}";
	}
}
