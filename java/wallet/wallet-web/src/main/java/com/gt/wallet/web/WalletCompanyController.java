package com.gt.wallet.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.CompanyUploadFile;
import com.gt.wallet.data.wallet.request.WalletCompanyAdd;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletCompany;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.service.member.WalletCompanyService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "walletCompany",description="企业会员明细 ")
@RequestMapping("/walletCompany")
@Slf4j
public class WalletCompanyController extends BaseController {
	
	@Autowired
	private WalletCompanyService walletCompanyService;
	
	@Autowired
	private WalletBankService walletBankService;
	
	
	/**
	 * 新增企业会员信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="save",method=RequestMethod.POST)
	 @ApiOperation(value="新增企业会员信息", notes="新增企业会员信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "memberId",value = "会员id(调用开通会员接口有返回)",paramType = "form",dataType = "int",required=true,example="调用开通会员接口有返回"),
        @ApiImplicitParam(name = "companyName",value = "公司名称",paramType = "form",dataType = "string",required=true,example="张三")
        ,
        @ApiImplicitParam(name = "companyAddress",value = "企业地址",paramType = "form",dataType = "string",required=true,example="企业地址")
        ,
        @ApiImplicitParam(name = "businessLicense",value = "营业执照号",paramType = "form",dataType = "string",required=true,example="营业执照号")
        ,
        @ApiImplicitParam(name = "telephone",value = "联系电话",paramType = "form",dataType = "string",required=false,example="13888888888")
        ,
        @ApiImplicitParam(name = "legalName",value = "法人姓名",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "legalPhone",value = "法人证件号码",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "province",value = "省份(code)",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "area",value = "县市(code)",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "accountNo",value = "对公账号",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "legalIds",value = "法人证件号码",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "parentBankName",value = "开户银行名称",paramType = "form",dataType = "string",required=false),
        @ApiImplicitParam(name = "bankName",value = "开户行支行名称",paramType = "form",dataType = "string",required=false),
        @ApiImplicitParam(name = "unionBank",value = "支付行号",paramType = "form",dataType = "string",required=true),
//        @ApiImplicitParam(name = "doBusinessUrl",value = "营业执照url",paramType = "form",dataType = "string",required=true),
//        @ApiImplicitParam(name = "identitycardUrl1",value = "身份证正面",paramType = "form",dataType = "string",required=true),
//        @ApiImplicitParam(name = "identitycardUrl2",value = "身份证反面",paramType = "form",dataType = "string",required=true),
//        @ApiImplicitParam(name = "licenseUrl",value = "开户许可证url",paramType = "form",dataType = "string",required=true),
//        ,
//        @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "form",dataType = "string",required=true)
        // path, query, body, header, form
	})
	public ServerResponse<?> save(HttpServletRequest request,WalletCompanyAdd walletCompanyAdd){
		log.info(CommonUtil.format("触发新增企业会员信息接口,walletCompanyAdd:%s",JsonUtil.toJSONString(walletCompanyAdd)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletCompanyService.save(walletCompanyAdd,CommonUtil.getLoginUser(request));
			if(serverResponse.getCode()==0){
				serverResponse=walletBankService.addPublic(walletCompanyAdd);
			}
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("新增企业会员信息：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	
	/**
	 * 上传文件证件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="uploadFile",method=RequestMethod.POST)
	 @ApiOperation(value="上传文件证件", notes="新增企业会员信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "memberId",value = "会员id(调用开通会员接口有返回)",paramType = "form",dataType = "int",required=true),
        @ApiImplicitParam(name = "doBusinessUrl",value = "营业执照url",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "identitycardUrl1",value = "身份证正面url",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "identitycardUrl2",value = "身份证反面url",paramType = "form",dataType = "string",required=true),
        @ApiImplicitParam(name = "licenseUrl",value = "开户许可证url",paramType = "form",dataType = "string",required=true),
//        ,
//        @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "form",dataType = "string",required=true)
        // path, query, body, header, form
	})
	public ServerResponse<?> uploadFile(HttpServletRequest request,CompanyUploadFile companyUploadFile){
		log.info(CommonUtil.format("uploadFile api ,companyUploadFile:%s",JsonUtil.toJSONString(companyUploadFile)));
		try {
			ServerResponse<?> serverResponse=walletCompanyService.uploadFile(companyUploadFile,CommonUtil.getLoginUser(request));
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				throw new ResponseEntityException(e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("uploadFile api：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
}
