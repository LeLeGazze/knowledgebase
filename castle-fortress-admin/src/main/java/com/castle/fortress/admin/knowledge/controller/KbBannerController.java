package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbBannerEntity;
import com.castle.fortress.admin.knowledge.dto.KbBannerDto;
import com.castle.fortress.admin.knowledge.service.KbBannerService;
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

/**
 * 知识banner图表 控制器
 *
 * @author
 * @since 2023-06-17
 */
@Api(tags = "知识banner图表管理控制器")
@Controller
public class KbBannerController {
    @Autowired
    private KbBannerService kbBannerService;

    /**
     * 知识banner图表的分页展示
     *
     * @param kbBannerDto 知识banner图表实体类
     * @param current     当前页
     * @param size        每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识banner图表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识banner图表分页展示")
    @GetMapping("/knowledge/kbBanner/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBanner:pageList")
    public RespBody<IPage<KbBannerDto>> pageKbBanner(KbBannerDto kbBannerDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbBannerDto> page = new Page(pageIndex, pageSize);
        IPage<KbBannerDto> pages = kbBannerService.pageKbBanner(page, kbBannerDto);
        return RespBody.data(pages);
    }

    /**
     * 知识banner图表的列表展示
     *
     * @param kbBannerDto 知识banner图表实体类
     * @return
     */
    @CastleLog(operLocation = "知识banner图表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识banner图表列表展示")
    @GetMapping("/knowledge/kbBanner/list")
    @ResponseBody
    public RespBody<List<KbBannerDto>> listKbBanner(KbBannerDto kbBannerDto) {
        List<KbBannerDto> list = kbBannerService.listKbBanner(kbBannerDto);
        return RespBody.data(list);
    }

    /**
     * 知识banner图表保存
     *
     * @param kbBannerDto 知识banner图表实体类
     * @return
     */
    @CastleLog(operLocation = "知识banner图表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识banner图表保存")
    @PostMapping("/knowledge/kbBanner/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBanner:save")
    public RespBody<String> saveKbBanner(@RequestBody KbBannerDto kbBannerDto) {
        if (kbBannerDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBannerEntity kbBannerEntity = ConvertUtil.transformObj(kbBannerDto, KbBannerEntity.class);
        if (kbBannerService.save(kbBannerEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识banner图表编辑
     *
     * @param kbBannerDto 知识banner图表实体类
     * @return
     */
    @CastleLog(operLocation = "知识banner图表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识banner图表编辑")
    @PostMapping("/knowledge/kbBanner/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBanner:edit")
    public RespBody<String> updateKbBanner(@RequestBody KbBannerDto kbBannerDto) {
        if (kbBannerDto == null || kbBannerDto.getId() == null || kbBannerDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBannerEntity kbBannerEntity = ConvertUtil.transformObj(kbBannerDto, KbBannerEntity.class);
        if (kbBannerEntity.getStatus() == 2) {
            List<KbBannerEntity> kbBannerEntityList = kbBannerService.findByStatus(1);
            if (kbBannerEntityList.size() <=1) {
                return RespBody.fail("知识banner图表必须开启一个");
            }
        }
        if (kbBannerService.updateById(kbBannerEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识banner图表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识banner图表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识banner图表删除")
    @PostMapping("/knowledge/kbBanner/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBanner:delete")
    public RespBody<String> deleteKbBanner(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBannerService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识banner图表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识banner图表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识banner图表批量删除")
    @PostMapping("/knowledge/kbBanner/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBanner:deleteBatch")
    public RespBody<String> deleteKbBannerBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBannerService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识banner图表详情
     *
     * @param id 知识banner图表id
     * @return
     */
    @CastleLog(operLocation = "知识banner图表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识banner图表详情")
    @GetMapping("/knowledge/kbBanner/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBanner:info")
    public RespBody<KbBannerDto> infoKbBanner(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBannerEntity kbBannerEntity = kbBannerService.getById(id);
        KbBannerDto kbBannerDto = ConvertUtil.transformObj(kbBannerEntity, KbBannerDto.class);

        return RespBody.data(kbBannerDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识banner图表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbBanner/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbBannerDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbBannerDto> list = kbBannerService.listKbBanner(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
