package com.castle.fortress.admin.knowledge.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbSubjectWarehouseEntity;
import com.castle.fortress.admin.knowledge.dto.KbSubjectWarehouseDto;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.service.KbSubjectWarehouseService;
import com.castle.fortress.admin.knowledge.service.KbWarehouseAuthService;
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
import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 主题知识仓库 控制器
 *
 * @author lyz
 * @since 2023-04-24
 */
@Api(tags = "主题知识仓库管理控制器")
@Controller
public class KbSubjectWarehouseController {
    @Autowired
    private KbSubjectWarehouseService kbSubjectWarehouseService;
    @Autowired
    private KbWarehouseAuthService kbWarehouseAuthService;

    /**
     * 主题知识仓库的分页展示
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库分页展示")
    @GetMapping("/knowledge/kbSubjectWarehouse/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:pageList")
    public RespBody<List<KbSubjectWarehouseDto>> pageKbSubjectWarehouse() {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        Long uid = sysUser.getId();
        // 登录的高级管理员可以查看全部
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.findByWid(new ArrayList<KbWarehouseAuthEntity>(), 2);
            return RespBody.data(list);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", null);
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(null);
            }
            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.findByWid(uAuths, 1);
            return RespBody.data(list);
        }
    }

    /**
     * 主题知识仓库的列表展示
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库列表展示")
    @GetMapping("/knowledge/kbSubjectWarehouse/list")
    @ResponseBody
    public RespBody<List<KbSubjectWarehouseDto>> listKbSubjectWarehouse(KbSubjectWarehouseDto kbSubjectWarehouseDto) {

        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        Long uid = sysUser.getId();
        // 登录的高级管理员可以查看全部
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.findByWid(new ArrayList<KbWarehouseAuthEntity>(), 0);
            return RespBody.data(list);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", null);
            if (uAuths == null || uAuths.size() == 0) {
                // 无数据
                List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.findByHomeShow();
                return RespBody.data(list);
            }
            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.findByWid(uAuths, 0);
            return RespBody.data(list);
        }

//        List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.listKbSubjectWarehouse(kbSubjectWarehouseDto);

    }

    /**
     * 主题知识仓库首页展示列表展示
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库首页展示列表展示")
    @GetMapping("/knowledge/kbSubjectWarehouse/showList")
    @ResponseBody
    public RespBody<List<KbSubjectWarehouseEntity>> showListKbSubjectWarehouse(KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        List<KbSubjectWarehouseEntity> list = kbSubjectWarehouseService.showList();
        return RespBody.data(list);
    }

    /**
     * 查询目录下的知识
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查询目录下的知识")
    @GetMapping("/knowledge/kbSubjectWarehouse/findByListToBackstage")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:findByListToBackstage")
    public RespBody<List<KbSubjectWarehouseDto>> findByListToBackstage(KbSubjectWarehouseDto kbSubjectWarehouseDto) {

        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        Long uid = sysUser.getId();
        // 登录的高级管理员可以查看全部
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.listKbSubjectToCategory(new ArrayList<KbWarehouseAuthEntity>());
            return RespBody.data(list);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", null);
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(null);
            }
            List<Integer> asList = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );

            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.listKbSubjectToCategory(uAuths, uid, asList);
            return RespBody.data(list);
        }
    }

    /**
     * 查询目录下的知识
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查询目录下的知识")
    @GetMapping("/knowledge/kbSubjectWarehouse/findByListToCategory")
    @ResponseBody
    public RespBody<List<KbSubjectWarehouseDto>> listKbSubjectToCategory(KbSubjectWarehouseDto kbSubjectWarehouseDto) {

        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        Long uid = sysUser.getId();
        // 登录的高级管理员可以查看全部
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.listKbSubjectToCategory(new ArrayList<KbWarehouseAuthEntity>());
            return RespBody.data(list);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", null);
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(null);
            }
            List<Integer> asList = Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );

            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.listKbSubjectToCategory(uAuths, uid, asList);
            return RespBody.data(list);
        }
    }

    /**
     * 查询目录下的知识
     *
     * @param isSearch 首页查询   1 个人中心
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查询目录下的知识")
    @GetMapping("/knowledge/kbSubjectWarehouse/findByListToCategoryVideo")
    @ResponseBody
    public RespBody<KbSubjectWarehouseDto> findByListToCategoryVideo(@RequestParam(required = false) String isSearch) {

        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        Long uid = sysUser.getId();
        // 登录的高级管理员可以查看全部
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            KbSubjectWarehouseDto list = kbSubjectWarehouseService.findByListToCategoryVideoAddAdmin();
            return RespBody.data(list);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", null);
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(null);
            }
            List<Integer> asList = new ArrayList<>(Arrays.asList(
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            ));
            if ("1".equals(isSearch)) {
                asList.add(KbAuthEnum.ADD.getCode());
            }
            KbSubjectWarehouseDto list = kbSubjectWarehouseService.findByListToCategoryVideoAdd(uAuths, uid, asList);
            return RespBody.data(list);
        }
    }

    /**
     * 查询详情
     *
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查询详情")
    @GetMapping("/knowledge/kbSubjectWarehouse/findById")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:findById")
    public RespBody<KbSubjectWarehouseDto> findById(@RequestParam() Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        return RespBody.data(kbSubjectWarehouseService.findById(id));

    }

    /**
     * 查询目录下的知识
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查询目录下的知识")
    @GetMapping("/knowledge/kbSubjectWarehouse/findByListToCategoryAdd")
    @ResponseBody
    public RespBody<List<KbSubjectWarehouseDto>> findByListToCategoryAdd(KbSubjectWarehouseDto kbSubjectWarehouseDto) {

        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        Long uid = sysUser.getId();
        // 登录的高级管理员可以查看全部
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.findByListToCategoryAddAdmin(new ArrayList<KbWarehouseAuthEntity>());
            return RespBody.data(list);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", null);
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(null);
            }
            List<Integer> asList = Arrays.asList(
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );

            List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.findByListToCategoryAdd(uAuths, uid, asList);
            return RespBody.data(list);
        }
    }

    /**
     * 查询当前用户有那些添加权限
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("查询当前用户有那些添加权限")
    @GetMapping("/knowledge/kbSubjectWarehouse/findByListToCategoryVideoAdd")
    @ResponseBody
    public RespBody<Object> findByListToCategoryVideoAdd(KbSubjectWarehouseDto kbSubjectWarehouseDto) {

        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }

        Long uid = sysUser.getId();
        // 登录的高级管理员可以查看全部
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            KbSubjectWarehouseDto list = kbSubjectWarehouseService.findByListToCategoryVideoAddAdmin();
            if (list == null) {
                HashMap<String, String> res = new HashMap<>();
                res.put("data", "暂无数据，请前往后台管理，添加分类");
                res.put("code", "2023");
                return RespBody.data(res);
            }
            return RespBody.data(list);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00", null);
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(null);
            }
            List<Integer> asList = Arrays.asList(
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );

            KbSubjectWarehouseDto list = kbSubjectWarehouseService.findByListToCategoryVideoAdd(uAuths, uid, asList);
            if (list != null) {
                return RespBody.data(list);
            }
            return kbSubjectWarehouseService.permissionVerification(sysUser);
        }
    }

    /**
     * 主题知识仓库列表展示and分类展示
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库列表展示and分类展示")
    @GetMapping("/knowledge/kbSubjectWarehouse/authList")
    @ResponseBody
    public RespBody<List<KbSubjectWarehouseDto>> AuthListSubjectWarehouse(KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.AuthlistKbSubjectWarehouse(kbSubjectWarehouseDto);
        return RespBody.data(list);
    }


    /**
     * 主题知识仓库保存
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.INSERT)
    @ApiOperation("主题知识仓库保存")
    @PostMapping("/knowledge/kbSubjectWarehouse/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:save")
    public RespBody<String> saveKbSubjectWarehouse(@RequestBody KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        if (kbSubjectWarehouseDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbSubjectWarehouseEntity kbSubjectWarehouseEntity = ConvertUtil.transformObj(kbSubjectWarehouseDto, KbSubjectWarehouseEntity.class);
        if (kbSubjectWarehouseService.save(kbSubjectWarehouseEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库保存
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.INSERT)
    @ApiOperation("主题知识仓库保存")
    @PostMapping("/knowledge/kbSubjectWarehouse/add")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:add")
    public RespBody<String> addKbSubjectWarehouse(@RequestBody KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        if (kbSubjectWarehouseDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
//        KbSubjectWarehouseEntity kbSubjectWarehouseEntity = ConvertUtil.transformObj(kbSubjectWarehouseDto,KbSubjectWarehouseEntity.class);
        if (kbSubjectWarehouseService.add(kbSubjectWarehouseDto)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 主题知识仓库编辑
     *
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("主题知识仓库编辑")
    @PostMapping("/knowledge/kbSubjectWarehouse/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:edit")
    public RespBody<String> updateKbSubjectWarehouse(@RequestBody KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        if (kbSubjectWarehouseDto == null || kbSubjectWarehouseDto.getId() == null || kbSubjectWarehouseDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }

        SysUser sysUser = WebUtil.currentUser();


//        throw new BizException(GlobalRespCode.NO_PERMISSION_ERROR);

        if (kbSubjectWarehouseService.updateById(kbSubjectWarehouseDto)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.DELETE)
    @ApiOperation("主题知识仓库删除")
    @PostMapping("/knowledge/kbSubjectWarehouse/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:delete")
    public RespBody<String> deleteKbSubjectWarehouse(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbSubjectWarehouseService.removeById(id)) {
            return RespBody.data("删除成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 主题知识仓库批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.DELETE)
    @ApiOperation("主题知识仓库批量删除")
    @PostMapping("/knowledge/kbSubjectWarehouse/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:deleteBatch")
    public RespBody<String> deleteKbSubjectWarehouseBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbSubjectWarehouseService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库详情
     *
     * @param id 主题知识仓库id
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库详情")
    @GetMapping("/knowledge/kbSubjectWarehouse/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbSubjectWarehouse:info")
    public RespBody<KbSubjectWarehouseDto> infoKbSubjectWarehouse(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbSubjectWarehouseDto kbSubjectWarehouseDto = kbSubjectWarehouseService.getByIdExtends(id);

        return RespBody.data(kbSubjectWarehouseDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "主题知识仓库", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbSubjectWarehouse/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbSubjectWarehouseDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbSubjectWarehouseDto> list = kbSubjectWarehouseService.listKbSubjectWarehouse(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
