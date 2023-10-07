package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbBasicLabelEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
/**
 * 知识与标签的中间表Mapper 接口
 *
 * @author sunhr
 * @since 2023-04-28
 */
@Mapper
public interface KbBasicLabelMapper extends BaseMapper<KbBasicLabelEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbBasicLabelEntity
    * @return
    */
    List<KbBasicLabelEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbBasicLabelEntity") KbBasicLabelEntity kbBasicLabelEntity);

    /**
    * 扩展信息记录总数
    * @param kbBasicLabelEntity
    * @return
    */
    Long extendsCount(@Param("kbBasicLabelEntity") KbBasicLabelEntity kbBasicLabelEntity);

    /**
    * 知识与标签的中间表扩展详情
    * @param id 知识与标签的中间表id
    * @return
    */
    KbBasicLabelEntity getByIdExtends(@Param("id")Long id);


    KbBasicLabelEntity findById(@Param("l_id") Long l_id ,@Param("bId") Long bid);

    int delectByIds(@Param("b_id") Long id);
    KbBasicLabelEntity findById(@Param("l_id") String l_id ,@Param("bId") Long bid);

    List<Long> selectBidByLid(@Param("id") Long id);


    List<String> findByUidAuthLabelAdmin(@Param("uid") Long uid,@Param("TopNum") Long topNum);

    List<String> findByUidAuthLabel(@Param("uid") Long uid, @Param("TopNum") Long topNum,@Param("kb_auths") List<Integer> kb_auths);

    Integer labelCount(@Param("lebalId") Long lebalId);

    List<KbModelLabelEntity> listBybId(@Param("basicId") Long id);

   List<Map<String,Object>> findByLabelIdToBasic(@Param("list") List<Long> lablelIdList);

    void deleteByBid(@Param("arrayList") List<Long> arrayList);

    List<KbBaseLabelTaskEntity> findByBid(@Param("arrayList") ArrayList<KbBaseLabelTaskEntity> arrayList);

    List<KbModelLabelEntity> selectLidByBid(@Param("basicId") Long id);
}
