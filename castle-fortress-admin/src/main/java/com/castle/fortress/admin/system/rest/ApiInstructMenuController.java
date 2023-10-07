package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.entity.InstructMenuEntity;
import com.castle.fortress.admin.system.dto.InstructMenuDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.InstructMenuService;
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
 * 菜单指令配置表 api 控制器
 *
 * @author castle
 * @since 2022-08-24
 */
@Api(tags="菜单指令配置表api管理控制器")
@RestController
@RequestMapping("/api/system/instructMenu")
public class ApiInstructMenuController {
    @Autowired
    private InstructMenuService instructMenuService;


    /**
     * 菜单指令配置表的分页展示
     * @param instructMenuDto 菜单指令配置表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("菜单指令配置表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<InstructMenuDto>> pageInstructMenu(InstructMenuDto instructMenuDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<InstructMenuDto> page = new Page(pageIndex, pageSize);

        IPage<InstructMenuDto> pages = instructMenuService.pageInstructMenu(page, instructMenuDto);
        return RespBody.data(pages);
    }

    /**
     * 菜单指令配置表保存
     * @param instructMenuDto 菜单指令配置表实体类
     * @return
     */
    @ApiOperation("菜单指令配置表保存")
    @PostMapping("/save")
    public RespBody<String> saveInstructMenu(@RequestBody InstructMenuDto instructMenuDto){
        if(instructMenuDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            InstructMenuEntity instructMenuEntity = ConvertUtil.transformObj(instructMenuDto,InstructMenuEntity.class);
        if(instructMenuService.save(instructMenuEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 菜单指令配置表编辑
     * @param instructMenuDto 菜单指令配置表实体类
     * @return
     */
    @ApiOperation("菜单指令配置表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateInstructMenu(@RequestBody InstructMenuDto instructMenuDto){
        if(instructMenuDto == null || instructMenuDto.getId() == null || instructMenuDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            InstructMenuEntity instructMenuEntity = ConvertUtil.transformObj(instructMenuDto,InstructMenuEntity.class);
        if(instructMenuService.updateById(instructMenuEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 菜单指令配置表删除
     * @param ids 菜单指令配置表id集合
     * @return
     */
    @ApiOperation("菜单指令配置表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteInstructMenu(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(instructMenuService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 菜单指令配置表详情
     * @param id 菜单指令配置表id
     * @return
     */
    @ApiOperation("菜单指令配置表详情")
    @GetMapping("/info")
    public RespBody<InstructMenuDto> infoInstructMenu(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            InstructMenuEntity instructMenuEntity = instructMenuService.getById(id);
            InstructMenuDto instructMenuDto = ConvertUtil.transformObj(instructMenuEntity,InstructMenuDto.class);
        return RespBody.data(instructMenuDto);
    }


}
