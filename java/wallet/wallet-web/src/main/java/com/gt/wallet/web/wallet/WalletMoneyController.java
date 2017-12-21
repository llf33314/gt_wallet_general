package com.gt.wallet.web.wallet;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.http.util.IPAddress;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.api.util.IPOrAddressUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.SearchPayOrderPage;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMoney;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.order.WalletMoneyService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.MyPageUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 提现记录表 前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@RestController  
@RequestMapping("/walletMoney")
@Api(value = "walletMoney",description="提现")
@Slf4j
public class WalletMoneyController extends BaseController {
	
	@Autowired
	private WalletMoneyService walletMoneyService;
	
	/**
	 * 分页查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getPage",method=RequestMethod.POST)
	 @ApiOperation(value="分页查询", notes="分页查询")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "current",value = "当前页",paramType = "form",dataType = "Integer",required=true,defaultValue="1"),
        @ApiImplicitParam(name = "size",value = "显示行数",paramType = "form",dataType = "Integer",required=false,defaultValue="10"),
        @ApiImplicitParam(name = "wmemberId",value = "钱包会员id",paramType = "form",dataType = "int",required=true),
        @ApiImplicitParam(name = "startTime",value = "开始时间",paramType = "form",dataType = "string",required=false),
        @ApiImplicitParam(name = "endTime",value = "结束时间",paramType = "form",dataType = "string",required=false),
        // path, query, body, header, form
	})
	public ServerResponse<MyPageUtil<WalletMoney>> getPage(HttpServletRequest request, SearchPayOrderPage searchPayOrderPage){
		log.info(CommonUtil.format("start view getPage api params:%s",JsonUtil.toJSONString(searchPayOrderPage)));
		try {
			Page<?> page=new Page<>();
			page.setSize(searchPayOrderPage.getSize());
			page.setCurrent(searchPayOrderPage.getCurrent());
			ServerResponse<MyPageUtil<WalletMoney>> serverResponse=walletMoneyService.getPage(page,searchPayOrderPage);
			log.info(CommonUtil.format("serverResponse:", JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view getPage api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view getPage api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	/**
	 * 提现
	 * 
	 * @return
	 */
	@RequestMapping(value = "/withdrawApply", method = RequestMethod.POST)
	@ApiOperation(value = "withdrawApply", notes = "提现(成功后会返回订单id),支付确认时需要传递")
	public String withdrawApply(HttpServletRequest request,@ApiParam(required=true,name="money" ,value="提现金额")@RequestParam double money,@ApiParam(required=true,name="bankId" ,value="银行卡id")@RequestParam Integer bankId) {
		log.info(CommonUtil.format("start view applyDeposit api params:%s,%s", JsonUtil.toJSONString(money),bankId));
		try {
			BusUser busUser=	CommonUtil.getLoginUser(request);
			ServerResponse<?> serverResponse=walletMoneyService.withdrawApply(busUser.getId(), money, bankId);
			log.info("serverResponse %s",JsonUtil.toJSONString(serverResponse));
			request.setAttribute("serverResponse", serverResponse);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view applyDeposit api fail：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
		return "";
	}
	
	
	/**
	 * 提现确认
	 * 
	 * @return
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	@ApiOperation(value = "confirm", notes = "提现确认")
	public String confirm(HttpServletRequest request,@ApiParam(required=true,name="id" ,value="订单id")Integer id,@ApiParam(required=true,name="verificationCode" ,value="验证码")String verificationCode) {
		log.info(CommonUtil.format("start view confirm api params:%s,%s", JsonUtil.toJSONString(id),verificationCode));
		try {
			BusUser busUser=	CommonUtil.getLoginUser(request);
			String ip=IPOrAddressUtils.getIpAddr(request);
			ServerResponse<?> serverResponse=walletMoneyService.confirm(busUser.getId(), id, verificationCode,ip);
			log.info("serverResponse %s",JsonUtil.toJSONString(serverResponse));
			request.setAttribute("serverResponse", serverResponse);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view confirm api fail：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
		return "";
	}
	
	/**
	 * 获取余额(提现页面展示)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getTotal",method=RequestMethod.POST)
	 @ApiOperation(value="getTotal", notes="获取余额(提现页面展示)")
	public ServerResponse<Double> getTotal(HttpServletRequest request,@ApiParam(required=true,name="wMemberId" ,value="钱包会员id")Integer wMemberId){
		log.info(CommonUtil.format("start  view getTotal api params: %s",JsonUtil.toJSONString(wMemberId)));
		try {
			ServerResponse<IndexStatistics> serverResponse=walletMoneyService.getTotal(wMemberId);
			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
			Double yue=0.0;
			if(CommonUtil.isNotEmpty(serverResponse.getData())){
				yue=serverResponse.getData().getBalance();
			}
			return ServerResponse.createBySuccessCodeData(yue);
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view getTotal api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view getTotal api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	/**
	 * 提现成功异步回调
	 * 
	 * @return
	 */
	@RequestMapping(value = "/79B4DE7C/withdrawSuccessNotify", method = RequestMethod.POST)
	@ApiOperation(value = "提现成功异步回调", notes = "提现成功异步回调")
	public ServerResponse<?> withdrawSuccessNotify(HttpServletRequest request, @RequestParam JSONObject params) {
		log.info(CommonUtil.format("start view withdrawSuccessNotify api,params:%s", JsonUtil.toJSONString(params)));
		try {
			ServerResponse<?> serverResponse=walletMoneyService.withdrawSuccessNotify(params);
			return serverResponse;
		} catch (BusinessException e) {
			log.error(CommonUtil.format("view withdrawSuccessNotify api fail：%s,%s", e.getCode(), e.getMessage()));
			throw new ResponseEntityException(e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view withdrawSuccessNotify api fail：%s,%s", WalletResponseEnums.SYSTEM_ERROR.getCode(),
					WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
}
