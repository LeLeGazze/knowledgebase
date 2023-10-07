package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 文件下载配置表 实体类
 *
 * @author 
 * @since 2023-06-25
 */
@Data
@TableName("kb_download_conf")
@EqualsAndHashCode(callSuper = true)
public class KbDownloadConfEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 类型名称
	*/
	private String name;
	/**
	 * 备注
	*/
	private String remark;
	/**
	 * 状态 1: 水印 2: 下载
	*/
	private Integer type;

}
