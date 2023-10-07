package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件传输配置表 实体类
 *
 * @author castle
 * @since 2021-01-04
 */
@Data
@TableName("castle_config_oss")
@EqualsAndHashCode(callSuper = true)
public class ConfigOssEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 文件存储编码
	 */
	private String ftCode;
	/**
	 * 文件存储备注
	 */
	private String remark;
	/**
	 * 存储平台类型
	*/
	private Integer platform;
	/**
	 * 平台配置
	*/
	private String ptConfig;

}
