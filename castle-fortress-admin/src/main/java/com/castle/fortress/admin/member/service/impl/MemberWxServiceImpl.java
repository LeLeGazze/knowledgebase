package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.entity.MemberWxEntity;
import com.castle.fortress.admin.member.dto.MemberWxDto;
import com.castle.fortress.admin.member.mapper.MemberWxMapper;
import com.castle.fortress.admin.member.service.MemberWxService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 微信会员表 服务实现类
 *
 * @author whc
 * @since 2022-11-28
 */
@Service
public class MemberWxServiceImpl extends ServiceImpl<MemberWxMapper, MemberWxEntity> implements MemberWxService {
    /**
     * 获取查询条件
     *
     * @param memberWxDto
     * @return
     */
    public QueryWrapper<MemberWxEntity> getWrapper(MemberWxDto memberWxDto) {
        QueryWrapper<MemberWxEntity> wrapper = new QueryWrapper();
        if (memberWxDto != null) {
            MemberWxEntity memberWxEntity = ConvertUtil.transformObj(memberWxDto, MemberWxEntity.class);
            wrapper.like(StrUtil.isNotEmpty(memberWxEntity.getNickName()), "nick_name", memberWxEntity.getNickName());
            wrapper.eq(memberWxEntity.getSex() != null, "sex", memberWxEntity.getSex());
            wrapper.like(StrUtil.isNotEmpty(memberWxEntity.getProvince()), "province", memberWxEntity.getProvince());
            wrapper.like(StrUtil.isNotEmpty(memberWxEntity.getCity()), "city", memberWxEntity.getCity());
            wrapper.like(StrUtil.isNotEmpty(memberWxEntity.getCountry()), "country", memberWxEntity.getCountry());
            wrapper.eq(memberWxEntity.getStatus() != null, "status", memberWxEntity.getStatus());
            wrapper.eq(StrUtil.isNotEmpty(memberWxEntity.getOpenId()), "open_id", memberWxEntity.getOpenId());
            wrapper.eq(StrUtil.isNotEmpty(memberWxEntity.getUnionId()), "union_id", memberWxEntity.getUnionId());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberWxDto> pageMemberWx(Page<MemberWxDto> page, MemberWxDto memberWxDto) {
        QueryWrapper<MemberWxEntity> wrapper = getWrapper(memberWxDto);
        Page<MemberWxEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberWxEntity> memberWxPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<MemberWxDto> pageDto = new Page(memberWxPage.getCurrent(), memberWxPage.getSize(), memberWxPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberWxPage.getRecords(), MemberWxDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberWxDto> pageMemberWxExtends(Page<MemberWxDto> page, MemberWxDto memberWxDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberWxEntity memberWxEntity = ConvertUtil.transformObj(memberWxDto, MemberWxEntity.class);
        List<MemberWxEntity> memberWxList = baseMapper.extendsList(pageMap, memberWxEntity);
        Long total = baseMapper.extendsCount(memberWxEntity);
        Page<MemberWxDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberWxList, MemberWxDto.class));
        return pageDto;
    }

    @Override
    public MemberWxDto getByIdExtends(Long id) {
        MemberWxEntity memberWxEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberWxEntity, MemberWxDto.class);
    }

    @Override
    public List<MemberWxDto> listMemberWx(MemberWxDto memberWxDto) {
        QueryWrapper<MemberWxEntity> wrapper = getWrapper(memberWxDto);
        List<MemberWxEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, MemberWxDto.class);
    }

    @Override
    public MemberWxEntity create(JSONObject userInfoDataJson) {
        MemberWxEntity temp = new MemberWxEntity();
        temp.setOpenId(userInfoDataJson.getStr("openid"));
        temp.setNickName(userInfoDataJson.getStr("nickname"));
        temp.setSex(userInfoDataJson.getInt("sex"));
        temp.setCity(userInfoDataJson.getStr("city"));
        temp.setProvince(userInfoDataJson.getStr("province"));
        temp.setCountry(userInfoDataJson.getStr("country"));
        temp.setHeadImgUrl(userInfoDataJson.getStr("headimgurl"));
        temp.setUnionId(userInfoDataJson.getStr("unionid"));
        baseMapper.insert(temp);
        return temp;
    }
}

