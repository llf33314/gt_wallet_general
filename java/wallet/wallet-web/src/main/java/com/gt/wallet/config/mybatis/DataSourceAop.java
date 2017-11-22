package com.gt.wallet.config.mybatis;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 下午3:06:21 
* 读写分离apo,根据接口名称切换数据源类说明 
*/
@Aspect
@Component
@Slf4j
public class DataSourceAop {
	
	@Before("execution(* com.gt.wallet.mapper..*.find*(..)) or execution(* com.gt.wallet.mapper..*.get*(..)) or execution(* com.gt.wallet.mapper..*.select*(..)) or execution(* com.gt.wallet.mapper..*.query*(..))")
    public void setReadDataSourceType() {
		DataSourceContextHolder.setDataSourceType(DataSourceEnum.slaver);
        log.info("dataSource切换到：Read");
    }

    @Before("execution(* com.gt.wallet.mapper..*.insert*(..)) or execution(* com.gt.wallet.mapper..*.update*(..)) or execution(* com.gt.wallet.mapper..*.add*(..))  or execution(* com.gt.wallet.mapper..*.save*(..))")
    public void setWriteDataSourceType() {
    	DataSourceContextHolder.setDataSourceType(DataSourceEnum.master);
        log.info("dataSource切换到：write");
    }

}
