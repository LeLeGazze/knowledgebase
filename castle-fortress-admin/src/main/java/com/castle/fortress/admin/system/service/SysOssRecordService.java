package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysOssRecordDto;
import com.castle.fortress.admin.system.entity.SysOssRecordEntity;

import java.util.List;

/**
 * oss上传记录 服务类
 *
 * @author castle
 * @since 2022-03-01
 */
public interface SysOssRecordService extends IService<SysOssRecordEntity> {

    /**
     * 分页展示oss上传记录列表
     * @param page
     * @param sysOssRecordDto
     * @return
     */
    IPage<SysOssRecordDto> pageSysOssRecord(Page<SysOssRecordDto> page, SysOssRecordDto sysOssRecordDto);


    /**
     * 展示oss上传记录列表
     * @param sysOssRecordDto
     * @return
     */
    List<SysOssRecordDto> listSysOssRecord(SysOssRecordDto sysOssRecordDto);

    /**
     * 异步保存记录信息
     * @param sysOssRecordDto
     */
    void saveAsyn(SysOssRecordDto sysOssRecordDto);

}
