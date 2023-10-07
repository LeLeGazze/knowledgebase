package com.castle.fortress.admin.system.bindapi.service;

import java.util.Map;

/**
 * 绑定的第三方api服务类
 * @@author castle
 */
public interface BindApiService {

    /**
     * 关键词提取
     * @param platformCode 平台编码
     * @param text 要提取的字符串
     * @param num 提取的关键字个数 默认是5个
     * @return
     */
    public String[] keywordsExtraction(String platformCode,String text,Long num);

    /**
     * 自动摘要
     * @param platformCode 平台编码
     * @param text 要提取的字符串
     * @param Length 摘要长度，默认200
     * @return
     */
    public String autoSummarization(String platformCode,String text,Long Length);

    /**
     * 文本纠错
     * @param platformCode 平台编码
     * @param text 要纠错的文本
     * @return
     */
    public Map<String,Object> textCorrection(String platformCode,String text);

    /**
     * 文本审核
     * @param platformCode 平台编码
     * @param text 要审核的文本
     * @param detectType 审核的场景类型
     * @return
     */
    public Map<String,Object> textAudit(String platformCode,String text,String detectType);

    /**
     * 获取语音实时识别的websocket地址
     * @param platformCode
     * @param engineModelType
     * @param voiceFormat
     * @return
     */
    default String getAsrWs(String platformCode, String engineModelType, String voiceFormat){
        return "";
    }
}
