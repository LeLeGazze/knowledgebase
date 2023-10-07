package com.castle.fortress.admin.message.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.message.sms.entity.ConfigSmsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 短信配置表Mapper 接口
 *
 * @author castle
 * @since 2021-04-12
 */
public interface ConfigSmsMapper extends BaseMapper<ConfigSmsEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param configSmsEntity
    * @return
    */
    List<ConfigSmsEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("configSmsEntity") ConfigSmsEntity configSmsEntity);

    /**
    * 扩展信息记录总数
    * @param configSmsEntity
    * @return
    */
    Long extendsCount(@Param("configSmsEntity") ConfigSmsEntity configSmsEntity);

    /**
    * 短信配置表扩展详情
    * @param id 短信配置表id
    * @return
    */
    ConfigSmsEntity getByIdExtends(@Param("id")Long id);



}

