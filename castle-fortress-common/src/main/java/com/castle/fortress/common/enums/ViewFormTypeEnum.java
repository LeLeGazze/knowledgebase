package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 视图表单类型枚举
 * @author castle
 */
@Getter
@AllArgsConstructor
public enum ViewFormTypeEnum {
    TEXT(1,"单行文本","单行文本")
    ,MULTI_TEXT(2,"多行文本","多行文本")
    ,RICH_TEXT(3,"富文本","富文本")
    ,SELECT(4,"下拉框","下拉框")
    ,RADIO(5,"单选框","单选框")
    ,CHECK_BOX(6,"复选框","复选框")
    ,DATE(7,"日期","日期")
    ,DATE_TIME(8,"日期时间","日期时间")
    ,IMAGE_UPLOAD(9,"图片上传组件","图片上传组件")
    ,FILE_UPLOAD(10,"文件上传组件","文件上传组件")
    ,VIDEO_UPLOAD(11,"视频上传组件","视频上传组件")
    ,SWITCH(12,"开关","开关")
    ,NUMBER(13,"计数器","计数器")
    ;
    Integer code;
    String name;
    String desc;
}
