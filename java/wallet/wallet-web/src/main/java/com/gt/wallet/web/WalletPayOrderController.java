package com.gt.wallet.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.wallet.base.BaseController;

import io.swagger.annotations.Api;

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
@RequestMapping("//walletPayOrder")
public class WalletPayOrderController extends BaseController {
	
}
