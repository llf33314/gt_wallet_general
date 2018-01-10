package com.gt.wallet.service.order;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletRefundOrder;

/**
 * <p>
 * 退款订单表 服务类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2018-01-08
 */
public interface WalletRefundOrderService extends BaseService<WalletRefundOrder> {
	
	 /**
	  * 新增退款记录
	  * @param walletRefundOrder 
	  * @return
	  */
	 ServerResponse<?> addRecord(WalletRefundOrder walletRefundOrder );
	 
	 /**
	  * 通过退款订单号查询订单信息
	  * @param refundOrderNo
	  * @return
	  */
	 ServerResponse<WalletRefundOrder> findRecord(String refundOrderNo);
}
