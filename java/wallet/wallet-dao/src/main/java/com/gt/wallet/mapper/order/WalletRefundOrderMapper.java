package com.gt.wallet.mapper.order;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gt.wallet.entity.WalletRefundOrder;

/**
 * <p>
  * 退款订单表 Mapper 接口
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2018-01-08
 */
public interface WalletRefundOrderMapper extends BaseMapper<WalletRefundOrder> {
	
	/**
	 * 获取当前订单已退款金额(退款金额+手续费)
	 * @param sysOrderNo
	 * @return
	 */
	public double getRefundMoney(@Param("sysOrderNo")String sysOrderNo);

}