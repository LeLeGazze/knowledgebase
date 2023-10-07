package com.castle.fortress.admin.message.mail.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.message.mail.dto.CastleMessageEmailRecordDto;
import com.castle.fortress.admin.message.mail.entity.CastleMessageEmailRecordEntity;

import java.util.List;

/**
 * 邮件发送记录表 服务类
 *
 * @author Mgg
 * @since 2021-10-27
 */
public interface CastleMessageEmailRecordService extends IService<CastleMessageEmailRecordEntity> {

    /**
     * 分页展示邮件发送记录表列表
     * @param page
     * @param castleMessageEmailRecordDto
     * @return
     */
    IPage<CastleMessageEmailRecordDto> pageCastleMessageEmailRecord(Page<CastleMessageEmailRecordDto> page, CastleMessageEmailRecordDto castleMessageEmailRecordDto);

    /**
    * 分页展示邮件发送记录表扩展列表
    * @param page
    * @param castleMessageEmailRecordDto
    * @return
    */
    IPage<CastleMessageEmailRecordDto> pageCastleMessageEmailRecordExtends(Page<CastleMessageEmailRecordDto> page, CastleMessageEmailRecordDto castleMessageEmailRecordDto);
    /**
    * 邮件发送记录表扩展详情
    * @param id 邮件发送记录表id
    * @return
    */
    CastleMessageEmailRecordDto getByIdExtends(Long id);

    /**
     * 展示邮件发送记录表列表
     * @param castleMessageEmailRecordDto
     * @return
     */
    List<CastleMessageEmailRecordDto> listCastleMessageEmailRecord(CastleMessageEmailRecordDto castleMessageEmailRecordDto);

}
