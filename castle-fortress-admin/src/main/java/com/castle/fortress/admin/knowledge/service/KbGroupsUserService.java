package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbGroupsUserEntity;
import com.castle.fortress.admin.knowledge.dto.KbGroupsUserDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysUser;

import java.util.Map;
import java.util.List;

/**
 * 知识库用户组与用户关联关系管理 服务类
 *
 * @author 
 * @since 2023-04-22
 */
public interface KbGroupsUserService extends IService<KbGroupsUserEntity> {

    /**
     * 分页展示知识库用户组与用户关联关系管理列表
     * @param page
     * @param kbGroupsUserDto
     * @return
     */
    IPage<KbGroupsUserDto> pageKbGroupsUser(Page<KbGroupsUserDto> page, KbGroupsUserDto kbGroupsUserDto);


    /**
     * 展示知识库用户组与用户关联关系管理列表
     * @param kbGroupsUserDto
     * @return
     */
    List<KbGroupsUserDto> listKbGroupsUser(KbGroupsUserDto kbGroupsUserDto);

    List<KbGroupsUserEntity> findById(Long id);

    /**
     * 根据部门id查询部门下的所有用户id
     * @param id
     * @return
     */
    IPage<SysUserDto> listFindUser(Long id,String name,Page<SysUserDto> page,Integer status);

    boolean removeBatch(List<Long> ids,Long groupId);
}
