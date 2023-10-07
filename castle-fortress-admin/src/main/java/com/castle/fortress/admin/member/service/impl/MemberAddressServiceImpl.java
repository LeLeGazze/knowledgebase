package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberAddressDto;
import com.castle.fortress.admin.member.entity.MemberAddressEntity;
import com.castle.fortress.admin.member.mapper.MemberAddressMapper;
import com.castle.fortress.admin.member.service.MemberAddressService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址表 服务实现类
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Service
public class MemberAddressServiceImpl extends ServiceImpl<MemberAddressMapper, MemberAddressEntity> implements MemberAddressService {
    /**
     * 获取查询条件
     *
     * @param memberAddressDto
     * @return
     */
    public QueryWrapper<MemberAddressEntity> getWrapper(MemberAddressDto memberAddressDto) {
        QueryWrapper<MemberAddressEntity> wrapper = new QueryWrapper();
        if (memberAddressDto != null) {
            MemberAddressEntity memberAddressEntity = ConvertUtil.transformObj(memberAddressDto, MemberAddressEntity.class);
            wrapper.eq(memberAddressEntity.getMemberId() != null, "member_id", memberAddressEntity.getMemberId());
            wrapper.like(StrUtil.isNotEmpty(memberAddressEntity.getConsignee()), "consignee", memberAddressEntity.getConsignee());
            wrapper.like(StrUtil.isNotEmpty(memberAddressEntity.getPhone()), "phone", memberAddressEntity.getPhone());
            wrapper.like(StrUtil.isNotEmpty(memberAddressEntity.getProvince()), "province", memberAddressEntity.getProvince());
            wrapper.like(StrUtil.isNotEmpty(memberAddressEntity.getCity()), "city", memberAddressEntity.getCity());
            wrapper.like(StrUtil.isNotEmpty(memberAddressEntity.getArea()), "area", memberAddressEntity.getArea());
            wrapper.like(StrUtil.isNotEmpty(memberAddressEntity.getAddress()), "address", memberAddressEntity.getAddress());
            wrapper.like(memberAddressEntity.getIsDefault() != null, "is_default", memberAddressEntity.getIsDefault());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberAddressDto> pageMemberAddress(Page<MemberAddressDto> page, MemberAddressDto memberAddressDto) {
        QueryWrapper<MemberAddressEntity> wrapper = getWrapper(memberAddressDto);
        Page<MemberAddressEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberAddressEntity> memberAddressPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<MemberAddressDto> pageDto = new Page(memberAddressPage.getCurrent(), memberAddressPage.getSize(), memberAddressPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberAddressPage.getRecords(), MemberAddressDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberAddressDto> pageMemberAddressExtends(Page<MemberAddressDto> page, MemberAddressDto memberAddressDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberAddressEntity memberAddressEntity = ConvertUtil.transformObj(memberAddressDto, MemberAddressEntity.class);
        List<MemberAddressEntity> memberAddressList = baseMapper.extendsList(pageMap, memberAddressEntity);
        Long total = baseMapper.extendsCount(memberAddressEntity);
        Page<MemberAddressDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberAddressList, MemberAddressDto.class));
        return pageDto;
    }

    @Override
    public MemberAddressDto getByIdExtends(Long id) {
        MemberAddressEntity memberAddressEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberAddressEntity, MemberAddressDto.class);
    }

    @Override
    public List<MemberAddressDto> listMemberAddress(MemberAddressDto memberAddressDto) {
        QueryWrapper<MemberAddressEntity> wrapper = getWrapper(memberAddressDto);
        List<MemberAddressEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, MemberAddressDto.class);
    }

    @Override
    public MemberAddressEntity changeDefaultAddress(MemberAddressEntity memberAddressEntity, Integer isDefault) {
        //其他地址全部设置为2 然后 当前地址设置为1
        if (YesNoEnum.YES.getCode().equals(isDefault)) {
            UpdateWrapper<MemberAddressEntity> memberAddressEntityQueryWrapper = new UpdateWrapper<>();
            memberAddressEntityQueryWrapper.eq("member_id", memberAddressEntity.getMemberId());
            memberAddressEntityQueryWrapper.set("is_default", 2);
            update(memberAddressEntityQueryWrapper);
            memberAddressEntity.setIsDefault(1);
            updateById(memberAddressEntity);
        } else {
            //当前地址设置为2即可
            memberAddressEntity.setIsDefault(2);
            updateById(memberAddressEntity);
        }
        return memberAddressEntity;
    }
}

