package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbCollectEntity;
import com.castle.fortress.admin.knowledge.dto.KbCollectDto;
import com.castle.fortress.admin.knowledge.entity.KbThumbsUpEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.service.KbCollectService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 知识收藏表 控制器
 *
 * @author
 * @since 2023-05-12
 */
@Api(tags = "知识收藏表管理控制器")
@Controller
public class KbCollectController {
    @Autowired
    private KbCollectService kbCollectService;

    /**
     * 知识收藏表的分页展示
     *
     * @param kbCollectDto 知识收藏表实体类
     * @param current      当前页
     * @param size         每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识收藏表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识收藏表分页展示")
    @GetMapping("/knowledge/kbCollect/page")
    @ResponseBody
    public RespBody<IPage<KbCollectDto>> pageKbCollect(KbCollectDto kbCollectDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbCollectDto> page = new Page(pageIndex, pageSize);
        IPage<KbCollectDto> pages = kbCollectService.pageKbCollect(page, kbCollectDto);

        return RespBody.data(pages);
    }

    /**
     * 知识收藏表的列表展示
     *
     * @param kbCollectDto 知识收藏表实体类
     * @return
     */
    @CastleLog(operLocation = "知识收藏表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识收藏表列表展示")
    @GetMapping("/knowledge/kbCollect/list")
    @ResponseBody
    public RespBody<List<KbCollectDto>> listKbCollect(KbCollectDto kbCollectDto) {
        List<KbCollectDto> list = kbCollectService.listKbCollect(kbCollectDto);
        return RespBody.data(list);
    }

    /**
     * 知识收藏表保存
     *
     * @param kbCollectDto 知识收藏表实体类
     * @return
     */
    @CastleLog(operLocation = "知识收藏表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识收藏表保存")
    @PostMapping("/knowledge/kbCollect/save")
    @ResponseBody
    public RespBody<String> saveKbCollect(@RequestBody KbCollectDto kbCollectDto) {
        if (kbCollectDto == null || kbCollectDto.getType() == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        kbCollectDto.setUserId(userId);
        KbCollectEntity kbCollectEntity = ConvertUtil.transformObj(kbCollectDto, KbCollectEntity.class);
        //判断点赞是否已经存在
        KbCollectEntity byid = kbCollectService.findByid(userId, kbCollectDto.getBasicId());
        if (byid == null) {
            if (kbCollectService.save(kbCollectEntity)) {
                return RespBody.data("收藏成功");
            } else {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        } else {
            if (kbCollectService.removeCollect(userId, kbCollectDto.getBasicId())) {
                return RespBody.data("操作成功");
            } else {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        }
    }

    /**
     * 知识收藏表编辑
     *
     * @param kbCollectEntity 知识收藏表实体类
     * @return
     */
    @CastleLog(operLocation = "知识收藏表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识收藏表编辑")
    @PostMapping("/knowledge/kbCollect/edit")
    @ResponseBody
    public RespBody<String> updateKbCollect(@RequestBody KbCollectEntity kbCollectEntity) {
        if (kbCollectEntity == null || kbCollectEntity.getId() == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();
        List<KbCollectEntity> collects = new ArrayList<>();
        if (kbCollectService.updateBatchById(collects)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识收藏表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识收藏表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识收藏表删除")
    @PostMapping("/knowledge/kbCollect/delete")
    @ResponseBody
    public RespBody<String> deleteKbCollect(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbCollectService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识收藏表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识收藏表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识收藏表批量删除")
    @PostMapping("/knowledge/kbCollect/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbCollectBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbCollectService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识收藏表详情
     *
     * @param id 知识收藏表id
     * @return
     */
    @CastleLog(operLocation = "知识收藏表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识收藏表详情")
    @GetMapping("/knowledge/kbCollect/info")
    @ResponseBody
    public RespBody<KbCollectDto> infoKbCollect(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCollectEntity kbCollectEntity = kbCollectService.getById(id);
        KbCollectDto kbCollectDto = ConvertUtil.transformObj(kbCollectEntity, KbCollectDto.class);

        return RespBody.data(kbCollectDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识收藏表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbCollect/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbCollectDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbCollectDto> list = kbCollectService.listKbCollect(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


    @ApiOperation("我的收藏查询")
    @GetMapping("/knowledge/kbCollect/selectCollect")
    @ResponseBody
    public RespBody<IPage<KbModelTransmitDto>> selectCollectByLike(KbBaseShowDto kbBaseShowDto,
                                                                   @RequestParam(required = false) Integer current,
                                                                   @RequestParam(required = false) Integer size) {

        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        String fromTime = kbBaseShowDto.getFromTime();
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        if (fromTime != null) {
            String[] s = fromTime.split(" ");
            kbBaseShowDto.setFromTime(s[0]);
        }
        Page<KbModelTransmitDto> page = new Page<>(pageIndex, pageSize);
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            IPage<KbModelTransmitDto> pages = kbCollectService.findBasicByLikeAdmin(kbBaseShowDto, page);
            return RespBody.data(pages);
        } else {
            IPage<KbModelTransmitDto> basicByLike = kbCollectService.findBasicByLike(kbBaseShowDto, page);
            return RespBody.data(basicByLike);
        }
    }
}
