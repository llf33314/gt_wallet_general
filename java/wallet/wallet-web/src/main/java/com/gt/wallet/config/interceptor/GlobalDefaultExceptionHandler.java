package com.gt.wallet.config.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gt.wallet.dto.ErrorInfo;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常统一处理
 * <pre>
 *
 * </pre>
 *
 * @author zhangmz
 * @create 2017/6/21
 */
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {
	 // 全局默认错误页
    private static final String DEFAULT_ERROR_VIEW = "/prompt/system_err";


    // 页面
    // 统一异常处理 页面跳转
    @ExceptionHandler( value = Exception.class )
    public ModelAndView defaultErrorHandlerModel( HttpServletRequest request, Exception e ) {
	ModelAndView modelAndView = new ModelAndView();
	modelAndView.addObject( "msg", e.getMessage() );
	modelAndView.addObject( "url", request.getRequestURL() );
	modelAndView.setViewName( DEFAULT_ERROR_VIEW );
	log.error( e.getMessage() );
	e.printStackTrace();
	return modelAndView;
    }

    // 统一异常处理 Ajax请求
    @ResponseBody
    @ExceptionHandler( value = ResponseEntityException.class )
    public ErrorInfo< String > defaultErrorHandler( HttpServletRequest request, ResponseEntityException e ) {
//	return ErrorInfo.createByErrorCodeMessage( ResponseEnums.ERROR.getCode(), e.getMessage(), request.getRequestURL().toString() );
    	Integer code=e.getCode();
    	if(CommonUtil.isEmpty(e.getCode())){
    		code=WalletResponseEnums.ERROR.getCode();
    	}
    	return ErrorInfo.createByErrorCodeMessage(code,e.getMessage(), request.getRequestURL().toString());
    }
}
