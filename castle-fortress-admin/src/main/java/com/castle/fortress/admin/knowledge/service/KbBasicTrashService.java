package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicTrashEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicTrashDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 知识回收表 服务类
 *
 * @author 
 * @since 2023-06-01
 */
public interface KbBasicTrashService extends IService<KbBasicTrashEntity> {

    /**
     * 分页展示知识回收表列表
     * @param page
     * @param kbBaseShowDto
     * @return
     */
    IPage<KbModelTransmitDto> pageKbBasicTrashAdmin(Page<KbBasicTrashDto> page, KbBaseShowDto kbBaseShowDto);


    /**
     * 展示知识回收表列表
     * @param kbBasicTrashDto
     * @return
     */
    List<KbBasicTrashDto> listKbBasicTrash(KbBasicTrashDto kbBasicTrashDto);

    /**
     * 普通用户知识回收列表
     * @param page
     * @param kbBaseShowDto
     * @param uid
     * @return
     */
    IPage<KbModelTransmitDto> pageKbBasicTrash(Page<KbBasicTrashDto> page, KbBaseShowDto kbBaseShowDto, Long uid);
}
