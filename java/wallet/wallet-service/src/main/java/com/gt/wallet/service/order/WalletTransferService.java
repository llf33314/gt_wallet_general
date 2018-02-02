package com.gt.wallet.service.order;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletTransfer;

/**
 * <p>
 * 转账记录 服务类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2018-01-30
 */
public interface WalletTransferService extends BaseService<WalletTransfer> {
	
	 /**
	  * 划账(手动)
	  * @param busId  商家id
	  * @return
	  */
	 ServerResponse<?> addDebit(Integer busId)throws Exception;
	 
	 /**
	  * 划账(系统定时)
	  * @param busId  商家id
	  * @return
	  */
	 ServerResponse<?> taskAddDebit()throws Exception;
	
}
