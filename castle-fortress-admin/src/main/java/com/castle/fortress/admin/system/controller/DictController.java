package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysDictDto;
import com.castle.fortress.admin.system.entity.SysDictEntity;
import com.castle.fortress.admin.system.service.SysDictService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统字典管理
 * @author castle
 */
@Controller
@Api(tags = "字典管理控制器")
public class DictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 系统字典表的分页展示
     * @param sysDictDto 系统字典表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="字典分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("系统字典表分页展示")
    @GetMapping("/system/dict/page")
    @ResponseBody
    @RequiresPermissions("system:sysDict:pageList")
    public RespBody<IPage<SysDictDto>> pageSysDictDto(SysDictDto sysDictDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysDictDto> page = new Page(pageIndex, pageSize);
        IPage<SysDictDto> pages = sysDictService.pageSysDict(page, sysDictDto);
        return RespBody.data(pages);
    }

    /**
     * 系统字典表子集分页展示
     * @param sysDictDto 系统字典表实体类 parentId 必填
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="字典子集分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("系统字典表子集分页展示")
    @GetMapping("/system/dict/subpage")
    @ResponseBody
    @RequiresPermissions("system:sysDict:pageList")
    public RespBody<IPage<SysDictDto>> subpageSysDictDto(SysDictDto sysDictDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        if(sysDictDto==null || sysDictDto.getParentId() == 0){
            return RespBody.data(null);
        }
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysDictDto> page = new Page(pageIndex, pageSize);
        IPage<SysDictDto> pages = sysDictService.subPageSysDict(page, sysDictDto);
        return RespBody.data(pages);
    }

    /**
     * 所有字典数据
     * @return
     */
    @CastleLog(operLocation="字典数据",operType= OperationTypeEnum.QUERY)
    @ApiOperation("所有字典数据")
    @GetMapping("/system/dict/listAll")
    @ResponseBody
    public RespBody<List<SysDictDto>> allDict(){
        List<SysDictEntity> dictList = sysDictService.list();
        List<SysDictDto> dictDtos = ConvertUtil.listToTree(ConvertUtil.transformObjList(dictList,SysDictDto.class));
        return RespBody.data(dictDtos);
    }

    /**
     * 所有字典数据
     * @return
     */
    @CastleLog(operLocation="字典数据",operType= OperationTypeEnum.QUERY)
    @ApiOperation("所有字典数据")
    @GetMapping("/system/dict/listAllParent")
    @ResponseBody
    public RespBody<List<SysDictDto>> allDictParent(){
        SysDictEntity sysDict = new SysDictEntity();
        sysDict.setParentId(0L);
        sysDict.setStatus(YesNoEnum.YES.getCode());
        List<SysDictEntity> dictList = sysDictService.selectBySelective(sysDict);
        return RespBody.data(ConvertUtil.transformObjList(dictList,SysDictDto.class));
    }

    /**
     * 获取指定的字典集
     * @return
     */
    @CastleLog(operLocation="获取字典",operType= OperationTypeEnum.QUERY)
    @ApiOperation("获取指定的字典集")
    @GetMapping("/system/dict/listByCode")
    @ResponseBody
    public RespBody<List<Map<String,Object>>> listByCode(@Param("code") String code){
        if(StrUtil.isEmpty(code)){
            return RespBody.data(null);
        }
        List<SysDictDto> list = sysDictService.listByCode(code);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(SysDictDto dictDto:list){
            Map<String,Object> map = new HashMap<>();
            map.put("code",dictDto.getDictKey());
            map.put("name",dictDto.getDictValue());
            mapList.add(map);
        }
        return RespBody.data(mapList);
    }

    /**
     * 字典保存
     * @param sysDictDto 字典实体类
     * @return
     */
    @CastleLog(operLocation="字典保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("字典保存")
    @PostMapping("/system/dict/save")
    @ResponseBody
    @RequiresPermissions("system:sysDict:save")
    public RespBody<String> saveSysDict(@RequestBody SysDictDto sysDictDto){
        if(sysDictDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysDictEntity sysDict = ConvertUtil.transformObj(sysDictDto,SysDictEntity.class);
        //字典组
        if(sysDict.getParentId() == 0){
            SysDictEntity tempDict = new SysDictEntity();
            tempDict.setCode(sysDictDto.getCode());
            List<SysDictEntity> list = sysDictService.selectBySelective(tempDict);
            if(list!=null && list.size()>0){
                throw new BizException(BizErrorCode.DICT_CODE_EXIST_ERROR);
            }
        //字典集
        }else{
            SysDictEntity tempDict = new SysDictEntity();
            tempDict.setCode(sysDictDto.getCode());
            tempDict.setParentId(sysDict.getParentId());
            tempDict.setDictKey(sysDict.getDictKey());
            List<SysDictEntity> list = sysDictService.selectBySelective(tempDict);
            if(list!=null && list.size()>0){
                throw new BizException(BizErrorCode.DICT_VALUE_EXIST_ERROR);
            }
        }
        if(sysDictService.save(sysDict)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 字典编辑
     * @param sysDictDto 字典实体类
     * @return
     */
    @CastleLog(operLocation="字典编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("字典编辑")
    @PostMapping("/system/dict/edit")
    @ResponseBody
    @RequiresPermissions("system:sysDict:edit")
    public RespBody<String> updateSysDict(@RequestBody SysDictDto sysDictDto){
        if(sysDictDto == null || sysDictDto.getId() == null || sysDictDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysDictEntity sysDict = ConvertUtil.transformObj(sysDictDto,SysDictEntity.class);
        boolean subUpdateFlag = false;
        //字典组
        if(sysDict.getParentId() == 0){
            //校验code是否重复
            SysDictEntity tempDict = new SysDictEntity();
            tempDict.setCode(sysDictDto.getCode());
            tempDict.setParentId(0L);
            List<SysDictEntity> list = sysDictService.selectBySelective(tempDict);
            for(SysDictEntity dictEntity:list){
                if(!dictEntity.getId().equals(sysDict.getId())){
                    throw new BizException(BizErrorCode.DICT_CODE_EXIST_ERROR);
                }
            }
            //校验code是否发生变化
            tempDict=sysDictService.getById(sysDict.getId());
            if(!tempDict.getCode().equals(sysDict.getCode())){
                subUpdateFlag = true;
            }
        //字典集
        }else{
            SysDictEntity tempDict = new SysDictEntity();
            tempDict.setCode(sysDictDto.getCode());
            tempDict.setParentId(sysDict.getParentId());
            tempDict.setDictKey(sysDict.getDictKey());
            List<SysDictEntity> list = sysDictService.selectBySelective(tempDict);
            for(SysDictEntity dictEntity:list){
                if(!dictEntity.getId().equals(sysDict.getId())){
                    throw new BizException(BizErrorCode.DICT_VALUE_EXIST_ERROR);
                }
            }
        }
        if(sysDictService.updateById(sysDict)){
            //字典组修改时需要同步修改字典集的code
            if(subUpdateFlag){
                sysDictService.updateSubCode(sysDict);
            }
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 删除该字典及子集
     * @param id 字典id
     * @return
     */
    @CastleLog(operLocation="字典删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("字典删除")
    @PostMapping("/system/dict/delete")
    @ResponseBody
    @RequiresPermissions("system:sysDict:delete")
    public RespBody<String> deleteSysDict(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysDictService.deleteDict(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 字典详情
     * @param id 字典id
     * @return
     */
    @CastleLog(operLocation="字典详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("字典详情")
    @GetMapping("/system/dict/info")
    @ResponseBody
    @RequiresPermissions("system:sysDict:info")
    public RespBody<SysDictDto> infoSysDict(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysDictEntity dictEntity = sysDictService.getById(id);
        return RespBody.data(ConvertUtil.transformObj(dictEntity,SysDictDto.class));
    }




}
