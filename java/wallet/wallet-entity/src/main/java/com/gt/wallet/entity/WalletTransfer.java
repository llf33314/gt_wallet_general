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
 * 转账记录
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2018-01-30
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_transfer")
public class WalletTransfer extends Model<WalletTransfer> {

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
     * 订单号
     */
	@TableField("sys_order_no")
	private String sysOrderNo;
    /**
     * 描述
     */
	@TableField("w_desc")
	private String wDesc;
    /**
     * 金额
     */
	private BigDecimal amount;
    /**
     * 订单生成时间
     */
	private Date ctime;
    /**
     * 状态
     */
	private Integer status;
	
	@TableField("transfer_no")
	private String transferNo;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletTransfer{" +
			"id=" + id +
			", busId=" + busId +
			", wMemberId=" + wMemberId +
			", sysOrderNo=" + sysOrderNo +
			", wDesc=" + wDesc +
			", amount=" + amount +
			", ctime=" + ctime +
			", status=" + status +
			"}";
	}
}
