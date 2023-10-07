package com.castle.fortress.develop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 代码生成 数据源 配置表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_dev_db_config")
@ApiModel("数据源配置表")
public class DevDbConfig {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long  id;
    @ApiModelProperty("登录名")
    private String dbUsername;
    @ApiModelProperty("登录密码")
    private String dbPassword;
    @ApiModelProperty("连接字符串")
    private String dbUrl;
    @ApiModelProperty("驱动类")
    private String dbDriverName;
    /**
     * 数据源名称
     */
    @ApiModelProperty("数据源名称")
    private String dbName;
    /**
     * 是否启用 YesNoEnum
     */
    @ApiModelProperty("是否启用")
    private Integer status;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建时间")
    private Date createTime;

}
