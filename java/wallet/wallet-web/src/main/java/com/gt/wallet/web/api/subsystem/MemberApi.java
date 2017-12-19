package com.gt.wallet.web.api.subsystem;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月18日 上午11:32:51 
* 类说明 
*/
@RequestMapping("8A5DA52E/memberApi/6F6D9AD2/79B4DE7C/")
@Slf4j
@RestController
@Api(value = "memberApi",description="多粉会员对外api ")  
public class MemberApi {

	@Autowired
	private WalletMemberService walletMemberService; 
	
	/**
	 * 判断商家是否开通多粉钱包(提供给各erp系统调用)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/open",method=RequestMethod.POST)
	 @ApiOperation(value="判断商家是否开通多粉钱包(提供给各erp系统调用)", notes="reqdata：为商家id",produces="application/json")
	public ServerResponse<?> is0pen(HttpServletRequest request,RequestUtils<Integer> requestUtils ){
		log.info(CommonUtil.format("is0pen api params:%s",JsonUtil.toJSONString(requestUtils)));
		try {
			String ua = request.getHeader("user-agent")
					.toLowerCase();
			log.info("ua:"+ua);
			ServerResponse<?> serverResponse=walletMemberService.isOpen(requestUtils.getReqdata());
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("is0pen api error ：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("isOpen api error：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
}
