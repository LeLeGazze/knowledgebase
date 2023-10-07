package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberPointsSerialDto;
import com.castle.fortress.admin.member.entity.MemberPointsSerialEntity;
import com.castle.fortress.admin.member.service.MemberPointsSerialService;
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
 * 会员积分流水 控制器
 *
 * @author Mgg
 * @since 2021-12-01
 */
@Api(tags="会员积分流水管理控制器")
@Controller
public class MemberPointsSerialController {
    @Autowired
    private MemberPointsSerialService memberPointsSerialService;

    /**
     * 会员积分流水的分页展示
     * @param memberPointsSerialDto 会员积分流水实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员积分流水分页展示")
    @GetMapping("/member/memberPointsSerial/page")
    @ResponseBody
    @RequiresPermissions("member:memberPointsSerial:pageList")
    public RespBody<IPage<MemberPointsSerialDto>> pageMemberPointsSerial(MemberPointsSerialDto memberPointsSerialDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberPointsSerialDto> page = new Page(pageIndex, pageSize);
        IPage<MemberPointsSerialDto> pages = memberPointsSerialService.pageMemberPointsSerialExtends(page, memberPointsSerialDto);

        return RespBody.data(pages);
    }

    /**
     * 会员积分流水的列表展示
     * @param memberPointsSerialDto 会员积分流水实体类
     * @return
     */
    @ApiOperation("会员积分流水列表展示")
    @GetMapping("/member/memberPointsSerial/list")
    @ResponseBody
    public RespBody<List<MemberPointsSerialDto>> listMemberPointsSerial(MemberPointsSerialDto memberPointsSerialDto){
        List<MemberPointsSerialDto> list = memberPointsSerialService.listMemberPointsSerial(memberPointsSerialDto);
        return RespBody.data(list);
    }

    /**
     * 会员积分流水保存
     * @param memberPointsSerialDto 会员积分流水实体类
     * @return
     */
    @ApiOperation("会员积分流水保存")
    @PostMapping("/member/memberPointsSerial/save")
    @ResponseBody
    @RequiresPermissions("member:memberPointsSerial:save")
    public RespBody<String> saveMemberPointsSerial(@RequestBody MemberPointsSerialDto memberPointsSerialDto){
        if(memberPointsSerialDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberPointsSerialEntity memberPointsSerialEntity = ConvertUtil.transformObj(memberPointsSerialDto,MemberPointsSerialEntity.class);
        if(memberPointsSerialService.save(memberPointsSerialEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分流水编辑
     * @param memberPointsSerialDto 会员积分流水实体类
     * @return
     */
    @ApiOperation("会员积分流水编辑")
    @PostMapping("/member/memberPointsSerial/edit")
    @ResponseBody
    @RequiresPermissions("member:memberPointsSerial:edit")
    public RespBody<String> updateMemberPointsSerial(@RequestBody MemberPointsSerialDto memberPointsSerialDto){
        if(memberPointsSerialDto == null || memberPointsSerialDto.getId() == null || memberPointsSerialDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberPointsSerialEntity memberPointsSerialEntity = ConvertUtil.transformObj(memberPointsSerialDto,MemberPointsSerialEntity.class);
        if(memberPointsSerialService.updateById(memberPointsSerialEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分流水删除
     * @param id
     * @return
     */
    @ApiOperation("会员积分流水删除")
    @PostMapping("/member/memberPointsSerial/delete")
    @ResponseBody
    @RequiresPermissions("member:memberPointsSerial:delete")
    public RespBody<String> deleteMemberPointsSerial(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberPointsSerialService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员积分流水批量删除
     * @param ids
     * @return
     */
    @ApiOperation("会员积分流水批量删除")
    @PostMapping("/member/memberPointsSerial/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberPointsSerial:deleteBatch")
    public RespBody<String> deleteMemberPointsSerialBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberPointsSerialService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分流水详情
     * @param id 会员积分流水id
     * @return
     */
    @ApiOperation("会员积分流水详情")
    @GetMapping("/member/memberPointsSerial/info")
    @ResponseBody
    @RequiresPermissions("member:memberPointsSerial:info")
    public RespBody<MemberPointsSerialDto> infoMemberPointsSerial(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberPointsSerialDto memberPointsSerialDto =  memberPointsSerialService.getByIdExtends(id);

        return RespBody.data(memberPointsSerialDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/member/memberPointsSerial/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberPointsSerialDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberPointsSerialDto> list = memberPointsSerialService.listMemberPointsSerial(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
