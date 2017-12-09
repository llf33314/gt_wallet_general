package com.gt.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

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
@TableName("t_wallet_pay_order")
@EqualsAndHashCode(callSuper=false)
public class WalletPayOrder extends Model<WalletPayOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商家id
     */
	@TableField("bus_id")
	private Integer busId;
    /**
     * 多粉钱包会员id
     */
	@TableField("w_member_id")
	private Integer wMemberId;
    /**
     * member表会员id
     */
	@TableField("member_id")
	private Integer memberId;

    /**
     * 系统订单号
     */
	@TableField("sys_order_no")
	private String sysOrderNo;

    /**
     * 订单金额
     */
	private BigDecimal amount;
    /**
     * 手续费
     */
	private BigDecimal fee;

    /**
     * 订单生成时间
     */
	private Date ctime;

    /**
     * 行业代码
     */
	@TableField("industry_code")
	private Integer industryCode;
    /**
     * 行业名称
     */
	@TableField("industry_name")
	private String industryName;
    /**
     * 访问终端类型
     */
	@TableField("visit_source")
	private Integer visitSource;
    /**
     * 摘要
     */
	@TableField("goods_summary")
	private String goodsSummary;

    /**
     * 支付状态 成功：success 进行中：pending 失败：fail
     */
	private String status;
    /**
     * 支付失败信息
     */
	@TableField("pay_fail_message")
	private String payFailMessage;
    /**
     * 多粉钱包订单号
     */
	@TableField("external_no")
	private String externalNo;
    /**
     * 交易编号
     */
	private String tradeNo;

    /**
     * POS 支付的付款码
     */
	@TableField("pay_code")
	private String payCode;
    /**
     * 微信支付信息(微信app 支付必传)
     */
	@TableField("we_chat_aapinfo")
	private String weChatAapinfo;

    /**
     * 退款订单号
     */
	@TableField("sys_refund_no")
	private String sysRefundNo;
    /**
     * 退款总金额
     */
	@TableField("refund_amount")
	private BigDecimal refundAmount;
    /**
     * 手续费退款金额
     */
	@TableField("refund_feeamount")
	private BigDecimal refundFeeamount;
    /**
     * 钱包退款单号
     */
	@TableField("refund_external_no")
	private String refundExternalNo;
	
	/**
	 * 支付宝或微信账号(openid)
	 */
	private String acct;
	
	/**
	 * 提交订单号
	 */
	@TableField("submit_no")
	private String submitNo;

	/**
	 * 支付方式
	 */
	@TableField("pay_type")
	private Integer payType;
	
	
	/**
	 * 是否可提现(1:可取 2:不可取)
	 */
	@TableField("take_state")
	private Integer takeState;

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
			", weChatAapinfo=" + weChatAapinfo +
			", sysRefundNo=" + sysRefundNo +
			", refundAmount=" + refundAmount +
			", refundFeeamount=" + refundFeeamount +
			", refundExternalNo=" + refundExternalNo +
			"}";
	}
}
