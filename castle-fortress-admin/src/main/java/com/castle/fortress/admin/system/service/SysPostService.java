package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysPostDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysPostEntity;

import java.util.List;

/**
 * 系统职位表 服务类
 *
 * @author castle
 * @since 2021-01-04
 */
public interface SysPostService extends IService<SysPostEntity> {

    /**
     * 分页展示系统职位表列表
     * @param page
     * @param sysPostDto
     * @return
     */
    IPage<SysPostDto> pageSysPost(Page<SysPostDto> page, SysPostDto sysPostDto);

    /**
     * 查询系统职位列表
     * @param sysPostDto
     * @return
     */
    List<SysPostDto> listPost(SysPostDto sysPostDto);

    /**
     * 查询子节点
     * @param id
     * @return
     */
    List<SysPostDto> children(Long id);

    /**
     * 检查字段是否重复
     * @param sysPostEntity
     * @return
     */
    boolean checkColumnRepeat(SysPostEntity sysPostEntity);

    /**
     * 查询部门及上级部门的主管职位
     * @param deptEntity
     * @return
     */
    List<SysPostDto> leadersPost(SysDeptEntity deptEntity);
}
