package com.castle.fortress.admin.knowledge.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.knowledge.entity.KbBasicLabelEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
import com.castle.fortress.common.exception.BizException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**kb模型字段接收 实体类
 * @author 17725
 * @create 2023/4/18 17:46
 */

@Data
@ApiModel(value = "kbCategory对象", description = "知识分类表")
public class KbModelAcceptanceDto {
    private static final long serialVersionUID = 1L;

    private Long id ; //主键

    /**
     * 主题仓库ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long whId;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long auth;
    /**
     * 部门ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    @JsonProperty("attachments")
    private List<Object> attachments;
    /**
     * 发布时间
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date pubTime;
    /**
     * 过期时间
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date expTime;
    @ApiModelProperty(value = "标签")
    @JsonProperty("label")
    private  List<String> label;
    @ApiModelProperty(value = "标签")
    @JsonProperty("labels")
    private  List<KbModelLabelEntity> labels;
    /**
     * 知识仓库分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;
    /**
     * 模型编码
     */
    private String modelCode;
    /**
     * 模型id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modelId;
    /**
     * 模型名称
     */
    private String modelName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 创始人
     */
    private Long createUser;
    /**
     * 创建人姓名
     */
    private String createName;
    /**
     * 标记删除
     */
    private Integer isDeleted;
    /**
     * 字段属性赋值
     */
    private Map<String,Object> cols;
    /**
     * 字段属性
     */
    private List<KbPropertyDesignEntity> propCols;
    /**
     * 状态
     */
    private Integer status;
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
}
