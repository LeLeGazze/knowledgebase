package com.castle.fortress.admin.check.es;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "tmpcheck对象", description = "知识库文件对象")
//indexName名字如果是字母那么必须是小写字母
@Document(indexName = "tmpcheck", createIndex = true)
@Accessors(chain = true)
public class EsTmpCheck implements Serializable {
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

    @Field(type = FieldType.Keyword)
    private int length;


    @Field(type = FieldType.Keyword)
    private String fileMd5; // 文件md5

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
}

