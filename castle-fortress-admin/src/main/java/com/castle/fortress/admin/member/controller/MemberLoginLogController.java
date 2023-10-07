package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberLoginLogDto;
import com.castle.fortress.admin.member.entity.MemberLoginLogEntity;
import com.castle.fortress.admin.member.service.MemberLoginLogService;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员登录日志表 控制器
 *
 * @author Mgg
 * @since 2021-11-26
 */
@Api(tags="会员登录日志表管理控制器")
@Controller
public class MemberLoginLogController {
    @Autowired
    private MemberLoginLogService memberLoginLogService;

    /**
     * 会员登录日志表的分页展示
     * @param memberLoginLogDto 会员登录日志表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员登录日志表分页展示")
    @GetMapping("/member/memberLoginLog/page")
    @ResponseBody
    @RequiresPermissions("member:memberLoginLog:pageList")
    public RespBody<IPage<MemberLoginLogDto>> pageMemberLoginLog(MemberLoginLogDto memberLoginLogDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberLoginLogDto> page = new Page(pageIndex, pageSize);
        IPage<MemberLoginLogDto> pages = memberLoginLogService.pageMemberLoginLog(page, memberLoginLogDto);

        return RespBody.data(pages);
    }

    /**
     * 会员登录日志表的列表展示
     * @param memberLoginLogDto 会员登录日志表实体类
     * @return
     */
    @ApiOperation("会员登录日志表列表展示")
    @GetMapping("/member/memberLoginLog/list")
    @ResponseBody
    public RespBody<List<MemberLoginLogDto>> listMemberLoginLog(MemberLoginLogDto memberLoginLogDto){
        List<MemberLoginLogDto> list = memberLoginLogService.listMemberLoginLog(memberLoginLogDto);
        return RespBody.data(list);
    }

    /**
     * 会员登录日志表保存
     * @param memberLoginLogDto 会员登录日志表实体类
     * @return
     */
    @ApiOperation("会员登录日志表保存")
    @PostMapping("/member/memberLoginLog/save")
    @ResponseBody
    @RequiresPermissions("member:memberLoginLog:save")
    public RespBody<String> saveMemberLoginLog(@RequestBody MemberLoginLogDto memberLoginLogDto){
        if(memberLoginLogDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberLoginLogEntity memberLoginLogEntity = ConvertUtil.transformObj(memberLoginLogDto,MemberLoginLogEntity.class);
        if(memberLoginLogService.save(memberLoginLogEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员登录日志表编辑
     * @param memberLoginLogDto 会员登录日志表实体类
     * @return
     */
    @ApiOperation("会员登录日志表编辑")
    @PostMapping("/member/memberLoginLog/edit")
    @ResponseBody
    @RequiresPermissions("member:memberLoginLog:edit")
    public RespBody<String> updateMemberLoginLog(@RequestBody MemberLoginLogDto memberLoginLogDto){
        if(memberLoginLogDto == null || memberLoginLogDto.getId() == null || memberLoginLogDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberLoginLogEntity memberLoginLogEntity = ConvertUtil.transformObj(memberLoginLogDto,MemberLoginLogEntity.class);
        if(memberLoginLogService.updateById(memberLoginLogEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员登录日志表删除
     * @param id
     * @return
     */
    @ApiOperation("会员登录日志表删除")
    @PostMapping("/member/memberLoginLog/delete")
    @ResponseBody
    @RequiresPermissions("member:memberLoginLog:delete")
    public RespBody<String> deleteMemberLoginLog(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberLoginLogService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员登录日志表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("会员登录日志表批量删除")
    @PostMapping("/member/memberLoginLog/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberLoginLog:deleteBatch")
    public RespBody<String> deleteMemberLoginLogBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberLoginLogService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员登录日志表详情
     * @param id 会员登录日志表id
     * @return
     */
    @ApiOperation("会员登录日志表详情")
    @GetMapping("/member/memberLoginLog/info")
    @ResponseBody
    @RequiresPermissions("member:memberLoginLog:info")
    public RespBody<MemberLoginLogDto> infoMemberLoginLog(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberLoginLogEntity memberLoginLogEntity = memberLoginLogService.getById(id);
        MemberLoginLogDto memberLoginLogDto = ConvertUtil.transformObj(memberLoginLogEntity,MemberLoginLogDto.class);

        return RespBody.data(memberLoginLogDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/member/memberLoginLog/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberLoginLogDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberLoginLogDto> list = memberLoginLogService.listMemberLoginLog(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
