package com.gt.wallet.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
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
@RestController  
@Api(value = "walletPayOrder")
@RequestMapping("/walletPayOrder")
@Slf4j
public class WalletPayOrderController extends BaseController {
	
	/**
	 * 支付成功异步回调
	 * @return
	 */
	@RequestMapping(value="/79B4DE7C/paySuccessNotify",method=RequestMethod.POST)
	 @ApiOperation(value="支付成功异步回调", notes="支付成功异步回调")
	public void paySuccessNotify(HttpServletRequest request,@RequestParam Map<String, Object> params){
		log.info(CommonUtil.format("支付成功异步回调,%s",JsonUtil.toJSONString(params)));
		try {
//			BusUser busUser=CommonUtil.getLoginUser(request);
//			ServerResponse<WalletMember> serverResponse=null;
//			ServerResponse<List<WalletMember>> temp=walletMemberService.findMember(busUser.getId());
//			if(CommonUtil.isNotEmpty(temp)&&temp.getCode()==0&&CommonUtil.isNotEmpty(temp.getData())&&temp.getData().size()==1){
//				serverResponse=ServerResponse.createBySuccessCodeData(temp.getData().get(0));
//				return serverResponse;
//			}else{
//				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
//			}
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("支付成功异步回调异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("支付成功异步回调接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	
	/**
	 * 提现成功异步回调
	 * @return
	 */
	@RequestMapping(value="/79B4DE7C/withdrawSuccessNotify",method=RequestMethod.POST)
	@ApiOperation(value="提现成功异步回调", notes="提现成功异步回调")
	public void withdrawSuccessNotify(HttpServletRequest request,@RequestParam Map<String, Object> params){
		log.info(CommonUtil.format("支付成功异步回调,%s",JsonUtil.toJSONString(params)));
		try {
//			BusUser busUser=CommonUtil.getLoginUser(request);
//			ServerResponse<WalletMember> serverResponse=null;
//			ServerResponse<List<WalletMember>> temp=walletMemberService.findMember(busUser.getId());
//			if(CommonUtil.isNotEmpty(temp)&&temp.getCode()==0&&CommonUtil.isNotEmpty(temp.getData())&&temp.getData().size()==1){
//				serverResponse=ServerResponse.createBySuccessCodeData(temp.getData().get(0));
//				return serverResponse;
//			}else{
//				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
//			}
		} catch ( BusinessException e) {
			log.error(CommonUtil.format("提现成功异步回调异常：%s,%s",e.getCode(),e.getMessage()));
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("提现成功异步回调接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
	
	
	/**
	 * 退款成功异步回调
	 * @return
	 */
	@RequestMapping(value="/79B4DE7C/refundSuccessNotify",method=RequestMethod.POST)
	@ApiOperation(value="退款成功异步回调", notes="退款成功异步回调")
	public void refundSuccessNotify(HttpServletRequest request,@RequestParam Map<String, Object> params){
		log.info(CommonUtil.format("支付成功异步回调,%s",JsonUtil.toJSONString(params)));
		try {
//			BusUser busUser=CommonUtil.getLoginUser(request);
//			ServerResponse<WalletMember> serverResponse=null;
//			ServerResponse<List<WalletMember>> temp=walletMemberService.findMember(busUser.getId());
//			if(CommonUtil.isNotEmpty(temp)&&temp.getCode()==0&&CommonUtil.isNotEmpty(temp.getData())&&temp.getData().size()==1){
//				serverResponse=ServerResponse.createBySuccessCodeData(temp.getData().get(0));
//				return serverResponse;
//			}else{
//				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
//			}
		} catch ( BusinessException e) {
			log.error(CommonUtil.format("退款成功异步回调异常：%s,%s",e.getCode(),e.getMessage()));
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("退款成功异步回调异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
	
	
}
