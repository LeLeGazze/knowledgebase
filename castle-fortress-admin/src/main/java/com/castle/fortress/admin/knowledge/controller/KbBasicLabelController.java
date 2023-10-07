package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicLabelDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.service.KbBasicLabelService;
import com.castle.fortress.admin.knowledge.service.KbModelLabelService;
import com.castle.fortress.admin.knowledge.service.KbModelService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 知识与标签的中间表 控制器
 *
 * @author
 * @since 2023-04-28
 */
@Api(tags = "知识与标签的中间表管理控制器")
@Controller
public class KbBasicLabelController {
    @Autowired
    private KbBasicLabelService kbBasicLabelService;
    @Autowired
    private KbModelLabelService kbModelLabelService;

    /**
     * 知识与标签的中间表的分页展示
     *
     * @param kbBasicLabelDto 知识与标签的中间表实体类
     * @param current         当前页
     * @param size            每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识与标签的中间表分页展示")
    @GetMapping("/knowledge/kbBasicLabel/page")
    @ResponseBody
    public RespBody<IPage<KbBasicLabelDto>> pageKbBasicLabel(KbBasicLabelDto kbBasicLabelDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbBasicLabelDto> page = new Page(pageIndex, pageSize);
        IPage<KbBasicLabelDto> pages = kbBasicLabelService.pageKbBasicLabelExtends(page, kbBasicLabelDto);

        return RespBody.data(pages);
    }

    /**
     * 知识与标签的中间表的列表展示
     *
     * @param bId 知识与标签的中间表实体类
     * @return
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识与标签的中间表列表展示")
    @GetMapping("/knowledge/kbBasicLabel/list")
    @ResponseBody
    public RespBody<List<KbModelLabelEntity>> listKbBasicLabel(@RequestParam Long bId) {
        if (bId == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<KbModelLabelEntity> kbModelLabelEntities = kbBasicLabelService.listBybId(bId);
        return RespBody.data(kbModelLabelEntities);
    }

    /**
     * 知识与标签的中间表的列表展示
     *
     * @param kbBasicLabelDto 知识与标签的中间表实体类
     * @return
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识与标签的中间表列表展示")
    @GetMapping("/knowledge/kbBasicLabel/findByUidAuthLabel")
    @ResponseBody
    public RespBody<List<String>> findByUidAuthLabel(KbBasicLabelDto kbBasicLabelDto) {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        //首页标签展示
        Long uid = sysUser.getId();
        // 管理员查询当前用户所有的
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<String> labelEntities = kbBasicLabelService.findByUidAuthLabelAdmin(uid);
            return RespBody.data(labelEntities);
        } else {
            // 普通用户只查询自己权限内的标签
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            List<String> labelEntities = kbBasicLabelService.findByUidAuthLabel(uid, integers);
            return RespBody.data(labelEntities);
        }
    }

    /**
     * 知识与标签的中间表保存
     *
     * @param kbBasicLabelDto 知识与标签的中间表实体类
     * @return
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识与标签的中间表保存")
    @PostMapping("/knowledge/kbBasicLabel/save")
    @ResponseBody
    public RespBody<String> saveKbBasicLabel(@RequestBody KbBasicLabelDto kbBasicLabelDto) {
        if (kbBasicLabelDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicLabelEntity kbBasicLabelEntity = ConvertUtil.transformObj(kbBasicLabelDto, KbBasicLabelEntity.class);
        if (kbBasicLabelService.save(kbBasicLabelEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识与标签的中间表编辑
     *
     * @param kbBasicLabelDto 知识与标签的中间表实体类
     * @return
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识与标签的中间表编辑")
    @PostMapping("/knowledge/kbBasicLabel/edit")
    @ResponseBody
    public RespBody<String> updateKbBasicLabel(@RequestBody KbBasicLabelDto kbBasicLabelDto) {
        if (kbBasicLabelDto == null || kbBasicLabelDto.getId() == null || kbBasicLabelDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicLabelEntity kbBasicLabelEntity = ConvertUtil.transformObj(kbBasicLabelDto, KbBasicLabelEntity.class);
        if (kbBasicLabelService.updateById(kbBasicLabelEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识与标签的中间表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识与标签的中间表删除")
    @PostMapping("/knowledge/kbBasicLabel/delete")
    @ResponseBody
    public RespBody<String> deleteKbBasicLabel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicLabelService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识与标签的中间表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识与标签的中间表批量删除")
    @PostMapping("/knowledge/kbBasicLabel/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbBasicLabelBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicLabelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识与标签的中间表详情
     *
     * @param id 知识与标签的中间表id
     * @return
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识与标签的中间表详情")
    @GetMapping("/knowledge/kbBasicLabel/info")
    @ResponseBody
    public RespBody<KbBasicLabelDto> infoKbBasicLabel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicLabelDto kbBasicLabelDto = kbBasicLabelService.getByIdExtends(id);

        return RespBody.data(kbBasicLabelDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbBasicLabel/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbBasicLabelDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbBasicLabelDto> list = kbBasicLabelService.listKbBasicLabel(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


    @CastleLog(operLocation = "知识与标签的中间表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签使用次数")
    @GetMapping("/knowledge/kbBasicLabel/labelCount")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBasicLabel:pageList")
    public RespBody<Integer> labelCount(@RequestParam Long lebalId) {
        Integer labelCount = kbBasicLabelService.labelCount(lebalId);
        return RespBody.data(labelCount);
    }


}
