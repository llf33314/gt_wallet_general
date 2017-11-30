package com.gt.wallet.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.bean.session.BusUser;
import com.gt.api.util.IPOrAddressUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.WalletPasswordSet;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 多粉钱包会员 前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Slf4j
@RestController("/walletMember")  
@Api(value = "walletMember",description="多粉会员")  
public class WalletMemberController extends BaseController {
	
	@Autowired
	private WalletMemberService walletMemberService; 
	
	
	
	/**
	 * 查询多粉会员信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="findMember",method=RequestMethod.GET)
	 @ApiOperation(value="查询多粉会员信息", notes="此接口不需要传入参数，但必须登录多粉商家后台",produces="application/json")
	public ServerResponse<WalletMember> findMember(HttpServletRequest request){
		log.info(CommonUtil.format("触发查询多粉会员信息接口"));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<WalletMember> serverResponse=null;
			ServerResponse<List<WalletMember>> temp=walletMemberService.findMember(busUser.getId());
			if(CommonUtil.isNotEmpty(temp)&&temp.getCode()==0&&CommonUtil.isNotEmpty(temp.getData())&&temp.getData().size()==1){
				serverResponse=ServerResponse.createBySuccessCodeData(temp.getData().get(0));
				return serverResponse;
			}else{
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("查询多粉会员信息接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("查询多粉会员信息接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	/**
	 * 判断商家是否开通多粉钱包
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="isPassWallet",method=RequestMethod.GET)
	 @ApiOperation(value="判断商家是否开通多粉钱包", notes="此接口不需要传入参数，但必须登录多粉商家后台,返回值中data表示0 :已开通, 1:未开通",produces="application/json")
	public ServerResponse<Integer> isPassWallet(HttpServletRequest request){
		log.info(CommonUtil.format("触发查询多粉会员信息接口"));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<Integer> serverResponse=walletMemberService.isPassWallet(busUser.getId());
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("判断商家是否开通多粉钱包接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("判断商家是否开通多粉钱包接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	/**
	 * 开通会员
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="register",method=RequestMethod.GET)
	 @ApiOperation(value="开通会员", notes="开通会员",produces="application/json")
	
	public ServerResponse<Integer> register(HttpServletRequest request,@ApiParam(required=true,name="memberType" ,value="会员类型(3:个人会员 2:企业会员),返回值data：表示会员id") @RequestParam(required=true) Integer memberType){
		log.info(CommonUtil.format("触发开通会员接口,参数%s",memberType));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<Integer> serverResponse=walletMemberService.register(memberType,IPOrAddressUtils.getIpAddr(request),busUser.getId());
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("开通会员接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("开通会员接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	} 
	
	
	
	/**
	 * 修改密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="setpwd",method=RequestMethod.POST)
	 @ApiOperation(value="修改密码", notes="修改密码")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "pwd",value = "密码(6位数字)",paramType = "form",dataType = "int",required=true,example="密码"),
        @ApiImplicitParam(name = "confirm",value = "确认密码",paramType = "form",dataType = "string",required=true,example="确认密码")
        ,
        @ApiImplicitParam(name = "wmemberId",value = "会员id",paramType = "form",dataType = "int",required=true,example="会员id")
        ,
        @ApiImplicitParam(name = "code",value = "短信验证码(开发默认8888)",paramType = "form",dataType = "string",required=true,defaultValue="8888")
        // path, query, body, header, form
	})
	public ServerResponse<?> setpwd(HttpServletRequest request,WalletPasswordSet walletPasswordSet
		){
		log.info(CommonUtil.format("触发修改密码接口,walletSet:%s",JsonUtil.toJSONString(walletPasswordSet)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletMemberService.setpwd(walletPasswordSet,CommonUtil.getLoginUser(request));
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("修改密码接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("修改密码接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	} 
	
	/**
	 * 发送短信验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="sendVerificationCode",method=RequestMethod.POST)
	 @ApiOperation(value="发送短信验证码", notes="发送短信验证码")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "phone",value = "手机号码",paramType = "form",dataType = "int",required=true,example="手机号码"),
        @ApiImplicitParam(name = "wmemberId",value = "钱包会员id",paramType = "form",dataType = "int",required=true,example="钱包会员id")
        // path, query, body, header, form
	})
	public ServerResponse<?> sendVerificationCode(HttpServletRequest request,String phone,Integer wmemberId
		){
		log.info(CommonUtil.format("触发发送短信验证码接口,参数:%s,%s",phone,wmemberId));
		try {
			Integer verificationCodeType=9;
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<?> serverResponse=null;
			serverResponse=walletMemberService.sendVerificationCode(busUser.getId(), phone, wmemberId,verificationCodeType);//YunSoaMemberUtil.sendVerificationCode(bizUserId, phone, verificationCodeType);
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("发送短信验证码接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("发送短信验证码接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	} 
	
	
	
}
