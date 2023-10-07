package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbThumbsUpEntity;
import com.castle.fortress.admin.knowledge.dto.KbThumbsUpDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 知识点赞表 服务类
 *
 * @author 
 * @since 2023-05-11
 */
public interface KbThumbsUpService extends IService<KbThumbsUpEntity> {

    /**
     * 分页展示知识点赞表列表
     * @param page
     * @param kbThumbsUpDto
     * @return
     */
    IPage<KbThumbsUpDto> pageKbThumbsUp(Page<KbThumbsUpDto> page, KbThumbsUpDto kbThumbsUpDto);


    /**
     * 展示知识点赞表列表
     * @param kbThumbsUpDto
     * @return
     */
    List<KbThumbsUpDto> listKbThumbsUp(KbThumbsUpDto kbThumbsUpDto);

    KbThumbsUpEntity checkExites(KbThumbsUpDto kbThumbsUpDto);

    boolean removeUp(KbThumbsUpDto kbThumbsUpDto);
}
