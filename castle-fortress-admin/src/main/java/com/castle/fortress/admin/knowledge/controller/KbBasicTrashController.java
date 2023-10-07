package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicTrashEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicTrashDto;
import com.castle.fortress.admin.knowledge.entity.KbVideoEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.service.KbBasicService;
import com.castle.fortress.admin.knowledge.service.KbBasicTrashService;
import com.castle.fortress.admin.knowledge.service.impl.KbBasicServiceImpl;
import com.castle.fortress.admin.knowledge.service.impl.KbVideoServiceImpl;
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
import java.util.Base64;
import java.util.List;

/**
 * 知识回收表 控制器
 *
 * @author 
 * @since 2023-06-01
 */
@Api(tags="知识回收表管理控制器")
@Controller
public class KbBasicTrashController {
    @Autowired
    private KbBasicTrashService kbBasicTrashService;
    @Autowired
    private KbBasicServiceImpl kbBasicServiceImpl;
    @Autowired
    private KbVideoServiceImpl kbVideoServiceImpl;

    @Autowired
    private EsSearchService esSearchService;

    /**
     * 知识回收表的分页展示
     *
     * @param kbBaseShowDto 知识回收表实体类
     * @param current       当前页
     * @param size          每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识回收表分页展示")
    @GetMapping("/knowledge/kbBasicTrash/page")
    @ResponseBody
    public RespBody<IPage<KbModelTransmitDto>> pageKbBasicTrash(KbBaseShowDto kbBaseShowDto,
                                                                @RequestParam(required = false) Integer current,
                                                                @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbBasicTrashDto> page = new Page<>(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName()
                .equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            IPage<KbModelTransmitDto> pages = kbBasicTrashService.pageKbBasicTrashAdmin(page, kbBaseShowDto);
            return RespBody.data(pages);
        } else {
            IPage<KbModelTransmitDto> pages = kbBasicTrashService.pageKbBasicTrash(page, kbBaseShowDto,uid);
            return RespBody.data(pages);
        }
    }

    /**
     * 知识回收表的列表展示
     *
     * @param kbBasicTrashDto 知识回收表实体类
     * @return
     */
    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识回收表列表展示")
    @GetMapping("/knowledge/kbBasicTrash/list")
    @ResponseBody
    public RespBody<List<KbBasicTrashDto>> listKbBasicTrash(KbBasicTrashDto kbBasicTrashDto) {
        List<KbBasicTrashDto> list = kbBasicTrashService.listKbBasicTrash(kbBasicTrashDto);
        return RespBody.data(list);
    }

    /**
     * 知识回收表保存
     *
     * @param kbBasicTrashDto 知识回收表实体类
     * @return
     */
    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识回收表保存")
    @PostMapping("/knowledge/kbBasicTrash/save")
    @ResponseBody
    public RespBody<String> saveKbBasicTrash(@RequestBody KbBasicTrashDto kbBasicTrashDto) {
        if (kbBasicTrashDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicTrashEntity kbBasicTrashEntity = ConvertUtil.transformObj(kbBasicTrashDto, KbBasicTrashEntity.class);
        if (kbBasicTrashService.save(kbBasicTrashEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识回收表编辑
     *
     * @param kbBasicTrashDto 知识回收表实体类
     * @return
     */
    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识回收表编辑")
    @PostMapping("/knowledge/kbBasicTrash/edit")
    @ResponseBody
    public RespBody<String> updateKbBasicTrash(@RequestBody KbBasicTrashDto kbBasicTrashDto) {
        if (kbBasicTrashDto == null || kbBasicTrashDto.getId() == null || kbBasicTrashDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicTrashEntity kbBasicTrashEntity = ConvertUtil.transformObj(kbBasicTrashDto, KbBasicTrashEntity.class);
        if (kbBasicTrashService.updateById(kbBasicTrashEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识回收表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识回收表删除")
    @PostMapping("/knowledge/kbBasicTrash/delete")
    @ResponseBody
    public RespBody<String> deleteKbBasicTrash(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbBasicTrashService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识回收表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识回收表批量删除")
    @PostMapping("/knowledge/kbBasicTrash/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbBasicTrashBatch(@RequestBody List<KbBasicTrashDto> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<Long> arrayList = new ArrayList<>();
        for (KbBasicTrashDto kbBasicTrashDto : ids) {
            if (kbBasicTrashDto.getType() == 1) {
                arrayList.add(kbBasicTrashDto.getId());
                int delete = kbBasicServiceImpl.delete(kbBasicTrashDto.getBasicId());
                if (delete <= 0) {
                    return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
                }
            } else {
                arrayList.add(kbBasicTrashDto.getId());
                int delete = kbVideoServiceImpl.delete(kbBasicTrashDto.getBasicId());
                if (delete <= 0) {
                    return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
                }
            }

        }
        if (kbBasicTrashService.removeByIds(arrayList)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识回收表批量恢复")
    @PostMapping("/knowledge/kbBasicTrash/editBatch")
    @ResponseBody
    public RespBody<String> editKbBasicTrashBatch(@RequestBody List<KbBasicTrashDto> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicEntity kbBasicEntity = new KbBasicEntity();

        List<Long> arrayList = new ArrayList<>();
        for (KbBasicTrashDto kbBasicTrashDto : ids) {
            if (kbBasicTrashDto.getType() == 1) {
                kbBasicEntity.setId(kbBasicTrashDto.getBasicId());
                kbBasicEntity.setIsDeleted(2);
                arrayList.add(kbBasicTrashDto.getId());
                esSearchService.updateByIdToIsDelete(String.valueOf(kbBasicTrashDto.getBasicId()), "2");
                boolean delete = kbBasicServiceImpl.updateById(kbBasicEntity);
                if (!delete) {
                    return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
                }
            } else {
                arrayList.add(kbBasicTrashDto.getId());
                boolean delete = kbVideoServiceImpl.updateByVideo(kbBasicTrashDto.getBasicId());
                if (!delete) {
                    return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
                }
            }

        }
        if (kbBasicTrashService.removeByIds(arrayList)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识回收表详情
     *
     * @param id 知识回收表id
     * @return
     */
    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识回收表详情")
    @GetMapping("/knowledge/kbBasicTrash/info")
    @ResponseBody
    public RespBody<KbBasicTrashDto> infoKbBasicTrash(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBasicTrashEntity kbBasicTrashEntity = kbBasicTrashService.getById(id);
        KbBasicTrashDto kbBasicTrashDto = ConvertUtil.transformObj(kbBasicTrashEntity, KbBasicTrashDto.class);

        return RespBody.data(kbBasicTrashDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识回收表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbBasicTrash/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbBasicTrashDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbBasicTrashDto> list = kbBasicTrashService.listKbBasicTrash(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
