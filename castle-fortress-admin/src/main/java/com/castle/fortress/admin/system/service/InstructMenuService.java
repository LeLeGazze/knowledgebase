package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysMenuDto;
import com.castle.fortress.admin.system.entity.InstructMenuEntity;
import com.castle.fortress.admin.system.dto.InstructMenuDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.system.entity.SysMenu;

import java.util.Map;
import java.util.List;

/**
 * 菜单指令配置表 服务类
 *
 * @author castle
 * @since 2022-08-24
 */
public interface InstructMenuService extends IService<InstructMenuEntity> {

    /**
     * 分页展示菜单指令配置表列表
     * @param page
     * @param instructMenuDto
     * @return
     */
    IPage<InstructMenuDto> pageInstructMenu(Page<InstructMenuDto> page, InstructMenuDto instructMenuDto);


    /**
     * 展示菜单指令配置表列表
     * @param instructMenuDto
     * @return
     */
    List<InstructMenuDto> listInstructMenu(InstructMenuDto instructMenuDto);

    /**
     * 依据过滤信息获取匹配菜单
     * @param filterInfo
     * @menuList 权限内菜单
     * @return
     */
    List<Map> filterInstructMenu(String filterInfo,List<SysMenu> menuList);
}
