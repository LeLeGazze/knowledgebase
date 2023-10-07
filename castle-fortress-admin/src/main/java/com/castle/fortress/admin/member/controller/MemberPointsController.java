package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberPointsDto;
import com.castle.fortress.admin.member.entity.MemberPointsEntity;
import com.castle.fortress.admin.member.enums.PointSerialCategoryEnum;
import com.castle.fortress.admin.member.enums.PointSerialTypeEnum;
import com.castle.fortress.admin.member.service.MemberPointsService;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 会员积分表 控制器
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Api(tags = "会员积分表管理控制器")
@Controller
public class MemberPointsController {
    @Autowired
    private MemberPointsService memberPointsService;

    /**
     * 会员积分表的分页展示
     *
     * @param memberPointsDto 会员积分表实体类
     * @param current         当前页
     * @param size            每页记录数
     * @return
     */
    @ApiOperation("会员积分表分页展示")
    @GetMapping("/member/memberPoints/page")
    @ResponseBody
    @RequiresPermissions("member:memberPoints:pageList")
    public RespBody<IPage<MemberPointsDto>> pageMemberPoints(MemberPointsDto memberPointsDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<MemberPointsDto> page = new Page(pageIndex, pageSize);
        IPage<MemberPointsDto> pages = memberPointsService.pageMemberPoints(page, memberPointsDto);

        return RespBody.data(pages);
    }

    /**
     * 会员积分表的列表展示
     *
     * @param memberPointsDto 会员积分表实体类
     * @return
     */
    @ApiOperation("会员积分表列表展示")
    @GetMapping("/member/memberPoints/list")
    @ResponseBody
    public RespBody<List<MemberPointsDto>> listMemberPoints(MemberPointsDto memberPointsDto) {
        List<MemberPointsDto> list = memberPointsService.listMemberPoints(memberPointsDto);
        return RespBody.data(list);
    }

    /**
     * 会员积分表保存
     *
     * @param memberPointsDto 会员积分表实体类
     * @return
     */
    @ApiOperation("会员积分表保存")
    @PostMapping("/member/memberPoints/save")
    @ResponseBody
    @RequiresPermissions("member:memberPoints:save")
    public RespBody<String> saveMemberPoints(@RequestBody MemberPointsDto memberPointsDto) {
        if (memberPointsDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberPointsEntity memberPointsEntity = ConvertUtil.transformObj(memberPointsDto, MemberPointsEntity.class);
        if (memberPointsService.save(memberPointsEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分表编辑
     *
     * @param memberPointsDto 会员积分表实体类
     * @return
     */
    @ApiOperation("会员积分表编辑")
    @PostMapping("/member/memberPoints/edit")
    @ResponseBody
    @RequiresPermissions("member:memberPoints:edit")
    public RespBody<String> updateMemberPoints(@RequestBody MemberPointsDto memberPointsDto) {
        if (memberPointsDto == null || memberPointsDto.getId() == null || memberPointsDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberPointsEntity memberPointsEntity = ConvertUtil.transformObj(memberPointsDto, MemberPointsEntity.class);
        if (memberPointsService.updateById(memberPointsEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分表删除
     *
     * @param id
     * @return
     */
    @ApiOperation("会员积分表删除")
    @PostMapping("/member/memberPoints/delete")
    @ResponseBody
    @RequiresPermissions("member:memberPoints:delete")
    public RespBody<String> deleteMemberPoints(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (memberPointsService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员积分表批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("会员积分表批量删除")
    @PostMapping("/member/memberPoints/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberPoints:deleteBatch")
    public RespBody<String> deleteMemberPointsBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (memberPointsService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分表详情
     *
     * @param id 会员积分表id
     * @return
     */
    @ApiOperation("会员积分表详情")
    @GetMapping("/member/memberPoints/info")
    @ResponseBody
    @RequiresPermissions("member:memberPoints:info")
    public RespBody<MemberPointsDto> infoMemberPoints(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberPointsEntity memberPointsEntity = memberPointsService.getById(id);
        MemberPointsDto memberPointsDto = ConvertUtil.transformObj(memberPointsEntity, MemberPointsDto.class);

        return RespBody.data(memberPointsDto);
    }


    /**
     * 根据memberId获取积分账户
     * @param memberId
     * @return
     */
    @ApiOperation("会员积分表详情")
    @GetMapping("/member/memberPoints/infoByMemberId")
    @ResponseBody
    public RespBody<MemberPointsDto> infoMemberPointsByMemberId(@RequestParam Long memberId) {
        if (memberId == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        final QueryWrapper<MemberPointsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.last("limit 1");
        MemberPointsEntity memberPointsEntity = memberPointsService.getOne(queryWrapper);
        MemberPointsDto memberPointsDto = ConvertUtil.transformObj(memberPointsEntity, MemberPointsDto.class);

        return RespBody.data(memberPointsDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @PostMapping("/member/memberPoints/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<MemberPointsDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<MemberPointsDto> list = memberPointsService.listMemberPoints(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


    /**
     * 修改/增加/减少用户积分
     * @param params
     * @return
     */
    @ApiOperation("修改用户积分")
    @PostMapping("/member/memberPoints/changePoint")
    @ResponseBody
    @RequiresPermissions("member:memberPoints:changePoint")
    public RespBody<String> changePoint(@RequestBody HashMap<String, String> params) {
        final PointSerialCategoryEnum category = PointSerialCategoryEnum.ADMIN_INC;
        final BigDecimal points = new BigDecimal(params.get("point"));
        Long memberId = Long.valueOf(params.get("memberId"));
        String memo = params.get("memo");
        final Integer type = Integer.valueOf(params.get("type"));
        if (PointSerialTypeEnum.INC.getCode().equals(type)) {
            memberPointsService.incPoint(memberId, points, category, memo);
        } else if (PointSerialTypeEnum.DEC.getCode().equals(type)) {
            memberPointsService.decPoint(memberId, points, category, memo);
        } else {
            memberPointsService.updatePoint(memberId, points, category, memo);
        }
        return RespBody.data("操作成功");
    }


}
