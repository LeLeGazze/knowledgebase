package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbCollectEntity;
import com.castle.fortress.admin.knowledge.dto.KbCollectDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 知识收藏表 服务类
 *
 * @author 
 * @since 2023-05-12
 */
public interface KbCollectService extends IService<KbCollectEntity> {

    /**
     * 分页展示知识收藏表列表
     * @param page
     * @param kbCollectDto
     * @return
     */
    IPage<KbCollectDto> pageKbCollect(Page<KbCollectDto> page, KbCollectDto kbCollectDto);


    /**
     * 展示知识收藏表列表
     * @param kbCollectDto
     * @return
     */
    List<KbCollectDto> listKbCollect(KbCollectDto kbCollectDto);

    KbCollectEntity checkExites(KbCollectDto kbCollectDto);

    /**
     * 我的知识查询
     * @param kbBaseShowDto
     * @param page
     * @return
     */
    IPage<KbModelTransmitDto> findBasicByLike(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page);

    /**
     * 根据userId和知识Id查询收藏信息
     * @param userId
     * @param basicId
     * @return
     */
    KbCollectEntity findByid(Long userId, Long basicId);

    boolean removeCollect(Long userId, Long basicId);

    int deleteByBid(Long id);

    IPage<KbModelTransmitDto> findBasicByLikeAdmin(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page);
}
