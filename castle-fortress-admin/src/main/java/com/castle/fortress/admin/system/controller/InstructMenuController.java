package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysMenuDto;
import com.castle.fortress.admin.system.entity.InstructMenuEntity;
import com.castle.fortress.admin.system.dto.InstructMenuDto;
import com.castle.fortress.admin.system.entity.SysMenu;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.InstructMenuService;
import com.castle.fortress.admin.system.service.SysMenuService;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;
import javax.servlet.http.HttpServletResponse;
import com.castle.fortress.common.entity.DynamicExcelEntity;
//import sun.swing.StringUIClientPropertyKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单指令配置表 控制器
 *
 * @author castle
 * @since 2022-08-24
 */
@Api(tags="菜单指令配置表管理控制器")
@Controller
public class InstructMenuController {
    @Autowired
    private InstructMenuService instructMenuService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 菜单指令配置表的分页展示
     * @param instructMenuDto 菜单指令配置表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("菜单指令配置表分页展示")
    @GetMapping("/system/instructMenu/page")
    @ResponseBody
    @RequiresPermissions("system:instructMenu:pageList")
    public RespBody<IPage<InstructMenuDto>> pageInstructMenu(InstructMenuDto instructMenuDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<InstructMenuDto> page = new Page(pageIndex, pageSize);
        IPage<InstructMenuDto> pages = instructMenuService.pageInstructMenu(page, instructMenuDto);

        return RespBody.data(pages);
    }

    /**
     * 菜单指令配置表的列表展示
     * @param instructMenuDto 菜单指令配置表实体类
     * @return
     */
    @ApiOperation("菜单指令配置表列表展示")
    @GetMapping("/system/instructMenu/list")
    @ResponseBody
    public RespBody<List<InstructMenuDto>> listInstructMenu(InstructMenuDto instructMenuDto){
        List<InstructMenuDto> list = instructMenuService.listInstructMenu(instructMenuDto);
        return RespBody.data(list);
    }

    /**
     * 菜单指令配置表保存
     * @param instructMenuDto 菜单指令配置表实体类
     * @return
     */
    @ApiOperation("菜单指令配置表保存")
    @PostMapping("/system/instructMenu/save")
    @ResponseBody
    @RequiresPermissions("system:instructMenu:save")
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
    @PostMapping("/system/instructMenu/edit")
    @ResponseBody
    @RequiresPermissions("system:instructMenu:edit")
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
     * @param id
     * @return
     */
    @ApiOperation("菜单指令配置表删除")
    @PostMapping("/system/instructMenu/delete")
    @ResponseBody
    @RequiresPermissions("system:instructMenu:delete")
    public RespBody<String> deleteInstructMenu(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(instructMenuService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 菜单指令配置表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("菜单指令配置表批量删除")
    @PostMapping("/system/instructMenu/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:instructMenu:deleteBatch")
    public RespBody<String> deleteInstructMenuBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
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
    @GetMapping("/system/instructMenu/info")
    @ResponseBody
    @RequiresPermissions("system:instructMenu:info")
    public RespBody<InstructMenuDto> infoInstructMenu(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        InstructMenuEntity instructMenuEntity = instructMenuService.getById(id);
        InstructMenuDto instructMenuDto = ConvertUtil.transformObj(instructMenuEntity,InstructMenuDto.class);

        return RespBody.data(instructMenuDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/system/instructMenu/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<InstructMenuDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<InstructMenuDto> list = instructMenuService.listInstructMenu(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}

    /**
     * 菜单指令配置表过滤
     * @param filterInfo 过滤信息
     * @return
     */
    @ApiOperation("菜单指令配置表过滤")
    @GetMapping("/system/instructMenu/filter")
    @ResponseBody
    public RespBody<List<Map>> filterInstructMenu(String filterInfo){
        if(StrUtil.isEmpty(filterInfo)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser= WebUtil.currentUser();
        if(sysUser == null){
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds=null;
        //非超级管理员
        if(!sysUser.getIsSuperAdmin()){
            List<SysRole> roleList=sysUser.getRoles();
            if(roleList == null || roleList.isEmpty()){
                return RespBody.data(null);
            }
            if(roleList!=null&& roleList.size()>0){
                roleIds=new ArrayList<>();
                for(SysRole role:roleList){
                    roleIds.add(role.getId());
                }
            }
        }
        return RespBody.data(instructMenuService.filterInstructMenu(filterInfo,sysMenuService.authorityAllData(roleIds)));
    }


}
