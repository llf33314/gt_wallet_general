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
 * 首页统计
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@TableName("t_wallet_index_statistics")
public class WalletIndexStatistics extends Model<WalletIndexStatistics> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 钱包会员id
     */
	@TableField("w_member_id")
	private Integer wMemberId;
    /**
     * 资产总额
     */
	private BigDecimal total;
    /**
     * 待结算
     */
	@TableField("wait_balance")
	private BigDecimal waitBalance;
    /**
     * 余额
     */
	private BigDecimal balance;
    /**
     * 日期
     */
	private Date ctime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletIndexStatistics{" +
			"id=" + id +
			", wMemberId=" + wMemberId +
			", total=" + total +
			", waitBalance=" + waitBalance +
			", balance=" + balance +
			", ctime=" + ctime +
			"}";
	}
}
