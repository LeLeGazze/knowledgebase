package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberAccountSerialDto;
import com.castle.fortress.admin.member.entity.MemberAccountSerialEntity;
import com.castle.fortress.admin.member.service.MemberAccountSerialService;
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
 * 会员账户流水 控制器
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Api(tags="会员账户流水管理控制器")
@Controller
public class MemberAccountSerialController {
    @Autowired
    private MemberAccountSerialService memberAccountSerialService;

    /**
     * 会员账户流水的分页展示
     * @param memberAccountSerialDto 会员账户流水实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员账户流水分页展示")
    @GetMapping("/member/memberAccountSerial/page")
    @ResponseBody
    @RequiresPermissions("member:memberAccountSerial:pageList")
    public RespBody<IPage<MemberAccountSerialDto>> pageMemberAccountSerial(MemberAccountSerialDto memberAccountSerialDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberAccountSerialDto> page = new Page(pageIndex, pageSize);
        IPage<MemberAccountSerialDto> pages = memberAccountSerialService.pageMemberAccountSerialExtends(page, memberAccountSerialDto);

        return RespBody.data(pages);
    }

    /**
     * 会员账户流水的列表展示
     * @param memberAccountSerialDto 会员账户流水实体类
     * @return
     */
    @ApiOperation("会员账户流水列表展示")
    @GetMapping("/member/memberAccountSerial/list")
    @ResponseBody
    public RespBody<List<MemberAccountSerialDto>> listMemberAccountSerial(MemberAccountSerialDto memberAccountSerialDto){
        List<MemberAccountSerialDto> list = memberAccountSerialService.listMemberAccountSerial(memberAccountSerialDto);
        return RespBody.data(list);
    }

    /**
     * 会员账户流水保存
     * @param memberAccountSerialDto 会员账户流水实体类
     * @return
     */
    @ApiOperation("会员账户流水保存")
    @PostMapping("/member/memberAccountSerial/save")
    @ResponseBody
    @RequiresPermissions("member:memberAccountSerial:save")
    public RespBody<String> saveMemberAccountSerial(@RequestBody MemberAccountSerialDto memberAccountSerialDto){
        if(memberAccountSerialDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAccountSerialEntity memberAccountSerialEntity = ConvertUtil.transformObj(memberAccountSerialDto,MemberAccountSerialEntity.class);
        if(memberAccountSerialService.save(memberAccountSerialEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员账户流水编辑
     * @param memberAccountSerialDto 会员账户流水实体类
     * @return
     */
    @ApiOperation("会员账户流水编辑")
    @PostMapping("/member/memberAccountSerial/edit")
    @ResponseBody
    @RequiresPermissions("member:memberAccountSerial:edit")
    public RespBody<String> updateMemberAccountSerial(@RequestBody MemberAccountSerialDto memberAccountSerialDto){
        if(memberAccountSerialDto == null || memberAccountSerialDto.getId() == null || memberAccountSerialDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAccountSerialEntity memberAccountSerialEntity = ConvertUtil.transformObj(memberAccountSerialDto,MemberAccountSerialEntity.class);
        if(memberAccountSerialService.updateById(memberAccountSerialEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员账户流水删除
     * @param id
     * @return
     */
    @ApiOperation("会员账户流水删除")
    @PostMapping("/member/memberAccountSerial/delete")
    @ResponseBody
    @RequiresPermissions("member:memberAccountSerial:delete")
    public RespBody<String> deleteMemberAccountSerial(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberAccountSerialService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员账户流水批量删除
     * @param ids
     * @return
     */
    @ApiOperation("会员账户流水批量删除")
    @PostMapping("/member/memberAccountSerial/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberAccountSerial:deleteBatch")
    public RespBody<String> deleteMemberAccountSerialBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberAccountSerialService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员账户流水详情
     * @param id 会员账户流水id
     * @return
     */
    @ApiOperation("会员账户流水详情")
    @GetMapping("/member/memberAccountSerial/info")
    @ResponseBody
    @RequiresPermissions("member:memberAccountSerial:info")
    public RespBody<MemberAccountSerialDto> infoMemberAccountSerial(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAccountSerialDto memberAccountSerialDto =  memberAccountSerialService.getByIdExtends(id);

        return RespBody.data(memberAccountSerialDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/member/memberAccountSerial/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberAccountSerialDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberAccountSerialDto> list = memberAccountSerialService.listMemberAccountSerial(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
