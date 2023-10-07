package com.castle.fortress.admin.knowledge.service.impl;

import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.mapper.KbModelLabelMapper;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbBasicLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicLabelDto;
import com.castle.fortress.admin.knowledge.mapper.KbBasicLabelMapper;
import com.castle.fortress.admin.knowledge.service.KbBasicLabelService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 知识与标签的中间表 服务实现类
 *
 * @author 
 * @since 2023-04-28
 */
@Service
public class KbBasicLabelServiceImpl extends ServiceImpl<KbBasicLabelMapper, KbBasicLabelEntity> implements KbBasicLabelService {
    @Autowired
    private KbBasicLabelMapper kbBasicLabelMapper;
    @Autowired
    private KbModelLabelMapper kbModelLabelMapper;
    /**
    * 获取查询条件
    * @param kbBasicLabelDto
    * @return
    */
    public QueryWrapper<KbBasicLabelEntity> getWrapper(KbBasicLabelDto kbBasicLabelDto){
        QueryWrapper<KbBasicLabelEntity> wrapper= new QueryWrapper();
        if(kbBasicLabelDto != null){
            KbBasicLabelEntity kbBasicLabelEntity = ConvertUtil.transformObj(kbBasicLabelDto,KbBasicLabelEntity.class);
            wrapper.like(kbBasicLabelEntity.getId() != null,"id",kbBasicLabelEntity.getId());
            wrapper.like(kbBasicLabelEntity.getBId() != null,"b_id",kbBasicLabelEntity.getBId());
            wrapper.like(kbBasicLabelEntity.getLId() != null,"l_id",kbBasicLabelEntity.getLId());
        }
        return wrapper;
    }


    @Override
    public IPage<KbBasicLabelDto> pageKbBasicLabel(Page<KbBasicLabelDto> page, KbBasicLabelDto kbBasicLabelDto) {
        QueryWrapper<KbBasicLabelEntity> wrapper = getWrapper(kbBasicLabelDto);
        Page<KbBasicLabelEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbBasicLabelEntity> kbBasicLabelPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<KbBasicLabelDto> pageDto = new Page(kbBasicLabelPage.getCurrent(), kbBasicLabelPage.getSize(),kbBasicLabelPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbBasicLabelPage.getRecords(),KbBasicLabelDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbBasicLabelDto> pageKbBasicLabelExtends(Page<KbBasicLabelDto> page, KbBasicLabelDto kbBasicLabelDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        KbBasicLabelEntity kbBasicLabelEntity = ConvertUtil.transformObj(kbBasicLabelDto,KbBasicLabelEntity.class);
        List<KbBasicLabelEntity> kbBasicLabelList=baseMapper.extendsList(pageMap,kbBasicLabelEntity);
        Long total = baseMapper.extendsCount(kbBasicLabelEntity);
        Page<KbBasicLabelDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbBasicLabelList,KbBasicLabelDto.class));
        return pageDto;
    }
    @Override
    public KbBasicLabelDto getByIdExtends(Long id){
        KbBasicLabelEntity  kbBasicLabelEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbBasicLabelEntity,KbBasicLabelDto.class);
    }

    @Override
    public List<KbBasicLabelDto> listKbBasicLabel(KbBasicLabelDto kbBasicLabelDto){
        QueryWrapper<KbBasicLabelEntity> wrapper = getWrapper(kbBasicLabelDto);
        List<KbBasicLabelEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbBasicLabelDto.class);
    }

    @Override
    public void saveById(KbModelAcceptanceDto formDataDto) {
        Long id = formDataDto.getId();
        kbBasicLabelMapper.delectByIds(id);
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
        saveBatch(kbBasicLabelEntities);
    }

    @Override
    public List<Long> listById(Long id) {
        return kbBasicLabelMapper.selectBidByLid(id);
    }

    @Override
    public List<String> findByUidAuthLabelAdmin(Long uid) {
        return kbBasicLabelMapper.findByUidAuthLabelAdmin(uid,10L);
    }

    @Override
    public List<String> findByUidAuthLabel(Long uid, List<Integer> integers) {
        return  kbBasicLabelMapper.findByUidAuthLabel(uid,10L,integers);
    }

    @Override
    public Integer labelCount(Long lebalId) {
        return kbBasicLabelMapper.labelCount(lebalId);
    }

    @Override
    public List<KbModelLabelEntity> listBybId(Long id) {
        return kbBasicLabelMapper.listBybId(id);
    }

    public int delectByIds(Long id) {
        return baseMapper.delectByIds(id);
    }
}

