package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.SysRegionEntity;
import org.apache.ibatis.annotations.Param;
/**
 * 行政区域Mapper 接口
 *
 * @author castle
 * @since 2021-04-28
 */
public interface SysRegionMapper extends BaseMapper<SysRegionEntity> {


    SysRegionEntity getByIdExtends(@Param("id") Long id);
}

