package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberPointsSerialDto;
import com.castle.fortress.admin.member.entity.MemberPointsSerialEntity;
import com.castle.fortress.admin.member.enums.PointSerialCategoryEnum;
import com.castle.fortress.admin.member.enums.PointSerialTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员积分流水 服务类
 *
 * @author Mgg
 * @since 2021-12-01
 */
public interface MemberPointsSerialService extends IService<MemberPointsSerialEntity> {

    /**
     * 分页展示会员积分流水列表
     *
     * @param page
     * @param memberPointsSerialDto
     * @return
     */
    IPage<MemberPointsSerialDto> pageMemberPointsSerial(Page<MemberPointsSerialDto> page, MemberPointsSerialDto memberPointsSerialDto);

    /**
     * 分页展示会员积分流水扩展列表
     *
     * @param page
     * @param memberPointsSerialDto
     * @return
     */
    IPage<MemberPointsSerialDto> pageMemberPointsSerialExtends(Page<MemberPointsSerialDto> page, MemberPointsSerialDto memberPointsSerialDto);

    /**
     * 会员积分流水扩展详情
     *
     * @param id 会员积分流水id
     * @return
     */
    MemberPointsSerialDto getByIdExtends(Long id);

    /**
     * 展示会员积分流水列表
     *
     * @param memberPointsSerialDto
     * @return
     */
    List<MemberPointsSerialDto> listMemberPointsSerial(MemberPointsSerialDto memberPointsSerialDto);

    /**
     * 写入积分变动流水
     * @param type
     * @param memberId
     * @param pointId
     * @param points
     * @param accountPoints
     * @param category
     * @param memo
     */
    void insertLog(PointSerialTypeEnum type, Long memberId, Long pointId, String points,String accountPoints, PointSerialCategoryEnum category, String memo);

    void insertLog(PointSerialTypeEnum type, Long memberId, Long pointId, String points,String accountPoints, PointSerialCategoryEnum category, String memo, String orderId);

}
