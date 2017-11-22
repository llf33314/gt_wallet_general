package com.gt.wallet.config.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 下午3:06:21 
* 数据源代理类
*/
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		 return DataSourceContextHolder.getDataSourceType();
	}

}
