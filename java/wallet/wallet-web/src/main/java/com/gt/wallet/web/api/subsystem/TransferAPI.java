package com.gt.wallet.web.api.subsystem;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.wallet.base.BaseController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月30日 下午7:53:16 
* 类说明 
*/


@RequestMapping("8A5DA52E/transferApi/")
@Slf4j
@RestController
@Api(value = "transferApi",description="转账")  
public class TransferAPI extends BaseController{

}
