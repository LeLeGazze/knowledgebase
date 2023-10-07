package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysRoleDto;
import com.castle.fortress.admin.system.entity.SysRole;

import java.util.List;

/**
 * 系统角色服务类
 * @author castle
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 通过用户id查询用户角色列表
     * @param userId
     * @return
     */
    List<SysRole> queryListByUser(Long userId);

    IPage<SysRoleDto> pageSysRole(Page<SysRoleDto> page, SysRoleDto sysRoleDto);

    /**
     * 检查字段是否重复
     * @param sysRole
     * @return
     */
    boolean checkColumnRepeat(SysRole sysRole);
    /**
     * 获取所有超管角色
     * @return
     */
    List<SysRole> listAdmin();

    SysRole findById(Long id);
}
