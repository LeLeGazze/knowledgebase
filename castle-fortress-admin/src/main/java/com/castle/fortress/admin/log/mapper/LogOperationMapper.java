package com.castle.fortress.admin.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.log.entity.LogOperationEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 用户操作记录日志表Mapper 接口
 *
 * @author castle
 * @since 2021-03-31
 */
public interface LogOperationMapper extends BaseMapper<LogOperationEntity> {


    void deleteByTime(@Param("days") int days);
}

