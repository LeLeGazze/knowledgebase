package com.castle.fortress.admin.member.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberWxEntity;
import java.util.Map;
import java.util.List;
/**
 * 微信会员表Mapper 接口
 *
 * @author whc
 * @since 2022-11-28
 */
public interface MemberWxMapper extends BaseMapper<MemberWxEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberWxEntity
    * @return
    */
    List<MemberWxEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberWxEntity") MemberWxEntity memberWxEntity);

    /**
    * 扩展信息记录总数
    * @param memberWxEntity
    * @return
    */
    Long extendsCount(@Param("memberWxEntity") MemberWxEntity memberWxEntity);

    /**
    * 微信会员表扩展详情
    * @param id 微信会员表id
    * @return
    */
    MemberWxEntity getByIdExtends(@Param("id")Long id);



}
