package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberBankCardEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 会员银行卡表Mapper 接口
 *
 * @author Mgg
 * @since 2021-12-03
 */
public interface MemberBankCardMapper extends BaseMapper<MemberBankCardEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberBankCardEntity
    * @return
    */
    List<MemberBankCardEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberBankCardEntity") MemberBankCardEntity memberBankCardEntity);

    /**
    * 扩展信息记录总数
    * @param memberBankCardEntity
    * @return
    */
    Long extendsCount(@Param("memberBankCardEntity") MemberBankCardEntity memberBankCardEntity);

    /**
    * 会员银行卡表扩展详情
    * @param id 会员银行卡表id
    * @return
    */
    MemberBankCardEntity getByIdExtends(@Param("id")Long id);

    /**
     * 查询多条
     * @param params
     * @return
     */
    List<MemberBankCardEntity> selectDataList(Map<String, Object> params);
}

