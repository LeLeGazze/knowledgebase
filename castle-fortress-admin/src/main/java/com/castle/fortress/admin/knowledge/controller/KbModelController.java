package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelDto;
import com.castle.fortress.admin.knowledge.dto.KbPropertyDesignDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
import com.castle.fortress.admin.knowledge.entity.KbSubjectWarehouseEntity;
import com.castle.fortress.admin.knowledge.service.*;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.admin.utils.RandomUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.IRespCode;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * kb模型配置表 控制器
 *
 * @author sunhr
 * @since 2023-04-19
 */
@Api(tags = "kb模型配置表管理控制器")
@Controller
public class KbModelController {
    @Autowired
    private KbModelService kbModelService;
    @Autowired
    private KbColConfigService kbColConfigService;
    @Autowired
    private KbSubjectWarehouseService kbSubjectWarehouseService;

    /**
     * kb模型配置表的分页展示
     *
     * @param kbModelDto kb模型配置表实体类
     * @param current    当前页
     * @param size       每页记录数
     * @return
     */
    @ApiOperation("kb模型配置表分页展示")
    @GetMapping("/kb/kbModel/page")
    @ResponseBody
//    @RequiresPermissions("kb:kbModel:pageList")
    public RespBody<IPage<KbModelDto>> pageCmsModel(KbModelDto kbModelDto,
                                                    @RequestParam(required = false) Integer current,
                                                    @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelDto> page = new Page(pageIndex, pageSize);
        IPage<KbModelDto> pages = kbModelService.pageCmsModelExtends(page, kbModelDto);
        return RespBody.data(pages);
    }

    /**
     * kb模型配置表的列表展示
     *
     * @param kbModelDto kb模型配置表实体类
     * @return
     */
    @ApiOperation("kb模型配置表列表展示")
    @GetMapping("/kb/kbModel/list")
    @ResponseBody
    public RespBody<List<KbModelDto>> listCmsModel(KbModelDto kbModelDto) {
        List<KbModelDto> list = kbModelService.listCmsModel(kbModelDto);
        return RespBody.data(list);
    }

