package com.gt.wallet.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletIndexStatistics;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.order.WalletIndexStatisticsMapper;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.service.order.WalletIndexStatisticsService;
import com.gt.wallet.service.order.WalletMoneyService;
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
	
//	@Autowired
//	private WalletIndexStatisticsMapper walletIndexStatisticsMapper;
	@Autowired
	private WalletMoneyService walletMoneyService;
	
	@Override
	public ServerResponse<IndexStatistics> getIndexStatistics(Integer busId) throws Exception {
		log.info(CommonUtil.format("start biz getIndexStatistics api params:%s",JsonUtil.toJSONString(busId)));
		ServerResponse<List<WalletMember>> memberServerResponse=walletMemberService.findMember(busId);
		if(ServerResponse.judgeSuccess(memberServerResponse)&&memberServerResponse.getData().size()>0){
			WalletMember walletMember=memberServerResponse.getData().get(0);
//			WalletIndexStatistics params=new WalletIndexStatistics();
//			params.setWMemberId(walletMember.getId());
//			WalletIndexStatistics walletIndexStatistics=walletIndexStatisticsMapper.selectOne(params);
//			IndexStatistics indexStatistics=new IndexStatistics();
//			if(CommonUtil.isEmpty(walletIndexStatistics)){
//				indexStatistics.setBalance(0.0);
//				indexStatistics.setTotal(0.0);
//				indexStatistics.setWaitBalance(0.0);
//				log.info(CommonUtil.format("end getIndexStatistics api:%s",JsonUtil.toJSONString(indexStatistics)));
//				return ServerResponse.createBySuccessCodeData(indexStatistics);
//			}else{
//				indexStatistics.setBalance(walletIndexStatistics.getBalance().doubleValue());
//				indexStatistics.setTotal(walletIndexStatistics.getTotal().doubleValue());
//				indexStatistics.setWaitBalance(walletIndexStatistics.getWaitBalance().doubleValue());
//				log.info(CommonUtil.format("end getIndexStatistics api:%s",JsonUtil.toJSONString(indexStatistics)));
//				return ServerResponse.createBySuccessCodeData(indexStatistics);
//			}
			
			return walletMoneyService.getTotal(walletMember.getId());
		}else{
			log.error("biz getIndexStatistics api fail:请先注册多粉会员");
			throw new BusinessException("请先注册多粉会员");
		}
		
	}
	
}
