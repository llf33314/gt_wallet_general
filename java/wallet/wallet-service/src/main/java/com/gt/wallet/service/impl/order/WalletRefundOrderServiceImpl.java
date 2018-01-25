package com.gt.wallet.service.impl.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletRefundOrder;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.order.WalletRefundOrderMapper;
import com.gt.wallet.service.order.WalletRefundOrderService;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 退款订单表 服务实现类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2018-01-08
 */
@Slf4j
@Service
public class WalletRefundOrderServiceImpl extends BaseServiceImpl<WalletRefundOrderMapper, WalletRefundOrder> implements WalletRefundOrderService {
	
	@Autowired
	private WalletRefundOrderMapper walletRefundOrderMapper;

	@Override
	public ServerResponse<?> addRecord(WalletRefundOrder walletRefundOrder) {
		log.info(CommonUtil.format("start biz addRecord api params:%s", JsonUtil.toJSONString(walletRefundOrder)));
		ServerResponse<WalletRefundOrder>  serverResponseWalletPayOrder=	findRecord(walletRefundOrder.getRefundOrderNo());
		if(ServerResponse.judgeSuccess(serverResponseWalletPayOrder)&&CommonUtil.isNotEmpty(serverResponseWalletPayOrder.getData())){
			log.error("biz addRecord api fail：退款订单号已存在!");
			throw new BusinessException("退款订单号已存在!");
		}
		
		Integer count=walletRefundOrderMapper.insert(walletRefundOrder);
		if(count==1){
			return ServerResponse.createBySuccess();
		}else{
			
			return ServerResponse.createByError();
		}
	}

	@Override
	public ServerResponse<WalletRefundOrder> findRecord(String refundOrderNo) {
		log.info(CommonUtil.format("start biz findRecord api params:%s", JsonUtil.toJSONString(refundOrderNo)));
		WalletRefundOrder entity=new WalletRefundOrder();
		entity.setRefundOrderNo(refundOrderNo);
		WalletRefundOrder walletRefundOrder=walletRefundOrderMapper.selectOne(entity);
		if(CommonUtil.isEmpty(walletRefundOrder)){
			return ServerResponse.createByErrorCode(WalletResponseEnums.DATA_NULL_ERROR);
		}else{
			
			return ServerResponse.createBySuccessCodeData(walletRefundOrder);
		}
	}
}
