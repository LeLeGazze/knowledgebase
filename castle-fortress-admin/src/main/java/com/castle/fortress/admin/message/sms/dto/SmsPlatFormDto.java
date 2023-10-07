package com.castle.fortress.admin.message.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * sms 平台配置对象
 * @author castle
 */
@Data
@ApiModel(value = "sms平台配置对象")
public class SmsPlatFormDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 阿里云相关参数配置
     */
    @ApiModelProperty(value = "阿里云AccessKeyId")
    private String aliyunAccessKeyId;

    @ApiModelProperty(value = "阿里云AccessKeySecret")
    private String aliyunAccessKeySecret;

    @ApiModelProperty(value = "阿里云区域")
    private String aliyunRegionId;

    @ApiModelProperty(value = "阿里云访问域名")
    private String aliyunEndpoint;

    @ApiModelProperty(value = "阿里云短信签名")
    private String aliyunSignName;

    @ApiModelProperty(value = "阿里云短信模板ID")
    private String aliyunTemplateCode;

    /**
     * 腾讯云相关参数配置
     */
    @ApiModelProperty(value = "腾讯云SdkAppid")
    private String tencentcloudSdkAppId;

    @ApiModelProperty(value = "腾讯云SecretId")
    private String tencentcloudSecretId;
    @ApiModelProperty(value = "腾讯云SecretKey")
    private String tencentcloudSecretKey;

    @ApiModelProperty(value = "腾讯云短信签名")
    private String tencentcloudSign;

    @ApiModelProperty(value = "腾讯云短信模板ID")
    private String tencentcloudTemplateId;

    @ApiModelProperty(value = "腾讯云区域")
    private String tencentcloudRegion;

}
