package com.gt.wallet.web.wallet;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
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
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 个人会员 前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Controller
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
	@PostMapping(value="saveIndividual")
	 @ApiOperation(value="新增个人会员信息", notes="新增个人会员信息(data:银行卡表主键id)")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "memberId",value = "会员id(调用开通会员接口有返回)",paramType = "form",dataType = "int",required=true,example="调用开通会员接口有返回"),
        @ApiImplicitParam(name = "name",value = "注册人姓名",paramType = "form",dataType = "string",required=true,example="张三")
        ,
        @ApiImplicitParam(name = "identityNo",value = "身份证号码",paramType = "query",dataType = "string",required=true,example="44*************10")
        ,
        @ApiImplicitParam(name = "cardNo",value = "银行卡号",paramType = "query",dataType = "string",required=true,example="1234567891234567")
        ,
        @ApiImplicitParam(name = "phone",value = "银行预留手机",paramType = "query",dataType = "string",required=true,example="13888888888")
        ,
        @ApiImplicitParam(name = "bankName",value = "银行卡开户人姓名(必须与注册人姓名一致)",paramType = "query",dataType = "string",required=true),
        @ApiImplicitParam(name = "unionBank",value = "支付行号",paramType = "form",dataType = "string",required=false),
        @ApiImplicitParam(required=true,name="identitycardUrl1File" ,value="身份证正面",paramType = "query",dataType = "string"),
        @ApiImplicitParam(required=true,name="identitycardUrl2File" ,value="身份证反面",paramType = "query",dataType = "string") 
//        ,
//        @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "form",dataType = "string",required=true)
        // path, query, body, header, form
	})
	public ServerResponse<Integer> saveIndividual(HttpServletRequest request,   WalletIndividualAdd walletIndividualAdd
			){
		log.info(CommonUtil.format("start view saveIndividual api,params:%s",JsonUtil.toJSONString(walletIndividualAdd)));
		try {
			ServerResponse<Integer> serverResponse=null;
			serverResponse=walletIndividualService.add(walletIndividualAdd,CommonUtil.getLoginUser(request));
			if(serverResponse.getCode()==0||serverResponse.getMsg().equals("当前步骤信息禁止修改")){
				
				serverResponse=walletBankService.add(walletIndividualAdd,0);
			}
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view saveIndividual api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view saveIndividual api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	 
}
