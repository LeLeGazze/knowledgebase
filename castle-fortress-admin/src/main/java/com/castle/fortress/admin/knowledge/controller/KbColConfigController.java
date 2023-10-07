package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbPropertyDesignDto;
import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
import com.castle.fortress.admin.knowledge.entity.KbSubjectWarehouseEntity;
import com.castle.fortress.admin.knowledge.service.KbBasicService;
import com.castle.fortress.admin.knowledge.service.KbColConfigService;
import com.castle.fortress.admin.knowledge.service.KbSubjectWarehouseService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * kb模型字段配置表 控制器
 *
 * @author sunhr
 * @since 2023-04-18
 */
@Api(tags = "kb模型字段配置表管理控制器")
@Controller
public class KbColConfigController {
    @Autowired
    private KbBasicService kbBasicService;
    @Autowired
    private KbColConfigService kbColConfigService;
    @Autowired
    private KbSubjectWarehouseService kbSubjectWarehouseService;

    /**
     * kb模型字段配置表的分页展示
     *
     * @param kbPropertyDesignDto cms模型字段配置表实体类
     * @param current             当前页
     * @param size                每页记录数
     * @return
     */
    @ApiOperation("kb模型字段配置表分页展示")
    @GetMapping("/kb/kbColConfig/page")
    @ResponseBody
    @RequiresPermissions("kb:kbColConfig:pageList")
    public RespBody<IPage<KbPropertyDesignDto>> pageCmsColConfig(KbPropertyDesignDto kbPropertyDesignDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbPropertyDesignDto> page = new Page(pageIndex, pageSize);
        IPage<KbPropertyDesignDto> pages = kbColConfigService.pageKbColConfig(page, kbPropertyDesignDto);

        return RespBody.data(pages);
    }

    /**
     * kb模型字段配置表的列表展示
     *
     * @param swId 目录Id
     * @return
     */
    @ApiOperation("kb模型字段配置表列表展示")
    @GetMapping("/kb/kbColConfig/list")
    @ResponseBody
    public RespBody<List<KbPropertyDesignDto>> listCmsColConfig(@RequestParam(value = "swId") Long swId) {
        KbSubjectWarehouseEntity byId = kbSubjectWarehouseService.getById(swId);
        Long modelId = byId.getModelId();
        KbPropertyDesignDto kbPropertyDesignDto = new KbPropertyDesignDto();
        kbPropertyDesignDto.setModelId(modelId);
        kbPropertyDesignDto.setStatus(1);
        kbPropertyDesignDto.setIsDeleted(2);
        List<KbPropertyDesignDto> list = kbColConfigService.listKbColConfig(kbPropertyDesignDto);
        return RespBody.data(list);
    }


    /**
     * kb模型字段配置表保存
     *
     * @param kbPropertyDesignDto cms模型字段配置表实体类
     * @return
     */
    @ApiOperation("kb模型字段配置表保存")
    @PostMapping("/kb/kbColConfig/save")
    @ResponseBody
    @RequiresPermissions("kb:kbColConfig:save")
    public RespBody<String> saveCmsColConfig(@RequestBody KbPropertyDesignDto kbPropertyDesignDto) {
        if (kbPropertyDesignDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbPropertyDesignEntity kbPropertyDesignEntity = ConvertUtil.transformObj(kbPropertyDesignDto, KbPropertyDesignEntity.class);
        if (kbColConfigService.save(kbPropertyDesignEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * kb模型字段配置表编辑
     *
     * @param kbPropertyDesignDto cms模型字段配置表实体类
     * @return
     */
    @ApiOperation("kb模型字段配置表编辑")
    @PostMapping("/kb/kbColConfig/edit")
    @ResponseBody
    @RequiresPermissions("kb:kbColConfig:edit")
    public RespBody<String> updateCmsColConfig(@RequestBody KbPropertyDesignDto kbPropertyDesignDto) {
        if (kbPropertyDesignDto == null || kbPropertyDesignDto.getId() == null || kbPropertyDesignDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbPropertyDesignEntity kbPropertyDesignEntity = ConvertUtil.transformObj(kbPropertyDesignDto, KbPropertyDesignEntity.class);
        if (kbColConfigService.updateById(kbPropertyDesignEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * kb模型字段配置表删除
     *
     * @param id
     * @return
     */
    @ApiOperation("kb模型字段配置表删除")
    @PostMapping("/kb/kbColConfig/delete")
    @ResponseBody
    @RequiresPermissions("kb:kbColConfig:delete")
    public RespBody<String> deleteCmsColConfig(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbColConfigService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * kb模型字段配置表批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("kb模型字段配置表批量删除")
    @PostMapping("/kb/kbColConfig/deleteBatch")
    @ResponseBody
    @RequiresPermissions("kb:kbColConfig:deleteBatch")
    public RespBody<String> deleteCmsColConfigBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbColConfigService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * kb模型字段配置表详情
     *
     * @param id cms模型字段配置表id
     * @return
     */
    @ApiOperation("kb模型字段配置表详情")
    @GetMapping("/kb/kbColConfig/info")
    @ResponseBody
    @RequiresPermissions("kb:kbColConfig:info")
    public RespBody<KbPropertyDesignDto> infoCmsColConfig(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbPropertyDesignEntity kbPropertyDesignEntity = kbColConfigService.getById(id);
        KbPropertyDesignDto kbPropertyDesignDto = ConvertUtil.transformObj(kbPropertyDesignEntity, KbPropertyDesignDto.class);

        return RespBody.data(kbPropertyDesignDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @PostMapping("/kb/kbColConfig/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbPropertyDesignDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbPropertyDesignDto> list = kbColConfigService.listKbColConfig(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
