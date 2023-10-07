package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysOssRecordDto;
import com.castle.fortress.admin.system.entity.SysOssRecordEntity;
import com.castle.fortress.admin.system.service.SysOssRecordService;
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
 * oss上传记录 api 控制器
 *
 * @author castle
 * @since 2022-03-01
 */
@Api(tags="oss上传记录api管理控制器")
@RestController
@RequestMapping("/api/system/sysOssRecord")
public class ApiSysOssRecordController {
    @Autowired
    private SysOssRecordService sysOssRecordService;


    /**
     * oss上传记录的分页展示
     * @param sysOssRecordDto oss上传记录实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("oss上传记录分页展示")
    @GetMapping("/page")
    public RespBody<IPage<SysOssRecordDto>> pageSysOssRecord(SysOssRecordDto sysOssRecordDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysOssRecordDto> page = new Page(pageIndex, pageSize);

        IPage<SysOssRecordDto> pages = sysOssRecordService.pageSysOssRecord(page, sysOssRecordDto);
        return RespBody.data(pages);
    }

    /**
     * oss上传记录保存
     * @param sysOssRecordDto oss上传记录实体类
     * @return
     */
    @ApiOperation("oss上传记录保存")
    @PostMapping("/save")
    public RespBody<String> saveSysOssRecord(@RequestBody SysOssRecordDto sysOssRecordDto){
        if(sysOssRecordDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysOssRecordEntity sysOssRecordEntity = ConvertUtil.transformObj(sysOssRecordDto,SysOssRecordEntity.class);
        if(sysOssRecordService.save(sysOssRecordEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * oss上传记录编辑
     * @param sysOssRecordDto oss上传记录实体类
     * @return
     */
    @ApiOperation("oss上传记录编辑")
    @PostMapping("/edit")
    public RespBody<String> updateSysOssRecord(@RequestBody SysOssRecordDto sysOssRecordDto){
        if(sysOssRecordDto == null || sysOssRecordDto.getId() == null || sysOssRecordDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysOssRecordEntity sysOssRecordEntity = ConvertUtil.transformObj(sysOssRecordDto,SysOssRecordEntity.class);
        if(sysOssRecordService.updateById(sysOssRecordEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * oss上传记录删除
     * @param ids oss上传记录id集合
     * @return
     */
    @ApiOperation("oss上传记录删除")
    @PostMapping("/delete")
    public RespBody<String> deleteSysOssRecord(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysOssRecordService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * oss上传记录详情
     * @param id oss上传记录id
     * @return
     */
    @ApiOperation("oss上传记录详情")
    @GetMapping("/info")
    public RespBody<SysOssRecordDto> infoSysOssRecord(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysOssRecordEntity sysOssRecordEntity = sysOssRecordService.getById(id);
            SysOssRecordDto sysOssRecordDto = ConvertUtil.transformObj(sysOssRecordEntity,SysOssRecordDto.class);
        return RespBody.data(sysOssRecordDto);
    }


}
