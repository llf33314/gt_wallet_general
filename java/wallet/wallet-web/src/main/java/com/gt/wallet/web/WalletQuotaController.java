package com.gt.wallet.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.ReviewResult;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletQuota;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.log.WalletQuotaService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.MyPageUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 额度申请 前端控制器
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-11-20
 */
@RequestMapping("/walletQuota")
@RestController
@Api(value = "walletQuota",description="额度申请 ")
@Slf4j
public class WalletQuotaController extends BaseController {
	
	@Autowired
	private WalletQuotaService walletQuotaService;
	
	/**
	 * 提升额度申请
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	 @ApiOperation(value="提升额度申请", notes="提升额度申请")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "wMemberId",value = "会员id",paramType = "form",dataType = "int",required=true,defaultValue="0"),
        @ApiImplicitParam(name = "quotaValue",value = "申请额度",paramType = "form",dataType = "double",required=true,defaultValue="1000")
        ,
        @ApiImplicitParam(name = "quotaDesc",value = "申请描述",paramType = "form",dataType = "string",required=true,example="申请描述")
        // path, query, body, header, form
	})
	public ServerResponse<?> add(HttpServletRequest request, @RequestBody WalletQuota walletQuota){
		log.info(CommonUtil.format("触发提升额度申请接口 %s",JsonUtil.toJSONString(walletQuota)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletQuotaService.add(walletQuota);
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("提升额度申请接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("提升额度申请接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	/**
	 * 审核结果回调
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="review",method=RequestMethod.POST)
	 @ApiOperation(value="审核结果回调", notes="提升额度申请")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "wMemberId",value = "会员id",paramType = "form",dataType = "int",required=true,defaultValue="0"),
        @ApiImplicitParam(name = "quotaValue",value = "申请额度",paramType = "form",dataType = "double",required=true,defaultValue="1000")
        ,
        @ApiImplicitParam(name = "quotaValue",value = "申请描述",paramType = "form",dataType = "string",required=true,example="申请描述")
        // path, query, body, header, form
	})
	public ServerResponse<?> review(HttpServletRequest request,ReviewResult result){
		log.info(CommonUtil.format("触发审核结果回调接口 %s",JsonUtil.toJSONString(result)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletQuotaService.review(result);
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("审核结果回调接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("审核结果回调接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	
	/**
	 * 分页查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getPage",method=RequestMethod.POST)
	 @ApiOperation(value="分页查询", notes="分页查询")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "current",value = "当前页",paramType = "form",dataType = "int",required=true,defaultValue="1"),
        @ApiImplicitParam(name = "total",value = "总条数",paramType = "form",dataType = "int",required=true,defaultValue="0"),
        @ApiImplicitParam(name = "status",value = "状态 0：创建待审核 1:审核通过 -1:审核不通过",paramType = "form",dataType = "Integer",required=false,defaultValue="1"),
        @ApiImplicitParam(name = "size",value = "显示行数",paramType = "form",dataType = "int",required=true,defaultValue="10")
        // path, query, body, header, form
	})
	public ServerResponse<MyPageUtil<WalletQuota>> getPage(HttpServletRequest request,Page<WalletQuota> page, Integer status){
		log.info(CommonUtil.format("触发分页查询接口 %s",JsonUtil.toJSONString(page)));
		try {
			ServerResponse<MyPageUtil<WalletQuota>> serverResponse=walletQuotaService.getPage(page,status);
			log.info(CommonUtil.format("serverResponse:", JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("分页查询接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("分页查询接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
}
