package com.gt.wallet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 静态文件访问配置
 * User : lifengxi
 * Date : 2017/7/21 0021
 * Time : 9:29
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	@Override
	 public void addViewControllers(ViewControllerRegistry registry)
	  {
	    registry.addViewController("/").setViewName("/index.html");
	    registry.addViewController("/prompt").setViewName("/prompt/system_err");
	  }
	@Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry)
	  {
	    super.addResourceHandlers(registry);
	  }
	  
}
