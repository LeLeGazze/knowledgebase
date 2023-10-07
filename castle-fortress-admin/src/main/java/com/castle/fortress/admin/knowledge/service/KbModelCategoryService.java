package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbModelCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelCategoryDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 模型分类管理 服务类
 *
 * @author Pan Chen
 * @since 2023-04-10
 */
public interface KbModelCategoryService extends IService<KbModelCategoryEntity> {

    /**
     * 分页展示模型分类管理列表
     * @param page
     * @param kbModelCategoryDto
     * @return
     */
    IPage<KbModelCategoryDto> pageKbModelCategory(Page<KbModelCategoryDto> page, KbModelCategoryDto kbModelCategoryDto);

    /**
    * 分页展示模型分类管理扩展列表
    * @param page
    * @param kbModelCategoryDto
    * @return
    */
    IPage<KbModelCategoryDto> pageKbModelCategoryExtends(Page<KbModelCategoryDto> page, KbModelCategoryDto kbModelCategoryDto);
    /**
    * 模型分类管理扩展详情
    * @param id 模型分类管理id
    * @return
    */
    KbModelCategoryDto getByIdExtends(Long id);

    /**
     * 展示模型分类管理列表
     * @param kbModelCategoryDto
     * @return
     */
    List<KbModelCategoryDto> listKbModelCategory(KbModelCategoryDto kbModelCategoryDto);

    List<Map<String,Object>> findBySwId(String swId);
}
