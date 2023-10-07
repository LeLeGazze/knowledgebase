package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbDataSynchronizationTaskEntity;
import com.castle.fortress.admin.knowledge.dto.KbDataSynchronizationTaskDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 数据同步 服务类
 *
 * @author 
 * @since 2023-06-29
 */
public interface KbDataSynchronizationTaskService extends IService<KbDataSynchronizationTaskEntity> {

    /**
     * 分页展示数据同步列表
     * @param page
     * @param kbDataSynchronizationTaskDto
     * @return
     */
    IPage<KbDataSynchronizationTaskDto> pageKbDataSynchronizationTask(Page<KbDataSynchronizationTaskDto> page, KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto);


    /**
     * 展示数据同步列表
     * @param kbDataSynchronizationTaskDto
     * @return
     */
    List<KbDataSynchronizationTaskDto> listKbDataSynchronizationTask(KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto);

    List<KbDataSynchronizationTaskEntity> getStatus(int num);

    List<Map<String, Object>> runSQL(String taskEntitySql);
}
