package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.entity.MemberExtendFieldConfigEntity;
import com.castle.fortress.admin.member.dto.MemberExtendFieldConfigDto;
import com.castle.fortress.admin.member.service.MemberExtendFieldConfigService;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
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
 * 用户扩展字段配置表 控制器
 *
 * @author whc
 * @since 2022-11-23
 */
@Api(tags = "用户扩展字段配置表管理控制器")
@Controller
public class MemberExtendFieldConfigController {
    @Autowired
    private MemberExtendFieldConfigService memberExtendFieldConfigService;

    /**
     * 用户扩展字段配置表的分页展示
     *
     * @param memberExtendFieldConfigDto 用户扩展字段配置表实体类
     * @param current                    当前页
     * @param size                       每页记录数
     * @return
     */
    @CastleLog(operLocation="扩展字段分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("用户扩展字段配置表分页展示")
    @GetMapping("/member/memberExtendFieldConfig/page")
    @ResponseBody
    @RequiresPermissions("member:memberExtendFieldConfig:pageList")
    public RespBody<IPage<MemberExtendFieldConfigDto>> pageMemberExtendFieldConfig(MemberExtendFieldConfigDto memberExtendFieldConfigDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<MemberExtendFieldConfigDto> page = new Page(pageIndex, pageSize);
        IPage<MemberExtendFieldConfigDto> pages = memberExtendFieldConfigService.pageMemberExtendFieldConfig(page, memberExtendFieldConfigDto);

        return RespBody.data(pages);
    }


    @CastleLog(operLocation="扩展字段生成",operType= OperationTypeEnum.INSERT)
    @ApiOperation("执行生成字段")
    @GetMapping("/member/memberExtendFieldConfig/generate")
    @ResponseBody
    @RequiresPermissions("member:memberExtendFieldConfig:generate")
    public RespBody<String> generate() {
        try {
            memberExtendFieldConfigService.generate();
            return RespBody.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBody.fail(e.getMessage());
        }
    }

    /**
     *  修改用户扩展字段信息
     * @param memberId
     * @param params
     * @return
     */
    @CastleLog(operLocation="扩展字段修改",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("更改用户扩展信息")
    @PostMapping("/member/memberExtendFieldConfig/editExtend")
    @ResponseBody
    public RespBody<String> updateMemberExtendFieldConfig(@RequestParam Long memberId, @RequestBody HashMap<String, String> params) {
        memberExtendFieldConfigService.updateOrCreateByMemberId(memberId, params);
        return RespBody.data("保存成功");
    }

    /**
     * 用户扩展字段配置表的列表展示
     *
     * @param memberExtendFieldConfigDto 用户扩展字段配置表实体类
     * @return
     */
    @CastleLog(operLocation="扩展字段列表",operType= OperationTypeEnum.QUERY)
    @ApiOperation("用户扩展字段配置表列表展示")
    @GetMapping("/member/memberExtendFieldConfig/list")
    @ResponseBody
    public RespBody<List<MemberExtendFieldConfigDto>> listMemberExtendFieldConfig(MemberExtendFieldConfigDto memberExtendFieldConfigDto) {
        List<MemberExtendFieldConfigDto> list = memberExtendFieldConfigService.listMemberExtendFieldConfig(memberExtendFieldConfigDto);
        return RespBody.data(list);
    }

    /**
     * 用户扩展字段配置表保存
     *
     * @param memberExtendFieldConfigDto 用户扩展字段配置表实体类
     * @return
     */
    @CastleLog(operLocation="扩展字段保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("用户扩展字段配置表保存")
    @PostMapping("/member/memberExtendFieldConfig/save")
    @ResponseBody
    @RequiresPermissions("member:memberExtendFieldConfig:save")
    public RespBody<String> saveMemberExtendFieldConfig(@RequestBody MemberExtendFieldConfigDto memberExtendFieldConfigDto) {
        if (memberExtendFieldConfigDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberExtendFieldConfigEntity memberExtendFieldConfigEntity = ConvertUtil.transformObj(memberExtendFieldConfigDto, MemberExtendFieldConfigEntity.class);
        if (memberExtendFieldConfigService.save(memberExtendFieldConfigEntity)) {
            memberExtendFieldConfigService.generate();
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户扩展字段配置表编辑
     *
     * @param memberExtendFieldConfigDto 用户扩展字段配置表实体类
     * @return
     */
    @CastleLog(operLocation="扩展字段编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("用户扩展字段配置表编辑")
    @PostMapping("/member/memberExtendFieldConfig/edit")
    @ResponseBody
    @RequiresPermissions("member:memberExtendFieldConfig:edit")
    public RespBody<String> updateMemberExtendFieldConfig(@RequestBody MemberExtendFieldConfigDto memberExtendFieldConfigDto) {
        if (memberExtendFieldConfigDto == null || memberExtendFieldConfigDto.getId() == null || memberExtendFieldConfigDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberExtendFieldConfigEntity memberExtendFieldConfigEntity = ConvertUtil.transformObj(memberExtendFieldConfigDto, MemberExtendFieldConfigEntity.class);
        if (memberExtendFieldConfigService.updateById(memberExtendFieldConfigEntity)) {
            memberExtendFieldConfigService.generate();
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户扩展字段配置表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation="扩展字段删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("用户扩展字段配置表删除")
    @PostMapping("/member/memberExtendFieldConfig/delete")
    @ResponseBody
    @RequiresPermissions("member:memberExtendFieldConfig:delete")
    public RespBody<String> deleteMemberExtendFieldConfig(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (memberExtendFieldConfigService.removeById(id)) {
            memberExtendFieldConfigService.generate();
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 用户扩展字段配置表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation="扩展字段批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("用户扩展字段配置表批量删除")
    @PostMapping("/member/memberExtendFieldConfig/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberExtendFieldConfig:deleteBatch")
    public RespBody<String> deleteMemberExtendFieldConfigBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (memberExtendFieldConfigService.removeByIds(ids)) {
            memberExtendFieldConfigService.generate();
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户扩展字段配置表详情
     *
     * @param id 用户扩展字段配置表id
     * @return
     */
    @CastleLog(operLocation="扩展字段详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("用户扩展字段配置表详情")
    @GetMapping("/member/memberExtendFieldConfig/info")
    @ResponseBody
    @RequiresPermissions("member:memberExtendFieldConfig:info")
    public RespBody<MemberExtendFieldConfigDto> infoMemberExtendFieldConfig(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberExtendFieldConfigEntity memberExtendFieldConfigEntity = memberExtendFieldConfigService.getById(id);
        MemberExtendFieldConfigDto memberExtendFieldConfigDto = ConvertUtil.transformObj(memberExtendFieldConfigEntity, MemberExtendFieldConfigDto.class);

        return RespBody.data(memberExtendFieldConfigDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="扩展字段导出",operType= OperationTypeEnum.EXPORT)
    @PostMapping("/member/memberExtendFieldConfig/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<MemberExtendFieldConfigDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<MemberExtendFieldConfigDto> list = memberExtendFieldConfigService.listMemberExtendFieldConfig(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }


}
