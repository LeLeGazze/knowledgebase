package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbCommentEntity;
import com.castle.fortress.admin.knowledge.dto.KbCommentDto;
import com.castle.fortress.admin.knowledge.service.KbBasicService;
import com.castle.fortress.admin.knowledge.service.KbCommentService;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;

import javax.servlet.http.HttpServletResponse;

import com.castle.fortress.common.entity.DynamicExcelEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识评论管理表 控制器
 *
 * @author sunhr
 * @since 2023-05-09
 */
@Api(tags = "知识评论管理表管理控制器")
@Controller
public class KbCommentController {
    @Autowired
    private KbCommentService kbCommentService;
    @Autowired
    private KbBasicService kbBasicService;

    /**
     * 知识评论管理表的分页展示
     *
     * @param kbCommentDto 知识评论管理表实体类
     * @param current      当前页
     * @param size         每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识评论管理表分页展示")
    @GetMapping("/knowledge/kbComment/page")
    @ResponseBody
    public RespBody<IPage<KbCommentDto>> pageKbComment(KbCommentDto kbCommentDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbCommentDto> page = new Page(pageIndex, pageSize);
        IPage<KbCommentDto> pages = kbCommentService.pageKbComment(page, kbCommentDto);

        return RespBody.data(pages);
    }

    /**
     * 知识评论管理表的列表展示
     *
     * @param newsId 知识评论管理表实体类
     * @return
     */
    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识评论管理表列表展示")
    @GetMapping("/knowledge/kbComment/list")
    @ResponseBody
    public RespBody<List<KbCommentDto>> listKbComment(@RequestParam("newsId") Long newsId) {
        List<KbCommentDto> list = kbCommentService.listKbComment(newsId);
        return RespBody.data(list);
    }

    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识评论管理表列表展示")
    @GetMapping("/knowledge/kbComment/findByParentId")
    @ResponseBody
    public RespBody<IPage<KbCommentDto>> findByParentId(@RequestParam("id") Long id ,@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbCommentDto> page = new Page(pageIndex, pageSize);
        IPage<KbCommentDto> ids = kbCommentService.selectSon(page,id);
        return RespBody.data(ids);
    }

    /**
     * 知识评论管理表保存
     *
     * @param kbCommentDto 知识评论管理表实体类
     * @return
     */
    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识评论管理表保存")
    @PostMapping("/knowledge/kbComment/save")
    @ResponseBody
    public RespBody<String> saveKbComment(@RequestBody KbCommentDto kbCommentDto) {
        if (kbCommentDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        Long basicId = kbCommentDto.getBasicId();
        KbBasicEntity byId = kbBasicService.getById(basicId);
        if (byId != null) {
            kbCommentDto.setStatus(1);
        } else {
            kbCommentDto.setStatus(2);
        }
        boolean save = kbCommentService.save(kbCommentDto);
        if (save) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识评论管理表编辑
     *
     * @param kbCommentDto 知识评论管理表实体类
     * @return
     */
    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识评论管理表编辑")
    @PostMapping("/knowledge/kbComment/edit")
    @ResponseBody
    public RespBody<String> updateKbComment(@RequestBody KbCommentDto kbCommentDto) {
        if (kbCommentDto == null || kbCommentDto.getId() == null || kbCommentDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCommentEntity kbCommentEntity = ConvertUtil.transformObj(kbCommentDto, KbCommentEntity.class);
        if (kbCommentService.updateById(kbCommentEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识评论管理表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识评论管理表删除")
    @DeleteMapping("/knowledge/kbComment/delete")
    @ResponseBody
    public RespBody<String> deleteKbComment(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCommentEntity byId = kbCommentService.getById(id);
        Long pId = null;
        if (byId.getOneId() != null) {
            pId = byId.getOneId();
        }
        byId.setIsDeleted(1);
        if (kbCommentService.updateById(byId)) {
            if(pId!=null){
                KbCommentDto commentOne = kbCommentService.getCommentOne(pId);
                if (commentOne == null) {
                    KbCommentEntity byId1 = kbCommentService.getById(pId);
                    byId1.setIsReply(2);
                    kbCommentService.updateById(byId1);
                }
            }
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识评论管理表批量删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识评论管理表批量删除")
    @DeleteMapping("/knowledge/kbComment/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbCommentBatch(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }

        List<Long> ids = kbCommentService.selectSonId(id);
        ids.add(id);
        if (kbCommentService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识评论管理表详情
     *
     * @param id 知识评论管理表id
     * @return
     */
    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识评论管理表详情")
    @GetMapping("/knowledge/kbComment/info")
    @ResponseBody
    public RespBody<KbCommentDto> infoKbComment(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCommentEntity kbCommentEntity = kbCommentService.getById(id);
        KbCommentDto kbCommentDto = ConvertUtil.transformObj(kbCommentEntity, KbCommentDto.class);

        return RespBody.data(kbCommentDto);
    }


    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识评论管理表详情")
    @GetMapping("/knowledge/kbComment/pageKbComment")
    @ResponseBody
    public RespBody<List<KbCommentDto>> pageOne(@RequestParam Long basicId, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        return RespBody.data(kbCommentService.selectCommentById(basicId, pageIndex, pageSize));
    }


    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识评论管理表详情")
    @GetMapping("/knowledge/kbComment/findSonComment")
    @ResponseBody
    public RespBody<List<KbCommentDto>> commentInfo(@RequestParam Long id) {
        KbCommentEntity byId = kbCommentService.getById(id);
        KbCommentDto kbCommentDto = ConvertUtil.transformObj(byId, KbCommentDto.class);
        List<KbCommentDto> list = new ArrayList<>();
        list.add(kbCommentDto);
        return RespBody.data(kbCommentService.getSons(list));
    }


    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("累计评论次数")
    @GetMapping("/knowledge/kbComment/commentCount")
    @ResponseBody
    public RespBody<Integer> commentCount() {
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        if (userId == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        return RespBody.data(kbCommentService.getCount(userId));
    }


    @CastleLog(operLocation = "知识评论管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("最近评论")
    @GetMapping("/knowledge/kbComment/recentComment")
    @ResponseBody
    public RespBody<IPage<KbModelTransmitDto>> recentComments(KbBaseShowDto kbBaseShowDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelTransmitDto> page = new Page(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        kbBaseShowDto.setCreateUser(userId);
        if (userId == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        return RespBody.data(kbCommentService.recentComments(page, kbBaseShowDto));
    }


}
