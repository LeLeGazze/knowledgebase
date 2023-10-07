package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberGoodsCollectEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 会员商品收藏表Mapper 接口
 *
 * @author Mgg
 * @since 2021-12-03
 */
public interface MemberGoodsCollectMapper extends BaseMapper<MemberGoodsCollectEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberGoodsCollectEntity
    * @return
    */
    List<MemberGoodsCollectEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberGoodsCollectEntity") MemberGoodsCollectEntity memberGoodsCollectEntity);

    /**
    * 扩展信息记录总数
    * @param memberGoodsCollectEntity
    * @return
    */
    Long extendsCount(@Param("memberGoodsCollectEntity") MemberGoodsCollectEntity memberGoodsCollectEntity);

    /**
    * 会员商品收藏表扩展详情
    * @param id 会员商品收藏表id
    * @return
    */
    MemberGoodsCollectEntity getByIdExtends(@Param("id")Long id);



}

