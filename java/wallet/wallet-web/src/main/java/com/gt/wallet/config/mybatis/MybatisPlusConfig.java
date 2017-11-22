package com.gt.wallet.config.mybatis;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 下午3:06:21 
* Mybatis-plus 配置类
*/
@Configuration
@MapperScan( "com.gt.wallet.mapper" )
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
    	 PaginationInterceptor page = new PaginationInterceptor();
         page.setDialectType("mysql");
         return page;
    }

}
