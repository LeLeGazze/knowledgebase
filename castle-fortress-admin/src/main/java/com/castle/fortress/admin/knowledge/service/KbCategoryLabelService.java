package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbCategoryLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbCategoryLabelDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.common.entity.RespBody;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * 标签分组和标签关联表 服务类
 *
 * @author 
 * @since 2023-06-14
 */
public interface KbCategoryLabelService extends IService<KbCategoryLabelEntity> {

    /**
     * 分页展示标签分组和标签关联表列表
     * @param page
     * @param kbCategoryLabelDto
     * @return
     */
    IPage<KbCategoryLabelDto> pageKbCategoryLabel(Page<KbCategoryLabelDto> page, KbCategoryLabelDto kbCategoryLabelDto);


    /**
     * 展示标签分组和标签关联表列表
     * @param kbCategoryLabelDto
     * @return
     */
    List<KbCategoryLabelDto> listKbCategoryLabel(KbCategoryLabelDto kbCategoryLabelDto);

    IPage<KbModelLabelDto> findLabelByCategory(Long ctId, Page<KbModelLabelDto> page, KbModelLabelDto kbCategoryLabelDto);

    RespBody<String> findAllLabelByCategory(Long ctId);

    RespBody<String> editHotWordLabelByCategory(Long ctId);

    KbLabelCategoryEntity findCategoryByLabel(Long id);

    List<KbLabelCategoryEntity> findCategoryByLabelIds(List<Long> ids);

    List<KbCategoryLabelEntity> isExistLabel(ArrayList<Long> longs);

    Integer removeByLabelId(Long id);

    void deleteBylId(ArrayList<KbCategoryLabelEntity> kbCategoryLabelEntities);
}
