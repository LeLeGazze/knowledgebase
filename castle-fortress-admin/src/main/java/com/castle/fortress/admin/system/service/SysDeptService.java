package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysDeptDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 系统部门表 服务类
 *
 * @author castle
 * @since 2021-01-04
 */
public interface SysDeptService extends IService<SysDeptEntity> {

    /**
     * 分页展示系统部门表列表
     *
     * @param page
     * @param sysDeptDto
     * @return
     */
    IPage<SysDeptDto> pageSysDept(Page<SysDeptDto> page, SysDeptDto sysDeptDto);

    /**
     * 查询用户可访问的部门数据
     *
     * @param roleIds
     * @return
     */
    List<SysDeptDto> authorityAllDept(List<Long> roleIds, Long parentId,String name);

    /**
     * 组织架构及人员组件列表展示
     *
     * @param roleIds
     * @return
     */
    List<Map<String, String>> authorityComponentList(List<Long> roleIds, Long deptId);

    /**
     * 查询指定数据下的子数据
     *
     * @param id
     * @return
     */
    List<SysDeptDto> children(Long id);

    /**
     * 检查字段是否重复
     *
     * @param sysDeptEntity
     * @return
     */
    boolean checkColumnRepeat(SysDeptEntity sysDeptEntity);

    /**
     * 组织架构及人员组件列表查询展示
     *
     * @param map     type：类型 00 部门 01 人员；name：名称
     * @param roleIds 角色列表
     * @param sysUser 当前登录用户
     * @return
     */
    List<Map<String, String>> authoritySearchForComponent(List<Long> roleIds, Map<String, String> map, SysUser sysUser);

    /**
     * 组织架构及人员组件列表id转name展示
     *
     * @param roleIds
     * @param map
     * @param sysUser
     * @return
     */
    List<Map<String, String>> authorityNameForComponent(List<Long> roleIds, Map<String, Object> map, SysUser sysUser);

    /**
     * 组织架构及人员组件列表id转name展示 无数据权限
     *
     * @param map
     * @return
     */
    List<Map<String, String>> nameForComponent(Map<String, Object> map);

    /**
     * 查询该部门下的所有下级部门
     *
     * @param parentId
     * @return
     */
    List<SysDeptDto> allChildren(Long parentId);

    List<SysDeptEntity> listToName(List<Long> roleIds, String name);

    List<SysDeptDto> myselfBelowDept(Long deptId);
}
