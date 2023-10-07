package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.service.KbModelLabelService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbCategoryLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbCategoryLabelDto;
import com.castle.fortress.admin.knowledge.mapper.KbCategoryLabelMapper;
import com.castle.fortress.admin.knowledge.service.KbCategoryLabelService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 标签分组和标签关联表 服务实现类
 *
 * @author 
 * @since 2023-06-14
 */
@Service
public class KbCategoryLabelServiceImpl extends ServiceImpl<KbCategoryLabelMapper, KbCategoryLabelEntity> implements KbCategoryLabelService {
    @Autowired
    public KbModelLabelService kbModelLabelService;
    /**
    * 获取查询条件
    * @param kbCategoryLabelDto
    * @return
    */
    public QueryWrapper<KbCategoryLabelEntity> getWrapper(KbCategoryLabelDto kbCategoryLabelDto){
        QueryWrapper<KbCategoryLabelEntity> wrapper= new QueryWrapper();
        if(kbCategoryLabelDto != null){
            KbCategoryLabelEntity kbCategoryLabelEntity = ConvertUtil.transformObj(kbCategoryLabelDto,KbCategoryLabelEntity.class);
            wrapper.like(kbCategoryLabelEntity.getId() != null,"id",kbCategoryLabelEntity.getId());
            wrapper.like(kbCategoryLabelEntity.getCtId() != null,"ct_id",kbCategoryLabelEntity.getCtId());
            wrapper.like(kbCategoryLabelEntity.getLId() != null,"l_id",kbCategoryLabelEntity.getLId());
        }
        return wrapper;
    }


    @Override
    public IPage<KbCategoryLabelDto> pageKbCategoryLabel(Page<KbCategoryLabelDto> page, KbCategoryLabelDto kbCategoryLabelDto) {
        QueryWrapper<KbCategoryLabelEntity> wrapper = getWrapper(kbCategoryLabelDto);
        Page<KbCategoryLabelEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbCategoryLabelEntity> kbCategoryLabelPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<KbCategoryLabelDto> pageDto = new Page(kbCategoryLabelPage.getCurrent(), kbCategoryLabelPage.getSize(),kbCategoryLabelPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbCategoryLabelPage.getRecords(),KbCategoryLabelDto.class));
        return pageDto;
    }


    @Override
    public List<KbCategoryLabelDto> listKbCategoryLabel(KbCategoryLabelDto kbCategoryLabelDto){
        QueryWrapper<KbCategoryLabelEntity> wrapper = getWrapper(kbCategoryLabelDto);
        List<KbCategoryLabelEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbCategoryLabelDto.class);
    }

    @Override
    public IPage<KbModelLabelDto> findLabelByCategory(Long ctId, Page<KbModelLabelDto> page, KbModelLabelDto kbModelLabelDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbModelLabelDto> basic = baseMapper.findLabelByCategory(ctId, pageMap,kbModelLabelDto);
        Integer total = baseMapper.findLabelByCategoryCount(ctId,kbModelLabelDto);
        Page<KbModelLabelDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(basic);
        return pageDto;
    }

    @Override
    public RespBody<String> findAllLabelByCategory(Long ctId) {
        List<KbModelLabelEntity> labels = baseMapper.findAllLabelByCategory(ctId);
        for (KbModelLabelEntity label : labels) {
            label.setIsDeleted(1);
        }
        if (kbModelLabelService.updateBatchById(labels)) {
            return RespBody.data("操作成功");
        }else {
            return RespBody.fail("操作失败");
        }
    }

    @Override
    public RespBody<String> editHotWordLabelByCategory(Long ctId) {
        List<KbModelLabelEntity> labels = baseMapper.findAllLabelByCategory(ctId);
        for (KbModelLabelEntity label : labels) {
            label.setHotWord(1);
        }
        if (kbModelLabelService.updateBatchById(labels)) {
            return RespBody.data("操作成功");
        }else {
            return RespBody.fail("操作失败");
        }
    }

    @Override
    public KbLabelCategoryEntity findCategoryByLabel(Long id) {
        return baseMapper.findCategoryByLabel(id);
    }

    @Override
    public List<KbLabelCategoryEntity> findCategoryByLabelIds(List<Long> ids) {
        return baseMapper.findCategoryByLabelIds(ids);
    }

    @Override
    public List<KbCategoryLabelEntity> isExistLabel(ArrayList<Long> longs) {
        return baseMapper.isExistLabel(longs);
    }

    @Override
    public Integer removeByLabelId(Long id) {
        return baseMapper.removeByLabelId(id);
    }

    @Override
    public void deleteBylId(ArrayList<KbCategoryLabelEntity> kbCategoryLabelEntities) {
        baseMapper.deleteBylId(kbCategoryLabelEntities);
    }
}

