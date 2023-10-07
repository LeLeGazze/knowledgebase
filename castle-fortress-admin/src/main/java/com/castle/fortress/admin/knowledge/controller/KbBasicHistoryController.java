package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicHistoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicHistoryDto;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.service.KbBasicHistoryService;
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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 知识基本表历史表 控制器
 *
 * @author
 * @since 2023-07-03
 */
@Api(tags = "知识基本表历史表管理控制器")
@Controller
public class KbBasicHistoryController {
    @Autowired
    private KbBasicHistoryService kbBasicHistoryService;

    /**
     * 知识基本表历史表的分页展示
     *
     * @param kbBasicHistoryDto 知识基本表历史表实体类
     * @param current           当前页
     * @param size              每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识基本表历史表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识基本表历史表分页展示")
    @GetMapping("/knowledge/kbBasicHistory/page")
    @ResponseBody
//    @RequiresPermissions("knowledge:kbBasicHistory:pageList")
    public RespBody<IPage<KbBasicHistoryDto>> pageKbBasicHistory(KbBasicHistoryDto kbBasicHistoryDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbBasicHistoryDto> page = new Page(pageIndex, pageSize);
        IPage<KbBasicHistoryDto> pages = kbBasicHistoryService.pageKbBasicHistoryExtends(page, kbBasicHistoryDto);
        return RespBody.data(pages);
    }

    @GetMapping("/knowledge/kbBasicHistory/findBasic")
    @ApiOperation("知识基本表历史查询详情")
    @ResponseBody
    public RespBody<KbModelTransmitDto> findBasic(@RequestParam Long id) throws Exception {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //查询当前知识的信息
        KbModelTransmitDto allByBasic = kbBasicHistoryService.findAllByBasic(id);
        allByBasic.setDownloadAuthority(false);
        // 未发布 浏览量不加 和不允许下载
        if (allByBasic.getStatus() == 1) {
            allByBasic.setDownloadAuthority(true);
        }
        return RespBody.data(allByBasic);
    }

    /**
     * 知识基本表历史表的列表展示
     *
     * @param kbBasicHistoryDto 知识基本表历史表实体类
     * @return
     */
    @CastleLog(operLocation = "知识基本表历史表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识基本表历史表列表展示")
    @GetMapping("/knowledge/kbBasicHistory/list")
    @ResponseBody
    public RespBody<List<KbBasicHistoryDto>> listKbBasicHistory(KbBasicHistoryDto kbBasicHistoryDto) {
        List<KbBasicHistoryDto> list = kbBasicHistoryService.listKbBasicHistory(kbBasicHistoryDto);
        return RespBody.data(list);
    }

    /**
     * 知识基本表历史表保存
     *
     * @param kbBasicHistoryDto 知识基本表历史表实体类
     * @return
     */
    @CastleLog(operLocation = "知识基本表历史表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识基本表历史表保存")
    @PostMapping("/knowledge/kbBasicHistory/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBasicHistory:save")
    public RespBody<String> saveKbBasicHistory(@RequestBody KbBasicHistoryDto kbBasicHistoryDto) {
        if (kbBasicHistoryDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicHistoryEntity kbBasicHistoryEntity = ConvertUtil.transformObj(kbBasicHistoryDto, KbBasicHistoryEntity.class);
        if (kbBasicHistoryService.save(kbBasicHistoryEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识基本表历史表编辑
     *
     * @param kbBasicHistoryDto 知识基本表历史表实体类
     * @return
     */
    @CastleLog(operLocation = "知识基本表历史表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识基本表历史表编辑")
    @PostMapping("/knowledge/kbBasicHistory/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBasicHistory:edit")
    public RespBody<String> updateKbBasicHistory(@RequestBody KbBasicHistoryDto kbBasicHistoryDto) {
        if (kbBasicHistoryDto == null || kbBasicHistoryDto.getId() == null || kbBasicHistoryDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicHistoryEntity kbBasicHistoryEntity = ConvertUtil.transformObj(kbBasicHistoryDto, KbBasicHistoryEntity.class);
        if (kbBasicHistoryService.updateById(kbBasicHistoryEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识基本表历史表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识基本表历史表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识基本表历史表删除")
    @PostMapping("/knowledge/kbBasicHistory/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBasicHistory:delete")
    public RespBody<String> deleteKbBasicHistory(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicHistoryService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识基本表历史表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识基本表历史表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识基本表历史表批量删除")
    @PostMapping("/knowledge/kbBasicHistory/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBasicHistory:deleteBatch")
    public RespBody<String> deleteKbBasicHistoryBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicHistoryService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识基本表历史表详情
     *
     * @param id 知识基本表历史表id
     * @return
     */
    @CastleLog(operLocation = "知识基本表历史表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识基本表历史表详情")
    @GetMapping("/knowledge/kbBasicHistory/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBasicHistory:info")
    public RespBody<KbBasicHistoryDto> infoKbBasicHistory(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicHistoryDto kbBasicHistoryDto = kbBasicHistoryService.getByIdExtends(id);

        return RespBody.data(kbBasicHistoryDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识基本表历史表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbBasicHistory/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbBasicHistoryDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbBasicHistoryDto> list = kbBasicHistoryService.listKbBasicHistory(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
