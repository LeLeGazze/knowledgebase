package com.castle.fortress.admin.knowledge.dto;

import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
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
import java.util.Map;

/**
 * @author 17725
 * @create 2023/4/27 22:51
 */
@Data
public class KbModelTransmitDto {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("id")
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String createUser;

    private Long trashId;

    private Long logId;


    private Long createUserId;


    private Long swId;

    /**
     * 部门
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String deptName;
    /**
     * 收藏状态
     */
    private Integer collectStatus;
    /**
     * 收藏id
     */
    private Long collectId;

    private Long deptId;

    /**
     * 目录名称
     */
    private String swName;

    @JsonProperty("attachments")
    private List<Object> attachments;

    private Object attachmentTmp;


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
    @JsonProperty("labelRes")
    private Object label;

    /**
     * 知识仓库分类Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ctId;
    /**
     * 知识仓库分类名称
     */
    public String ctName;
    /**
     * 旧知识分类名称
     */
    public String oldctName;
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
    private Map<String, Object> cols;
    /**
     * 字段属性
     */
    private List<KbPropertyDesignEntity> propCols;
    /**
     * 状态
     */
    private Integer status;

    private Integer readCount; // 浏览次数
    private Integer uploadCount; //上传次数
    private Integer downloadCount; // 下载次数
    private Integer commentsCount; //评论次数

    private List<KbVideVersionDto> kbVideVersionDtos; // 附件

    private boolean isDownloadAuthority; // 是否有下载权限
    private boolean isUpdateAuthority; // 是否有编辑
    private boolean isDeleteCommentsAuthority; // 是否有删除评论权限
    /**
     * 词云
     */
    @ApiModelProperty(value = "词云")
    @JsonProperty("wordCloud")
    private Object wordCloud;

    public Object getWordCloud() {
        return wordCloud != null ? JSONObject.parseArray(String.valueOf(wordCloud)) : null;
    }

    public Integer getDownloadCount() {
        return downloadCount == null ? 0 : downloadCount;
    }

    public Integer getCommentsCount() {
        return commentsCount == null ? 0 : commentsCount;
    }

    public Integer getReadCount() {
        return readCount == null ? 0 : readCount;
    }

    public Integer getUploadCount() {
        return uploadCount == null ? 0 : uploadCount;
    }

    public Object getLabel() {
       if (label instanceof  String){
           return JSONObject.parseArray(label.toString());
       }
        return label;
    }
}
