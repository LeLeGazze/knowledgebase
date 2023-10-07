package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberPointsDto;
import com.castle.fortress.admin.member.entity.MemberPointsEntity;
import com.castle.fortress.admin.member.enums.PointSerialCategoryEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员积分表 服务类
 *
 * @author Mgg
 * @since 2021-11-27
 */
public interface MemberPointsService extends IService<MemberPointsEntity> {

    /**
     * 分页展示会员积分表列表
     * @param page
     * @param memberPointsDto
     * @return
     */
    IPage<MemberPointsDto> pageMemberPoints(Page<MemberPointsDto> page, MemberPointsDto memberPointsDto);


    /**
     * 展示会员积分表列表
     * @param memberPointsDto
     * @return
     */
    List<MemberPointsDto> listMemberPoints(MemberPointsDto memberPointsDto);

    /**
     * 给用户增加积分 并写入流水
     * @param memberId
     * @param points
     * @param category
     * @param memo
     */
    void incPoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo);
    void incPoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo,String orderId);

    /**
     * 给用户减少积分 并写入流水
     * @param memberId
     * @param points
     * @param category
     * @param memo
     */
    void decPoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo);
    void decPoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo,String orderId);

    /**
     * 直接修改用户的积分余额为 传入数值
     * @param memberId
     * @param points
     * @param category
     * @param memo
     */
    void updatePoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo);
    void updatePoint(Long memberId, BigDecimal points, PointSerialCategoryEnum category, String memo,String orderId);
}
