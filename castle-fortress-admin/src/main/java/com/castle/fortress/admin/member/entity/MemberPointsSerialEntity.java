package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 会员积分流水 实体类
 *
 * @author Mgg
 * @since 2021-12-01
 */
@Data
@TableName("member_points_serial")
@EqualsAndHashCode(callSuper = true)
public class MemberPointsSerialEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 会员id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;
    /**
     * 会员积分账户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberPointsId;
    /**
     * 积分
     */
    private String points;

    /**
     * 备注
     */
    private String memo;

    /**
     * 订单iD
     */
    private String orderId;
    /**
     * 流水类型（加或减）
     */
    private Integer type;
    /**
     * 类别（为什么加或减）
     */
    private Integer category;

    /**
     * 变动后账户积分 实时积分
     */
    private String accountPoints;

    /**
     * 查询用开始结束时间
     */
    @TableField(exist = false)
    private Date startTime;
    @TableField(exist = false)
    private Date endTime;


}
