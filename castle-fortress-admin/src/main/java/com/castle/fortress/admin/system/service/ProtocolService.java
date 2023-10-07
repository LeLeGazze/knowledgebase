package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.ProtocolDto;
import com.castle.fortress.admin.system.entity.ProtocolEntity;

import java.util.List;

/**
 * 协议管理 服务类
 *
 * @author majunjie
 * @since 2022-01-28
 */
public interface ProtocolService extends IService<ProtocolEntity> {

    /**
     * 分页展示协议管理列表
     * @param page
     * @param protocolDto
     * @return
     */
    IPage<ProtocolDto> pageProtocol(Page<ProtocolDto> page, ProtocolDto protocolDto);

    /**
    * 分页展示协议管理扩展列表
    * @param page
    * @param protocolDto
    * @return
    */
    IPage<ProtocolDto> pageProtocolExtends(Page<ProtocolDto> page, ProtocolDto protocolDto);
    /**
    * 协议管理扩展详情
    * @param id 协议管理id
    * @return
    */
    ProtocolDto getByIdExtends(Long id);

    /**
     * 展示协议管理列表
     * @param protocolDto
     * @return
     */
    List<ProtocolDto> listProtocol(ProtocolDto protocolDto);

    /**
     * 根据编码获取协议
     * @param code
     * @return
     */
    ProtocolDto getByCode(String code);
}
