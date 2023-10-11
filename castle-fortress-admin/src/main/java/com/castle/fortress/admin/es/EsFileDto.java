package com.castle.fortress.admin.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 知识库 文件实体类
 *
 * @author castle
 */
@Data
@ApiModel(value = "KbFileDto对象", description = "知识库文件对象")
//indexName名字如果是字母那么必须是小写字母
@Document(indexName = "kbfilevt343", createIndex = true)
public class EsFileDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @ApiModelProperty(value = "文件名称")
    @JsonProperty("fileName")
    //ik_smart=粗粒度分词 ik_max_word 为细粒度分词
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String fileName;

    @ApiModelProperty(value = "标题")
    @JsonProperty("title")
    //ik_smart=粗粒度分词 ik_max_word 为细粒度分词
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;


    @ApiModelProperty(value = "文件内容")
    @JsonProperty("fileContent")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String fileContent;
    //文件所有人即上传文件人员名称
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String fileOwner;
    @ApiModelProperty(value = "文件类型")
    @JsonProperty("fileType")
    @Field(type = FieldType.Keyword)
    private List<String> fileType;
    @ApiModelProperty(value = "文件分类")
    @JsonProperty("fileCategorys")
    @Field(type = FieldType.Keyword)
    private String fileCategorys;
    @ApiModelProperty(value = "文件标签")
    @JsonProperty("fileTags")
    @Field(type = FieldType.Keyword)
    private List<String> fileTags;
    @ApiModelProperty(value = "录入时间")
    @JsonProperty("fileDate")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date fileDate;
    @ApiModelProperty(value = "来源")
    @JsonProperty("source")
    @Field(type = FieldType.Text)
    private String source;

    /**
     * 目录id
     */
    @Field(type = FieldType.Keyword)
    private String swId;

    /**
     * 分类表示 视频 知识
     */
    @Field(type = FieldType.Keyword)
    private String isVideo;

    /**
     * 是否删除
     */
    @Field(type = FieldType.Keyword)
    private String isDelete;
    @ApiModelProperty(value = "文件物理路径")
    @JsonProperty("filePath")
    @Field(type = FieldType.Text)
    private String filePath;

    @Field(type = FieldType.Keyword)
    private int length;
//    @ApiModelProperty(value = "文件下载路径")
//    @JsonProperty("fileUrl")
//    @Field(type = FieldType.Text)
//    private String fileUrl;
}

