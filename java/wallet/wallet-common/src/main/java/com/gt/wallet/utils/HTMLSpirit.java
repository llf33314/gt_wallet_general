package com.gt.wallet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 过滤html创建时间：2018年1月3日 上午10:42:05 
* 类说明 
*/

public class HTMLSpirit {
	
	 public static boolean delHTMLTag(String htmlStr){ 
	        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
	        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
	        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	        Matcher m_script=p_script.matcher(htmlStr); 
	   //     System.out.println( m_script.find());
	        boolean flag=m_script.find();
	        if(flag){
	        	return flag;
	        }
	        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	        Matcher m_style=p_style.matcher(htmlStr); 
	        flag=m_style.find();
	        if(flag){
	        	return flag;
	        }
	        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	        Matcher m_html=p_html.matcher(htmlStr); 
	        flag=m_html.find();
	        if(flag){
	        	return flag;
	        }
	        return false;
	    } 

	 
	 public static void main(String[] args) {
		 boolean st= delHTMLTag("111");
		System.out.println(st);
	}
}
