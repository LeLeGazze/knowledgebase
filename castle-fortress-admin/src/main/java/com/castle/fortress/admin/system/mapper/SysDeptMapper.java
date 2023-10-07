package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.dto.SysDeptDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
/**
 * 系统部门表Mapper 接口
 *
 * @author castle
 * @since 2021-01-04
 */
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {
    /**
     * 查询用户可访问的部门数据
     * @param roleIds
     * @return
     */
    List<SysDeptEntity> authorityAllDept(@Param("roleIds") List<Long> roleIds,@Param("parentId") Long parentId,@Param("name") String name);

    List<SysDeptEntity> authorityComponentList(@Param("roleIds")List<Long> roleIds,@Param("deptId") Long deptId);

    List<Map<String, Object>> countChild(@Param("ids")List<Long> ids);

    List<SysDeptEntity> authoritySearchForComponent(@Param("roleIds")List<Long> roleIds,@Param("name") String name);

    List<SysDeptEntity> authorityNameForComponent(@Param("roleIds")List<Long> roleIds,@Param("ids")List<Long> ids);

    List<SysDeptDto> myselfBelowDept(@Param("deptId") Long deptId);
}

