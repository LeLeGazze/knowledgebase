package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberGoodsCartDto;
import com.castle.fortress.admin.member.entity.MemberGoodsCartEntity;
import com.castle.fortress.admin.member.service.MemberGoodsCartService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员商品购物车 api 控制器
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Api(tags="会员商品购物车api管理控制器")
@RestController
@RequestMapping("/api/member/memberGoodsCart")
public class ApiMemberGoodsCartController {
    @Autowired
    private MemberGoodsCartService memberGoodsCartService;


    /**
     * 会员商品购物车的分页展示
     * @param memberGoodsCartDto 会员商品购物车实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员商品购物车分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberGoodsCartDto>> pageMemberGoodsCart(MemberGoodsCartDto memberGoodsCartDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberGoodsCartDto> page = new Page(pageIndex, pageSize);

        IPage<MemberGoodsCartDto> pages = memberGoodsCartService.pageMemberGoodsCart(page, memberGoodsCartDto);
        return RespBody.data(pages);
    }

    /**
     * 会员商品购物车保存
     * @param memberGoodsCartDto 会员商品购物车实体类
     * @return
     */
    @ApiOperation("会员商品购物车保存")
    @PostMapping("/save")
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
    @PostMapping("/edit")
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
     * @param ids 会员商品购物车id集合
     * @return
     */
    @ApiOperation("会员商品购物车删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberGoodsCart(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
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
    @GetMapping("/info")
    public RespBody<MemberGoodsCartDto> infoMemberGoodsCart(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberGoodsCartEntity memberGoodsCartEntity = memberGoodsCartService.getById(id);
            MemberGoodsCartDto memberGoodsCartDto = ConvertUtil.transformObj(memberGoodsCartEntity,MemberGoodsCartDto.class);
        return RespBody.data(memberGoodsCartDto);
    }


}
