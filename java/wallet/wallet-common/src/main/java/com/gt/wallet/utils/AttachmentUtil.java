package com.gt.wallet.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.gt.api.bean.session.BusUser;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月18日 下午2:37:45 
* 附件处理类说明 
*/
@Slf4j
public class AttachmentUtil {
	
	/**
	 * 上传附件
	 * @param multipartFile 附件
	 * @param busUser 商家对象
	 * @return code:0表示成功 ，其他失败 msg描述 data:附件绝对路径
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ServerResponse<String> uploadAttachment(MultipartFile multipartFile,BusUser busUser)  throws Exception{
		ServerResponse result=null;
		if(multipartFile.isEmpty()||multipartFile.getSize()<=0){
			log.error("上传附件为空");
			throw new BusinessException(WalletResponseEnums.ATTACHMENT_NULL_ERROR);
		}
		String format=multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1, multipartFile.getOriginalFilename().length());
		String path = WalletWebConfig.getPathImage() + "2/wallet/" + busUser.getName() +"/"+System.currentTimeMillis()+"."+format;
		File file = new File( WalletWebConfig.getPathImage() + "2/wallet/" + busUser.getName());
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		
//		multipartFile.transferTo(file);
		ContinueFTP myFtp = new ContinueFTP();
	        FileOutputStream out = new FileOutputStream(path);
	        out.write(multipartFile.getBytes());
	        out.flush();
	        out.close();
	    Map<String, Object> obj=myFtp.upload(path);
	    log.info("obj:"+JsonUtil.toJSONString(obj));
		result=ServerResponse.createBySuccessCodeData(path);
		return result;
	}

}
