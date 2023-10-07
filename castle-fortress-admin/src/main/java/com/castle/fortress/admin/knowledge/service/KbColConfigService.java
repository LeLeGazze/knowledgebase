package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.dto.KbPropertyDesignDto;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
import com.castle.fortress.common.entity.RespBody;

import java.util.List;

/**
 * kb模型字段配置表 服务类
 *
 * @author sunhr
 * @since 2022-03-03
 */
public interface KbColConfigService extends IService<KbPropertyDesignEntity> {

    /**
     * 分页展示kb模型字段配置表列表
     * @param page
     * @param kbPropertyDesignDto
     * @return
     */
    IPage<KbPropertyDesignDto> pageKbColConfig(Page<KbPropertyDesignDto> page, KbPropertyDesignDto kbPropertyDesignDto);

    /**
     * 展示kb模型字段配置表列表
     * @param kbPropertyDesignDto
     * @return
     */
    List<KbPropertyDesignDto> listKbColConfig(KbPropertyDesignDto kbPropertyDesignDto);

    /**
     * 填充字段类型
     * @param modelId
     * @param list
     * @return
     */
    List<KbPropertyDesignEntity> fillType(Long modelId, List<KbPropertyDesignEntity> list);

    /**
     * 保存字段信息
     * @param kbModelEntity
     * @param entities
     */
    RespBody saveCols(KbModelEntity kbModelEntity, List<KbPropertyDesignEntity> entities);

    /**
     * 修改字段信息
     * @param kbModelEntity
     * @param entities
     */
    void updateCols(KbModelEntity kbModelEntity,List<KbPropertyDesignEntity> entities);


    /**
     * 获取所有的表名
     * @return
     */
    List<String> allTableNames();

    /**
     * 删除模型对应的字段
     * @param e
     */
    void delCols(KbModelEntity e);

    /**
     * 检查字段是否重复
     * @param dto
     * @return
     */
    RespBody checkColumnRepeat(KbPropertyDesignDto dto);

    List<KbPropertyDesignDto> listCmsColConfig(KbPropertyDesignDto kbPropertyDesignDto);

    /**
     * 查询当前知识的所有信息
     * @param id
     * @return KbModelTransmitDto
     */
    KbModelTransmitDto findColDate(KbModelTransmitDto KbModelTransmitDto,Long id);

    void findHistoryColDate(KbModelTransmitDto allByBasic, Long id);
}
