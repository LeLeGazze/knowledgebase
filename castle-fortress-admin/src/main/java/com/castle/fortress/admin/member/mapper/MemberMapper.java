package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 会员表Mapper 接口
 *
 * @author Mgg
 * @since 2021-11-25
 */
public interface MemberMapper extends BaseMapper<MemberEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberEntity
    * @return
    */
    List<MemberEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberEntity") MemberEntity memberEntity);

    /**
    * 扩展信息记录总数
    * @param memberEntity
    * @return
    */
    Long extendsCount(@Param("memberEntity") MemberEntity memberEntity);

    /**
    * 会员表扩展详情
    * @param id 会员表id
    * @return
    */
    MemberEntity getByIdExtends(@Param("id")Long id);



}

