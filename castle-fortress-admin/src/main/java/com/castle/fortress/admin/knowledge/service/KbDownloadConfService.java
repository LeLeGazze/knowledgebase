package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbDownloadConfEntity;
import com.castle.fortress.admin.knowledge.dto.KbDownloadConfDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 文件下载配置表 服务类
 *
 * @author 
 * @since 2023-06-25
 */
public interface KbDownloadConfService extends IService<KbDownloadConfEntity> {

    /**
     * 分页展示文件下载配置表列表
     * @param page
     * @param kbDownloadConfDto
     * @return
     */
    IPage<KbDownloadConfDto> pageKbDownloadConf(Page<KbDownloadConfDto> page, KbDownloadConfDto kbDownloadConfDto);

    /**
    * 分页展示文件下载配置表扩展列表
    * @param page
    * @param kbDownloadConfDto
    * @return
    */
    IPage<KbDownloadConfDto> pageKbDownloadConfExtends(Page<KbDownloadConfDto> page, KbDownloadConfDto kbDownloadConfDto);
    /**
    * 文件下载配置表扩展详情
    * @param id 文件下载配置表id
    * @return
    */
    KbDownloadConfDto getByIdExtends(Long id);

    /**
     * 展示文件下载配置表列表
     * @param kbDownloadConfDto
     * @return
     */
    List<KbDownloadConfDto> listKbDownloadConf(KbDownloadConfDto kbDownloadConfDto);

    boolean isWatermark();

    boolean isSourceFile();
}
