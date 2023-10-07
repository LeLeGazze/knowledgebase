package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbGroupsUserEntity;
import com.castle.fortress.admin.knowledge.dto.KbGroupsUserDto;
import com.castle.fortress.admin.knowledge.service.KbGroupsUserService;
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

import java.util.HashMap;
import java.util.List;

/**
 * 知识库用户组与用户关联关系管理 控制器
 *
 * @author sunhr
 * @since 2023-04-22
 */
@Api(tags = "知识库用户组与用户关联关系管理管理控制器")
@Controller
public class KbGroupsUserController {
    @Autowired
    private KbGroupsUserService kbGroupsUserService;

    /**
     * 知识库用户组与用户关联关系管理的分页展示
     *
     * @param kbGroupsUserDto 知识库用户组与用户关联关系管理实体类
     * @param current         当前页
     * @param size            每页记录数
     * @return
     */
    @CastleLog(operLocation = "知识库用户组与用户关联关系管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库用户组与用户关联关系管理分页展示")
    @GetMapping("/knowledge/kbGroupsUser/page")
    @ResponseBody
    public RespBody<IPage<KbGroupsUserDto>> pageKbGroupsUser(KbGroupsUserDto kbGroupsUserDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbGroupsUserDto> page = new Page(pageIndex, pageSize);
        IPage<KbGroupsUserDto> pages = kbGroupsUserService.pageKbGroupsUser(page, kbGroupsUserDto);

        return RespBody.data(pages);
    }

    /**
     * 知识库用户组与用户关联关系管理的列表展示
     *
     * @param kbGroupsUserDto 知识库用户组与用户关联关系管理实体类
     * @return
     */
    @CastleLog(operLocation = "知识库用户组与用户关联关系管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库用户组与用户关联关系管理列表展示")
    @GetMapping("/knowledge/kbGroupsUser/list")
    @ResponseBody
    public RespBody<List<KbGroupsUserDto>> listKbGroupsUser(KbGroupsUserDto kbGroupsUserDto) {
        List<KbGroupsUserDto> list = kbGroupsUserService.listKbGroupsUser(kbGroupsUserDto);
        return RespBody.data(list);
    }

    /**
     * 知识库用户组与用户关联关系管理保存
     *
     * @param list 知识库用户组与用户关联关系管理实体类
     * @return
     */
    @CastleLog(operLocation = "知识库用户组与用户关联关系管理", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识库用户组与用户关联关系管理保存")
    @PostMapping("/knowledge/kbGroupsUser/save")
    @ResponseBody
    public RespBody<String> saveKbGroupsUser(@RequestBody List<KbGroupsUserDto> list) {
        if (list == null || list.size() == 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //获取部门id
        KbGroupsUserDto kbGroupsUserDto1 = list.get(0);
        Long groupId = kbGroupsUserDto1.getGroupId();
        //map记录
        HashMap<Long, KbGroupsUserEntity> map = new HashMap<>();
        List<KbGroupsUserEntity> byId = kbGroupsUserService.findById(groupId);
        for (KbGroupsUserEntity kbGroupsUserEntity : byId) {
            map.put(kbGroupsUserEntity.getUserId(), kbGroupsUserEntity);
        }
        //批量保存
        for (KbGroupsUserDto kbGroupsUserDto : list) {
            if (kbGroupsUserDto == null) {
                throw new BizException(GlobalRespCode.PARAM_MISSED);
            }
            //利用map判断是否重复添加用户
            if (map.containsKey(kbGroupsUserDto.getUserId())) {
                continue;
            }
            KbGroupsUserEntity kbGroupsUserEntity = ConvertUtil.transformObj(kbGroupsUserDto, KbGroupsUserEntity.class);
            boolean save = kbGroupsUserService.save(kbGroupsUserEntity);
            if (!save) {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        }
        return RespBody.data("保存成功");
    }

    /**
     * 知识库用户组与用户关联关系管理编辑
     *
     * @param kbGroupsUserDto 知识库用户组与用户关联关系管理实体类
     * @return
     */
    @CastleLog(operLocation = "知识库用户组与用户关联关系管理", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识库用户组与用户关联关系管理编辑")
    @PostMapping("/knowledge/kbGroupsUser/edit")
    @ResponseBody
    public RespBody<String> updateKbGroupsUser(@RequestBody KbGroupsUserDto kbGroupsUserDto) {
        if (kbGroupsUserDto == null || kbGroupsUserDto.getId() == null || kbGroupsUserDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbGroupsUserEntity kbGroupsUserEntity = ConvertUtil.transformObj(kbGroupsUserDto, KbGroupsUserEntity.class);
        if (kbGroupsUserService.updateById(kbGroupsUserEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库用户组与用户关联关系管理删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识库用户组与用户关联关系管理", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库用户组与用户关联关系管理删除")
    @PostMapping("/knowledge/kbGroupsUser/delete")
    @ResponseBody
    public RespBody<String> deleteKbGroupsUser(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbGroupsUserService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 知识库用户组与用户关联关系管理批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "知识库用户组与用户关联关系管理", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库用户组与用户关联关系管理批量删除")
    @PostMapping("/knowledge/kbGroupsUser/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbGroupsUserBatch(@RequestBody List<Long> ids, @RequestParam Long groupId) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbGroupsUserService.removeBatch(ids, groupId)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库用户组与用户关联关系管理详情
     *
     * @param id 知识库用户组与用户关联关系管理id
     * @return
     */
    @CastleLog(operLocation = "知识库用户组与用户关联关系管理", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库用户组与用户关联关系管理详情")
    @GetMapping("/knowledge/kbGroupsUser/info")
    @ResponseBody
    public RespBody<KbGroupsUserDto> infoKbGroupsUser(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbGroupsUserEntity kbGroupsUserEntity = kbGroupsUserService.getById(id);
        KbGroupsUserDto kbGroupsUserDto = ConvertUtil.transformObj(kbGroupsUserEntity, KbGroupsUserDto.class);

        return RespBody.data(kbGroupsUserDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "知识库用户组与用户关联关系管理", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbGroupsUser/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbGroupsUserDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbGroupsUserDto> list = kbGroupsUserService.listKbGroupsUser(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
