package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.member.entity.MemberEntity;
import com.castle.fortress.admin.member.mapper.MemberMapper;
import com.castle.fortress.admin.member.service.MemberService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员表 服务实现类
 *
 * @author Mgg
 * @since 2021-11-25
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {
    /**
    * 获取查询条件
    * @param memberDto
    * @return
    */
    public QueryWrapper<MemberEntity> getWrapper(MemberDto memberDto){
        QueryWrapper<MemberEntity> wrapper= new QueryWrapper();
        if(memberDto != null){
            MemberEntity memberEntity = ConvertUtil.transformObj(memberDto,MemberEntity.class);
            wrapper.like(StrUtil.isNotEmpty(memberEntity.getUserName()),"user_name",memberEntity.getUserName());
            wrapper.like(StrUtil.isNotEmpty(memberEntity.getPhone()),"phone",memberEntity.getPhone());
            wrapper.like(StrUtil.isNotEmpty(memberEntity.getNickName()),"nick_name",memberEntity.getNickName());
            wrapper.like(StrUtil.isNotEmpty(memberEntity.getOpenid()),"openid",memberEntity.getOpenid());
            wrapper.like(StrUtil.isNotEmpty(memberEntity.getUnionid()),"unionid",memberEntity.getUnionid());

            wrapper.like(memberEntity.getStatus() != null,"status",memberEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberDto> pageMember(Page<MemberDto> page, MemberDto memberDto) {
        QueryWrapper<MemberEntity> wrapper = getWrapper(memberDto);
        Page<MemberEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberEntity> memberPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberDto> pageDto = new Page(memberPage.getCurrent(), memberPage.getSize(),memberPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberPage.getRecords(),MemberDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberDto> pageMemberExtends(Page<MemberDto> page, MemberDto memberDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberEntity memberEntity = ConvertUtil.transformObj(memberDto,MemberEntity.class);
        List<MemberEntity> memberList=baseMapper.extendsList(pageMap,memberEntity);
        Long total = baseMapper.extendsCount(memberEntity);
        Page<MemberDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberList,MemberDto.class));
        return pageDto;
    }
    @Override
    public MemberDto getByIdExtends(Long id){
        MemberEntity  memberEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberEntity,MemberDto.class);
    }

    @Override
    public List<MemberDto> listMember(MemberDto memberDto){
        QueryWrapper<MemberEntity> wrapper = getWrapper(memberDto);
        List<MemberEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberDto.class);
    }

    @Override
    public MemberDto getByPhone(String phone) {
        QueryWrapper<MemberEntity> wrapper= new QueryWrapper();
        wrapper.eq("phone" , phone);
        List<MemberEntity> list = baseMapper.selectList(wrapper);
        return list.size() == 0 ? null : ConvertUtil.transformObj(list.get(0),MemberDto.class);
    }
}

