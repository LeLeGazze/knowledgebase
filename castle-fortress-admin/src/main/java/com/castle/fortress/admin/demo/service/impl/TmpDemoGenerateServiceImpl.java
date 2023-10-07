package com.castle.fortress.admin.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.demo.dto.TmpDemoGenerateDto;
import com.castle.fortress.admin.demo.entity.TmpDemoGenerateEntity;
import com.castle.fortress.admin.demo.mapper.TmpDemoGenerateMapper;
import com.castle.fortress.admin.demo.service.TmpDemoGenerateService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 代码生成示例表 服务实现类
 *
 * @author castle
 * @since 2021-11-08
 */
@Service
public class TmpDemoGenerateServiceImpl extends ServiceImpl<TmpDemoGenerateMapper, TmpDemoGenerateEntity> implements TmpDemoGenerateService {
    /**
    * 获取查询条件
    * @param tmpDemoGenerateDto
    * @return
    */
    public QueryWrapper<TmpDemoGenerateEntity> getWrapper(TmpDemoGenerateDto tmpDemoGenerateDto){
        QueryWrapper<TmpDemoGenerateEntity> wrapper= new QueryWrapper();
        if(tmpDemoGenerateDto != null){
            TmpDemoGenerateEntity tmpDemoGenerateEntity = ConvertUtil.transformObj(tmpDemoGenerateDto,TmpDemoGenerateEntity.class);
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getName()),"name",tmpDemoGenerateEntity.getName());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getAuth()),"auth",tmpDemoGenerateEntity.getAuth());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getPhone()),"phone",tmpDemoGenerateEntity.getPhone());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getEmail()),"email",tmpDemoGenerateEntity.getEmail());
            wrapper.like(tmpDemoGenerateEntity.getVueNumber() != null,"vue_number",tmpDemoGenerateEntity.getVueNumber());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getVueRadio()),"vue_radio",tmpDemoGenerateEntity.getVueRadio());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getVueCheckbox()),"vue_checkbox",tmpDemoGenerateEntity.getVueCheckbox());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getVueSelect()),"vue_select",tmpDemoGenerateEntity.getVueSelect());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getSelectEnum()),"select_enum",tmpDemoGenerateEntity.getSelectEnum());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getSelectUrl()),"select_url",tmpDemoGenerateEntity.getSelectUrl());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getSelectJson()),"select_json",tmpDemoGenerateEntity.getSelectJson());
            wrapper.like(tmpDemoGenerateEntity.getVueDate() != null,"vue_date",tmpDemoGenerateEntity.getVueDate());
            wrapper.like(tmpDemoGenerateEntity.getVueDatetime() != null,"vue_datetime",tmpDemoGenerateEntity.getVueDatetime());
            wrapper.like(tmpDemoGenerateEntity.getStatus() != null,"status",tmpDemoGenerateEntity.getStatus());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getRedioEnum()),"redio_enum",tmpDemoGenerateEntity.getRedioEnum());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getRadioUrl()),"radio_url",tmpDemoGenerateEntity.getRadioUrl());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getRadioJson()),"radio_json",tmpDemoGenerateEntity.getRadioJson());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getCheckboxEnum()),"checkbox_enum",tmpDemoGenerateEntity.getCheckboxEnum());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getCheckboxUrl()),"checkbox_url",tmpDemoGenerateEntity.getCheckboxUrl());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoGenerateEntity.getCheckboxJson()),"checkbox_json",tmpDemoGenerateEntity.getCheckboxJson());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<TmpDemoGenerateDto> pageTmpDemoGenerate(Page<TmpDemoGenerateDto> page, TmpDemoGenerateDto tmpDemoGenerateDto) {
        QueryWrapper<TmpDemoGenerateEntity> wrapper = getWrapper(tmpDemoGenerateDto);
        Page<TmpDemoGenerateEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<TmpDemoGenerateEntity> tmpDemoGeneratePage=baseMapper.selectPage(pageEntity,wrapper);
        Page<TmpDemoGenerateDto> pageDto = new Page(tmpDemoGeneratePage.getCurrent(), tmpDemoGeneratePage.getSize(),tmpDemoGeneratePage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(tmpDemoGeneratePage.getRecords(),TmpDemoGenerateDto.class));
        return pageDto;
    }

    @Override
    public IPage<TmpDemoGenerateDto> pageTmpDemoGenerateExtends(Page<TmpDemoGenerateDto> page, TmpDemoGenerateDto tmpDemoGenerateDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        TmpDemoGenerateEntity tmpDemoGenerateEntity = ConvertUtil.transformObj(tmpDemoGenerateDto,TmpDemoGenerateEntity.class);
        List<TmpDemoGenerateEntity> tmpDemoGenerateList=baseMapper.extendsList(pageMap,tmpDemoGenerateEntity);
        Long total = baseMapper.extendsCount(tmpDemoGenerateEntity);
        Page<TmpDemoGenerateDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(tmpDemoGenerateList,TmpDemoGenerateDto.class));
        return pageDto;
    }
    @Override
    public TmpDemoGenerateDto getByIdExtends(Long id){
        TmpDemoGenerateEntity  tmpDemoGenerateEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(tmpDemoGenerateEntity,TmpDemoGenerateDto.class);
    }

    @Override
    public List<TmpDemoGenerateDto> listTmpDemoGenerate(TmpDemoGenerateDto tmpDemoGenerateDto){
        QueryWrapper<TmpDemoGenerateEntity> wrapper = getWrapper(tmpDemoGenerateDto);
        List<TmpDemoGenerateEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,TmpDemoGenerateDto.class);
    }
}

