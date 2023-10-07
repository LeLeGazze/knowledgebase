package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberBankCardDto;
import com.castle.fortress.admin.member.entity.MemberBankCardEntity;
import com.castle.fortress.admin.member.mapper.MemberBankCardMapper;
import com.castle.fortress.admin.member.service.MemberBankCardService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员银行卡表 服务实现类
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Service
public class MemberBankCardServiceImpl extends ServiceImpl<MemberBankCardMapper, MemberBankCardEntity> implements MemberBankCardService {
    /**
    * 获取查询条件
    * @param memberBankCardDto
    * @return
    */
    public QueryWrapper<MemberBankCardEntity> getWrapper(MemberBankCardDto memberBankCardDto){
        QueryWrapper<MemberBankCardEntity> wrapper= new QueryWrapper();
        if(memberBankCardDto != null){
            MemberBankCardEntity memberBankCardEntity = ConvertUtil.transformObj(memberBankCardDto,MemberBankCardEntity.class);
            wrapper.like(memberBankCardEntity.getMemberId() != null,"member_id",memberBankCardEntity.getMemberId());
            wrapper.like(StrUtil.isNotEmpty(memberBankCardEntity.getCardNum()),"card_num",memberBankCardEntity.getCardNum());
            wrapper.like(StrUtil.isNotEmpty(memberBankCardEntity.getOpenAccount()),"open_account",memberBankCardEntity.getOpenAccount());
            wrapper.like(StrUtil.isNotEmpty(memberBankCardEntity.getBankName()),"bank_name",memberBankCardEntity.getBankName());
            wrapper.like(StrUtil.isNotEmpty(memberBankCardEntity.getPhone()),"phone",memberBankCardEntity.getPhone());
            wrapper.like(StrUtil.isNotEmpty(memberBankCardEntity.getCardUrl()),"card_url",memberBankCardEntity.getCardUrl());
            wrapper.like(memberBankCardEntity.getValidDate() != null,"valid_date",memberBankCardEntity.getValidDate());
            wrapper.like(StrUtil.isNotEmpty(memberBankCardEntity.getCardType()),"card_type",memberBankCardEntity.getCardType());
            wrapper.like(StrUtil.isNotEmpty(memberBankCardEntity.getBankType()),"bank_type",memberBankCardEntity.getBankType());
            wrapper.like(StrUtil.isNotEmpty(memberBankCardEntity.getCity()),"city",memberBankCardEntity.getCity());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberBankCardDto> pageMemberBankCard(Page<MemberBankCardDto> page, MemberBankCardDto memberBankCardDto) {
        QueryWrapper<MemberBankCardEntity> wrapper = getWrapper(memberBankCardDto);
        Page<MemberBankCardEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberBankCardEntity> memberBankCardPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberBankCardDto> pageDto = new Page(memberBankCardPage.getCurrent(), memberBankCardPage.getSize(),memberBankCardPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberBankCardPage.getRecords(),MemberBankCardDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberBankCardDto> pageMemberBankCardExtends(Page<MemberBankCardDto> page, MemberBankCardDto memberBankCardDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberBankCardEntity memberBankCardEntity = ConvertUtil.transformObj(memberBankCardDto,MemberBankCardEntity.class);
        List<MemberBankCardEntity> memberBankCardList=baseMapper.extendsList(pageMap,memberBankCardEntity);
        Long total = baseMapper.extendsCount(memberBankCardEntity);
        Page<MemberBankCardDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberBankCardList,MemberBankCardDto.class));
        return pageDto;
    }
    @Override
    public MemberBankCardDto getByIdExtends(Long id){
        MemberBankCardEntity  memberBankCardEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberBankCardEntity,MemberBankCardDto.class);
    }

    @Override
    public List<MemberBankCardDto> listMemberBankCard(MemberBankCardDto memberBankCardDto){
        QueryWrapper<MemberBankCardEntity> wrapper = getWrapper(memberBankCardDto);
        List<MemberBankCardEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberBankCardDto.class);
    }

    @Override
    public MemberBankCardDto getByCardNum(String cardNum) {
        Map<String , Object> params = new HashMap<>();
        params.put("cardNum", cardNum);
        List<MemberBankCardEntity> list = baseMapper.selectDataList(params);
        return list.size() > 0 ? ConvertUtil.transformObj(list.get(0) , MemberBankCardDto.class) : null;
    }

    @Override
    public List<MemberBankCardDto> listByMemberId(Long memberId) {
        Map<String , Object> params = new HashMap<>();
        params.put("memberId", memberId);
        List<MemberBankCardEntity> list = baseMapper.selectDataList(params);
        return ConvertUtil.transformObjList(list , MemberBankCardDto.class);
    }
}

