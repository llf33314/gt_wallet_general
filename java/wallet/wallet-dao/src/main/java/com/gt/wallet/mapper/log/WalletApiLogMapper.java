package com.gt.wallet.mapper.log;

import com.gt.wallet.entity.WalletApiLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 调用第三方api日志表 Mapper 接口
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletApiLogMapper extends BaseMapper<WalletApiLog> {
	
	WalletApiLog	queryByPrimaryKey(Integer id);

}