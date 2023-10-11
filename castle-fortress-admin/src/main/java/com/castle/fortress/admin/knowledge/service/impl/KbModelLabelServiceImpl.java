package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicLabelEntity;
import com.castle.fortress.admin.knowledge.entity.KbCategoryLabelEntity;
import com.castle.fortress.admin.knowledge.service.KbBasicLabelService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.mapper.KbModelLabelMapper;
import com.castle.fortress.admin.knowledge.service.KbModelLabelService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.stream.Collectors;

/**
 * 标签管理表 服务实现类
 *
 * @author sunhr
 * @since 2023-04-26
 */
@Service
public class KbModelLabelServiceImpl extends ServiceImpl<KbModelLabelMapper, KbModelLabelEntity> implements KbModelLabelService {
    @Autowired
    private KbModelLabelMapper kbModelLabelMapper;
    @Autowired
    private KbBasicLabelService kbBasicLabelService;

    /**
     * 获取查询条件
     *
     * @param kbModelLabelDto
     * @return
     */
    public QueryWrapper<KbModelLabelEntity> getWrapper(KbModelLabelDto kbModelLabelDto) {
        QueryWrapper<KbModelLabelEntity> wrapper = new QueryWrapper();
        if (kbModelLabelDto != null) {
            KbModelLabelEntity kbModelLabelEntity = ConvertUtil.transformObj(kbModelLabelDto, KbModelLabelEntity.class);
            wrapper.like(kbModelLabelEntity.getId() != null, "id", kbModelLabelEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbModelLabelEntity.getName()), "name", kbModelLabelEntity.getName());
            wrapper.like(kbModelLabelEntity.getStatus() != null, "status", kbModelLabelEntity.getStatus());
            wrapper.like(kbModelLabelEntity.getSort() != null, "sort", kbModelLabelEntity.getSort());
            wrapper.like(kbModelLabelEntity.getHotWord() != null, "hot_word", kbModelLabelEntity.getHotWord());
        }
        return wrapper;
    }


    @Override
    public IPage<KbModelLabelDto> pageKbModelLabel(Page<KbModelLabelDto> page, KbModelLabelDto kbModelLabelDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        if (kbModelLabelDto == null) {
            KbModelLabelDto kbModelLabelDto1 = new KbModelLabelDto();
            kbModelLabelDto1.setStatus(null);
            kbModelLabelDto1.setIsDeleted(null);
            List<KbModelLabelDto> basic = kbModelLabelMapper.selectPageLabel(kbModelLabelDto1, pageMap);
            Integer total = kbModelLabelMapper.selectPageLabelCount(kbModelLabelDto);
            Page<KbModelLabelDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(basic);
            return pageDto;
        } else {
            List<KbModelLabelDto> basic = kbModelLabelMapper.selectPageLabel(kbModelLabelDto, pageMap);
            Integer total = kbModelLabelMapper.selectPageLabelCount(kbModelLabelDto);
            Page<KbModelLabelDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(basic);
            return pageDto;
        }
    }


    @Override
    public List<KbModelLabelDto> listKbModelLabel(KbModelLabelDto kbModelLabelDto) {
        QueryWrapper<KbModelLabelEntity> wrapper = getWrapper(kbModelLabelDto);
        List<KbModelLabelEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbModelLabelDto.class);
    }

    @Override
    public List<KbModelLabelEntity> findIsExistName(List<String> labelName) {
        return kbModelLabelMapper.findIsExistName(labelName);
    }

    @Override
    public List<KbModelLabelDto> listHotWord() {
        List<KbModelLabelDto> kbModelLabelDtos = kbModelLabelMapper.listHotWord();
        return kbModelLabelDtos;
    }

    @Override
    public boolean updateBatch(List<KbModelLabelEntity> kbModelLabelEntitys) {
        return kbModelLabelMapper.updateBatch(kbModelLabelEntitys);
    }

    @Override
    public boolean updateLabel(KbModelLabelEntity kbModelLabelEntity) {
        return kbModelLabelMapper.updateLabel(kbModelLabelEntity);
    }

    @Override
    public void saveLabel(KbModelAcceptanceDto formDataDto) {
        List<KbModelLabelEntity> labels = formDataDto.getLabels();
        List<String> labelLiST = labels.stream().map(item -> item.getName()).collect(Collectors.toList());
        formDataDto.setLabel(labelLiST);
        // 查询哪个标签已经在数据库存在了
        List<KbModelLabelEntity> kbBasicLabelEntityList = kbModelLabelMapper.findIsExistNames(labelLiST);
        Map<String, KbModelLabelEntity> labelMap = new HashMap<>();
        for (KbModelLabelEntity kbModelLabelEntity : kbBasicLabelEntityList) {
            labelMap.put(kbModelLabelEntity.getName()+"_"+kbModelLabelEntity.getStatus(),kbModelLabelEntity);
        }
        ArrayList<KbBasicLabelEntity> kbBasicLabelEntities = new ArrayList<>();
        // 插入标签中间表
        for (KbModelLabelEntity labelName : labels) {
            if (labelMap.get(labelName.getName()+"_"+labelName.getStatus()) == null) {
                // 说明是空的 添加到标签表中
                KbModelLabelEntity kbModelLabelEntity = new KbModelLabelEntity();
                kbModelLabelEntity.setName(labelName.getName());
                //status 2：机选标签 手工是1
                if (labelName.getStatus() == null) {
                    throw new BizException("标签状态不允许为空");
                }
                kbModelLabelEntity.setStatus(labelName.getStatus());
                kbModelLabelEntity.setSort(1);
                kbModelLabelEntity.setHotWord(2);
                kbModelLabelMapper.insert(kbModelLabelEntity);
                //存入标签关联表
                KbBasicLabelEntity kbl = new KbBasicLabelEntity();
                kbl.setLId(kbModelLabelEntity.getId());
                kbl.setBId(formDataDto.getId());
                kbBasicLabelEntities.add(kbl);
            } else {
                // 说明之前已经添加过标签了直接插入中间表就行
                //存入标签关联表
                KbBasicLabelEntity kbl = new KbBasicLabelEntity();
                kbl.setLId(labelMap.get(labelName.getName()+"_"+labelName.getStatus()).getId());
                kbl.setBId(formDataDto.getId());
                kbBasicLabelEntities.add(kbl);
            }
        }
        kbBasicLabelService.saveBatch(kbBasicLabelEntities);
    }

    @Override
    public boolean removeLabels(List<Long> ids) {
        return kbModelLabelMapper.removeLabels(ids);
    }

    @Override
    public boolean updateByLabel(Long id) {
        return kbModelLabelMapper.updateByLabel(id);
    }

    @Override
    public RespBody<String> saveLabels(List<KbModelLabelDto> kbModelLabelDtos) {
        if (kbModelLabelDtos.size() == 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<KbModelLabelEntity> kbModelLabelEntities = ConvertUtil.transformObjList(kbModelLabelDtos, KbModelLabelEntity.class);
        //传过来的标签名
        List<String> list = kbModelLabelEntities.stream().map(KbModelLabelEntity::getName).collect(Collectors.toList());
        //已经存在的标签名
        List<KbModelLabelEntity> isExistName = findIsExistName(list);
        List<String> labelExists = isExistName.stream().map(KbModelLabelEntity::getName).collect(Collectors.toList());
        // 过滤出新增的标签
        List<KbModelLabelEntity> newLabel = kbModelLabelEntities.stream().filter(item -> !labelExists.contains(item.getName())).peek(item -> item.setStatus(2)).collect(Collectors.toList());
        HashMap<String, List<KbModelLabelEntity>> disposeHashMap = new HashMap<>();
        if (newLabel.size() != kbModelLabelDtos.size()) {
            isExistName.forEach(item -> {
                if (item.getIsDeleted() == 1) {
                    item.setIsDeleted(2);
                    disposeHashMap.computeIfAbsent("删除", k -> new ArrayList<>()).add(item);
                } else if (item.getIsDeleted() == 2) {
                    disposeHashMap.computeIfAbsent("存在", k -> new ArrayList<>()).add(item);
                }
            });
        }
        if (disposeHashMap.containsKey("存在")) {
            List<KbModelLabelEntity> existLabel = disposeHashMap.get("存在");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("标签【");
            for (KbModelLabelEntity labelEntity : existLabel) {
                stringBuilder.append(labelEntity.getName() + " ");
            }
            stringBuilder.append("】系统标签中已存在");
            return RespBody.fail(stringBuilder.toString());
        }
        if (disposeHashMap.containsKey("删除")) {
            List<KbModelLabelEntity> deleteLabel = disposeHashMap.get("删除");
            updateBatch(deleteLabel);
        }
        saveBatch(newLabel); //保存新增标签
        return RespBody.success("保存成功");
    }

    @Override
    public List<KbModelLabelEntity> findByBId(Long bid) {
        return baseMapper.findByUserId(bid);
    }
}

