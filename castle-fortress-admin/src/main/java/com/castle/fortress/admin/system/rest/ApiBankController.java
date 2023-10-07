package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.entity.BankEntity;
import com.castle.fortress.admin.system.dto.BankDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.BankService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 银行信息 api 控制器
 *
 * @author castle
 * @since 2022-11-02
 */
@Api(tags="银行信息api管理控制器")
@RestController
@RequestMapping("/api/system/bank")
public class ApiBankController {
    @Autowired
    private BankService bankService;


    /**
     * 银行信息的分页展示
     * @param bankDto 银行信息实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("银行信息分页展示")
    @GetMapping("/page")
    public RespBody<IPage<BankDto>> pageBank(BankDto bankDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<BankDto> page = new Page(pageIndex, pageSize);

        IPage<BankDto> pages = bankService.pageBank(page, bankDto);
        return RespBody.data(pages);
    }

    /**
     * 银行信息保存
     * @param bankDto 银行信息实体类
     * @return
     */
    @ApiOperation("银行信息保存")
    @PostMapping("/save")
    public RespBody<String> saveBank(@RequestBody BankDto bankDto){
        if(bankDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            BankEntity bankEntity = ConvertUtil.transformObj(bankDto,BankEntity.class);
        if(bankService.save(bankEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 银行信息编辑
     * @param bankDto 银行信息实体类
     * @return
     */
    @ApiOperation("银行信息编辑")
    @PostMapping("/edit")
    public RespBody<String> updateBank(@RequestBody BankDto bankDto){
        if(bankDto == null || bankDto.getId() == null || bankDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            BankEntity bankEntity = ConvertUtil.transformObj(bankDto,BankEntity.class);
        if(bankService.updateById(bankEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 银行信息删除
     * @param ids 银行信息id集合
     * @return
     */
    @ApiOperation("银行信息删除")
    @PostMapping("/delete")
    public RespBody<String> deleteBank(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(bankService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 银行信息详情
     * @param id 银行信息id
     * @return
     */
    @ApiOperation("银行信息详情")
    @GetMapping("/info")
    public RespBody<BankDto> infoBank(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            BankEntity bankEntity = bankService.getById(id);
            BankDto bankDto = ConvertUtil.transformObj(bankEntity,BankDto.class);
        return RespBody.data(bankDto);
    }


}
