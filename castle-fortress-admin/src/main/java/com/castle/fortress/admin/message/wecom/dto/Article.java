package com.castle.fortress.admin.message.wecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 图文消息类
 */
@Data
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 标题，不超过128个字节，超过会自动截断（支持id转译）
     * 必填
     */
    private String title;
    /**
     * 描述，不超过512个字节，超过会自动截断（支持id转译）
     * 非必填
     */
    private String description;
    /**
     * 点击后跳转的链接。 最长2048字节，请确保包含了协议头(http/https)，小程序或者url必须填写一个
     * 非必填
     */
    private String url;
    /**
     * 	图文消息的图片链接，最长2048字节，支持JPG、PNG格式，较好的效果为大图 1068*455，小图150*150。
     * 	非必填
     */
    private String picurl;
    /**
     * 	小程序appid，必须是与当前应用关联的小程序，appid和pagepath必须同时填写，填写后会忽略url字段
     * 	非必填
     */
    private String appid;
    /**
     * 点击消息卡片后的小程序页面，最长128字节，仅限本小程序内的页面。appid和pagepath必须同时填写，填写后会忽略url字段
     * 非必填
     */
    private String pagepath;
}
