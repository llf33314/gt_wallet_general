package com.gt.wallet.service.order;

import java.util.Map;

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
	 * 通联下单
	 * @param payOrder
	 * @return
	 */
	ServerResponse<com.alibaba.fastjson.JSONObject> applyDeposit(PayOrder payOrder) throws Exception;
	
	/**
	 * 保存订单信息
	 * @param payOrder
	 * @return
	 */
	ServerResponse<?> save(PayOrder payOrder) throws Exception;
	
	
	/**
	 * 根据订单号查询
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	ServerResponse<WalletPayOrder> findByOrderNo(String orderNo) throws Exception;
	
//	ServerResponse<?>	paySuccessNotify(Map<String, Object> params);
}
