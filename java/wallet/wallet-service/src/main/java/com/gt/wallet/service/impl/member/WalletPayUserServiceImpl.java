package com.gt.wallet.service.impl.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletPayUser;
import com.gt.wallet.mapper.member.WalletPayUserMapper;
import com.gt.wallet.service.member.WalletPayUserService;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 支付用户 服务实现类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-15
 */
@Service
@Slf4j
public class WalletPayUserServiceImpl extends BaseServiceImpl<WalletPayUserMapper, WalletPayUser> implements WalletPayUserService {
	
	@Autowired
	private WalletPayUserMapper walletPayUserMapper;

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> add(WalletPayUser walletPayUser) {
		log.info(CommonUtil.format("start biz add api params:%s", JsonUtil.toJSONString(walletPayUser)));
		ServerResponse<WalletPayUser> responseWalletPayUser=findByOpenidAndUserType(walletPayUser.getOpenid(), walletPayUser.getUserType());
		log.info(CommonUtil.format("responseWalletPayUser:%s", JsonUtil.toJSONString(walletPayUser)));
		Integer count=null;
		if(ServerResponse.judgeSuccess(responseWalletPayUser)&&CommonUtil.isNotEmpty(responseWalletPayUser.getData())){
			count=walletPayUserMapper.updateById(walletPayUser);
		}else{
			count=walletPayUserMapper.insert(walletPayUser);
		}
		log.info(CommonUtil.format("biz add api count:%s", count));
		return ServerResponse.createBySuccess();
	}

	@Override
	public ServerResponse<WalletPayUser> findByOpenidAndUserType(String openid, Integer userType) {
		log.info(CommonUtil.format("start biz findByOpenidAndUserType api params:%s,%s", openid,userType));
		WalletPayUser entity=new  WalletPayUser();
		entity.setOpenid(openid);
		entity.setUserType(userType);
		WalletPayUser walletPayUser=walletPayUserMapper.selectOne(entity);
		return ServerResponse.createBySuccessCodeData(walletPayUser);
	}
	
}
