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
 * 代码生成 表结构 配置表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_dev_tb_config")
@ApiModel("表信息配置表")
public class DevTbConfig {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long  id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("数据源主键,必填")
    private Long dbId;
    /**
     * 表名
     */
    @ApiModelProperty("表名,必填")
    private String tbName;
    /**
     * 表注释
     */
    @ApiModelProperty("表注释")
    private String tbDesc;
    /**
     * 后端输出路径
     */
    @ApiModelProperty("后端代码输出路径,必填")
    private String backOutPutDir;
    /**
     * 前端输出路径
     */
    @ApiModelProperty("前端代码输出路径,必填")
    private String frontOutPutDir;
    /**
     * 所在模块名称
     */
    @ApiModelProperty("模块名称,必填")
    private String moduleName;
    /**
     * 所在子模块名称
     */
    @ApiModelProperty("子模块名称,非必填")
    private String subModuleName;
    /**
     * 基本包名
     */
    @ApiModelProperty("基本包名")
    private String packageBase;
    /**
     * 表前缀
     */
    @ApiModelProperty("表前缀")
    private String tbPrefix;
    /**
     * author 注解
     */
    @ApiModelProperty("作者")
    private String author;
    /**
     * 实体类的父类
     */
    @ApiModelProperty("实体类的父类")
    private String superEntityClass;
    /**
     * 是否启用swagger2 YesNoEnum
     */
    @ApiModelProperty("是否启用swagger")
    private Integer swagger2Flag;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 页面类型 1 表格 2 左树右表
     */
    @ApiModelProperty("页面类型")
    private Integer viewType;
    /**
     * 树型结构数据表
     */
    @ApiModelProperty("树型结构数据表")
    private String treeTableName;
    /**
     * 树数据接口
     */
    @ApiModelProperty("树数据接口")
    private String treeUrl;
    /**
     * 树数据value
     */
    @ApiModelProperty("树数据value")
    private String treeValue;
    /**
     * 树数据label
     */
    @ApiModelProperty("树数据label")
    private String treeLabel;
    /**
     * 表关联字段
     */
    @ApiModelProperty("表关联字段")
    private String tableCol;
}
