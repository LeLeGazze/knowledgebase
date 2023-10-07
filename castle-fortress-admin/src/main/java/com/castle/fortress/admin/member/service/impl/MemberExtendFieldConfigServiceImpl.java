package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.admin.member.dto.FieldTypeMapDto;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.entity.MemberExtendFieldConfigEntity;
import com.castle.fortress.admin.member.dto.MemberExtendFieldConfigDto;
import com.castle.fortress.admin.member.mapper.MemberExtendFieldConfigMapper;
import com.castle.fortress.admin.member.service.MemberExtendFieldConfigService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 用户扩展字段配置表 服务实现类
 *
 * @author whc
 * @since 2022-11-23
 */
@Service
public class MemberExtendFieldConfigServiceImpl extends ServiceImpl<MemberExtendFieldConfigMapper, MemberExtendFieldConfigEntity> implements MemberExtendFieldConfigService {
    /**
     * 获取查询条件
     *
     * @param memberExtendFieldConfigDto
     * @return
     */
    public QueryWrapper<MemberExtendFieldConfigEntity> getWrapper(MemberExtendFieldConfigDto memberExtendFieldConfigDto) {
        QueryWrapper<MemberExtendFieldConfigEntity> wrapper = new QueryWrapper();
        if (memberExtendFieldConfigDto != null) {
            MemberExtendFieldConfigEntity memberExtendFieldConfigEntity = ConvertUtil.transformObj(memberExtendFieldConfigDto, MemberExtendFieldConfigEntity.class);
        }
        wrapper.orderByDesc("sort");
        return wrapper;
    }


    @Override
    public IPage<MemberExtendFieldConfigDto> pageMemberExtendFieldConfig(Page<MemberExtendFieldConfigDto> page, MemberExtendFieldConfigDto memberExtendFieldConfigDto) {
        QueryWrapper<MemberExtendFieldConfigEntity> wrapper = getWrapper(memberExtendFieldConfigDto);
        Page<MemberExtendFieldConfigEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberExtendFieldConfigEntity> memberExtendFieldConfigPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<MemberExtendFieldConfigDto> pageDto = new Page(memberExtendFieldConfigPage.getCurrent(), memberExtendFieldConfigPage.getSize(), memberExtendFieldConfigPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberExtendFieldConfigPage.getRecords(), MemberExtendFieldConfigDto.class));
        return pageDto;
    }


    @Override
    public List<MemberExtendFieldConfigDto> listMemberExtendFieldConfig(MemberExtendFieldConfigDto memberExtendFieldConfigDto) {
        QueryWrapper<MemberExtendFieldConfigEntity> wrapper = getWrapper(memberExtendFieldConfigDto);
        List<MemberExtendFieldConfigEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, MemberExtendFieldConfigDto.class);
    }


    @Override
    public void generate() {
        List<String> tables = baseMapper.allTableNames();
        List<MemberExtendFieldConfigEntity> fieldConfigs = baseMapper.selectList(null);
        final String tableName = "member_extend_field";
        if (tables.contains(tableName)) {
            //表已经存在 对字段进行增删改
            List<String> fieldNames = baseMapper.allFieldNames(tableName);
            for (MemberExtendFieldConfigEntity fieldConfig : fieldConfigs) {
                //判断字段是否存在于现有的表
                boolean fieldExists = checkFieldExists(fieldConfig, fieldNames);
                if (!fieldExists) {
                    //不存在 直接新增
                    baseMapper.addField(fieldConfig, tableName);
                    break;
                }
                FieldTypeMapDto field = baseMapper.getField(fieldConfig.getColName(), tableName);
                boolean fieldChangeType = checkFieldType(fieldConfig, field);
                if (!fieldChangeType) {
                    //删除已有列
                    baseMapper.deleteField(fieldConfig.getColName(), tableName);
                    baseMapper.addField(fieldConfig, tableName);
                }

            }
            checkRemoveField(tableName);
            return;
        }
        //表不存在 直接创建
        baseMapper.createTable(fieldConfigs);
    }

    @Override
    public List<HashMap<String, String>> extendInfoMember(Long id) {
        //首先查出所有的字段名 然后根据字段名 查询出对应的设置 然后返回
        final List<MemberExtendFieldConfigDto> memberExtendFieldConfigDtos = listMemberExtendFieldConfig(null);
        final List<HashMap<String, String>> result = new ArrayList<>();
        List<String> colNames = memberExtendFieldConfigDtos.stream().map(MemberExtendFieldConfigDto::getColName).collect(Collectors.toList());
        colNames.forEach(colName -> {
            HashMap<String, String> row = baseMapper.getRow(colName, id);
            result.add(row);
        });
        return result;
    }

    @Override
    public void updateOrCreateByMemberId(Long memberId, HashMap<String, String> params) {
        final List<String> fieldNames = baseMapper.allFieldNames("member_extend_field");
        Long exists = baseMapper.existsByMemberId(memberId);
        if (exists == null || exists <= 0) {
            baseMapper.createByMemberId(memberId);
        }
        if (params != null && params.keySet().size() > 0) {
            params.forEach((key, value) -> {
                if (!fieldNames.contains(key)) {
                    params.remove(key);
                }
            });
            baseMapper.updateByMemberId(memberId, params);
        }
    }

    //如果存在现在的表 但是不存在于设置中
    private void checkRemoveField(String tableName) {
        List<String> fieldNames = baseMapper.allFieldNames(tableName);
        List<String> setField = baseMapper.selectList(null).stream().map(MemberExtendFieldConfigEntity::getColName).collect(Collectors.toList());
        for (String field : fieldNames) {
            if ("member_id".equals(field) || "id".equals(field)) {
                continue;
            }
            //如果现在数据库中的字段不存在于设置中 那就删除
            if (!setField.contains(field)) {
                baseMapper.deleteField(field, tableName);
            }
        }
    }

    //检测字段类型是否一致
    private boolean checkFieldType(MemberExtendFieldConfigEntity fieldConfig, FieldTypeMapDto field) {
        if (field.getType().equals(fieldConfig.getColType())) {
            //     --类型一致 不需要修改
            return true;
        }
        //     --类型不一致 判断是否同类型的 例如 varchar 和varchar  int 和bigint
        //判断长度
        return false;
    }

    //检测字段类型是否存在
    private boolean checkFieldExists(MemberExtendFieldConfigEntity fieldConfig, List<String> fieldNames) {
        return fieldNames.contains(fieldConfig.getColName());
    }
}

