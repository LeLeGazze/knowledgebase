package com.castle.fortress.develop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.common.enums.ViewFormTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.develop.common.DbQueryConditionEnum;
import com.castle.fortress.develop.entity.DevColConfig;
import com.castle.fortress.develop.entity.DevDbConfig;
import com.castle.fortress.develop.entity.DevTbConfig;
import com.castle.fortress.develop.mapper.DevColConfigMapper;
import com.castle.fortress.develop.service.DevColConfigService;
import com.castle.fortress.develop.utils.DbUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发 数据源 配置服务实现类
 * @author castle
 */
@Service
public class DevColConfigServiceImpl extends ServiceImpl<DevColConfigMapper, DevColConfig> implements DevColConfigService {
    @Override
    public IPage<DevColConfig> pageColConfig(Page<DevColConfig> page, DevColConfig colConfig) {
        QueryWrapper<DevColConfig> wrapper= new QueryWrapper<>();
        Page<DevColConfig> colConfigPage=baseMapper.selectPage(page,wrapper);
        return colConfigPage;
    }

    @Override
    public boolean initColumns(DevDbConfig dbConfig, DevTbConfig tbConfig) {
        List<DevColConfig> colConfigList= DbUtils.getTableColumns(dbConfig,tbConfig.getId(),tbConfig.getTbName());
        for(DevColConfig colConfig:colConfigList){
            colConfig.setId(IdWorker.getId());
            colConfig.setPropName(StrUtil.toCamelCase(colConfig.getColName()));
            colConfig.setPropType(DbUtils.getProType(colConfig.getColType()));
            colConfig.setIsList(YesNoEnum.YES.getCode());
            colConfig.setIsQuery(YesNoEnum.YES.getCode());
            colConfig.setQueryType(DbQueryConditionEnum.LIKE.getCode().toString());
            colConfig.setIsCopy(YesNoEnum.NO.getCode());
            colConfig.setIsForm(YesNoEnum.YES.getCode());
            colConfig.setIsFormRequire(YesNoEnum.NO.getCode());
            colConfig.setFormType(ViewFormTypeEnum.TEXT.getCode());
        }
        return colConfigList.size() == baseMapper.insertBatch(colConfigList);
    }

    @Override
    public boolean delByTbId(Long tbId) {
        return baseMapper.delById(tbId)>0;
    }

    @Override
    public List<DevColConfig> listColConfig(Long tbId) {
        QueryWrapper<DevColConfig> wrapper= new QueryWrapper<>();
        wrapper.eq("tb_id",tbId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean synColumns(List<DevColConfig> oldCols, List<DevColConfig> newCols) {
        List<DevColConfig> newList=new ArrayList<>();
        List<DevColConfig> updateList=new ArrayList<>();
        List<DevColConfig> existList=new ArrayList<>();
        boolean isFind = false;
        for(DevColConfig newC:newCols){
            isFind = false;
            for(DevColConfig oldC:oldCols){
                if(oldC.getColName().equals(newC.getColName())){
                    isFind = true;
                    existList.add(oldC);
                    if(!oldC.getColType().equals(newC.getColType())){
                        oldC.setColType(newC.getColType());
                        oldC.setPropType(DbUtils.getProType(oldC.getColType()));
                        updateList.add(oldC);
                    }
                    break;
                }
            }
            if(!isFind){
                newC.setId(IdWorker.getId());
                newC.setPropName(StrUtil.toCamelCase(newC.getColName()));
                newC.setPropType(DbUtils.getProType(newC.getColType()));
                newC.setIsList(YesNoEnum.YES.getCode());
                newC.setIsQuery(YesNoEnum.YES.getCode());
                newC.setQueryType(DbQueryConditionEnum.LIKE.getCode().toString());
                newC.setIsCopy(YesNoEnum.NO.getCode());
                newC.setIsForm(YesNoEnum.YES.getCode());
                newC.setIsFormRequire(YesNoEnum.NO.getCode());
                newC.setFormType(ViewFormTypeEnum.TEXT.getCode());
                newList.add(newC);
            }
        }
        oldCols.removeAll(existList);
        if(!newList.isEmpty()){
            baseMapper.insertBatch(newList);
        }
        if(!oldCols.isEmpty()){
            List<Long> delIds = new ArrayList<>();
            for(DevColConfig d:oldCols){
                delIds.add(d.getId());
            }
            baseMapper.deleteBatchIds(delIds);
        }
        if(!updateList.isEmpty()){
            baseMapper.updateCols(updateList);
        }
        return true;
    }
}
