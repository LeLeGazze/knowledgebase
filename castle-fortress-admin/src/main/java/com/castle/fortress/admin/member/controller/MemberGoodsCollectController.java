package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberGoodsCollectDto;
import com.castle.fortress.admin.member.entity.MemberGoodsCollectEntity;
import com.castle.fortress.admin.member.service.MemberGoodsCollectService;
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
 * 会员商品收藏表 控制器
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Api(tags="会员商品收藏表管理控制器")
@Controller
public class MemberGoodsCollectController {
    @Autowired
    private MemberGoodsCollectService memberGoodsCollectService;

    /**
     * 会员商品收藏表的分页展示
     * @param memberGoodsCollectDto 会员商品收藏表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员商品收藏表分页展示")
    @GetMapping("/member/memberGoodsCollect/page")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCollect:pageList")
    public RespBody<IPage<MemberGoodsCollectDto>> pageMemberGoodsCollect(MemberGoodsCollectDto memberGoodsCollectDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberGoodsCollectDto> page = new Page(pageIndex, pageSize);
        IPage<MemberGoodsCollectDto> pages = memberGoodsCollectService.pageMemberGoodsCollectExtends(page, memberGoodsCollectDto);

        return RespBody.data(pages);
    }

    /**
     * 会员商品收藏表的列表展示
     * @param memberGoodsCollectDto 会员商品收藏表实体类
     * @return
     */
    @ApiOperation("会员商品收藏表列表展示")
    @GetMapping("/member/memberGoodsCollect/list")
    @ResponseBody
    public RespBody<List<MemberGoodsCollectDto>> listMemberGoodsCollect(MemberGoodsCollectDto memberGoodsCollectDto){
        List<MemberGoodsCollectDto> list = memberGoodsCollectService.listMemberGoodsCollect(memberGoodsCollectDto);
        return RespBody.data(list);
    }

    /**
     * 会员商品收藏表保存
     * @param memberGoodsCollectDto 会员商品收藏表实体类
     * @return
     */
    @ApiOperation("会员商品收藏表保存")
    @PostMapping("/member/memberGoodsCollect/save")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCollect:save")
    public RespBody<String> saveMemberGoodsCollect(@RequestBody MemberGoodsCollectDto memberGoodsCollectDto){
        if(memberGoodsCollectDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberGoodsCollectEntity memberGoodsCollectEntity = ConvertUtil.transformObj(memberGoodsCollectDto,MemberGoodsCollectEntity.class);
        if(memberGoodsCollectService.save(memberGoodsCollectEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员商品收藏表编辑
     * @param memberGoodsCollectDto 会员商品收藏表实体类
     * @return
     */
    @ApiOperation("会员商品收藏表编辑")
    @PostMapping("/member/memberGoodsCollect/edit")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCollect:edit")
    public RespBody<String> updateMemberGoodsCollect(@RequestBody MemberGoodsCollectDto memberGoodsCollectDto){
        if(memberGoodsCollectDto == null || memberGoodsCollectDto.getId() == null || memberGoodsCollectDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberGoodsCollectEntity memberGoodsCollectEntity = ConvertUtil.transformObj(memberGoodsCollectDto,MemberGoodsCollectEntity.class);
        if(memberGoodsCollectService.updateById(memberGoodsCollectEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员商品收藏表删除
     * @param id
     * @return
     */
    @ApiOperation("会员商品收藏表删除")
    @PostMapping("/member/memberGoodsCollect/delete")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCollect:delete")
    public RespBody<String> deleteMemberGoodsCollect(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberGoodsCollectService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员商品收藏表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("会员商品收藏表批量删除")
    @PostMapping("/member/memberGoodsCollect/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCollect:deleteBatch")
    public RespBody<String> deleteMemberGoodsCollectBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberGoodsCollectService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员商品收藏表详情
     * @param id 会员商品收藏表id
     * @return
     */
    @ApiOperation("会员商品收藏表详情")
    @GetMapping("/member/memberGoodsCollect/info")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCollect:info")
    public RespBody<MemberGoodsCollectDto> infoMemberGoodsCollect(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberGoodsCollectDto memberGoodsCollectDto =  memberGoodsCollectService.getByIdExtends(id);

        return RespBody.data(memberGoodsCollectDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/member/memberGoodsCollect/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberGoodsCollectDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberGoodsCollectDto> list = memberGoodsCollectService.listMemberGoodsCollect(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
