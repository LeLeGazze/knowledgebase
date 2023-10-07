package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicUserEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicUserDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 知识浏览收藏评论 服务类
 *
 * @author 
 * @since 2023-05-05
 */
public interface KbBasicUserService extends IService<KbBasicUserEntity> {

    /**
     * 分页展示知识浏览收藏评论列表
     * @param page
     * @param kbBasicUserDto
     * @return
     */
    IPage<KbBasicUserDto> pageKbBasicUser(Page<KbBasicUserDto> page, KbBasicUserDto kbBasicUserDto);


    /**
     * 展示知识浏览收藏评论列表
     * @param kbBasicUserDto
     * @return
     */
    List<KbBasicUserDto> listKbBasicUser(KbBasicUserDto kbBasicUserDto);

    /**
     * 保存知识与用户关联表信息
     * @param KbModelTransmitDto
     */
//    boolean save(KbBasicUserEntity kbBasicUserEntity);

    /**
     * 保存上传文件知识与用户上传关联表信息
     * @param formData
     */
    void saveKnowUser(KbModelAcceptanceDto formData);


    /**
     * 最近浏览
     * @param userId
     * @return
     */
    IPage<KbModelTransmitDto> recentViews(Page<KbModelTransmitDto> page,Long userId);

    /**
     * 最近上传
     * @param userId
     * @return
     */
    IPage<KbModelTransmitDto> recentlyUploaded(Page<KbModelTransmitDto> page,Long userId);

    /**
     * 最近下载
     * @param userId
     * @return
     */
    IPage<KbModelTransmitDto> recentlyDownloaded(Page<KbModelTransmitDto> page,Long userId);

    void insertDownLoad(Long basicId);

    List<KbModelTransmitDto> randomId();

    KbModelTransmitDto selectDownCount(Long userId);

    KbModelTransmitDto selectUpCount();

    void removeBasicUser(Long auth, Long id);
}
