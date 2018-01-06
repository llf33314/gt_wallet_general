package com.gt.wallet.service.order;

import java.util.LinkedHashMap;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.api.tonglian.request.TRefundOrder;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.data.wallet.request.SearchPayOrderPage;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletPayOrder;
import com.gt.wallet.utils.MyPageUtil;

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
	 * 钱包支付(支付宝微信)
	 * @param payOrder
	 * @return
	 */
	ServerResponse<com.alibaba.fastjson.JSONObject> codepay(PayOrder payOrder) throws Exception;
	
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
	
	
	/**
	 * 根据提交订单号查询
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	ServerResponse<WalletPayOrder> findBySubmitOrderNo(String submitOrderNo) throws Exception;
	
	/**
	 * 支付成功异步回调
	 * @param params
	 * @return
	 * @throws Exception
	 */
	ServerResponse<?>	paySuccessNotify(LinkedHashMap<String,Object> params)throws Exception;
	
	/**
	 * 分页
	 * @param page
	 * @param status
	 * @return
	 */
	 ServerResponse<MyPageUtil<WalletPayOrder>> getPage(Page<?> page,SearchPayOrderPage searchPayOrderPage);
	 
	 
	
	 /**
	  * 退款
	  * @param payOrder
	  * @return
	  */
	 ServerResponse<?> refund(TRefundOrder refundOrder) throws Exception;
}


