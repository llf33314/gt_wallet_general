package com.gt.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
 * 钱包支付记录
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@TableName("t_wallet_pay_order")
public class WalletPayOrder extends Model<WalletPayOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(name="id",notes="主键",required=false)
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商家id
     */
	@ApiModelProperty(name="busId",notes=" 商家id",required=false,hidden=true)
	@TableField("bus_id")
	private Integer busId;
    /**
     * 多粉钱包会员id
     */
	@ApiModelProperty(name="wMemberId",notes="多粉钱包会员id",required=false,hidden=true)
	@TableField("w_member_id")
	private Integer wMemberId;
    /**
     * member表会员id
     */
	@ApiModelProperty(name="memberId",notes="member表会员id",required=false,hidden=true)
	@TableField("member_id")
	private Integer memberId;

    /**
     * 系统订单号
     */
	@ApiModelProperty(name="sysOrderNo",notes="系统订单号",required=false)
	@TableField("sys_order_no")
	private String sysOrderNo;

    /**
     * 订单金额
     */
	@ApiModelProperty(name="amount",notes="订单金额",required=false)
	private BigDecimal amount;
    /**
     * 手续费
     */
	@ApiModelProperty(name="fee",notes="手续费",required=false)
	private BigDecimal fee;

    /**
     * 订单生成时间
     */
	@ApiModelProperty(name="ctime",notes="订单生成时间",required=false)
	private Date ctime;

    /**
     * 行业代码
     */
	@ApiModelProperty(name="industryCode",notes="行业代码",required=false,hidden=true)
	@TableField("industry_code")
	private Integer industryCode;
    /**
     * 行业名称
     */
	@ApiModelProperty(name="industryName",notes="行业名称",required=false,hidden=true)
	@TableField("industry_name")
	private String industryName;
    /**
     * 访问终端类型
     */
	@ApiModelProperty(name="visitSource",notes="访问终端类型",required=false,hidden=true)
	@TableField("visit_source")
	private Integer visitSource;
    /**
     * 摘要
     */
	@ApiModelProperty(name="goodsSummary",notes="摘要",required=false)
	@TableField("goods_summary")
	private String goodsSummary;

    /**
     * 支付状态 成功：success 进行中：pending 失败：fail
     */
	@ApiModelProperty(name="status",notes="支付状态 成功：success 进行中：pending 失败：fail",required=false,hidden=true)
	private String status;
    /**
     * 支付失败信息
     */
	@TableField("pay_fail_message")
	@ApiModelProperty(name="payFailMessage",notes="支付失败信息",required=false,hidden=true)
	private String payFailMessage;
    /**
     * 多粉钱包订单号
     */
	@TableField("external_no")
	@ApiModelProperty(name="externalNo",notes="多粉钱包订单号",required=false)
	private String externalNo;
    /**
     * 交易编号
     */
	@ApiModelProperty(name="tradeNo",notes="交易编号",required=false,hidden=true)
	private String tradeNo;

    /**
     * POS 支付的付款码
     */
	@TableField("pay_code")
	@ApiModelProperty(name="payCode",notes="POS 支付的付款码",required=false,hidden=true)
	private String payCode;

    /**
     * 退款订单号
     */
	@ApiModelProperty(name="sysRefundNo",notes="退款订单号",required=false,hidden=true)
	@TableField("sys_refund_no")
	private String sysRefundNo;
    /**
     * 退款总金额
     */
	@ApiModelProperty(name="refundAmount",notes="退款总金额",required=false,hidden=true)
	@TableField("refund_amount")
	private BigDecimal refundAmount;
    /**
     * 手续费退款金额
     */
	@ApiModelProperty(name="refundFeeamount",notes="手续费退款金额",required=false,hidden=true)
	@TableField("refund_feeamount")
	private BigDecimal refundFeeamount;
    /**
     * 钱包退款单号
     */
	@ApiModelProperty(name="refundExternalNo",notes="钱包退款单号",required=false,hidden=true)
	@TableField("refund_external_no")
	private String refundExternalNo;
	
	/**
	 * 支付宝或微信账号(openid)
	 */
	@ApiModelProperty(name="acct",notes="支付宝或微信账号(openid)",required=false,hidden=true)
	private String acct;
	
	/**
	 * 提交订单号
	 */
	@ApiModelProperty(name="submitNo",notes="提交订单号",required=false,hidden=true)
	@TableField("submit_no")
	private String submitNo;

	/**
	 * 支付方式
	 */
	@ApiModelProperty(name="payType",notes="支付方式(支付方式 1：微信 2:支付宝 3:H5,6:微信刷卡 7:支付宝刷卡)",required=false)
	@TableField("pay_type")
	private Integer payType;
	
	
	/**
	 * 是否可提现(1:可取 2:不可取)
	 */
	@ApiModelProperty(name="startTime",notes="开始时间",required=false,hidden=true)
	@TableField("take_state")
	private Integer takeState;
	
	
	/**
	 * 支付模块
	 */
	@ApiModelProperty(name="model",notes="支付模块",required=false,hidden=true)
	@TableField("model")
	private Integer model;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletPayOrder{" +
			"id=" + id +
			", busId=" + busId +
			", wMemberId=" + wMemberId +
			", memberId=" + memberId +
			", sysOrderNo=" + sysOrderNo +
			", amount=" + amount +
			", fee=" + fee +
			", ctime=" + ctime +
			", industryCode=" + industryCode +
			", industryName=" + industryName +
			", visitSource=" + visitSource +
			", goodsSummary=" + goodsSummary +
			", status=" + status +
			", payFailMessage=" + payFailMessage +
			", externalNo=" + externalNo +
			", tradeNo=" + tradeNo +
			", payCode=" + payCode +
			", sysRefundNo=" + sysRefundNo +
			", refundAmount=" + refundAmount +
			", refundFeeamount=" + refundFeeamount +
			", refundExternalNo=" + refundExternalNo +
			"}";
	}

	public WalletPayOrder(Integer busId, Integer wMemberId, Integer memberId, String sysOrderNo, BigDecimal amount,
			BigDecimal fee, Date ctime, Integer industryCode, String industryName, Integer visitSource,
			String goodsSummary, String status, String payFailMessage, String externalNo, String tradeNo,
			String payCode, String sysRefundNo, BigDecimal refundAmount, BigDecimal refundFeeamount,
			String refundExternalNo, String acct, String submitNo, Integer payType, Integer takeState, Integer model) {
		super();
		this.busId = busId;
		this.wMemberId = wMemberId;
		this.memberId = memberId;
		this.sysOrderNo = sysOrderNo;
		this.amount = amount;
		this.fee = fee;
		this.ctime = ctime;
		this.industryCode = industryCode;
		this.industryName = industryName;
		this.visitSource = visitSource;
		this.goodsSummary = goodsSummary;
		this.status = status;
		this.payFailMessage = payFailMessage;
		this.externalNo = externalNo;
		this.tradeNo = tradeNo;
		this.payCode = payCode;
		this.sysRefundNo = sysRefundNo;
		this.refundAmount = refundAmount;
		this.refundFeeamount = refundFeeamount;
		this.refundExternalNo = refundExternalNo;
		this.acct = acct;
		this.submitNo = submitNo;
		this.payType = payType;
		this.takeState = takeState;
		this.model = model;
	}
	
	public WalletPayOrder() {
		super();
	}
}
