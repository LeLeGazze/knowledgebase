package com.castle.fortress.admin.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberGoodsCollectDto;
import com.castle.fortress.admin.member.entity.MemberGoodsCollectEntity;
import com.castle.fortress.admin.member.mapper.MemberGoodsCollectMapper;
import com.castle.fortress.admin.member.service.MemberGoodsCollectService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员商品收藏表 服务实现类
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Service
public class MemberGoodsCollectServiceImpl extends ServiceImpl<MemberGoodsCollectMapper, MemberGoodsCollectEntity> implements MemberGoodsCollectService {
    /**
    * 获取查询条件
    * @param memberGoodsCollectDto
    * @return
    */
    public QueryWrapper<MemberGoodsCollectEntity> getWrapper(MemberGoodsCollectDto memberGoodsCollectDto){
        QueryWrapper<MemberGoodsCollectEntity> wrapper= new QueryWrapper();
        if(memberGoodsCollectDto != null){
            MemberGoodsCollectEntity memberGoodsCollectEntity = ConvertUtil.transformObj(memberGoodsCollectDto,MemberGoodsCollectEntity.class);
            wrapper.like(memberGoodsCollectEntity.getGoodsId() != null,"goods_id",memberGoodsCollectEntity.getGoodsId());
            wrapper.like(memberGoodsCollectEntity.getMemberId() != null,"member_id",memberGoodsCollectEntity.getMemberId());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberGoodsCollectDto> pageMemberGoodsCollect(Page<MemberGoodsCollectDto> page, MemberGoodsCollectDto memberGoodsCollectDto) {
        QueryWrapper<MemberGoodsCollectEntity> wrapper = getWrapper(memberGoodsCollectDto);
        Page<MemberGoodsCollectEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberGoodsCollectEntity> memberGoodsCollectPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberGoodsCollectDto> pageDto = new Page(memberGoodsCollectPage.getCurrent(), memberGoodsCollectPage.getSize(),memberGoodsCollectPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberGoodsCollectPage.getRecords(),MemberGoodsCollectDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberGoodsCollectDto> pageMemberGoodsCollectExtends(Page<MemberGoodsCollectDto> page, MemberGoodsCollectDto memberGoodsCollectDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberGoodsCollectEntity memberGoodsCollectEntity = ConvertUtil.transformObj(memberGoodsCollectDto,MemberGoodsCollectEntity.class);
        List<MemberGoodsCollectEntity> memberGoodsCollectList=baseMapper.extendsList(pageMap,memberGoodsCollectEntity);
        Long total = baseMapper.extendsCount(memberGoodsCollectEntity);
        Page<MemberGoodsCollectDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberGoodsCollectList,MemberGoodsCollectDto.class));
        return pageDto;
    }
    @Override
    public MemberGoodsCollectDto getByIdExtends(Long id){
        MemberGoodsCollectEntity  memberGoodsCollectEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberGoodsCollectEntity,MemberGoodsCollectDto.class);
    }

    @Override
    public List<MemberGoodsCollectDto> listMemberGoodsCollect(MemberGoodsCollectDto memberGoodsCollectDto){
        QueryWrapper<MemberGoodsCollectEntity> wrapper = getWrapper(memberGoodsCollectDto);
        List<MemberGoodsCollectEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberGoodsCollectDto.class);
    }
}

