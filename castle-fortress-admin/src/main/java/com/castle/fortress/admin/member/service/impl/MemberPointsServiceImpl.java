package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.member.dto.MemberPointsDto;
import com.castle.fortress.admin.member.entity.MemberPointsEntity;
import com.castle.fortress.admin.member.enums.PointSerialCategoryEnum;
import com.castle.fortress.admin.member.enums.PointSerialTypeEnum;
import com.castle.fortress.admin.member.mapper.MemberPointsMapper;
import com.castle.fortress.admin.member.mapper.MemberPointsSerialMapper;
import com.castle.fortress.admin.member.service.MemberPointsSerialService;
import com.castle.fortress.admin.member.service.MemberPointsService;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员积分表 服务实现类
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Service
public class MemberPointsServiceImpl extends ServiceImpl<MemberPointsMapper, MemberPointsEntity> implements MemberPointsService {

    @Autowired
    private MemberPointsSerialService memberPointsSerialService;


    /**
     * 获取查询条件
     *
     * @param memberPointsDto
     * @return
     */
    public QueryWrapper<MemberPointsEntity> getWrapper(MemberPointsDto memberPointsDto) {
        QueryWrapper<MemberPointsEntity> wrapper = new QueryWrapper();
        if (memberPointsDto != null) {
            MemberPointsEntity memberPointsEntity = ConvertUtil.transformObj(memberPointsDto, MemberPointsEntity.class);
            wrapper.like(memberPointsEntity.getId() != null, "id", memberPointsEntity.getId());
            wrapper.like(memberPointsEntity.getMemberId() != null, "member_id", memberPointsEntity.getMemberId());
            wrapper.like(StrUtil.isNotEmpty(memberPointsEntity.getBalance()), "balance", memberPointsEntity.getBalance());
            wrapper.like(StrUtil.isNotEmpty(memberPointsEntity.getFrozen()), "frozen", memberPointsEntity.getFrozen());
            wrapper.like(memberPointsEntity.getStatus() != null, "status", memberPointsEntity.getStatus());
        }
        return wrapper;
    }


    @Override
    public IPage<MemberPointsDto> pageMemberPoints(Page<MemberPointsDto> page, MemberPointsDto memberPointsDto) {
        QueryWrapper<MemberPointsEntity> wrapper = getWrapper(memberPointsDto);
        Page<MemberPointsEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<MemberPointsEntity> memberPointsPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<MemberPointsDto> pageDto = new Page(memberPointsPage.getCurrent(), memberPointsPage.getSize(), memberPointsPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(memberPointsPage.getRecords(), MemberPointsDto.class));
        return pageDto;
    }


    @Override
    public List<MemberPointsDto> listMemberPoints(MemberPointsDto memberPointsDto) {
        QueryWrapper<MemberPointsEntity> wrapper = getWrapper(memberPointsDto);
        List<MemberPointsEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, MemberPointsDto.class);
    }

    @Override
    public void incPoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo) {
        this.incPoint(memberId, points, category, memo, null);
    }

    @Override
    public void incPoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo, String orderId) {
        final QueryWrapper<MemberPointsEntity> queryMapper = new QueryWrapper<>();
        queryMapper.eq("member_id", memberId);
        queryMapper.last("limit 1");
        //查出用户的积分账户
        MemberPointsEntity memberPointsEntity = baseMapper.selectOne(queryMapper);
        if (memberPointsEntity == null) {
            throw new BizException("会员积分账户异常");
        }
        if (points.compareTo(new BigDecimal("0")) < 0) {
            throw new BizException("不可增加负数");
        }
        //然后增加
        BigDecimal nowBalance = new BigDecimal(memberPointsEntity.getBalance());
        BigDecimal newBalance = nowBalance.add(points);
        memberPointsEntity.setBalance(newBalance.toPlainString());
        baseMapper.updateById(memberPointsEntity);
        //写入流水
        if (orderId == null) {
            memberPointsSerialService.insertLog(PointSerialTypeEnum.INC, memberId, memberPointsEntity.getId(), points.toPlainString(),memberPointsEntity.getBalance(), category, memo);
            return;
        }
        memberPointsSerialService.insertLog(PointSerialTypeEnum.INC, memberId, memberPointsEntity.getId(), points.toPlainString(),memberPointsEntity.getBalance(), category, memo, orderId);
    }

    @Override
    public void decPoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo) {
        this.decPoint(memberId, points, category, memo, null);
    }

    @Override
    public void decPoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo, String orderId) {
        final QueryWrapper<MemberPointsEntity> queryMapper = new QueryWrapper<>();
        queryMapper.eq("member_id", memberId);
        queryMapper.last("limit 1");
        //查出用户的积分账户
        MemberPointsEntity memberPointsEntity = baseMapper.selectOne(queryMapper);
        if (memberPointsEntity == null) {
            throw new BizException("会员积分账户异常");
        }
        if (points.compareTo(new BigDecimal("0")) < 0) {
            throw new BizException("不可传入负数");
        }
        BigDecimal nowBalance = new BigDecimal(memberPointsEntity.getBalance());
        if (nowBalance.compareTo(points) < 0) {
            throw new BizException("会员积分账户余额不足");
        }
        BigDecimal newBalance = nowBalance.subtract(points);
        memberPointsEntity.setBalance(newBalance.toPlainString());
        baseMapper.updateById(memberPointsEntity);
        //写入流水
        if (orderId == null) {
            memberPointsSerialService.insertLog(PointSerialTypeEnum.DEC, memberId, memberPointsEntity.getId(), points.toPlainString(),memberPointsEntity.getBalance(), category, memo);
            return;
        }
        memberPointsSerialService.insertLog(PointSerialTypeEnum.DEC, memberId, memberPointsEntity.getId(), points.toPlainString(),memberPointsEntity.getBalance(), category, memo, orderId);
    }

    @Override
    public void updatePoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo) {
        this.updatePoint(memberId, points, category, memo, null);
    }

    @Override
    public void updatePoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo, String orderId) {
        final QueryWrapper<MemberPointsEntity> queryMapper = new QueryWrapper<>();
        queryMapper.eq("member_id", memberId);
        queryMapper.last("limit 1");
        //查出用户的积分账户
        MemberPointsEntity memberPointsEntity = baseMapper.selectOne(queryMapper);
        if (memberPointsEntity == null) {
            throw new BizException("会员积分账户异常");
        }
        //如果现有余额加传进来的小于0 就报错
        if (points.compareTo(new BigDecimal("0")) < 0) {
            throw new BizException("不可修改为负数");
        }
        //判断传入的数值与现在余额的关系 如果  余额<传入 那就 调用增加
        final BigDecimal balance = new BigDecimal(memberPointsEntity.getBalance());
        if (balance.compareTo(points) < 0) {
            this.incPoint(memberId, points.subtract(balance), category, memo, orderId);
        } else if (balance.compareTo(points) > 0) {
            // 余额>传入 调用减少
            this.decPoint(memberId, balance.subtract(points), category, memo, orderId);
        } else {
            System.out.println("数值没有更改 无需修改");
        }

//        memberPointsEntity.setBalance(points.toPlainString());
//        baseMapper.updateById(memberPointsEntity);
//        //写入流水
//        if (orderId == null) {
//            memberPointsSerialService.insertLog(PointSerialTypeEnum.UPDATE, memberId, memberPointsEntity.getId(), points.toPlainString(), category, memo);
//            return;
//        }
//        memberPointsSerialService.insertLog(PointSerialTypeEnum.UPDATE, memberId, memberPointsEntity.getId(), points.toPlainString(), category, memo, orderId);
    }
}

