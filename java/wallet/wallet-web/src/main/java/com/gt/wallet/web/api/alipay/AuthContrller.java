package com.gt.wallet.web.api.alipay;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.gt.wallet.constant.alipay.AlipayServiceEnvConstants;
import com.gt.wallet.utils.alipay.AlipayAPIClientFactory;
import com.gt.wallet.utils.alipay.RequestUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lifengxi(gt_sky@qq.com)
 * @version 创建时间：2017年12月19日 上午10:36:36 类说明
 */
@Slf4j
@Controller
@RequestMapping("alipayAuth")
public class AuthContrller {

	@RequestMapping("79B4DE7C/auth")
	protected void auth(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 解析请求参数
		Map<String, String> params = RequestUtil.getRequestParams(request);
		// 2. 获得authCode
		String authCode = params.get("auth_code");

		try {
			// 3. 利用authCode获得authToken
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(authCode);
			oauthTokenRequest.setGrantType(AlipayServiceEnvConstants.GRANT_TYPE);
			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			log.info(oauthTokenResponse.getAlipayUserId());
			log.info(oauthTokenResponse.getUserId());
			response.getWriter().write("用户id：" + oauthTokenResponse.getUserId());

			// 成功获得authToken
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {

				// 4. 利用authToken获取用户信息
				AlipayUserInfoShareRequest userinfoShareRequest = new AlipayUserInfoShareRequest();
				AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(userinfoShareRequest,
						oauthTokenResponse.getAccessToken());

				// 成功获得用户信息
				if (null != userinfoShareResponse && userinfoShareResponse.isSuccess()) {
					// 这里仅是简单打印， 请开发者按实际情况自行进行处理
					log.info("获取用户信息成功：" + userinfoShareResponse.getBody());

				} else {
					// 这里仅是简单打印， 请开发者按实际情况自行进行处理
					log.info("获取用户信息失败");

				}
			} else {
				// 这里仅是简单打印， 请开发者按实际情况自行进行处理
				log.info("authCode换取authToken失败");
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
		}
	}

}
