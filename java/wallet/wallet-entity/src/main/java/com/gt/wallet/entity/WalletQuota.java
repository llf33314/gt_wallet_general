package com.gt.wallet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 额度申请
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-11-20
 */
@Data
@Accessors(chain = true)
@TableName("t_wallet_quota")
@ApiModel(description="申请记录信息")
@EqualsAndHashCode(callSuper=false)
public class WalletQuota extends Model<WalletQuota> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="id",notes="id",required=true,hidden=true)
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 会员id
     */
	@TableField("w_member_id")
	@ApiModelProperty(name="会员id",notes="会员id",required=true)
	private Integer wMemberId;
    /**
     * 申请额度
     */
	@ApiModelProperty(name="申请额度",notes="申请额度",required=true)
	@TableField("quota_value")
	private BigDecimal quotaValue;
    /**
     * 申请描述
     */
	@ApiModelProperty(name="申请描述",notes="申请描述",required=true)
	@TableField("quota_desc")
	private String quotaDesc;
	private Date ctime;
    /**
     * 状态 0：创建待审核 1:审核通过 -1:审核不通过
     */
	@ApiModelProperty(name="status",notes="状态 0：创建待审核 1:审核通过 -1:审核不通过",required=true,hidden=true)
	private Integer status;
    /**
     * 失败原因
     */
	@ApiModelProperty(name="failReason",notes="失败原因",required=true,hidden=true)
	@TableField("fail_reason")
	private String failReason;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WalletQuota{" +
			"id=" + id +
			", wMemberId=" + wMemberId +
			", quotaValue=" + quotaValue +
			", quotaDesc=" + quotaDesc +
			", ctime=" + ctime +
			", status=" + status +
			", failReason=" + failReason +
			"}";
	}
}
