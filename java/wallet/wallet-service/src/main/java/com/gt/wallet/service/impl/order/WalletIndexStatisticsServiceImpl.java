package com.gt.wallet.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletIndexStatistics;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletPayOrder;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.order.WalletIndexStatisticsMapper;
import com.gt.wallet.mapper.order.WalletPayOrderMapper;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.service.order.WalletIndexStatisticsService;
import com.gt.wallet.service.order.WalletMoneyService;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 首页统计 服务实现类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-09
 */
@Service
@Slf4j
public class WalletIndexStatisticsServiceImpl extends BaseServiceImpl<WalletIndexStatisticsMapper, WalletIndexStatistics> implements WalletIndexStatisticsService {

	
	@Autowired
	private WalletMemberService walletMemberService;
	
	@Autowired
	WalletPayOrderService walletPayOrderService;
	
	@Override
	public ServerResponse<IndexStatistics> getIndexStatistics(Integer busId) throws Exception {
		log.info(CommonUtil.format("start biz getIndexStatistics api params:%s",JsonUtil.toJSONString(busId)));
		ServerResponse<List<WalletMember>> memberServerResponse=walletMemberService.findMember(busId);
		if(ServerResponse.judgeSuccess(memberServerResponse)&&memberServerResponse.getData().size()>0){
			IndexStatistics indexStatistics=new IndexStatistics();
			WalletMember walletMember=memberServerResponse.getData().get(0);
			Wrapper<WalletPayOrder> wrapper=new EntityWrapper<WalletPayOrder>() ;
			indexStatistics.setBalance(walletMember.getWBalance());
			wrapper.where("w_member_id={0}",walletMember.getId()).and("status={0}", "success");
			List<WalletPayOrder> walletPayOrders=walletPayOrderService.selectList(wrapper);
			if(walletPayOrders.size()>0){
				Double waitBalance=0.00;
				for (WalletPayOrder walletPayOrder : walletPayOrders) {
					if(CommonUtil.isEmpty(walletPayOrder.getSysRefundNo())){//没有退款过
						waitBalance=waitBalance+(walletPayOrder.getAmount().doubleValue()-walletPayOrder.getFee().doubleValue());
					}else {
						if(walletPayOrder.getAmount()==walletPayOrder.getRefundAmount()){//全额退款
							waitBalance=waitBalance+(walletPayOrder.getAmount().doubleValue()-walletPayOrder.getRefundAmount().doubleValue());
						}else{//多次退款
							waitBalance=waitBalance+(walletPayOrder.getAmount().doubleValue()-walletPayOrder.getFee().doubleValue()-walletPayOrder.getRefundAmount().doubleValue());
						}
					}
				}
				indexStatistics.setWaitBalance(waitBalance);
			}else{
				indexStatistics.setWaitBalance(0.00);
			}
			indexStatistics.setTotal(indexStatistics.getBalance()+indexStatistics.getWaitBalance());
			return ServerResponse.createBySuccessCodeData(indexStatistics);
		}else{
			log.error("biz getIndexStatistics api fail:请先注册多粉会员");
			throw new BusinessException("请先注册多粉会员");
		}
		
	}
	
}
