package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.dto.KbBaseLabelTaskDto;
import com.castle.fortress.admin.knowledge.mapper.KbBaseLabelTaskMapper;
import com.castle.fortress.admin.knowledge.service.KbBaseLabelTaskService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 标签删除任务表 服务实现类
 *
 * @author 
 * @since 2023-06-07
 */
@Service
public class KbBaseLabelTaskServiceImpl extends ServiceImpl<KbBaseLabelTaskMapper, KbBaseLabelTaskEntity> implements KbBaseLabelTaskService {
    /**
    * 获取查询条件
    * @param kbBaseLabelTaskDto
    * @return
    */
    public QueryWrapper<KbBaseLabelTaskEntity> getWrapper(KbBaseLabelTaskDto kbBaseLabelTaskDto){
        QueryWrapper<KbBaseLabelTaskEntity> wrapper= new QueryWrapper();
        if(kbBaseLabelTaskDto != null){
            KbBaseLabelTaskEntity kbBaseLabelTaskEntity = ConvertUtil.transformObj(kbBaseLabelTaskDto,KbBaseLabelTaskEntity.class);
            wrapper.eq(kbBaseLabelTaskEntity.getId() != null,"id",kbBaseLabelTaskEntity.getId());
            wrapper.eq(kbBaseLabelTaskEntity.getLId() != null,"l_id",kbBaseLabelTaskEntity.getLId());
            wrapper.eq(kbBaseLabelTaskEntity.getStatus() != null,"status",kbBaseLabelTaskEntity.getStatus());
        }
        return wrapper;
    }


    @Override
    public IPage<KbBaseLabelTaskDto> pageKbBaseLabelTask(Page<KbBaseLabelTaskDto> page, KbBaseLabelTaskDto kbBaseLabelTaskDto) {
        QueryWrapper<KbBaseLabelTaskEntity> wrapper = getWrapper(kbBaseLabelTaskDto);
        Page<KbBaseLabelTaskEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbBaseLabelTaskEntity> kbBaseLabelTaskPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<KbBaseLabelTaskDto> pageDto = new Page(kbBaseLabelTaskPage.getCurrent(), kbBaseLabelTaskPage.getSize(),kbBaseLabelTaskPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbBaseLabelTaskPage.getRecords(),KbBaseLabelTaskDto.class));
        return pageDto;
    }


    @Override
    public List<KbBaseLabelTaskDto> listKbBaseLabelTask(KbBaseLabelTaskDto kbBaseLabelTaskDto){
        QueryWrapper<KbBaseLabelTaskEntity> wrapper = getWrapper(kbBaseLabelTaskDto);
        List<KbBaseLabelTaskEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbBaseLabelTaskDto.class);
    }
}

