package com.gt.wallet.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.bean.session.BusUser;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.api.tonglian.TCardBin;
import com.gt.wallet.data.wallet.request.WalletBankAdd;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
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
import io.swagger.annotations.ApiParam;
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
		log.info(CommonUtil.format("触发getWalletBanksByMemberId api"));
		try {
				ServerResponse<List<WalletBank>> serverResponse=walletBankService.getWalletBanksByMemberId(wmemberId);
				
				for (WalletBank walletBank:serverResponse.getData()) {
					walletBank.setCardNo(BankUtil.hide(WalletKeyUtil.getDesString(walletBank.getCardNo())));
				}
				log.info("serverResponse:%s",JsonUtil.toJSONString(serverResponse));
				return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("getWalletBanksByMemberId api异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("getWalletBanksByMemberId api异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
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
        @ApiImplicitParam(name = "verificationCode",value = "验证码",paramType = "form",dataType = "int",required=true,example="verificationCode")
        // path, query, body, header, form
	})
	public ServerResponse<?> bindBankCard(HttpServletRequest request,Integer id, String verificationCode 
		){
		log.info(CommonUtil.format("触发bindBankCard api,参数:%s%s",id,verificationCode));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<?> serverResponse=null;
			serverResponse=walletBankService.bindBankCard(busUser.getId(),id, verificationCode);//YunSoaMemberUtil.sendVerificationCode(bizUserId, phone, verificationCodeType);
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("bindBankCard api异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("bindBankCard api异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	} 
	
	
	
	/**
	 * 新增个人银行卡
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addBank",method=RequestMethod.POST)
	 @ApiOperation(value="新增个人银行卡", notes="新增个人银行卡")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "memberId",value = "会员id(调用开通会员接口有返回)",paramType = "form",dataType = "int",required=true,example="调用开通会员接口有返回"),
        @ApiImplicitParam(name = "cardNo",value = "银行卡号",paramType = "form",dataType = "string",required=true,example="1234567891234567")
        ,
        @ApiImplicitParam(name = "phone",value = "银行预留手机",paramType = "form",dataType = "string",required=true,example="13888888888")
        ,
        @ApiImplicitParam(name = "bankName",value = "银行卡开户人姓名(必须与注册人姓名一致)",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "unionBank",value = "支付行号",paramType = "form",dataType = "string",required=false),
        @ApiImplicitParam(name = "isSafeCard",value = "是否为安全卡 0：是 1：否",paramType = "form",dataType = "string",required=false)
//        ,
//        @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "form",dataType = "string",required=true)
        // path, query, body, header, form
	})
	public ServerResponse<Integer> addBank(HttpServletRequest request,@RequestBody WalletBankAdd walletBankAdd
			){
		log.info(CommonUtil.format("触发addBank api:%s",JsonUtil.toJSONString(walletBankAdd)));
		try {
			ServerResponse<Integer> serverResponse=null;
			WalletIndividualAdd walletIndividualAdd=new WalletIndividualAdd();
			walletIndividualAdd.setMemberId(walletBankAdd.getMemberId());
			walletIndividualAdd.setBankName(walletBankAdd.getBankName());
			walletIndividualAdd.setCardNo(walletBankAdd.getCardNo());
			walletIndividualAdd.setPhone(walletBankAdd.getPhone());
			walletIndividualAdd.setUnionBank(walletBankAdd.getUnionBank());
			serverResponse=walletBankService.add(walletIndividualAdd,walletBankAdd.getIsSafeCard());
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("addBank api异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	/**
	 * 查询银行卡bin信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getBankCardBin",method=RequestMethod.POST)
	 @ApiOperation(value="查询银行卡bin信息", notes="查询银行卡bin信息")
	
	public ServerResponse<TCardBin> getBankCardBin(HttpServletRequest request,@ApiParam(required=true,name="bankCardNo" ,value="银行卡号") @RequestParam(required=true) String bankCardNo
			){
		log.info(CommonUtil.format("触发getBankCardBin api:%s",bankCardNo));
		try {
			ServerResponse<TCardBin> serverResponse=null;
			serverResponse=walletBankService.getBankCardBin(bankCardNo);
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("getBankCardBin api异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
}
