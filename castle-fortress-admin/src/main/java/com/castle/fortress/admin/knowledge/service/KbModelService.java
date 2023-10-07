package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelDto;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.common.entity.RespBody;

import java.util.List;

/**
 * kb模型配置表 服务类
 *
 * @author sunhr
 * @since 2023-04-20
 */
public interface KbModelService extends IService<KbModelEntity> {

    /**
     * 分页展示cms模型配置表列表
     * @param page
     * @param cmsModelDto
     * @return
     */
    IPage<KbModelDto> pageCmsModel(Page<KbModelDto> page, KbModelDto cmsModelDto);

    /**
    * 分页展示cms模型配置表扩展列表
    * @param page
    * @param kbModelDto
    * @return
    */
    IPage<KbModelDto> pageCmsModelExtends(Page<KbModelDto> page, KbModelDto kbModelDto);
    /**
    * cms模型配置表扩展详情
    * @param id cms模型配置表id
    * @return
    */
    KbModelDto getByIdExtends(Long id);

    /**
     * 展示cms模型配置表列表
     * @param kbModelDto
     * @return
     */
    List<KbModelDto> listCmsModel(KbModelDto kbModelDto);

    /**
     * 删除表结构
     * @param tbName
     * @return
     */
    boolean dropTable(String tbName);

    /**
     * 检查字段是否重复
     * @param dto
     * @return
     */
    RespBody checkColumnRepeat(KbModelAcceptanceDto dto);

    /**
     * cms模型配置表扩展详情
     * @param id 文章id
     * @return
     */
    KbModelDto getByArticleId(Long id);

    /**
     * cms模型配置表扩展详情
     * @param code 模型编码
     * @return
     */
    KbModelDto getByCode(String code);

    List<Long> findswId(Long id);
}
