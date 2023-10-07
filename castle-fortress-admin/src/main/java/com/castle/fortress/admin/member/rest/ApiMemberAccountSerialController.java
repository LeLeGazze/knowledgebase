package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberAccountSerialDto;
import com.castle.fortress.admin.member.entity.MemberAccountSerialEntity;
import com.castle.fortress.admin.member.service.MemberAccountSerialService;
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
 * 会员账户流水 api 控制器
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Api(tags="会员账户流水api管理控制器")
@RestController
@RequestMapping("/api/member/memberAccountSerial")
public class ApiMemberAccountSerialController {
    @Autowired
    private MemberAccountSerialService memberAccountSerialService;


    /**
     * 会员账户流水的分页展示
     * @param memberAccountSerialDto 会员账户流水实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员账户流水分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberAccountSerialDto>> pageMemberAccountSerial(MemberAccountSerialDto memberAccountSerialDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberAccountSerialDto> page = new Page(pageIndex, pageSize);

        IPage<MemberAccountSerialDto> pages = memberAccountSerialService.pageMemberAccountSerial(page, memberAccountSerialDto);
        return RespBody.data(pages);
    }

    /**
     * 会员账户流水保存
     * @param memberAccountSerialDto 会员账户流水实体类
     * @return
     */
    @ApiOperation("会员账户流水保存")
    @PostMapping("/save")
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
    @PostMapping("/edit")
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
     * @param ids 会员账户流水id集合
     * @return
     */
    @ApiOperation("会员账户流水删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberAccountSerial(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
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
    @GetMapping("/info")
    public RespBody<MemberAccountSerialDto> infoMemberAccountSerial(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberAccountSerialEntity memberAccountSerialEntity = memberAccountSerialService.getById(id);
            MemberAccountSerialDto memberAccountSerialDto = ConvertUtil.transformObj(memberAccountSerialEntity,MemberAccountSerialDto.class);
        return RespBody.data(memberAccountSerialDto);
    }


}
