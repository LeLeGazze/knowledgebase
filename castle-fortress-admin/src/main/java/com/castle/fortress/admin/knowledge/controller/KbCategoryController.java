package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbCategoryShowDto;
import com.castle.fortress.admin.knowledge.dto.KbSubjectWarehouseDto;
import com.castle.fortress.admin.knowledge.entity.KbCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbCategoryDto;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.service.KbBasicService;
import com.castle.fortress.admin.knowledge.service.KbCategoryService;
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
import org.apache.commons.lang3.ArrayUtils;
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

import java.util.*;

/**
 * 知识分类表 控制器
 *
 * @author
 * @since 2023-04-24
 */
@Api(tags = "知识分类表管理控制器")
@Controller
public class KbCategoryController {
    @Autowired
    private KbCategoryService kbCategoryService;
    @Autowired
    private KbWarehouseAuthService kbWarehouseAuthService;

    @Autowired
    private KbBasicService basicService;

    /**
     * 知识分类表的分页展示
     *
     * @param kbCategoryDto 知识分类表实体类
     * @param current       当前页
     * @param size          每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识分类表分页展示")
    @GetMapping("/knowledge/kbCategory/page")
    @ResponseBody
    public RespBody<IPage<KbCategoryDto>> pageKbCategory(KbCategoryDto kbCategoryDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbCategoryDto> page = new Page(pageIndex, pageSize);
        IPage<KbCategoryDto> pages = kbCategoryService.pageKbCategoryExtends(page, kbCategoryDto);

        return RespBody.data(pages);
    }

//    /**
//     * 知识分类表的列表展示
//     *
//     * @param kbCategoryDto 知识分类表实体类
//     * @return
//     */
//    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
//    @ApiOperation("知识分类表列表展示")
//    @GetMapping("/knowledge/kbCategory/list")
//    @ResponseBody
//    @RequiresPermissions("knowledge:kbCategory:list")
//    public RespBody<List<KbCategoryDto>> listKbCategory(KbCategoryDto kbCategoryDto) {
//        // 获取当前登录用户
//        SysUser sysUser = WebUtil.currentUser();
//        if (sysUser == null) {
//            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
//        }
//        // 校验该用户是否有此知识目录权限
//        Long uid = sysUser.getId();
//        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
//            List<KbCategoryDto> list = kbCategoryService.listKbCategoryAdmin();// 管理员查询所有知识
//            return RespBody.data(list);
//        } else {
//            Integer[] longs = {
//                    KbAuthEnum.ADD.getCode(),
//                    KbAuthEnum.MANAGE.getCode(),
//                    KbAuthEnum.SHOW.getCode(),
//                    KbAuthEnum.UPDATE.getCode(),
//                    KbAuthEnum.DOWNLOAD.getCode()
//            };
//            List<KbCategoryDto> list = kbCategoryService.listKbCategory();
//            return RespBody.data(list);
//        }
//    }

