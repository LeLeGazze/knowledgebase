package com.castle.pdftools.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * PDF/word 等转换成PDF 实体类
 *
 * @author
 * @since 2023-05-08
 */
@Data
@TableName("kb_vide_version")

public class KbVideVersionEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 文件存储路径
     */
    private String fileUrl;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 自动生成的URL图片路径
     */
    private String url;
    /**
     * 文章id
     */
    private Long bId;
    /**
     * 文章MD5值
     */
    private String fId;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 类型（扩展、基础）
     */
    private String type;
    /**
     * 状态：1提取成功、2正在提取中、3：提取失败
     */
    private Integer status;

    private Long id;


    private Long createUser;


    private String createUserName;


    private Date createTime;

    private Long updateUser;

    private Date updateTime;

    private String AccessPath;
    private String downloadUrl;

    private Integer isDeleted;
}
