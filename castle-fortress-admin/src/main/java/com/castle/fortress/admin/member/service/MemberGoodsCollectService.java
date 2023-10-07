package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberGoodsCollectDto;
import com.castle.fortress.admin.member.entity.MemberGoodsCollectEntity;

import java.util.List;

/**
 * 会员商品收藏表 服务类
 *
 * @author Mgg
 * @since 2021-12-03
 */
public interface MemberGoodsCollectService extends IService<MemberGoodsCollectEntity> {

    /**
     * 分页展示会员商品收藏表列表
     * @param page
     * @param memberGoodsCollectDto
     * @return
     */
    IPage<MemberGoodsCollectDto> pageMemberGoodsCollect(Page<MemberGoodsCollectDto> page, MemberGoodsCollectDto memberGoodsCollectDto);

    /**
    * 分页展示会员商品收藏表扩展列表
    * @param page
    * @param memberGoodsCollectDto
    * @return
    */
    IPage<MemberGoodsCollectDto> pageMemberGoodsCollectExtends(Page<MemberGoodsCollectDto> page, MemberGoodsCollectDto memberGoodsCollectDto);
    /**
    * 会员商品收藏表扩展详情
    * @param id 会员商品收藏表id
    * @return
    */
    MemberGoodsCollectDto getByIdExtends(Long id);

    /**
     * 展示会员商品收藏表列表
     * @param memberGoodsCollectDto
     * @return
     */
    List<MemberGoodsCollectDto> listMemberGoodsCollect(MemberGoodsCollectDto memberGoodsCollectDto);

}
