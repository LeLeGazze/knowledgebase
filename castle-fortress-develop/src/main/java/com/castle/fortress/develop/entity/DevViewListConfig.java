package com.castle.fortress.develop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码生成 视图字段展示 配置表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_sys_view_table_list")
@ApiModel("视图字段展示")
public class DevViewListConfig {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long  id;
    /**
     * 表名
     */
    @ApiModelProperty("表名")
    private String  tbName;
    /**
     * 属性名
     */
    @ApiModelProperty("属性名")
    private String propName;
    /**
     * 属性注释
     */
    @ApiModelProperty("属性注释")
    private String propDesc;

}
