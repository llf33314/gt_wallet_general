package com.gt.wallet.service.member;

import com.gt.api.bean.session.BusUser;
import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.request.CompanyUploadFile;
import com.gt.wallet.data.wallet.request.WalletCompanyAdd;
import com.gt.wallet.data.wallet.request.WalletCompanyAddress;
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
	 * @param walletCompany 企业信息
	 * @param busUser 商家信息
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> save(WalletCompanyAdd walletCompany,BusUser busUser) throws Exception;
	
	
	/**
	 * 上传文件
	 * @param companyUploadFile
	 * @return
	 * @throws Exception
	 */
	ServerResponse<?> uploadFile(CompanyUploadFile companyUploadFile,BusUser busUser)throws Exception;
	
	/**
	 * 根据会员id查询
	 * @param memberId
	 * @return
	 */
	ServerResponse<WalletCompany> findByMemberId(Integer memberId);
	
	
	/**
	 * 修改地址
	 * @param walletCompany 企业信息
	 * @param busUser 商家信息
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> updateAddress(WalletCompanyAddress walletCompany,BusUser busUser) throws Exception;
	
	
}
