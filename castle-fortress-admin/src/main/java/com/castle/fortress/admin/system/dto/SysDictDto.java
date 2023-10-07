package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统字典表
 *
 * @author castle
 */
@Data
@ApiModel("系统字典表")
public class SysDictDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("父级id")
    private Long parentId;
    @ApiModelProperty("字典编码")
    private String code;
    @ApiModelProperty("字典key")
    private String dictKey;
    @ApiModelProperty("字典value")
    private String dictValue;
    @ApiModelProperty("字典排序")
    private Integer sort;
    @ApiModelProperty("字典备注")
    private String remark;
    @ApiModelProperty("字典key-value集合")
    private List<SysDictDto> children;
    @ApiModelProperty(value = "状态 YesNoEnum。yes生效；no失效")
    private Integer status;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "创建人")
    private Long createUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "修改人")
    private Long updateUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "是否删除 YesNoEnum。 yes删除；no未删除")
    private Integer isDeleted;
}
