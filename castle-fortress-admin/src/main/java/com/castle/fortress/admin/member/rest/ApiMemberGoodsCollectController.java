package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberGoodsCollectDto;
import com.castle.fortress.admin.member.entity.MemberGoodsCollectEntity;
import com.castle.fortress.admin.member.service.MemberGoodsCollectService;
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
 * 会员商品收藏表 api 控制器
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Api(tags="会员商品收藏表api管理控制器")
@RestController
@RequestMapping("/api/member/memberGoodsCollect")
public class ApiMemberGoodsCollectController {
    @Autowired
    private MemberGoodsCollectService memberGoodsCollectService;


    /**
     * 会员商品收藏表的分页展示
     * @param memberGoodsCollectDto 会员商品收藏表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员商品收藏表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberGoodsCollectDto>> pageMemberGoodsCollect(MemberGoodsCollectDto memberGoodsCollectDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberGoodsCollectDto> page = new Page(pageIndex, pageSize);

        IPage<MemberGoodsCollectDto> pages = memberGoodsCollectService.pageMemberGoodsCollect(page, memberGoodsCollectDto);
        return RespBody.data(pages);
    }

    /**
     * 会员商品收藏表保存
     * @param memberGoodsCollectDto 会员商品收藏表实体类
     * @return
     */
    @ApiOperation("会员商品收藏表保存")
    @PostMapping("/save")
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
    @PostMapping("/edit")
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
     * @param ids 会员商品收藏表id集合
     * @return
     */
    @ApiOperation("会员商品收藏表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberGoodsCollect(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
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
    @GetMapping("/info")
    public RespBody<MemberGoodsCollectDto> infoMemberGoodsCollect(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberGoodsCollectEntity memberGoodsCollectEntity = memberGoodsCollectService.getById(id);
            MemberGoodsCollectDto memberGoodsCollectDto = ConvertUtil.transformObj(memberGoodsCollectEntity,MemberGoodsCollectDto.class);
        return RespBody.data(memberGoodsCollectDto);
    }


}
