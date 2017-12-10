package com.gt.wallet.service.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.request.SearchPayOrderPage;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMoney;
import com.gt.wallet.utils.MyPageUtil;

/**
 * <p>
 * 提现记录表 服务类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletMoneyService extends BaseService<WalletMoney> {
	
	/**
	 * 分页
	 * @param page
	 * @param status
	 * @return
	 */
	 ServerResponse<MyPageUtil<WalletMoney>> getPage(Page<?> page,SearchPayOrderPage searchPayOrderPage);
	 
	 
	 /**
	 * 获取首页统计数据(实时)
	 * @param wmemberId 钱包会员id
	 * @param status
	 * @return
	 */
	 ServerResponse<IndexStatistics> getTotal(Integer wmemberId);
	 
	 /**
	  * 提现
	  * @param busId 商家id
	  * @param money 金额
	  * @param bankId 银行卡id
	  * @return
	  */
	 ServerResponse<?> withdrawApply(Integer busId,double money,Integer bankId) throws Exception;
	
}
