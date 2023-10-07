package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbLabelCategoryDto;
import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbCategoryLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbCategoryLabelDto;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.service.KbCategoryLabelService;
import com.castle.fortress.admin.knowledge.service.KbModelLabelService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 标签分组和标签关联表 控制器
 *
 * @author
 * @since 2023-06-14
 */
@Api(tags = "标签分组和标签关联表管理控制器")
@Controller
public class KbCategoryLabelController {
    @Autowired
    private KbCategoryLabelService kbCategoryLabelService;
    @Autowired
    private KbModelLabelService kbModelLabelService;

    /**
     * 标签分组和标签关联表的分页展示
     *
     * @param kbCategoryLabelDto 标签分组和标签关联表实体类
     * @param current            当前页
     * @param size               每页记录数
     * @return
     */
    @CastleLog(operLocation = "标签分组和标签关联表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分组和标签关联表分页展示")
    @GetMapping("/knowledge/kbCategoryLabel/page")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbCategoryLabel:pageList")
    public RespBody<IPage<KbCategoryLabelDto>> pageKbCategoryLabel(KbCategoryLabelDto kbCategoryLabelDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbCategoryLabelDto> page = new Page(pageIndex, pageSize);
        IPage<KbCategoryLabelDto> pages = kbCategoryLabelService.pageKbCategoryLabel(page, kbCategoryLabelDto);

        return RespBody.data(pages);
    }

    /**
     * 标签分组和标签关联表的列表展示
     *
     * @param kbCategoryLabelDto 标签分组和标签关联表实体类
     * @return
     */
    @CastleLog(operLocation = "标签分组和标签关联表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分组和标签关联表列表展示")
    @GetMapping("/knowledge/kbCategoryLabel/list")
    @ResponseBody
    public RespBody<List<KbCategoryLabelDto>> listKbCategoryLabel(KbCategoryLabelDto kbCategoryLabelDto) {
        List<KbCategoryLabelDto> list = kbCategoryLabelService.listKbCategoryLabel(kbCategoryLabelDto);
        return RespBody.data(list);
    }

