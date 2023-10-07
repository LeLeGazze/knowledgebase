package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.ProtocolDto;
import com.castle.fortress.admin.system.entity.ProtocolEntity;
import com.castle.fortress.admin.system.service.ProtocolService;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 协议管理 控制器
 *
 * @author majunjie
 * @since 2022-01-28
 */
@Api(tags = "协议管理管理控制器")
@Controller
public class ProtocolController {
    @Autowired
    private ProtocolService protocolService;

    /**
     * 协议管理的分页展示
     *
     * @param protocolDto 协议管理实体类
     * @param current     当前页
     * @param size        每页记录数
     * @return
     */
    @ApiOperation("协议管理分页展示")
    @GetMapping("/system/protocol/page")
    @ResponseBody
    @RequiresPermissions("system:protocol:pageList")
    public RespBody<IPage<ProtocolDto>> pageProtocol(ProtocolDto protocolDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<ProtocolDto> page = new Page(pageIndex, pageSize);
        IPage<ProtocolDto> pages = protocolService.pageProtocolExtends(page, protocolDto);

        return RespBody.data(pages);
    }

    /**
     * 协议管理的列表展示
     *
     * @param protocolDto 协议管理实体类
     * @return
     */
    @ApiOperation("协议管理列表展示")
    @GetMapping("/system/protocol/list")
    @ResponseBody
    public RespBody<List<ProtocolDto>> listProtocol(ProtocolDto protocolDto) {
        List<ProtocolDto> list = protocolService.listProtocol(protocolDto);
        return RespBody.data(list);
    }

    /**
     * 协议管理保存
     *
     * @param protocolDto 协议管理实体类
     * @return
     */
    @ApiOperation("协议管理保存")
    @PostMapping("/system/protocol/save")
    @ResponseBody
    @RequiresPermissions("system:protocol:save")
    public RespBody<String> saveProtocol(@RequestBody ProtocolDto protocolDto) {
        if (protocolDto == null || StrUtil.isEmpty(protocolDto.getCode())) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ProtocolEntity protocolEntity = ConvertUtil.transformObj(protocolDto, ProtocolEntity.class);
        ProtocolDto codeProtocolEntity = protocolService.getByCode(protocolEntity.getCode());
        if (codeProtocolEntity != null) {
            return RespBody.fail("协议编码已存在,不允许重复");
        }
        if (protocolService.save(protocolEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 协议管理编辑
     *
     * @param protocolDto 协议管理实体类
     * @return
     */
    @ApiOperation("协议管理编辑")
    @PostMapping("/system/protocol/edit")
    @ResponseBody
    @RequiresPermissions("system:protocol:edit")
    public RespBody<String> updateProtocol(@RequestBody ProtocolDto protocolDto) {
        if (protocolDto == null || protocolDto.getId() == null || protocolDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ProtocolEntity protocolEntity = ConvertUtil.transformObj(protocolDto, ProtocolEntity.class);
        ProtocolDto codeProtocolDto = protocolService.getByCode(protocolEntity.getCode());
        if (codeProtocolDto != null && !Objects.equals(codeProtocolDto.getId(), protocolEntity.getId())) {
            return RespBody.fail("协议编码已存在,不允许重复");
        }
        if (protocolService.updateById(protocolEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 协议管理删除
     *
     * @param id
     * @return
     */
    @ApiOperation("协议管理删除")
    @PostMapping("/system/protocol/delete")
    @ResponseBody
    @RequiresPermissions("system:protocol:delete")
    public RespBody<String> deleteProtocol(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (protocolService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 协议管理批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("协议管理批量删除")
    @PostMapping("/system/protocol/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:protocol:deleteBatch")
    public RespBody<String> deleteProtocolBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (protocolService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 协议管理详情
     *
     * @param id 协议管理id
     * @return
     */
    @ApiOperation("协议管理详情")
    @GetMapping("/system/protocol/info")
    @ResponseBody
    @RequiresPermissions("system:protocol:info")
    public RespBody<ProtocolDto> infoProtocol(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ProtocolDto protocolDto = protocolService.getByIdExtends(id);

        return RespBody.data(protocolDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @PostMapping("/system/protocol/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<ProtocolDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<ProtocolDto> list = protocolService.listProtocol(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
