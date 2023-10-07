package com.castle.fortress.admin.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.log.entity.LogLoginEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 登录操作日志Mapper 接口
 *
 * @author castle
 * @since 2021-04-01
 */
public interface LogLoginMapper extends BaseMapper<LogLoginEntity> {


    List<LogLoginEntity> logLoginList(@Param("map") Map<String, Long> pageMap,@Param("logLoginEntity") LogLoginEntity logLoginEntity);

    Long logLoginCount(@Param("logLoginEntity") LogLoginEntity logLoginEntity);
}

