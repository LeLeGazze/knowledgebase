package com.castle.fortress.admin.shiro.entity;

import com.castle.fortress.admin.system.entity.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 扩展的用户详情
 * @author castle
 */
@Data
public class CastleUserDetail {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 出生日期
     */
    private Date birthdate;

    /**
     * 性别
     */
    private String gender;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 部门编码
     */
    private Long deptId;
    private String deptName;

    /**
     * 所有上级部门
     */
    private String deptParents;
    /**
     * 职位编码
     */
    private Long postId;

    /**
     * 用户类型 取自 UserTypeEnum
     */
    private String userType;
    /**
     * 角色列表
     */
    private List<SysRole> roles;
    /**
     * 是否超管 true 是 false 否
     */
    private Boolean isAdmin;
    /**
     * 是否超管 true 是 false 否
     */
    private Boolean isSuperAdmin;
    /**
     * 权限集合
     */
    private Set<String> permission;
    /**
     * 有数据权限的部门
     */
    private List<Long> authDept;
    /**
     * 下级职位
     */
    private List<Long> subPost;
    /**
     * 该岗位的数据权限类型
     */
    private Integer postDataAuth;

}
