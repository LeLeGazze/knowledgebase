package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberRealinfoDto;
import com.castle.fortress.admin.member.entity.MemberRealinfoEntity;

import java.util.List;

/**
 * 会员真实信息表 服务类
 *
 * @author Mgg
 * @since 2021-11-27
 */
public interface MemberRealinfoService extends IService<MemberRealinfoEntity> {

    /**
     * 分页展示会员真实信息表列表
     * @param page
     * @param memberRealinfoDto
     * @return
     */
    IPage<MemberRealinfoDto> pageMemberRealinfo(Page<MemberRealinfoDto> page, MemberRealinfoDto memberRealinfoDto);

    /**
    * 分页展示会员真实信息表扩展列表
    * @param page
    * @param memberRealinfoDto
    * @return
    */
    IPage<MemberRealinfoDto> pageMemberRealinfoExtends(Page<MemberRealinfoDto> page, MemberRealinfoDto memberRealinfoDto);
    /**
    * 会员真实信息表扩展详情
    * @param id 会员真实信息表id
    * @return
    */
    MemberRealinfoDto getByIdExtends(Long id);

    /**
     * 展示会员真实信息表列表
     * @param memberRealinfoDto
     * @return
     */
    List<MemberRealinfoDto> listMemberRealinfo(MemberRealinfoDto memberRealinfoDto);

    /**
     * 查询会员的真实信息
     * @param memberId
     * @return
     */
    MemberRealinfoDto getByMemberId(Long memberId);
}
