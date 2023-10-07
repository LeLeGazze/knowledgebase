package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberBankCardDto;
import com.castle.fortress.admin.member.entity.MemberBankCardEntity;

import java.util.List;

/**
 * 会员银行卡表 服务类
 *
 * @author Mgg
 * @since 2021-12-03
 */
public interface MemberBankCardService extends IService<MemberBankCardEntity> {

    /**
     * 分页展示会员银行卡表列表
     * @param page
     * @param memberBankCardDto
     * @return
     */
    IPage<MemberBankCardDto> pageMemberBankCard(Page<MemberBankCardDto> page, MemberBankCardDto memberBankCardDto);

    /**
    * 分页展示会员银行卡表扩展列表
    * @param page
    * @param memberBankCardDto
    * @return
    */
    IPage<MemberBankCardDto> pageMemberBankCardExtends(Page<MemberBankCardDto> page, MemberBankCardDto memberBankCardDto);
    /**
    * 会员银行卡表扩展详情
    * @param id 会员银行卡表id
    * @return
    */
    MemberBankCardDto getByIdExtends(Long id);

    /**
     * 展示会员银行卡表列表
     * @param memberBankCardDto
     * @return
     */
    List<MemberBankCardDto> listMemberBankCard(MemberBankCardDto memberBankCardDto);

    /**
     * 根据银行卡号查询银行卡
     * @return
     */
    MemberBankCardDto getByCardNum(String cardNum);

    /**
     * 获取会员添加的银行卡
     * @return
     */
    List<MemberBankCardDto> listByMemberId(Long memberId);

}
