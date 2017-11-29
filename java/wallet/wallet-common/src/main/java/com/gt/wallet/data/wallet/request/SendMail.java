package com.gt.wallet.data.wallet.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/** 
* <p>
* 发送邮件请求
* </p>
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月10日 上午9:30:05 
* 类说明 
*/
@Getter
@Setter
public class SendMail {
	
	/**
	 * 标题
	 */
	private String title;
	
	
	/**
	 * 发送内容
	 */
	private String content;
	
	/**
	 * 发送附件
	 */
	private List<String> files;
	
	
	
	public SendMail(){
		super();
	}



	public SendMail(String title, String content, List<String> files) {
		super();
		this.title = title;
		this.content = content;
		this.files = files;
	}
	
}
