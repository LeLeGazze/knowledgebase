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
 * 对外开放接口秘钥 实体类
 *
 * @author 
 * @since 2021-03-19
 */
@Data
@TableName("castle_api_secret")
public class ApiSecretEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 客户名称
	*/
	private String custName;
	/**
	 * 秘钥id
	*/
	private String secretId;
	/**
	 * 秘钥key
	*/
	private String secretKey;
	/**
	 * 过期日期
	*/
	private Date expiredDate;
	/**
	 * 启用状态
	*/
	private Integer status;

}
