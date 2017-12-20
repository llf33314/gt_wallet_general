package com.gt.wallet.web.wallet;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletPayUser;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletPayUserService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 支付用户 前端控制器
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-15
 */
@RequestMapping("/walletPayUser")
@RestController
@Api(value = "walletPayUser",description="支付用户")
@Slf4j
public class WalletPayUserController extends BaseController {
	
	@Autowired
	private  WalletPayUserService walletPayUserService;
	
	/**
	 * 新增记录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="79B4DE7C/add",method=RequestMethod.POST)
	 @ApiOperation(value="新增记录", notes="新增记录")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "openid",value = "openid",paramType = "body",dataType = "string",required=true),
        @ApiImplicitParam(name = "headimgurl",value = "头像",paramType = "form",dataType = "string",required=true)
        ,
        @ApiImplicitParam(name = "userType",value = "用户类型(1:微信 2:支付宝)",paramType = "form",dataType = "Integer"),
        @ApiImplicitParam(name = "city",value = "城市",paramType = "form",dataType = "string",required=false)
        ,
        @ApiImplicitParam(name = "province",value = "省份",paramType = "form",dataType = "string",required=false)
        ,
        @ApiImplicitParam(name = "nickName",value = "昵称",paramType = "form",dataType = "string",required=false)
        ,
        @ApiImplicitParam(name = "gender",value = "性别(1:男  2:女  0:未知)",paramType = "form",dataType = "string",required=true)
        ,
        
        // path, query, body, header, form
	})
	public ServerResponse<?> add(HttpServletRequest request,@RequestBody WalletPayUser entity){
		log.info(CommonUtil.format("start view add api params: %s",JsonUtil.toJSONString(entity)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletPayUserService.add(entity);
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view add api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view add api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
}
