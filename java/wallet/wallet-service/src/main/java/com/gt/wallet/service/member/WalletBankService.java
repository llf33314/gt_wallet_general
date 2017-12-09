package com.gt.wallet.service.member;

import java.util.List;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.api.tonglian.request.TCardBin;
import com.gt.wallet.data.wallet.request.WalletCompanyAdd;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletBank;

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
	 * @param walletIndividualAdd 银行卡信息
	 * @param isSafeCard 是否为安全卡 0：是 1：否
	 * @return
	 * @throws Exception
	 */
	ServerResponse<Integer> add(WalletIndividualAdd walletIndividualAdd,Integer isSafeCard)throws Exception;
	
	/**
	 * 绑定银行卡(对公账号)
	 * @param walletIndividualAdd
	 * @return
	 * @throws Exception
	 */
	ServerResponse<Integer> addPublic(WalletCompanyAdd walletCompanyAdd)throws Exception;
	
	
	
	
	/**
	 * 确认绑定银行卡
	 * @param busId 商家id
	 * @param id 主键
	 * @param verificationCode 验证码
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> bindBankCard(Integer busId, Integer id,String verificationCode) throws Exception;
	
	/**
	 * 获取银行卡信息
	 * @param bankCardNo
	 * @return
	 */
	ServerResponse<TCardBin> getBankCardBin(String bankCardNo);
	
	
}
