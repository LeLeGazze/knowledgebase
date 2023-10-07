package com.castle.fortress.admin.knowledge.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbBasicDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 17725
 * @create 2023/4/19 15:48
 */
@Mapper
public interface KbBasicMapper extends BaseMapper<KbBasicEntity> {

    List<KbBaseShowDto> extendsList(@Param("map") Map<String, Long> pageMap, @Param("kbBasicDto") KbBaseShowDto kbBasicDto, @Param("uid") Long uid, @Param("kb_auths") List<Integer> kb_auths, @Param("extendList") List<JSONObject> extendList);

    Long extendsCount(@Param("kbBasicDto") KbBaseShowDto kbBasicDto, @Param("uid") Long uid, @Param("kb_auths") List<Integer> kb_auths, @Param("extendList") List<JSONObject> extendList);

    KbModelTransmitDto findAllByBasic(@Param("id") Long id);

    /**
     * 查询出刚刚插入成功的文件id
     *
     * @param kbBasicDto
     * @return
     */
    KbBasicEntity findByKbBasic(@Param("kbBasicDto") KbBasicDto kbBasicDto);

    KbBasicEntity findByIdAuth(@Param("uid") Long uid, @Param("kb_auths") List<Integer> kb_auths, @Param("bid") Long bid);


    List<KbBaseShowDto> extendsListAdmin(@Param("map") Map<String, Long> pageMap, @Param("kbBasicDto") KbBaseShowDto kbBasicDto, @Param("extendList") List<JSONObject> extendList);

    Long extendsCountAdmin(@Param("kbBasicDto") KbBaseShowDto kbBasicDto, @Param("extendList") List<JSONObject> extendList);

    List<KbModelTransmitDto> intelligentRecommendation(@Param("list") List<Long> longs);

    List<KbBaseShowDto> randBasicPageAdmin(@Param("map") Map<String, Long> pageMap, @Param("swId") String swId);

    List<KbBaseShowDto> randBasicPage(@Param("map") Map<String, Long> pageMap, @Param("uid") Long uid, @Param("kb_auths") List<Integer> kb_auths, @Param("swId") String swId);

    List<KbBaseShowDto> newBasicListAdmin();

    List<KbBaseShowDto> newBasicList(@Param("uid") Long uid, @Param("kb_auths") List<Integer> kb_auths);

    List<KbBaseShowDto> recentPreviewBasicListAdmin(@Param("uid") Long uid, @Param("swId") String swId, @Param("size") Integer size);


    List<KbBaseShowDto> recentPreviewBasicList(@Param("uid") Long uid, @Param("kb_auths") List<Integer> integers, @Param("swId") String swId, @Param("size") Integer size);

    List<KbModelTransmitDto> findBasicByLike(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap, @Param("userId") Long userId, @Param("kb_auths") List<Integer> integers);

    Integer findBasicByLikeCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("userId") Long userId, @Param("kb_auths") List<Integer> integers);

    List<KbModelTransmitDto> findVideoByLike(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap, @Param("userId") Long userId, @Param("kb_auths") List<Integer> integers);

    Integer findVideoByLikeCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("userId") Long userId, @Param("kb_auths") List<Integer> integers);

    int deleteByid(@Param("id") Long id);

    @Select("select is_deleted,count(1) count from kb_basic where category_id=#{id} group by  is_deleted order by is_deleted  ")
    List<Map<String, Integer>> selectByIdIsDelete(@Param("id") Long id);

    List<SysUser> findAllAuth();

    List<SysUser> findBaseAuth(@Param("kb_auths") List<Integer> asList, @Param("uid") Long uid);

    List<KbModelTransmitDto> findBasicByLikeAdmin(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap);

    Integer findBasicByLikeAdminCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    List<KbModelTransmitDto> findBasicByUploud(@Param("userId") Long userId, @Param("kb_auths") List<Integer> integers, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap);

    Integer findBasicByUploudCount(@Param("userId") Long userId, @Param("kb_auths") List<Integer> integers, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    List<KbModelTransmitDto> findBasicByUploudVideo(@Param("userId") Long userId, @Param("kb_auths") List<Integer> integers, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap);

    Integer findBasicByUploudVideoCount(@Param("userId") Long userId, @Param("kb_auths") List<Integer> integers, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    List<KbModelTransmitDto> findBasicByVideoAdmin(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap);

    Integer findBasicByVideoAdminCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    @Select("select * from kb_basic where exp_time <=now() and status=1")
    List<KbBasicEntity> selectByExpireBasic();

    List<Map<String, Object>> getTheLatest12QuantitiesAdmin();

    List<Long> findByAuth(@Param("userId") Long id,@Param("kb_auths") List<Integer> auths);

    List<Map<String, Object>> getTheLatest12Quantities(@Param("userId")Long uid,@Param("authList") List<Long> authList);

    List<Map<String, Object>> getKnowledgeBaseTopNAdmin(@Param("num") int num);

    List<Map<String, Object>> getKnowledgeBaseTopN(@Param("authList")List<Long> authList,@Param("userId") Long uid, @Param("num")int num);
}
