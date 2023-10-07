package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * oss上传记录 实体类
 *
 * @author castle
 * @since 2022-03-01
 */
@Data
@TableName("castle_sys_oss_record")
public class SysOssRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 文件名
	*/
	private String resourceName;
	/**
	 * 文件地址
	*/
	private String resourceUrl;
	/**
	 * 平台类型
	*/
	private Integer ossPlatform;
	/**
	 * 创建者
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long createUser;
	/**
	 * 创建时间
	*/
	private Date createTime;
	/**
	 * 用户类型
	*/
	private Integer userType;

}
