package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.dto.KbPropertyDesignDto;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
import com.castle.fortress.admin.knowledge.enums.FileTypeEnum;
import com.castle.fortress.admin.knowledge.enums.FromTypeEnum;
import com.castle.fortress.admin.knowledge.mapper.KbColConfigMapper;
import com.castle.fortress.admin.knowledge.service.*;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * kb模型字段配置表 服务实现类
 *
 * @author sunhr
 * @since 2023-04-19
 */
@Service
public class KbColConfigServiceImpl extends ServiceImpl<KbColConfigMapper, KbPropertyDesignEntity> implements KbColConfigService {

    @Autowired
    private KbModelService kbModelService;
    @Autowired
    private KbColConfigMapper kbColConfigMapper;
    @Autowired
    private KbModelDataService kbModelDataService;
    @Autowired
    private KbBasicUserService kbBasicUserService;

    /**
     * 获取查询条件
     *
     * @param kbPropertyDesignDto
     * @return
     */
    public QueryWrapper<KbPropertyDesignEntity> getWrapper(KbPropertyDesignDto kbPropertyDesignDto) {
        QueryWrapper<KbPropertyDesignEntity> wrapper = new QueryWrapper();
        if (kbPropertyDesignDto != null) {
            KbPropertyDesignEntity kbPropertyDesignEntity = ConvertUtil.transformObj(kbPropertyDesignDto, KbPropertyDesignEntity.class);
            wrapper.like(kbPropertyDesignEntity.getModelId() != null, "model_id", kbPropertyDesignEntity.getModelId());
            wrapper.like(StrUtil.isNotEmpty(kbPropertyDesignEntity.getName()), "name", kbPropertyDesignEntity.getName());
            wrapper.like(StrUtil.isNotEmpty(kbPropertyDesignEntity.getPropName()), "prop_name", kbPropertyDesignEntity.getPropName());
            wrapper.like(kbPropertyDesignEntity.getIsDeleted() != null, "is_deleted", kbPropertyDesignEntity.getIsDeleted());
            wrapper.eq(kbPropertyDesignEntity.getStatus() != null, "status", kbPropertyDesignEntity.getStatus());
        }
        return wrapper;
    }


