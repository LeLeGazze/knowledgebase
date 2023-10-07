package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.dto.KbVideVersionDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicUserEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicUserDto;
import com.castle.fortress.admin.knowledge.service.KbBasicUserService;
import com.castle.fortress.admin.knowledge.service.KbVideVersionService;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.tencentcloudapi.vpc.v20170312.models.Ip6Rule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.common.io.stream.NamedWriteable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;

import javax.servlet.http.HttpServletResponse;

import com.castle.fortress.common.entity.DynamicExcelEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 知识浏览收藏评论 控制器
 *
 * @author
 * @since 2023-05-05
 */
@Api(tags = "知识浏览收藏评论管理控制器")
@Controller
public class KbBasicUserController {
    @Autowired
    private KbBasicUserService kbBasicUserService;


    /**
     * 知识浏览收藏评论的分页展示
     *
     * @param kbBasicUserDto 知识浏览收藏评论实体类
     * @param current        当前页
     * @param size           每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识浏览收藏评论分页展示")
    @GetMapping("/knowledge/kbBasicUser/page")
    @ResponseBody
    public RespBody<IPage<KbBasicUserDto>> pageKbBasicUser(KbBasicUserDto kbBasicUserDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbBasicUserDto> page = new Page(pageIndex, pageSize);
        IPage<KbBasicUserDto> pages = kbBasicUserService.pageKbBasicUser(page, kbBasicUserDto);

        return RespBody.data(pages);
    }

    /**
     * 知识浏览收藏评论的列表展示
     *
     * @param kbBasicUserDto 知识浏览收藏评论实体类
     * @return
     */
    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识浏览收藏评论列表展示")
    @GetMapping("/knowledge/kbBasicUser/list")
    @ResponseBody
    public RespBody<List<KbBasicUserDto>> listKbBasicUser(KbBasicUserDto kbBasicUserDto) {
        List<KbBasicUserDto> list = kbBasicUserService.listKbBasicUser(kbBasicUserDto);
        return RespBody.data(list);
    }

    /**
     * 知识浏览收藏评论保存
     *
     * @param kbBasicUserDto 知识浏览收藏评论实体类
     * @return
     */
    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识浏览收藏评论保存")
    @PostMapping("/knowledge/kbBasicUser/save")
    @ResponseBody
    public RespBody<String> saveKbBasicUser(@RequestBody KbBasicUserDto kbBasicUserDto) {
        if (kbBasicUserDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicUserEntity kbBasicUserEntity = ConvertUtil.transformObj(kbBasicUserDto, KbBasicUserEntity.class);
        if (kbBasicUserService.save(kbBasicUserEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识浏览收藏评论编辑
     *
     * @param kbBasicUserDto 知识浏览收藏评论实体类
     * @return
     */
    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识浏览收藏评论编辑")
    @PostMapping("/knowledge/kbBasicUser/edit")
    @ResponseBody
    public RespBody<String> updateKbBasicUser(@RequestBody KbBasicUserDto kbBasicUserDto) {
        if (kbBasicUserDto == null || kbBasicUserDto.getId() == null || kbBasicUserDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicUserEntity kbBasicUserEntity = ConvertUtil.transformObj(kbBasicUserDto, KbBasicUserEntity.class);
        if (kbBasicUserService.updateById(kbBasicUserEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识浏览收藏评论删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识浏览收藏评论删除")
    @PostMapping("/knowledge/kbBasicUser/delete")
    @ResponseBody
    public RespBody<String> deleteKbBasicUser(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicUserService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识浏览收藏评论批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识浏览收藏评论批量删除")
    @PostMapping("/knowledge/kbBasicUser/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbBasicUserBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicUserService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识浏览收藏评论详情
     *
     * @param id 知识浏览收藏评论id
     * @return
     */
    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识浏览收藏评论详情")
    @GetMapping("/knowledge/kbBasicUser/info")
    @ResponseBody
    public RespBody<KbBasicUserDto> infoKbBasicUser(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicUserEntity kbBasicUserEntity = kbBasicUserService.getById(id);
        KbBasicUserDto kbBasicUserDto = ConvertUtil.transformObj(kbBasicUserEntity, KbBasicUserDto.class);

        return RespBody.data(kbBasicUserDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbBasicUser/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbBasicUserDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbBasicUserDto> list = kbBasicUserService.listKbBasicUser(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }

    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("上传数量")
    @GetMapping("/knowledge/kbBasicUser/uploadNums")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbBasicUser:info")
    public RespBody<KbModelTransmitDto> upLoadNums() {
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        if (userId == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelTransmitDto maps = kbBasicUserService.selectUpCount();
        return RespBody.data(maps);
    }



    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("下载数量")
    @GetMapping("/knowledge/kbBasicUser/downloadNums")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbBasicUser:info")
    public RespBody<KbModelTransmitDto> downloadNums() {
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        if (userId == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelTransmitDto maps = kbBasicUserService.selectDownCount(userId);
        return RespBody.data(maps);
    }

    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("最近浏览")
    @GetMapping("/knowledge/kbBasicUser/recentViews")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbBasicUser:info")
    public RespBody<IPage<KbModelTransmitDto>> recentViews(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelTransmitDto> page = new Page<>(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        if (userId == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        IPage<KbModelTransmitDto> kbBasicEntity = kbBasicUserService.recentViews(page, userId);
        return RespBody.data(kbBasicEntity);
    }


    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("最近上传")
    @GetMapping("/knowledge/kbBasicUser/recentlyUploaded")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbBasicUser:info")
    public RespBody<IPage<KbModelTransmitDto>> recentlyUploaded(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelTransmitDto> page = new Page(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        if (userId == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        IPage<KbModelTransmitDto> kbBasicEntity = kbBasicUserService.recentlyUploaded(page, userId);
        return RespBody.data(kbBasicEntity);
    }


    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("最近下载")
    @GetMapping("/knowledge/kbBasicUser/recentlyDownloaded")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbBasicUser:info")
    public RespBody<IPage<KbModelTransmitDto>> recentlyDownloaded(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelTransmitDto> page = new Page(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        if (userId == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        IPage<KbModelTransmitDto> kbBasicEntity = kbBasicUserService.recentlyDownloaded(page, userId);
        return RespBody.data(kbBasicEntity);
    }


    @CastleLog(operLocation = "知识浏览收藏评论", operType = OperationTypeEnum.QUERY)
    @ApiOperation("智能推荐")
    @GetMapping("/knowledge/kbBasicUser/intelligentRecommendation")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbBasicUser:info")
    public RespBody<List<KbModelTransmitDto>> intelligentRecommendation() {
        List<KbModelTransmitDto> kbBasicEntity = kbBasicUserService.randomId();
        return RespBody.data(kbBasicEntity);
    }




}
