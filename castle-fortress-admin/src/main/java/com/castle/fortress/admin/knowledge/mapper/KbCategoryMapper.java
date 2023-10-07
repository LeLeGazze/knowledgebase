package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbCategoryShowDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbCategoryEntity;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * 知识分类表Mapper 接口
 *
 * @author
 * @since 2023-04-24
 */
public interface KbCategoryMapper extends BaseMapper<KbCategoryEntity> {

    /**
     * 扩展信息列表
     *
     * @param pageMap
     * @param kbCategoryEntity
     * @return
     */
    List<KbCategoryEntity> extendsList(@Param("map") Map<String, Long> pageMap, @Param("kbCategoryEntity") KbCategoryEntity kbCategoryEntity);

    /**
     * 扩展信息记录总数
     *
     * @param kbCategoryEntity
     * @return
     */
    Long extendsCount(@Param("kbCategoryEntity") KbCategoryEntity kbCategoryEntity);

    /**
     * 知识分类表扩展详情
     *
     * @param id 知识分类表id
     * @return
     */
    KbCategoryEntity getByIdExtends(@Param("id") Long id);


    /**
     * 查询当前用户有哪些分类权限
     *
     * @param uid      登录用户
     * @param wh_id    知识仓库id
     * @param kb_auths 权限
     * @return
     */

    List<KbCategoryEntity> findByUidAndAuthKbCategory(@Param("uid") Long uid, @Param("wh_id") Long wh_id, @Param("kb_auths")  List<Integer> kb_auths);
    /*
      根据id查询出知识目录知识分类
     */
    String findBySource(@Param("id") Long id);

    List<KbCategoryShowDto> findByUidAuthHotKbCategoryAdmin(@Param("topCount") int topCount);

    List<KbCategoryShowDto> findByUidAuthHotKbCategory(@Param("kbAuthList")  List<Integer> kbAuthList,@Param("uid") Long uid ,@Param("topCount")  int topCount);

    List<KbCategoryEntity> findVideoCategoryAdmin(@Param("swId") Long id);

    List<KbCategoryEntity> findVideoCategory(@Param("swId") Long id,@Param("kb_auths") List<Integer> asList, @Param("uid") Long uid);
}
