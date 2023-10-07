package com.castle.fortress.admin.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.check.entity.KbDuplicateCheckEntity;
import com.castle.fortress.admin.check.dto.KbDuplicateCheckDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.List;

/**
 * 知识库查重表 服务类
 *
 * @author 
 * @since 2023-07-15
 */
public interface KbDuplicateCheckService extends IService<KbDuplicateCheckEntity> {

    /**
     * 分页展示知识库查重表列表
     * @param page
     * @param kbDuplicateCheckDto
     * @return
     */
    IPage<KbDuplicateCheckDto> pageKbDuplicateCheck(Page<KbDuplicateCheckDto> page, KbDuplicateCheckDto kbDuplicateCheckDto);


    /**
     * 展示知识库查重表列表
     * @param kbDuplicateCheckDto
     * @return
     */
    List<KbDuplicateCheckDto> listKbDuplicateCheck(KbDuplicateCheckDto kbDuplicateCheckDto);

    void fileShow(HttpServletResponse response,String id);

    void downloadFile(HttpServletResponse response, String id,String userName);
}
