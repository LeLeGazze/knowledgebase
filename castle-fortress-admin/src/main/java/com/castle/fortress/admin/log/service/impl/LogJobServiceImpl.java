package com.castle.fortress.admin.log.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.log.dto.LogJobDto;
import com.castle.fortress.admin.log.entity.LogJobEntity;
import com.castle.fortress.admin.log.mapper.LogJobMapper;
import com.castle.fortress.admin.log.service.LogJobService;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务调用日志 服务实现类
 *
 * @author castle
 * @since 2021-04-02
 */
@Service
public class LogJobServiceImpl extends ServiceImpl<LogJobMapper, LogJobEntity> implements LogJobService {
    /**
    * 获取查询条件
    * @param logJobDto
    * @return
    */
    public QueryWrapper<LogJobEntity> getWrapper(LogJobDto logJobDto){
        QueryWrapper<LogJobEntity> wrapper= new QueryWrapper();
        if(logJobDto != null){
            LogJobEntity logJobEntity = ConvertUtil.transformObj(logJobDto,LogJobEntity.class);
            wrapper.like(StrUtil.isNotEmpty(logJobEntity.getTaskName()),"task_name",logJobEntity.getTaskName());
            wrapper.like(StrUtil.isNotEmpty(logJobEntity.getTaskId()),"task_id",logJobEntity.getTaskId());
            wrapper.like(StrUtil.isNotEmpty(logJobEntity.getInvokeParams()),"invoke_params",logJobEntity.getInvokeParams());
            wrapper.like(StrUtil.isNotEmpty(logJobEntity.getInvokeStatus()),"invoke_status",logJobEntity.getInvokeStatus());
            wrapper.like(logJobEntity.getInvokeTime()!=null,"invoke_time",logJobEntity.getInvokeTime());
            wrapper.like(logJobEntity.getElapsedTime()!=null,"elapsed_time",logJobEntity.getElapsedTime());
            if(logJobDto.getInvokeTimeStart()!=null && logJobDto.getInvokeTimeEnd()!=null){
                wrapper.between("invoke_time",logJobDto.getInvokeTimeStart(),logJobDto.getInvokeTimeEnd());
            }
        }
        wrapper.orderByDesc("invoke_time");
        return wrapper;
    }


    @Override
    public IPage<LogJobDto> pageLogJob(Page<LogJobDto> page, LogJobDto logJobDto) {
        QueryWrapper<LogJobEntity> wrapper = getWrapper(logJobDto);
        Page<LogJobEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<LogJobEntity> logJobPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<LogJobDto> pageDto = new Page(logJobPage.getCurrent(), logJobPage.getSize(),logJobPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(logJobPage.getRecords(),LogJobDto.class));
        return pageDto;
    }


    @Override
    public List<LogJobDto> listLogJob(LogJobDto logJobDto){
        QueryWrapper<LogJobEntity> wrapper = getWrapper(logJobDto);
        List<LogJobEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,LogJobDto.class);
    }
    @Async
    @Override
    public void saveLog(LogJobEntity logEntity) {
        baseMapper.insert(logEntity);
    }
}

