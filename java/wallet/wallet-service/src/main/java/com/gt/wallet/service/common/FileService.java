package com.gt.wallet.service.common;

import org.springframework.web.multipart.MultipartFile;

import com.gt.api.bean.session.BusUser;
import com.gt.wallet.dto.ServerResponse;

/** 
*
* <p>
* 公共 服务类
* </p>
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月27日 上午10:47:13 
*/

public interface FileService {
	
	
	/**
	 * 文件上传
	 * @param file	上传文件
	 * @param busUser 商家信息
	 * @return
	 */
	public ServerResponse<String> upload(MultipartFile file,BusUser busUser) throws Exception;

}
