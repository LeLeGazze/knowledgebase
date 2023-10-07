package com.castle.fortress.admin.member.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberLevelEntity;
import java.util.Map;
import java.util.List;
/**
 * 会员等级表Mapper 接口
 *
 * @author whc
 * @since 2022-12-29
 */
public interface MemberLevelMapper extends BaseMapper<MemberLevelEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberLevelEntity
    * @return
    */
    List<MemberLevelEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberLevelEntity") MemberLevelEntity memberLevelEntity);

    /**
    * 扩展信息记录总数
    * @param memberLevelEntity
    * @return
    */
    Long extendsCount(@Param("memberLevelEntity") MemberLevelEntity memberLevelEntity);

    /**
    * 会员等级表扩展详情
    * @param id 会员等级表id
    * @return
    */
    MemberLevelEntity getByIdExtends(@Param("id")Long id);



}
