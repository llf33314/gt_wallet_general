package com.gt.wallet.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gt.wallet.annotation.SysLogAnnotation;

/**
 * 功能日志拦截器
 * User : lifengxi
 * Date : 2017/11/1 0004
 * Time : 16:30
 */
public class SysLogInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void afterCompletion( HttpServletRequest request,
		    HttpServletResponse response, Object handler, Exception ex )
		    throws Exception {
	super.afterCompletion( request, response, handler, ex );
    }

    @Override
    public void postHandle( HttpServletRequest request,
		    HttpServletResponse response, Object handler,
		    ModelAndView modelAndView ) throws Exception {
//    	HandlerMethod handlerMethod = (HandlerMethod) handler;
//    	SysLogAnnotation annotation = handlerMethod.getMethodAnnotation( SysLogAnnotation.class );
//    	if ( annotation != null ) {
    		//todo 调用陈丹日志接口
    		/*DaoUtil daoUtil= CommonUtil.getApplicationContext().getBean(DaoUtil.class);
		Map<String, Object> logObj=new HashMap<String, Object>();
		BusUser user=CommonUtil.getLoginUser(request);
		Class<?> bean=handlerMethod.getBeanType();
		String controller=bean.getName();
		if(user!=null){
		    logObj.put("opt_person", user.getName());
		}
		logObj.put("opt_controller",controller);
		logObj.put("opt_method", handlerMethod.getMethod().getName());
		logObj.put("opt_desc", annotation.description());
		logObj.put("opt_function", annotation.op_function());
		logObj.put("log_type", annotation.log_type());
		logObj.put("opt_ip", IPKit.getIpAddr(request));
		KeysUtil des = new KeysUtil();
		logObj.put("opt_paramers",des.getEncString(JSONObject.toJSONString(getParamers(request))));
		String date= DateTimeKit.getDateTime(DateTimeKit.DEFAULT_DATE_FORMAT_YYYYMM);
		daoUtil.saveObjectByMap("", "t_bus_log_"+date, logObj);*/
//    	}
    	super.postHandle( request, response, handler, modelAndView );
    }

    @Override
    public boolean preHandle( HttpServletRequest request,
		    HttpServletResponse response, Object handler ) throws Exception {

	return super.preHandle( request, response, handler );
    }
  
}
