package com.gt.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 提现记录表
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_money")
@EqualsAndHashCode(callSuper=false)
@ApiModel(description=" 提现记录")
public class WalletMoney extends Model<WalletMoney> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(name="id",notes="主键",required=true)
	private Integer id;
    /**
     * 商家id
     */
	@ApiModelProperty(name="busId",notes="商家id",required=true)
	@TableField("bus_id")
	private Integer busId;
    /**
     * 多粉钱包会员id
     */
	@ApiModelProperty(name="wMemberId",notes="多粉钱包会员id",required=true)
	@TableField("w_member_id")
	private Integer wMemberId;
    /**
     * 系统订单号
     */
	@ApiModelProperty(name="sysOrderNo",notes="系统订单号",required=true)
	@TableField("sys_order_no")
	private String sysOrderNo;
    /**
     * 商品类型
     */
	@ApiModelProperty(name="goodsType",notes="商品类型",required=true,hidden=true)
	@TableField("goods_type")
	private Integer goodsType;
    /**
     * 商户系统商品编号
     */
	@ApiModelProperty(name="goodsNo",notes="商户系统商品编号",required=true,hidden=true)
	@TableField("goods_no")
	private String goodsNo;
    /**
     * 业务码
     */
	@ApiModelProperty(name="tradeCode",notes="业务码",required=true,hidden=true)
	private String tradeCode;
    /**
     * 订单金额
     */
	@ApiModelProperty(name="amount",notes="订单金额",required=true)
	private BigDecimal amount;
    /**
     * 手续费
     */
	@ApiModelProperty(name="fee",notes="手续费",required=true)
	private BigDecimal fee;
    /**
     * 交易验证方式
     */
	@TableField("validate_type")
	@ApiModelProperty(name="validateType",notes="交易验证方式",required=true,hidden=true)
	private Integer validateType;
    /**
     * 订单生成时间
     */
	@ApiModelProperty(name="ctime",notes="订单生成时间",required=true)
	private Date ctime;
    /**
     * 支付方式
     */
	@ApiModelProperty(name="payMethod",notes="支付方式",required=true,hidden=true)
	@TableField("pay_method")
	private String payMethod;
    /**
     * 银行卡号/账号
     */
	@ApiModelProperty(name="bankCardNo",notes="银行卡号/账号",required=true,hidden=true)
	private String bankCardNo;
    /**
     * 银行卡/账户属性
     */
	@ApiModelProperty(name="bankCardPro",notes="银行卡/账户属性",required=true,hidden=true)
	private Integer bankCardPro;
    /**
     * 行业代码
     */
	@ApiModelProperty(name="industryCode",notes="行业代码",required=true,hidden=true)
	@TableField("industry_code")
	private Integer industryCode;
    /**
     * 行业名称
     */
	@ApiModelProperty(name="industryName",notes="行业名称",required=true,hidden=true)
	@TableField("industry_name")
	private String industryName;
    /**
     * 访问终端类型
     */
	@ApiModelProperty(name="visitSource",notes="访问终端类型",required=true,hidden=true)
	@TableField("visit_source")
	private Integer visitSource;
    /**
     * 摘要
     */
	@ApiModelProperty(name="goodsSummary",notes="摘要",required=true,hidden=true)
	@TableField("goods_summary")
	private String goodsSummary;
    /**
     * 扩展信息
     */
	@ApiModelProperty(name="extendInfo",notes="扩展信息",required=true,hidden=true)
	private String extendInfo;
    /**
     * 支付状态 成功：success 进行中：pending 失败：fail
     */
	@ApiModelProperty(name="status",notes="支付状态 成功：success 进行中：pending 失败：fail",required=true)
	private String status;
    /**
     * 支付失败信息
     */
	@ApiModelProperty(name="payFailMessage",notes="支付失败信息",required=true,hidden=true)
	@TableField("pay_fail_message")
	private String payFailMessage;
    /**
     * 账户集编号
     */
	@ApiModelProperty(name="accountSetNo",notes="账户集编号",required=true,hidden=true)
	@TableField("account_set_no")
	private String accountSetNo;
    /**
     * 提现方式
     */
	@ApiModelProperty(name="withdrawType",notes="提现方式",required=true,hidden=true)
	@TableField("withdraw_type")
	private String withdrawType;
    /**
     * 云账号订单
     */
	@ApiModelProperty(name="externalOrderNo",notes="云账号订单",required=true)
	@TableField("external_order_no")
	private String externalOrderNo;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletMoney{" +
			"id=" + id +
			", busId=" + busId +
			", wMemberId=" + wMemberId +
			", sysOrderNo=" + sysOrderNo +
			", goodsType=" + goodsType +
			", goodsNo=" + goodsNo +
			", tradeCode=" + tradeCode +
			", amount=" + amount +
			", fee=" + fee +
			", validateType=" + validateType +
			", ctime=" + ctime +
			", payMethod=" + payMethod +
			", bankCardNo=" + bankCardNo +
			", bankCardPro=" + bankCardPro +
			", industryCode=" + industryCode +
			", industryName=" + industryName +
			", visitSource=" + visitSource +
			", goodsSummary=" + goodsSummary +
			", extendInfo=" + extendInfo +
			", status=" + status +
			", payFailMessage=" + payFailMessage +
			", accountSetNo=" + accountSetNo +
			", withdrawType=" + withdrawType +
			", externalOrderNo=" + externalOrderNo +
			"}";
	}
}
