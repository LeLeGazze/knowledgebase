package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbModelCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelCategoryDto;
import com.castle.fortress.admin.knowledge.service.KbModelCategoryService;
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

import java.util.List;
import java.util.Map;

/**
 * 模型分类管理 控制器
 *
 * @author Pan Chen
 * @since 2023-04-10
 */
@Api(tags = "模型分类管理管理控制器")
@Controller
public class KbModelCategoryController {
    @Autowired
    private KbModelCategoryService kbModelCategoryService;

    /**
     * 模型分类管理的分页展示
     *
     * @param kbModelCategoryDto 模型分类管理实体类
     * @param current            当前页
     * @param size               每页记录数
     * @return
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("模型分类管理分页展示")
    @GetMapping("/knowledge/kbModelCategory/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelCategory:pageList")
    public RespBody<IPage<KbModelCategoryDto>> pageKbModelCategory(KbModelCategoryDto kbModelCategoryDto,
                                                                   @RequestParam(required = false) Integer current,
                                                                   @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelCategoryDto> page = new Page(pageIndex, pageSize);
        IPage<KbModelCategoryDto> pages = kbModelCategoryService.pageKbModelCategoryExtends(page, kbModelCategoryDto);

        return RespBody.data(pages);
    }

    /**
     * 模型分类管理的列表展示
     *
     * @param kbModelCategoryDto 模型分类管理实体类
     * @return
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("模型分类管理列表展示")
    @GetMapping("/knowledge/kbModelCategory/list")
    @ResponseBody
    public RespBody<List<KbModelCategoryDto>> listKbModelCategory(KbModelCategoryDto kbModelCategoryDto) {
        List<KbModelCategoryDto> list = kbModelCategoryService.listKbModelCategory(kbModelCategoryDto);
        return RespBody.data(list);
    }

    /**
     * 查询有哪些支持查询字段
     *
     * @param swId 目录id
     * @return
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查询有哪些支持查询字段")
    @GetMapping("/knowledge/kbModelCategory/findBySwId")
    @ResponseBody
    public RespBody<List<Map<String, Object>>> findBySwId(@RequestParam String swId) {
        return RespBody.data(kbModelCategoryService.findBySwId(swId));
    }

    /**
     * 模型分类管理保存
     *
     * @param kbModelCategoryDto 模型分类管理实体类
     * @return
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.INSERT)
    @ApiOperation("模型分类管理保存")
    @PostMapping("/knowledge/kbModelCategory/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelCategory:save")
    public RespBody<String> saveKbModelCategory(@RequestBody KbModelCategoryDto kbModelCategoryDto) {
        if (kbModelCategoryDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelCategoryEntity kbModelCategoryEntity = ConvertUtil.transformObj(kbModelCategoryDto, KbModelCategoryEntity.class);
        if (kbModelCategoryService.save(kbModelCategoryEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 模型分类管理编辑
     *
     * @param kbModelCategoryDto 模型分类管理实体类
     * @return
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("模型分类管理编辑")
    @PostMapping("/knowledge/kbModelCategory/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelCategory:edit")
    public RespBody<String> updateKbModelCategory(@RequestBody KbModelCategoryDto kbModelCategoryDto) {
        if (kbModelCategoryDto == null || kbModelCategoryDto.getId() == null || kbModelCategoryDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelCategoryEntity kbModelCategoryEntity = ConvertUtil.transformObj(kbModelCategoryDto, KbModelCategoryEntity.class);
        if (kbModelCategoryService.updateById(kbModelCategoryEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 模型分类管理删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.DELETE)
    @ApiOperation("模型分类管理删除")
    @PostMapping("/knowledge/kbModelCategory/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelCategory:delete")
    public RespBody<String> deleteKbModelCategory(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbModelCategoryService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 模型分类管理批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.DELETE)
    @ApiOperation("模型分类管理批量删除")
    @PostMapping("/knowledge/kbModelCategory/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelCategory:deleteBatch")
    public RespBody<String> deleteKbModelCategoryBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbModelCategoryService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 模型分类管理详情
     *
     * @param id 模型分类管理id
     * @return
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("模型分类管理详情")
    @GetMapping("/knowledge/kbModelCategory/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelCategory:info")
    public RespBody<KbModelCategoryDto> infoKbModelCategory(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelCategoryDto kbModelCategoryDto = kbModelCategoryService.getByIdExtends(id);

        return RespBody.data(kbModelCategoryDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "模型分类管理", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbModelCategory/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbModelCategoryDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbModelCategoryDto> list = kbModelCategoryService.listKbModelCategory(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
