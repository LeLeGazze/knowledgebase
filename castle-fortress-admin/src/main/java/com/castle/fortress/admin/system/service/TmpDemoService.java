package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.TmpDemoDto;
import com.castle.fortress.admin.system.entity.TmpDemoEntity;

import java.util.List;
import java.util.Map;

/**
 * 组件示例表 服务类
 *
 * @author castle
 * @since 2021-06-02
 */
public interface TmpDemoService extends IService<TmpDemoEntity> {

    /**
     * 分页展示组件示例表列表
     * @param page
     * @param tmpDemoDto
     * @return
     */
    IPage<TmpDemoDto> pageTmpDemo(Page<TmpDemoDto> page, TmpDemoDto tmpDemoDto);

    /**
    * 分页展示组件示例表扩展列表
    * @param page
    * @param tmpDemoDto
    * @return
    */
    IPage<TmpDemoDto> pageTmpDemoExtends(Page<TmpDemoDto> page, TmpDemoDto tmpDemoDto);
    /**
    * 组件示例表扩展详情
    * @param id 组件示例表id
    * @return
    */
    TmpDemoDto getByIdExtends(Long id);

    /**
     * 展示组件示例表列表
     * @param tmpDemoDto
     * @return
     */
    List<TmpDemoDto> listTmpDemo(TmpDemoDto tmpDemoDto);

    List<Map> infoTmpDemo(String tb1, String tb2, String col1, String col2, String col3);
}
