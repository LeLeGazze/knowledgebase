package com.castle.fortress.admin.member.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberTagEntity;
import java.util.Map;
import java.util.List;
/**
 * 会员标签表Mapper 接口
 *
 * @author whc
 * @since 2022-12-08
 */
public interface MemberTagMapper extends BaseMapper<MemberTagEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberTagEntity
    * @return
    */
    List<MemberTagEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberTagEntity") MemberTagEntity memberTagEntity);

    /**
    * 扩展信息记录总数
    * @param memberTagEntity
    * @return
    */
    Long extendsCount(@Param("memberTagEntity") MemberTagEntity memberTagEntity);

    /**
    * 会员标签表扩展详情
    * @param id 会员标签表id
    * @return
    */
    MemberTagEntity getByIdExtends(@Param("id")Long id);



}
