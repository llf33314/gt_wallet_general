package com.gt.wallet.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.PayOrder;
import com.gt.wallet.data.wallet.request.SearchPayOrderPage;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletPayOrder;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.MyPageUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 钱包支付记录 前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Controller
@RequestMapping("/walletPayOrder")
@Api(value = "walletMember",description="多粉钱包订单")
@Slf4j
public class WalletPayOrderController extends BaseController {

	@Autowired
	private WalletPayOrderService walletPayOrderService;
	

	/**
	 * 支付成功异步回调
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/79B4DE7C/paySuccessNotify", method = RequestMethod.POST)
	@ApiOperation(value = "支付成功异步回调", notes = "支付成功异步回调")
	public void paySuccessNotify(HttpServletRequest request,HttpServletResponse response, @RequestParam JSONObject params) throws Exception {
		log.info(CommonUtil.format("支付成功异步回调,%s", JsonUtil.toJSONString(params)));
		try {
			ServerResponse<?> serverResponse=walletPayOrderService.paySuccessNotify(params);
			if(ServerResponse.judgeSuccess(serverResponse)){
				response.getWriter().print("success");
			}else{
				response.getWriter().print("error");
			}
		} catch (BusinessException e) {
			log.error(CommonUtil.format("支付成功异步回调异常：%s,%s", e.getCode(), e.getMessage()));
			response.getWriter().print("error");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("支付成功异步回调接口异常：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			response.getWriter().print("error");
		}
		response.getWriter().flush();
		response.getWriter().close();
	}


	/**
	 * 退款成功异步回调
	 * 
	 * @return
	 */
	@RequestMapping(value = "/79B4DE7C/refundSuccessNotify", method = RequestMethod.POST)
	@ApiOperation(value = "退款成功异步回调", notes = "退款成功异步回调")
	public void refundSuccessNotify(HttpServletRequest request, @RequestParam Map<String, Object> params) {
		log.info(CommonUtil.format("支付成功异步回调,%s", JsonUtil.toJSONString(params)));
		try {
			// BusUser busUser=CommonUtil.getLoginUser(request);
			// ServerResponse<WalletMember> serverResponse=null;
			// ServerResponse<List<WalletMember>>
			// temp=walletMemberService.findMember(busUser.getId());
			// if(CommonUtil.isNotEmpty(temp)&&temp.getCode()==0&&CommonUtil.isNotEmpty(temp.getData())&&temp.getData().size()==1){
			// serverResponse=ServerResponse.createBySuccessCodeData(temp.getData().get(0));
			// return serverResponse;
			// }else{
			// throw new
			// ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			// }
		} catch (BusinessException e) {
			log.error(CommonUtil.format("退款成功异步回调异常：%s,%s", e.getCode(), e.getMessage()));
			throw new ResponseEntityException(e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("退款成功异步回调异常：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}

	/**
	 * 支付下单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/79B4DE7C/applyDeposit", method = RequestMethod.POST)
	@ApiOperation(value = "支付下单", notes = "支付下单")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "busId",value = "商家id",paramType = "form",dataType = "int",required=true,example="35"),
        @ApiImplicitParam(name = "bizOrderNo",value = "系统订单号",paramType = "form",dataType = "string",required=true,example="cy123456789")
        ,
        @ApiImplicitParam(name = "acct",value = "openid或userid(支付宝)",paramType = "form",dataType = "string",required=true,example="openid或userid(支付宝)")
        ,
        @ApiImplicitParam(name = "frontUrl",value = "前台通知地址",paramType = "form",dataType = "string",required=true,defaultValue="http://duofriend.com")
        ,
        @ApiImplicitParam(name = "backUrl",value = "后台通知地址",paramType = "form",dataType = "string",required=true,defaultValue="http://duofriend.com")
        ,
        @ApiImplicitParam(name = "type",value = "支付方式 1：微信 2:支付宝",paramType = "form",dataType = "int",required=true,defaultValue="1")
        ,
        @ApiImplicitParam(name = "desc",value = "描述",paramType = "form",dataType = "string",required=true,defaultValue="描述")
        ,
        @ApiImplicitParam(name = "type",value = "支付方式 1：微信 2:支付宝",paramType = "form",dataType = "string",required=true,defaultValue="1")
       })
	public String applyDeposit(HttpServletRequest request, PayOrder payOrder) {
		log.info(CommonUtil.format("applyDeposit api,%s", JsonUtil.toJSONString(payOrder)));
		try {
			ServerResponse<com.alibaba.fastjson.JSONObject> serverResponse=walletPayOrderService.applyDeposit(payOrder);
			log.info("serverResponse %s",JsonUtil.toJSONString(serverResponse));
			request.setAttribute("serverResponse", serverResponse);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("applyDeposit api异常：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.SYSTEM_ERROR);
		}
		return "";
	}
	
	
	
	/**
	 * 分页查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getPage",method=RequestMethod.POST)
	 @ApiOperation(value="分页查询", notes="分页查询")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "current",value = "当前页",paramType = "form",dataType = "int",required=true,defaultValue="1"),
        @ApiImplicitParam(name = "total",value = "总条数",paramType = "form",dataType = "int",required=false,defaultValue="0"),
        @ApiImplicitParam(name = "size",value = "显示行数",paramType = "form",dataType = "int",required=false,defaultValue="10"),
        @ApiImplicitParam(name = "wmemberId",value = "钱包会员id",paramType = "form",dataType = "int",required=true),
        @ApiImplicitParam(name = "startTime",value = "开始时间",paramType = "form",dataType = "date",required=false),
        @ApiImplicitParam(name = "endTime",value = "结束时间",paramType = "form",dataType = "date",required=false),
        // path, query, body, header, form
	})
	public ServerResponse<MyPageUtil<WalletPayOrder>> getPage(HttpServletRequest request,Page<?> page,SearchPayOrderPage searchPayOrderPage){
		log.info(CommonUtil.format("触发分页查询接口 %s",JsonUtil.toJSONString(page)));
		try {
			ServerResponse<MyPageUtil<WalletPayOrder>> serverResponse=walletPayOrderService.getPage(page,searchPayOrderPage);
			log.info(CommonUtil.format("serverResponse:", JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("分页查询接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("分页查询接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
}
