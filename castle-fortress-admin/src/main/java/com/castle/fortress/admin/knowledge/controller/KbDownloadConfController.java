package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbDownloadConfEntity;
import com.castle.fortress.admin.knowledge.dto.KbDownloadConfDto;
import com.castle.fortress.admin.knowledge.service.KbDownloadConfService;
import com.castle.fortress.admin.utils.RedisUtils;
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

import java.util.List;

/**
 * 文件下载配置表 控制器
 *
 * @author
 * @since 2023-06-25
 */
@Api(tags = "文件下载配置表管理控制器")
@Controller
public class KbDownloadConfController {
    @Autowired
    private KbDownloadConfService kbDownloadConfService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 文件下载配置表的分页展示
     *
     * @param kbDownloadConfDto 文件下载配置表实体类
     * @param current           当前页
     * @param size              每页记录数
     * @return
     */
    @CastleLog(operLocation = "文件下载配置表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("文件下载配置表分页展示")
    @GetMapping("/knowledge/kbDownloadConf/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDownloadConf:pageList")
    public RespBody<IPage<KbDownloadConfDto>> pageKbDownloadConf(KbDownloadConfDto kbDownloadConfDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbDownloadConfDto> page = new Page(pageIndex, pageSize);
        IPage<KbDownloadConfDto> pages = kbDownloadConfService.pageKbDownloadConfExtends(page, kbDownloadConfDto);

        return RespBody.data(pages);
    }

    /**
     * 文件下载配置表的列表展示
     *
     * @param kbDownloadConfDto 文件下载配置表实体类
     * @return
     */
    @CastleLog(operLocation = "文件下载配置表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("文件下载配置表列表展示")
    @GetMapping("/knowledge/kbDownloadConf/list")
    @ResponseBody
    public RespBody<List<KbDownloadConfDto>> listKbDownloadConf(KbDownloadConfDto kbDownloadConfDto) {
        List<KbDownloadConfDto> list = kbDownloadConfService.listKbDownloadConf(kbDownloadConfDto);
        return RespBody.data(list);
    }

    /**
     * 文件下载配置表保存
     *
     * @param kbDownloadConfDto 文件下载配置表实体类
     * @return
     */
    @CastleLog(operLocation = "文件下载配置表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("文件下载配置表保存")
    @PostMapping("/knowledge/kbDownloadConf/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDownloadConf:save")
    public RespBody<String> saveKbDownloadConf(@RequestBody KbDownloadConfDto kbDownloadConfDto) {
        if (kbDownloadConfDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbDownloadConfEntity kbDownloadConfEntity = ConvertUtil.transformObj(kbDownloadConfDto, KbDownloadConfEntity.class);
        if (kbDownloadConfService.save(kbDownloadConfEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 文件下载配置表编辑
     *
     * @param kbDownloadConfDto 文件下载配置表实体类
     * @return
     */
    @CastleLog(operLocation = "文件下载配置表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("文件下载配置表编辑")
    @PostMapping("/knowledge/kbDownloadConf/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDownloadConf:edit")
    public RespBody<String> updateKbDownloadConf(@RequestBody KbDownloadConfDto kbDownloadConfDto) {
        if (kbDownloadConfDto == null || kbDownloadConfDto.getId() == null || kbDownloadConfDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbDownloadConfEntity kbDownloadConfEntity = ConvertUtil.transformObj(kbDownloadConfDto, KbDownloadConfEntity.class);
        if (kbDownloadConfEntity.getType() == 1 && kbDownloadConfEntity.getStatus() == 1) {
            QueryWrapper<KbDownloadConfEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", "2");
            queryWrapper.eq("type", "2");
            // 修改下载水印从从关闭 到开启 必须下载的是PDF
            int count = kbDownloadConfService.count(queryWrapper);
            if (count == 0) {
                throw new BizException("开启下载水印必须把源文件关闭，下载PDF");
            }
        }else if (kbDownloadConfEntity.getType() == 2 && kbDownloadConfEntity.getStatus() == 1){
            QueryWrapper<KbDownloadConfEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", "2");
            queryWrapper.eq("name","下载水印");
            // 修改下载水印从从关闭 到开启 必须下载的是PDF
            int count = kbDownloadConfService.count(queryWrapper);
            if (count == 0) {
                throw new BizException("开启源文件下载必须把下载水印关闭");
            }
        }
        if (kbDownloadConfService.updateById(kbDownloadConfEntity)) {
            //类型为1 修改水印 类型 为2 修改的是下载源文件
            if (kbDownloadConfEntity.getType() == 1) {
                redisUtils.set("downloadBase:isWatermark", kbDownloadConfEntity.getStatus());
            } else {
                redisUtils.set("downloadBase:isSourceFile", kbDownloadConfEntity.getStatus());
            }
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 文件下载配置表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "文件下载配置表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("文件下载配置表删除")
    @PostMapping("/knowledge/kbDownloadConf/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDownloadConf:delete")
    public RespBody<String> deleteKbDownloadConf(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbDownloadConfService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 文件下载配置表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "文件下载配置表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("文件下载配置表批量删除")
    @PostMapping("/knowledge/kbDownloadConf/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDownloadConf:deleteBatch")
    public RespBody<String> deleteKbDownloadConfBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbDownloadConfService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 文件下载配置表详情
     *
     * @param id 文件下载配置表id
     * @return
     */
    @CastleLog(operLocation = "文件下载配置表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("文件下载配置表详情")
    @GetMapping("/knowledge/kbDownloadConf/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDownloadConf:info")
    public RespBody<KbDownloadConfDto> infoKbDownloadConf(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbDownloadConfDto kbDownloadConfDto = kbDownloadConfService.getByIdExtends(id);

        return RespBody.data(kbDownloadConfDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "文件下载配置表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbDownloadConf/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbDownloadConfDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbDownloadConfDto> list = kbDownloadConfService.listKbDownloadConf(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
