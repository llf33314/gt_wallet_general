package com.gt.wallet.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.common.FileService;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

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
	@RequestMapping(value="upload",method=RequestMethod.POST)
	 @ApiOperation(value="文件上传", notes="文件上传")
	public ServerResponse<String> upload(HttpServletRequest request,
			@ApiParam(required=true,name="file" ,value="文件") MultipartFile file){
		log.info(CommonUtil.format("触发文件上传接口,file:%s",file.getSize()));
		try {
			ServerResponse<String> serverResponse=null;
			serverResponse=fileService.upload(file,CommonUtil.getLoginUser(request));
			log.info(CommonUtil.format("serverResponse:%s",JsonUtil.toJSONString(serverResponse)));
			return serverResponse;
			} catch ( BusinessException e) {
				throw new ResponseEntityException(e.getCode(),e.getMessage());
			} catch ( Exception e) {
				e.printStackTrace();
				log.error(CommonUtil.format("文件上传接口异常：%s,%s",WalletResponseEnums.SYSTEM_ERROR.getCode(),WalletResponseEnums.SYSTEM_ERROR.getDesc()));
				throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
			}
	}

}