    /**
     * kb模型配置表保存
     *
     * @param kbModelAcceptanceDto cms模型配置表实体类
     * @return
     */
    @ApiOperation("kb模型配置表保存")
    @PostMapping("/kb/kbModel/save")
    @ResponseBody
    @RequiresPermissions("kb:kbModel:save")
    public RespBody saveCmsModel(@RequestBody KbModelAcceptanceDto kbModelAcceptanceDto) {
        if (kbModelAcceptanceDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        kbModelAcceptanceDto.setLabel(null);
        KbModelEntity kbModelEntity = ConvertUtil.transformObj(kbModelAcceptanceDto, KbModelEntity.class);
        kbModelEntity.setIsDeleted(2);
        kbModelEntity.setName(kbModelAcceptanceDto.getModelName());
        kbModelEntity.setCode(RandomUtils.verifyUserName(7));


        boolean save1 = kbModelService.save(kbModelEntity);
        kbModelAcceptanceDto.setModelId(kbModelEntity.getId());

        if (save1) {
            //将拓展模块分离出来
            kbModelAcceptanceDto.setModelId(kbModelEntity.getId());
            List<KbPropertyDesignEntity> entities = kbModelAcceptanceDto.getPropCols();
            //自动创表
            kbColConfigService.saveCols(kbModelEntity, entities);
            return RespBody.success("创建成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * kb模型配置表编辑
     *
     * @param kbModelDto kb模型配置表实体类
     * @return
     */
    @ApiOperation("kb模型配置表编辑")
    @PostMapping("/kb/kbModel/edit")
    @ResponseBody
    @RequiresPermissions("kb:kbModel:edit")
    public RespBody<String> updateCmsModel(@RequestBody KbModelDto kbModelDto) {
        if (kbModelDto == null || kbModelDto.getId() == null || kbModelDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelEntity o = kbModelService.getById(kbModelDto.getId());
        if (!o.getCode().equals(kbModelDto.getCode())) {
            throw new BizException(BizErrorCode.CMS_MODEL_CODE_ALTER_ERROR);
        }
        KbModelEntity kbModelEntity = ConvertUtil.transformObj(kbModelDto, KbModelEntity.class);
        // 查询模本是否绑定了知识库
        if (kbModelEntity.getStatus() == 2) {
            List<KbSubjectWarehouseEntity> byModelId = kbSubjectWarehouseService.findByModelId(kbModelEntity.getId());
            if (byModelId.size() > 0) {
                List<String> collect = byModelId.stream().map(KbSubjectWarehouseEntity::getName).collect(Collectors.toList());
                throw new BizException("该模板绑定了知识库不允许设置不启用【" + String.join(",", collect) + "】");
            }
        }
        if (kbModelService.updateById(kbModelEntity)) {
            List<KbPropertyDesignEntity> entities = ConvertUtil.transformObjList(kbModelDto.getCols(), KbPropertyDesignEntity.class);
            kbColConfigService.updateCols(kbModelEntity, entities);
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    @ApiOperation("kb模型配置表编辑")
    @PostMapping("/kb/kbModel/updateStatus")
    @ResponseBody
    @RequiresPermissions("kb:kbModel:edit")
    public RespBody<String> updateStatus(@RequestBody KbModelDto kbModelDto) {
        if (kbModelDto == null || kbModelDto.getId() == null || kbModelDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelEntity o = kbModelService.getById(kbModelDto.getId());
        if (!o.getCode().equals(kbModelDto.getCode())) {
            throw new BizException(BizErrorCode.CMS_MODEL_CODE_ALTER_ERROR);
        }
        KbModelEntity kbModelEntity = ConvertUtil.transformObj(kbModelDto, KbModelEntity.class);
        // 查询模本是否绑定了知识库
        if (kbModelEntity.getStatus() == 2) {
            List<KbSubjectWarehouseEntity> byModelId = kbSubjectWarehouseService.findByModelId(kbModelEntity.getId());
            if (byModelId.size() > 0) {
                List<String> collect = byModelId.stream().map(KbSubjectWarehouseEntity::getName).collect(Collectors.toList());
                throw new BizException("该模板绑定了知识库不允许设置不启用【" + String.join(",", collect) + "】");
            }
        }
        if (kbModelService.updateById(kbModelEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }
    /**
     * kb模型配置表删除
     *
     * @param id
     * @return
     */
    @ApiOperation("kb模型配置表删除")
    @PostMapping("/kb/kbModel/delete")
    @ResponseBody
    @RequiresPermissions("kb:kbModel:delete")
    public RespBody<String> deleteCmsModel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<Long> aLong = kbModelService.findswId(id);
        if (aLong.size() != 0) {
            return RespBody.fail("该模板下绑定了知识库请先删除知识库");
        }
        KbModelEntity e = kbModelService.getById(id);
        if (kbModelService.removeById(id)) {
            kbColConfigService.delCols(e);
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * kb模型配置表批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("kb模型配置表批量删除")
    @PostMapping("/kb/kbModel/deleteBatch")
    @ResponseBody
    @RequiresPermissions("kb:kbModel:deleteBatch")
    public RespBody<String> deleteCmsModelBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbModelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * kb模型配置表详情
     *
     * @param id kb模型配置表id
     * @return
     */
    @ApiOperation("kb模型配置表详情")
    @GetMapping("/kb/kbModel/info")
    @ResponseBody
    @RequiresPermissions("kb:kbModel:info")
    public RespBody<KbModelDto> infoCmsModel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelDto kbModelDto = kbModelService.getByIdExtends(id);
        KbPropertyDesignDto kbPropertyDesignDto = new KbPropertyDesignDto();
        kbPropertyDesignDto.setModelId(id);
        List<KbPropertyDesignDto> sysCols = kbModelDto.getSysCols();
        List<KbPropertyDesignDto> list = kbColConfigService.listCmsColConfig(kbPropertyDesignDto);
        kbModelDto.setCols(list);
        kbModelDto.setSysCols(sysCols);
        return RespBody.data(kbModelDto);
    }

    /**
     * kb模型配置表详情
     *
     * @return
     */
    @ApiOperation("kb模型配置表详情")
    @GetMapping("/kb/kbModel/kbCols")
    @ResponseBody
    @RequiresPermissions("kb:kbModel:info")
    public RespBody<KbModelDto> sysCols() {
        KbModelDto kbModelDto = new KbModelDto();
//        kbModelDto.setSysCols(ModelColumn.sysCols);
        return RespBody.data(kbModelDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @PostMapping("/kb/kbModel/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbModelDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbModelDto> list = kbModelService.listCmsModel(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }

}
