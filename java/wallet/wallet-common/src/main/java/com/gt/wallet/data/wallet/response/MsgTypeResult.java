package com.gt.wallet.data.wallet.response;

import com.gt.wallet.enums.WalletMsgEnums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年12月14日 下午6:02:40 
* 类说明 
*/
@Data
@ApiModel(description="消息类型实体")
public class MsgTypeResult {
	
	  /**
     * 消息类型
     */
    @ApiModelProperty(required=true,name="msgType",notes="消息类型")
	private Integer msgType;
	
	
	 /**
     * 消息类型描述
     */
    @ApiModelProperty(required=true,name="msgTypeDesc",notes="消息类型描述")
	private String msgTypeDesc;
    
    public MsgTypeResult(){
    	super();
    }
    
    
    public MsgTypeResult(WalletMsgEnums walletMsgEnums){
    	super();
    	msgType=walletMsgEnums.getCode();
    	msgTypeDesc=walletMsgEnums.getDesc();
    }

}
