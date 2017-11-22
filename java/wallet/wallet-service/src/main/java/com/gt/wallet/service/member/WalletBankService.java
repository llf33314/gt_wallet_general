package com.gt.wallet.service.member;

import com.gt.wallet.entity.WalletBank;

import java.util.List;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
import com.gt.wallet.dto.ServerResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletBankService extends BaseService<WalletBank> {
	
	
	
	/**
	 * 获取会员银行卡列表
	 * @param wmemberId
	 * @return
	 */
	public ServerResponse<List<WalletBank>> getWalletBanksByMemberId(Integer wmemberId);
	
	
	/**
	 * 绑定银行卡
	 * @param walletIndividualAdd
	 * @return
	 * @throws Exception
	 */
	ServerResponse<Integer> add(WalletIndividualAdd walletIndividualAdd)throws Exception;
	
	
	/**
	 * 确认绑定银行卡
	 * @param busId 商家id
	 * @param id 主键
	 * @param verificationCode 验证码
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> bindBankCard(Integer busId, Integer id,String verificationCode) throws Exception;
}
