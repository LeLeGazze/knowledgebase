package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbGroupsEntity;
import com.castle.fortress.admin.knowledge.dto.KbGroupsDto;
import com.castle.fortress.admin.knowledge.entity.KbGroupsUserEntity;
import com.castle.fortress.admin.knowledge.service.KbGroupsService;
import com.castle.fortress.admin.knowledge.service.KbGroupsUserService;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.SysUserService;
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
 * 知识库用户组管理 控制器
 *
 * @author sunhr
 * @since 2023-04-22
 */
@Api(tags = "知识库用户组管理管理控制器")
@Controller
public class KbGroupsController {
    @Autowired
    private KbGroupsService kbGroupsService;
    @Autowired
    private KbGroupsUserService kbGroupsUserService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 知识库用户组管理的分页展示
     *
     * @param kbGroupsDto 知识库用户组管理实体类
     * @param current     当前页
     * @param size        每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库用户组管理分页展示")
    @GetMapping("/knowledge/kbGroups/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:pageList")
    public RespBody<IPage<KbGroupsDto>> pageKbGroups(KbGroupsDto kbGroupsDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbGroupsDto> page = new Page(pageIndex, pageSize);
        IPage<KbGroupsDto> pages = kbGroupsService.pageKbGroupsExtends(page, kbGroupsDto);

        return RespBody.data(pages);
    }

    /**
     * 知识库用户组管理的列表展示
     *
     * @param kbGroupsDto 知识库用户组管理实体类
     * @return
     */
    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库用户组管理列表展示")
    @GetMapping("/knowledge/kbGroups/list")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:list")
    public RespBody<List<KbGroupsDto>> listKbGroups(KbGroupsDto kbGroupsDto) {
        List<KbGroupsDto> list = kbGroupsService.listKbGroups(kbGroupsDto);
        return RespBody.data(list);
    }

    /**
     * 知识库用户组管理保存
     *
     * @param kbGroupsDto 知识库用户组管理实体类
     * @return
     */
    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识库用户组管理保存")
    @PostMapping("/knowledge/kbGroups/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:save")
    public RespBody<String> saveKbGroups(@RequestBody KbGroupsDto kbGroupsDto) {
        if (kbGroupsDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //给定赛选条件
        kbGroupsDto.setIsDeleted(2);
        //获取当前用户
        SysUser sysUser = WebUtil.currentUser();
        KbGroupsEntity kbGroupsEntity = ConvertUtil.transformObj(kbGroupsDto, KbGroupsEntity.class);
        if (!(sysUser == null)) {
            kbGroupsEntity.setCreateUser(sysUser.getId());
            kbGroupsEntity.setCreateUserName(sysUser.getLoginName());
        }
        kbGroupsDto.setSort(null);
        //查询满足条件的数据
        List<KbGroupsDto> list = kbGroupsService.listKbGroups(kbGroupsDto);
        //判断群组是否存在
        for (KbGroupsDto dto : list) {
            if (dto.getName().equals(kbGroupsEntity.getName())) {
                return RespBody.fail("群组已存在！");
            }
        }
        //保存群组
        if (kbGroupsService.save(kbGroupsEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库用户组管理编辑
     *
     * @param kbGroupsDto 知识库用户组管理实体类
     * @return
     */
    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识库用户组管理编辑")
    @PostMapping("/knowledge/kbGroups/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:edit")
    public RespBody<String> updateKbGroups(@RequestBody KbGroupsDto kbGroupsDto) {
        if (kbGroupsDto == null || kbGroupsDto.getId() == null || kbGroupsDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbGroupsEntity kbGroupsEntity = ConvertUtil.transformObj(kbGroupsDto, KbGroupsEntity.class);
        if (kbGroupsService.updateById(kbGroupsEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库用户组管理删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库用户组管理删除")
    @DeleteMapping("/knowledge/kbGroups/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:delete")
    public RespBody<String> deleteKbGroups(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbGroupsEntity kbGroups = kbGroupsService.findById(id);
        if (kbGroups == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        List<KbGroupsUserEntity> byId = kbGroupsUserService.findById(id);
        if (byId != null && byId.size() > 0) {
            return RespBody.fail("当前群组下还有用户成员，无法删除！");
        }
        KbGroupsDto kbGroupsDto = ConvertUtil.transformObj(kbGroups, KbGroupsDto.class);
        List<KbGroupsDto> kbGroupsDtos = kbGroupsService.findpId(kbGroupsDto.getId());
        if (kbGroupsDtos.size() == 0) {
            if (kbGroupsService.removeById(kbGroupsDto.getId())) {
                return RespBody.data("操作成功");
            } else {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        } else {
            return RespBody.fail("当前群组下还有子群组，无法删除");
        }
    }


    /**
     * 知识库用户组管理批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库用户组管理批量删除")
    @PostMapping("/knowledge/kbGroups/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:deleteBatch")
    public RespBody<String> deleteKbGroupsBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        for (Long id : ids) {
            List<KbGroupsUserEntity> user = kbGroupsUserService.findById(id);
            if (user.size() != 0) {
                return RespBody.fail("当前群组下还有用户成员，无法删除！");
            }
        }
        if (kbGroupsService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库用户组管理详情
     *
     * @param id 知识库用户组管理id
     * @return
     */
    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库用户组管理详情")
    @GetMapping("/knowledge/kbGroups/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:info")
    public RespBody<KbGroupsDto> infoKbGroups(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbGroupsDto kbGroupsDto = kbGroupsService.getByIdExtends(id);

        return RespBody.data(kbGroupsDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbGroups/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbGroupsDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbGroupsDto> list = kbGroupsService.listKbGroups(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }

    /**
     * 群组成员查询
     *
     * @param id
     * @return
     */

    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("群组成员查询")
    @GetMapping("/knowledge/kbGroups/listFind")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:listFind")
    public RespBody<IPage<SysUserDto>> listFindUserId(@RequestParam(required = false) Long id,
                                                      @RequestParam(required = false) Integer status,
                                                      @RequestParam(required = false) String name,
                                                      @RequestParam(required = false) Integer current,
                                                      @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<SysUserDto> page = new Page<>(pageIndex, pageSize);
        IPage<SysUserDto> ids = kbGroupsUserService.listFindUser(id, name, page, status);
        return RespBody.data(ids);
    }


    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("全部成员查询成员查询")
    @GetMapping("/knowledge/kbGroups/listFindAll")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:listFindAll")
    public RespBody<List<SysUser>> listFindUser() {
        List<SysUser> ids = sysUserService.findAll();
        return RespBody.data(ids);
    }


    @CastleLog(operLocation = "知识库用户组管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("根据pid查询群组")
    @GetMapping("/knowledge/kbGroups/listById")
    @ResponseBody
    @RequiresPermissions("knowledge:kbGroups:list")
    public RespBody<List<KbGroupsDto>> listFindpId(Long pId) {
        List<KbGroupsDto> ids = kbGroupsService.findpId(pId);
        return RespBody.data(ids);
    }


}
