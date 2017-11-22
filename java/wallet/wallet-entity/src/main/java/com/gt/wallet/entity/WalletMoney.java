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
public class WalletMoney extends Model<WalletMoney> {

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
     * 银行卡号/账号
     */
	private String bankCardNo;
    /**
     * 银行卡/账户属性
     */
	private Integer bankCardPro;
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
     * 账户集编号
     */
	@TableField("account_set_no")
	private String accountSetNo;
    /**
     * 提现方式
     */
	@TableField("withdraw_type")
	private String withdrawType;
    /**
     * 云账号订单
     */
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