    /**
     * 知识分类表的列表展示
     *
     * @param swId 目录id
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识分类表列表展示")
    @GetMapping("/knowledge/kbCategory/findBySwWidToBackstageAdd")
    @ResponseBody
    public RespBody<Object> findBySwWidToBackstageAdd(@RequestParam("swId") Long swId) {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            KbCategoryDto kbCategoryDto = new KbCategoryDto();
            kbCategoryDto.setSwId(swId);
            List<KbCategoryDto> list = kbCategoryService.listKbCategory(kbCategoryDto);// 管理员查询所有知识
            if (list==null || list.size()==0){
                HashMap<String, String> res = new HashMap<>();
                res.put("data", "暂无数据，请前往后台管理，添加分类");
                res.put("code", "2023");
                return   RespBody.data(res);
            }
            return RespBody.data(list);
        } else {
            HashMap<String, String> res = new HashMap<>();
            ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities = kbWarehouseAuthService.findByUid(sysUser.getId(), "00", swId);
            if (kbWarehouseAuthEntities == null || kbWarehouseAuthEntities.size() == 0) {
                res.put("code", "2022");
                res.put("data", "无知识库权限，请联系管理员");
                return RespBody.data( res);
            }
            Integer[] longs = {
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
            };
            List<KbCategoryDto> list = kbCategoryService.findByUidAndAuthKbCategory(uid, swId, longs);
            if (list!=null && list.size()>0){
                return RespBody.data(list);
            }
          return   kbCategoryService.permissionVerification(sysUser,swId);
        }
    }

    /**
     * 知识分类表的列表展示
     *
     * @param swId 目录id
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识分类表列表展示")
    @GetMapping("/knowledge/kbCategory/findBySwWidToBackstage")
    @ResponseBody
    @RequiresPermissions("knowledge:kbCategory:findBySwWidToBackstage")
    public RespBody<List<KbCategoryDto>> findBySwWidToBackstage(@RequestParam("swId") Long swId) {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            KbCategoryDto kbCategoryDto = new KbCategoryDto();
            kbCategoryDto.setSwId(swId);
            List<KbCategoryDto> list = kbCategoryService.listKbCategory(kbCategoryDto);// 管理员查询所有知识
            return RespBody.data(list);
        } else {
            Integer[] longs = {
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode()
            };
            List<KbCategoryDto> list = kbCategoryService.findByUidAndAuthKbCategory(uid, swId, longs);
            return RespBody.data(list);
        }
    }

    /**
     * 知识分类根据id查询
     *
     * @param id 目录id
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("根据id查询")
    @GetMapping("/knowledge/kbCategory/findById")
    @ResponseBody
    @RequiresPermissions("knowledge:kbCategory:findById")
    public RespBody<KbCategoryDto> findById(@RequestParam("id") Long id) {
        KbCategoryDto kbCategoryDto = kbCategoryService.selectById(id);

        return RespBody.data(kbCategoryDto);
    }

    /**
     * 知识分类表的列表展示
     *
     * @param swId 目录id
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识分类表列表展示")
    @GetMapping("/knowledge/kbCategory/findBySwWid")
    @ResponseBody
    public RespBody<List<KbCategoryDto>> findByUidKbCategory(@RequestParam("swId") Long swId) {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            KbCategoryDto kbCategoryDto = new KbCategoryDto();
            kbCategoryDto.setSwId(swId);
            List<KbCategoryDto> list = kbCategoryService.listKbCategory(kbCategoryDto);// 管理员查询所有知识
            return RespBody.data(list);
        } else {
            // 校验该用户是否有此知识目录权限
            ArrayList<KbWarehouseAuthEntity> uAuths = kbWarehouseAuthService.findByUid(uid, "00",swId);
            if (uAuths == null || uAuths.size() == 0) {
                return RespBody.data(new ArrayList<>());
            }

            Integer[] longs = {
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.MANAGE.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode()
            };
            List<KbCategoryDto> list = kbCategoryService.findByUidAndAuthKbCategory(uid, swId, longs);
            return RespBody.data(list);
        }
    }

    /**
     * 查询知识分类自己的热词
     *
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识分类表列表展示")
    @GetMapping("/knowledge/kbCategory/findByUidAuthHot")
    @ResponseBody
    public RespBody<List<KbCategoryShowDto>> findByUidAuthHotKbCategory() {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            List<KbCategoryShowDto> byUidAuthHotKbCategory = kbCategoryService.findByUidAuthHotKbCategory(null, uid, 10);
            return RespBody.data(byUidAuthHotKbCategory);
        } else {
            List<Integer> asList = Arrays.asList(KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            List<KbCategoryShowDto> byUidAuthHotKbCategory = kbCategoryService.findByUidAuthHotKbCategory(asList, uid, 10);
            return RespBody.data(byUidAuthHotKbCategory);
        }
    }

    /**
     * 知识分类表保存
     *
     * @param kbCategoryDto 知识分类表实体类
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识分类表保存")
    @PostMapping("/knowledge/kbCategory/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbCategory:save")
    public RespBody<String> saveKbCategory(@RequestBody KbCategoryDto kbCategoryDto) {
        if (kbCategoryDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCategoryEntity kbCategoryEntity = ConvertUtil.transformObj(kbCategoryDto, KbCategoryEntity.class);
        if (kbCategoryService.save(kbCategoryEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识分类表保存
     *
     * @param kbCategoryDto 知识分类表实体类
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识分类表保存")
    @PostMapping("/knowledge/kbCategory/add")
    @ResponseBody
    @RequiresPermissions("knowledge:kbCategory:add")
    public RespBody<String> addKbCategory(@RequestBody KbCategoryDto kbCategoryDto) {
        if (kbCategoryDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
//        KbCategoryEntity kbCategoryEntity = ConvertUtil.transformObj(kbCategoryDto,KbCategoryEntity.class);
        if (kbCategoryService.add(kbCategoryDto)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识分类表编辑
     *
     * @param kbCategoryDto 知识分类表实体类
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识分类表编辑")
    @PostMapping("/knowledge/kbCategory/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbCategory:edit")
    public RespBody<String> updateKbCategory(@RequestBody KbCategoryDto kbCategoryDto) {
        if (kbCategoryDto == null || kbCategoryDto.getId() == null || kbCategoryDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
//        KbCategoryEntity kbCategoryEntity = ConvertUtil.transformObj(kbCategoryDto,KbCategoryEntity.class);
        if (kbCategoryService.updateById(kbCategoryDto)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识分类表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识分类表删除")
    @PostMapping("/knowledge/kbCategory/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbCategory:delete")
    public RespBody<String> deleteKbCategory(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        int num = basicService.findByCategoryId(id);
        if (num == -1) {
            throw new BizException("请在回收站删除该分类下的知识，在进行删除分类");
        }
        if (num > 0) {
            throw new BizException("请删除该分类下的知识，在进行删除分类");
        }
        if (kbCategoryService.deleteById(id)) {
            return RespBody.data("删除成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识分类表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识分类表批量删除")
    @PostMapping("/knowledge/kbCategory/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbCategory:deleteBatch")
    public RespBody<String> deleteKbCategoryBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbCategoryService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识分类表详情
     *
     * @param id 知识分类表id
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识分类表详情")
    @GetMapping("/knowledge/kbCategory/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbCategory:info")
    public RespBody<KbCategoryDto> infoKbCategory(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbCategoryDto kbCategoryDto = kbCategoryService.getByIdExtends(id);

        return RespBody.data(kbCategoryDto);
    }


    /**
     * 查询视频先关的分类
     *
     * @return
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识分类表详情")
    @GetMapping("/knowledge/kbCategory/findVideoCategory")
    @ResponseBody
    public RespBody<Map<String, Object>> findVideoCategory() {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            Map<String, Object> kbCategoryEntityMap = kbCategoryService.findVideoCategoryAdmin();
            return RespBody.data(kbCategoryEntityMap);
        } else {
            List<Integer> asList = Arrays.asList(KbAuthEnum.ADD.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            Map<String, Object> byUidAuthHotKbCategory = kbCategoryService.findVideoCategory(asList, uid);
            return RespBody.data(byUidAuthHotKbCategory);
        }

    }


    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识分类表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbCategory/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbCategoryDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbCategoryDto> list = kbCategoryService.listKbCategory(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
