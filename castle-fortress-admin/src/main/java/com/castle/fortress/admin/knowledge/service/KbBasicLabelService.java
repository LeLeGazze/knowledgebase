package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicLabelDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;

import java.util.Map;
import java.util.List;

/**
 * 知识与标签的中间表 服务类
 *
 * @author 
 * @since 2023-04-28
 */
public interface KbBasicLabelService extends IService<KbBasicLabelEntity> {

    /**
     * 分页展示知识与标签的中间表列表
     * @param page
     * @param kbBasicLabelDto
     * @return
     */
    IPage<KbBasicLabelDto> pageKbBasicLabel(Page<KbBasicLabelDto> page, KbBasicLabelDto kbBasicLabelDto);

    /**
    * 分页展示知识与标签的中间表扩展列表
    * @param page
    * @param kbBasicLabelDto
    * @return
    */
    IPage<KbBasicLabelDto> pageKbBasicLabelExtends(Page<KbBasicLabelDto> page, KbBasicLabelDto kbBasicLabelDto);
    /**
    * 知识与标签的中间表扩展详情
    * @param id 知识与标签的中间表id
    * @return
    */
    KbBasicLabelDto getByIdExtends(Long id);

    /**
     * 展示知识与标签的中间表列表
     * @param kbBasicLabelDto
     * @return
     */
    List<KbBasicLabelDto> listKbBasicLabel(KbBasicLabelDto kbBasicLabelDto);

    void saveById(KbModelAcceptanceDto formDataDto);

    /**
     * 查询当前标签下的所有知识id
     * @param id
     * @return
     */
    List<Long> listById(Long id);

    List<String> findByUidAuthLabelAdmin(Long uid);

    List<String> findByUidAuthLabel(Long uid, List<Integer> integers);

    Integer labelCount(Long lebalId);

    List<KbModelLabelEntity> listBybId(Long bId);


//    Kb findById();
}
