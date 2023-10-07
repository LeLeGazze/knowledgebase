package com.castle.fortress.admin.system.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 银行信息 实体类
 *
 * @author castle
 * @since 2022-11-02
 */
@Data
@TableName("castle_bank")
public class BankEntity implements Serializable {
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
	 * 银行名称
	*/
	private String bankName;
	/**
	 * 银行简码
	*/
	private String bankCode;
	/**
	 * 银行logo
	*/
	private String bankLogo;

}
