package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.entity.KbUserLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbUserLabelDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * 用户与表签关联表 服务类
 *
 * @author 
 * @since 2023-05-17
 */
public interface KbUserLabelService extends IService<KbUserLabelEntity> {

    /**
     * 分页展示用户与表签关联表列表
     * @param page
     * @param kbUserLabelDto
     * @return
     */
    IPage<KbUserLabelDto> pageKbUserLabel(Page<KbUserLabelDto> page, KbUserLabelDto kbUserLabelDto);


    /**
     * 展示用户与表签关联表列表
     * @param kbUserLabelDto
     * @return
     */
    List<KbUserLabelDto> listKbUserLabel(KbUserLabelDto kbUserLabelDto);

    IPage<KbModelTransmitDto> findBasicByLabel(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page);

    List<KbModelLabelEntity> findLabelByUser(Long userId);

    List<Long> findIds(Long userId);

    IPage<KbModelTransmitDto> findBasicByLabelAdmin(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page);

    Integer findLabelByUserToBasicCountAdmin(Long id);

    Integer findLabelByUserToBasicCount(Long id,  List<Integer> integers );
}
