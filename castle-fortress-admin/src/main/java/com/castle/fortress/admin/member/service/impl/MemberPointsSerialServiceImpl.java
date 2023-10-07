package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberPointsSerialDto;
import com.castle.fortress.admin.member.entity.MemberPointsSerialEntity;
import com.castle.fortress.admin.member.enums.PointSerialCategoryEnum;
import com.castle.fortress.admin.member.enums.PointSerialTypeEnum;
import com.castle.fortress.admin.member.mapper.MemberPointsSerialMapper;
import com.castle.fortress.admin.member.service.MemberPointsSerialService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员积分流水 服务实现类
 *
 * @author Mgg
 * @since 2021-12-01
 */
@Service
public class MemberPointsSerialServiceImpl extends ServiceImpl<MemberPointsSerialMapper, MemberPointsSerialEntity> implements MemberPointsSerialService {
    /**
     * 获取查询条件
     *
     * @param memberPointsSerialDto
     * @return
     */
    public QueryWrapper<MemberPointsSerialEntity> getWrapper(MemberPointsSerialDto memberPointsSerialDto) {
        QueryWrapper<MemberPointsSerialEntity> wrapper = new QueryWrapper();
        if (memberPointsSerialDto != null) {
            MemberPointsSerialEntity memberPointsSerialEntity = ConvertUtil.transformObj(memberPointsSerialDto, MemberPointsSerialEntity.class);
            wrapper.like(memberPointsSerialEntity.getId() != null, "id", memberPointsSerialEntity.getId());
            wrapper.like(memberPointsSerialEntity.getMemberId() != null, "member_id", memberPointsSerialEntity.getMemberId());
            wrapper.like(StrUtil.isNotEmpty(memberPointsSerialEntity.getPoints()), "points", memberPointsSerialEntity.getPoints());
            wrapper.like(memberPointsSerialEntity.getType() != null, "type", memberPointsSerialEntity.getType());
            wrapper.like(memberPointsSerialEntity.getCategory() != null, "category", memberPointsSerialEntity.getCategory());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<MemberPointsSerialDto> pageMemberPointsSerial(Page<MemberPointsSerialDto> page, MemberPointsSerialDto memberPointsSerialDto) {
        QueryWrapper<MemberPointsSerialEntity> wrapper = getWrapper(memberPointsSerialDto);
        Page<MemberPointsSerialEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberPointsSerialEntity> memberPointsSerialPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<MemberPointsSerialDto> pageDto = new Page(memberPointsSerialPage.getCurrent(), memberPointsSerialPage.getSize(), memberPointsSerialPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberPointsSerialPage.getRecords(), MemberPointsSerialDto.class));
        return pageDto;
    }

    @Override
    public IPage<MemberPointsSerialDto> pageMemberPointsSerialExtends(Page<MemberPointsSerialDto> page, MemberPointsSerialDto memberPointsSerialDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberPointsSerialEntity memberPointsSerialEntity = ConvertUtil.transformObj(memberPointsSerialDto, MemberPointsSerialEntity.class);
        List<MemberPointsSerialEntity> memberPointsSerialList = baseMapper.extendsList(pageMap, memberPointsSerialEntity);
        Long total = baseMapper.extendsCount(memberPointsSerialEntity);
        Page<MemberPointsSerialDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(memberPointsSerialList, MemberPointsSerialDto.class));
        return pageDto;
    }

    @Override
    public MemberPointsSerialDto getByIdExtends(Long id) {
        MemberPointsSerialEntity memberPointsSerialEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(memberPointsSerialEntity, MemberPointsSerialDto.class);
    }

    @Override
    public List<MemberPointsSerialDto> listMemberPointsSerial(MemberPointsSerialDto memberPointsSerialDto) {
        QueryWrapper<MemberPointsSerialEntity> wrapper = getWrapper(memberPointsSerialDto);
        List<MemberPointsSerialEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, MemberPointsSerialDto.class);
    }

    @Override
    public void insertLog(PointSerialTypeEnum type, Long memberId, Long pointId, String points, String accountPoints, PointSerialCategoryEnum category, String memo) {
        this.insertLog(type, memberId, pointId, points, accountPoints, category, memo, null);
    }

    @Override
    public void insertLog(PointSerialTypeEnum type, Long memberId, Long pointId, String points, String accountPoints, PointSerialCategoryEnum category, String memo, String orderId) {
        final MemberPointsSerialEntity temp = new MemberPointsSerialEntity();
        temp.setMemberId(memberId);
        temp.setMemberPointsId(pointId);
        temp.setPoints(points);
        temp.setType(type.getCode());
        temp.setCategory(category.getCode());
        temp.setMemo(category.getDesc() + " " + memo);
        temp.setOrderId(orderId);
        temp.setAccountPoints(accountPoints);
        save(temp);
    }
}

