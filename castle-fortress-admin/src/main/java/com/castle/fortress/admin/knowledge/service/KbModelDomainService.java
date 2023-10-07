package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbModelDomainEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelDomainDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 值域字典表 服务类
 *
 * @author sunhr
 * @since 2023-04-20
 */
public interface KbModelDomainService extends IService<KbModelDomainEntity> {

    /**
     * 分页展示值域字典表列表
     * @param page
     * @param kbModelDomainDto
     * @return
     */
    IPage<KbModelDomainDto> pageKbModelDomain(Page<KbModelDomainDto> page, KbModelDomainDto kbModelDomainDto);

    /**
    * 分页展示值域字典表扩展列表
    * @param page
    * @param kbModelDomainDto
    * @return
    */
    IPage<KbModelDomainDto> pageKbModelDomainExtends(Page<KbModelDomainDto> page, KbModelDomainDto kbModelDomainDto);
    /**
    * 值域字典表扩展详情
    * @param id 值域字典表id
    * @return
    */
    KbModelDomainDto getByIdExtends(Long id);

    /**
     * 展示值域字典表列表
     * @param kbModelDomainDto
     * @return
     */
    List<KbModelDomainDto> listKbModelDomain(KbModelDomainDto kbModelDomainDto);

    /**
     * 查询值域字典列表
     *
     * @return
     */
    List<KbModelDomainEntity> selectAll();
}
