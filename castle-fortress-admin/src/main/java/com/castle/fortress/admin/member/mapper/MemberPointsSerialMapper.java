package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberPointsSerialEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 会员积分流水Mapper 接口
 *
 * @author Mgg
 * @since 2021-12-01
 */
public interface MemberPointsSerialMapper extends BaseMapper<MemberPointsSerialEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberPointsSerialEntity
    * @return
    */
    List<MemberPointsSerialEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberPointsSerialEntity") MemberPointsSerialEntity memberPointsSerialEntity);

    /**
    * 扩展信息记录总数
    * @param memberPointsSerialEntity
    * @return
    */
    Long extendsCount(@Param("memberPointsSerialEntity") MemberPointsSerialEntity memberPointsSerialEntity);

    /**
    * 会员积分流水扩展详情
    * @param id 会员积分流水id
    * @return
    */
    MemberPointsSerialEntity getByIdExtends(@Param("id")Long id);

}

