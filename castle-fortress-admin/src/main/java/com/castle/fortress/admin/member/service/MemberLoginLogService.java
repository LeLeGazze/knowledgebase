package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberLoginLogDto;
import com.castle.fortress.admin.member.entity.MemberLoginLogEntity;

import java.util.List;

/**
 * 会员登录日志表 服务类
 *
 * @author Mgg
 * @since 2021-11-26
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    /**
     * 分页展示会员登录日志表列表
     * @param page
     * @param memberLoginLogDto
     * @return
     */
    IPage<MemberLoginLogDto> pageMemberLoginLog(Page<MemberLoginLogDto> page, MemberLoginLogDto memberLoginLogDto);


    /**
     * 展示会员登录日志表列表
     * @param memberLoginLogDto
     * @return
     */
    List<MemberLoginLogDto> listMemberLoginLog(MemberLoginLogDto memberLoginLogDto);

    /**
     * 异步保存登录日志
     * @param memberLoginLogEntity
     */
    void saveLog(MemberLoginLogEntity memberLoginLogEntity);
}
