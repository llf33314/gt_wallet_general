package com.gt.wallet.config.mybatis;



/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 下午3:06:21 
* 数据源线程类
*/
public class DataSourceContextHolder {
	
	
	private static final ThreadLocal<DataSourceEnum> CONTEXT_HOLDER = new ThreadLocal<DataSourceEnum>() {

        @Override
        protected DataSourceEnum initialValue() {
            return DataSourceEnum.master;
        }
    };


    public static void setDataSourceType(DataSourceEnum type) {
        CONTEXT_HOLDER.set(type);
    }

    public static DataSourceEnum getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void resetDataSourceType() {
        CONTEXT_HOLDER.set(DataSourceEnum.master);
    }

}
