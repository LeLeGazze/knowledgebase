package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbVideoShowDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbVideoEntity;
import com.castle.fortress.admin.knowledge.dto.KbVideoDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 视频库 服务类
 *
 * @author 
 * @since 2023-05-13
 */
public interface KbVideoService extends IService<KbVideoEntity> {

    /**
     * 分页展示视频库列表
     * @param page
     * @param kbVideoDto
     * @return
     */
//    IPage<KbVideoDto> pageKbVideo(Page<KbVideoDto> page, KbVideoDto kbVideoDto);

    /**
    * 分页展示视频库扩展列表
    * @param page
    * @param kbVideoDto
    * @return
    */
    IPage<KbVideoDto> pageKbVideoExtends(Page<KbVideoDto> page, KbVideoDto kbVideoDto);
    /**
    * 视频库扩展详情
    * @param id 视频库id
    * @return
    */
    KbVideoDto getByIdExtends(Long id);

    /**
     * 展示视频库列表
     * @param kbVideoDto
     * @return
     */
    List<KbVideoDto> listKbVideo(KbVideoDto kbVideoDto);

    boolean saveAll(KbVideoDto kbVideoDto);

    IPage<KbVideoShowDto> pageKbVideoAdmin(Page<KbVideoDto> page, KbVideoShowDto kbVideoShowDto);

    IPage<KbVideoShowDto> pageKbVideo(Page<KbVideoDto> page, KbVideoShowDto kbVideoShowDto, Long uid, List<Integer> integers);

    KbVideoDto findByIdVideo(Long id);

    KbVideoEntity findByIdAuth(Long uid, List<Integer> integers, Long id);

    String findByVidToUrl(String vid);

    boolean editById(KbVideoDto kbVideoDto);

    int deleteByIdsAdmin(List<Long> ids);

    String deleteByIds(List<Long> kbWarehouseAuthEntityList, List<Long> ids);

    List<KbVideoDto> showListVideoAdmin(Long swId);

    List<KbVideoDto> showListVideo(Long swId, List<Integer> integers, Long uid);



    List<KbVideoDto> randVideoListAdmin();

    List<KbVideoDto> randVideoList(List<Integer> integers, Long uid);

    List<KbVideoEntity> selectByExpireVideo();
}
