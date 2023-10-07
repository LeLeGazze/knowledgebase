package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.entity.KbUserLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbUserLabelDto;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.service.KbUserLabelService;
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
import java.util.Arrays;
import java.util.List;

/**
 * 用户与表签关联表 控制器
 *
 * @author
 * @since 2023-05-17
 */
@Api(tags = "用户与表签关联表管理控制器")
@Controller
public class KbUserLabelController {
    @Autowired

    private KbUserLabelService kbUserLabelService;

    /**
     * 用户与表签关联表的分页展示
     *
     * @param kbUserLabelDto 用户与表签关联表实体类
     * @param current        当前页
     * @param size           每页记录数
     * @return
     */
    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户与表签关联表分页展示")
    @GetMapping("/knowledge/kbUserLabel/page")
    @ResponseBody
    public RespBody<IPage<KbUserLabelDto>> pageKbUserLabel(KbUserLabelDto kbUserLabelDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbUserLabelDto> page = new Page(pageIndex, pageSize);
        IPage<KbUserLabelDto> pages = kbUserLabelService.pageKbUserLabel(page, kbUserLabelDto);

        return RespBody.data(pages);
    }

    /**
     * 用户与表签关联表的列表展示
     *
     * @param kbUserLabelDto 用户与表签关联表实体类
     * @return
     */
    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户与表签关联表列表展示")
    @GetMapping("/knowledge/kbUserLabel/list")
    @ResponseBody
    public RespBody<List<KbUserLabelDto>> listKbUserLabel(KbUserLabelDto kbUserLabelDto) {
        List<KbUserLabelDto> list = kbUserLabelService.listKbUserLabel(kbUserLabelDto);
        return RespBody.data(list);
    }

    /**
     * 用户与表签关联表保存
     *
     * @param ids 用户与表签关联表实体类
     * @return
     */
    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("用户与表签关联表保存")
    @PostMapping("/knowledge/kbUserLabel/save")
    @ResponseBody
    public RespBody<String> saveKbUserLabel(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        List<KbUserLabelEntity> list = new ArrayList<>();
        List<Long> id1 = kbUserLabelService.findIds(userId);
        if (id1.size() > 0) {
            boolean b = kbUserLabelService.removeByIds(id1);
            if (!b) {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        }
        for (Long id : ids) {

            KbUserLabelEntity kbUserLabelEntity = new KbUserLabelEntity();
            kbUserLabelEntity.setLabelId(id);
            kbUserLabelEntity.setUserId(userId);
            list.add(kbUserLabelEntity);

        }
        if (kbUserLabelService.saveBatch(list)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户与表签关联表编辑
     *
     * @param kbUserLabelDto 用户与表签关联表实体类
     * @return
     */
    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("用户与表签关联表编辑")
    @PostMapping("/knowledge/kbUserLabel/edit")
    @ResponseBody
    public RespBody<String> updateKbUserLabel(@RequestBody KbUserLabelDto kbUserLabelDto) {
        if (kbUserLabelDto == null || kbUserLabelDto.getId() == null || kbUserLabelDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbUserLabelEntity kbUserLabelEntity = ConvertUtil.transformObj(kbUserLabelDto, KbUserLabelEntity.class);
        if (kbUserLabelService.updateById(kbUserLabelEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户与表签关联表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户与表签关联表删除")
    @DeleteMapping("/knowledge/kbUserLabel/delete")
    @ResponseBody
    public RespBody<String> deleteKbUserLabel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbUserLabelService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 用户与表签关联表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户与表签关联表批量删除")
    @PostMapping("/knowledge/kbUserLabel/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbUserLabelBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbUserLabelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户与表签关联表详情
     *
     * @param id 用户与表签关联表id
     * @return
     */
    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户与表签关联表详情")
    @GetMapping("/knowledge/kbUserLabel/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbUserLabel:info")
    public RespBody<KbUserLabelDto> infoKbUserLabel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbUserLabelEntity kbUserLabelEntity = kbUserLabelService.getById(id);
        KbUserLabelDto kbUserLabelDto = ConvertUtil.transformObj(kbUserLabelEntity, KbUserLabelDto.class);

        return RespBody.data(kbUserLabelDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.QUERY)
    @PostMapping("/knowledge/kbUserLabel/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbUserLabelDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbUserLabelDto> list = kbUserLabelService.listKbUserLabel(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.QUERY)
    @GetMapping("/knowledge/kbUserLabel/selectBasicByLabel")
    @ApiOperation("根据订阅标签查询知识")
    @ResponseBody
    public RespBody<IPage<KbModelTransmitDto>> selectBasicByLabel(KbBaseShowDto kbBaseShowDto,
                                                                  @RequestParam(required = false) Integer current,
                                                                  @RequestParam(required = false) Integer size) throws Exception {
        if (kbBaseShowDto.getLabelIds() == null || kbBaseShowDto.getLabelIds().size() == 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelTransmitDto> page = new Page<>(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userid = sysUser.getId();
        kbBaseShowDto.setCreateUser(userid);
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            IPage<KbModelTransmitDto> pages = kbUserLabelService.findBasicByLabelAdmin(kbBaseShowDto, page);
            return RespBody.data(pages);
        } else {
            IPage<KbModelTransmitDto> basicByLike = kbUserLabelService.findBasicByLabel(kbBaseShowDto, page);
            return RespBody.data(basicByLike);
        }
    }


    @CastleLog(operLocation = "用户与表签关联表", operType = OperationTypeEnum.QUERY)
    @GetMapping("/knowledge/kbUserLabel/findLabelByUser")
    @ApiOperation("根据用户查询订阅标签")
    @ResponseBody
    public RespBody<List<KbModelLabelEntity>> findLabelByUser() {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        List<KbModelLabelEntity> basicByLike = kbUserLabelService.findLabelByUser(userId);
        return RespBody.data(basicByLike);
    }

    @CastleLog(operLocation = "查询订阅标识文章数量", operType = OperationTypeEnum.QUERY)
    @GetMapping("/knowledge/kbUserLabel/findLabelByUserToBasicCount")
    @ApiOperation("查询订阅标识文章数量")
    @ResponseBody
    public RespBody<Integer> findLabelByUserToBasicCount() {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            Integer count = kbUserLabelService.findLabelByUserToBasicCountAdmin(sysUser.getId());
            return RespBody.data(count);
        } else {
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            Integer count = kbUserLabelService.findLabelByUserToBasicCount(sysUser.getId(),integers);
            return RespBody.data(count);
        }
    }
}
