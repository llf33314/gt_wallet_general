package com.gt.wallet.service.member;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletPayUser;

/**
 * <p>
 * 支付用户 服务类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-15
 */
public interface WalletPayUserService extends BaseService<WalletPayUser> {
	
	
	/**
	 * 
	 * @param walletPayUser
	 * @return
	 */
	ServerResponse<?> add(WalletPayUser walletPayUser);
	
	/**
	 * 根据第三方唯一标识查询
	 * @param openid 第三方唯一标识
	 * @param userType 类型(1:微信 2:支付宝)
	 * @return
	 */
	ServerResponse<WalletPayUser> findByOpenidAndUserType(String openid,Integer userType);
}
