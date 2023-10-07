package com.castle.fortress.admin.knowledge.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.common.exception.BizException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 模型属性设计表 实体类
 *
 * @author Pan Chen
 * @since 2023-04-11
 */
@Data
@ApiModel(value = "kbPropertyDesign对象", description = "模型属性设计表")
public class KbPropertyDesignDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键 ID")
    @JsonProperty("id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "与模型表的关联字段")
    @JsonProperty("modelId")
    private Long modelId;
    @ApiModelProperty(value = "属性名字")
    @JsonProperty("name")
    private String name;
    @ApiModelProperty(value = "属性编码（用于代码中引用）")
    @JsonProperty("propName")
    private String propName;
    @ApiModelProperty(value = "序号")
    @JsonProperty("sort")
    private Integer sort;
    @ApiModelProperty(value = "是否为必填项")
    @JsonProperty("isRequired")
    private Integer isRequired;
    @ApiModelProperty(value = "是否为筛选项")
    @JsonProperty("isFiltrate")
    private Integer isFiltrate;
    @ApiModelProperty(value = "表单类型（短文本、文本等）")
    @JsonProperty("formType")
    private Integer formType;
    @ApiModelProperty(value = "列表类型数据配置")
    @JsonProperty("datType")
    private String dataType;
    @ApiModelProperty(value = "附件")
    @JsonProperty("attachment")
    private String attachment;
    @ApiModelProperty(value = "状态 YesNoEnum。Yes启用；no 禁用")
    @JsonProperty("status")
    private Integer status;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "修改人")
    @JsonProperty("updateUser")
    private Long updateUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "修改时间")
    @JsonProperty("updateTime")
    private Date updateTime;
    @ApiModelProperty(value = "创建者姓名")
    @JsonProperty("createUserName")
    private String createUserName;
    @ApiModelProperty(value = "标记删除")
    @JsonProperty("isDeleted")
    private Integer isDeleted;
    @ApiModelProperty(value = "下拉框")
    @JsonProperty("selectFrame")
    private Object selectFrame;


    public void setSelectFrame(Object selectFrame) {
        if (selectFrame instanceof ArrayList) {
            this.selectFrame = JSONObject.toJSON(selectFrame);
        } else {
            this.selectFrame = selectFrame;
        }
    }

    public KbPropertyDesignDto() {
    }


}
