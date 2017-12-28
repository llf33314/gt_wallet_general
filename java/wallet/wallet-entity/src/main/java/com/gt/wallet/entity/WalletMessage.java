package com.gt.wallet.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息中心
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-14
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_message")
@EqualsAndHashCode(callSuper=false)
public class WalletMessage extends Model<WalletMessage> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(required=true,name="主键",notes="唯一键")
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 钱包会员id
     */
    @ApiModelProperty(required=true,name="wMemberId",notes="钱包会员id")
	@TableField("w_member_id")
	private Integer wMemberId;
    /**
     * 消息类型
     */
    @ApiModelProperty(required=true,name="msgType",notes="消息类型")
	@TableField("msg_type")
	private Integer msgType;
	
	
	 /**
     * 消息类型描述
     */
    @ApiModelProperty(required=true,name="msgTypeDesc",notes="消息类型描述")
	@TableField(exist=false)
	private String msgTypeDesc;
	
    /**
     * 描述
     */
    @ApiModelProperty(required=true,name="descContent",notes="描述")
	@TableField("desc_content")
	private String descContent;
    /**
     * 状态描述(0:未读 1:已读)
     */
    @ApiModelProperty(required=true,name="state",notes="状态描述(0:未读 1:已读)")
	private Integer state;
	
	
	/**
	 * 状态(0:未读 1:已读)
	 */
    @ApiModelProperty(required=true,name="stateDesc",notes="状态描述")
	@TableField(exist=false)
	private String stateDesc;
    /**
     * 业务id
     */
    @ApiModelProperty(required=true,name="orderId",notes="业务id")
	@TableField("order_id")
	private Integer orderId;
    /**
     * 时间
     */
    @ApiModelProperty(required=true,name="ctime",notes="时间")
	private Date ctime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletMessage{" +
			"id=" + id +
			", wMemberId=" + wMemberId +
			", msgType=" + msgType +
			", descContent=" + descContent +
			", state=" + state +
			", orderId=" + orderId +
			", ctime=" + ctime +
			"}";
	}

	public WalletMessage(Integer wMemberId, Integer msgType, String descContent,
			Integer orderId) {
		super();
		this.wMemberId = wMemberId;
		this.msgType = msgType;
		this.descContent = descContent;
		this.orderId = orderId;
	}
	
	

	public WalletMessage() {
		super();
	}
}
