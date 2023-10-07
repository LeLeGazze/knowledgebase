package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbLabelCategoryDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.service.KbLabelCategoryService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
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

import java.util.*;

/**
 * 标签分类表 控制器
 *
 * @author 
 * @since 2023-06-14
 */
@Api(tags="标签分类表管理控制器")
@Controller
public class KbLabelCategoryController {
    @Autowired
    private KbLabelCategoryService kbLabelCategoryService;

    /**
     * 标签分类表的分页展示
     * @param kbLabelCategoryDto 标签分类表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="标签分类表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分类表分页展示")
    @GetMapping("/knowledge/kbLabelCategory/page")
    @ResponseBody
//    @RequiresPermissions("knowledge:kbLabelCategory:pageList")
    public RespBody<IPage<KbLabelCategoryDto>> pageKbLabelCategory(KbLabelCategoryDto kbLabelCategoryDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbLabelCategoryDto> page = new Page(pageIndex, pageSize);
        kbLabelCategoryDto.setIsDeleted(2);
        IPage<KbLabelCategoryDto> pages = kbLabelCategoryService.pageKbLabelCategory(page, kbLabelCategoryDto);

        return RespBody.data(pages);
    }

    /**
     * 标签分类表的列表展示
     * @param kbLabelCategoryDto 标签分类表实体类
     * @return
     */
    @CastleLog(operLocation="标签分类表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分类表列表展示")
    @GetMapping("/knowledge/kbLabelCategory/list")
    @ResponseBody
    public RespBody<List<KbLabelCategoryDto>> listKbLabelCategory(KbLabelCategoryDto kbLabelCategoryDto){
        kbLabelCategoryDto.setIsDeleted(2);
        List<KbLabelCategoryDto> list = kbLabelCategoryService.listKbLabelCategory(kbLabelCategoryDto);
        return RespBody.data(list);
    }

