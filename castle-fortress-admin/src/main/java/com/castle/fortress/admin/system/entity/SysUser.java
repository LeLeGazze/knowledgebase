package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.DataAuthBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 系统用户表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_sys_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统用户表")
public class SysUser extends DataAuthBaseEntity {
    @ApiModelProperty("登录名")
    private String loginName;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("性别")
    private String gender;
    @ApiModelProperty("出生日期")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date birthday;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("证件类型")
    private String idcardType;
    @ApiModelProperty("证件号")
    private String idcard;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("办公电话")
    private String officePhone;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("部门编码")
    private Long deptId;
    @TableField(exist = false)
    @ApiModelProperty("部门名称")
    private String deptName;
    @TableField(exist = false)
    @ApiModelProperty("所有上级部门")
    private String deptParents;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("职位编码")
    private Long postId;
    @TableField(exist = false)
    @ApiModelProperty("职位名称")
    private String postName;
    @TableField(exist = false)
    @ApiModelProperty("角色列表")
    private List<SysRole> roles;
    @TableField(exist = false)
    @ApiModelProperty("分配权限列表")
    private Set<String> permission;
    @TableField(exist = false)
    @ApiModelProperty("是否管理员 true:是;false:否")
    private Boolean isAdmin;
    @TableField(exist = false)
    @ApiModelProperty("是否超级管理员 true:是;false:否")
    private Boolean isSuperAdmin;
    @TableField(exist = false)
    @ApiModelProperty("有数据权限的部门")
    private List<Long> authDept;
    @TableField(exist = false)
    @ApiModelProperty("下级职位")
    private List<Long> subPost;
    @TableField(exist = false)
    @ApiModelProperty("该岗位的数据权限类型")
    private Integer postDataAuth;


    @ApiModelProperty("微信unionId")
    private String unionId;
    @ApiModelProperty("微信openid")
    private String openid;

    @ApiModelProperty("微信绑定时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date wxBindTime;
    @ApiModelProperty("微信头像")
    private String wxAvatar;
    @ApiModelProperty("微信昵称")
    private String wxName;


    @ApiModelProperty("qqOpenid")
    private String qqOpenid;


    @ApiModelProperty("qq绑定时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date qqBindTime;
    @ApiModelProperty("qq头像")
    private String qqAvatar;
    @ApiModelProperty("qq昵称")
    private String qqName;
    @ApiModelProperty("地址")
    private String address;
}
