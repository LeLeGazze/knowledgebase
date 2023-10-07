package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.system.entity.FeedbackEntity;
import com.castle.fortress.admin.system.dto.FeedbackDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.FeedbackService;
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 意见反馈 api 控制器
 *
 * @author castle
 * @since 2022-11-01
 */
@Api(tags = "意见反馈api管理控制器")
@RestController
@RequestMapping("/api/system/feedback")
public class ApiFeedbackController {
    @Autowired
    private FeedbackService feedbackService;


    /**
     * 意见反馈的分页展示
     *
     * @param feedbackDto 意见反馈实体类
     * @param currentPage 当前页
     * @param size        每页记录数
     * @return
     */
    @ApiOperation("意见反馈分页展示")
    @GetMapping("/page")
    public RespBody<IPage<FeedbackDto>> pageFeedback(FeedbackDto feedbackDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false) Integer size) {
        Integer pageIndex = currentPage == null ? GlobalConstants.DEFAULT_PAGE_INDEX : currentPage;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<FeedbackDto> page = new Page(pageIndex, pageSize);

        IPage<FeedbackDto> pages = feedbackService.pageFeedback(page, feedbackDto);
        return RespBody.data(pages);
    }

    /**
     * 意见反馈保存
     *
     * @param feedbackDto 意见反馈实体类
     * @return
     */
    @ApiOperation("意见反馈保存")
    @PostMapping("/save")
    public RespBody<String> saveFeedback(@RequestBody FeedbackDto feedbackDto) {
        if (feedbackDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        final MemberDto memberDto = WebUtil.currentMember();
        if (memberDto == null) {
            throw new ErrorException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        if (StringUtils.isBlank(feedbackDto.getContent())) {
            throw new BizException("内容不可为空");
        }
        FeedbackEntity feedbackEntity = ConvertUtil.transformObj(feedbackDto, FeedbackEntity.class);
        if (feedbackEntity.getContactWay() == null || "".equals(feedbackEntity.getContactWay())) {
            //获取当前用户联系方式
            feedbackEntity.setContactWay(memberDto.getPhone());
        }
        feedbackEntity.setMemberId(memberDto.getId());
        if (feedbackService.save(feedbackEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 意见反馈编辑
     *
     * @param feedbackDto 意见反馈实体类
     * @return
     */
    @ApiOperation("意见反馈编辑")
    @PostMapping("/edit")
    public RespBody<String> updateFeedback(@RequestBody FeedbackDto feedbackDto) {
        if (feedbackDto == null || feedbackDto.getId() == null || feedbackDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        FeedbackEntity feedbackEntity = ConvertUtil.transformObj(feedbackDto, FeedbackEntity.class);
        if (feedbackService.updateById(feedbackEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 意见反馈删除
     *
     * @param ids 意见反馈id集合
     * @return
     */
    @ApiOperation("意见反馈删除")
    @PostMapping("/delete")
    public RespBody<String> deleteFeedback(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (feedbackService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 意见反馈详情
     *
     * @param id 意见反馈id
     * @return
     */
    @ApiOperation("意见反馈详情")
    @GetMapping("/info")
    public RespBody<FeedbackDto> infoFeedback(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        FeedbackEntity feedbackEntity = feedbackService.getById(id);
        FeedbackDto feedbackDto = ConvertUtil.transformObj(feedbackEntity, FeedbackDto.class);
        return RespBody.data(feedbackDto);
    }


}
