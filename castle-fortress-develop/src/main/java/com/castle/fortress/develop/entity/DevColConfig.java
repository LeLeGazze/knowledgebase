package com.castle.fortress.develop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 代码生成 表字段 配置表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_dev_col_config")
@ApiModel("表字段配置表")
public class DevColConfig {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long  id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("表信息主键")
    private Long  tbId;
    /**
     * 字段名
     */
    @ApiModelProperty("字段名")
    private String  colName;
    /**
     * 属性名
     */
    @ApiModelProperty("属性名")
    private String propName;
    /**
     * 字段类型
     */
    @ApiModelProperty("字段类型")
    private String colType;
    /**
     * 属性类型
     */
    @ApiModelProperty("属性类型")
    private String propType;
    /**
     * 属性注释
     */
    @ApiModelProperty("属性注释")
    private String propDesc;
    /**
     * 是否在列表中展示 YesNoEnum
     */
    @ApiModelProperty("是否在列表中展示")
    private Integer isList;
    /**
     * 是否复制 YesNoEnum
     */
    @ApiModelProperty("是否复制")
    private Integer isCopy;
    /**
     * 是否在查询条件中 YesNoEnum
     */
    @ApiModelProperty("是否在查询条件中")
    private Integer isQuery;
    /**
     * 查询条件
     */
    @ApiModelProperty("查询条件")
    private String queryType;
    /**
     * 是否包含在表单中 YesNoEnum
     */
    @ApiModelProperty("是否包含在表单中")
    private Integer isForm;
    /**
     * 是否必填 YesNoEnum
     */
    @ApiModelProperty("是否表单必填")
    private Integer isFormRequire;
    /**
     * 自定义校验类型 ValidateTypeEnum
     */
    @ApiModelProperty("自定义校验类型")
    private Integer validateType;
    /**
     * 自定义校验类型名称 ValidateTypeEnum
     */
    @ApiModelProperty("自定义校验类型名称")
    @TableField(exist = false)
    private String validateTypeName;
    /**
     * 表单类型
     */
    @ApiModelProperty("表单类型")
    private Integer formType;
    /**
     * 集合数据类型
     */
    @ApiModelProperty("集合数据类型")
    private Integer listdataType;
    /**
     * 集合数据配置
     */
    @ApiModelProperty("集合数据配置")
    private String listdataConfig;

    /**
     * 属性名首字母大写
     */
    @ApiModelProperty("属性名首字母大写")
    @TableField(exist = false)
    private String propNameUpperFirst;
    /**
     * 数据集合名称
     */
    @ApiModelProperty("数据集合名称")
    @TableField(exist = false)
    private String dataListName;

    /**
     * enum配置对象
     */
    @ApiModelProperty("enum配置对象")
    @TableField(exist = false)
    private Map<String,String> enumObj;

    /**
     * 接口配置对象
     */
    @ApiModelProperty("接口配置对象")
    @TableField(exist = false)
    private Map<String,String> urlObj;
    /**
     * 字典配置对象
     */
    @ApiModelProperty("字典配置对象")
    @TableField(exist = false)
    private String dictName;

    /**
     * json配置对象
     */
    @ApiModelProperty("json配置对象")
    @TableField(exist = false)
    private String jsonObj;



}
