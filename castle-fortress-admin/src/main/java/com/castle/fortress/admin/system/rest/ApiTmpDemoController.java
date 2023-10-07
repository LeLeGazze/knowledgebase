package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.TmpDemoDto;
import com.castle.fortress.admin.system.entity.TmpDemoEntity;
import com.castle.fortress.admin.system.service.TmpDemoService;
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
 * 组件示例表 api 控制器
 *
 * @author castle
 * @since 2021-06-02
 */
@Api(tags="组件示例表api管理控制器")
@RestController
@RequestMapping("/api/system/tmpDemo")
public class ApiTmpDemoController {
    @Autowired
    private TmpDemoService tmpDemoService;


    /**
     * 组件示例表的分页展示
     * @param tmpDemoDto 组件示例表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("组件示例表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<TmpDemoDto>> pageTmpDemo(TmpDemoDto tmpDemoDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<TmpDemoDto> page = new Page(pageIndex, pageSize);

        IPage<TmpDemoDto> pages = tmpDemoService.pageTmpDemo(page, tmpDemoDto);
        return RespBody.data(pages);
    }

    /**
     * 组件示例表保存
     * @param tmpDemoDto 组件示例表实体类
     * @return
     */
    @ApiOperation("组件示例表保存")
    @PostMapping("/save")
    public RespBody<String> saveTmpDemo(@RequestBody TmpDemoDto tmpDemoDto){
        if(tmpDemoDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            TmpDemoEntity tmpDemoEntity = ConvertUtil.transformObj(tmpDemoDto,TmpDemoEntity.class);
        if(tmpDemoService.save(tmpDemoEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 组件示例表编辑
     * @param tmpDemoDto 组件示例表实体类
     * @return
     */
    @ApiOperation("组件示例表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateTmpDemo(@RequestBody TmpDemoDto tmpDemoDto){
        if(tmpDemoDto == null || tmpDemoDto.getId() == null || tmpDemoDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            TmpDemoEntity tmpDemoEntity = ConvertUtil.transformObj(tmpDemoDto,TmpDemoEntity.class);
        if(tmpDemoService.updateById(tmpDemoEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 组件示例表删除
     * @param ids 组件示例表id集合
     * @return
     */
    @ApiOperation("组件示例表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteTmpDemo(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(tmpDemoService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 组件示例表详情
     * @param id 组件示例表id
     * @return
     */
    @ApiOperation("组件示例表详情")
    @GetMapping("/info")
    public RespBody<TmpDemoDto> infoTmpDemo(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            TmpDemoEntity tmpDemoEntity = tmpDemoService.getById(id);
            TmpDemoDto tmpDemoDto = ConvertUtil.transformObj(tmpDemoEntity,TmpDemoDto.class);
        return RespBody.data(tmpDemoDto);
    }


}
