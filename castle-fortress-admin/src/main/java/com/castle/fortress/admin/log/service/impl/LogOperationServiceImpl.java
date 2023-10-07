package com.castle.fortress.admin.log.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.log.dto.LogOperationDto;
import com.castle.fortress.admin.log.entity.LogOperationEntity;
import com.castle.fortress.admin.log.mapper.LogOperationMapper;
import com.castle.fortress.admin.log.service.LogOperationService;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户操作记录日志表 服务实现类
 *
 * @author castle
 * @since 2021-03-31
 */
@Service
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperationEntity> implements LogOperationService {
    /**
    * 获取查询条件
    * @param logOperationDto
    * @return
    */
    public QueryWrapper<LogOperationEntity> getWrapper(LogOperationDto logOperationDto){
        QueryWrapper<LogOperationEntity> wrapper= new QueryWrapper();
        if(logOperationDto != null){
            LogOperationEntity logOperationEntity = ConvertUtil.transformObj(logOperationDto,LogOperationEntity.class);
            wrapper.like(StrUtil.isNotEmpty(logOperationEntity.getInvokeUrl()),"invoke_url",logOperationEntity.getInvokeUrl());
            wrapper.eq(StrUtil.isNotEmpty(logOperationEntity.getClassName()),"class_name",logOperationEntity.getClassName());
            wrapper.eq(StrUtil.isNotEmpty(logOperationEntity.getMethodName()),"method_name",logOperationEntity.getMethodName());
            wrapper.eq(StrUtil.isNotEmpty(logOperationEntity.getInvokeStatus()),"invoke_status",logOperationEntity.getInvokeStatus());
            wrapper.eq(logOperationEntity.getInvokeUserId() != null,"invoke_user_id",logOperationEntity.getInvokeUserId());
            wrapper.like(StrUtil.isNotEmpty(logOperationEntity.getInvokeUserName()),"invoke_user_name",logOperationEntity.getInvokeUserName());
            wrapper.eq(logOperationEntity.getInvokeTime() != null,"invoke_time",logOperationEntity.getInvokeTime());

            wrapper.like(StrUtil.isNotEmpty(logOperationEntity.getOperLocation()),"oper_location",logOperationEntity.getOperLocation());
            wrapper.eq(logOperationEntity.getOperType() != null,"oper_type",logOperationEntity.getOperType());

            if(logOperationDto.getInvokeTimeStart()!=null && logOperationDto.getInvokeTimeEnd()!=null){
                wrapper.between("invoke_time",logOperationDto.getInvokeTimeStart(),logOperationDto.getInvokeTimeEnd());
            }
        }
        wrapper.orderByDesc("invoke_time");
        return wrapper;
    }


    @Override
    public IPage<LogOperationDto> pageLogOperation(Page<LogOperationDto> page, LogOperationDto logOperationDto) {
        QueryWrapper<LogOperationEntity> wrapper = getWrapper(logOperationDto);
        Page<LogOperationEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<LogOperationEntity> logOperationPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<LogOperationDto> pageDto = new Page(logOperationPage.getCurrent(), logOperationPage.getSize(),logOperationPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(logOperationPage.getRecords(),LogOperationDto.class));
        return pageDto;
    }


    @Override
    public List<LogOperationDto> listLogOperation(LogOperationDto logOperationDto){
        QueryWrapper<LogOperationEntity> wrapper = getWrapper(logOperationDto);
        List<LogOperationEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,LogOperationDto.class);
    }
    @Async
    @Override
    public void saveLog(LogOperationEntity logOperationEntity) {
        baseMapper.insert(logOperationEntity);
    }

    @Override
    public void deleteByTime(int days) {
        baseMapper.deleteByTime(days);
    }
}

