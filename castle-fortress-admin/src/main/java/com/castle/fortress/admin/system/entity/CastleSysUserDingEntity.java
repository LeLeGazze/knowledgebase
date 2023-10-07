package com.castle.fortress.admin.system.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 用户钉钉信息表 实体类
 *
 * @author Mgg
 * @since 2022-12-13
 */
@Data
@TableName("castle_sys_user_ding")
public class CastleSysUserDingEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 用户id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 钉钉unionid
	*/
	private String dingUnionid;
	/**
	 * 钉钉userid
	*/
	private String dingUserid;
	/**
	 * 姓名
	*/
	private String name;
	/**
	 * 成员所属部门id列表
	*/
	private String deptIdList;
	/**
	 * 角色列表(角色ID,名称,角色组名称)
	*/
	private String roleList;
	/**
	 * 手机号
	*/
	private String mobile;
	/**
	 * 邮箱
	*/
	private String email;
	/**
	 * 企业邮箱。
	*/
	private String orgEmail;
	/**
	 * 头像
	*/
	private String avatar;

}
