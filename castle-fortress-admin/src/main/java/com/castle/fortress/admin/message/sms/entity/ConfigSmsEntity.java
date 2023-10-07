package com.castle.fortress.admin.message.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.DataAuthBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 短信配置表 实体类
 *
 * @author castle
 * @since 2021-04-12
 */
@Data
@TableName("castle_config_sms")
@EqualsAndHashCode(callSuper = true)
public class ConfigSmsEntity extends DataAuthBaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 短信编码
	*/
	private String smsCode;
	/**
	 * 平台类型
	*/
	private Integer platform;
	/**
	 * 短信配置
	*/
	private String smsConfig;
	/**
	 * 备注
	*/
	private String remark;

}
