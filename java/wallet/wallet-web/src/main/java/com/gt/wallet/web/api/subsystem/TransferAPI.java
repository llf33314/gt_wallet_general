package com.gt.wallet.web.api.subsystem;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.order.WalletTransferService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月30日 下午7:53:16 
* 类说明 
*/


@RequestMapping("8A5DA52E/transferApi/")
@Slf4j
@RestController
@Api(value = "transferApi",description="转账")  
@ApiIgnore
public class TransferAPI extends BaseController{
	
	@Autowired
	private WalletTransferService walletTransferService;
	
	/**
	 * 划账api
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/debit",method=RequestMethod.POST)
	 @ApiOperation(value="划账api", notes="reqdata：为商家id",produces="application/json")
	@ApiIgnore
	public ServerResponse<?> debit(HttpServletRequest request,@RequestBody RequestUtils<Integer> requestUtils ){
		log.info(CommonUtil.format("start view debit api params:%s",JsonUtil.toJSONString(requestUtils)));
		try {
			ServerResponse<?> serverResponse=walletTransferService.addDebit(requestUtils.getReqdata());
			log.info(CommonUtil.format("view debit api serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view debit api fail ：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view debit api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}

	
	/**
	 * 划账api(定时)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/debit",method=RequestMethod.GET)
	 @ApiOperation(value="划账api(定时)", notes="reqdata：为商家id",produces="application/json")
	@ApiIgnore
	public void taskDebit(HttpServletRequest request ){
		log.info(CommonUtil.format("start view debit api"));
		try {
			ServerResponse<?> serverResponse=walletTransferService.taskAddDebit();
			log.info(CommonUtil.format("view debit api serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view debit api fail ：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view debit api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
}
