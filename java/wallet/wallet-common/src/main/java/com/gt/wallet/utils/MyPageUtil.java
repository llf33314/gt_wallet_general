package com.gt.wallet.utils;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月20日 下午6:41:51 
* 类说明 
*/

public class MyPageUtil<T> extends Page<T>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 设置分页
	 * @param records
	 * @param total
	 */
	public void setRecords(List<T> records,Integer total) {
		super.setRecords(records);
		super.setTotal(total);
	}
	
	
	public MyPageUtil(){
		super();
	}
	
	public MyPageUtil(int current, int size) {
        super(current, size);
	}
//	/**
//	 * 总行数
//	 */
//	public static Page<?>  getInit(int total,Page<?> page){
//		page.setTotal(total);
//		page.getPages();
//		return page;
//	}
	
	

}
