package com.castle.fortress.admin.demo.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.demo.dto.TmpDemoGenerateDto;
import com.castle.fortress.admin.demo.entity.TmpDemoGenerateEntity;
import com.castle.fortress.admin.demo.service.TmpDemoGenerateService;
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
 * 代码生成示例表 api 控制器
 *
 * @author castle
 * @since 2021-11-08
 */
@Api(tags="代码生成示例表api管理控制器")
@RestController
@RequestMapping("/api/demo/tmpDemoGenerate")
public class ApiTmpDemoGenerateController {
    @Autowired
    private TmpDemoGenerateService tmpDemoGenerateService;


    /**
     * 代码生成示例表的分页展示
     * @param tmpDemoGenerateDto 代码生成示例表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("代码生成示例表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<TmpDemoGenerateDto>> pageTmpDemoGenerate(TmpDemoGenerateDto tmpDemoGenerateDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<TmpDemoGenerateDto> page = new Page(pageIndex, pageSize);

        IPage<TmpDemoGenerateDto> pages = tmpDemoGenerateService.pageTmpDemoGenerate(page, tmpDemoGenerateDto);
        return RespBody.data(pages);
    }

    /**
     * 代码生成示例表保存
     * @param tmpDemoGenerateDto 代码生成示例表实体类
     * @return
     */
    @ApiOperation("代码生成示例表保存")
    @PostMapping("/save")
    public RespBody<String> saveTmpDemoGenerate(@RequestBody TmpDemoGenerateDto tmpDemoGenerateDto){
        if(tmpDemoGenerateDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            TmpDemoGenerateEntity tmpDemoGenerateEntity = ConvertUtil.transformObj(tmpDemoGenerateDto,TmpDemoGenerateEntity.class);
        if(tmpDemoGenerateService.save(tmpDemoGenerateEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 代码生成示例表编辑
     * @param tmpDemoGenerateDto 代码生成示例表实体类
     * @return
     */
    @ApiOperation("代码生成示例表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateTmpDemoGenerate(@RequestBody TmpDemoGenerateDto tmpDemoGenerateDto){
        if(tmpDemoGenerateDto == null || tmpDemoGenerateDto.getId() == null || tmpDemoGenerateDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            TmpDemoGenerateEntity tmpDemoGenerateEntity = ConvertUtil.transformObj(tmpDemoGenerateDto,TmpDemoGenerateEntity.class);
        if(tmpDemoGenerateService.updateById(tmpDemoGenerateEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 代码生成示例表删除
     * @param ids 代码生成示例表id集合
     * @return
     */
    @ApiOperation("代码生成示例表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteTmpDemoGenerate(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(tmpDemoGenerateService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 代码生成示例表详情
     * @param id 代码生成示例表id
     * @return
     */
    @ApiOperation("代码生成示例表详情")
    @GetMapping("/info")
    public RespBody<TmpDemoGenerateDto> infoTmpDemoGenerate(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            TmpDemoGenerateEntity tmpDemoGenerateEntity = tmpDemoGenerateService.getById(id);
            TmpDemoGenerateDto tmpDemoGenerateDto = ConvertUtil.transformObj(tmpDemoGenerateEntity,TmpDemoGenerateDto.class);
        return RespBody.data(tmpDemoGenerateDto);
    }


}
