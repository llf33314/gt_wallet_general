package com.gt.wallet.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 调用第三方api日志表
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_api_log")
@EqualsAndHashCode(callSuper=false)
public class WalletApiLog extends Model<WalletApiLog> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	
	/**
	 * 多粉钱包会员id
	 */
	@TableField("w_member_id")
	private Integer wMemberId;
	
	/**
	 * 请求参数
	 */
	@TableField("params_json")
	private String paramsJson;
	
	/**
	 * 接口类型
	 */
	private Integer type;
	
	/**
	 * 返回结果
	 */
	private String result;
	
	/**
	 * 创建时间
	 */
	private Date ctime;
    /**
     * 状态
     */
	private Integer status;
	
	/**
	 * 描述
	 */
	private String msg;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletApiLog{" +
			"id=" + id +
			", wMemberId=" + wMemberId +
			", paramsJson=" + paramsJson +
			", type=" + type +
			", result=" + result +
			", ctime=" + ctime +
			", status=" + status +
			", msg=" + msg +
			"}";
	}
}
