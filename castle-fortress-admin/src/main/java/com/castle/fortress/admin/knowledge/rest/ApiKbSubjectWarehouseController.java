package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbSubjectWarehouseEntity;
import com.castle.fortress.admin.knowledge.dto.KbSubjectWarehouseDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbSubjectWarehouseService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
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
 * 主题知识仓库 api 控制器
 *
 * @author lyz
 * @since 2023-04-24
 */
@Api(tags="主题知识仓库api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbSubjectWarehouse")
public class ApiKbSubjectWarehouseController {
    @Autowired
    private KbSubjectWarehouseService kbSubjectWarehouseService;


    /**
     * 主题知识仓库的分页展示
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="主题知识仓库-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbSubjectWarehouseDto>> pageKbSubjectWarehouse(KbSubjectWarehouseDto kbSubjectWarehouseDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbSubjectWarehouseDto> page = new Page(pageIndex, pageSize);

        IPage<KbSubjectWarehouseDto> pages = kbSubjectWarehouseService.pageKbSubjectWarehouse(page, kbSubjectWarehouseDto);
        return RespBody.data(pages);
    }

    /**
     * 主题知识仓库保存
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation="主题知识仓库-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("主题知识仓库保存")
    @PostMapping("/save")
    public RespBody<String> saveKbSubjectWarehouse(@RequestBody KbSubjectWarehouseDto kbSubjectWarehouseDto){
        if(kbSubjectWarehouseDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbSubjectWarehouseEntity kbSubjectWarehouseEntity = ConvertUtil.transformObj(kbSubjectWarehouseDto,KbSubjectWarehouseEntity.class);
        if(kbSubjectWarehouseService.save(kbSubjectWarehouseEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库编辑
     * @param kbSubjectWarehouseDto 主题知识仓库实体类
     * @return
     */
    @CastleLog(operLocation="主题知识仓库-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("主题知识仓库编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbSubjectWarehouse(@RequestBody KbSubjectWarehouseDto kbSubjectWarehouseDto){
        if(kbSubjectWarehouseDto == null || kbSubjectWarehouseDto.getId() == null || kbSubjectWarehouseDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbSubjectWarehouseEntity kbSubjectWarehouseEntity = ConvertUtil.transformObj(kbSubjectWarehouseDto,KbSubjectWarehouseEntity.class);
        if(kbSubjectWarehouseService.updateById(kbSubjectWarehouseEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库删除
     * @param ids 主题知识仓库id集合
     * @return
     */
    @CastleLog(operLocation="主题知识仓库-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("主题知识仓库删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbSubjectWarehouse(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbSubjectWarehouseService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库详情
     * @param id 主题知识仓库id
     * @return
     */
    @CastleLog(operLocation="主题知识仓库-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库详情")
    @GetMapping("/info")
    public RespBody<KbSubjectWarehouseDto> infoKbSubjectWarehouse(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbSubjectWarehouseEntity kbSubjectWarehouseEntity = kbSubjectWarehouseService.getById(id);
            KbSubjectWarehouseDto kbSubjectWarehouseDto = ConvertUtil.transformObj(kbSubjectWarehouseEntity,KbSubjectWarehouseDto.class);
        return RespBody.data(kbSubjectWarehouseDto);
    }


}
