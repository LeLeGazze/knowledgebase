package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.CastleSysIndustryDto;
import com.castle.fortress.admin.system.entity.CastleSysIndustryEntity;
import com.castle.fortress.admin.system.service.CastleSysIndustryService;
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
 * 行业职位 api 控制器
 *
 * @author Mgg
 * @since 2021-09-02
 */
@Api(tags="行业职位api管理控制器")
@RestController
@RequestMapping("/api/system/castleSysIndustry")
public class ApiCastleSysIndustryController {
    @Autowired
    private CastleSysIndustryService castleSysIndustryService;


    /**
     * 行业职位的分页展示
     * @param castleSysIndustryDto 行业职位实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("行业职位分页展示")
    @GetMapping("/page")
    public RespBody<IPage<CastleSysIndustryDto>> pageCastleSysIndustry(CastleSysIndustryDto castleSysIndustryDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleSysIndustryDto> page = new Page(pageIndex, pageSize);

        IPage<CastleSysIndustryDto> pages = castleSysIndustryService.pageCastleSysIndustry(page, castleSysIndustryDto);
        return RespBody.data(pages);
    }

    /**
     * 行业职位保存
     * @param castleSysIndustryDto 行业职位实体类
     * @return
     */
    @ApiOperation("行业职位保存")
    @PostMapping("/save")
    public RespBody<String> saveCastleSysIndustry(@RequestBody CastleSysIndustryDto castleSysIndustryDto){
        if(castleSysIndustryDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysIndustryEntity castleSysIndustryEntity = ConvertUtil.transformObj(castleSysIndustryDto,CastleSysIndustryEntity.class);
        if(castleSysIndustryService.save(castleSysIndustryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 行业职位编辑
     * @param castleSysIndustryDto 行业职位实体类
     * @return
     */
    @ApiOperation("行业职位编辑")
    @PostMapping("/edit")
    public RespBody<String> updateCastleSysIndustry(@RequestBody CastleSysIndustryDto castleSysIndustryDto){
        if(castleSysIndustryDto == null || castleSysIndustryDto.getId() == null || castleSysIndustryDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysIndustryEntity castleSysIndustryEntity = ConvertUtil.transformObj(castleSysIndustryDto,CastleSysIndustryEntity.class);
        if(castleSysIndustryService.updateById(castleSysIndustryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 行业职位删除
     * @param ids 行业职位id集合
     * @return
     */
    @ApiOperation("行业职位删除")
    @PostMapping("/delete")
    public RespBody<String> deleteCastleSysIndustry(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleSysIndustryService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 行业职位详情
     * @param id 行业职位id
     * @return
     */
    @ApiOperation("行业职位详情")
    @GetMapping("/info")
    public RespBody<CastleSysIndustryDto> infoCastleSysIndustry(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysIndustryEntity castleSysIndustryEntity = castleSysIndustryService.getById(id);
            CastleSysIndustryDto castleSysIndustryDto = ConvertUtil.transformObj(castleSysIndustryEntity,CastleSysIndustryDto.class);
        return RespBody.data(castleSysIndustryDto);
    }




}
