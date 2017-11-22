package com.gt.wallet.service.log;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.request.ReviewResult;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletQuota;
import com.gt.wallet.utils.MyPageUtil;

/**
 * <p>
 * 额度申请 服务类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-11-20
 */
public interface WalletQuotaService extends BaseService<WalletQuota> {
	
	/**
	 * 新增申请额度记录
	 * @param walletQuota
	 * @return code:0成功 其他失败
	 * @throws Exception
	 */
	public ServerResponse<?> add(WalletQuota walletQuota) throws Exception;
	
	
	/**
	 * 审核结果回调
	 * @param reviewResult
	 * @return
	 */
	public ServerResponse<?> review(ReviewResult reviewResult);
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public ServerResponse<MyPageUtil<WalletQuota>> getPage(Page<WalletQuota> page,Integer status);
	
}
