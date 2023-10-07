package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.ApiSecretEntity;
/**
 * 对外开放接口秘钥Mapper 接口
 *
 * @author 
 * @since 2021-03-19
 */
public interface ApiSecretMapper extends BaseMapper<ApiSecretEntity> {

    /**
     * 过期当前时间失效的秘钥
     * @return
     */
    Integer expiredSecrets();
}

