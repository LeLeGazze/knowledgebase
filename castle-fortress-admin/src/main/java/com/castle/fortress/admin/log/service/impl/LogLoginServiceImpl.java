package com.castle.fortress.admin.log.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.log.dto.LogLoginDto;
import com.castle.fortress.admin.log.entity.LogLoginEntity;
import com.castle.fortress.admin.log.mapper.LogLoginMapper;
import com.castle.fortress.admin.log.service.LogLoginService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 登录操作日志 服务实现类
 *
 * @author castle
 * @since 2021-04-01
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLoginEntity> implements LogLoginService {

    /**
    * 获取查询条件
    * @param logLoginDto
    * @return
    */
    public QueryWrapper<LogLoginEntity> getWrapper(LogLoginDto logLoginDto){
        QueryWrapper<LogLoginEntity> wrapper= new QueryWrapper();
        if(logLoginDto != null){
            LogLoginEntity logLoginEntity = ConvertUtil.transformObj(logLoginDto,LogLoginEntity.class);
            wrapper.like(StrUtil.isNotEmpty(logLoginEntity.getInvokeUrl()),"invoke_url",logLoginEntity.getInvokeUrl());
            wrapper.like(StrUtil.isNotEmpty(logLoginEntity.getRemoteAddr()),"remote_addr",logLoginEntity.getRemoteAddr());
            wrapper.like(StrUtil.isNotEmpty(logLoginEntity.getInvokeParams()),"invoke_params",logLoginEntity.getInvokeParams());
            wrapper.like(StrUtil.isNotEmpty(logLoginEntity.getInvokeStatus()),"invoke_status",logLoginEntity.getInvokeStatus());
            wrapper.like(logLoginEntity.getInvokeTime()!=null,"invoke_time",logLoginEntity.getInvokeTime());
            wrapper.like(logLoginEntity.getElapsedTime()!=null,"elapsed_time",logLoginEntity.getElapsedTime());
            if(logLoginDto.getInvokeTimeStart()!=null && logLoginDto.getInvokeTimeEnd()!=null){
                wrapper.between("invoke_time",logLoginDto.getInvokeTimeStart(),logLoginDto.getInvokeTimeEnd());
            }
        }
        wrapper.orderByDesc("invoke_time");
        return wrapper;
    }


    @Override
    public IPage<LogLoginDto> pageLogLogin(Page<LogLoginDto> page, LogLoginDto logLoginDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        LogLoginEntity logLoginEntity = ConvertUtil.transformObj(logLoginDto,LogLoginEntity.class);
        List<LogLoginEntity> loginEntityList = baseMapper.logLoginList(pageMap,logLoginEntity);
        Long total = baseMapper.logLoginCount(logLoginEntity);
        Page<LogLoginDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(loginEntityList,LogLoginDto.class));
        return pageDto;
    }


    @Override
    public List<LogLoginDto> listLogLogin(LogLoginDto logLoginDto){
        QueryWrapper<LogLoginEntity> wrapper = getWrapper(logLoginDto);
        List<LogLoginEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,LogLoginDto.class);
    }

    @Async
    @Override
    public void saveLog(LogLoginEntity logEntity) {
            baseMapper.insert(logEntity);
    }
}

