package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicLogEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicLogDto;
import com.castle.fortress.admin.knowledge.service.KbBasicLogService;
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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;

import javax.servlet.http.HttpServletResponse;

import com.castle.fortress.common.entity.DynamicExcelEntity;

import java.text.ParseException;
import java.util.List;

/**
 * 知识移动日志表 控制器
 *
 * @author
 * @since 2023-06-02
 */
@Api(tags = "知识移动日志表管理控制器")
@Controller
public class KbBasicLogController {
    @Autowired
    private KbBasicLogService kbBasicLogService;

    /**
     * 知识移动日志表的分页展示
     *
     * @param kbBaseShowDto 知识移动日志表实体类
     * @param current       当前页
     * @param size          每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识移动日志表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识移动日志表分页展示")
    @GetMapping("/knowledge/kbBaiscLog/page")
    @ResponseBody
    public RespBody<IPage<KbModelTransmitDto>> pageKbBasicLog(KbBaseShowDto kbBaseShowDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) throws Exception {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbBasicLogDto> page = new Page(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        IPage<KbModelTransmitDto> pages = kbBasicLogService.pageKbBasicLog(page, kbBaseShowDto,sysUser);

        return RespBody.data(pages);
    }

    /**
     * 知识移动日志表的列表展示
     *
     * @param kbBasicLogDto 知识移动日志表实体类
     * @return
     */
    @CastleLog(operLocation = "知识移动日志表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识移动日志表列表展示")
    @GetMapping("/knowledge/kbBaiscLog/list")
    @ResponseBody
    public RespBody<List<KbBasicLogDto>> listKbBasicLog(KbBasicLogDto kbBasicLogDto) {
        List<KbBasicLogDto> list = kbBasicLogService.listKbBasicLog(kbBasicLogDto);
        return RespBody.data(list);
    }

    /**
     * 知识移动日志表保存
     *
     * @param kbBasicLogDto 知识移动日志表实体类
     * @return
     */
    @CastleLog(operLocation = "知识移动日志表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识移动日志表保存")
    @PostMapping("/knowledge/kbBaiscLog/save")
    @ResponseBody
    public RespBody<String> saveKbBasicLog(@RequestBody KbBasicLogDto kbBasicLogDto) {
        if (kbBasicLogDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicLogEntity kbBasicLogEntity = ConvertUtil.transformObj(kbBasicLogDto, KbBasicLogEntity.class);
        if (kbBasicLogService.save(kbBasicLogEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识移动日志表编辑
     *
     * @param kbBasicLogDto 知识移动日志表实体类
     * @return
     */
    @CastleLog(operLocation = "知识移动日志表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识移动日志表编辑")
    @PostMapping("/knowledge/kbBaiscLog/edit")
    @ResponseBody
    public RespBody<String> updateKbBasicLog(@RequestBody KbBasicLogDto kbBasicLogDto) {
        if (kbBasicLogDto == null || kbBasicLogDto.getId() == null || kbBasicLogDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicLogEntity kbBasicLogEntity = ConvertUtil.transformObj(kbBasicLogDto, KbBasicLogEntity.class);
        if (kbBasicLogService.updateById(kbBasicLogEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识移动日志表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识移动日志表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识移动日志表删除")
    @PostMapping("/knowledge/kbBaiscLog/delete")
    @ResponseBody
    public RespBody<String> deleteKbBasicLog(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicLogService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识移动日志表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识移动日志表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识移动日志表批量删除")
    @PostMapping("/knowledge/kbBaiscLog/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbBasicLogBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicLogService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识移动日志表详情
     *
     * @param id 知识移动日志表id
     * @return
     */
    @CastleLog(operLocation = "知识移动日志表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识移动日志表详情")
    @GetMapping("/knowledge/kbBaiscLog/info")
    @ResponseBody
    public RespBody<KbBasicLogDto> infoKbBasicLog(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicLogEntity kbBasicLogEntity = kbBasicLogService.getById(id);
        KbBasicLogDto kbBasicLogDto = ConvertUtil.transformObj(kbBasicLogEntity, KbBasicLogDto.class);

        return RespBody.data(kbBasicLogDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识移动日志表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbBasicLog/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbBasicLogDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbBasicLogDto> list = kbBasicLogService.listKbBasicLog(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
