package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.TmpDemoDto;
import com.castle.fortress.admin.system.entity.TmpDemoEntity;
import com.castle.fortress.admin.system.mapper.TmpDemoMapper;
import com.castle.fortress.admin.system.service.TmpDemoService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 组件示例表 服务实现类
 *
 * @author castle
 * @since 2021-06-02
 */
@Service
public class TmpDemoServiceImpl extends ServiceImpl<TmpDemoMapper, TmpDemoEntity> implements TmpDemoService {
    /**
    * 获取查询条件
    * @param tmpDemoDto
    * @return
    */
    public QueryWrapper<TmpDemoEntity> getWrapper(TmpDemoDto tmpDemoDto){
        QueryWrapper<TmpDemoEntity> wrapper= new QueryWrapper();
        if(tmpDemoDto != null){
            TmpDemoEntity tmpDemoEntity = ConvertUtil.transformObj(tmpDemoDto,TmpDemoEntity.class);
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getName()),"name",tmpDemoEntity.getName());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getContent()),"content",tmpDemoEntity.getContent());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getAuth()),"auth",tmpDemoEntity.getAuth());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getPhone()),"phone",tmpDemoEntity.getPhone());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getEmail()),"email",tmpDemoEntity.getEmail());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getImages()),"images",tmpDemoEntity.getImages());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getFiles()),"files",tmpDemoEntity.getFiles());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getVideo()),"video",tmpDemoEntity.getVideo());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getLongitude()),"longitude",tmpDemoEntity.getLongitude());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getLatitude()),"latitude",tmpDemoEntity.getLatitude());
            wrapper.like(tmpDemoEntity.getVueNumber() != null,"vue_number",tmpDemoEntity.getVueNumber());
            wrapper.like(tmpDemoEntity.getVueRadio() != null,"vue_radio",tmpDemoEntity.getVueRadio());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getVueTextarea()),"vue_textarea",tmpDemoEntity.getVueTextarea());
            wrapper.like(StrUtil.isNotEmpty(tmpDemoEntity.getVueCheckbox()),"vue_checkbox",tmpDemoEntity.getVueCheckbox());
            wrapper.like(tmpDemoEntity.getVueSelect() != null,"vue_select",tmpDemoEntity.getVueSelect());
            wrapper.like(tmpDemoEntity.getVueDate() != null,"vue_date",tmpDemoEntity.getVueDate());
            wrapper.like(tmpDemoEntity.getVueDatetime() != null,"vue_datetime",tmpDemoEntity.getVueDatetime());
            wrapper.like(tmpDemoEntity.getStatus() != null,"status",tmpDemoEntity.getStatus());
            wrapper.like(tmpDemoEntity.getCreateDept() != null,"create_dept",tmpDemoEntity.getCreateDept());
            wrapper.like(tmpDemoEntity.getCreatePost() != null,"create_post",tmpDemoEntity.getCreatePost());
            wrapper.like(tmpDemoEntity.getCreateTime() != null,"create_time",tmpDemoEntity.getCreateTime());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<TmpDemoDto> pageTmpDemo(Page<TmpDemoDto> page, TmpDemoDto tmpDemoDto) {
        QueryWrapper<TmpDemoEntity> wrapper = getWrapper(tmpDemoDto);
        Page<TmpDemoEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<TmpDemoEntity> tmpDemoPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<TmpDemoDto> pageDto = new Page(tmpDemoPage.getCurrent(), tmpDemoPage.getSize(),tmpDemoPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(tmpDemoPage.getRecords(),TmpDemoDto.class));
        return pageDto;
    }

    @Override
    public IPage<TmpDemoDto> pageTmpDemoExtends(Page<TmpDemoDto> page, TmpDemoDto tmpDemoDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        TmpDemoEntity tmpDemoEntity = ConvertUtil.transformObj(tmpDemoDto,TmpDemoEntity.class);
        List<TmpDemoEntity> tmpDemoList=baseMapper.extendsList(pageMap,tmpDemoEntity);
        Long total = baseMapper.extendsCount(tmpDemoEntity);
        Page<TmpDemoDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(tmpDemoList,TmpDemoDto.class));
        return pageDto;
    }
    @Override
    public TmpDemoDto getByIdExtends(Long id){
        TmpDemoEntity  tmpDemoEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(tmpDemoEntity,TmpDemoDto.class);
    }

    @Override
    public List<TmpDemoDto> listTmpDemo(TmpDemoDto tmpDemoDto){
        QueryWrapper<TmpDemoEntity> wrapper = getWrapper(tmpDemoDto);
        List<TmpDemoEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,TmpDemoDto.class);
    }

    @Override
    public List<Map> infoTmpDemo(String tb1, String tb2, String col1, String col2, String col3) {
        return baseMapper.infoTmpDemo(tb1,tb2,col1,col2,col3);
    }
}

