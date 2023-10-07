package com.castle.fortress.admin.knowledge.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.excel.write.metadata.RowData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbVideVersionEntity;
import com.castle.fortress.admin.knowledge.dto.KbVideVersionDto;
import com.castle.fortress.admin.knowledge.service.KbVideVersionService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.castle.fortress.common.entity.DynamicExcelEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.ui.Model;

/**
 * PDF/word 等转换成PDF 控制器
 *
 * @author
 * @since 2023-05-08
 */
@Api(tags = "PDF/word 等转换成PDF管理控制器")
@Controller
public class KbVideVersionController {
    @Autowired
    private KbVideVersionService kbVideVersionService;

    /**
     * PDF/word 等转换成PDF的分页展示
     *
     * @param kbVideVersionDto PDF/word 等转换成PDF实体类
     * @param current          当前页
     * @param size             每页记录数
     * @return
     */
    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.QUERY)
    @ApiOperation("PDF/word 等转换成PDF分页展示")
    @GetMapping("/knowledge/kbVideVersion/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbVideVersion:pageList")
    public RespBody<IPage<KbVideVersionDto>> pageKbVideVersion(KbVideVersionDto kbVideVersionDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbVideVersionDto> page = new Page(pageIndex, pageSize);
        IPage<KbVideVersionDto> pages = kbVideVersionService.pageKbVideVersionExtends(page, kbVideVersionDto);

        return RespBody.data(pages);
    }

    /**
     * PDF/word 等转换成PDF的列表展示
     *
     * @param kbVideVersionDto PDF/word 等转换成PDF实体类
     * @return
     */
    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.QUERY)
    @ApiOperation("PDF/word 等转换成PDF列表展示")
    @GetMapping("/knowledge/kbVideVersion/list")
    @ResponseBody
    public RespBody<List<KbVideVersionDto>> listKbVideVersion(KbVideVersionDto kbVideVersionDto) {
        List<KbVideVersionDto> list = kbVideVersionService.listKbVideVersion(kbVideVersionDto);
        return RespBody.data(list);
    }

    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.QUERY)
    @ApiOperation("PDF/word 等转换成PDF列表展示")
    @GetMapping("/knowledge/kbVideVersion/findByTypeVersion")
    @ResponseBody
    public RespBody<List<KbVideVersionDto>> findByTypeVersion(KbVideVersionDto kbVideVersionDto) {
        if (kbVideVersionDto == null || StrUtil.isEmpty(kbVideVersionDto.getType()) || StrUtil.isEmpty(String.valueOf(kbVideVersionDto.getBId()))) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<KbVideVersionDto> list = kbVideVersionService.findByTypeVersion(kbVideVersionDto);
        return RespBody.data(list);
    }

    /**
     * PDF/word 等转换成PDF保存
     *
     * @param kbVideVersionDto PDF/word 等转换成PDF实体类
     * @return
     */
    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.INSERT)
    @ApiOperation("PDF/word 等转换成PDF保存")
    @PostMapping("/knowledge/kbVideVersion/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbVideVersion:save")
    public RespBody<String> saveKbVideVersion(@RequestBody KbVideVersionDto kbVideVersionDto) {
        if (kbVideVersionDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbVideVersionEntity kbVideVersionEntity = ConvertUtil.transformObj(kbVideVersionDto, KbVideVersionEntity.class);
        if (kbVideVersionService.save(kbVideVersionEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * PDF/word 等转换成PDF编辑
     *
     * @param kbVideVersionDto PDF/word 等转换成PDF实体类
     * @return
     */
    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("PDF/word 等转换成PDF编辑")
    @PostMapping("/knowledge/kbVideVersion/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbVideVersion:edit")
    public RespBody<String> updateKbVideVersion(@RequestBody KbVideVersionDto kbVideVersionDto) {
        if (kbVideVersionDto == null || kbVideVersionDto.getId() == null || kbVideVersionDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbVideVersionEntity kbVideVersionEntity = ConvertUtil.transformObj(kbVideVersionDto, KbVideVersionEntity.class);
        if (kbVideVersionService.updateById(kbVideVersionEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * PDF/word 等转换成PDF删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.DELETE)
    @ApiOperation("PDF/word 等转换成PDF删除")
    @PostMapping("/knowledge/kbVideVersion/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbVideVersion:delete")
    public RespBody<String> deleteKbVideVersion(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbVideVersionService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * PDF/word 等转换成PDF批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.DELETE)
    @ApiOperation("PDF/word 等转换成PDF批量删除")
    @PostMapping("/knowledge/kbVideVersion/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbVideVersion:deleteBatch")
    public RespBody<String> deleteKbVideVersionBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbVideVersionService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * PDF/word 等转换成PDF详情
     *
     * @param id PDF/word 等转换成PDFid
     * @return
     */
    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.QUERY)
    @ApiOperation("PDF/word 等转换成PDF详情")
    @GetMapping("/knowledge/kbVideVersion/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbVideVersion:info")
    public RespBody<KbVideVersionDto> infoKbVideVersion(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbVideVersionDto kbVideVersionDto = kbVideVersionService.getByIdExtends(id);

        return RespBody.data(kbVideVersionDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "PDF/word 等转换成PDF", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbVideVersion/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbVideVersionDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbVideVersionDto> list = kbVideVersionService.listKbVideVersion(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }

    /**
     * 文件内容对比
     *
     * @param model
     * @param sourceId 源id
     * @param targetId 目标id
     * @return
     */
    @GetMapping("/knowledge/kbVideVersion/getContentComparison")
    public String getContentComparison(Model model, @RequestParam String sourceId, @RequestParam String targetId) {
        Object results = kbVideVersionService.getContentComparison(model, sourceId, targetId);
        if (results instanceof Boolean && (Boolean) results) {
            return "contentComparison.ftl";
        } else if (results instanceof Boolean) {
            return "error.ftl";
        }
        model.addAttribute("errorMsg", "系统还不支持该格式文件在线对比：" + results);
        return "notSupported.ftl";
    }

    /**
     * 文件预览返回 对应的文件
     *
     * @param model
     * @param id
     * @return
     */

   @GetMapping("/knowledge/kbVideVersion/filePreview")
    public String getFilePreview(Model model, @RequestParam String id) {
        try {
            String url = kbVideVersionService.getFilePreview(model, id);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "notSupported.ftl";
        }
    }


    /**
     * 文件预览 返回base64
     *
     * @param id
     * @return
     */
    @GetMapping("/knowledge/kbVideVersion/filePreviewBase")
    @ResponseBody
    public RespBody<String> getfilePreviewBase64(@RequestParam String id) {
        try {
            String base64 = kbVideVersionService.getfilePreviewBase64(id);
            return RespBody.data(base64);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(GlobalRespCode.FAIL);
        }
    }

    @GetMapping("/knowledge/kbVideVersion/filePreviewExecl")
    @ResponseBody
    public void getfilePreviewExecl(HttpServletResponse response, @RequestParam String filePath) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        Path path = Paths.get(new String(Base64.decodeBase64(filePath.getBytes())));
        ServletOutputStream outputStream = response.getOutputStream();
        Files.copy(path, outputStream);
        outputStream.close();

    }
}
