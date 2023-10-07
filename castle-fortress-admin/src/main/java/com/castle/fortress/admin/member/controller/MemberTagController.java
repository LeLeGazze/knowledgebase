package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.entity.MemberTagEntity;
import com.castle.fortress.admin.member.dto.MemberTagDto;
import com.castle.fortress.admin.member.service.MemberTagService;
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
 * 会员标签表 控制器
 *
 * @author whc
 * @since 2022-12-08
 */
@Api(tags="会员标签表管理控制器")
@Controller
public class MemberTagController {
    @Autowired
    private MemberTagService memberTagService;

    /**
     * 会员标签表的分页展示
     * @param memberTagDto 会员标签表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="会员标签表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员标签表分页展示")
    @GetMapping("/member/memberTag/page")
    @ResponseBody
    @RequiresPermissions("member:memberTag:pageList")
    public RespBody<IPage<MemberTagDto>> pageMemberTag(MemberTagDto memberTagDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberTagDto> page = new Page(pageIndex, pageSize);
        IPage<MemberTagDto> pages = memberTagService.pageMemberTagExtends(page, memberTagDto);

        return RespBody.data(pages);
    }

    /**
     * 会员标签表的列表展示
     * @param memberTagDto 会员标签表实体类
     * @return
     */
    @CastleLog(operLocation="会员标签表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员标签表列表展示")
    @GetMapping("/member/memberTag/list")
    @ResponseBody
    public RespBody<List<MemberTagDto>> listMemberTag(MemberTagDto memberTagDto){
        List<MemberTagDto> list = memberTagService.listMemberTag(memberTagDto);
        return RespBody.data(list);
    }

    /**
     * 会员标签表保存
     * @param memberTagDto 会员标签表实体类
     * @return
     */
    @CastleLog(operLocation="会员标签表",operType = OperationTypeEnum.INSERT)
    @ApiOperation("会员标签表保存")
    @PostMapping("/member/memberTag/save")
    @ResponseBody
    @RequiresPermissions("member:memberTag:save")
    public RespBody<String> saveMemberTag(@RequestBody MemberTagDto memberTagDto){
        if(memberTagDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberTagEntity memberTagEntity = ConvertUtil.transformObj(memberTagDto,MemberTagEntity.class);
        if(memberTagService.save(memberTagEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员标签表编辑
     * @param memberTagDto 会员标签表实体类
     * @return
     */
    @CastleLog(operLocation="会员标签表",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("会员标签表编辑")
    @PostMapping("/member/memberTag/edit")
    @ResponseBody
    @RequiresPermissions("member:memberTag:edit")
    public RespBody<String> updateMemberTag(@RequestBody MemberTagDto memberTagDto){
        if(memberTagDto == null || memberTagDto.getId() == null || memberTagDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberTagEntity memberTagEntity = ConvertUtil.transformObj(memberTagDto,MemberTagEntity.class);
        if(memberTagService.updateById(memberTagEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员标签表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="会员标签表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("会员标签表删除")
    @PostMapping("/member/memberTag/delete")
    @ResponseBody
    @RequiresPermissions("member:memberTag:delete")
    public RespBody<String> deleteMemberTag(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberTagService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员标签表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="会员标签表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("会员标签表批量删除")
    @PostMapping("/member/memberTag/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberTag:deleteBatch")
    public RespBody<String> deleteMemberTagBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberTagService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员标签表详情
     * @param id 会员标签表id
     * @return
     */
    @CastleLog(operLocation="会员标签表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员标签表详情")
    @GetMapping("/member/memberTag/info")
    @ResponseBody
    @RequiresPermissions("member:memberTag:info")
    public RespBody<MemberTagDto> infoMemberTag(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberTagDto memberTagDto =  memberTagService.getByIdExtends(id);

        return RespBody.data(memberTagDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="会员标签表",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/member/memberTag/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberTagDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberTagDto> list = memberTagService.listMemberTag(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
