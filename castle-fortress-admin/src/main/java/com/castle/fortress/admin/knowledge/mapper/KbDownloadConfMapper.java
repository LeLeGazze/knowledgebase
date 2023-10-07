package com.castle.fortress.admin.knowledge.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbDownloadConfEntity;
import java.util.Map;
import java.util.List;
/**
 * 文件下载配置表Mapper 接口
 *
 * @author 
 * @since 2023-06-25
 */
public interface KbDownloadConfMapper extends BaseMapper<KbDownloadConfEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbDownloadConfEntity
    * @return
    */
    List<KbDownloadConfEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbDownloadConfEntity") KbDownloadConfEntity kbDownloadConfEntity);

    /**
    * 扩展信息记录总数
    * @param kbDownloadConfEntity
    * @return
    */
    Long extendsCount(@Param("kbDownloadConfEntity") KbDownloadConfEntity kbDownloadConfEntity);

    /**
    * 文件下载配置表扩展详情
    * @param id 文件下载配置表id
    * @return
    */
    KbDownloadConfEntity getByIdExtends(@Param("id")Long id);



}
