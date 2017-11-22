/**
 * 
 */
package com.gt.wallet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lifengxi
 *
 * @date 2015-6-23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLogAnnotation {
	/**
	 * 功能模块(字典)
	 */
	public String op_function() default "" ;
	
	/**
	 * 操作类型(分为 1:功能日志、2:错误日志)
	 */
	public String log_type() default "1" ;
	
	/**
	 * 访问类型(1:pc端(商家后台操作) 2、手机端(用户访问))
	 */
	public String visit_type() default "1";
	
	/**
	 * 描述
	 */
	public String description() default "" ;
	
	
	
}
