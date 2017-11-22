package com.gt.wallet.service.impl.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletApiLog;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.log.WalletApiLogMapper;
import com.gt.wallet.service.log.WalletApiLogService;
import com.gt.wallet.utils.CommonUtil;

/**
 * <p>
 * 调用第三方api日志表 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
public class WalletApiLogServiceImpl extends BaseServiceImpl<WalletApiLogMapper, WalletApiLog> implements WalletApiLogService {
	@Autowired
	private WalletApiLogMapper walletApiLogMapper;

	@Override
	public ServerResponse<WalletApiLog> selectById(Integer id) {
		// TODO Auto-generated method stub
		ServerResponse<WalletApiLog> serverResponse=null;
		serverResponse=ServerResponse.createBySuccessCodeData(walletApiLogMapper.selectById(id));
		return serverResponse;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerResponse<?> save(WalletApiLog apiLog)throws  Exception {
		ServerResponse<?> serverResponse=null;
		if(CommonUtil.isNotEmpty(apiLog)){
			int count=walletApiLogMapper.insert(apiLog);
			serverResponse=ServerResponse.createBySuccessCodeData(count);
			return serverResponse;
		}else{
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
	}
	
}
