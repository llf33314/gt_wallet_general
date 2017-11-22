package com.gt.wallet.exception;

import com.gt.wallet.enums.WalletResponseEnums;

/**
 * 业务异常
 *
 * @author zhangmz
 * @version 1.0.0
 * @date 2017-07-15
 * @since 1.0.0
 */
public class BusinessException extends SystemException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public BusinessException( String message ) {
	super( message );
    }

    public BusinessException( int code, String message ) {
	super( code, message );
    }
    
    
    public BusinessException(WalletResponseEnums walletResponseEnums) {
    	super( walletResponseEnums.getCode(), walletResponseEnums.getDesc() );
        }
}
