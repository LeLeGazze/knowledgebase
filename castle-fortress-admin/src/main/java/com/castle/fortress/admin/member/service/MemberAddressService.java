package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberAddressDto;
import com.castle.fortress.admin.member.entity.MemberAddressEntity;

import java.util.List;

/**
 * 会员收货地址表 服务类
 *
 * @author Mgg
 * @since 2021-12-02
 */
public interface MemberAddressService extends IService<MemberAddressEntity> {

    /**
     * 分页展示会员收货地址表列表
     * @param page
     * @param memberAddressDto
     * @return
     */
    IPage<MemberAddressDto> pageMemberAddress(Page<MemberAddressDto> page, MemberAddressDto memberAddressDto);

    /**
    * 分页展示会员收货地址表扩展列表
    * @param page
    * @param memberAddressDto
    * @return
    */
    IPage<MemberAddressDto> pageMemberAddressExtends(Page<MemberAddressDto> page, MemberAddressDto memberAddressDto);
    /**
    * 会员收货地址表扩展详情
    * @param id 会员收货地址表id
    * @return
    */
    MemberAddressDto getByIdExtends(Long id);

    /**
     * 展示会员收货地址表列表
     * @param memberAddressDto
     * @return
     */
    List<MemberAddressDto> listMemberAddress(MemberAddressDto memberAddressDto);

    MemberAddressEntity changeDefaultAddress(MemberAddressEntity memberAddressEntity,Integer isDefault);
}
