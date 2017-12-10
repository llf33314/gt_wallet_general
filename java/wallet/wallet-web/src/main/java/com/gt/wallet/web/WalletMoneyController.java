package com.gt.wallet.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
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
@RequestMapping("//walletMoney")
@Api(value = "walletMember",description="提现记录")
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
        @ApiImplicitParam(name = "current",value = "当前页",paramType = "form",dataType = "int",required=true,defaultValue="1"),
        @ApiImplicitParam(name = "total",value = "总条数",paramType = "form",dataType = "int",required=false,defaultValue="0"),
        @ApiImplicitParam(name = "size",value = "显示行数",paramType = "form",dataType = "int",required=false,defaultValue="10"),
        @ApiImplicitParam(name = "wmemberId",value = "钱包会员id",paramType = "form",dataType = "int",required=true),
        @ApiImplicitParam(name = "startTime",value = "开始时间",paramType = "form",dataType = "date",required=false),
        @ApiImplicitParam(name = "endTime",value = "结束时间",paramType = "form",dataType = "date",required=false),
        // path, query, body, header, form
	})
	public ServerResponse<MyPageUtil<WalletMoney>> getPage(HttpServletRequest request,Page<?> page,SearchPayOrderPage searchPayOrderPage){
		log.info(CommonUtil.format("触发分页查询接口 %s",JsonUtil.toJSONString(page)));
		try {
			ServerResponse<MyPageUtil<WalletMoney>> serverResponse=walletMoneyService.getPage(page,searchPayOrderPage);
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
	
	
	/**
	 * 提现
	 * 
	 * @return
	 */
	@RequestMapping(value = "/withdrawApply", method = RequestMethod.POST)
	@ApiOperation(value = "支付下单", notes = "支付下单")
	public String withdrawApply(HttpServletRequest request,@ApiParam(required=true,name="money" ,value="钱包会员id")double money,@ApiParam(required=true,name="bankId" ,value="银行卡id")Integer bankId) {
		log.info(CommonUtil.format("applyDeposit api,%s,%s", JsonUtil.toJSONString(money),bankId));
		try {
			BusUser busUser=	CommonUtil.getLoginUser(request);
			ServerResponse<?> serverResponse=walletMoneyService.withdrawApply(busUser.getId(), money, bankId);
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
	 * 获取余额(提现页面展示)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getPage",method=RequestMethod.POST)
	 @ApiOperation(value="getPage", notes="获取余额(提现页面展示)")
	public ServerResponse<Double> getPage(HttpServletRequest request,@ApiParam(required=true,name="wMemberId" ,value="钱包会员id")Integer wMemberId){
		log.info(CommonUtil.format("触发获取余额(提现页面展示)接口 %s",JsonUtil.toJSONString(wMemberId)));
		try {
			ServerResponse<IndexStatistics> serverResponse=walletMoneyService.getTotal(wMemberId);
			log.info(CommonUtil.format("serverResponse:", JsonUtil.toJSONString(serverResponse)));
			Double yue=0.0;
			if(CommonUtil.isNotEmpty(serverResponse.getData())){
				yue=serverResponse.getData().getBalance();
			}
			return ServerResponse.createBySuccessCodeData(yue);
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("获取余额(提现页面展示)接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("获取余额(提现页面展示)接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
}
