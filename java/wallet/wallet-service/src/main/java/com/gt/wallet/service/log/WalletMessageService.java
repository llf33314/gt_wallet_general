package com.gt.wallet.service.log;

import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.request.SearchMsgPage;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMessage;
import com.gt.wallet.utils.MyPageUtil;

/**
 * <p>
 * 消息中心 服务类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-14
 */
public interface WalletMessageService extends BaseService<WalletMessage> {
	
	/**
	 * 新增消息
	 * @param wMemberId
	 * @param msgType
	 * @param descContent
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> add(Integer wMemberId,Integer msgType,String descContent ,Integer orderId) throws Exception;
	
	/**
	 * 修为为已读状态
	 * @param listStr 消息id,可传多个用逗号隔开
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> upstate(String listStr) throws Exception;
	
	
	
	/**
	 * 分页查询
	 * @param searchMsgPage
	 * @return
	 */
	public ServerResponse<MyPageUtil<WalletMessage>> getPage(SearchMsgPage searchMsgPage);
	
	
	/**
	 * 获取未读消息条数
	 * @param wMemberId
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<Integer> getReadState(Integer wMemberId) throws Exception; 
	
}
