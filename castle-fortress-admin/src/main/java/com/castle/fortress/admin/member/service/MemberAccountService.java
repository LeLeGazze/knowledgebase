package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberAccountDto;
import com.castle.fortress.admin.member.entity.MemberAccountEntity;

import java.util.List;

/**
 * 会员账户表 服务类
 *
 * @author Mgg
 * @since 2021-12-02
 */
public interface MemberAccountService extends IService<MemberAccountEntity> {

    /**
     * 分页展示会员账户表列表
     * @param page
     * @param memberAccountDto
     * @return
     */
    IPage<MemberAccountDto> pageMemberAccount(Page<MemberAccountDto> page, MemberAccountDto memberAccountDto);


    /**
     * 展示会员账户表列表
     * @param memberAccountDto
     * @return
     */
    List<MemberAccountDto> listMemberAccount(MemberAccountDto memberAccountDto);

}
