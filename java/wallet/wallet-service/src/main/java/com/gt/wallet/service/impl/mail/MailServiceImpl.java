package com.gt.wallet.service.impl.mail;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.wallet.request.SendMail;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.service.mail.MailService;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/** 
* 邮件服务类
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月9日 下午2:15:03 
* 类说明 
*/

@Service
@Slf4j
public class MailServiceImpl implements MailService {
	
	@Autowired
    private JavaMailSender mailSender; //自动注入的Bean

    @Value("${spring.mail.username}")
    private String Sender; //发送者
    
    @Value("${mail.receiver.username}")
    private String receiver; //收件人

	@Override
	public ServerResponse<?> sendAttachmentsMail(SendMail sendMail) throws Exception {
		log.info(CommonUtil.format("start biz sendAttachmentsMail api params:%s", JsonUtil.toJSONString(sendMail)));
		if(CommonUtil.isEmpty(sendMail)||CommonUtil.isEmpty(sendMail.getTitle())||CommonUtil.isEmpty(sendMail.getContent())){
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		MimeMessage message = null;
		message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(Sender);
		helper.setTo(Sender);
		helper.setSubject(sendMail.getTitle());
		helper.setText(sendMail.getContent());
		if(CommonUtil.isNotEmpty(sendMail.getFiles())&&sendMail.getFiles().size()>0){//当附件为空时，发送文本邮件
			for (String string : sendMail.getFiles()) {
				FileSystemResource file = new FileSystemResource(new File(string));
				String format=string.substring(string.lastIndexOf(".")+1,string.length());
				helper.addAttachment(CommonUtil.format("%s.%s", System.currentTimeMillis(),format), file);
			}
		}
		mailSender.send(message);
		log.info("*******biz接口:邮箱发送成功********");
		return ServerResponse.createBySuccess();
		
	}

}
