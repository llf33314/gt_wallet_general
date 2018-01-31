package com.gt.wallet.web.wallet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gt.api.dto.ResponseUtils;
import com.gt.api.util.HttpClienUtils;
import com.gt.api.util.RequestUtils;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.wallet.request.SendSocket;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.common.FileService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.SocketUtil;
import com.gt.wallet.utils.WalletWebConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月27日 上午10:40:27 
* 文件处理控制类说明 
*/
@RestController
@RequestMapping("wcommon")
@Api(value = "wcommon",description="公共部分")
@Slf4j
public class WalletCommonController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 文件上传
	 * @return
	 */
	@PostMapping(value="upload")
	 @ApiOperation(value="文件上传", notes="文件上传")
	public ServerResponse<String> upload(HttpServletRequest request,
			MultipartFile file){
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				log.info(CommonUtil.format("start view upload,file:%s",file.getSize()));
				ServerResponse<String> serverResponse=null;
				serverResponse=fileService.upload(file,CommonUtil.getLoginUser(request));
				
				log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
				return serverResponse;
			}else{
				throw new ResponseEntityException("view upload faill:file is empty");
			}
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view upload api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view upload api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}

	/**
	 * 获取省份数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getProvince",method=RequestMethod.GET)
	 @ApiOperation(value="获取省份数据", notes="获取省份数据")
	public ResponseUtils<List<Map<String, Object>>> getProvince(HttpServletRequest request){
		try {
			log.info(CommonUtil.format("start view getProvince api"));
			ResponseUtils<List<Map<String, Object>>> serverResponse=null;
			RequestUtils<Integer> requestUtils=new RequestUtils<>();
			requestUtils.setReqdata(2);	
			serverResponse= 	HttpClienUtils.reqPostUTF8(JsonUtil.toJSONString(requestUtils), WalletWebConfig.getHomeUrl()+"8A5DA52E/shopapi/6F6D9AD2/79B4DE7C/queryCityByLevel.do", ResponseUtils.class, WalletWebConfig.getWxmpKey());
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view getProvince api fail：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view getProvince api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
	
	
	/**
	 * 根据父级ID查询城市数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value="queryCityByParentId")
	@ApiOperation(value="获取省份数据", notes="根据父级ID查询城市数据")
	public ResponseUtils<List<Map<String, Object>>> queryCityByParentId(HttpServletRequest request,@ApiParam(required=true,name="parentId" ,value="父级id") @RequestParam(required=true) Integer  parentId){
		try {
			log.info(CommonUtil.format("start view queryCityByParentId api "));
			ResponseUtils<List<Map<String, Object>>> serverResponse=null;
			RequestUtils<Integer> requestUtils=new RequestUtils<>();
			requestUtils.setReqdata(parentId);	
			serverResponse= 	HttpClienUtils.reqPostUTF8(JsonUtil.toJSONString(requestUtils), WalletWebConfig.getHomeUrl()+"8A5DA52E/shopapi/6F6D9AD2/79B4DE7C/queryCityByParentId.do", ResponseUtils.class, WalletWebConfig.getWxmpKey());
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
		} catch ( BusinessException e) {
			log.error(CommonUtil.format("view queryCityByParentId api fail：%s,%s",e.getCode(),e.getMessage()));
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			e.printStackTrace();
			log.error(CommonUtil.format("view queryCityByParentId api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
	
	
	/**
	 * 消息推送
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/79B4DE7C/sendMessage",method=RequestMethod.POST)
	@ApiIgnore
	public ServerResponse<?> sendMessage(HttpServletRequest request, SendSocket sendSocket ){
		log.info(CommonUtil.format("start sendMessage debit api"));
		try {
				ServerResponse<?>	 serverResponse=SocketUtil.sendMsg(""+sendSocket.getBusId(), null, sendSocket.getSendUrl());
				return serverResponse;
			} catch ( BusinessException e) {
				log.error(CommonUtil.format("view sendMessage api fail ：%s,%s",e.getCode(),e.getMessage()));
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("view sendMessage api fail：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}
}
