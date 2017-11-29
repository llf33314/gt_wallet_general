package com.gt.wallet.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
import com.gt.wallet.data.wallet.request.WalletSet;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.service.member.WalletIndividualService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 个人会员 前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@RestController
@RequestMapping("/walletIndividual")
@Api(value = "walletIndividual",description="个人会员")
@Slf4j
public class WalletIndividualController extends BaseController {
	
	
	@Autowired
	private WalletIndividualService walletIndividualService;
	
	@Autowired
	private WalletBankService walletBankService;
	
	/**
	 * 新增个人会员信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveIndividual",method=RequestMethod.POST)
	 @ApiOperation(value="新增个人会员信息", notes="新增个人会员信息(data:银行卡表主键id)")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "memberId",value = "会员id(调用开通会员接口有返回)",paramType = "form",dataType = "int",required=true,example="调用开通会员接口有返回"),
        @ApiImplicitParam(name = "name",value = "注册人姓名",paramType = "form",dataType = "string",required=true,example="张三")
        ,
        @ApiImplicitParam(name = "identityNo",value = "身份证号码",paramType = "form",dataType = "string",required=true,example="44*************10")
        ,
        @ApiImplicitParam(name = "cardNo",value = "银行卡号",paramType = "form",dataType = "string",required=true,example="1234567891234567")
        ,
        @ApiImplicitParam(name = "phone",value = "银行预留手机",paramType = "form",dataType = "string",required=true,example="13888888888")
        ,
        @ApiImplicitParam(name = "bankName",value = "银行卡开户人姓名(必须与注册人姓名一致)",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "unionBank",value = "支付行号",paramType = "form",dataType = "string",required=false),
        @ApiImplicitParam(required=true,name="identitycardUrl1File" ,value="身份证正面",paramType = "form",dataType = "string"),
        @ApiImplicitParam(required=true,name="identitycardUrl2File" ,value="身份证反面",paramType = "form",dataType = "string") 
//        ,
//        @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "form",dataType = "string",required=true)
        // path, query, body, header, form
	})
	public ServerResponse<Integer> saveIndividual(HttpServletRequest request,@RequestBody WalletIndividualAdd walletIndividualAdd
			){
		log.info(CommonUtil.format("触发保存个人会员信息接口,walletIndividualAdd:%s",JsonUtil.toJSONString(walletIndividualAdd)));
		try {
			ServerResponse<Integer> serverResponse=null;
			serverResponse=walletIndividualService.add(walletIndividualAdd,CommonUtil.getLoginUser(request));
			if(serverResponse.getCode()==0){
				
				serverResponse=walletBankService.add(walletIndividualAdd);
			}
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("保存个人会员信息接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	
	/**
	 * 钱包设置(个人会员)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="set",method=RequestMethod.POST)
	 @ApiOperation(value="钱包设置(个人会员)", notes="钱包设置(个人会员)")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "wmemberId",value = "多粉钱包会员id",paramType = "form",dataType = "int",required=true,example="多粉钱包会员id",defaultValue="123456"),
        @ApiImplicitParam(name = "pwd",value = "密码(6位数字)",paramType = "form",dataType = "int",required=true,example="密码",defaultValue="123456"),
        @ApiImplicitParam(name = "confirm",value = "确认密码",paramType = "form",dataType = "string",required=true,example="确认密码",defaultValue="123456")
        ,
        @ApiImplicitParam(name = "phone",value = "手机号码",paramType = "form",dataType = "string",required=true,example="手机号码",defaultValue="13888888888")
        ,
        @ApiImplicitParam(name = "code",value = "短信验证码(开发默认8888)",paramType = "form",dataType = "string",required=true,defaultValue="8888")
        // path, query, body, header, form
	})
	public ServerResponse<?> set(HttpServletRequest request,WalletSet walletSet
		){
		log.info(CommonUtil.format("触发钱包设置(个人会员)接口,walletSet:%s",JsonUtil.toJSONString(walletSet)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletIndividualService.set(walletSet,CommonUtil.getLoginUser(request));
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("钱包设置(个人会员)接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("钱包设置(个人会员)接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	} 
	
	
	
	
	 
}