    /**
     * 标签分组和标签关联表保存
     *
     * @param kbCategoryLabelDto 标签分组和标签关联表实体类
     * @return
     */
    @CastleLog(operLocation = "标签分组和标签关联表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签分组和标签关联表保存")
    @PostMapping("/knowledge/kbCategoryLabel/save")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbCategoryLabel:save")
    public RespBody<String> saveKbCategoryLabel(@RequestBody KbCategoryLabelDto kbCategoryLabelDto) {
        if (kbCategoryLabelDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCategoryLabelEntity kbCategoryLabelEntity = ConvertUtil.transformObj(kbCategoryLabelDto, KbCategoryLabelEntity.class);
        if (kbCategoryLabelService.save(kbCategoryLabelEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分组和标签关联表编辑
     *
     * @param kbCategoryLabelDto 标签分组和标签关联表实体类
     * @return
     */
    @CastleLog(operLocation = "标签分组和标签关联表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签分组和标签关联表编辑")
    @PostMapping("/knowledge/kbCategoryLabel/edit")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbCategoryLabel:edit")
    public RespBody<String> updateKbCategoryLabel(@RequestBody KbCategoryLabelDto kbCategoryLabelDto) {
        if (kbCategoryLabelDto == null || kbCategoryLabelDto.getId() == null || kbCategoryLabelDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCategoryLabelEntity kbCategoryLabelEntity = ConvertUtil.transformObj(kbCategoryLabelDto, KbCategoryLabelEntity.class);
        if (kbCategoryLabelService.updateById(kbCategoryLabelEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分组和标签关联表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "标签分组和标签关联表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签分组和标签关联表删除")
    @PostMapping("/knowledge/kbCategoryLabel/delete")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbCategoryLabel:delete")
    public RespBody<String> deleteKbCategoryLabel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbCategoryLabelService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 标签分组和标签关联表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "标签分组和标签关联表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签分组和标签关联表批量删除")
    @PostMapping("/knowledge/kbCategoryLabel/deleteBatch")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbCategoryLabel:deleteBatch")
    public RespBody<String> deleteKbCategoryLabelBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbCategoryLabelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分组和标签关联表详情
     *
     * @param id 标签分组和标签关联表id
     * @return
     */
    @CastleLog(operLocation = "标签分组和标签关联表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分组和标签关联表详情")
    @GetMapping("/knowledge/kbCategoryLabel/info")
    @ResponseBody
    //@RequiresPermissions("knowledge:kbCategoryLabel:info")
    public RespBody<KbCategoryLabelDto> infoKbCategoryLabel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCategoryLabelEntity kbCategoryLabelEntity = kbCategoryLabelService.getById(id);
        KbCategoryLabelDto kbCategoryLabelDto = ConvertUtil.transformObj(kbCategoryLabelEntity, KbCategoryLabelDto.class);

        return RespBody.data(kbCategoryLabelDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "标签分组和标签关联表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbCategoryLabel/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbCategoryLabelDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbCategoryLabelDto> list = kbCategoryLabelService.listKbCategoryLabel(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


    @CastleLog(operLocation = "标签分类表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签分类表保存")
    @PostMapping("/knowledge/kbLabelCategory/saveLabel")
    @ResponseBody
    @RequiresPermissions("knowledge:kbLabelCategory:save")
    public RespBody<String> saveKbLabel(@RequestBody KbCategoryLabelDto kbCategoryLabelDto) {
        if (kbCategoryLabelDto == null || kbCategoryLabelDto.getLabels().size() == 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<KbModelLabelDto> labels = new ArrayList<>( kbCategoryLabelDto.getLabels());
        RespBody<String> stringRespBody = kbModelLabelService.saveLabels(labels);
        ArrayList<KbCategoryLabelEntity> kbCategoryLabelEntities = new ArrayList<>();
        // 新增中间表
        for (KbModelLabelEntity labelEntity : kbModelLabelService.findIsExistName(labels.stream().map(KbModelLabelDto::getName).collect(Collectors.toList()))) {
            KbCategoryLabelEntity kbCategoryLabelEntity = new KbCategoryLabelEntity();
            kbCategoryLabelEntity.setLId(labelEntity.getId());
            kbCategoryLabelEntity.setCtId(kbCategoryLabelDto.getCtId());
            kbCategoryLabelEntities.add(kbCategoryLabelEntity);
        }
        kbCategoryLabelService.deleteBylId(kbCategoryLabelEntities);
        kbCategoryLabelService.saveBatch(kbCategoryLabelEntities);
        return stringRespBody;
    }


    @CastleLog(operLocation = "标签分类表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("分类下的标签查询")
    @GetMapping("/knowledge/kbLabelCategory/showLabel")
    @ResponseBody
    public RespBody<IPage<KbModelLabelDto>> showLabel(@RequestParam Long ctId,
                                                      KbModelLabelDto kbModelLabelDto,
                                                      @RequestParam(required = false) Integer current,
                                                      @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelLabelDto> page = new Page<>(pageIndex, pageSize);
        IPage<KbModelLabelDto> pageDto = kbCategoryLabelService.findLabelByCategory(ctId, page, kbModelLabelDto);
        return RespBody.data(pageDto);
    }

    @CastleLog(operLocation = "标签分类表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("分类置为失效")
    @PostMapping("/knowledge/kbLabelCategory/editStatus")
    @ResponseBody
    public RespBody<String> editLabel(@RequestParam Long ctId) {
        RespBody<String> labels = kbCategoryLabelService.findAllLabelByCategory(ctId);
        return labels;
    }

    @CastleLog(operLocation = "标签分类表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("分类置为热词")
    @PostMapping("/knowledge/kbLabelCategory/editHotWord")
    @ResponseBody
    public RespBody<String> editHotWord(@RequestParam Long ctId) {
        RespBody<String> labels = kbCategoryLabelService.editHotWordLabelByCategory(ctId);
        return labels;
    }


}
