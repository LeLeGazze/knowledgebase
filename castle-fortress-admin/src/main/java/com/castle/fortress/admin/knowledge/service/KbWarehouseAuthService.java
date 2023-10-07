package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.dto.KbWarehouseAuthDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * 主题知识仓库权限表 服务类
 *
 * @author 
 * @since 2023-04-24
 */
public interface KbWarehouseAuthService extends IService<KbWarehouseAuthEntity> {

    /**
     * 分页展示主题知识仓库权限表列表
     * @param page
     * @param kbWarehouseAuthDto
     * @return
     */
    IPage<KbWarehouseAuthDto> pageKbWarehouseAuth(Page<KbWarehouseAuthDto> page, KbWarehouseAuthDto kbWarehouseAuthDto);


    /**
     * 展示主题知识仓库权限表列表
     * @param kbWarehouseAuthDto
     * @return
     */
    List<KbWarehouseAuthDto> listKbWarehouseAuth(KbWarehouseAuthDto kbWarehouseAuthDto);

    boolean saveAll(KbWarehouseAuthDto kbWarehouseAuthDto);

    boolean updateById(KbWarehouseAuthDto kbWarehouseAuthDto);

    ArrayList<KbWarehouseAuthEntity> findByUid(Long uid, String cateGory,Long swId );

    ArrayList<KbWarehouseAuthEntity> findByUidAndSwid(Long uid, Long swId, String s);

    List<KbWarehouseAuthDto> findByUidWarehouseAuth(Long uid,Long wh_id, Long wc_id);

    void deleteByWid(Long id);

    List<KbWarehouseAuthDto> findBySwId(Long id);

    KbWarehouseAuthDto findBySwIdAuth(Long swId);

    ArrayList<KbWarehouseAuthEntity> findByUidVideo(Long uid, String number);
}
