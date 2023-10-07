package com.castle.fortress.admin.check.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.check.service.CheckService;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.check.entity.KbDuplicateCheckEntity;
import com.castle.fortress.admin.check.dto.KbDuplicateCheckDto;
import com.castle.fortress.admin.check.service.KbDuplicateCheckService;
import com.castle.fortress.admin.knowledge.dto.KbVideVersionDto;
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
import org.springframework.context.ApplicationContext;
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
 * 知识库查重表 控制器
 *
 * @author
 * @since 2023-07-15
 */
@Api(tags = "知识库查重表管理控制器")
@Controller
public class KbDuplicateCheckController {
    @Autowired
    private KbDuplicateCheckService kbDuplicateCheckService;
    @Autowired
    private ApplicationContext context;

    /**
     * 知识库查重表的分页展示
     *
     * @param kbDuplicateCheckDto 知识库查重表实体类
     * @param current             当前页
     * @param size                每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识库查重表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库查重表分页展示")
    @GetMapping("/check/kbDuplicateCheck/page")
    @ResponseBody
//    @RequiresPermissions("check:kbDuplicateCheck:pageList")
    public RespBody<IPage<KbDuplicateCheckDto>> pageKbDuplicateCheck(KbDuplicateCheckDto kbDuplicateCheckDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbDuplicateCheckDto> page = new Page(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        kbDuplicateCheckDto.setCreateUser(sysUser.getId());
        IPage<KbDuplicateCheckDto> pages = kbDuplicateCheckService.pageKbDuplicateCheck(page, kbDuplicateCheckDto);

        return RespBody.data(pages);
    }

    /**
     * 知识库查重表的列表展示
     *
     * @param kbDuplicateCheckDto 知识库查重表实体类
     * @return
     */
    @CastleLog(operLocation = "知识库查重表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库查重表列表展示")
    @GetMapping("/check/kbDuplicateCheck/list")
    @ResponseBody
    public RespBody<List<KbDuplicateCheckDto>> listKbDuplicateCheck(KbDuplicateCheckDto kbDuplicateCheckDto) {
        List<KbDuplicateCheckDto> list = kbDuplicateCheckService.listKbDuplicateCheck(kbDuplicateCheckDto);
        return RespBody.data(list);
    }

    /**
     * 知识库查重表保存
     *
     * @param kbDuplicateCheckDto 知识库查重表实体类
     * @return
     */
    @CastleLog(operLocation = "知识库查重表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识库查重表保存")
    @PostMapping("/check/kbDuplicateCheck/save")
    @ResponseBody
    public RespBody<String> saveKbDuplicateCheck(@RequestBody KbDuplicateCheckDto kbDuplicateCheckDto) {
        if (kbDuplicateCheckDto == null || StrUtil.isEmpty(kbDuplicateCheckDto.getBeanName())) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        try {
            SysUser sysUser = WebUtil.currentUser();
            if (sysUser == null) {
                throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
            }
            CheckService checkService = context.getBean(kbDuplicateCheckDto.getBeanName()+"Check", CheckService.class);
            checkService.taskProcessing(kbDuplicateCheckDto,sysUser.getId()); // 运行任务
            return RespBody.data("提交成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }
    /**
     * 知识库查重表编辑
     *
     * @param kbDuplicateCheckDto 知识库查重表实体类
     * @return
     */
    @CastleLog(operLocation = "知识库查重表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识库查重表编辑")
    @PostMapping("/check/kbDuplicateCheck/edit")
    @ResponseBody
    @RequiresPermissions("check:kbDuplicateCheck:edit")
    public RespBody<String> updateKbDuplicateCheck(@RequestBody KbDuplicateCheckDto kbDuplicateCheckDto) {
        if (kbDuplicateCheckDto == null || kbDuplicateCheckDto.getId() == null || kbDuplicateCheckDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbDuplicateCheckEntity kbDuplicateCheckEntity = ConvertUtil.transformObj(kbDuplicateCheckDto, KbDuplicateCheckEntity.class);
        if (kbDuplicateCheckService.updateById(kbDuplicateCheckEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库查重表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识库查重表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库查重表删除")
    @PostMapping("/check/kbDuplicateCheck/delete")
    @ResponseBody
    @RequiresPermissions("check:kbDuplicateCheck:delete")
    public RespBody<String> deleteKbDuplicateCheck(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbDuplicateCheckService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识库查重表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识库查重表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库查重表批量删除")
    @PostMapping("/check/kbDuplicateCheck/deleteBatch")
    @ResponseBody
//    @RequiresPermissions("check:kbDuplicateCheck:deleteBatch")
    public RespBody<String> deleteKbDuplicateCheckBatch(@RequestBody List<String> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbDuplicateCheckService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库查重表详情
     *
     * @param id 知识库查重表id
     * @return
     */
    @CastleLog(operLocation = "知识库查重表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库查重表详情")
    @GetMapping("/check/kbDuplicateCheck/info")
    @ResponseBody
    @RequiresPermissions("check:kbDuplicateCheck:info")
    public RespBody<KbDuplicateCheckDto> infoKbDuplicateCheck(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbDuplicateCheckEntity kbDuplicateCheckEntity = kbDuplicateCheckService.getById(id);
        KbDuplicateCheckDto kbDuplicateCheckDto = ConvertUtil.transformObj(kbDuplicateCheckEntity, KbDuplicateCheckDto.class);

        return RespBody.data(kbDuplicateCheckDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识库查重表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/check/kbDuplicateCheck/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbDuplicateCheckDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbDuplicateCheckDto> list = kbDuplicateCheckService.listKbDuplicateCheck(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }

    @CastleLog(operLocation = "查重预览", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查重文件预览")
    @PostMapping("/check/kbDuplicateCheck/fileShow")
    @ResponseBody
    public void fileShow(@RequestBody KbDuplicateCheckDto kbDuplicateCheckDto, HttpServletResponse response) {
        if (kbDuplicateCheckDto.getId() == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        kbDuplicateCheckService.fileShow(response, kbDuplicateCheckDto.getId());
    }


    @CastleLog(operLocation = "查重下载", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查重下载")
    @PostMapping("/check/kbDuplicateCheck/insertDownload")
    @ResponseBody
    public void downloadInsert(@RequestBody KbDuplicateCheckDto kbDuplicateCheckDto, HttpServletResponse response) {
        if (StrUtil.isEmpty(kbDuplicateCheckDto.getId())) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        kbDuplicateCheckService.downloadFile(response, kbDuplicateCheckDto.getId(), sysUser.getRealName());
    }
}
