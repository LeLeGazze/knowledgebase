package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysPostEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统职位表Mapper 接口
 *
 * @author castle
 * @since 2021-01-04
 */
public interface SysPostMapper extends BaseMapper<SysPostEntity> {

    List<SysPostEntity> leadersPost(@Param("deptId") Long deptId,@Param("deptParents")String deptParents);
}

