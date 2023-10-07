package com.castle.fortress.admin.knowledge.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.mapper.KbBasicLabelMapper;
import com.castle.fortress.admin.knowledge.service.KbBaseLabelTaskService;
import com.castle.fortress.admin.knowledge.service.KbCategoryLabelService;
import com.castle.fortress.admin.knowledge.service.KbLabelCategoryService;
import com.castle.fortress.admin.knowledge.service.KbModelLabelService;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;

import javax.servlet.http.HttpServletResponse;

import com.castle.fortress.common.entity.DynamicExcelEntity;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 标签管理表 控制器
 *
 * @author
 * @since 2023-04-26
 */
@Api(tags = "标签管理表管理控制器")
@Controller
public class KbModelLabelController {
    @Autowired
    private KbModelLabelService kbModelLabelService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private KbBaseLabelTaskService kbBaseLabelTaskService;
    @Autowired
    private KbCategoryLabelService kbCategoryLabelService;
    @Autowired
    private KbLabelCategoryService kbLabelCategoryService;

    /**
     * 标签管理表的分页展示
     *
     * @param kbModelLabelDto 标签管理表实体类
     * @param current         当前页
     * @param size            每页记录数
     * @return
     */
    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签管理表分页展示")
    @GetMapping("/knowledge/kbModelLabel/page")
    @ResponseBody
    public RespBody<IPage<KbModelLabelDto>> pageKbModelLabel(KbModelLabelDto kbModelLabelDto,
                                                             @RequestParam(required = false) Integer current,
                                                             @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<KbModelLabelDto> page = new Page(pageIndex, pageSize);
        IPage<KbModelLabelDto> pages = kbModelLabelService.pageKbModelLabel(page, kbModelLabelDto);

        return RespBody.data(pages);
    }

    /**
     * 标签管理表的列表展示
     *
     * @param kbModelLabelDto 标签管理表实体类
     * @return
     */
    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签管理表列表展示")
    @GetMapping("/knowledge/kbModelLabel/list")
    @ResponseBody
    public RespBody<List<KbModelLabelDto>> listKbModelLabel(KbModelLabelDto kbModelLabelDto) {
        List<KbModelLabelDto> list = kbModelLabelService.listKbModelLabel(kbModelLabelDto);
        return RespBody.data(list);
    }

    /**
     * 标签管理表保存
     *
     * @param kbModelLabelDtos 标签管理表实体类
     * @return
     */
    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签管理表保存")
    @PostMapping("/knowledge/kbModelLabel/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelLabel:save")
    public RespBody<String> saveKbModelLabel(@RequestBody Set<KbModelLabelDto> kbModelLabelDtos) {
        return kbModelLabelService.saveLabels(new ArrayList<>(kbModelLabelDtos));
    }

