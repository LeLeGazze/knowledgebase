package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.entity.SysUserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户角色关联服务类
 * @author castle
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 查询用户的角色列表
     * @param userId 用户id
     * @return
     */
    List<SysUserRole> queryListByUser(Long userId);

    /**
     * 查询角色绑定的用户列表
     * @param id
     * @return
     */
    List<SysUserRole> queryByRole(Long id);

    /**
     * 删除指定用户的角色关系
     * @param userId
     */
    void delByUserId(Long userId);

    /**
     * 查询角色列表对应的用户
     * @param roleIds
     * @return
     */
    List<SysUserRole> queryByRoleList(List<Long> roleIds);
}
