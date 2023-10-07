package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberAccountSerialEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 会员账户流水Mapper 接口
 *
 * @author Mgg
 * @since 2021-12-02
 */
public interface MemberAccountSerialMapper extends BaseMapper<MemberAccountSerialEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberAccountSerialEntity
    * @return
    */
    List<MemberAccountSerialEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberAccountSerialEntity") MemberAccountSerialEntity memberAccountSerialEntity);

    /**
    * 扩展信息记录总数
    * @param memberAccountSerialEntity
    * @return
    */
    Long extendsCount(@Param("memberAccountSerialEntity") MemberAccountSerialEntity memberAccountSerialEntity);

    /**
    * 会员账户流水扩展详情
    * @param id 会员账户流水id
    * @return
    */
    MemberAccountSerialEntity getByIdExtends(@Param("id")Long id);



}

