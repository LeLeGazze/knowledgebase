package com.castle.fortress.admin.message.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.message.mail.entity.CastleMessageEmailRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 邮件发送记录表Mapper 接口
 *
 * @author Mgg
 * @since 2021-10-27
 */
public interface CastleMessageEmailRecordMapper extends BaseMapper<CastleMessageEmailRecordEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param castleMessageEmailRecordEntity
    * @return
    */
    List<CastleMessageEmailRecordEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("castleMessageEmailRecordEntity") CastleMessageEmailRecordEntity castleMessageEmailRecordEntity);

    /**
    * 扩展信息记录总数
    * @param castleMessageEmailRecordEntity
    * @return
    */
    Long extendsCount(@Param("castleMessageEmailRecordEntity") CastleMessageEmailRecordEntity castleMessageEmailRecordEntity);

    /**
    * 邮件发送记录表扩展详情
    * @param id 邮件发送记录表id
    * @return
    */
    CastleMessageEmailRecordEntity getByIdExtends(@Param("id")Long id);



}

