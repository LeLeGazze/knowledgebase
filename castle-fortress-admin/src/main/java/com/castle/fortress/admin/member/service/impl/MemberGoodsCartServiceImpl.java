package com.castle.fortress.admin.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberGoodsCartDto;
import com.castle.fortress.admin.member.entity.MemberGoodsCartEntity;
import com.castle.fortress.admin.member.mapper.MemberGoodsCartMapper;
import com.castle.fortress.admin.member.service.MemberGoodsCartService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员商品购物车 服务实现类
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Service
public class MemberGoodsCartServiceImpl extends ServiceImpl<MemberGoodsCartMapper, MemberGoodsCartEntity> implements MemberGoodsCartService {
    /**
    * 获取查询条件
    * @param memberGoodsCartDto
    * @return
    */
    public QueryWrapper<MemberGoodsCartEntity> getWrapper(MemberGoodsCartDto memberGoodsCartDto){
        QueryWrapper<MemberGoodsCartEntity> wrapper= new QueryWrapper();
        if(memberGoodsCartDto != null){
            MemberGoodsCartEntity memberGoodsCartEntity = ConvertUtil.transformObj(memberGoodsCartDto,MemberGoodsCartEntity.class);
            wrapper.like(memberGoodsCartEntity.getMemberId() != null,"member_id",memberGoodsCartEntity.getMemberId());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberGoodsCartDto> pageMemberGoodsCart(Page<MemberGoodsCartDto> page, MemberGoodsCartDto memberGoodsCartDto) {
        QueryWrapper<MemberGoodsCartEntity> wrapper = getWrapper(memberGoodsCartDto);
        Page<MemberGoodsCartEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberGoodsCartEntity> memberGoodsCartPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberGoodsCartDto> pageDto = new Page(memberGoodsCartPage.getCurrent(), memberGoodsCartPage.getSize(),memberGoodsCartPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberGoodsCartPage.getRecords(),MemberGoodsCartDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberGoodsCartDto> pageMemberGoodsCartExtends(Page<MemberGoodsCartDto> page, MemberGoodsCartDto memberGoodsCartDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberGoodsCartEntity memberGoodsCartEntity = ConvertUtil.transformObj(memberGoodsCartDto,MemberGoodsCartEntity.class);
        List<MemberGoodsCartEntity> memberGoodsCartList=baseMapper.extendsList(pageMap,memberGoodsCartEntity);
        Long total = baseMapper.extendsCount(memberGoodsCartEntity);
        Page<MemberGoodsCartDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberGoodsCartList,MemberGoodsCartDto.class));
        return pageDto;
    }
    @Override
    public MemberGoodsCartDto getByIdExtends(Long id){
        MemberGoodsCartEntity  memberGoodsCartEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberGoodsCartEntity,MemberGoodsCartDto.class);
    }

    @Override
    public List<MemberGoodsCartDto> listMemberGoodsCart(MemberGoodsCartDto memberGoodsCartDto){
        QueryWrapper<MemberGoodsCartEntity> wrapper = getWrapper(memberGoodsCartDto);
        List<MemberGoodsCartEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberGoodsCartDto.class);
    }
}

