package com.gt.wallet.exception;

import com.gt.wallet.enums.WalletResponseEnums;

/**
 * Ajax 异常
 * <pre>
 *     ajax 请求的异常处理类
 * </pre>
 *
 * @author zhangmz
 * @create 2017/6/21
 */
public class ResponseEntityException extends SystemException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseEntityException( String message ) {
	super( message );
    }

    public ResponseEntityException( int code, String message ) {
	super( code, message );
    }
    
    public ResponseEntityException(WalletResponseEnums responseEnums) {
    	super( responseEnums.getCode(), responseEnums.getDesc() );
       }
}
