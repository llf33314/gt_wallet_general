package com.gt.wallet.service.member;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletCompany;

/**
 * <p>
 * 企业会员明细 服务类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletCompanyService extends BaseService<WalletCompany> {
	
	
	/**
	 * 保存企业会员
	 * @param walletIndividual 企业会员
	 * @return
	 */
	public ServerResponse<Integer> save(WalletCompany walletCompany) throws Exception;
	
}
