package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysDictDto;
import com.castle.fortress.admin.system.entity.SysDictEntity;

import java.util.List;

/**
 * 系统字典服务类
 * @author castle
 */
public interface SysDictService extends IService<SysDictEntity> {

    /**
     * 分页查询系统字典表
     * @param page
     * @param sysDictDto
     * @return
     */
    IPage<SysDictDto> pageSysDict(Page<SysDictDto> page, SysDictDto sysDictDto);

    /**
     *删除指定的字典
     * @param id
     * @return
     */
    boolean deleteDict(Long id);

    /**
     * 通过编码查询字典集合
     * @param code
     * @return
     */
    List<SysDictDto> listByCode(String code);

    /**
     * 系统字典表子集分页展示
     * @param page
     * @param sysDictDto
     * @return
     */
    IPage<SysDictDto> subPageSysDict(Page<SysDictDto> page, SysDictDto sysDictDto);

    List<SysDictEntity> selectBySelective(SysDictEntity sysDict);

    /**
     * 通过父级字典修改子集的code值
     * @param sysDictParent
     */
    void updateSubCode(SysDictEntity sysDictParent);
}
