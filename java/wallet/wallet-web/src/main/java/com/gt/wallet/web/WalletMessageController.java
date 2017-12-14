package com.gt.wallet.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.data.wallet.request.SearchMsgPage;
import com.gt.wallet.data.wallet.response.MsgTypeResult;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMessage;
import com.gt.wallet.enums.WalletMsgEnums;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.log.WalletMessageService;
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
 * 消息中心 前端控制器
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-14
 */

@RequestMapping("/walletMessage")
@RestController
@Api(value = "walletQuota",description="消息中心")
@Slf4j
public class WalletMessageController extends BaseController {
	
	@Autowired
	private WalletMessageService walletMessageService;
	
	
	/**
	 * 分页查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="79B4DE7C/getPage",method=RequestMethod.POST)
	 @ApiOperation(value="分页查询", notes="分页查询")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "current",value = "当前页",paramType = "form",dataType = "int",required=true,defaultValue="1"),
        @ApiImplicitParam(name = "wmemberId",value = "钱包会员id",paramType = "form",dataType = "Integer",required=true),
        @ApiImplicitParam(name = "size",value = "显示行数",paramType = "form",dataType = "int",required=true,defaultValue="10"),
        @ApiImplicitParam(name = "msgType",value = "消息类型",paramType = "form",dataType = "int",required=false)
        // path, query, body, header, form
	})
	public ServerResponse<MyPageUtil<WalletMessage>> getPage(HttpServletRequest request,SearchMsgPage searchMsgPage){
		log.info(CommonUtil.format("触发分页查询接口 %s",JsonUtil.toJSONString(searchMsgPage)));
		try {
			ServerResponse<MyPageUtil<WalletMessage>> serverResponse=walletMessageService.getPage(searchMsgPage);
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
	
	/**
	 * 获取未读记录数
	 * @return
	 */
	@ApiOperation(value="获取未读记录数", notes="获取未读记录数")
	@ResponseBody
	@RequestMapping(value="79B4DE7C/getReadState",method=RequestMethod.POST)
	public ServerResponse<Integer> getReadState(HttpServletRequest request,@ApiParam(required=true,name="wMemberId" ,value="钱包会员id") @RequestParam Integer wMemberId){
		log.info(CommonUtil.format("触发获取未读记录数接口 %s",JsonUtil.toJSONString(wMemberId)));
		try {
			ServerResponse<Integer> serverResponse=walletMessageService.getReadState(wMemberId);
			log.info(CommonUtil.format("serverResponse:", JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("获取未读记录数接口异常：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("获取未读记录数接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	/**
	 * 修为已读状态
	 * @return
	 */
	@ApiOperation(value="修为已读状态", notes="修为已读状态")
	@ResponseBody
	@RequestMapping(value="79B4DE7C/upstate",method=RequestMethod.POST)
	public ServerResponse<?> upstate(HttpServletRequest request,@ApiParam(required=true,name="listStr" ,value="消息id，可填写多个，用逗号隔开") String listStr){
		log.info(CommonUtil.format("触发修为已读状态接口 %s",JsonUtil.toJSONString(listStr)));
		try {
			ServerResponse<?> serverResponse=walletMessageService.upstate(listStr);
			log.info(CommonUtil.format("serverResponse:", JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
		} catch ( BusinessException e) {
			log.error(CommonUtil.format("修为已读状态接口异常：%s,%s",e.getCode(),e.getMessage()));
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("修为已读状态接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 获取消息类型
	 * @return
	 */
	@ApiOperation(value="获取消息类型", notes="获取消息类型")
	@ResponseBody
	@RequestMapping(value="79B4DE7C/getMsgTypeResult",method=RequestMethod.POST)
	public ServerResponse<List<MsgTypeResult>> getMsgTypeResult(HttpServletRequest request){
		log.info(CommonUtil.format("触发获取消息类型接口 %s",JsonUtil.toJSONString(CommonUtil.getLoginUser(request))));
		try {
			List<MsgTypeResult> results=new ArrayList<>();
			MsgTypeResult result2=new MsgTypeResult(WalletMsgEnums.MSGTYPE_QUOTAREVIEW);
			MsgTypeResult result3=new MsgTypeResult(WalletMsgEnums.MSGTYPE_TAKE_MONEY);
			MsgTypeResult result4=new MsgTypeResult(WalletMsgEnums.MSGTYPE_USERLOCK);
			MsgTypeResult result=new MsgTypeResult(WalletMsgEnums.MSGTYPE_USERUNLOCK);
			results.add(result);
			results.add(result2);
			results.add(result3);
			results.add(result4);
			ServerResponse<List<MsgTypeResult>> serverResponse=ServerResponse.createBySuccessCodeData(results);
			log.info(CommonUtil.format("serverResponse:", JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
		} catch ( BusinessException e) {
			log.error(CommonUtil.format("获取消息类型接口异常：%s,%s",e.getCode(),e.getMessage()));
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("获取消息类型接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
	
	
}
