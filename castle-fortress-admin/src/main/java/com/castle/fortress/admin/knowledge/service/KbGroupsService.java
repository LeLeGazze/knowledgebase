package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbGroupsEntity;
import com.castle.fortress.admin.knowledge.dto.KbGroupsDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 知识库用户组管理 服务类
 *
 * @author 
 * @since 2023-04-22
 */
public interface KbGroupsService extends IService<KbGroupsEntity> {

    /**
     * 分页展示知识库用户组管理列表
     * @param page
     * @param kbGroupsDto
     * @return
     */
    IPage<KbGroupsDto> pageKbGroups(Page<KbGroupsDto> page, KbGroupsDto kbGroupsDto);

    /**
    * 分页展示知识库用户组管理扩展列表
    * @param page
    * @param kbGroupsDto
    * @return
    */
    IPage<KbGroupsDto> pageKbGroupsExtends(Page<KbGroupsDto> page, KbGroupsDto kbGroupsDto);
    /**
    * 知识库用户组管理扩展详情
    * @param id 知识库用户组管理id
    * @return
    */
    KbGroupsDto getByIdExtends(Long id);

    /**
     * 展示知识库用户组管理列表
     * @param kbGroupsDto
     * @return
     */
    List<KbGroupsDto> listKbGroups(KbGroupsDto kbGroupsDto);

    List<KbGroupsDto> findpId(Long id);

    KbGroupsEntity findById(Long id);

    IPage<KbGroupsDto> findpIdPage(Long pId,Page<KbGroupsDto> page);
}
