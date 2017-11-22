package com.gt.wallet.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.wallet.base.BaseController;
import com.gt.wallet.entity.WalletMember;

import io.swagger.annotations.Api;

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
@RequestMapping("//walletCompany")
public class WalletCompanyController extends BaseController {
	
	
	/**
	 * 保存信息
	 * @return
	 */
	@ResponseBody
	public String saveCompany(WalletMember walletMember){
		
		return null;
	}
	
	
	
	/**
	 * 进入会员编辑页面
	 * @return
	 */
	public String editCompany(Integer id){
		
		return null;
	}
	
}