    /**
     * 标签分类表保存
     * @param kbLabelCategoryDto 标签分类表实体类
     * @return
     */
    @CastleLog(operLocation="标签分类表",operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签分类表保存")
    @PostMapping("/knowledge/kbLabelCategory/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbLabelCategory:save")
    public RespBody<String> saveKbLabelCategory(@RequestBody Set<KbLabelCategoryDto> kbLabelCategoryDto){
        if(kbLabelCategoryDto == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<KbLabelCategoryEntity> kbLabelCategoryEntities = ConvertUtil.transformObjList(kbLabelCategoryDto, KbLabelCategoryEntity.class);
        ArrayList<String> list = new ArrayList<>();
        //
        for (KbLabelCategoryEntity kbLabelCategoryEntity : kbLabelCategoryEntities) {
            list.add(kbLabelCategoryEntity.getName());
        }
        List<KbLabelCategoryEntity> exiteName = kbLabelCategoryService.isExiteName(list);
        HashMap<String, KbLabelCategoryEntity> map = new HashMap<>();
        for (KbLabelCategoryEntity labelCategoryEntity : exiteName) {
            map.put(labelCategoryEntity.getName(),labelCategoryEntity);
        }
        List<KbLabelCategoryEntity> kbLabelCategoryEntities1 = new ArrayList<>();
        List<KbLabelCategoryEntity> kbLabelCategoryEntities2 = new ArrayList<>();
        StringBuilder res = new StringBuilder();
        res.append("分类【");
        for (KbLabelCategoryEntity kbLabelCategoryEntity : kbLabelCategoryEntities) {
            if (!map.containsKey(kbLabelCategoryEntity.getName())){
                kbLabelCategoryEntity.setIsDeleted(2);
                kbLabelCategoryEntity.setStatus(1);
                kbLabelCategoryEntities1.add(kbLabelCategoryEntity);
            }else {
                if(map.get(kbLabelCategoryEntity.getName()).getIsDeleted() == 1){
                    KbLabelCategoryEntity labelCategoryEntity = map.get(kbLabelCategoryEntity.getName());
                    labelCategoryEntity.setIsDeleted(2);
                    kbLabelCategoryEntities2.add(labelCategoryEntity);
                }else {
                    res.append(kbLabelCategoryEntity.getName() + " ");
                }
            }

        }
        res.append("】已存在");
        if(!"分类【】已存在".equals(res.toString())) {
            return RespBody.fail(res.toString());
        }
        if (kbLabelCategoryEntities2.size() > 0){
            kbLabelCategoryService.updateBatchById(kbLabelCategoryEntities2);
        }
        if(kbLabelCategoryService.saveBatch(kbLabelCategoryEntities1)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分类表编辑
     * @param kbLabelCategoryDto 标签分类表实体类
     * @return
     */
    @CastleLog(operLocation="标签分类表",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签分类表编辑")
    @PostMapping("/knowledge/kbLabelCategory/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbLabelCategory:edit")
    public RespBody<String> updateKbLabelCategory(@RequestBody KbLabelCategoryDto kbLabelCategoryDto){
        if(kbLabelCategoryDto == null || kbLabelCategoryDto.getId() == null || kbLabelCategoryDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbLabelCategoryEntity kbLabelCategoryEntity = ConvertUtil.transformObj(kbLabelCategoryDto,KbLabelCategoryEntity.class);
        if(kbLabelCategoryService.updateById(kbLabelCategoryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分类表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="标签分类表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签分类表置为失效")
    @PostMapping("/knowledge/kbLabelCategory/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbLabelCategory:delete")
    public RespBody<String> deleteKbLabelCategory(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //查询分类下是否拥有标签
        List<KbModelLabelEntity> labels = kbLabelCategoryService.findLabelByCtId(id);
        if (labels != null && labels.size() != 0){
            return RespBody.fail("该分类下还有标签，请删除后再操作。");
        }else {
            KbLabelCategoryEntity byId = kbLabelCategoryService.getById(id);
            byId.setIsDeleted(1);
            if(kbLabelCategoryService.updateById(byId)) {
                return RespBody.data("保存成功");
            }else{
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        }

    }


    /**
     * 标签分类表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="标签分类表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签分类表批量删除")
    @PostMapping("/knowledge/kbLabelCategory/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbLabelCategory:deleteBatch")
    public RespBody<String> deleteKbLabelCategoryBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<KbModelLabelDto> labelByCtIds = kbLabelCategoryService.findLabelByCtIds(ids);
        Map<String, KbModelLabelDto> map = new HashMap<>();
        for (KbModelLabelDto labelByCtId : labelByCtIds) {
            map.put(labelByCtId.getCtName(), labelByCtId);
        }
        List<KbLabelCategoryEntity> byIds = kbLabelCategoryService.findByIds(ids);
        StringBuilder res = new StringBuilder();
        res.append("分类【");
        ArrayList<Long> longs = new ArrayList<>();
        for (KbLabelCategoryEntity byId : byIds) {
            if (map.containsKey(byId.getName())) {
                res.append(byId.getName() + " ");
                longs.add(byId.getId());
            }
        }
        res.append("】下存在标签，请删除后操作");
        ArrayList<Long> longs1 = new ArrayList<>();
        for (Long id : ids) {
            if (!(longs.contains(id))) {
                longs1.add(id);
            }
        }
        if (longs1.size() > 0) {
            List<KbLabelCategoryEntity> byIds1 = kbLabelCategoryService.findByIds(longs1);
            for (KbLabelCategoryEntity labelCategoryEntity : byIds1) {
                labelCategoryEntity.setIsDeleted(1);
            }
            String str = "分类【】下存在标签，请删除后操作";
            if (str.equals(res.toString())) {
                if (kbLabelCategoryService.updateBatchById(byIds1)) {
                    return RespBody.data("保存成功");
                } else {
                    return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
                }
            }else {
                if (kbLabelCategoryService.updateBatchById(byIds1)) {
                    return RespBody.fail(res.toString());
                } else {
                    return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
                }
            }

        }else {
            return RespBody.fail(res.toString());
        }
    }

    /**
     * 标签分类表详情
     * @param id 标签分类表id
     * @return
     */
    @CastleLog(operLocation="标签分类表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分类表详情")
    @GetMapping("/knowledge/kbLabelCategory/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbLabelCategory:info")
    public RespBody<KbLabelCategoryDto> infoKbLabelCategory(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbLabelCategoryEntity kbLabelCategoryEntity = kbLabelCategoryService.getById(id);
        KbLabelCategoryDto kbLabelCategoryDto = ConvertUtil.transformObj(kbLabelCategoryEntity,KbLabelCategoryDto.class);

        return RespBody.data(kbLabelCategoryDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="标签分类表",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/knowledge/kbLabelCategory/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<KbLabelCategoryDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<KbLabelCategoryDto> list = kbLabelCategoryService.listKbLabelCategory(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}




}
