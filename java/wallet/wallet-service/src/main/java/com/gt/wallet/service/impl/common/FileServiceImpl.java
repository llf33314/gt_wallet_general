package com.gt.wallet.service.impl.common;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gt.api.bean.session.BusUser;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.service.common.FileService;
import com.gt.wallet.utils.AttachmentUtil;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.WalletWebConfig;

import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月27日 上午10:51:47 
* 类说明 
*/
@Service
@Slf4j
public class FileServiceImpl implements FileService {

	
	@Override
	public ServerResponse<String> upload(MultipartFile file,BusUser busUser)  throws Exception{
		log.info(CommonUtil.format("start biz upload api,params:%s,%s", JsonUtil.toJSONString(file), JsonUtil.toJSONString(busUser)));
		ServerResponse<String> serverResponse=AttachmentUtil.uploadAttachment(file, busUser);
		if(ServerResponse.judgeSuccess(serverResponse)){
			String path=serverResponse.getData();
			String newPath=WalletWebConfig.getFileDomain()+path.split("upload/")[1];
			serverResponse=ServerResponse.createBySuccessCodeData(newPath);
		}
		log.info(CommonUtil.format("biz upload api serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return serverResponse;
	}

}
