package com.castle.fortress.admin.system.bindapi.service.impl;

import com.aliyuncs.profile.DefaultProfile;
import com.castle.fortress.admin.system.bindapi.service.BindApiService;
import com.castle.fortress.admin.system.dto.ConfigApiDto;

import java.util.Map;

/**
 * 绑定的阿里云api服务类
 * @@author castle
 */
public class AliBindApiClient implements BindApiService {
    private DefaultProfile defaultProfile;
    private String region;
    private ConfigApiDto platform;

    public AliBindApiClient(ConfigApiDto platform) {
        this.platform = platform;
        this.region="cn-hangzhou";
        this.defaultProfile = DefaultProfile.getProfile(
                this.region,
                platform.getParamMap().get("secretId").toString(),
                platform.getParamMap().get("secretKey").toString());
    }

    @Override
    public String[] keywordsExtraction(String platformCode, String text, Long num) {
        return new String[0];
    }

    @Override
    public String autoSummarization(String platformCode, String text, Long Length) {
        return null;
    }

    @Override
    public Map<String, Object> textCorrection(String platformCode, String text) {
        return null;
    }

    @Override
    public Map<String, Object> textAudit(String platformCode, String text, String detectType) {
        return null;
    }
}
