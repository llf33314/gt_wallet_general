package com.gt.wallet.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
@TableName("t_wallet_bank")
public class WalletBank extends Model<WalletBank> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	
	/**
	 * 多粉钱包会员id
	 */
	@ApiModelProperty(name="多粉钱包会员id",notes="多粉钱包会员id,如非业务需要不传")
	@TableField("w_member_id")
	private Integer wMemberId;
	
	/**
	 * 会员账号
	 */
	@ApiModelProperty(name="会员账号",notes="多粉钱包会员id,如非业务需要不传")
	@TableField("member_num")
	private String memberNum;
	
	/**
	 * 银行卡号
	 */
	@ApiModelProperty(name="银行卡号",notes="银行卡号,如非业务需要不传")
	private String cardNo;
	
	/**
	 * 银行预留手机
	 */
	@ApiModelProperty(name="银行预留手机",notes="银行预留手机,如非业务需要不传")
	private String phone;
	
	/**
	 * 绑卡方式
	 */
	@ApiModelProperty(name="绑卡方式",notes="绑卡方式,如非业务需要不传")
	@TableField("card_check")
	private Integer cardCheck;
   
	/**
	 * 证件类型
	 */
	@ApiModelProperty(name="证件类型",notes="证件类型,如非业务需要不传")
	private Integer identityType;
   

	/**
	 * 证件号
	 */
	@ApiModelProperty(name="身份证",notes="身份证,如非业务需要不传")
	private String identityNo;
	
	/**
	 * 信用卡必填，格式为年月，
            有效期，格式为年月如2103
	 */
	@ApiModelProperty(name="信用卡必填",notes="可忽略")
	private String validate;
    /**
     * CVV2
     */
	@ApiModelProperty(name="CVV2",notes="可忽略")
	private String cvv2;
	/**
	 * 是否为安全卡
	 */
	@TableField("is_safe_card")
	@ApiModelProperty(name="是否为安全卡",notes="0:安全卡1:非安全卡")
	private Integer isSafeCard;
   

	/**
	 * 支付行号
	 */
	@ApiModelProperty(name="支付行号",notes="支付行号")
	@TableField("union_bank")
	private String unionBank;
	
	/**
	 * 流水号
	 */
	@ApiModelProperty(name="流水号",notes="流水号")
	private String tranceNum;
	
	/**
	 * 申请时间
	 */
	@ApiModelProperty(name="申请时间",notes="申请时间")
	private String transDate;
	
	/**
	 * 开户银行名称
	 */
	@ApiModelProperty(name="开户银行名称",notes="开户银行名称")
	@TableField("bank_name")
	private String bankName;
	
	/**
	 * 银行代码
	 */
	@ApiModelProperty(name="银行代码",notes="银行代码")
	@TableField("bank_code")
	private String bankCode;
	
	/**
	 * 银行卡类型1 储蓄卡2 信用卡
	 */
	@ApiModelProperty(name="银行卡类型1 储蓄卡2 信用卡",notes="银行卡类型1 储蓄卡2 信用卡")
	@TableField("card_type")
	private Integer cardType;
    /**
     * 1：个人银行卡 2：对公账号
     */
	@ApiModelProperty(name="1：个人银行卡 2：对公账号",notes="1：个人银行卡 2：对公账号")
	@TableField("card_class")
	private Integer cardClass;
	
	/**
	 * 卡片长度
	 */
	@ApiModelProperty(name="卡片长度",notes="卡片长度")
	@TableField("card_lenth")
	private Integer cardLenth;
	
	
	/**
	 * 姓名
	 */
	@ApiModelProperty(name="姓名",notes="姓名")
	@TableField("name")
	private String name;
	


	/**
	 * 状态（1：有效；0：无效）
	 */
	@ApiModelProperty(name="卡的状态",notes="状态（1：有效；0：无效）")
	@TableField("card_state")
	private Integer cardState;
	
	/**
	 * 状态0:绑定前，1:绑定 -1:解绑
	 */
	@ApiModelProperty(name="状态",notes="状态0:绑定前，1:绑定 -1:解绑")
	@TableField("status")
	private Integer status;

	/**
	 * 银行卡logo url
	 */
	@ApiModelProperty(name="logo url",notes="银行卡logo url")
	@TableField("icon_url")
	private String iconUrl;
	

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletBank{" +
			"id=" + id +
			", wMemberId=" + wMemberId +
			", memberNum=" + memberNum +
			", cardNo=" + cardNo +
			", phone=" + phone +
			", cardCheck=" + cardCheck +
			", identityType=" + identityType +
			", identityNo=" + identityNo +
			", validate=" + validate +
			", cvv2=" + cvv2 +
			", isSafeCard=" + isSafeCard +
			", unionBank=" + unionBank +
			", tranceNum=" + tranceNum +
			", transDate=" + transDate +
			", bankName=" + bankName +
			", bankCode=" + bankCode +
			", cardType=" + cardType +
			", cardClass=" + cardClass +
			", cardLenth=" + cardLenth +
			", cardState=" + cardState +
			"}";
	}
	
	public WalletBank(){
		super();
	}
	
	
	public WalletBank(Integer wMemberId){
		this.wMemberId=wMemberId;
	}
}
