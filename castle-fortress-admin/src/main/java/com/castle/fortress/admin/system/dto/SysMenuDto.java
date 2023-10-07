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
 * 系统菜单表
 *
 * @author castle
 */
@Data
@ApiModel("系统菜单表")
public class SysMenuDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("父级id")
    private Long parentId;
    @ApiModelProperty("授权编码,多个用分号隔开")
    private String permissions;
    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("菜单路径")
    private String url;
    @ApiModelProperty("菜单图标")
    private String icon;
    @ApiModelProperty("菜单类型")
    private Integer type;
    @ApiModelProperty("菜单排序")
    private Integer sort;
    @ApiModelProperty("页面路径")
    private String viewPath;
    @ApiModelProperty("菜单备注")
    private String remark;
    @ApiModelProperty("下级菜单或按钮")
    private List<SysMenuDto> children;
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
    @ApiModelProperty(value = "是否选中")
    private Integer isChecked;
}
