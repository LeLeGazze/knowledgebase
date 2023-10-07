package com.castle.fortress.admin.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.log.entity.LogSmsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 短信发送记录Mapper 接口
 *
 * @author Mgg
 * @since 2021-12-06
 */
public interface LogSmsMapper extends BaseMapper<LogSmsEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param logSmsEntity
    * @return
    */
    List<LogSmsEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("logSmsEntity") LogSmsEntity logSmsEntity);

    /**
    * 扩展信息记录总数
    * @param logSmsEntity
    * @return
    */
    Long extendsCount(@Param("logSmsEntity") LogSmsEntity logSmsEntity);

    /**
    * 短信发送记录扩展详情
    * @param id 短信发送记录id
    * @return
    */
    LogSmsEntity getByIdExtends(@Param("id")Long id);

    /**
     * 查询多条数据
     * @param params
     * @return
     */
    List<LogSmsEntity> selectDataList(Map<String, Object> params);

}

