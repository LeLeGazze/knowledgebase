package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.entity.MemberLevelEntity;
import com.castle.fortress.admin.member.dto.MemberLevelDto;
import com.castle.fortress.admin.member.service.MemberLevelService;
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
 * 会员等级表 控制器
 *
 * @author whc
 * @since 2022-12-29
 */
@Api(tags="会员等级表管理控制器")
@Controller
public class MemberLevelController {
    @Autowired
    private MemberLevelService memberLevelService;

    /**
     * 会员等级表的分页展示
     * @param memberLevelDto 会员等级表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="会员等级表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员等级表分页展示")
    @GetMapping("/member/memberLevel/page")
    @ResponseBody
    @RequiresPermissions("member:memberLevel:pageList")
    public RespBody<IPage<MemberLevelDto>> pageMemberLevel(MemberLevelDto memberLevelDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberLevelDto> page = new Page(pageIndex, pageSize);
        IPage<MemberLevelDto> pages = memberLevelService.pageMemberLevelExtends(page, memberLevelDto);

        return RespBody.data(pages);
    }

    /**
     * 会员等级表的列表展示
     * @param memberLevelDto 会员等级表实体类
     * @return
     */
    @CastleLog(operLocation="会员等级表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员等级表列表展示")
    @GetMapping("/member/memberLevel/list")
    @ResponseBody
    public RespBody<List<MemberLevelDto>> listMemberLevel(MemberLevelDto memberLevelDto){
        List<MemberLevelDto> list = memberLevelService.listMemberLevel(memberLevelDto);
        return RespBody.data(list);
    }

    /**
     * 会员等级表保存
     * @param memberLevelDto 会员等级表实体类
     * @return
     */
    @CastleLog(operLocation="会员等级表",operType = OperationTypeEnum.INSERT)
    @ApiOperation("会员等级表保存")
    @PostMapping("/member/memberLevel/save")
    @ResponseBody
    @RequiresPermissions("member:memberLevel:save")
    public RespBody<String> saveMemberLevel(@RequestBody MemberLevelDto memberLevelDto){
        if(memberLevelDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberLevelEntity memberLevelEntity = ConvertUtil.transformObj(memberLevelDto,MemberLevelEntity.class);
        if(memberLevelService.save(memberLevelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员等级表编辑
     * @param memberLevelDto 会员等级表实体类
     * @return
     */
    @CastleLog(operLocation="会员等级表",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("会员等级表编辑")
    @PostMapping("/member/memberLevel/edit")
    @ResponseBody
    @RequiresPermissions("member:memberLevel:edit")
    public RespBody<String> updateMemberLevel(@RequestBody MemberLevelDto memberLevelDto){
        if(memberLevelDto == null || memberLevelDto.getId() == null || memberLevelDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberLevelEntity memberLevelEntity = ConvertUtil.transformObj(memberLevelDto,MemberLevelEntity.class);
        if(memberLevelService.updateById(memberLevelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员等级表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="会员等级表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("会员等级表删除")
    @PostMapping("/member/memberLevel/delete")
    @ResponseBody
    @RequiresPermissions("member:memberLevel:delete")
    public RespBody<String> deleteMemberLevel(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberLevelService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员等级表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="会员等级表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("会员等级表批量删除")
    @PostMapping("/member/memberLevel/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberLevel:deleteBatch")
    public RespBody<String> deleteMemberLevelBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberLevelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员等级表详情
     * @param id 会员等级表id
     * @return
     */
    @CastleLog(operLocation="会员等级表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员等级表详情")
    @GetMapping("/member/memberLevel/info")
    @ResponseBody
    @RequiresPermissions("member:memberLevel:info")
    public RespBody<MemberLevelDto> infoMemberLevel(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberLevelDto memberLevelDto =  memberLevelService.getByIdExtends(id);

        return RespBody.data(memberLevelDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="会员等级表",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/member/memberLevel/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberLevelDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberLevelDto> list = memberLevelService.listMemberLevel(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
