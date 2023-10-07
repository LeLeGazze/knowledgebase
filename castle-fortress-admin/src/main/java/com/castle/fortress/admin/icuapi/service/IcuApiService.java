package com.castle.fortress.admin.icuapi.service;

import cn.hutool.json.JSONObject;
import com.castle.fortress.common.entity.RespBody;

/**
 * 调用自营接口
 * @author Castle
 */
public interface IcuApiService {
    /**
     * ip转地址
     */
    RespBody<JSONObject> ipToAddress(String ip);
    /**
     * 身份证信息核验（二要素）
     */
    RespBody<JSONObject> idCardVerification(String name, String idCard );

    /**
     *
     * @param frontImageBase64 正面图片的 Base64 值 （与FrontImageUrl必传其一，都传则只使用FrontImageUrl）
     * @param frontImageUrl 正面图片的 Url 地址（与FrontImageBase64必传其一，都传则只使用FrontImageUrl）
     * @param backImageBase64 反面图片的 Base64 值 （与BackImageUrl必传其一，都传则只使用BackImageUrl）
     * @param backImageUrl 反面图片的 Url 地址（与BackImageBase64必传其一，都传则只使用BackImageUrl）
     * @return
     */
    RespBody<JSONObject> idCardOcr(String frontImageBase64, String frontImageUrl, String backImageBase64,String backImageUrl);


}
