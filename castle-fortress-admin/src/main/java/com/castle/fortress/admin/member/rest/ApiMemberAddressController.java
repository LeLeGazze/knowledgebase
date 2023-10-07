package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberAddressDto;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.member.entity.MemberAddressEntity;
import com.castle.fortress.admin.member.service.MemberAddressService;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员收货地址表 api 控制器
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Api(tags = "会员收货地址表api管理控制器")
@RestController
@RequestMapping("/api/member/memberAddress")
public class ApiMemberAddressController {
    @Autowired
    private MemberAddressService memberAddressService;


    /**
     * 会员收货地址表的分页展示
     *
     * @param memberAddressDto 会员收货地址表实体类
     * @param currentPage      当前页
     * @param size             每页记录数
     * @return
     */
    @ApiOperation("会员收货地址表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberAddressDto>> pageMemberAddress(MemberAddressDto memberAddressDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false) Integer size) {
        Integer pageIndex = currentPage == null ? GlobalConstants.DEFAULT_PAGE_INDEX : currentPage;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<MemberAddressDto> page = new Page(pageIndex, pageSize);

        IPage<MemberAddressDto> pages = memberAddressService.pageMemberAddress(page, memberAddressDto);
        return RespBody.data(pages);
    }


    @ApiOperation("会员收货地址列表")
    @GetMapping("/list")
    public RespBody<List<MemberAddressDto>> listMemberAddress(MemberAddressDto memberAddressDto) {
        final MemberDto memberDto = WebUtil.currentMember();
        memberAddressDto.setMemberId(memberDto.getId());
        List<MemberAddressDto> memberAddressDtos = memberAddressService.listMemberAddress(memberAddressDto);
        memberAddressDtos.forEach(item -> {
            item.setIsDefaultBool(item.getIsDefault() == 1);
        });
        return RespBody.data(memberAddressDtos);
    }

    @ApiOperation("修改默认地址")
    @PostMapping("/changeDefaultAddress")
    public RespBody<MemberAddressDto> changeDefaultAddress(@RequestBody MemberAddressDto memberAddressDto) {
        if (memberAddressDto.getId() == null) {
            throw new ErrorException(GlobalRespCode.PARAM_MISSED);
        }
        final MemberAddressDto getById = memberAddressService.getByIdExtends(memberAddressDto.getId());
        if (getById == null) {
            throw new ErrorException(GlobalRespCode.PARAM_MISSED);
        }

        MemberAddressEntity memberAddressEntity = ConvertUtil.transformObj(getById, MemberAddressEntity.class);
        memberAddressService.changeDefaultAddress(memberAddressEntity, memberAddressDto.getIsDefault());

        MemberAddressDto result = ConvertUtil.transformObj(memberAddressEntity, MemberAddressDto.class);
        result.setIsDefaultBool(memberAddressEntity.getIsDefault() == 1);
        return RespBody.data(result);
    }

    /**
     * 会员收货地址表保存
     *
     * @param memberAddressDto 会员收货地址表实体类
     * @return
     */
    @ApiOperation("会员收货地址表保存")
    @PostMapping("/save")
    public RespBody<String> saveMemberAddress(@RequestBody MemberAddressDto memberAddressDto) {
        if (memberAddressDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAddressEntity memberAddressEntity = ConvertUtil.transformObj(memberAddressDto, MemberAddressEntity.class);
        memberAddressEntity.setMemberId(WebUtil.currentMember().getId());
        if (memberAddressService.save(memberAddressEntity)) {
            memberAddressService.changeDefaultAddress(memberAddressEntity, memberAddressDto.getIsDefault());
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员收货地址表编辑
     *
     * @param memberAddressDto 会员收货地址表实体类
     * @return
     */
    @ApiOperation("会员收货地址表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateMemberAddress(@RequestBody MemberAddressDto memberAddressDto) {
        if (memberAddressDto == null || memberAddressDto.getId() == null || memberAddressDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAddressEntity memberAddressEntity = ConvertUtil.transformObj(memberAddressDto, MemberAddressEntity.class);
        if (memberAddressService.updateById(memberAddressEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员收货地址表删除
     *
     * @param ids 会员收货地址表id集合
     * @return
     */
    @ApiOperation("会员收货地址表删除")
    @GetMapping("/delete")
    public RespBody<String> deleteMemberAddress(Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (memberAddressService.removeById(id)) {
            return RespBody.data("删除成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员收货地址表详情
     *
     * @param id 会员收货地址表id
     * @return
     */
    @ApiOperation("会员收货地址表详情")
    @GetMapping("/info")
    public RespBody<MemberAddressDto> infoMemberAddress(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAddressEntity memberAddressEntity = memberAddressService.getById(id);
        MemberAddressDto memberAddressDto = ConvertUtil.transformObj(memberAddressEntity, MemberAddressDto.class);
        memberAddressDto.setIsDefaultBool(YesNoEnum.YES.getCode().equals(memberAddressDto.getIsDefault()));
        return RespBody.data(memberAddressDto);
    }
}
