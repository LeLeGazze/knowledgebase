package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.entity.CastleSysUserDingEntity;
import com.castle.fortress.admin.system.dto.CastleSysUserDingDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 用户钉钉信息表 服务类
 *
 * @author Mgg
 * @since 2022-12-13
 */
public interface CastleSysUserDingService extends IService<CastleSysUserDingEntity> {

    /**
     * 分页展示用户钉钉信息表列表
     * @param page
     * @param castleSysUserDingDto
     * @return
     */
    IPage<CastleSysUserDingDto> pageCastleSysUserDing(Page<CastleSysUserDingDto> page, CastleSysUserDingDto castleSysUserDingDto);


    /**
     * 展示用户钉钉信息表列表
     * @param castleSysUserDingDto
     * @return
     */
    List<CastleSysUserDingDto> listCastleSysUserDing(CastleSysUserDingDto castleSysUserDingDto);

    /**
     * 同步用户钉钉信息
     * @return
     */
    boolean syncDing();
}
