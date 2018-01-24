package com.gt.wallet.web.api.subsystem;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.dto.ResponseUtils;
import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
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
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月8日 上午10:47:39 
* 类说明 
*/
@RequestMapping("8A5DA52E/orderApi/")
@Slf4j
@RestController
@Api(value = "orderApi",description="订单api")  
public class OrderAPI extends BaseController{
	@Autowired
	private WalletPayOrderService walletPayOrderService;
	/**
	 * 退款api
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/refund", method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE, consumes="application/json")
	 @ApiOperation(value="退款api", notes="退款api",consumes="application/json", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,response=ServerResponse.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "busId",value = "商家id",paramType = "form",dataType = "int",required=true,example="35"),
        @ApiImplicitParam(name = "bizOrderNo",value = "商户退款订单号",paramType = "form",dataType = "string",required=true,example="cy123456789")
        ,
        @ApiImplicitParam(name = "oriBizOrderNo",value = "商户原订单号",paramType = "form",dataType = "string",required=true)
        ,
        @ApiImplicitParam(name = "backUrl",value = "后台通知地址",paramType = "form",dataType = "string",required=true,defaultValue="http://duofriend.com")
        ,
        @ApiImplicitParam(name = "amount",value = "订单金额",paramType = "form",dataType = "double",required=true,defaultValue="1")
        ,
       })
	public ServerResponse<?> refund(HttpServletRequest request,@RequestBody RequestUtils<TRefundOrder> requestUtils ){
		log.info(CommonUtil.format("start view refund api params:%s",JsonUtil.toJSONString(requestUtils)));
		try {
			super.verification(request, requestUtils);
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
	@RequestMapping(value = "/79B4DE7C/codepay", method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE, consumes="application/json")
	@ApiOperation(value = "刷卡支付", notes = "刷卡支付",consumes="application/json", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,response=ServerResponse.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "busId",value = "商家id",paramType = "form",dataType = "int",required=true,example="35"),
        @ApiImplicitParam(name = "bizOrderNo",value = "系统订单号",paramType = "form",dataType = "string",required=true,example="cy123456789")
        ,
        @ApiImplicitParam(name = "acct",value = "支付授权码",paramType = "form",dataType = "string",required=true)
        ,
        @ApiImplicitParam(name = "notifyUrl",value = "后台通知地址",paramType = "form",dataType = "string",required=true,defaultValue="http://duofriend.com")
        ,
        @ApiImplicitParam(name = "type",value = "支付方式6：微信 ,7:支付宝",paramType = "form",dataType = "int",required=true,defaultValue="1")
        ,
        @ApiImplicitParam(name = "desc",value = "描述",paramType = "form",dataType = "string",required=true,defaultValue="描述")
        ,
        @ApiImplicitParam(name = "takeState",value = "是否可立即提现(1:可取 2:不可取)",paramType = "form",dataType = "string",required=true,defaultValue="1")
        ,
        @ApiImplicitParam(name = "model",value = "支付模块",paramType = "form",dataType = "string",required=true,defaultValue="1")
        ,
       })
//	@ApiResponses()
	public ServerResponse<Integer>  codepay(HttpServletRequest request,@RequestBody RequestUtils<PayOrder> requestUtils) {
		log.info(CommonUtil.format("start view codepay api params:%s", JsonUtil.toJSONString(requestUtils)));
		try {
			super.verification(request, requestUtils);
			ServerResponse<Integer> serverResponse=walletPayOrderService.codepay(requestUtils.getReqdata());
			log.info("serverResponse %s",JsonUtil.toJSONString(serverResponse));
			return serverResponse;
		} catch (BusinessException e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view codepay api fail：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new BusinessException(e.getCode(),e.getMessage());
		}  catch (Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view codepay api fail：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
	
}
