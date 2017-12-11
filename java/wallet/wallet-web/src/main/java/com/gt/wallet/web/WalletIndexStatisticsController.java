package com.gt.wallet.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.bean.session.BusUser;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.order.WalletIndexStatisticsService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 首页统计 前端控制器
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-09
 */
@RestController
@RequestMapping("/walletIndexStatistics")
@Slf4j
@Api(value = "walletIndexStatistics",description="首页统计")  
public class WalletIndexStatisticsController extends BaseController {
	
	@Autowired
	private WalletIndexStatisticsService walletIndexStatisticsService;
	
	/**
	 * 获取首页总计数据
	 * @return
	 */
	@RequestMapping(value="getIndexStatistics",method=RequestMethod.GET)
	 @ApiOperation(value="获取首页总计数据", notes="获取首页总计数据，但必须登录多粉商家后台",produces="application/json")
	public ServerResponse<IndexStatistics> getIndexStatistics(HttpServletRequest request){
		log.info(CommonUtil.format("触发获取首页总计数据接口"));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<IndexStatistics> serverResponse2=walletIndexStatisticsService.getIndexStatistics(busUser.getId());
			return serverResponse2;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("获取首页总计数据接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("获取首页总计数据接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
}
