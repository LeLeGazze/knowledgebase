package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicLogEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicLogDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.system.entity.SysUser;

import java.text.ParseException;
import java.util.List;

/**
 * 知识移动日志表 服务类
 *
 * @author 
 * @since 2023-06-02
 */
public interface KbBasicLogService extends IService<KbBasicLogEntity> {

    /**
     * 分页展示知识移动日志表列表
     * @param page
     * @param kbBaseShowDto
     * @return
     */
    IPage<KbModelTransmitDto> pageKbBasicLog(Page<KbBasicLogDto> page, KbBaseShowDto kbBaseShowDto, SysUser sysUser) throws ParseException;


    /**
     * 展示知识移动日志表列表
     * @param kbBasicLogDto
     * @return
     */
    List<KbBasicLogDto> listKbBasicLog(KbBasicLogDto kbBasicLogDto);

}
