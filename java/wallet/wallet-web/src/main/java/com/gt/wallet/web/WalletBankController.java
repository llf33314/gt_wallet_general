package com.gt.wallet.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.bean.session.BusUser;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletBank;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.utils.BankUtil;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.WalletKeyUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@RestController("/walletBank")  
@Api(value = "walletBank",description="银行卡")
@Slf4j
public class WalletBankController extends BaseController {
	
	@Autowired
	private WalletBankService walletBankService;
	
	
	/**
	 * 获取会员银行卡列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getWalletBanksByMemberId",method=RequestMethod.GET)
	 @ApiOperation(value="获取会员银行卡列表", notes="获取会员银行卡列表")
	public ServerResponse<List<WalletBank>> getWalletBanksByMemberId(HttpServletRequest request,Integer wmemberId){
		log.info(CommonUtil.format("触发获取会员银行卡列表接口"));
		try {
				ServerResponse<List<WalletBank>> serverResponse=walletBankService.getWalletBanksByMemberId(wmemberId);
				
				for (WalletBank walletBank:serverResponse.getData()) {
					walletBank.setCardNo(BankUtil.hide(WalletKeyUtil.getDesString(walletBank.getCardNo())));
				}
				log.info("serverResponse:%s",JsonUtil.toJSONString(serverResponse));
				return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("获取会员银行卡列表接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("获取会员银行卡列表接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	/**
	 * 确认绑定银行卡
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="bindBankCard",method=RequestMethod.POST)
	 @ApiOperation(value="确认绑定银行卡", notes="确认绑定银行卡")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "银行卡主键",paramType = "form",dataType = "int",required=true,example="银行卡主键"),
        @ApiImplicitParam(name = "id",value = "verificationCode",paramType = "form",dataType = "int",required=true,example="verificationCode")
        // path, query, body, header, form
	})
	public ServerResponse<?> bindBankCard(HttpServletRequest request,Integer id, String verificationCode 
		){
		log.info(CommonUtil.format("触发确认绑定银行卡接口,参数:%s%s",id,verificationCode));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<?> serverResponse=null;
			serverResponse=walletBankService.bindBankCard(busUser.getId(),id, verificationCode);//YunSoaMemberUtil.sendVerificationCode(bizUserId, phone, verificationCodeType);
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("确认绑定银行卡接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("确认绑定银行卡接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	} 
	
	
}
