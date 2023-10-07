package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberAddressEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 会员收货地址表Mapper 接口
 *
 * @author Mgg
 * @since 2021-12-02
 */
public interface MemberAddressMapper extends BaseMapper<MemberAddressEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberAddressEntity
    * @return
    */
    List<MemberAddressEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberAddressEntity") MemberAddressEntity memberAddressEntity);

    /**
    * 扩展信息记录总数
    * @param memberAddressEntity
    * @return
    */
    Long extendsCount(@Param("memberAddressEntity") MemberAddressEntity memberAddressEntity);

    /**
    * 会员收货地址表扩展详情
    * @param id 会员收货地址表id
    * @return
    */
    MemberAddressEntity getByIdExtends(@Param("id")Long id);



}

