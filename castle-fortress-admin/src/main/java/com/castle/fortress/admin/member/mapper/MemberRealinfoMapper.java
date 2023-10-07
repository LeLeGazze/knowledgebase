package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberRealinfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 会员真实信息表Mapper 接口
 *
 * @author Mgg
 * @since 2021-11-27
 */
public interface MemberRealinfoMapper extends BaseMapper<MemberRealinfoEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberRealinfoEntity
    * @return
    */
    List<MemberRealinfoEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberRealinfoEntity") MemberRealinfoEntity memberRealinfoEntity);

    /**
    * 扩展信息记录总数
    * @param memberRealinfoEntity
    * @return
    */
    Long extendsCount(@Param("memberRealinfoEntity") MemberRealinfoEntity memberRealinfoEntity);

    /**
    * 会员真实信息表扩展详情
    * @param id 会员真实信息表id
    * @return
    */
    MemberRealinfoEntity getByIdExtends(@Param("id")Long id);

    /**
     * 查询多条
     * @param params
     * @return
     */
    List<MemberRealinfoEntity> selectDataList(Map<String, Object> params);
}

