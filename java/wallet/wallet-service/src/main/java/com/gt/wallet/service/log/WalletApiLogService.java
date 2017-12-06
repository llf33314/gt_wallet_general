package com.gt.wallet.service.log;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletApiLog;

/**
 * <p>
 * 调用第三方api日志表 服务类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletApiLogService extends BaseService<WalletApiLog> {
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	ServerResponse<WalletApiLog> selectById(Integer id);
	
	
	/**
	 * 保存api调用日志
	 * @param apiLog
	 * @return
	 */
	ServerResponse<?> save(WalletApiLog apiLog)throws  Exception;
	
	
	/**
	 * 保存api调用日志
	 * @param apiLog
	 * @return
	 */
	ServerResponse<?> save(String paramJson, ServerResponse<?> serverResponse,Integer memberId,String url,String orderNo)throws  Exception;
	
}
