package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberAccountDto;
import com.castle.fortress.admin.member.entity.MemberAccountEntity;
import com.castle.fortress.admin.member.mapper.MemberAccountMapper;
import com.castle.fortress.admin.member.service.MemberAccountService;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员账户表 服务实现类
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Service
public class MemberAccountServiceImpl extends ServiceImpl<MemberAccountMapper, MemberAccountEntity> implements MemberAccountService {
    /**
    * 获取查询条件
    * @param memberAccountDto
    * @return
    */
    public QueryWrapper<MemberAccountEntity> getWrapper(MemberAccountDto memberAccountDto){
        QueryWrapper<MemberAccountEntity> wrapper= new QueryWrapper();
        if(memberAccountDto != null){
            MemberAccountEntity memberAccountEntity = ConvertUtil.transformObj(memberAccountDto,MemberAccountEntity.class);
            wrapper.like(memberAccountEntity.getId() != null,"id",memberAccountEntity.getId());
            wrapper.like(memberAccountEntity.getMemberId() != null,"member_id",memberAccountEntity.getMemberId());
            wrapper.like(StrUtil.isNotEmpty(memberAccountEntity.getBalance()),"balance",memberAccountEntity.getBalance());
            wrapper.like(StrUtil.isNotEmpty(memberAccountEntity.getFrozen()),"frozen",memberAccountEntity.getFrozen());
            wrapper.like(memberAccountEntity.getStatus() != null,"status",memberAccountEntity.getStatus());
        }
        return wrapper;
    }


    @Override
    public IPage<MemberAccountDto> pageMemberAccount(Page<MemberAccountDto> page, MemberAccountDto memberAccountDto) {
        QueryWrapper<MemberAccountEntity> wrapper = getWrapper(memberAccountDto);
        Page<MemberAccountEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberAccountEntity> memberAccountPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberAccountDto> pageDto = new Page(memberAccountPage.getCurrent(), memberAccountPage.getSize(),memberAccountPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberAccountPage.getRecords(),MemberAccountDto.class));
        return pageDto;
    }


    @Override
    public List<MemberAccountDto> listMemberAccount(MemberAccountDto memberAccountDto){
        QueryWrapper<MemberAccountEntity> wrapper = getWrapper(memberAccountDto);
        List<MemberAccountEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberAccountDto.class);
    }
}

