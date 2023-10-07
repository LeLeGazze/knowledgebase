package com.castle.fortress.admin.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.job.dto.ConfigTaskDto;
import com.castle.fortress.admin.job.entity.ConfigTaskEntity;

import java.util.List;

/**
 * 系统任务调度表 服务类
 *
 * @author 
 * @since 2021-03-24
 */
public interface ConfigTaskService extends IService<ConfigTaskEntity> {

    /**
     * 分页展示系统任务调度表列表
     * @param page
     * @param configTaskDto
     * @return
     */
    IPage<ConfigTaskDto> pageConfigTask(Page<ConfigTaskDto> page, ConfigTaskDto configTaskDto);

    /**
    * 分页展示系统任务调度表扩展列表
    * @param page
    * @param configTaskDto
    * @return
    */
    IPage<ConfigTaskDto> pageConfigTaskExtends(Page<ConfigTaskDto> page, ConfigTaskDto configTaskDto);
    /**
    * 系统任务调度表扩展详情
    * @param id 系统任务调度表id
    * @return
    */
    ConfigTaskDto getByIdExtends(Long id);

    /**
     * 展示系统任务调度表列表
     * @param configTaskDto
     * @return
     */
    List<ConfigTaskDto> listConfigTask(ConfigTaskDto configTaskDto);

    /**
     * 查询所有运行中的任务
     * @return
     */
    List<ConfigTaskDto> listRun();
}
