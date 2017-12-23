package com.gt.wallet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
	    registry.addViewController("/").setViewName("/index");
	    registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
	    registry.addViewController("/prompt").setViewName("/prompt/system_err");
	  }
	@Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry)
	  {
	    super.addResourceHandlers(registry);
	  }
	  
	
	/**
     * 跨域配置
     * 默认设置全局跨域配置
     * TODO: 部署服务器需要注释掉。因为，nginx已配置跨域。否则会起冲突
     *
     * @param registry Corsregistry
     */
    @Override
    public void addCorsMappings( CorsRegistry registry ) {
//	registry.addMapping( "/**" ).allowedHeaders( "*" ).allowedMethods( "*" ).allowedOrigins( "*" );
	super.addCorsMappings( registry );
    }
}
