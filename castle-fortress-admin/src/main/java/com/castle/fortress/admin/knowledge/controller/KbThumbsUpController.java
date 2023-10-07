package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbThumbsUpEntity;
import com.castle.fortress.admin.knowledge.dto.KbThumbsUpDto;
import com.castle.fortress.admin.knowledge.service.KbThumbsUpService;
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
 * 知识点赞表 控制器
 *
 * @author
 * @since 2023-05-11
 */
@Api(tags = "知识点赞表管理控制器")
@Controller
public class KbThumbsUpController {
    @Autowired
    private KbThumbsUpService kbThumbsUpService;

    /**
     * 知识点赞表的分页展示
     *
     * @param kbThumbsUpDto 知识点赞表实体类
     * @param current       当前页
     * @param size          每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识点赞表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识点赞表分页展示")
    @GetMapping("/knowledge/kbThumbsUp/page")
    @ResponseBody
    public RespBody<IPage<KbThumbsUpDto>> pageKbThumbsUp(KbThumbsUpDto kbThumbsUpDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbThumbsUpDto> page = new Page(pageIndex, pageSize);
        IPage<KbThumbsUpDto> pages = kbThumbsUpService.pageKbThumbsUp(page, kbThumbsUpDto);

        return RespBody.data(pages);
    }

    /**
     * 知识点赞表的列表展示
     *
     * @param kbThumbsUpDto 知识点赞表实体类
     * @return
     */
    @CastleLog(operLocation = "知识点赞表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识点赞表列表展示")
    @GetMapping("/knowledge/kbThumbsUp/list")
    @ResponseBody
    public RespBody<List<KbThumbsUpDto>> listKbThumbsUp(KbThumbsUpDto kbThumbsUpDto) {
        List<KbThumbsUpDto> list = kbThumbsUpService.listKbThumbsUp(kbThumbsUpDto);
        return RespBody.data(list);
    }


    /**
     * 知识点赞表保存
     *
     * @param kbThumbsUpDto 知识点赞表实体类
     * @return
     */
    @CastleLog(operLocation = "知识点赞表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识点赞表保存")
    @PostMapping("/knowledge/kbThumbsUp/save")
    @ResponseBody
    public RespBody<String> saveKbThumbsUp(@RequestBody KbThumbsUpDto kbThumbsUpDto) {
        if (kbThumbsUpDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        kbThumbsUpDto.setUserId(userId);
        KbThumbsUpEntity kbThumbsUpEntity = ConvertUtil.transformObj(kbThumbsUpDto, KbThumbsUpEntity.class);
        //判断点赞是否已经存在
        KbThumbsUpEntity exites = kbThumbsUpService.checkExites(kbThumbsUpDto);
        if (exites != null) {
            if (kbThumbsUpService.removeUp(kbThumbsUpDto)) {
                return RespBody.data("取消成功");
            } else {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        } else {
            if (kbThumbsUpService.save(kbThumbsUpEntity)) {
                return RespBody.data("点赞成功");
            } else {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        }

    }

    /**
     * 知识点赞表编辑
     *
     * @param kbThumbsUpDto 知识点赞表实体类
     * @return
     */
    @CastleLog(operLocation = "知识点赞表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识点赞表编辑")
    @PostMapping("/knowledge/kbThumbsUp/edit")
    @ResponseBody
    public RespBody<String> updateKbThumbsUp(@RequestBody KbThumbsUpDto kbThumbsUpDto) {
        if (kbThumbsUpDto == null || kbThumbsUpDto.getId() == null || kbThumbsUpDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbThumbsUpEntity kbThumbsUpEntity = ConvertUtil.transformObj(kbThumbsUpDto, KbThumbsUpEntity.class);
        if (kbThumbsUpService.updateById(kbThumbsUpEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识点赞表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识点赞表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识点赞表删除")
    @PostMapping("/knowledge/kbThumbsUp/delete")
    @ResponseBody
    public RespBody<String> deleteKbThumbsUp(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbThumbsUpService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识点赞表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识点赞表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识点赞表批量删除")
    @PostMapping("/knowledge/kbThumbsUp/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbThumbsUpBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbThumbsUpService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识点赞表详情
     *
     * @param id 知识点赞表id
     * @return
     */
    @CastleLog(operLocation = "知识点赞表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识点赞表详情")
    @GetMapping("/knowledge/kbThumbsUp/info")
    @ResponseBody
    public RespBody<KbThumbsUpDto> infoKbThumbsUp(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbThumbsUpEntity kbThumbsUpEntity = kbThumbsUpService.getById(id);
        KbThumbsUpDto kbThumbsUpDto = ConvertUtil.transformObj(kbThumbsUpEntity, KbThumbsUpDto.class);

        return RespBody.data(kbThumbsUpDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识点赞表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbThumbsUp/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbThumbsUpDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbThumbsUpDto> list = kbThumbsUpService.listKbThumbsUp(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
