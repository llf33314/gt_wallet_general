package com.gt.wallet.web.api.subsystem;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.api.tonglian.request.TRefundOrder;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月8日 上午10:47:39 
* 类说明 
*/
@RequestMapping("8A5DA52E/memberApi/")
@Slf4j
@RestController
@Api(value = "orderApi",description="订单api")  
public class OrderAPI {
	
	private WalletPayOrderService walletPayOrderService;
	/**
	 * 退款api
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/refund",method=RequestMethod.POST)
	 @ApiOperation(value="退款api", notes="退款api",produces="application/json")
	public ServerResponse<?> refund(HttpServletRequest request,@RequestBody RequestUtils<TRefundOrder> requestUtils ){
		log.info(CommonUtil.format("start view refund api params:%s",JsonUtil.toJSONString(requestUtils)));
		try {
			ServerResponse<?> serverResponse=walletPayOrderService.refund(requestUtils.getReqdata());
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view refund api fail ：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view refund api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
}
