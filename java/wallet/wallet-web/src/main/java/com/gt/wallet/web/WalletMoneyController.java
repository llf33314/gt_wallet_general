package com.gt.wallet.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.wallet.base.BaseController;

import io.swagger.annotations.Api;

/**
 * <p>
 * 提现记录表 前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@RestController  
@Api(value = "walletMoney")
@RequestMapping("//walletMoney")
public class WalletMoneyController extends BaseController {
	
}
