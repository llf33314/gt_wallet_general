package com.gt.wallet.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import com.baomidou.mybatisplus.enums.FieldFill;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 退款订单表
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2018-01-08
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_refund_order")
public class WalletRefundOrder extends Model<WalletRefundOrder> {

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
     * 退款订单号
     */
	@TableField("refund_order_no")
	private String refundOrderNo;
    /**
     * 通联订单号
     */
	@TableField("refund_external_no")
	private String refundExternalNo;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletRefundOrder{" +
			"id=" + id +
			", busId=" + busId +
			", wMemberId=" + wMemberId +
			", sysOrderNo=" + sysOrderNo +
			", amount=" + amount +
			", fee=" + fee +
			", ctime=" + ctime +
			", refundOrderNo=" + refundOrderNo +
			", refundExternalNo=" + refundExternalNo +
			"}";
	}
}
