package com.gt.wallet.mapper.order;

import com.gt.wallet.entity.WalletPayOrder;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 钱包支付记录 Mapper 接口
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletPayOrderMapper extends BaseMapper<WalletPayOrder> {
	
	/**
	 * 获取待结算金额
	 * @param wmemberId
	 * @param cime
	 * @return
	 */
	Double getWithBalance(@Param("wmemberId") Integer wmemberId,@Param("ctime") String ctime);

}