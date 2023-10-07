package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.ApiSecretDto;
import com.castle.fortress.admin.system.entity.ApiSecretEntity;
import com.castle.fortress.common.entity.RespBody;

import java.util.List;

/**
 * 对外开放接口秘钥 服务类
 *
 * @author 
 * @since 2021-03-19
 */
public interface ApiSecretService extends IService<ApiSecretEntity> {

    /**
     * 分页展示对外开放接口秘钥列表
     * @param page
     * @param apiSecretDto
     * @return
     */
    IPage<ApiSecretDto> pageApiSecret(Page<ApiSecretDto> page, ApiSecretDto apiSecretDto);


    /**
     * 展示对外开放接口秘钥列表
     * @param apiSecretDto
     * @return
     */
    List<ApiSecretDto> listApiSecret(ApiSecretDto apiSecretDto);

    /**
     * 通过secretId查询信息 用于API接口调用
     * @param apiSecretId
     * @return
     */
    RespBody<ApiSecretEntity> querySecretKey(String apiSecretId);

    /**
     * 过期当前时间失效的秘钥
     * @return
     */
    Integer expiredSecrets();
}
