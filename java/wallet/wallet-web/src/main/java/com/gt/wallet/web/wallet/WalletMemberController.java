package com.gt.wallet.web.wallet;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.api.util.IPOrAddressUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.WalletSet;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.MyPageUtil;

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
@RestController
@RequestMapping("/walletMember")
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
		log.info(CommonUtil.format("start view findMember api"));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<WalletMember> serverResponse=null;
			ServerResponse<List<WalletMember>> temp=walletMemberService.findMember(busUser.getId());
			log.info("view findMember serverResponse:"+JsonUtil.toJSONString(temp));
			if(CommonUtil.isNotEmpty(temp)&&temp.getCode()==0&&CommonUtil.isNotEmpty(temp.getData())&&temp.getData().size()==1){
				serverResponse=ServerResponse.createBySuccessCodeData(temp.getData().get(0));
				return serverResponse;
			}else{
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view findMember api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view findMember api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	/**
	 * 判断商家是否开通多粉钱包
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="isPassWallet",method=RequestMethod.GET)
	 @ApiOperation(value="判断商家是否开通多粉钱包(内部接口)", notes="此接口不需要传入参数，但必须登录多粉商家后台,返回值中data表示0 :已开通, 1:未开通",produces="application/json")
	public ServerResponse<Integer> isPassWallet(HttpServletRequest request){
		log.info(CommonUtil.format("start view isPassWallet api"));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<Integer> serverResponse=walletMemberService.isPassWallet(busUser.getId());
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view isPassWallet api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view isPassWallet api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
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
		log.info(CommonUtil.format("start view register api,params%s",memberType));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<Integer> serverResponse=walletMemberService.register(memberType,IPOrAddressUtils.getIpAddr(request),busUser.getId());
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view register api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view register api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	} 
	
	
	
//	/**
//	 * 绑定手机
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="bindingPhone",method=RequestMethod.POST)
//	 @ApiOperation(value="修改密码", notes="修改密码")
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "phone",value = "手机号码",paramType = "form",dataType = "string",required=true),
//        @ApiImplicitParam(name = "wmemberId",value = "会员id",paramType = "form",dataType = "int",required=true,example="会员id")
//        ,
//        @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "form",dataType = "string",required=true)
//        // path, query, body, header, form
//	})
//	public ServerResponse<?> bindingPhone(HttpServletRequest request,WalletPasswordSet walletPasswordSet
//		){
//		log.info(CommonUtil.format("start view setpwd api ,params:%s",JsonUtil.toJSONString(walletPasswordSet)));
//		try {
//			ServerResponse<?> serverResponse=null;
//			serverResponse=walletMemberService.bindingPhone(walletPasswordSet,CommonUtil.getLoginUser(request));
//			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
//			return serverResponse;
//			} catch ( BusinessException e) {
//				log.error(CommonUtil.format("view setpwd api fail：%s,%s",e.getCode(),e.getMessage()));
//				throw new ResponseEntityException(e.getCode(),e.getMessage());
//			} catch ( Exception e) {
//				e.printStackTrace();
//				log.error(CommonUtil.format("view setpwd api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
//				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
//			}
//	} 
	
	
	/**
	 * 绑定手机
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="bindingPhone",method=RequestMethod.POST)
	 @ApiOperation(value="绑定手机", notes="绑定手机")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "wmemberId",value = "多粉钱包会员id",paramType = "form",dataType = "int",required=true,example="多粉钱包会员id"),
        @ApiImplicitParam(name = "phone",value = "手机号码",paramType = "form",dataType = "string",required=true,example="手机号码")
        ,
        @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "form",dataType = "string",required=true,defaultValue="8888")
	})
	public ServerResponse<?> bindingPhone(HttpServletRequest request,WalletSet walletSet
		){
		log.info(CommonUtil.format("start view bindingPhone api ,params:%s",JsonUtil.toJSONString(walletSet)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletMemberService.bindingPhone(walletSet,CommonUtil.getLoginUser(request));
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view bindingPhone api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view bindingPhone api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
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
        @ApiImplicitParam(name = "wmemberId",value = "钱包会员id",paramType = "form",dataType = "int",required=true,example="钱包会员id"),
        @ApiImplicitParam(name = "verificationCodeType",value = "验证码类型(绑定、修改手机)verificationCodeType=9",paramType = "form",dataType = "int",required=true,defaultValue="9")
        // path, query, body, header, form
	})
	public ServerResponse<?> sendVerificationCode(HttpServletRequest request,String phone,Integer wmemberId,Integer verificationCodeType
		){
		log.info(CommonUtil.format("start view sendVerificationCode api,params:%s,%s",phone,wmemberId));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<?> serverResponse=null;
			serverResponse=walletMemberService.sendVerificationCode(busUser.getId(), phone, wmemberId,verificationCodeType);//YunSoaMemberUtil.sendVerificationCode(bizUserId, phone, verificationCodeType);
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view sendVerificationCode api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view sendVerificationCode api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	} 
	
	
	
	/**
	 *  锁定用户
	 * @param request
	 * @param phone
	 * @param wmemberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="79B4DE7C/lockMember",method=RequestMethod.POST)
	@ApiOperation(value="锁定用户", notes="锁定用户")
	public ServerResponse<?> lockMember(HttpServletRequest request,@RequestBody Map<String, Object> params
			){
		log.info(CommonUtil.format("start view lockMember api params:%s",JsonUtil.toJSONString(params)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletMemberService.lockMember(CommonUtil.toInteger(params.get("wmemberId")));//YunSoaMemberUtil.sendVerificationCode(bizUserId, phone, verificationCodeType);
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
		} catch ( BusinessException e) {
			log.error(CommonUtil.format("view lockMember api fail：%s,%s",e.getCode(),e.getMessage()));
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view lockMember api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	} 
	
	
	/**
	 * 解锁用户
	 * @param request
	 * @param phone
	 * @param wmemberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="79B4DE7C/unlockMember",method=RequestMethod.POST)
	@ApiOperation(value="解锁用户", notes="解锁用户")
	public ServerResponse<?> unlockMember(HttpServletRequest request,@RequestBody Map<String, Object> params
			){
		log.info(CommonUtil.format("start view unlockMember api fail,params:%s",JsonUtil.toJSONString(params)));
		try {
			ServerResponse<?> serverResponse=null;
			serverResponse=walletMemberService.unlockMember(CommonUtil.toInteger(params.get("wmemberId")));
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
		} catch ( BusinessException e) {
			log.error(CommonUtil.format("view unlockMember api fail：%s,%s",e.getCode(),e.getMessage()));
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view unlockMember api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	} 
	
	
	/**
	 * 会员列表分页
	 * @param request
	 * @param phone
	 * @param wmemberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="79B4DE7C/memberListPage",method=RequestMethod.POST)
	@ApiOperation(value="会员列表分页", notes="会员列表分页")
	public ServerResponse<MyPageUtil<WalletMember>> memberListPage(HttpServletRequest request,@RequestBody com.alibaba.fastjson.JSONObject params
			){
		log.info(CommonUtil.format("start view memberListPage api params:%s",JsonUtil.toJSONString(params)));
		try {
			 Page<WalletMember> page=new Page<WalletMember>();
			 page.setSize(params.getInteger("size"));
			 page.setCurrent(params.getInteger("current"));
			 Integer status=null;
			 String phone=null;
			 Integer memberType=null;
			if( CommonUtil.isNotEmpty(params.get("status"))){
				status=params.getInteger("status");
			}
			if( CommonUtil.isNotEmpty(params.get("phone"))){
				phone=params.getString("phone");
			}
			if( CommonUtil.isNotEmpty(params.get("memberType"))){
				memberType=params.getInteger("memberType");
			}
			ServerResponse<MyPageUtil<WalletMember>>  serverResponse=walletMemberService.getPage(page,status, phone,  memberType);
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
		} catch ( BusinessException e) {
			log.error(CommonUtil.format("view memberListPage api fail：%s,%s",e.getCode(),e.getMessage()));
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view memberListPage api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	} 
	
	
	/**
	 * 重置手机
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="reset",method=RequestMethod.POST)
	 @ApiOperation(value="重置手机", notes="重置手机",produces="application/json")
	
	public ServerResponse<?> reset(HttpServletRequest request,@ApiParam(required=true,name="newPhone" ,value="新手机号码") @RequestParam(required=true) String newPhone
			,@ApiParam(required=true,name="verificationCode" ,value="验证码") @RequestParam(required=true) String verificationCode,@ApiParam(required=true,name="wmemberId" ,value="钱包会员id") @RequestParam(required=true) Integer wmemberId){
		log.info(CommonUtil.format("start view reset api params:%s,%s",newPhone,verificationCode));
		try {
			BusUser busUser=CommonUtil.getLoginUser(request);
			ServerResponse<?> serverResponse=walletMemberService.reset(busUser.getId(), newPhone, verificationCode, wmemberId);
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view reset api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view reset api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}  
}
