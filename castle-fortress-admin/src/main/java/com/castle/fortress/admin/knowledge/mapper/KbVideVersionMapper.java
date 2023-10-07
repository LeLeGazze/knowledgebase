package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbVideVersionDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbVideVersionEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;
import java.util.List;
/**
 * PDF/word 等转换成PDFMapper 接口
 *
 * @author 
 * @since 2023-05-08
 */
public interface KbVideVersionMapper extends BaseMapper<KbVideVersionEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbVideVersionEntity
    * @return
    */
    List<KbVideVersionEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbVideVersionEntity") KbVideVersionEntity kbVideVersionEntity);

    /**
    * 扩展信息记录总数
    * @param kbVideVersionEntity
    * @return
    */
    Long extendsCount(@Param("kbVideVersionEntity") KbVideVersionEntity kbVideVersionEntity);

    /**
    * PDF/word 等转换成PDF扩展详情
    * @param id PDF/word 等转换成PDFid
    * @return
    */
    KbVideVersionEntity getByIdExtends(@Param("id")Long id);


    List<KbVideVersionDto> findByUid(@Param("uid") Long id);

    int deleteByBid(@Param("bId") Long id);

    @Update("update kb_vide_version set b_id = #{newBid}  where b_id=#{beforeBid} ")
    void updateByBid(@Param("newBid") Long newBid,@Param("beforeBid") Long beforeBid);

    @Select("select ve.*, ba.version\n" +
            "from kb_vide_version ve\n" +
            "         left join kb_basic_history ba on ve.b_id = ba.id\n" +
            "where (ba.b_id = #{kbVideVersionDto.bId} or ve.b_id = #{kbVideVersionDto.bId})\n" +
            "  and type = #{kbVideVersionDto.type} \n" +
            "order by ve.create_time desc")
    List<KbVideVersionDto> findByTypeVersion(@Param("kbVideVersionDto") KbVideVersionDto kbVideVersionDto);
}
