package com.castle.fortress.admin.log.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.log.dto.LogOpenapiDto;
import com.castle.fortress.admin.log.entity.LogOpenapiEntity;
import com.castle.fortress.admin.log.mapper.LogOpenapiMapper;
import com.castle.fortress.admin.log.service.LogOpenapiService;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对外开放api调用日志 服务实现类
 *
 * @author castle
 * @since 2021-04-01
 */
@Service
public class LogOpenapiServiceImpl extends ServiceImpl<LogOpenapiMapper, LogOpenapiEntity> implements LogOpenapiService {
    /**
    * 获取查询条件
    * @param logOpenapiDto
    * @return
    */
    public QueryWrapper<LogOpenapiEntity> getWrapper(LogOpenapiDto logOpenapiDto){
        QueryWrapper<LogOpenapiEntity> wrapper= new QueryWrapper();
        if(logOpenapiDto != null){
            LogOpenapiEntity logOpenapiEntity = ConvertUtil.transformObj(logOpenapiDto,LogOpenapiEntity.class);
            wrapper.like(StrUtil.isNotEmpty(logOpenapiEntity.getInvokeUrl()),"invoke_url",logOpenapiEntity.getInvokeUrl());
            wrapper.like(StrUtil.isNotEmpty(logOpenapiEntity.getRemoteAddr()),"remote_addr",logOpenapiEntity.getRemoteAddr());
            wrapper.like(StrUtil.isNotEmpty(logOpenapiEntity.getInvokeParams()),"invoke_params",logOpenapiEntity.getInvokeParams());
            wrapper.like(StrUtil.isNotEmpty(logOpenapiEntity.getInvokeStatus()),"invoke_status",logOpenapiEntity.getInvokeStatus());
            wrapper.like(StrUtil.isNotEmpty(logOpenapiEntity.getClassName()),"class_name",logOpenapiEntity.getClassName());
            wrapper.like(StrUtil.isNotEmpty(logOpenapiEntity.getMethodName()),"method_name",logOpenapiEntity.getMethodName());
            wrapper.like(StrUtil.isNotEmpty(logOpenapiEntity.getSecretId()),"secret_id",logOpenapiEntity.getSecretId());
            wrapper.like(logOpenapiEntity.getInvokeTime()!=null,"invoke_time",logOpenapiEntity.getInvokeTime());
            wrapper.like(logOpenapiEntity.getElapsedTime()!=null,"elapsed_time",logOpenapiEntity.getElapsedTime());
            if(logOpenapiDto.getInvokeTimeStart()!=null && logOpenapiDto.getInvokeTimeEnd()!=null){
                wrapper.between("invoke_time",logOpenapiDto.getInvokeTimeStart(),logOpenapiDto.getInvokeTimeEnd());
            }
        }
        wrapper.orderByDesc("invoke_time");
        return wrapper;
    }


    @Override
    public IPage<LogOpenapiDto> pageLogOpenapi(Page<LogOpenapiDto> page, LogOpenapiDto logOpenapiDto) {
        QueryWrapper<LogOpenapiEntity> wrapper = getWrapper(logOpenapiDto);
        Page<LogOpenapiEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<LogOpenapiEntity> logOpenapiPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<LogOpenapiDto> pageDto = new Page(logOpenapiPage.getCurrent(), logOpenapiPage.getSize(),logOpenapiPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(logOpenapiPage.getRecords(),LogOpenapiDto.class));
        return pageDto;
    }


    @Override
    public List<LogOpenapiDto> listLogOpenapi(LogOpenapiDto logOpenapiDto){
        QueryWrapper<LogOpenapiEntity> wrapper = getWrapper(logOpenapiDto);
        List<LogOpenapiEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,LogOpenapiDto.class);
    }
    @Async
    @Override
    public void saveLog(LogOpenapiEntity logEntity) {
        baseMapper.insert(logEntity);
    }
}

