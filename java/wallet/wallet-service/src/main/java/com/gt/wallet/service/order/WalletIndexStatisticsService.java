package com.gt.wallet.service.order;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletIndexStatistics;

/**
 * <p>
 * 首页统计 服务类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-09
 */
public interface WalletIndexStatisticsService extends BaseService<WalletIndexStatistics> {
	
	
	/**
	 * 获取首页总计数据
	 * @param busId 商家id
	 * @return
	 * @throws Exception
	 */
	ServerResponse<IndexStatistics> getIndexStatistics(Integer busId) throws Exception;
	
	
	
	
	
}
