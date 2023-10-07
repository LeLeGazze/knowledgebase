package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbSubjectWarehouseEntity;
import com.castle.fortress.admin.knowledge.dto.KbSubjectWarehouseDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.common.entity.RespBody;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * 主题知识仓库 服务类
 *
 * @author lyz
 * @since 2023-04-24
 */
public interface KbSubjectWarehouseService extends IService<KbSubjectWarehouseEntity> {

    /**
     * 分页展示主题知识仓库列表
     * @param page
     * @param kbSubjectWarehouseDto
     * @return
     */
    IPage<KbSubjectWarehouseDto> pageKbSubjectWarehouse(Page<KbSubjectWarehouseDto> page, KbSubjectWarehouseDto kbSubjectWarehouseDto);

    /**
    * 分页展示主题知识仓库扩展列表
    * @param page
    * @param kbSubjectWarehouseDto
    * @return
    */
    IPage<KbSubjectWarehouseDto> pageKbSubjectWarehouseExtends(Page<KbSubjectWarehouseDto> page, KbSubjectWarehouseDto kbSubjectWarehouseDto);
    /**
    * 主题知识仓库扩展详情
    * @param id 主题知识仓库id
    * @return
    */
    KbSubjectWarehouseDto getByIdExtends(Long id);

    /**
     * 展示主题知识仓库列表
     * @param kbSubjectWarehouseDto
     * @return
     */
    List<KbSubjectWarehouseDto> listKbSubjectWarehouse(KbSubjectWarehouseDto kbSubjectWarehouseDto);

    boolean add(KbSubjectWarehouseDto kbSubjectWarehouseDto);

    List<KbSubjectWarehouseDto> AuthlistKbSubjectWarehouse(KbSubjectWarehouseDto kbSubjectWarehouseDto);

    boolean updateById(KbSubjectWarehouseDto kbSubjectWarehouseDto);

    List<KbSubjectWarehouseDto> findByWid(ArrayList<KbWarehouseAuthEntity> uAuths,int num);

    List<KbSubjectWarehouseDto> listKbSubjectToCategory(ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities);
    List<KbSubjectWarehouseDto> listKbSubjectToCategory(ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities, Long uid, List<Integer> longs);

    /**
     * 首页展示
     * @return
     */
    List<KbSubjectWarehouseEntity> showList();

    List<KbSubjectWarehouseDto> findByListToCategoryAdd(ArrayList<KbWarehouseAuthEntity> uAuths, Long uid, List<Integer> asList);

    List<KbSubjectWarehouseDto> findByListToCategoryAddAdmin(ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities);

    KbSubjectWarehouseDto findByListToCategoryVideoAddAdmin();

    KbSubjectWarehouseDto findByListToCategoryVideoAdd(ArrayList<KbWarehouseAuthEntity> uAuths, Long uid, List<Integer> asList);

    KbSubjectWarehouseDto findById(Long id);

    List<KbSubjectWarehouseEntity> findByModelId(Long id);

    RespBody<Object> permissionVerification(SysUser sysUser);

    /**
     *  没事设置任何权限 展示首页展示
     * @return
     */
    List<KbSubjectWarehouseDto> findByHomeShow();
}
