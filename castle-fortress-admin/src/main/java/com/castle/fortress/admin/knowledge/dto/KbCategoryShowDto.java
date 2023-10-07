package com.castle.fortress.admin.knowledge.dto;


import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 知识分类表 实体类 首页展示
 *
 * @author
 * @since 2023-04-24
 */
@Data
@ApiModel(value = "kbCategory对象", description = "知识分类表")
public class KbCategoryShowDto {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键ID")
    @JsonProperty("id")
    private Long id;
    @ApiModelProperty(value = "分类名称")
    @JsonProperty("name")
    private String name;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "知识仓库")
    @JsonProperty("swId")
    private Long swId;
    @ApiModelProperty(value = "目录名称")
    @JsonProperty("swName")
    private String swName;
}
