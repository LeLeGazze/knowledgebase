package com.castle.fortress.admin.system.controller;

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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行业职位 控制器
 *
 * @author Mgg
 * @since 2021-09-02
 */
@Api(tags="行业职位管理控制器")
@Controller
public class CastleSysIndustryController {
    @Autowired
    private CastleSysIndustryService castleSysIndustryService;


    /**
     * 行业职位的分页展示
     * @param castleSysIndustryDto 行业职位实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("行业职位分页展示")
    @GetMapping("/system/castleSysIndustry/page")
    @ResponseBody
    @RequiresPermissions("system:castleSysIndustry:pageList")
    public RespBody<IPage<CastleSysIndustryDto>> pageCastleSysIndustry(CastleSysIndustryDto castleSysIndustryDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleSysIndustryDto> page = new Page(pageIndex, pageSize);
        IPage<CastleSysIndustryDto> pages = castleSysIndustryService.pageCastleSysIndustryExtends(page, castleSysIndustryDto);

        return RespBody.data(pages);
    }

    /**
     * 行业职位的列表展示
     * @param castleSysIndustryDto 行业职位实体类
     * @return
     */
    @ApiOperation("行业职位列表展示")
    @GetMapping("/system/castleSysIndustry/list")
    @ResponseBody
    public RespBody<List<CastleSysIndustryDto>> listCastleSysIndustry(CastleSysIndustryDto castleSysIndustryDto){
        List<CastleSysIndustryDto> list = castleSysIndustryService.listCastleSysIndustry(castleSysIndustryDto);
        return RespBody.data(list);
    }

    /**
     * 行业职位的树形展示
     * @param castleSysIndustryDto 行业职位实体类
     * @return
     */
    @ApiOperation("行业职位树形展示")
    @GetMapping("/system/castleSysIndustry/tree")
    @ResponseBody
    public RespBody<List<CastleSysIndustryEntity>> treeCastleSysIndustry(CastleSysIndustryDto castleSysIndustryDto){

        List<CastleSysIndustryEntity> list = ConvertUtil.transformObjList(castleSysIndustryService.listCastleSysIndustry(castleSysIndustryDto),CastleSysIndustryEntity.class);
        List<CastleSysIndustryEntity> treeList = ConvertUtil.listToTree(list);
        return RespBody.data(treeList);
    }

    /**
     * 行业职位保存
     * @param castleSysIndustryDto 行业职位实体类
     * @return
     */
    @ApiOperation("行业职位保存")
    @PostMapping("/system/castleSysIndustry/save")
    @ResponseBody
    @RequiresPermissions("system:castleSysIndustry:save")
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
    @PostMapping("/system/castleSysIndustry/edit")
    @ResponseBody
    @RequiresPermissions("system:castleSysIndustry:edit")
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
     * @param id
     * @return
     */
    @ApiOperation("行业职位删除")
    @PostMapping("/system/castleSysIndustry/delete")
    @ResponseBody
    @RequiresPermissions("system:castleSysIndustry:delete")
    public RespBody<String> deleteCastleSysIndustry(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleSysIndustryService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 行业职位批量删除
     * @param ids
     * @return
     */
    @ApiOperation("行业职位批量删除")
    @PostMapping("/system/castleSysIndustry/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:castleSysIndustry:deleteBatch")
    public RespBody<String> deleteCastleSysIndustryBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
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
    @GetMapping("/system/castleSysIndustry/info")
    @ResponseBody
    @RequiresPermissions("system:castleSysIndustry:info")
    public RespBody<CastleSysIndustryDto> infoCastleSysIndustry(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleSysIndustryDto castleSysIndustryDto =  castleSysIndustryService.getByIdExtends(id);

        return RespBody.data(castleSysIndustryDto);
    }

}
