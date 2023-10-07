package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberRealinfoDto;
import com.castle.fortress.admin.member.entity.MemberRealinfoEntity;
import com.castle.fortress.admin.member.service.MemberRealinfoService;
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
 * 会员真实信息表 控制器
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Api(tags="会员真实信息表管理控制器")
@Controller
public class MemberRealinfoController {
    @Autowired
    private MemberRealinfoService memberRealinfoService;

    /**
     * 会员真实信息表的分页展示
     * @param memberRealinfoDto 会员真实信息表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员真实信息表分页展示")
    @GetMapping("/member/memberRealinfo/page")
    @ResponseBody
    @RequiresPermissions("member:memberRealinfo:pageList")
    public RespBody<IPage<MemberRealinfoDto>> pageMemberRealinfo(MemberRealinfoDto memberRealinfoDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberRealinfoDto> page = new Page(pageIndex, pageSize);
        IPage<MemberRealinfoDto> pages = memberRealinfoService.pageMemberRealinfoExtends(page, memberRealinfoDto);

        return RespBody.data(pages);
    }

    /**
     * 会员真实信息表的列表展示
     * @param memberRealinfoDto 会员真实信息表实体类
     * @return
     */
    @ApiOperation("会员真实信息表列表展示")
    @GetMapping("/member/memberRealinfo/list")
    @ResponseBody
    public RespBody<List<MemberRealinfoDto>> listMemberRealinfo(MemberRealinfoDto memberRealinfoDto){
        List<MemberRealinfoDto> list = memberRealinfoService.listMemberRealinfo(memberRealinfoDto);
        return RespBody.data(list);
    }

    /**
     * 会员真实信息表保存
     * @param memberRealinfoDto 会员真实信息表实体类
     * @return
     */
    @ApiOperation("会员真实信息表保存")
    @PostMapping("/member/memberRealinfo/save")
    @ResponseBody
    @RequiresPermissions("member:memberRealinfo:save")
    public RespBody<String> saveMemberRealinfo(@RequestBody MemberRealinfoDto memberRealinfoDto){
        if(memberRealinfoDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberRealinfoEntity memberRealinfoEntity = ConvertUtil.transformObj(memberRealinfoDto,MemberRealinfoEntity.class);
        if(memberRealinfoService.save(memberRealinfoEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员真实信息表编辑
     * @param memberRealinfoDto 会员真实信息表实体类
     * @return
     */
    @ApiOperation("会员真实信息表编辑")
    @PostMapping("/member/memberRealinfo/edit")
    @ResponseBody
    @RequiresPermissions("member:memberRealinfo:edit")
    public RespBody<String> updateMemberRealinfo(@RequestBody MemberRealinfoDto memberRealinfoDto){
        if(memberRealinfoDto == null || memberRealinfoDto.getId() == null || memberRealinfoDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberRealinfoEntity memberRealinfoEntity = ConvertUtil.transformObj(memberRealinfoDto,MemberRealinfoEntity.class);
        if(memberRealinfoService.updateById(memberRealinfoEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员真实信息表删除
     * @param id
     * @return
     */
    @ApiOperation("会员真实信息表删除")
    @PostMapping("/member/memberRealinfo/delete")
    @ResponseBody
    @RequiresPermissions("member:memberRealinfo:delete")
    public RespBody<String> deleteMemberRealinfo(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberRealinfoService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员真实信息表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("会员真实信息表批量删除")
    @PostMapping("/member/memberRealinfo/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberRealinfo:deleteBatch")
    public RespBody<String> deleteMemberRealinfoBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberRealinfoService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员真实信息表详情
     * @param id 会员真实信息表id
     * @return
     */
    @ApiOperation("会员真实信息表详情")
    @GetMapping("/member/memberRealinfo/info")
    @ResponseBody
    @RequiresPermissions("member:memberRealinfo:info")
    public RespBody<MemberRealinfoDto> infoMemberRealinfo(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberRealinfoDto memberRealinfoDto =  memberRealinfoService.getByIdExtends(id);

        return RespBody.data(memberRealinfoDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/member/memberRealinfo/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberRealinfoDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberRealinfoDto> list = memberRealinfoService.listMemberRealinfo(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
