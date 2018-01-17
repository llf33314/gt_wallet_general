package com.gt.wallet.web.api.subsystem;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.util.KeysUtil;
import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.api.tonglian.request.TRefundOrder;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	
	
	
	/**
	 * 支付下单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/79B4DE7C/codepay", method = RequestMethod.POST)
	@ApiOperation(value = "支付下单", notes = "支付下单")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "busId",value = "商家id",paramType = "form",dataType = "int",required=true,example="35"),
        @ApiImplicitParam(name = "bizOrderNo",value = "系统订单号",paramType = "form",dataType = "string",required=true,example="cy123456789")
        ,
        @ApiImplicitParam(name = "acct",value = "支付授权码",paramType = "form",dataType = "string",required=true,example="openid或userid(支付宝)")
        ,
//        @ApiImplicitParam(name = "frontUrl",value = "前台通知地址",paramType = "form",dataType = "string",required=true,defaultValue="http://duofriend.com")
//        ,
        @ApiImplicitParam(name = "notifyUrl",value = "后台通知地址",paramType = "form",dataType = "string",required=true,defaultValue="http://duofriend.com")
        ,
        @ApiImplicitParam(name = "type",value = "支付方式 1：微信 2:支付宝",paramType = "form",dataType = "int",required=true,defaultValue="1")
        ,
        @ApiImplicitParam(name = "desc",value = "描述",paramType = "form",dataType = "string",required=true,defaultValue="描述")
        ,
        @ApiImplicitParam(name = "takeState",value = "是否可立即提现(1:可取 2:不可取)",paramType = "form",dataType = "string",required=true,defaultValue="1")
        ,
        @ApiImplicitParam(name = "model",value = "支付模块",paramType = "form",dataType = "string",required=true,defaultValue="1")
        ,
        @ApiImplicitParam(name = "memberId",value = "会员ID",paramType = "form",dataType = "string",required=true,defaultValue="1")
        ,
        @ApiImplicitParam(name = "sendUrl",value = "推送路径",paramType = "form",dataType = "string",required=true,defaultValue="1")
       })
	public ServerResponse<Integer>  codepay(HttpServletRequest request,@RequestBody PayOrder payOrder) {
		log.info(CommonUtil.format("start view codepay api params:%s", JsonUtil.toJSONString(payOrder)));
		try {
//			PayOrder payOrder=null;
//			String  json=KeysUtil.getDesString(obj);
//			payOrder=JsonUtil.parseObject(json, PayOrder.class);
			ServerResponse<Integer> serverResponse=walletPayOrderService.codepay(payOrder);
			log.info("serverResponse %s",JsonUtil.toJSONString(serverResponse));
			return serverResponse;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view codepay api fail：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
	
}
