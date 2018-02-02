package com.gt.wallet.web.api.subsystem;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.bean.session.BusUser;
import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.service.order.WalletIndexStatisticsService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月18日 上午11:32:51 
* 类说明 
*/
@RequestMapping("8A5DA52E/memberApi/")
@Slf4j
@RestController
@Api(value = "memberApi",description="多粉会员对外api ")  
public class MemberApi extends BaseController{

	@Autowired
	private WalletMemberService walletMemberService; 
	
	@Autowired
	private WalletIndexStatisticsService walletIndexStatisticsService;
	
	/**
	 * 判断商家是否开通多粉钱包(提供给各erp系统调用)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/open",method=RequestMethod.POST)
	 @ApiOperation(value="判断商家是否开通多粉钱包(提供给各erp系统调用)", notes="reqdata：为商家id",produces="application/json")
	public ServerResponse<?> is0pen(HttpServletRequest request,@RequestBody RequestUtils<Integer> requestUtils ){
		log.info(CommonUtil.format("start view is0pen api params:%s",JsonUtil.toJSONString(requestUtils)));
		try {
			ServerResponse<?> serverResponse=walletMemberService.isOpen(requestUtils.getReqdata());
			log.info(CommonUtil.format("view is0pen api serverResponse:%s",JsonUtil.toJSONString(requestUtils)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view is0pen api fail ：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view is0pen api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	/**
	 * 获取会员认证类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/getMemberAuth", method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE, consumes="application/json")
	 @ApiOperation(value="获取会员认证类型", notes="reqdata：为商家id",consumes="application/json", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,response=ServerResponse.class)
	public ServerResponse<Integer> getMemberAuth(HttpServletRequest request,@RequestBody RequestUtils<Integer> requestUtils ){
		log.info(CommonUtil.format("start view is0pen api params:%s",JsonUtil.toJSONString(requestUtils)));
		try {
			super.verification(request, requestUtils);
			ServerResponse<Integer> serverResponse=walletMemberService.getMemberAuth(requestUtils.getReqdata());
			log.info(CommonUtil.format("view is0pen api serverResponse:%s",JsonUtil.toJSONString(requestUtils)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view is0pen api fail ：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view is0pen api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	/**
	 * 获取统计数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/getIndexStatistics", method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE, consumes="application/json")
	 @ApiOperation(value="获取会员认证类型", notes="reqdata：为商家id",consumes="application/json", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,response=ServerResponse.class)
	public ServerResponse<IndexStatistics> getIndexStatistics(HttpServletRequest request){
		log.info(CommonUtil.format("start view getIndexStatistics api"));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<IndexStatistics> serverResponse2=walletIndexStatisticsService.getIndexStatistics(busUser.getId());
			return serverResponse2;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view getIndexStatistics api fail ：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view getIndexStatistics api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
}
