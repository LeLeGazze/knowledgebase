package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbCategoryShowDto;
import com.castle.fortress.admin.knowledge.entity.KbCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbCategoryDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.common.entity.RespBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * 知识分类表 服务类
 *
 * @author
 * @since 2023-04-24
 */
public interface KbCategoryService extends IService<KbCategoryEntity> {

    /**
     * 分页展示知识分类表列表
     *
     * @param page
     * @param kbCategoryDto
     * @return
     */
    IPage<KbCategoryDto> pageKbCategory(Page<KbCategoryDto> page, KbCategoryDto kbCategoryDto);

    /**
     * 分页展示知识分类表扩展列表
     *
     * @param page
     * @param kbCategoryDto
     * @return
     */
    IPage<KbCategoryDto> pageKbCategoryExtends(Page<KbCategoryDto> page, KbCategoryDto kbCategoryDto);

    /**
     * 知识分类表扩展详情
     *
     * @param id 知识分类表id
     * @return
     */
    KbCategoryDto getByIdExtends(Long id);

    /**
     * 展示知识分类表列表
     *
     * @param kbCategoryDto
     * @return
     */
    List<KbCategoryDto> listKbCategory(KbCategoryDto kbCategoryDto);

    boolean add(KbCategoryDto kbCategoryDto);

    boolean updateById(KbCategoryDto kbCategoryDto);


    List<KbCategoryDto> findByUidAndAuthKbCategory(Long uid, Long wh_id, Integer[] kb_auths);

    List<KbCategoryShowDto> findByUidAuthHotKbCategory(List<Integer> asList, Long uid, int count);

    public boolean deleteById(Long id);

    KbCategoryDto selectById(Long id);

    List<Long> findByUidToCategoryAuth(SysUser sysUser);

    Map<String, Object> findVideoCategoryAdmin();

    Map<String, Object> findVideoCategory(List<Integer> asList, Long uid);

    RespBody<Object> permissionVerification(SysUser sysUser, Long swId);
}
