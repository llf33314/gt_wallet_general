package com.gt.wallet.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletCompanyService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 企业会员明细 前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@RestController  
@Api(value = "walletCompany")
@RequestMapping("/walletCompany")
@Slf4j
public class WalletCompanyController extends BaseController {
	
	@Autowired
	private WalletCompanyService walletCompanyService;
	
	
//	/**
//	 * 新增个人会员信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="saveIndividual",method=RequestMethod.POST)
//	 @ApiOperation(value="新增个人会员信息", notes="新增个人会员信息(data:银行卡表主键id)")
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "memberId",value = "会员id(调用开通会员接口有返回)",paramType = "form",dataType = "int",required=true,example="调用开通会员接口有返回"),
//        @ApiImplicitParam(name = "name",value = "注册人姓名",paramType = "form",dataType = "string",required=true,example="张三")
//        ,
//        @ApiImplicitParam(name = "identityNo",value = "身份证号码",paramType = "form",dataType = "string",required=true,example="44*************10")
//        ,
//        @ApiImplicitParam(name = "cardNo",value = "银行卡号",paramType = "form",dataType = "string",required=true,example="1234567891234567")
//        ,
//        @ApiImplicitParam(name = "phone",value = "银行预留手机",paramType = "form",dataType = "string",required=true,example="13888888888")
//        ,
//        @ApiImplicitParam(name = "bankName",value = "银行卡开户人姓名(必须与注册人姓名一致)",paramType = "form",dataType = "string",required=true),
//        @ApiImplicitParam(name = "unionBank",value = "支付行号",paramType = "form",dataType = "string",required=false)
////        ,
////        @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "form",dataType = "string",required=true)
//        // path, query, body, header, form
//	})
//	public ServerResponse<Integer> saveIndividual(HttpServletRequest request,WalletIndividualAdd walletIndividualAdd,
//			@ApiParam(required=true,name="identitycardUrl1File" ,value="身份证正面")@RequestParam("identitycardUrl1File") MultipartFile identitycardUrl1File,
//			@ApiParam(required=true,name="identitycardUrl2File" ,value="身份证反面") @RequestParam("identitycardUrl2File") MultipartFile identitycardUrl2File){
//		log.info(CommonUtil.format("触发保存个人会员信息接口,walletIndividualAdd:%s",JsonUtil.toJSONString(walletIndividualAdd)));
//		try {
//			ServerResponse<Integer> serverResponse=null;
//			serverResponse=walletIndividualService.add(walletIndividualAdd,identitycardUrl1File,identitycardUrl2File,CommonUtil.getLoginUser(request));
//			if(serverResponse.getCode()==0){
//				serverResponse=walletBankService.add(walletIndividualAdd);
//			}
//			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
//			return serverResponse;
//			} catch ( BusinessException e) {
//				throw new ResponseEntityException(e.getCode(),e.getMessage());
//			} catch ( Exception e) {
//				e.printStackTrace();
//				log.error(CommonUtil.format("钱包设置(个人会员)接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
//				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
//			}
//	}
//	
}
