package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberRealinfoDto;
import com.castle.fortress.admin.member.entity.MemberRealinfoEntity;
import com.castle.fortress.admin.member.mapper.MemberRealinfoMapper;
import com.castle.fortress.admin.member.service.MemberRealinfoService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员真实信息表 服务实现类
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Service
public class MemberRealinfoServiceImpl extends ServiceImpl<MemberRealinfoMapper, MemberRealinfoEntity> implements MemberRealinfoService {
    /**
    * 获取查询条件
    * @param memberRealinfoDto
    * @return
    */
    public QueryWrapper<MemberRealinfoEntity> getWrapper(MemberRealinfoDto memberRealinfoDto){
        QueryWrapper<MemberRealinfoEntity> wrapper= new QueryWrapper();
        if(memberRealinfoDto != null){
            MemberRealinfoEntity memberRealinfoEntity = ConvertUtil.transformObj(memberRealinfoDto,MemberRealinfoEntity.class);
            wrapper.like(memberRealinfoEntity.getMemberId() != null,"member_id",memberRealinfoEntity.getMemberId());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getRealName()),"real_name",memberRealinfoEntity.getRealName());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getCardNum()),"card_num",memberRealinfoEntity.getCardNum());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getGender()),"gender",memberRealinfoEntity.getGender());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getCardType()),"card_type",memberRealinfoEntity.getCardType());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getPerson()),"person",memberRealinfoEntity.getPerson());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getCapital()),"capital",memberRealinfoEntity.getCapital());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getBusiness()),"business",memberRealinfoEntity.getBusiness());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getEnterpriseName()),"enterprise_name",memberRealinfoEntity.getEnterpriseName());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getCreditCode()),"credit_code",memberRealinfoEntity.getCreditCode());
            wrapper.like(memberRealinfoEntity.getRegDate() != null,"reg_date",memberRealinfoEntity.getRegDate());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getBusinessTerm()),"business_term",memberRealinfoEntity.getBusinessTerm());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getResidence()),"residence",memberRealinfoEntity.getResidence());
            wrapper.like(StrUtil.isNotEmpty(memberRealinfoEntity.getRegAuthority()),"reg_authority",memberRealinfoEntity.getRegAuthority());
            wrapper.like(memberRealinfoEntity.getAwardDate() != null,"award_date",memberRealinfoEntity.getAwardDate());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberRealinfoDto> pageMemberRealinfo(Page<MemberRealinfoDto> page, MemberRealinfoDto memberRealinfoDto) {
        QueryWrapper<MemberRealinfoEntity> wrapper = getWrapper(memberRealinfoDto);
        Page<MemberRealinfoEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberRealinfoEntity> memberRealinfoPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberRealinfoDto> pageDto = new Page(memberRealinfoPage.getCurrent(), memberRealinfoPage.getSize(),memberRealinfoPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberRealinfoPage.getRecords(),MemberRealinfoDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberRealinfoDto> pageMemberRealinfoExtends(Page<MemberRealinfoDto> page, MemberRealinfoDto memberRealinfoDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberRealinfoEntity memberRealinfoEntity = ConvertUtil.transformObj(memberRealinfoDto,MemberRealinfoEntity.class);
        List<MemberRealinfoEntity> memberRealinfoList=baseMapper.extendsList(pageMap,memberRealinfoEntity);
        Long total = baseMapper.extendsCount(memberRealinfoEntity);
        Page<MemberRealinfoDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberRealinfoList,MemberRealinfoDto.class));
        return pageDto;
    }
    @Override
    public MemberRealinfoDto getByIdExtends(Long id){
        MemberRealinfoEntity  memberRealinfoEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberRealinfoEntity,MemberRealinfoDto.class);
    }

    @Override
    public List<MemberRealinfoDto> listMemberRealinfo(MemberRealinfoDto memberRealinfoDto){
        QueryWrapper<MemberRealinfoEntity> wrapper = getWrapper(memberRealinfoDto);
        List<MemberRealinfoEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberRealinfoDto.class);
    }

    @Override
    public MemberRealinfoDto getByMemberId(Long memberId) {
        Map<String , Object> params = new HashMap<>();
        params.put("memberId" , memberId);
        List<MemberRealinfoEntity> list = baseMapper.selectDataList(params);
        return list.size() > 0 ? ConvertUtil.transformObj(list.get(0) , MemberRealinfoDto.class) : null;
    }


}

