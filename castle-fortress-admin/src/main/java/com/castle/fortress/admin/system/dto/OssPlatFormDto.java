package com.castle.fortress.admin.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 存储 平台配置对象
 * @author castle
 */
@Data
@ApiModel(value = "存储平台配置对象")
public class OssPlatFormDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 本地存储相关参数配置
     */
    @ApiModelProperty(value = "文件存储位置")
    private String localFilePosition;
    @ApiModelProperty(value = "文件访问路径")
    private String localFileUrl;

    /**
     * 阿里云存储相关参数配置
     */
    @ApiModelProperty(value = "阿里云访问域名(访问用的域名)")
    private String aliyunDomain;
    @ApiModelProperty(value = "阿里云访问域名(Endpoint)")
    private String aliyunEndpoint;
    @ApiModelProperty(value = "阿里云存储空间")
    private String aliyunBucket;
    @ApiModelProperty(value = "阿里云AccessKey")
    private String aliyunAccessKey;
    @ApiModelProperty(value = "阿里云SecretKey")
    private String aliyunSecretKey;
    @ApiModelProperty(value = "阿里云路径前缀,以斜线结尾")
    private String aliyunPathPrefix;

    /**
     * 京东云存储相关参数配置
     */
    @ApiModelProperty(value = "京东云访问域名")
    private String jdcloudEndpoint;
    @ApiModelProperty(value = "京东云地区")
    private String jdcloudRegion;
    @ApiModelProperty(value = "京东云AccessKey")
    private String jdcloudAccessKey;
    @ApiModelProperty(value = "京东云SecretKey")
    private String jdcloudSecretKey;
    @ApiModelProperty(value = "京东云存储空间")
    private String jdcloudBucket;
    @ApiModelProperty(value = "京东云路径前缀,以斜线结尾")
    private String jdcloudPathPrefix;

    /**
     * 腾讯云存储相关参数配置
     */
    @ApiModelProperty(value = "腾讯云secretId")
    private String cosSecretId;
    @ApiModelProperty(value = "腾讯云secretKey")
    private String cosSecretKey;
    @ApiModelProperty(value = "腾讯云地域")
    private String cosRegion;
    @ApiModelProperty(value = "腾讯云bucketName")
    private String cosBucketName;
    @ApiModelProperty(value = "腾讯云AppId")
    private String cosAppId;
    @ApiModelProperty(value = "腾讯云访问域名")
    private String cosEndpoint;
    @ApiModelProperty(value = "腾讯云路径前缀,以斜线结尾")
    private String cosPathPrefix;

    // 1 压缩 2 不压缩
    @ApiModelProperty(value = "压缩标识")
    private String compressFlag;

}
