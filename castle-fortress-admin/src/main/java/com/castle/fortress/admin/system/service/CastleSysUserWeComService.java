package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.CastleSysUserWeComEntity;
import com.castle.fortress.admin.system.dto.CastleSysUserWeComDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 用户企业微信信息表 服务类
 *
 * @author mjj
 * @since 2022-11-30
 */
public interface CastleSysUserWeComService extends IService<CastleSysUserWeComEntity> {

    /**
     * 分页展示用户企业微信信息表列表
     * @param page
     * @param castleSysUserWeComDto
     * @return
     */
    IPage<CastleSysUserWeComDto> pageCastleSysUserWeCom(Page<CastleSysUserWeComDto> page, CastleSysUserWeComDto castleSysUserWeComDto);


    /**
     * 展示用户企业微信信息表列表
     * @param castleSysUserWeComDto
     * @return
     */
    List<CastleSysUserWeComDto> listCastleSysUserWeCom(CastleSysUserWeComDto castleSysUserWeComDto);

    /**
     * 同步用户企业微信信息
     */
    boolean syncWeCom();

    /**
     * 获取用户企微信息
     * @param id
     * @return
     */
    CastleSysUserWeComDto getByUserId(Long id);
}
