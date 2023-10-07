package com.castle.fortress.admin.knowledge.dto;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.common.exception.BizException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 模型表 实体类
 *
 * @author Pan Chen
 * @since 2023-04-11
 */
@Data
@ApiModel(value = "kbModel对象", description = "模型表")
public class KbModelDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键 ID")
    @JsonProperty("id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "模型名字")
    @JsonProperty("name")
    private String name;
    @ApiModelProperty(value = "属性编码（用于代码中引用）")
    @JsonProperty("code")
    private String code;
    @ApiModelProperty(value = "序号")
    @JsonProperty("sort")
    private Integer sort;
    @ApiModelProperty(value = "状态")
    @JsonProperty("status")
    private Integer status;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "创建人")
    @JsonProperty("createUser")
    private Long createUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "创建时间")
    @JsonProperty("createTime")
    private Date createTime;
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
    @ApiModelProperty(value = "是否已删除")
    @JsonProperty("isDeleted")
    private Integer isDeleted;
    @ApiModelProperty(value = "模型通用字段列表")
    @JsonProperty("sysCols")
    private List<KbPropertyDesignDto> sysCols;
    @ApiModelProperty(value = "模型字段列表")
    @JsonProperty("cols")
    private List<KbPropertyDesignDto> cols;
    @ApiModelProperty(value = "下拉框")
    @JsonProperty("selectFrame")
    private Object selectFrame;

    public Object getSelectFrame() {
        if (selectFrame ==null){
            return new ArrayList<>();
        }
        if (selectFrame instanceof String) {
            try {
                return JSONArray.parseArray(selectFrame.toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException("下拉框格式错误");
            }
        }
        return selectFrame;
    }

    public void setSelectFrame(Object selectFrame) {
        if (selectFrame instanceof ArrayList) {
            this.selectFrame = JSONObject.toJSON(selectFrame);
        } else {
            this.selectFrame = selectFrame;
        }
    }
}
