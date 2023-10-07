package com.castle.fortress.admin.knowledge.entity;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;

/**
 * PDF/word 等转换成PDF 实体类
 *
 * @author
 * @since 2023-05-08
 */
@Data
@TableName("kb_vide_version")
@EqualsAndHashCode(callSuper = true)
public class KbVideVersionEntity extends BaseEntity {
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
    private Object url;
    /**
     * 文章id
     */
    @JsonSerialize(using = ToStringSerializer.class)
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

    /**
     *  前端访问路径
     */
    private String AccessPath;

    /**
     * 下载路径
     */
    private String downloadUrl;
    // 自定义url json解析
    public void setUrl(Object url) {
        this.url = JSONUtil.parseArray(url);
    }
}
