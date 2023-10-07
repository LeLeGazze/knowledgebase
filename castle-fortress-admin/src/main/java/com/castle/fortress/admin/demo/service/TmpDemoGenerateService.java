package com.castle.fortress.admin.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.demo.dto.TmpDemoGenerateDto;
import com.castle.fortress.admin.demo.entity.TmpDemoGenerateEntity;

import java.util.List;

/**
 * 代码生成示例表 服务类
 *
 * @author castle
 * @since 2021-11-08
 */
public interface TmpDemoGenerateService extends IService<TmpDemoGenerateEntity> {

    /**
     * 分页展示代码生成示例表列表
     * @param page
     * @param tmpDemoGenerateDto
     * @return
     */
    IPage<TmpDemoGenerateDto> pageTmpDemoGenerate(Page<TmpDemoGenerateDto> page, TmpDemoGenerateDto tmpDemoGenerateDto);

    /**
    * 分页展示代码生成示例表扩展列表
    * @param page
    * @param tmpDemoGenerateDto
    * @return
    */
    IPage<TmpDemoGenerateDto> pageTmpDemoGenerateExtends(Page<TmpDemoGenerateDto> page, TmpDemoGenerateDto tmpDemoGenerateDto);
    /**
    * 代码生成示例表扩展详情
    * @param id 代码生成示例表id
    * @return
    */
    TmpDemoGenerateDto getByIdExtends(Long id);

    /**
     * 展示代码生成示例表列表
     * @param tmpDemoGenerateDto
     * @return
     */
    List<TmpDemoGenerateDto> listTmpDemoGenerate(TmpDemoGenerateDto tmpDemoGenerateDto);

}
