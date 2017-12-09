package com.gt.wallet.dto;

import static com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing.DEFAULT_TYPING;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.utils.CommonUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务响应类
 * <pre>
 *     统一响应返回数据格式
 * </pre>
 *
 * @author zhangmz
 * @create 2017/6/16
 */
//保证序列化json的时候,如果是null的对象,key也会消失
@JsonSerialize( typing = DEFAULT_TYPING )
@ApiModel(description="相应类")
public class ServerResponse< T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*状态码*/
	@ApiModelProperty(required=true,name="状态码",notes="返回0表示请求成功，其他表示失败")
    private int code;

    /*返回消息*/
	@ApiModelProperty(required=true,name="描述信息",notes="描述信息")
    private String msg;

    /*泛型数据*/
	@ApiModelProperty(name="业务数据",notes="业务数据,code返回0必传")
    private T data;

    protected ServerResponse( int code ) {
	this.code = code;
    }

    protected ServerResponse( int code, T data ) {
	this.code = code;
	this.data = data;
    }

    protected ServerResponse( int code, String msg ) {
	this.code = code;
	this.msg = msg;
    }

    protected ServerResponse( int code, String msg, T data ) {
	this.code = code;
	this.msg = msg;
	this.data = data;
    }

    public ServerResponse(){
    	
    }
    
    /**
     * 创建响应成功
     *
     * @return ServerResponse
     */
    public static < T > ServerResponse< T > createBySuccess() {
    	
	return new ServerResponse<>( WalletResponseEnums.SUCCESS.getCode(), WalletResponseEnums.SUCCESS.getDesc()  );
    }






    
    /**
     * 创建响应成功
     *
     * @param code 状态吗
     * @param data 数据
     *
     * @return ServerResponse
     */
//    public static < T > ServerResponse< T > createBySuccessCodeData( WalletResponseEnums responseEnums, T data ) {
//	return new ServerResponse< T >( responseEnums.getCode(),responseEnums.getDesc(), data );
//    }
    
    
    /**
     * 创建响应成功
     *
     * @param code 状态吗
     * @param data 数据
     *
     * @return ServerResponse
     */
    public static < T > ServerResponse< T > createBySuccessCodeData(  T data ) {
	return new ServerResponse< T >( WalletResponseEnums.SUCCESS.getCode(), WalletResponseEnums.SUCCESS.getDesc(), data );
    }

    /**
     * 创建响应失败
     *
     * @return ServerResponse
     */
    public static < T > ServerResponse< T > createByError() {
	return createByErrorCodeMessage( WalletResponseEnums.ERROR.getCode(), WalletResponseEnums.ERROR.getDesc() );
    }

    /**
     * 创建响应失败
     *
     * @param errorMessage 消息
     *
     * @return ServerResponse
     */
    public static < T > ServerResponse< T > createByErrorMessage( String errorMessage ) {
	return createByErrorCodeMessage( WalletResponseEnums.ERROR.getCode(), errorMessage );
    }
    
    
    /**
     * 创建响应失败
     *
     * @param 错误代码枚举
     *
     * @return ServerResponse
     */
    public static < T > ServerResponse< T > createByErrorCode(WalletResponseEnums enums) {
	return createByErrorCodeMessage(enums.getCode(),enums.getDesc());
    }
    
    
    /**
     * 创建响应失败
     *
     * @param 错误代码枚举
     *
     * @return ServerResponse
     */
    public static < T > ServerResponse< T > createByErrorCode(Map<String, Object> params) {
    	Integer code=CommonUtil.toInteger(params.get("code"));
		String msg=CommonUtil.toString(params.get("msg"));
		return createByErrorCodeMessage(code,msg);
    }


    /**
     * 判断是否是返回成功
     * @param serverResponse
     * @return
     */
    public static boolean judgeSuccess(ServerResponse<?> serverResponse){
    	return (CommonUtil.isNotEmpty(serverResponse)&&serverResponse.getCode()==0)?true:false;
    }
    
    /**
     * 创建响应失败
     *
     * @param errorCode    状态码
     * @param errorMessage 消息
     *
     * @return ServerResponse
     */
    public static < T > ServerResponse< T > createByErrorCodeMessage( int errorCode, String errorMessage ) {
	return new ServerResponse<>( errorCode, errorMessage );
    }

    //使之不在json序列化结果当中，作用用于判断
    @JsonIgnore
    public boolean isSuccess() {
	return this.code == WalletResponseEnums.SUCCESS.getCode();
    }

    public int getCode() {
	return code;
    }

    public T getData() {
	return data;
    }

    public String getMsg() {
	return msg;
    }

}
