package com.gt.wallet.web.api.alipay;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.constant.alipay.AlipayServiceEnvConstants;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.WalletWebConfig;
import com.gt.wallet.utils.alipay.AlipayAPIClientFactory;
import com.gt.wallet.utils.alipay.RequestUtil;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lifengxi(gt_sky@qq.com)
 * @version 创建时间：2017年12月19日 上午10:36:36 类说明
 */
@Slf4j
@Controller
@RequestMapping("alipayAuth")
@Api(value="alipayAuth",description="支付宝授权 ",hidden=true)
public class AuthContrller {

	@RequestMapping(value="79B4DE7C/auth",method=RequestMethod.GET)
	public void auth(HttpServletRequest request, HttpServletResponse response,String obj)
			throws ServletException, IOException {
		log.info("start view auth api  params:%s"+obj);
		String redirect_uri=CommonUtil.urlEncode(WalletWebConfig.getDomain()+"/alipayAuth/79B4DE7C/redirect_uri");
		//scope 接口权限值，目前只支持auth_user和auth_base两个值
		String url= CommonUtil.format("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=%s&scope=%s&state=%s&redirect_uri=%s", AlipayServiceEnvConstants.APP_ID,"auth_base",obj,redirect_uri);
		 response.sendRedirect(url);
	}
	
	@RequestMapping(value="79B4DE7C/redirect_uri",method=RequestMethod.GET)
	public void  redirect_uri(HttpServletRequest request, HttpServletResponse response)throws Exception {
		// 1. 解析请求参数
		log.info(" start view redirect_uri api");
		Map<String, String> params = RequestUtil.getRequestParams(request);
		log.info("params:%s"+JsonUtil.toJSONString(params));
		// 2. 获得authCode
		String authCode = params.get("auth_code");

		try {
			// 3. 利用authCode获得authToken
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(authCode);
			oauthTokenRequest.setGrantType(AlipayServiceEnvConstants.GRANT_TYPE);
			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			log.info("oauthTokenResponse:%s"+JsonUtil.toJSONString(oauthTokenResponse));
			
//			// 成功获得authToken
//			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
//				
//				// 4. 利用authToken获取用户信息
//				AlipayUserInfoShareRequest userinfoShareRequest = new AlipayUserInfoShareRequest();
//				AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(userinfoShareRequest,
//						oauthTokenResponse.getAccessToken());
//				log.info("userinfoShareResponse:%s"+JsonUtil.toJSONString(userinfoShareResponse));
//				// 成功获得用户信息
//				if (null != userinfoShareResponse && userinfoShareResponse.isSuccess()) {
//					// 这里仅是简单打印， 请开发者按实际情况自行进行处理
//					log.info("获取用户信息成功：" + userinfoShareResponse.getBody());
//					
//				} else {
//					// 这里仅是简单打印， 请开发者按实际情况自行进行处理
//					log.info("获取用户信息失败");
//					
//				}
//			} else {
//				// 这里仅是简单打印， 请开发者按实际情况自行进行处理
//				log.info("authCode换取authToken失败");
//			}
			String orderKey=params.get("state");
			String url=WalletWebConfig.getDomain()+"walletPayOrder/79B4DE7C/applyDeposit?obj="+orderKey+"&acct="+oauthTokenResponse.getAlipayUserId();
			//return "redirect:"+url;
			response.sendRedirect(url);
		} catch (AlipayApiException alipayApiException) {
			log.error("view redirect_uri fail !");
			throw new BusinessException("alipay redirect_uri fail ! call admin");
			
		}
	}
}
