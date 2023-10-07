package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.common.entity.RespBody;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

/**
 * 标签管理表 服务类
 *
 * @author 
 * @since 2023-04-26
 */
public interface KbModelLabelService extends IService<KbModelLabelEntity> {

    /**
     * 分页展示标签管理表列表
     * @param page
     * @param kbModelLabelDto
     * @return
     */
    IPage<KbModelLabelDto> pageKbModelLabel(Page<KbModelLabelDto> page, KbModelLabelDto kbModelLabelDto);


    /**
     * 展示标签管理表列表
     * @param kbModelLabelDto
     * @return
     */
    List<KbModelLabelDto> listKbModelLabel(KbModelLabelDto kbModelLabelDto);

    /**
     * 根据name查询标签
     * @param labelName
     * @return
     */
    List<KbModelLabelEntity> findIsExistName(List<String> labelName);

    /**
     * 热词列表
     * @return
     */
    List<KbModelLabelDto> listHotWord();

    /**
     * 置为失效多个
     * @param kbModelLabelEntitys
     * @return
     */
    boolean updateBatch(List<KbModelLabelEntity> kbModelLabelEntitys);

    /**
     * 失效生效按钮
     * @param kbModelLabelEntity
     * @return
     */
    boolean updateLabel(KbModelLabelEntity kbModelLabelEntity);

    void saveLabel(KbModelAcceptanceDto formDataDto);

    boolean removeLabels(List<Long> ids);

    /**
     * 标签更新
     * @param id
     * @return
     */
    boolean updateByLabel(Long id);

    /**
     * 标签保存
     * @param kbModelLabelDtos
     * @return
     */
    RespBody<String> saveLabels(List<KbModelLabelDto> kbModelLabelDtos);

}
