package com.gt.wallet.service.impl.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletApiLog;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.log.WalletApiLogMapper;
import com.gt.wallet.service.log.WalletApiLogService;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 调用第三方api日志表 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletApiLogServiceImpl extends BaseServiceImpl<WalletApiLogMapper, WalletApiLog> implements WalletApiLogService {
	@Autowired
	private WalletApiLogMapper walletApiLogMapper;

	@Override
	public ServerResponse<WalletApiLog> selectById(Integer id) {
		log.info(CommonUtil.format("start biz selectById api params:%s", id));
		ServerResponse<WalletApiLog> serverResponse=null;
		serverResponse=ServerResponse.createBySuccessCodeData(walletApiLogMapper.selectById(id));
		log.info(CommonUtil.format("biz selectById api serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return serverResponse;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerResponse<?> save(WalletApiLog apiLog)throws  Exception {
		log.info(CommonUtil.format("start biz selectById api params:%s", JsonUtil.toJSONString(apiLog)));
		ServerResponse<?> serverResponse=null;
		if(CommonUtil.isNotEmpty(apiLog)){
			int count=walletApiLogMapper.insert(apiLog);
			serverResponse=ServerResponse.createBySuccessCodeData(count);
			log.info(CommonUtil.format("biz save api serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
		}else{
			log.error("biz selectById api fail");
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
	}

	@Override
	public ServerResponse<?> save(String paramJson, ServerResponse<?> serverResponse,Integer memberId,String url,String orderNo,Integer type) throws Exception {
		log.info(CommonUtil.format("start biz save api params:%s,%s,%s,%s,%s,%s",paramJson, JsonUtil.toJSONString(serverResponse),memberId,url,orderNo,type));
		WalletApiLog walletApiLog=new WalletApiLog();
		walletApiLog.setMsg(serverResponse.getMsg());
		walletApiLog.setParamsJson(paramJson);
		walletApiLog.setResult(JsonUtil.toJSONString(serverResponse));
		walletApiLog.setType(type);
		walletApiLog.setUrl(url);
		walletApiLog.setWMemberId(memberId);
		walletApiLog.setOrderNo(orderNo);
		if(ServerResponse.judgeSuccess(serverResponse)){
			walletApiLog.setStatus(serverResponse.getCode());
			
		}else{
			walletApiLog.setStatus(-1);
		}
		Integer count=walletApiLogMapper.insert(walletApiLog);
		log.info(CommonUtil.format("biz save api count:%s", count));
		if(count==1){
			return ServerResponse.createBySuccess();
		}else{
			return ServerResponse.createByError();
		}
	}

	@Override
	public ServerResponse<WalletApiLog> findById(String orderNo, Integer type) {
		log.info(CommonUtil.format("start biz findById api params:%s,%s",orderNo,type));
		WalletApiLog params=new  WalletApiLog();
		params.setOrderNo(orderNo);
		params.setType(type);
		WalletApiLog walletApiLog=	walletApiLogMapper.selectOne(params);
		if(CommonUtil.isEmpty(walletApiLog)){
			log.error("record is empty");
			return ServerResponse.createByError();
		}else{
			log.info(CommonUtil.format("biz save api walletApiLog:%s", JsonUtil.toJSONString(walletApiLog)));
			return ServerResponse.createBySuccessCodeData(walletApiLog);
		}
	}
	
	
	
	
}
