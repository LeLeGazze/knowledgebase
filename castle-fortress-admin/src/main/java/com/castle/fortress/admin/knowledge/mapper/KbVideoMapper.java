package com.castle.fortress.admin.knowledge.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.knowledge.dto.KbVideoDto;
import com.castle.fortress.admin.knowledge.dto.KbVideoShowDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbVideoEntity;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Map;
import java.util.List;
/**
 * 视频库Mapper 接口
 *
 * @author 
 * @since 2023-05-13
 */
public interface KbVideoMapper extends BaseMapper<KbVideoEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbVideoEntity
    * @return
    */
    List<KbVideoEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbVideoEntity") KbVideoEntity kbVideoEntity);

    /**
    * 扩展信息记录总数
    * @param kbVideoEntity
    * @return
    */
    Long extendsCount(@Param("kbVideoEntity") KbVideoEntity kbVideoEntity);

    /**
    * 视频库扩展详情
    * @param id 视频库id
    * @return
    */
    KbVideoEntity getByIdExtends(@Param("id")Long id);


    List<KbVideoShowDto> pageKbVideoAdmin(@Param("map") Map<String, Long>  map, @Param("kbVideoShowDto") KbVideoShowDto kbVideoShowDto);

    Long pageKbVideoAdminCount(@Param("kbVideoShowDto") KbVideoShowDto kbVideoShowDto);

    List<KbVideoShowDto> pageKbVideo(@Param("map") Map<String, Long> pageMap,@Param("kbVideoShowDto") KbVideoShowDto kbVideoShowDto,@Param("uid") Long uid,@Param("kb_auths") List<Integer> kb_auths);

    Long pageKbVideoCount(@Param("kbVideoShowDto") KbVideoShowDto kbVideoShowDto,@Param("uid") Long uid,@Param("kb_auths") List<Integer> kb_auths);

    KbVideoDto findById(@Param("id") Long id);

    KbVideoEntity findByIdAuth(@Param("uid") Long uid,@Param("kb_auths") List<Integer> kb_auths,@Param("vid") Long id);

    @Delete("delete from  kb_video where id=#{id}")
    @Override
    int  deleteById(@Param("id") Serializable id);

    List<KbVideoDto> showListVideoAdmin(@Param("swId") Long swId);

    List<KbVideoDto> showListVideo(@Param("swId") Long swId,@Param("kb_auths") List<Integer> integers,@Param("uid") Long uid);

    List<KbVideoDto> randVideoListAdmin();

    List<KbVideoDto> randVideoList(@Param("kb_auths") List<Integer> integers, @Param("uid") Long uid);

    int deletedByid(@Param("id") Long id);

    boolean updateByVideo(@Param("basicId") Long basicId);

    @Select("select * from kb_video where exp_time <=now() and status=1")
    List<KbVideoEntity> selectByExpireVideo();
}
