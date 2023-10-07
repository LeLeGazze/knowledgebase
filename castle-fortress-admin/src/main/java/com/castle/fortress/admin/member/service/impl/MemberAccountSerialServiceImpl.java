package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberAccountSerialDto;
import com.castle.fortress.admin.member.entity.MemberAccountSerialEntity;
import com.castle.fortress.admin.member.mapper.MemberAccountSerialMapper;
import com.castle.fortress.admin.member.service.MemberAccountSerialService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员账户流水 服务实现类
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Service
public class MemberAccountSerialServiceImpl extends ServiceImpl<MemberAccountSerialMapper, MemberAccountSerialEntity> implements MemberAccountSerialService {
    /**
    * 获取查询条件
    * @param memberAccountSerialDto
    * @return
    */
    public QueryWrapper<MemberAccountSerialEntity> getWrapper(MemberAccountSerialDto memberAccountSerialDto){
        QueryWrapper<MemberAccountSerialEntity> wrapper= new QueryWrapper();
        if(memberAccountSerialDto != null){
            MemberAccountSerialEntity memberAccountSerialEntity = ConvertUtil.transformObj(memberAccountSerialDto,MemberAccountSerialEntity.class);
            wrapper.like(memberAccountSerialEntity.getId() != null,"id",memberAccountSerialEntity.getId());
            wrapper.like(memberAccountSerialEntity.getMemberId() != null,"member_id",memberAccountSerialEntity.getMemberId());
            wrapper.like(memberAccountSerialEntity.getMemberAccountId() != null,"member_account_id",memberAccountSerialEntity.getMemberAccountId());
            wrapper.like(StrUtil.isNotEmpty(memberAccountSerialEntity.getMoney()),"money",memberAccountSerialEntity.getMoney());
            wrapper.like(memberAccountSerialEntity.getType() != null,"type",memberAccountSerialEntity.getType());
            wrapper.like(memberAccountSerialEntity.getCategory() != null,"category",memberAccountSerialEntity.getCategory());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberAccountSerialDto> pageMemberAccountSerial(Page<MemberAccountSerialDto> page, MemberAccountSerialDto memberAccountSerialDto) {
        QueryWrapper<MemberAccountSerialEntity> wrapper = getWrapper(memberAccountSerialDto);
        Page<MemberAccountSerialEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberAccountSerialEntity> memberAccountSerialPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<MemberAccountSerialDto> pageDto = new Page(memberAccountSerialPage.getCurrent(), memberAccountSerialPage.getSize(),memberAccountSerialPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberAccountSerialPage.getRecords(),MemberAccountSerialDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberAccountSerialDto> pageMemberAccountSerialExtends(Page<MemberAccountSerialDto> page, MemberAccountSerialDto memberAccountSerialDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberAccountSerialEntity memberAccountSerialEntity = ConvertUtil.transformObj(memberAccountSerialDto,MemberAccountSerialEntity.class);
        List<MemberAccountSerialEntity> memberAccountSerialList=baseMapper.extendsList(pageMap,memberAccountSerialEntity);
        Long total = baseMapper.extendsCount(memberAccountSerialEntity);
        Page<MemberAccountSerialDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberAccountSerialList,MemberAccountSerialDto.class));
        return pageDto;
    }
    @Override
    public MemberAccountSerialDto getByIdExtends(Long id){
        MemberAccountSerialEntity  memberAccountSerialEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberAccountSerialEntity,MemberAccountSerialDto.class);
    }

    @Override
    public List<MemberAccountSerialDto> listMemberAccountSerial(MemberAccountSerialDto memberAccountSerialDto){
        QueryWrapper<MemberAccountSerialEntity> wrapper = getWrapper(memberAccountSerialDto);
        List<MemberAccountSerialEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberAccountSerialDto.class);
    }
}

