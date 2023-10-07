package com.castle.fortress.admin.system.dto;

import com.castle.fortress.admin.system.entity.SysRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 系统用户表
 *
 * @author castle
 */
@Data
@ApiModel(value = "sysUserDto对象", description = "系统用戶表")
public class SysUserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;
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
    @ApiModelProperty("办公电话")
    private String officePhone;
    @ApiModelProperty("性别")
    private String gender;
    @ApiModelProperty("出生日期")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date birthday;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("群组名称")
    private String groupName;
    @ApiModelProperty("群组Id")
    private String groupId;
    @ApiModelProperty("证件类型")
    private String idcardType;
    @ApiModelProperty("证件号")
    private String idcard;
    @ApiModelProperty("备注")
    private String remark;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("部门编码")
    private Long deptId;
    @ApiModelProperty("部门名称")
    private String deptName;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("职位编码")
    private Long postId;
    @ApiModelProperty("职位名称")
    private String postName;
    @ApiModelProperty("角色列表")
    private List<SysRole> roles;
    @ApiModelProperty("分配权限列表")
    private Set<String> permission;
    @ApiModelProperty("是否管理员 true:是;false:否")
    private Boolean isAdmin;
    @ApiModelProperty("是否超级管理员 true:是;false:否")
    private Boolean isSuperAdmin;
    @ApiModelProperty("有数据权限的部门")
    private List<Long> authDept;
    @ApiModelProperty("下级职位")
    private List<Long> subPost;
    @ApiModelProperty("该岗位的数据权限类型")
    private Integer postDataAuth;
    @ApiModelProperty("状态")
    private Integer status;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("创建者id")
    private Long createUser;
    @ApiModelProperty("创建者姓名")
    private String createUserName;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建时间")
    private Date createTime;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("更新人id")
    private Long updateUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("创建部门")
    private Long createDept;
    @ApiModelProperty("创建部门名称")
    private String createDeptName;
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("创建职位")
    private Long createPost;
    @ApiModelProperty("创建职位名称")
    private String createPostName;
    @ApiModelProperty("数据权限校验标识 true 校验数据权限 false 不校验数据权限")
    private Boolean dataAuthFlag=true;
    @ApiModelProperty("原密码")
    private String oldPassword;
    @ApiModelProperty("旧手机号")
    private String oldPhone;
    @ApiModelProperty("验证码")
    private String captcha;
    @ApiModelProperty("旧手机验证码")
    private String oldCaptcha;



    @ApiModelProperty("unionId")
    private String unionId;
    @ApiModelProperty("openid")
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
