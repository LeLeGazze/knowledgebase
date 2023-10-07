package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 会员表 实体类
 *
 * @author Mgg
 * @since 2021-11-25
 */
@Data
@TableName("member")
@EqualsAndHashCode(callSuper = true)
public class MemberEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 微信openid
     */
    private String openid;
    /**
     * 微信unionid
     */
    private String unionid;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 备注
     */
    private String remark;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 标签ids
     */
    private String tags;

    /**
     * 开始时间(筛选用)
     */
    @TableField(exist = false)
    private Date startTime;

    /**
     * 结束时间(筛选用)
     */
    @TableField(exist = false)
    private Date endTime;

    /**
     * 会员等级id
     */
    private Long levelId;

    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 省代号
     */
    private String provinceCode;

    /**
     * 市名称
     */
    private String cityName;
    /**
     * 市代号
     */
    private String cityCode;

    /**
     * 区名称
     */
    private String areaName;
    /**
     * 区代号
     */
    private String areaCode;

}
