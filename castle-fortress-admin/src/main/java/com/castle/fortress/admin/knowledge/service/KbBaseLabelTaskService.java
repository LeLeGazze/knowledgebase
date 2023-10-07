package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.dto.KbBaseLabelTaskDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 标签删除任务表 服务类
 *
 * @author 
 * @since 2023-06-07
 */
public interface KbBaseLabelTaskService extends IService<KbBaseLabelTaskEntity> {

    /**
     * 分页展示标签删除任务表列表
     * @param page
     * @param kbBaseLabelTaskDto
     * @return
     */
    IPage<KbBaseLabelTaskDto> pageKbBaseLabelTask(Page<KbBaseLabelTaskDto> page, KbBaseLabelTaskDto kbBaseLabelTaskDto);


    /**
     * 展示标签删除任务表列表
     * @param kbBaseLabelTaskDto
     * @return
     */
    List<KbBaseLabelTaskDto> listKbBaseLabelTask(KbBaseLabelTaskDto kbBaseLabelTaskDto);

}
