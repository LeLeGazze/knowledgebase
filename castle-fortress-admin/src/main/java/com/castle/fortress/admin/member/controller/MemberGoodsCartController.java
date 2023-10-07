package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberGoodsCartDto;
import com.castle.fortress.admin.member.entity.MemberGoodsCartEntity;
import com.castle.fortress.admin.member.service.MemberGoodsCartService;
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
 * 会员商品购物车 控制器
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Api(tags="会员商品购物车管理控制器")
@Controller
public class MemberGoodsCartController {
    @Autowired
    private MemberGoodsCartService memberGoodsCartService;

    /**
     * 会员商品购物车的分页展示
     * @param memberGoodsCartDto 会员商品购物车实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员商品购物车分页展示")
    @GetMapping("/member/memberGoodsCart/page")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCart:pageList")
    public RespBody<IPage<MemberGoodsCartDto>> pageMemberGoodsCart(MemberGoodsCartDto memberGoodsCartDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberGoodsCartDto> page = new Page(pageIndex, pageSize);
        IPage<MemberGoodsCartDto> pages = memberGoodsCartService.pageMemberGoodsCartExtends(page, memberGoodsCartDto);

        return RespBody.data(pages);
    }

    /**
     * 会员商品购物车的列表展示
     * @param memberGoodsCartDto 会员商品购物车实体类
     * @return
     */
    @ApiOperation("会员商品购物车列表展示")
    @GetMapping("/member/memberGoodsCart/list")
    @ResponseBody
    public RespBody<List<MemberGoodsCartDto>> listMemberGoodsCart(MemberGoodsCartDto memberGoodsCartDto){
        List<MemberGoodsCartDto> list = memberGoodsCartService.listMemberGoodsCart(memberGoodsCartDto);
        return RespBody.data(list);
    }

    /**
     * 会员商品购物车保存
     * @param memberGoodsCartDto 会员商品购物车实体类
     * @return
     */
    @ApiOperation("会员商品购物车保存")
    @PostMapping("/member/memberGoodsCart/save")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCart:save")
    public RespBody<String> saveMemberGoodsCart(@RequestBody MemberGoodsCartDto memberGoodsCartDto){
        if(memberGoodsCartDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberGoodsCartEntity memberGoodsCartEntity = ConvertUtil.transformObj(memberGoodsCartDto,MemberGoodsCartEntity.class);
        if(memberGoodsCartService.save(memberGoodsCartEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员商品购物车编辑
     * @param memberGoodsCartDto 会员商品购物车实体类
     * @return
     */
    @ApiOperation("会员商品购物车编辑")
    @PostMapping("/member/memberGoodsCart/edit")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCart:edit")
    public RespBody<String> updateMemberGoodsCart(@RequestBody MemberGoodsCartDto memberGoodsCartDto){
        if(memberGoodsCartDto == null || memberGoodsCartDto.getId() == null || memberGoodsCartDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberGoodsCartEntity memberGoodsCartEntity = ConvertUtil.transformObj(memberGoodsCartDto,MemberGoodsCartEntity.class);
        if(memberGoodsCartService.updateById(memberGoodsCartEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员商品购物车删除
     * @param id
     * @return
     */
    @ApiOperation("会员商品购物车删除")
    @PostMapping("/member/memberGoodsCart/delete")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCart:delete")
    public RespBody<String> deleteMemberGoodsCart(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberGoodsCartService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员商品购物车批量删除
     * @param ids
     * @return
     */
    @ApiOperation("会员商品购物车批量删除")
    @PostMapping("/member/memberGoodsCart/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCart:deleteBatch")
    public RespBody<String> deleteMemberGoodsCartBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberGoodsCartService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员商品购物车详情
     * @param id 会员商品购物车id
     * @return
     */
    @ApiOperation("会员商品购物车详情")
    @GetMapping("/member/memberGoodsCart/info")
    @ResponseBody
    @RequiresPermissions("member:memberGoodsCart:info")
    public RespBody<MemberGoodsCartDto> infoMemberGoodsCart(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberGoodsCartDto memberGoodsCartDto =  memberGoodsCartService.getByIdExtends(id);

        return RespBody.data(memberGoodsCartDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/member/memberGoodsCart/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberGoodsCartDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberGoodsCartDto> list = memberGoodsCartService.listMemberGoodsCart(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
