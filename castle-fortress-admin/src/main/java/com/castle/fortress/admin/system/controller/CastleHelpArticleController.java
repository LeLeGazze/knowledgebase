package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.CastleHelpArticleDto;
import com.castle.fortress.admin.system.entity.CastleHelpArticleEntity;
import com.castle.fortress.admin.system.service.CastleHelpArticleService;
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
 * 帮助中心文章 控制器
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Api(tags="帮助中心文章管理控制器")
@Controller
public class CastleHelpArticleController {
    @Autowired
    private CastleHelpArticleService castleHelpArticleService;

    /**
     * 帮助中心文章的分页展示
     * @param castleHelpArticleDto 帮助中心文章实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("帮助中心文章分页展示")
    @GetMapping("/system/castleHelpArticle/page")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticle:pageList")
    public RespBody<IPage<CastleHelpArticleDto>> pageCastleHelpArticle(CastleHelpArticleDto castleHelpArticleDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleHelpArticleDto> page = new Page(pageIndex, pageSize);
        IPage<CastleHelpArticleDto> pages = castleHelpArticleService.pageCastleHelpArticleExtends(page, castleHelpArticleDto);

        return RespBody.data(pages);
    }

    /**
     * 帮助中心文章的列表展示
     * @param castleHelpArticleDto 帮助中心文章实体类
     * @return
     */
    @ApiOperation("帮助中心文章列表展示")
    @GetMapping("/system/castleHelpArticle/list")
    @ResponseBody
    public RespBody<List<CastleHelpArticleDto>> listCastleHelpArticle(CastleHelpArticleDto castleHelpArticleDto){
        List<CastleHelpArticleDto> list = castleHelpArticleService.listCastleHelpArticle(castleHelpArticleDto);
        return RespBody.data(list);
    }

    /**
     * 帮助中心文章保存
     * @param castleHelpArticleDto 帮助中心文章实体类
     * @return
     */
    @ApiOperation("帮助中心文章保存")
    @PostMapping("/system/castleHelpArticle/save")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticle:save")
    public RespBody<String> saveCastleHelpArticle(@RequestBody CastleHelpArticleDto castleHelpArticleDto){
        if(castleHelpArticleDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleHelpArticleEntity castleHelpArticleEntity = ConvertUtil.transformObj(castleHelpArticleDto,CastleHelpArticleEntity.class);
        if(castleHelpArticleService.save(castleHelpArticleEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 帮助中心文章编辑
     * @param castleHelpArticleDto 帮助中心文章实体类
     * @return
     */
    @ApiOperation("帮助中心文章编辑")
    @PostMapping("/system/castleHelpArticle/edit")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticle:edit")
    public RespBody<String> updateCastleHelpArticle(@RequestBody CastleHelpArticleDto castleHelpArticleDto){
        if(castleHelpArticleDto == null || castleHelpArticleDto.getId() == null || castleHelpArticleDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleHelpArticleEntity castleHelpArticleEntity = ConvertUtil.transformObj(castleHelpArticleDto,CastleHelpArticleEntity.class);
        if(castleHelpArticleService.updateById(castleHelpArticleEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 帮助中心文章删除
     * @param id
     * @return
     */
    @ApiOperation("帮助中心文章删除")
    @PostMapping("/system/castleHelpArticle/delete")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticle:delete")
    public RespBody<String> deleteCastleHelpArticle(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleHelpArticleService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 帮助中心文章批量删除
     * @param ids
     * @return
     */
    @ApiOperation("帮助中心文章批量删除")
    @PostMapping("/system/castleHelpArticle/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticle:deleteBatch")
    public RespBody<String> deleteCastleHelpArticleBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleHelpArticleService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 帮助中心文章详情
     * @param id 帮助中心文章id
     * @return
     */
    @ApiOperation("帮助中心文章详情")
    @GetMapping("/system/castleHelpArticle/info")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticle:info")
    public RespBody<CastleHelpArticleDto> infoCastleHelpArticle(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleHelpArticleDto castleHelpArticleDto =  castleHelpArticleService.getByIdExtends(id);

        return RespBody.data(castleHelpArticleDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/system/castleHelpArticle/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<CastleHelpArticleDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<CastleHelpArticleDto> list = castleHelpArticleService.listCastleHelpArticle(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
