package com.gt.wallet.service.order;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletPayOrder;

/**
 * <p>
 * 钱包支付记录 服务类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletPayOrderService extends BaseService<WalletPayOrder> {
	
	
	/**
	 * 商家
	 * @param payOrder
	 * @return
	 */
	ServerResponse<?> applyDeposit(PayOrder payOrder) throws Exception;
	
}
