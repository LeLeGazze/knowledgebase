package com.castle.fortress.admin.log.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.log.dto.LogSmsDto;
import com.castle.fortress.admin.log.entity.LogSmsEntity;
import com.castle.fortress.admin.log.enums.LogSmsStatusEnum;
import com.castle.fortress.admin.log.mapper.LogSmsMapper;
import com.castle.fortress.admin.log.service.LogSmsService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信发送记录 服务实现类
 *
 * @author Mgg
 * @since 2021-12-06
 */
@Service
public class LogSmsServiceImpl extends ServiceImpl<LogSmsMapper, LogSmsEntity> implements LogSmsService {
    /**
    * 获取查询条件
    * @param logSmsDto
    * @return
    */
    public QueryWrapper<LogSmsEntity> getWrapper(LogSmsDto logSmsDto){
        QueryWrapper<LogSmsEntity> wrapper= new QueryWrapper();
        if(logSmsDto != null){
            LogSmsEntity logSmsEntity = ConvertUtil.transformObj(logSmsDto,LogSmsEntity.class);
            wrapper.like(StrUtil.isNotEmpty(logSmsEntity.getSmsCode()),"sms_code",logSmsEntity.getSmsCode());
            wrapper.like(StrUtil.isNotEmpty(logSmsEntity.getMobile()),"mobile",logSmsEntity.getMobile());
            wrapper.like(logSmsEntity.getStatus() != null,"status",logSmsEntity.getStatus());
            wrapper.like(logSmsEntity.getCreateDate() != null,"create_date",logSmsEntity.getCreateDate());
            wrapper.like(logSmsEntity.getType() != null,"type",logSmsEntity.getType());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<LogSmsDto> pageLogSms(Page<LogSmsDto> page, LogSmsDto logSmsDto) {
        QueryWrapper<LogSmsEntity> wrapper = getWrapper(logSmsDto);
        Page<LogSmsEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<LogSmsEntity> logSmsPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<LogSmsDto> pageDto = new Page(logSmsPage.getCurrent(), logSmsPage.getSize(),logSmsPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(logSmsPage.getRecords(),LogSmsDto.class));
        return pageDto;
    }

    @Override
    public IPage<LogSmsDto> pageLogSmsExtends(Page<LogSmsDto> page, LogSmsDto logSmsDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        LogSmsEntity logSmsEntity = ConvertUtil.transformObj(logSmsDto,LogSmsEntity.class);
        List<LogSmsEntity> logSmsList=baseMapper.extendsList(pageMap,logSmsEntity);
        Long total = baseMapper.extendsCount(logSmsEntity);
        Page<LogSmsDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(logSmsList,LogSmsDto.class));
        return pageDto;
    }
    @Override
    public LogSmsDto getByIdExtends(Long id){
        LogSmsEntity  logSmsEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(logSmsEntity,LogSmsDto.class);
    }

    @Override
    public List<LogSmsDto> listLogSms(LogSmsDto logSmsDto){
        QueryWrapper<LogSmsEntity> wrapper = getWrapper(logSmsDto);
        List<LogSmsEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,LogSmsDto.class);
    }

    @Override
    public List<LogSmsDto> getDataList(Map<String, Object> params) {
        System.err.println(params);
        List<LogSmsEntity> list = baseMapper.selectDataList(params);
        return ConvertUtil.transformObjList(list , LogSmsDto.class);
    }

    @Override
    public void checkCodeSms() {
        // 短信暂设五分钟失效
        Long overTime = 5L *60*1000;
        Long now = System.currentTimeMillis();
        Map<String , Object> params = new HashMap<>();
        params.put("status" , LogSmsStatusEnum.VALID.getCode());
        List<LogSmsDto> list = getDataList(params);
        List<LogSmsEntity> updateList = new ArrayList<>();
        list.forEach(e->{
            if(e.getCreateDate().getTime() + overTime <= now){
                e.setStatus( LogSmsStatusEnum.INVALID.getCode());
                updateList.add(ConvertUtil.transformObj(e,LogSmsEntity.class));
            }
        });
        if(updateList.size() > 0){
            updateBatchById(updateList);
        }

    }
}

