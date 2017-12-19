package com.gt.wallet.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付用户
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-15
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_pay_user")
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="支付用户")
public class WalletPayUser extends Model<WalletPayUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="id",notes="主键",required=false)
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户唯一标识(微信或支付宝)
     */
	@ApiModelProperty(name="openid",notes="用户唯一标识(微信或支付宝)",required=true)
	private String openid;
    /**
     * 头像
     */
	@ApiModelProperty(name="headimgurl",notes="头像",required=false)
	private String headimgurl;
    /**
     * 用户类型(1:微信 2:支付宝)
     */
	@ApiModelProperty(name="userType",notes="用户类型(1:微信 2:支付宝)",required=true)
	@TableField("user_type")
	private Integer userType;
	/**
	 * 城市
	 */
	@ApiModelProperty(name="city",notes="city",required=false)
	private String city;
	/**
	 * 省份
	 */
	@ApiModelProperty(name="province",notes="主键",required=false)
	private String province;
	/**
	 * 昵称
	 */
	@ApiModelProperty(name="nickName",notes="昵称",required=true)
	@TableField("nick_name")
	private String nickName;
    /**
     * 性别(1:男  2:女  0:未知)
     */
	@ApiModelProperty(name="gender",notes="性别(1:男  2:女  0:未知)",required=true)
	private Integer gender;
    /**
     * 创建时间
     */
	private Date ctime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletPayUser{" +
			"id=" + id +
			", openid=" + openid +
			", headimgurl=" + headimgurl +
			", userType=" + userType +
			", city=" + city +
			", province=" + province +
			", nickName=" + nickName +
			", gender=" + gender +
			", ctime=" + ctime +
			"}";
	}
}
