package com.castle.fortress.admin.message.mail.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.message.mail.dto.CastleConfigMailDto;
import com.castle.fortress.admin.message.mail.entity.CastleConfigMailEntity;
import com.castle.fortress.common.entity.RespBody;

import java.util.List;

/**
 * 邮件配置表 服务类
 *
 * @author Mgg
 * @since 2021-10-27
 */
public interface CastleConfigMailService extends IService<CastleConfigMailEntity> {

    /**
     * 分页展示邮件配置表列表
     * @param page
     * @param castleConfigMailDto
     * @return
     */
    IPage<CastleConfigMailDto> pageCastleConfigMail(Page<CastleConfigMailDto> page, CastleConfigMailDto castleConfigMailDto);

    /**
    * 分页展示邮件配置表扩展列表
    * @param page
    * @param castleConfigMailDto
    * @return
    */
    IPage<CastleConfigMailDto> pageCastleConfigMailExtends(Page<CastleConfigMailDto> page, CastleConfigMailDto castleConfigMailDto);
    /**
    * 邮件配置表扩展详情
    * @param id 邮件配置表id
    * @return
    */
    CastleConfigMailDto getByIdExtends(Long id);

    /**
     * 展示邮件配置表列表
     * @param castleConfigMailDto
     * @return
     */
    List<CastleConfigMailDto> listCastleConfigMail(CastleConfigMailDto castleConfigMailDto);
    /**
     * 检查字段是否重复
     * @param dto
     * @return
     */
    RespBody checkColumnRepeat(CastleConfigMailDto dto);

}
