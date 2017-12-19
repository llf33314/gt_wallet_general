package com.gt.wallet.service.member;

import com.gt.api.bean.session.BusUser;
import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
import com.gt.wallet.data.wallet.request.WalletSet;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletIndividual;

/**
 * <p>
 * 个人会员 服务类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletIndividualService extends BaseService<WalletIndividual> {
	
	
	/**
	 * 新增个人会员信息
	 * @param walletIndividual 会员信息
	 * @return
	 */
	public ServerResponse<Integer> add(WalletIndividualAdd walletIndividualAdd ,BusUser busUser) throws Exception;
	
	
	
	/**
	 * 钱包设置
	 * @param walletSet 需要设置的信息
	 * @param busUser 商家信息
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> set(WalletSet walletSet,BusUser busUser) throws Exception;
	
	
	/**
	 * 根据会员id查询
	 * @param memberId
	 * @return
	 */
	ServerResponse<WalletIndividual> findByMemberId(Integer memberId);
}


