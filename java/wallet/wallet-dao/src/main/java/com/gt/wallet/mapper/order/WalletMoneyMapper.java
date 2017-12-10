package com.gt.wallet.mapper.order;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.entity.WalletMoney;

/**
 * <p>
  * 提现记录表 Mapper 接口
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletMoneyMapper extends BaseMapper<WalletMoney> {
	
	
	/**
	 * 获取历史提现总额
	 * @param wmemberId
	 * @return
	 */
	IndexStatistics getStatisticsByWmemberId(@Param("wmemberId")Integer wmemberId);

	
}