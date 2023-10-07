package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.dto.KbWarehouseAuthDto;
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
 * 主题知识仓库权限表 控制器
 *
 * @author
 * @since 2023-04-24
 */
@Api(tags = "主题知识仓库权限表管理控制器")
@Controller
public class KbWarehouseAuthController {
    @Autowired
    private KbWarehouseAuthService kbWarehouseAuthService;


    /**
     * 主题知识仓库权限表的分页展示
     *
     * @param kbWarehouseAuthDto 主题知识仓库权限表实体类
     * @param current            当前页
     * @param size               每页记录数
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库权限表分页展示")
    @GetMapping("/knowledge/kbWarehouseAuth/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbWarehouseAuth:pageList")
    public RespBody<IPage<KbWarehouseAuthDto>> pageKbWarehouseAuth(KbWarehouseAuthDto kbWarehouseAuthDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbWarehouseAuthDto> page = new Page(pageIndex, pageSize);
        IPage<KbWarehouseAuthDto> pages = kbWarehouseAuthService.pageKbWarehouseAuth(page, kbWarehouseAuthDto);

        return RespBody.data(pages);
    }

    /**
     * 主题知识仓库权限表的列表展示
     *
     * @param kbWarehouseAuthDto 主题知识仓库权限表实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库权限表列表展示")
    @GetMapping("/knowledge/kbWarehouseAuth/list")
    @ResponseBody
    public RespBody<List<KbWarehouseAuthDto>> listKbWarehouseAuth(KbWarehouseAuthDto kbWarehouseAuthDto) {
        List<KbWarehouseAuthDto> list = kbWarehouseAuthService.listKbWarehouseAuth(kbWarehouseAuthDto);
        return RespBody.data(list);
    }


    /**
     * 根据目录id 查询有哪些用户
     *
     * @param swId 目录id
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库权限表展示根据目录ID")
    @GetMapping("/knowledge/kbWarehouseAuth/findBySwId")
    @ResponseBody
    @RequiresPermissions("knowledge:kbWarehouseAuth:findBySwId")
    public RespBody<KbWarehouseAuthDto> findBySwId(@RequestParam Long swId) {
        KbWarehouseAuthDto kbWarehouseAuthDto= kbWarehouseAuthService.findBySwIdAuth(swId);
        return RespBody.data(kbWarehouseAuthDto);
    }


    /**
     * 主题知识仓库权限表的列表展示
     *
     * @param wh_id 目录ID
     * @param wc_id 目录下的分类ID
     * @return 返回可用的权限
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库权限表列表展示")
    @GetMapping("/knowledge/kbWarehouseAuth/findByUid")
    @ResponseBody
    public RespBody<List<KbWarehouseAuthDto>> findByUidbWarehouseAuth(@RequestParam Long wh_id, @RequestParam(required = false) Long wc_id) {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        // 校验该用户是否有此知识目录权限
        Long uid = sysUser.getId();


        List<KbWarehouseAuthDto> list = kbWarehouseAuthService.findByUidWarehouseAuth(uid, wh_id, wc_id);
        return RespBody.data(list);
    }


    /**
     * 主题知识仓库权限表保存
     *
     * @param kbWarehouseAuthDto 主题知识仓库权限表实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("主题知识仓库权限表保存")
    @PostMapping("/knowledge/kbWarehouseAuth/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbWarehouseAuth:save")
    public RespBody<String> saveKbWarehouseAuth(@RequestBody KbWarehouseAuthDto kbWarehouseAuthDto) {
        if (kbWarehouseAuthDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbWarehouseAuthEntity kbWarehouseAuthEntity = ConvertUtil.transformObj(kbWarehouseAuthDto, KbWarehouseAuthEntity.class);
        if (kbWarehouseAuthService.save(kbWarehouseAuthEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库权限表编辑
     *
     * @param kbWarehouseAuthDto 主题知识仓库权限表实体类
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("主题知识仓库权限表编辑")
    @PostMapping("/knowledge/kbWarehouseAuth/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbWarehouseAuth:edit")
    public RespBody<String> updateKbWarehouseAuth(@RequestBody KbWarehouseAuthDto kbWarehouseAuthDto) {
        if (kbWarehouseAuthDto == null || kbWarehouseAuthDto.getWhId() == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
//        KbWarehouseAuthEntity kbWarehouseAuthEntity = ConvertUtil.transformObj(kbWarehouseAuthDto,KbWarehouseAuthEntity.class);
        if (kbWarehouseAuthService.updateById(kbWarehouseAuthDto)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库权限表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("主题知识仓库权限表删除")
    @PostMapping("/knowledge/kbWarehouseAuth/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbWarehouseAuth:delete")
    public RespBody<String> deleteKbWarehouseAuth(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbWarehouseAuthService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 主题知识仓库权限表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("主题知识仓库权限表批量删除")
    @PostMapping("/knowledge/kbWarehouseAuth/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbWarehouseAuth:deleteBatch")
    public RespBody<String> deleteKbWarehouseAuthBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbWarehouseAuthService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库权限表详情
     *
     * @param id 主题知识仓库权限表id
     * @return
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库权限表详情")
    @GetMapping("/knowledge/kbWarehouseAuth/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbWarehouseAuth:info")
    public RespBody<KbWarehouseAuthDto> infoKbWarehouseAuth(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbWarehouseAuthEntity kbWarehouseAuthEntity = kbWarehouseAuthService.getById(id);
        KbWarehouseAuthDto kbWarehouseAuthDto = ConvertUtil.transformObj(kbWarehouseAuthEntity, KbWarehouseAuthDto.class);

        return RespBody.data(kbWarehouseAuthDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "主题知识仓库权限表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbWarehouseAuth/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbWarehouseAuthDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbWarehouseAuthDto> list = kbWarehouseAuthService.listKbWarehouseAuth(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


//    @CastleLog(operLocation="主题知识仓库权限表",operType = OperationTypeEnum.INSERT)
//    @ApiOperation("主题库下的权限多个用户保持")
//    @PostMapping("/knowledge/kbWarehouseAuth/saveAll")
//    @ResponseBody
////    @RequiresPermissions("knowledge:kbWarehouseAuth:saveAll")
//    public RespBody<String> saveAllKbWarehouseAuth(@RequestBody KbWarehouseAuthDto kbWarehouseAuthDto){
//        if(kbWarehouseAuthDto == null ){
//            throw new BizException(GlobalRespCode.PARAM_MISSED);
//        }
//        if(kbWarehouseAuthService.saveAll(kbWarehouseAuthDto)){
//            return RespBody.data("保存成功");
//        }else{
//            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
//        }
//    }

}
