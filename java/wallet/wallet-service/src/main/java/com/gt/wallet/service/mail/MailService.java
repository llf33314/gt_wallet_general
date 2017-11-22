package com.gt.wallet.service.mail;

import com.gt.wallet.data.wallet.request.SendMail;
import com.gt.wallet.dto.ServerResponse;

/** 
* <p>
*  邮件服务类
* </p>
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月9日 上午11:55:10 
* 类说明 
*/

public interface MailService {
	
	/**
	 * 邮箱发送(带附件)
	 * @param files 附件  可为空
	 * @param content 内容 必填
	 * @param title 标题 必填
	 */
	public ServerResponse<?> sendAttachmentsMail(SendMail sendMail)  throws Exception ;

}
