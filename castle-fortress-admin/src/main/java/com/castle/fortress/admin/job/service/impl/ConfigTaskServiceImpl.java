package com.castle.fortress.admin.job.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.job.dto.ConfigTaskDto;
import com.castle.fortress.admin.job.entity.ConfigTaskEntity;
import com.castle.fortress.admin.job.mapper.ConfigTaskMapper;
import com.castle.fortress.admin.job.service.ConfigTaskService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.enums.JobStatusEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统任务调度表 服务实现类
 *
 * @author 
 * @since 2021-03-24
 */
@Service
public class ConfigTaskServiceImpl extends ServiceImpl<ConfigTaskMapper, ConfigTaskEntity> implements ConfigTaskService {
    /**
    * 获取查询条件
    * @param configTaskDto
    * @return
    */
    public QueryWrapper<ConfigTaskEntity> getWrapper(ConfigTaskDto configTaskDto){
        QueryWrapper<ConfigTaskEntity> wrapper= new QueryWrapper();
        if(configTaskDto != null){
            ConfigTaskEntity configTaskEntity = ConvertUtil.transformObj(configTaskDto,ConfigTaskEntity.class);
            wrapper.like(StrUtil.isNotEmpty(configTaskEntity.getTaskName()),"task_name",configTaskEntity.getTaskName());
            wrapper.eq(configTaskEntity.getStatus() != null,"status",configTaskEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<ConfigTaskDto> pageConfigTask(Page<ConfigTaskDto> page, ConfigTaskDto configTaskDto) {
        QueryWrapper<ConfigTaskEntity> wrapper = getWrapper(configTaskDto);
        Page<ConfigTaskEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<ConfigTaskEntity> configTaskPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<ConfigTaskDto> pageDto = new Page(configTaskPage.getCurrent(), configTaskPage.getSize(),configTaskPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(configTaskPage.getRecords(),ConfigTaskDto.class));
        return pageDto;
    }

    @Override
    public IPage<ConfigTaskDto> pageConfigTaskExtends(Page<ConfigTaskDto> page, ConfigTaskDto configTaskDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        ConfigTaskEntity configTaskEntity = ConvertUtil.transformObj(configTaskDto,ConfigTaskEntity.class);
        List<ConfigTaskEntity> configTaskList=baseMapper.extendsList(pageMap,configTaskEntity);
        Long total = baseMapper.extendsCount(configTaskEntity);
        Page<ConfigTaskDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(configTaskList,ConfigTaskDto.class));
        return pageDto;
    }
    @Override
    public ConfigTaskDto getByIdExtends(Long id){
        ConfigTaskEntity  configTaskEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(configTaskEntity,ConfigTaskDto.class);
    }

    @Override
    public List<ConfigTaskDto> listConfigTask(ConfigTaskDto configTaskDto){
        QueryWrapper<ConfigTaskEntity> wrapper = getWrapper(configTaskDto);
        List<ConfigTaskEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,ConfigTaskDto.class);
    }

    @Override
    public List<ConfigTaskDto> listRun() {
        QueryWrapper<ConfigTaskEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("status", JobStatusEnum.RUN.getCode());
        List<ConfigTaskEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,ConfigTaskDto.class);
    }
}

