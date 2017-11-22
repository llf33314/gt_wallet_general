package com.gt.wallet.service.impl.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletCompany;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.member.WalletCompanyMapper;
import com.gt.wallet.service.member.WalletCompanyService;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 企业会员明细 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletCompanyServiceImpl extends BaseServiceImpl<WalletCompanyMapper, WalletCompany> implements WalletCompanyService {
	
	private WalletCompanyMapper walletCompanyMapper;

	/**
	 * 使用事务控制
	 * REQUIRED:当前没有其他事务控制则新增一个，有则引用当前事务(事务控制)
	 * Isolation.DEFAULT：存在多个事务时，A事务读取了一条记录时，B事务将不能修改记录,防止脏读(事务隔离级别)
	 * timeout
	 * rollbackFor：指定事务回滚异常类型
	 */
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<Integer> save(WalletCompany walletCompany) throws Exception {
		log.info(CommonUtil.format("biz接口:保存会员,请求参数:%s", JsonUtil.toJSONString(walletCompany)));
		ServerResponse<Integer> serverResponse=null;
		if(CommonUtil.isEmpty(walletCompany)){
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		if(CommonUtil.isEmpty(walletCompany.getId())){//新增
			walletCompanyMapper.insert(walletCompany);
		}else{//修改
			walletCompanyMapper.updateById(walletCompany);
		}
		serverResponse=ServerResponse.createBySuccess();
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return serverResponse=ServerResponse.createBySuccess();
	}
}
