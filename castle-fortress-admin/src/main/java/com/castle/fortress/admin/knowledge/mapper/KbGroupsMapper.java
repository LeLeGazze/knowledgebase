package com.castle.fortress.admin.knowledge.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.castle.fortress.admin.knowledge.dto.KbGroupsDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbGroupsEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识库用户组管理Mapper 接口
 *
 * @author 
 * @since 2023-04-22
 */
public interface KbGroupsMapper extends BaseMapper<KbGroupsEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbGroupsEntity
    * @return
    */
    List<KbGroupsEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbGroupsEntity") KbGroupsEntity kbGroupsEntity);

    /**
    * 扩展信息记录总数
    * @param kbGroupsEntity
    * @return
    */
    Long extendsCount(@Param("kbGroupsEntity") KbGroupsEntity kbGroupsEntity);

    /**
    * 知识库用户组管理扩展详情
    * @param id 知识库用户组管理id
    * @return
    */
    KbGroupsEntity getByIdExtends(@Param("id")Long id);

    List<KbGroupsDto> findGroupBypIdPage(@Param("id") Long pId,@Param("map") Map<String, Long> pageMap);

    Integer findGroupBypIdPageCount(@Param("id") Long pId);
}
