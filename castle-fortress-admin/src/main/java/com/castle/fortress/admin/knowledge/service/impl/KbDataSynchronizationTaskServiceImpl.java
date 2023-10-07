package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbDataSynchronizationTaskEntity;
import com.castle.fortress.admin.knowledge.dto.KbDataSynchronizationTaskDto;
import com.castle.fortress.admin.knowledge.mapper.KbDataSynchronizationTaskMapper;
import com.castle.fortress.admin.knowledge.service.KbDataSynchronizationTaskService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 数据同步 服务实现类
 *
 * @author
 * @since 2023-06-29
 */
@Service
public class KbDataSynchronizationTaskServiceImpl extends ServiceImpl<KbDataSynchronizationTaskMapper, KbDataSynchronizationTaskEntity> implements KbDataSynchronizationTaskService {
    /**
     * 获取查询条件
     *
     * @param kbDataSynchronizationTaskDto
     * @return
     */
    public QueryWrapper<KbDataSynchronizationTaskEntity> getWrapper(KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto) {
        QueryWrapper<KbDataSynchronizationTaskEntity> wrapper = new QueryWrapper();
        if (kbDataSynchronizationTaskDto != null) {
            KbDataSynchronizationTaskEntity kbDataSynchronizationTaskEntity = ConvertUtil.transformObj(kbDataSynchronizationTaskDto, KbDataSynchronizationTaskEntity.class);
            wrapper.like(kbDataSynchronizationTaskEntity.getId() != null, "id", kbDataSynchronizationTaskEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbDataSynchronizationTaskEntity.getType()), "type", kbDataSynchronizationTaskEntity.getType());
            wrapper.like(StrUtil.isNotEmpty(kbDataSynchronizationTaskEntity.getSql()), "sql", kbDataSynchronizationTaskEntity.getSql());
            wrapper.like(kbDataSynchronizationTaskEntity.getAuth() != null, "auth", kbDataSynchronizationTaskEntity.getAuth());
            wrapper.like(kbDataSynchronizationTaskEntity.getDept() != null, "dept", kbDataSynchronizationTaskEntity.getDept());
            wrapper.like(kbDataSynchronizationTaskEntity.getCategoryId() != null, "category_id", kbDataSynchronizationTaskEntity.getCategoryId());
            wrapper.like(kbDataSynchronizationTaskEntity.getModeId() != null, "mode_id", kbDataSynchronizationTaskEntity.getModeId());
            wrapper.like(kbDataSynchronizationTaskEntity.getStatus() != null, "status", kbDataSynchronizationTaskEntity.getStatus());
        }
        return wrapper;
    }


    @Override
    public IPage<KbDataSynchronizationTaskDto> pageKbDataSynchronizationTask(Page<KbDataSynchronizationTaskDto> page, KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto) {
        QueryWrapper<KbDataSynchronizationTaskEntity> wrapper = getWrapper(kbDataSynchronizationTaskDto);
        Page<KbDataSynchronizationTaskEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbDataSynchronizationTaskEntity> kbDataSynchronizationTaskPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbDataSynchronizationTaskDto> pageDto = new Page(kbDataSynchronizationTaskPage.getCurrent(), kbDataSynchronizationTaskPage.getSize(), kbDataSynchronizationTaskPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbDataSynchronizationTaskPage.getRecords(), KbDataSynchronizationTaskDto.class));
        return pageDto;
    }


    @Override
    public List<KbDataSynchronizationTaskDto> listKbDataSynchronizationTask(KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto) {
        QueryWrapper<KbDataSynchronizationTaskEntity> wrapper = getWrapper(kbDataSynchronizationTaskDto);
        List<KbDataSynchronizationTaskEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbDataSynchronizationTaskDto.class);
    }

    @Override
    public List<KbDataSynchronizationTaskEntity> getStatus(int num) {
        LambdaQueryWrapper<KbDataSynchronizationTaskEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KbDataSynchronizationTaskEntity::getStatus, num);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Map<String, Object>> runSQL(String taskEntitySql) {
        return baseMapper.runSQL(taskEntitySql);
    }
}