    /**
     * 标签管理表编辑
     *
     * @param kbModelLabelDto 标签管理表实体类
     * @return
     */
    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签管理表编辑")
    @PostMapping("/knowledge/kbModelLabel/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelLabel:edit")
    public RespBody<String> updateKbModelLabel(@RequestBody KbModelLabelDto kbModelLabelDto) {
        if (kbModelLabelDto == null || kbModelLabelDto.getId() == null || kbModelLabelDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelLabelEntity kbModelLabelEntity = ConvertUtil.transformObj(kbModelLabelDto, KbModelLabelEntity.class);
        if (kbModelLabelService.updateById(kbModelLabelEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签管理表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签管理表删除")
    @PostMapping("/knowledge/kbModelLabel/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelLabel:delete")
    public RespBody<String> deleteKbModelLabel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbModelLabelService.updateByLabel(id)) {
            KbBaseLabelTaskEntity kbBaseLabelTaskEntity = new KbBaseLabelTaskEntity();
            kbBaseLabelTaskEntity.setLId(id);
            kbBaseLabelTaskEntity.setStatus(1);
            kbBaseLabelTaskService.save(kbBaseLabelTaskEntity);
            //删除与标签分类之间的联系
            kbCategoryLabelService.removeByLabelId(id);
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 标签管理表批量删除
     *
     * @param ids
     * @return
     */
    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签管理表批量删除")
    @PostMapping("/knowledge/kbModelLabel/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelLabel:deleteBatch")
    public RespBody<String> deleteKbModelLabelBatch(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() < 1) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (kbModelLabelService.removeLabels(ids)) {
            ArrayList<KbBaseLabelTaskEntity> kbBaseLabelTaskEntities = new ArrayList<>();
            for (Long id : ids) {
                KbBaseLabelTaskEntity kbBaseLabelTaskEntity = new KbBaseLabelTaskEntity();
                kbBaseLabelTaskEntity.setLId(id);
                kbBaseLabelTaskEntity.setStatus(1);
                kbBaseLabelTaskEntities.add(kbBaseLabelTaskEntity);
            }
            kbBaseLabelTaskService.saveBatch(kbBaseLabelTaskEntities);
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签管理表详情
     *
     * @param id 标签管理表id
     * @return
     */
    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签管理表详情")
    @GetMapping("/knowledge/kbModelLabel/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelLabel:info")
    public RespBody<KbModelLabelDto> infoKbModelLabel(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelLabelEntity kbModelLabelEntity = kbModelLabelService.getById(id);
        KbModelLabelDto kbModelLabelDto = ConvertUtil.transformObj(kbModelLabelEntity, KbModelLabelDto.class);

        return RespBody.data(kbModelLabelDto);
    }

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     *
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbModelLabel/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<KbModelLabelDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<KbModelLabelDto> list = kbModelLabelService.listKbModelLabel(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        /**
         * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
         */
        ExcelUtils.exportDynamic(response, dynamicExcelEntity.getFileName() + ".xlsx", null, list, dynamicExcelEntity.getHeaderList(), dataList);
    }

    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.EXPORT)
    @PostMapping("/knowledge/kbModelLabel/keyWord")
    @ApiOperation("关键字自动生成")
    @ResponseBody
    public RespBody getKeyWord(@RequestBody List<String> md5List) throws Exception {

        if (md5List == null || md5List.size() == 0) {
            throw new BizException("请先上传附件在进行提取");
        }
        HashMap<String, Object> resMap = new HashMap<>();

        for (String item : md5List) {
            Object keyword = redisUtils.get("keyword:" + item);
            ArrayList key = (ArrayList) keyword;
            if (keyword == null ||key.size()==0 ) {
                return RespBody.fail("未提取到关键字");
            } else {
                resMap.put(item, keyword);
            }
        }
        return RespBody.data(resMap);
    }


    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("热词标签分页展示")
    @GetMapping("/knowledge/kbModelLabel/listHotWord")
    @ResponseBody
    public RespBody<List<KbModelLabelDto>> listHotWord(KbModelLabelDto kbModelLabelDto) {
        List<KbModelLabelDto> kbModelLabelDtos = kbModelLabelService.listHotWord();
        return RespBody.data(kbModelLabelDtos);
    }


    @CastleLog(operLocation = "标签管理表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签管理表置为失效或生效")
    @PostMapping("/knowledge/kbModelLabel/editStatus")
    @ResponseBody
    @RequiresPermissions("knowledge:kbModelLabel:edit")
    public RespBody<String> updateLabelStatus(@RequestBody List<KbModelLabelDto> kbModelLabelDtos, @RequestParam(required = false) Integer type) {
        if (kbModelLabelDtos == null || kbModelLabelDtos.size() == 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (type != null) {
            KbModelLabelDto kbModelLabelDto = kbModelLabelDtos.get(0);
            KbModelLabelEntity kbModelLabelEntity = ConvertUtil.transformObj(kbModelLabelDto, KbModelLabelEntity.class);
            if (kbModelLabelEntity.getIsDeleted() == 1) {
                KbLabelCategoryEntity labelCategory = kbCategoryLabelService.findCategoryByLabel(kbModelLabelEntity.getId());
                if (labelCategory != null && labelCategory.getIsDeleted() == 1){
                    labelCategory.setIsDeleted(2);
                    kbLabelCategoryService.updateById(labelCategory);
                }else if(labelCategory == null){
                    throw new BizException(GlobalRespCode.OPERATE_ERROR);
                }
            }
            if (kbModelLabelService.updateLabel(kbModelLabelEntity)) {
                return RespBody.data("保存成功");
            } else {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        }
        List<KbModelLabelEntity> kbModelLabelEntitys = ConvertUtil.transformObjList(kbModelLabelDtos, KbModelLabelEntity.class);
        for (KbModelLabelEntity kbModelLabelEntity : kbModelLabelEntitys) {
            kbModelLabelEntity.setStatus(2);
        }
        if (kbModelLabelService.updateBatch(kbModelLabelEntitys)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }
}
