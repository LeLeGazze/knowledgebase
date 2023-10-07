package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.entity.MemberTagEntity;
import com.castle.fortress.admin.member.dto.MemberTagDto;
import com.castle.fortress.admin.member.mapper.MemberTagMapper;
import com.castle.fortress.admin.member.service.MemberTagService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 会员标签表 服务实现类
 *
 * @author whc
 * @since 2022-12-08
 */
@Service
public class MemberTagServiceImpl extends ServiceImpl<MemberTagMapper, MemberTagEntity> implements MemberTagService {
    /**
    * 获取查询条件
    * @param memberTagDto
    * @return
    */
    public QueryWrapper<MemberTagEntity> getWrapper(MemberTagDto memberTagDto){
        QueryWrapper<MemberTagEntity> wrapper= new QueryWrapper();
        if(memberTagDto != null){
            MemberTagEntity memberTagEntity = ConvertUtil.transformObj(memberTagDto,MemberTagEntity.class);
            wrapper.like(StrUtil.isNotEmpty(memberTagEntity.getName()),"name",memberTagEntity.getName());
            wrapper.orderByDesc("sort");
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberTagDto> pageMemberTag(Page<MemberTagDto> page, MemberTagDto memberTagDto) {
        QueryWrapper<MemberTagEntity> wrapper = getWrapper(memberTagDto);
        Page<MemberTagEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberTagEntity> memberTagPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberTagDto> pageDto = new Page(memberTagPage.getCurrent(), memberTagPage.getSize(),memberTagPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberTagPage.getRecords(),MemberTagDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberTagDto> pageMemberTagExtends(Page<MemberTagDto> page, MemberTagDto memberTagDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberTagEntity memberTagEntity = ConvertUtil.transformObj(memberTagDto,MemberTagEntity.class);
        List<MemberTagEntity> memberTagList=baseMapper.extendsList(pageMap,memberTagEntity);
        Long total = baseMapper.extendsCount(memberTagEntity);
        Page<MemberTagDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberTagList,MemberTagDto.class));
        return pageDto;
    }
    @Override
    public MemberTagDto getByIdExtends(Long id){
        MemberTagEntity  memberTagEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberTagEntity,MemberTagDto.class);
    }

    @Override
    public List<MemberTagDto> listMemberTag(MemberTagDto memberTagDto){
        QueryWrapper<MemberTagEntity> wrapper = getWrapper(memberTagDto);
        List<MemberTagEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberTagDto.class);
    }
}

