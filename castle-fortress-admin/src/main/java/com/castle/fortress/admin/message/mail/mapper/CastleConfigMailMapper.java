package com.castle.fortress.admin.message.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.message.mail.entity.CastleConfigMailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 邮件配置表Mapper 接口
 *
 * @author Mgg
 * @since 2021-10-27
 */
public interface CastleConfigMailMapper extends BaseMapper<CastleConfigMailEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param castleConfigMailEntity
    * @return
    */
    List<CastleConfigMailEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("castleConfigMailEntity") CastleConfigMailEntity castleConfigMailEntity);

    /**
    * 扩展信息记录总数
    * @param castleConfigMailEntity
    * @return
    */
    Long extendsCount(@Param("castleConfigMailEntity") CastleConfigMailEntity castleConfigMailEntity);

    /**
    * 邮件配置表扩展详情
    * @param id 邮件配置表id
    * @return
    */
    CastleConfigMailEntity getByIdExtends(@Param("id")Long id);



}

