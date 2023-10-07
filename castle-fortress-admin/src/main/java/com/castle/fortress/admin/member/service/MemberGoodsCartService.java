package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberGoodsCartDto;
import com.castle.fortress.admin.member.entity.MemberGoodsCartEntity;

import java.util.List;

/**
 * 会员商品购物车 服务类
 *
 * @author Mgg
 * @since 2021-12-03
 */
public interface MemberGoodsCartService extends IService<MemberGoodsCartEntity> {

    /**
     * 分页展示会员商品购物车列表
     * @param page
     * @param memberGoodsCartDto
     * @return
     */
    IPage<MemberGoodsCartDto> pageMemberGoodsCart(Page<MemberGoodsCartDto> page, MemberGoodsCartDto memberGoodsCartDto);

    /**
    * 分页展示会员商品购物车扩展列表
    * @param page
    * @param memberGoodsCartDto
    * @return
    */
    IPage<MemberGoodsCartDto> pageMemberGoodsCartExtends(Page<MemberGoodsCartDto> page, MemberGoodsCartDto memberGoodsCartDto);
    /**
    * 会员商品购物车扩展详情
    * @param id 会员商品购物车id
    * @return
    */
    MemberGoodsCartDto getByIdExtends(Long id);

    /**
     * 展示会员商品购物车列表
     * @param memberGoodsCartDto
     * @return
     */
    List<MemberGoodsCartDto> listMemberGoodsCart(MemberGoodsCartDto memberGoodsCartDto);

}
