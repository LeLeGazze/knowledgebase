package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbBannerEntity;
import com.castle.fortress.admin.knowledge.dto.KbBannerDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 知识banner图表 服务类
 *
 * @author 
 * @since 2023-06-17
 */
public interface KbBannerService extends IService<KbBannerEntity> {

    /**
     * 分页展示知识banner图表列表
     * @param page
     * @param kbBannerDto
     * @return
     */
    IPage<KbBannerDto> pageKbBanner(Page<KbBannerDto> page, KbBannerDto kbBannerDto);


    /**
     * 展示知识banner图表列表
     * @param kbBannerDto
     * @return
     */
    List<KbBannerDto> listKbBanner(KbBannerDto kbBannerDto);

    List<KbBannerEntity> findByStatus(Integer status);
}
