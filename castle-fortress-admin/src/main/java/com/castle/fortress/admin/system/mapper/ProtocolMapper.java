package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.ProtocolEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 协议管理Mapper 接口
 *
 * @author majunjie
 * @since 2022-01-28
 */
public interface ProtocolMapper extends BaseMapper<ProtocolEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param protocolEntity
    * @return
    */
    List<ProtocolEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("protocolEntity") ProtocolEntity protocolEntity);

    /**
    * 扩展信息记录总数
    * @param protocolEntity
    * @return
    */
    Long extendsCount(@Param("protocolEntity") ProtocolEntity protocolEntity);

    /**
    * 协议管理扩展详情
    * @param id 协议管理id
    * @return
    */
    ProtocolEntity getByIdExtends(@Param("id")Long id);

    /**
     * 查询多条
     * @param params
     * @return
     */
    List<ProtocolEntity> selectDataList(Map<String, Object> params);
}