    @Override
    public IPage<KbPropertyDesignDto> pageKbColConfig(Page<KbPropertyDesignDto> page, KbPropertyDesignDto kbPropertyDesignDto) {
        QueryWrapper<KbPropertyDesignEntity> wrapper = getWrapper(kbPropertyDesignDto);
        Page<KbPropertyDesignEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbPropertyDesignEntity> kbColConfigPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbPropertyDesignDto> pageDto = new Page(kbColConfigPage.getCurrent(), kbColConfigPage.getSize(), kbColConfigPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbColConfigPage.getRecords(), KbPropertyDesignDto.class));
        return pageDto;
    }


    @Override
    public List<KbPropertyDesignDto> listKbColConfig(KbPropertyDesignDto kbPropertyDesignDto) {
        QueryWrapper<KbPropertyDesignEntity> wrapper = getWrapper(kbPropertyDesignDto);
        List<KbPropertyDesignEntity> list = baseMapper.selectList(wrapper);
        List<KbPropertyDesignDto> dtos = ConvertUtil.transformObjList(list, KbPropertyDesignDto.class);
        return dtos;
    }

    @Override
    public List<KbPropertyDesignEntity> fillType(Long modelId, List<KbPropertyDesignEntity> list) {
        for (KbPropertyDesignEntity e : list) {
            if (modelId != null) {
                e.setModelId(modelId);
            }
            e.setId(IdWorker.getId());
            switch (e.getFormType()) {
                //多行文本
                case 2:
                    e.setDataType("text");
                    //附件
                case 4: {
                    e.setDataType("mediumtext");
                    break;
                }
                //日期时间
                case 3:
                    e.setDataType("datetime");
                    break;
                case 5:
                    e.setDataType("longtext");
                    break;
                //单行文本
                default:
                    e.setDataType("varchar(255)");
                    break;
            }
        }
        return list;
    }

    @Async
    @Override
    public RespBody saveCols(KbModelEntity kbModelEntity, List<KbPropertyDesignEntity> entities) {
        int i = 1;
        if (entities != null && !entities.isEmpty()) {
            for (KbPropertyDesignEntity entity : entities) {
                entity.setPropName("col_" + i);
                i++;
                entity.setIsDeleted(2);
            }
            entities = fillType(kbModelEntity.getId(), entities);
            saveBatch(entities);

            //创建表
            List<String> tableNames = allTableNames();
            for (String name : tableNames) {
                if (name.equals("kb_model_ks_" + kbModelEntity.getCode())) {
                    return RespBody.fail(BizErrorCode.FORM_TABLE_EXIST_ERROR);
                }
            }
            KbModelAcceptanceDto kbModelAcceptanceDto = new KbModelAcceptanceDto();
            kbModelAcceptanceDto.setPropCols(entities);
            kbModelService.checkColumnRepeat(kbModelAcceptanceDto);
            // 创建基础表
            boolean table = baseMapper.createTable("kb_model_ks_" + kbModelEntity.getCode(), kbModelEntity.getName(), entities);

            // 创建历史表
            boolean tableHistory = baseMapper.createHistoryTable("kb_model_ks_history_" + kbModelEntity.getCode(), kbModelEntity.getName(), entities);
            //查看字段状态 如果为不启用 修改表结构为非必填
            for (KbPropertyDesignEntity entity : entities) {
                if (entity.getStatus() == 2) {
                    String code = kbModelEntity.getCode();
                    String propName = entity.getPropName();
                    kbColConfigMapper.alterTable("kb_model_ks_" + code, propName);
                    kbColConfigMapper.alterTable("kb_model_ks_history_" + code, propName);
                }
            }
            if (!table || !tableHistory) {
                return RespBody.fail("创建表失败");
            }
        }
        return RespBody.success("创建成功");
    }

    @Async
    @Override
    public void updateCols(KbModelEntity kbModelEntity, List<KbPropertyDesignEntity> entities) {
        QueryWrapper<KbPropertyDesignEntity> w = new QueryWrapper<>();
        w.eq("model_id", kbModelEntity.getId());
        //获取旧的字段信息
        List<KbPropertyDesignEntity> oldCols = baseMapper.selectList(w);
        //清除原数据  保存新数据
        kbColConfigMapper.removeAll(kbModelEntity.getId());
        entities = fillType(kbModelEntity.getId(), entities);
        ArrayList<Integer> list1 = new ArrayList<>();
        for (KbPropertyDesignEntity entity : entities) {
            if (entity.getPropName() != null) {
                String[] split = entity.getPropName().split("_");
                Integer integer = Integer.valueOf(split[1]);
                list1.add(integer);
            }
        }

        if ((oldCols==null || oldCols.size()==0) && list1.size() == 0) {
            Integer start = 1;
            for (KbPropertyDesignEntity entity : entities) {
                start++;
                entity.setPropName("col_" + start);
            }

        } else {
            Integer integer = 1;
            if (list1.size() > 0){
                integer= list1.stream().max(Integer::compare).get();
            }
            if(oldCols!=null && oldCols.size()>0){
                int dIndex = oldCols.stream().map(item->{return Integer.parseInt(item.getPropName().split("_")[1]);}).max(Integer::compare).get();
                integer = integer> dIndex ? integer : dIndex;
            }
            for (KbPropertyDesignEntity entity : entities) {
                if (entity.getPropName() == null) {
                    integer++;
                    entity.setPropName("col_" + integer);
                }
            }
        }
        saveBatch(entities);
        if (entities != null && !entities.isEmpty()) {

            //新扩展字段
            if (oldCols == null || oldCols.isEmpty()) {
                saveCols(kbModelEntity, entities);
                //修改字段
            } else {
                //修改表结构
                List<KbPropertyDesignEntity> addCols = new ArrayList<>();
                List<KbPropertyDesignEntity> modifyCols = new ArrayList<>();
                List<KbPropertyDesignEntity> existCols = new ArrayList<>();
                boolean isFind = false;
                for (KbPropertyDesignEntity newCol : entities) {
                    isFind = false;
                    for (KbPropertyDesignEntity oldCol : oldCols) {
                        if (newCol.getPropName().equals(oldCol.getPropName())   ) {
                            isFind = true;
                            existCols.add(oldCol);
                            if (!newCol.equals(oldCol)) {
                                modifyCols.add(newCol);
                            }
                            break;
                        }
                    }
                    if (!isFind) {
                        addCols.add(newCol);
                    }
                }
                oldCols.removeAll(existCols);
                //删除字段
                if (!oldCols.isEmpty()) {
                    baseMapper.alterTableDrop("kb_model_ks_" + kbModelEntity.getCode(), oldCols);
                    baseMapper.alterTableDrop("kb_model_ks_history_" + kbModelEntity.getCode(), oldCols);
                }
                //修改字段
                if (!modifyCols.isEmpty()) {
                    baseMapper.alterTableModify("kb_model_ks_" + kbModelEntity.getCode(), modifyCols);
                    baseMapper.alterTableModify("kb_model_ks_history_" + kbModelEntity.getCode(), modifyCols);
                }
                //添加字段
                if (!addCols.isEmpty()) {
                    //判断字段是否重复
                    KbModelAcceptanceDto kbModelAcceptanceDto = new KbModelAcceptanceDto();
                    kbModelAcceptanceDto.setPropCols(addCols);
                    kbModelService.checkColumnRepeat(kbModelAcceptanceDto);
                    baseMapper.alterTableAdd("kb_model_ks_" + kbModelEntity.getCode(), addCols);
                    baseMapper.alterTableAdd("kb_model_ks_history_" + kbModelEntity.getCode(), addCols);
                    for (KbPropertyDesignEntity addCol : addCols) {
                        if (addCol.getStatus() == 2) {
                            kbColConfigMapper.alterTable("kb_model_ks_" + kbModelEntity.getCode(), addCol.getPropName());
                            kbColConfigMapper.alterTable("kb_model_ks_history_" + kbModelEntity.getCode(), addCol.getPropName());
                        }
                    }
                    //扩展字段清空
                } else {
                    if (oldCols != null && !oldCols.isEmpty()) {
                        baseMapper.dropTable("kb_model_" + kbModelEntity.getCode());
                        baseMapper.dropTable("kb_model_ks_history_" + kbModelEntity.getCode());
                    }
                }
            }
        }
    }

    @Override
    public List<String> allTableNames() {
        return baseMapper.allTableNames();
    }

    @Async
    @Override
    public void delCols(KbModelEntity e) {
        QueryWrapper<KbPropertyDesignEntity> w = new QueryWrapper<>();
        w.eq("model_id", e.getId());
        baseMapper.delete(w);
        baseMapper.dropTable("kb_model_ks_history_"+e.getCode());
        baseMapper.dropTable("kb_model_ks_"+e.getCode());

    }

    @Override
    public RespBody checkColumnRepeat(KbPropertyDesignDto dto) {
        //校验code
        QueryWrapper<KbPropertyDesignEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("prop_name", dto.getName());
        List<KbPropertyDesignEntity> list = baseMapper.selectList(queryWrapper);
        if (list != null && !list.isEmpty()) {
            //新增
            if (dto.getId() == null) {
                return RespBody.fail(BizErrorCode.CMS_COL_REPEAT_ERROR);
                //修改
            } else {
                for (KbPropertyDesignEntity entity : list) {
                    if (!entity.getId().equals(dto.getId())) {
                        return RespBody.fail(BizErrorCode.CMS_COL_REPEAT_ERROR);
                    }
                }
            }
        }
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("prop_name", dto.getPropName());
        list = baseMapper.selectList(queryWrapper);
        if (list != null && !list.isEmpty()) {
            //新增
            if (dto.getId() == null) {
                return RespBody.fail(BizErrorCode.CMS_PROP_REPEAT_ERROR);
                //修改
            } else {
                for (KbPropertyDesignEntity entity : list) {
                    if (!entity.getId().equals(dto.getId())) {
                        return RespBody.fail(BizErrorCode.CMS_PROP_REPEAT_ERROR);
                    }
                }
            }
        }
        return RespBody.data("校验通过");
    }

    @Override
    public List<KbPropertyDesignDto> listCmsColConfig(KbPropertyDesignDto kbPropertyDesignDto) {
        QueryWrapper<KbPropertyDesignEntity> wrapper = getWrapper(kbPropertyDesignDto);
        List<KbPropertyDesignEntity> list = baseMapper.selectList(wrapper);
        List<KbPropertyDesignDto> dtos = ConvertUtil.transformObjList(list, KbPropertyDesignDto.class);
        for (KbPropertyDesignDto dto : dtos) {
            if (FromTypeEnum.SELECT_FRAME.getCode().equals(dto.getFormType())){
                String selectFrameStr = String.valueOf(dto.getSelectFrame());
                dto.setSelectFrame( JSONArray.parseArray(selectFrameStr,Object.class));
            }
        }
        return dtos;
    }

    @Override
    public KbModelTransmitDto findColDate(KbModelTransmitDto allByBasic, Long id) {
        //查询基础信息
        Long modelId = allByBasic.getModelId();
        //查询当前模型下所有字段类型为日期和附件格式的字段
        LambdaQueryWrapper<KbPropertyDesignEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(KbPropertyDesignEntity::getModelId, modelId);
        lqw.eq(KbPropertyDesignEntity::getIsDeleted, 2);
        lqw.in(KbPropertyDesignEntity::getFormType, 4, 3);
        List<KbPropertyDesignEntity> colsType = list(lqw);
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (KbPropertyDesignEntity kbPropertyDesignEntity : colsType) {
            hashMap.put(kbPropertyDesignEntity.getPropName(), kbPropertyDesignEntity.getFormType());
        }
        String code = allByBasic.getModelCode();
        //查询当前知识下拓展表数据
        Map<String, Object> map = kbModelDataService.queryByDataId(code, id);
        if (map == null) {
            return allByBasic;
        }
        Map<String, Object> map2 = new HashMap<>();
        // Json化日期格式
        map.forEach((k, v) -> {
            if (hashMap.containsKey(k)) {
                if (hashMap.get(k) == 3 && v != null) {
                    map2.put(k, DateUtil.format(DateUtil.parse(v.toString()), "yyyy-MM-dd"));
                } else if (hashMap.get(k) == 4 && v != null) {
                    map2.put(k, JSONObject.parseArray(v.toString()));
                }
            } else {
                map2.put(k, v);
            }
        });
        allByBasic.setCols(map2);
        return allByBasic;
    }

    @Override
    public void findHistoryColDate(KbModelTransmitDto allByBasic, Long id) {
        //查询基础信息
        Long modelId = allByBasic.getModelId();
        //查询当前模型下所有字段类型为日期和附件格式的字段
        LambdaQueryWrapper<KbPropertyDesignEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(KbPropertyDesignEntity::getModelId, modelId);
        lqw.eq(KbPropertyDesignEntity::getIsDeleted, 2);
        List<KbPropertyDesignEntity> colsType = list(lqw);
        // 将获取出来的列转换成map
        Map<String, KbPropertyDesignEntity> typeMap = colsType.stream().collect(Collectors.toMap(it -> it.getName(), it -> it));
        //查询当前知识下拓展表数据
        Map<String, Object> map = kbModelDataService.queryByHistoryDataId(allByBasic.getModelCode(), id);
        if (map == null) {
            return;
        }
        HashMap<String, Object> resColsMap = new HashMap<>();
        map.forEach((key, value) -> {
            KbPropertyDesignEntity designEntity = typeMap.get(key);
            if (designEntity != null) {
                if (designEntity.getFormType() == 3) {
                    resColsMap.put(designEntity.getPropName(), DateUtil.format(DateUtil.parse(value.toString()), "yyyy-MM-dd"));
                } else if (designEntity.getFormType() == 4) {
                    resColsMap.put(designEntity.getPropName(), JSONObject.parseArray(value.toString()));
                }
            } else {
                resColsMap.put(key, value);
            }
        });
        allByBasic.setCols(resColsMap);
    }
}

