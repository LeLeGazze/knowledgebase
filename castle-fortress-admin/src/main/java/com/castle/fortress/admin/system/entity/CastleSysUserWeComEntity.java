package com.castle.fortress.admin.system.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 用户企业微信信息表 实体类
 *
 * @author mjj
 * @since 2022-11-30
 */
@Data
@TableName("castle_sys_user_we_com")
public class CastleSysUserWeComEntity implements Serializable {
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
	 * 企微userid
	*/
	private String weComUserid;
	/**
	 * 姓名
	*/
	private String name;
	/**
	 * 成员所属部门id列表
	*/
	private String department;
	/**
	 * 职务信息
	*/
	private String position;
	/**
	 * 手机号
	*/
	private String mobile;
	/**
	 * 邮箱
	*/
	private String email;
	/**
	 * 性别 0表示未定义，1表示男性，2表示女性
	*/
	private String gender;
	/**
	 * 激活状态 1=已激活，2=已禁用，4=未激活，5=退出企业。
	*/
	private String status;
	/**
	 * 头像
	*/
	private String avatar;
	/**
	 * 未知意义字段
	*/
	private String isleader;
	/**
	 * 扩展属性
	*/
	private String extattr;
	/**
	 * 英文名
	*/
	private String englishName;
	/**
	 * 座机
	*/
	private String telephone;
	/**
	 * 未知意义字段
	*/
	private String enable;
	/**
	 * 未知意义字段
	*/
	private String hideMobile;
	/**
	 * 部门内的排序值
	*/
	private String sort;
	/**
	 * 成员对外属性
	*/
	private String externalProfile;
	/**
	 * 主部门 仅当应用对主部门有查看权限时返回
	*/
	private String mainDepartment;
	/**
	 * 员工个人二维码
	*/
	private String qrCode;
	/**
	 * 别名
	*/
	private String alias;
	/**
	 * 表示在所在的部门内是否为部门负责人，数量与department一致
	*/
	private String isLeaderInDept;
	/**
	 * 地址
	*/
	private String address;
	/**
	 * 头像缩略图url
	*/
	private String thumbAvatar;
	/**
	 * 直属上级UserID
	*/
	private String directLeader;
	/**
	 * 企业邮箱
	*/
	private String bizMail;
	/**
	 * 全局唯一id 仅第三方应用可获取
	*/
	private String openUserid;
	/**
	 * 创建时间
	*/
	private Date createTime;

}
