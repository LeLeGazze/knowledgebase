package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.member.entity.MemberEntity;

import java.util.List;

/**
 * 会员表 服务类
 *
 * @author Mgg
 * @since 2021-11-25
 */
public interface MemberService extends IService<MemberEntity> {

    /**
     * 分页展示会员表列表
     * @param page
     * @param memberDto
     * @return
     */
    IPage<MemberDto> pageMember(Page<MemberDto> page, MemberDto memberDto);

    /**
    * 分页展示会员表扩展列表
    * @param page
    * @param memberDto
    * @return
    */
    IPage<MemberDto> pageMemberExtends(Page<MemberDto> page, MemberDto memberDto);
    /**
    * 会员表扩展详情
    * @param id 会员表id
    * @return
    */
    MemberDto getByIdExtends(Long id);

    /**
     * 展示会员表列表
     * @param memberDto
     * @return
     */
    List<MemberDto> listMember(MemberDto memberDto);

    /**
     * 根据手机获取会员
     * @param phone 手机号
     * @return
     */
    MemberDto getByPhone(String phone);
}
