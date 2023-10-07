package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.entity.MemberLevelEntity;
import com.castle.fortress.admin.member.dto.MemberLevelDto;
import com.castle.fortress.admin.member.mapper.MemberLevelMapper;
import com.castle.fortress.admin.member.service.MemberLevelService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 会员等级表 服务实现类
 *
 * @author whc
 * @since 2022-12-29
 */
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevelEntity> implements MemberLevelService {
    /**
    * 获取查询条件
    * @param memberLevelDto
    * @return
    */
    public QueryWrapper<MemberLevelEntity> getWrapper(MemberLevelDto memberLevelDto){
        QueryWrapper<MemberLevelEntity> wrapper= new QueryWrapper();
        if(memberLevelDto != null){
            MemberLevelEntity memberLevelEntity = ConvertUtil.transformObj(memberLevelDto,MemberLevelEntity.class);
            wrapper.like(StrUtil.isNotEmpty(memberLevelEntity.getName()),"name",memberLevelEntity.getName());
            wrapper.eq(memberLevelEntity.getUpConditions() != null,"up_conditions",memberLevelEntity.getUpConditions());
            wrapper.eq(memberLevelEntity.getGoodsId() != null,"goods_id",memberLevelEntity.getGoodsId());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberLevelDto> pageMemberLevel(Page<MemberLevelDto> page, MemberLevelDto memberLevelDto) {
        QueryWrapper<MemberLevelEntity> wrapper = getWrapper(memberLevelDto);
        Page<MemberLevelEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberLevelEntity> memberLevelPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberLevelDto> pageDto = new Page(memberLevelPage.getCurrent(), memberLevelPage.getSize(),memberLevelPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberLevelPage.getRecords(),MemberLevelDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberLevelDto> pageMemberLevelExtends(Page<MemberLevelDto> page, MemberLevelDto memberLevelDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberLevelEntity memberLevelEntity = ConvertUtil.transformObj(memberLevelDto,MemberLevelEntity.class);
        List<MemberLevelEntity> memberLevelList=baseMapper.extendsList(pageMap,memberLevelEntity);
        Long total = baseMapper.extendsCount(memberLevelEntity);
        Page<MemberLevelDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberLevelList,MemberLevelDto.class));
        return pageDto;
    }
    @Override
    public MemberLevelDto getByIdExtends(Long id){
        MemberLevelEntity  memberLevelEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberLevelEntity,MemberLevelDto.class);
    }

    @Override
    public List<MemberLevelDto> listMemberLevel(MemberLevelDto memberLevelDto){
        QueryWrapper<MemberLevelEntity> wrapper = getWrapper(memberLevelDto);
        List<MemberLevelEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberLevelDto.class);
    }
}

