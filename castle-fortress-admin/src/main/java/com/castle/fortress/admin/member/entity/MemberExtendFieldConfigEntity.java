package com.castle.fortress.admin.member.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 用户扩展字段配置表 实体类
 *
 * @author whc
 * @since 2022-11-23
 */
@Data
@TableName("member_extend_field_config")
public class MemberExtendFieldConfigEntity implements Serializable {
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
	 * 字段标题
	*/
	private String colTitle;
	/**
	 * 字段名
	*/
	private String colName;
	/**
	 * 属性名
	*/
	private String propName;
	/**
	 * 字段类型
	*/
	private String colType;

	/**
	 * 表单类型
	 */
	private Integer formType;

	/**
	 * 排序字段
	 */
	private Integer sort;

	/**
	 * 集合数据类型
	 */
	private Integer dataType;
	/**
	 * 集合数据配置
	 */
	private String dataConfig;
}
