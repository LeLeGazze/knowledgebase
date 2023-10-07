package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicHistoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicHistoryDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;

import java.util.Map;
import java.util.List;

/**
 * 知识基本表历史表 服务类
 *
 * @author 
 * @since 2023-07-03
 */
public interface KbBasicHistoryService extends IService<KbBasicHistoryEntity> {

    /**
     * 分页展示知识基本表历史表列表
     * @param page
     * @param kbBasicHistoryDto
     * @return
     */
    IPage<KbBasicHistoryDto> pageKbBasicHistory(Page<KbBasicHistoryDto> page, KbBasicHistoryDto kbBasicHistoryDto);

    /**
    * 分页展示知识基本表历史表扩展列表
    * @param page
    * @param kbBasicHistoryDto
    * @return
    */
    IPage<KbBasicHistoryDto> pageKbBasicHistoryExtends(Page<KbBasicHistoryDto> page, KbBasicHistoryDto kbBasicHistoryDto);
    /**
    * 知识基本表历史表扩展详情
    * @param id 知识基本表历史表id
    * @return
    */
    KbBasicHistoryDto getByIdExtends(Long id);

    /**
     * 展示知识基本表历史表列表
     * @param kbBasicHistoryDto
     * @return
     */
    List<KbBasicHistoryDto> listKbBasicHistory(KbBasicHistoryDto kbBasicHistoryDto);

    void addARecord(Long userId,KbModelEntity model, KbBasicEntity kbBasic, Map<String, Object> extendDataMap, KbModelAcceptanceDto formDataDto);

    KbModelTransmitDto findAllByBasic(Long id);

    int deleteByBId(Long id);
}
