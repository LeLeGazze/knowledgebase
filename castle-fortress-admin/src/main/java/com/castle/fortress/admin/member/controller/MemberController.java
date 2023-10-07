package com.castle.fortress.admin.member.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.member.entity.MemberEntity;
import com.castle.fortress.admin.member.entity.MemberTagEntity;
import com.castle.fortress.admin.member.event.UpdateMemberTagSortEvent;
import com.castle.fortress.admin.member.service.MemberExtendFieldConfigService;
import com.castle.fortress.admin.member.service.MemberService;
import com.castle.fortress.admin.member.service.MemberTagService;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会员表 控制器
 *
 * @author Mgg
 * @since 2021-11-25
 */
@Api(tags = "会员表管理控制器")
@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberTagService memberTagService;

    @Autowired
    private MemberExtendFieldConfigService memberExtendFieldConfigService;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 会员表的分页展示
     *
     * @param memberDto 会员表实体类
     * @param current   当前页
     * @param size      每页记录数
     * @return
     */
    @CastleLog(operLocation = "会员分页", operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员表分页展示")
    @GetMapping("/member/member/page")
    @ResponseBody
    @RequiresPermissions("member:member:pageList")
    public RespBody<IPage<MemberDto>> pageMember(MemberDto memberDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<MemberDto> page = new Page(pageIndex, pageSize);
        if (memberDto.getStartTime() != null) {
            memberDto.setStartTime(DateUtil.beginOfDay(memberDto.getStartTime()).toJdkDate());
        }
        if (memberDto.getEndTime() != null) {
            memberDto.setEndTime(DateUtil.endOfDay(memberDto.getEndTime()).toJdkDate());
        }
        System.out.println(memberDto);
        IPage<MemberDto> pages = memberService.pageMemberExtends(page, memberDto);

        return RespBody.data(pages);
    }

    /**
     * 会员表的列表展示
     *
     * @param memberDto 会员表实体类
     * @return
     */
    @CastleLog(operLocation = "会员列表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员表列表展示")
    @GetMapping("/member/member/list")
    @ResponseBody
    public RespBody<List<MemberDto>> listMember(MemberDto memberDto) {
        List<MemberDto> list = memberService.listMember(memberDto);
        return RespBody.data(list);
    }

    /**
     * 会员表保存
     *
     * @param memberDto 会员表实体类
     * @return
     */
    @CastleLog(operLocation = "会员新增", operType = OperationTypeEnum.INSERT)
    @ApiOperation("会员表保存")
    @PostMapping("/member/member/save")
    @ResponseBody
    @RequiresPermissions("member:member:save")
    public RespBody<String> saveMember(@RequestBody MemberDto memberDto) {
        if (memberDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberEntity memberEntity = ConvertUtil.transformObj(memberDto, MemberEntity.class);
        if (memberService.save(memberEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }



    /**
     * 会员表编辑
     *
     * @param memberDto 会员表实体类
     * @return
     */
    @CastleLog(operLocation = "会员编辑", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("会员表编辑")
    @PostMapping("/member/member/edit")
    @ResponseBody
    @RequiresPermissions("member:member:edit")
    public RespBody<String> updateMember(@RequestBody MemberDto memberDto) {
        if (memberDto == null || memberDto.getId() == null || memberDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberEntity memberEntity = ConvertUtil.transformObj(memberDto, MemberEntity.class);
        //更新用户的标签
        if (memberEntity.getTags() != null && !"".equals(memberEntity.getTags())) {

            StringBuilder tagRes = new StringBuilder("");
            if (memberEntity.getTags() != null && !"".equals(memberEntity.getTags())) {
                final List<String> tags = new ArrayList<>(Arrays.asList(memberEntity.getTags().split(",")));
                final QueryWrapper<MemberTagEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("id", tags);
                final List<MemberTagEntity> existsList = memberTagService.list(queryWrapper);
                //获取id和现有的差集
                final List<String> existsIds = existsList.stream().map(item -> {
                    return item.getId() + "";
                }).collect(Collectors.toList());
                tags.removeAll(existsIds);
                existsIds.forEach(ids -> {
                    tagRes.append(ids);
                    tagRes.append(",");
                });
                tags.forEach(item -> {
                    final MemberTagEntity temp = new MemberTagEntity();
                    temp.setName(item);
                    memberTagService.save(temp);
                    tagRes.append(temp.getId());
                    tagRes.append(",");
                });
            }
            tagRes.delete(tagRes.length() - 1, tagRes.length());
            memberEntity.setTags(tagRes.toString());
            publisher.publishEvent(new UpdateMemberTagSortEvent(this, memberService.getById(memberDto.getId()).getTags(), tagRes.toString()));
        }

        if (memberService.updateById(memberEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "会员删除", operType = OperationTypeEnum.DELETE)
    @ApiOperation("会员表删除")
    @PostMapping("/member/member/delete")
    @ResponseBody
    @RequiresPermissions("member:member:delete")
    public RespBody<String> deleteMember(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (memberService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "会员批量删除", operType = OperationTypeEnum.DELETE)
    @ApiOperation("会员表批量删除")
    @PostMapping("/member/member/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:member:deleteBatch")
    public RespBody<String> deleteMemberBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (memberService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员表详情
     *
     * @param id 会员表id
     * @return
     */
    @CastleLog(operLocation = "会员详情", operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员表详情")
    @GetMapping("/member/member/info")
    @ResponseBody
    @RequiresPermissions("member:member:info")
    public RespBody<MemberDto> infoMember(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberDto memberDto = memberService.getByIdExtends(id);

        return RespBody.data(memberDto);
    }

    /**
     * 会员扩展详情
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "会员扩展详情", operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员表扩展详情")
    @GetMapping("/member/member/extendInfo")
    @ResponseBody
    @RequiresPermissions("member:member:extendInfo")
    public RespBody<List<HashMap<String, String>>> extendInfoMember(@RequestParam Long id) {
        //避免出现返回空数组的情况 查询前 先新增
        memberExtendFieldConfigService.updateOrCreateByMemberId(id, null);
        return RespBody.data(memberExtendFieldConfigService.extendInfoMember(id));
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "会员导出", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/member/member/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<MemberDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<MemberDto> list = memberService.listMember(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
