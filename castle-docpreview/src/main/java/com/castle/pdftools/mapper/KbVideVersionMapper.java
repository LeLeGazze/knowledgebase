package com.castle.pdftools.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.pdftools.entity.KbVideVersionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KbVideVersionMapper extends BaseMapper<KbVideVersionEntity> {

    List<KbVideVersionEntity> selectByBid(@Param("bid") String bid);

    List<KbVideVersionEntity> selectByStatus(@Param("num") int num);
}
