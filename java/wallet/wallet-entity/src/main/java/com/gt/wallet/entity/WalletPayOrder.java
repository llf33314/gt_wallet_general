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
     * 商户系统用户标识
     */
	@TableField("payer_id")
	private String payerId;
    /**
     * 系统订单号
     */
	@TableField("sys_order_no")
	private String sysOrderNo;
    /**
     * 商品类型
     */
	@TableField("goods_type")
	private Integer goodsType;
    /**
     * 商户系统商品编号
     */
	@TableField("goods_no")
	private String goodsNo;
    /**
     * 业务码
     */
	private String tradeCode;
    /**
     * 订单金额
     */
	private BigDecimal amount;
    /**
     * 手续费
     */
	private BigDecimal fee;
    /**
     * 交易验证方式
     */
	@TableField("validate_type")
	private Integer validateType;
    /**
     * 订单生成时间
     */
	private Date ctime;
    /**
     * 支付方式
     */
	@TableField("pay_method")
	private String payMethod;
    /**
     * 商品名称
     */
	@TableField("goods_name")
	private String goodsName;
    /**
     * 商品描述
     */
	@TableField("goods_desc")
	private String goodsDesc;
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
     * 扩展信息
     */
	private String extendInfo;
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
     * 移动认证支付订单号
     */
	@TableField("cert_pay_order_no")
	private String certPayOrderNo;
    /**
     * 移动认证支付订单生成时
            间
     */
	@TableField("order_datetime")
	private String orderDatetime;
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
     * 扫码支付信息/ JS 支付串信息  JS 支付必传;
            微信公众号JS 支付：返回
            json 字符串
     */
	@TableField("pay_info")
	private String payInfo;
    /**
     * 新移动快捷支付支付密码标记
     */
	private String needpassword;
    /**
     * 新移动快捷支付短信标记
     */
	@TableField("mobile_flag")
	private Integer mobileFlag;
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
			", payerId=" + payerId +
			", sysOrderNo=" + sysOrderNo +
			", goodsType=" + goodsType +
			", goodsNo=" + goodsNo +
			", tradeCode=" + tradeCode +
			", amount=" + amount +
			", fee=" + fee +
			", validateType=" + validateType +
			", ctime=" + ctime +
			", payMethod=" + payMethod +
			", goodsName=" + goodsName +
			", goodsDesc=" + goodsDesc +
			", industryCode=" + industryCode +
			", industryName=" + industryName +
			", visitSource=" + visitSource +
			", goodsSummary=" + goodsSummary +
			", extendInfo=" + extendInfo +
			", status=" + status +
			", payFailMessage=" + payFailMessage +
			", externalNo=" + externalNo +
			", tradeNo=" + tradeNo +
			", certPayOrderNo=" + certPayOrderNo +
			", orderDatetime=" + orderDatetime +
			", payCode=" + payCode +
			", weChatAapinfo=" + weChatAapinfo +
			", payInfo=" + payInfo +
			", needpassword=" + needpassword +
			", mobileFlag=" + mobileFlag +
			", sysRefundNo=" + sysRefundNo +
			", refundAmount=" + refundAmount +
			", refundFeeamount=" + refundFeeamount +
			", refundExternalNo=" + refundExternalNo +
			"}";
	}
}
