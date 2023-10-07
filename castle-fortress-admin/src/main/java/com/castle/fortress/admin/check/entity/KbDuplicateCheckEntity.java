package com.castle.fortress.admin.check.entity;

import java.util.Date;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

/**
 * 知识库查重表 实体类
 *
 * @author
 * @since 2023-07-15
 */
@Data
@TableName("kb_duplicate_check")
public class KbDuplicateCheckEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 上传路径
     */
    private String uploadPath;
    /**
     * 状态：1=未开始，2=处理中，3=转换PDF中，4=成功，5=失败
     */
    private Integer status;
    /**
     * 检测类型
     */
    private String type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * PDF转换成功后地址
     */
    private String pdfPath;
    /**
     * 检测时间
     */
    private Date detectionTime;
    /**
     * 结束时间
     */
    private Date deadlineTime;

    private Long createUser; // 用户
    private String htmlPath;    //html原文地址
    private int weight; //权重
    private int contextLength;    //上下文长度
    private int maxNumberContiguous;    //最大连续数
    private int readDataLength;    //读取数据长度
    private String estimatedEndTime;    //预计结束时间
    private Integer uploadType; // 上传类型
    private String contrastPath; // 对比路径

}
