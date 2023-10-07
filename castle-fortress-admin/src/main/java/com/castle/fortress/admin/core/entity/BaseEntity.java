package com.castle.fortress.admin.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础父类
 * @author castle
 */
@Data
@ApiModel("BaseEntity对象")
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;

    @ApiModelProperty("状态")
    private Integer status;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("创建者id")
    private Long createUser;
    @TableField(exist = false)
    @ApiModelProperty("创建者姓名")
    private String createUserName;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建时间")
    private Date createTime;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("更新人id")
    private Long updateUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableLogic(value= "2",delval="1")
    @ApiModelProperty("是否删除")
    private Integer isDeleted;

}
