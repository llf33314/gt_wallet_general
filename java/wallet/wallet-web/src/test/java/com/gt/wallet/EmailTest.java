package com.gt.wallet;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class EmailTest  {
	@Autowired
    private JavaMailSender mailSender; //自动注入的Bean
//
    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数

    @Test
    public void sendSimpleMail() throws Exception {
    	MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(Sender);
            helper.setSubject("主题：带附件的邮件");
            helper.setText("带附件的邮件内容");
            //注意项目路径问题，自动补用项目路径
            FileSystemResource file = new FileSystemResource(new File("C:/Users/Administrator/Desktop/12/下载.jpg"));
            //加入邮件
            helper.addAttachment("图片.jpg", file);
            FileSystemResource file1 = new FileSystemResource(new File("D:/文档/2017-9-14多粉钱包设计图与需求文档V1.0.3/多粉钱包V1.0.3需求文档 .docx"));
            //加入邮件
            helper.addAttachment("2.docx", file1);
        } catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }

}
