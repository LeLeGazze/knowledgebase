package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbLabelCategoryDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * 标签分类表 服务类
 *
 * @author 
 * @since 2023-06-14
 */
public interface KbLabelCategoryService extends IService<KbLabelCategoryEntity> {

    /**
     * 分页展示标签分类表列表
     * @param page
     * @param kbLabelCategoryDto
     * @return
     */
    IPage<KbLabelCategoryDto> pageKbLabelCategory(Page<KbLabelCategoryDto> page, KbLabelCategoryDto kbLabelCategoryDto);


    /**
     * 展示标签分类表列表
     * @param kbLabelCategoryDto
     * @return
     */
    List<KbLabelCategoryDto> listKbLabelCategory(KbLabelCategoryDto kbLabelCategoryDto);

    List<KbModelLabelEntity> findLabelByCtId(Long id);

    List<KbModelLabelDto> findLabelByCtIds(List<Long> ids);

    List<KbLabelCategoryEntity> findByIds(List<Long> ids);

    List<KbLabelCategoryEntity> isExiteName(List<String> list);
}
