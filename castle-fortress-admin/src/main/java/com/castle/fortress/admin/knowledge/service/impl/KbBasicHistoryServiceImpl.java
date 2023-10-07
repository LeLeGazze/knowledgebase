package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.dto.KbPropertyDesignDto;
import com.castle.fortress.admin.knowledge.entity.*;
import com.castle.fortress.admin.knowledge.mapper.*;
import com.castle.fortress.admin.knowledge.service.*;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.dto.KbBasicHistoryDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.stream.Collectors;

/**
 * 知识基本表历史表 服务实现类
 *
 * @author
 * @since 2023-07-03
 */
@Service
public class KbBasicHistoryServiceImpl extends ServiceImpl<KbBasicHistoryMapper, KbBasicHistoryEntity> implements KbBasicHistoryService {
    @Autowired
    private KbColConfigService kbColConfigService;
    @Autowired
    private KbModelDataService kbModelDataService;
    @Autowired
    private KbVideVersionService kbVideVersionService;

    @Autowired
    private KbVideVersionMapper kbVideVersionMapper;
    @Autowired
    private KbCommentMapper commentMapper; // 评论
    @Autowired
    private KbBasicUserMapper userMapper;
    @Autowired
    private KbBasicLabelService kbBasicLabelService;
    /**
     * 获取查询条件
     *
     * @param kbBasicHistoryDto
     * @return
     */
    public QueryWrapper<KbBasicHistoryEntity> getWrapper(KbBasicHistoryDto kbBasicHistoryDto) {
        QueryWrapper<KbBasicHistoryEntity> wrapper = new QueryWrapper();
        if (kbBasicHistoryDto != null) {
            KbBasicHistoryEntity kbBasicHistoryEntity = ConvertUtil.transformObj(kbBasicHistoryDto, KbBasicHistoryEntity.class);
            wrapper.like(kbBasicHistoryEntity.getId() != null, "id", kbBasicHistoryEntity.getId());
            wrapper.like(kbBasicHistoryEntity.getParentId() != null, "parent_id", kbBasicHistoryEntity.getParentId());
            wrapper.like(kbBasicHistoryEntity.getBId() != null, "b_id", kbBasicHistoryEntity.getBId());
            wrapper.like(kbBasicHistoryEntity.getWhId() != null, "wh_id", kbBasicHistoryEntity.getWhId());
            wrapper.like(StrUtil.isNotEmpty(kbBasicHistoryEntity.getTitle()), "title", kbBasicHistoryEntity.getTitle());
            wrapper.like(kbBasicHistoryEntity.getAuth() != null, "auth", kbBasicHistoryEntity.getAuth());
            wrapper.like(kbBasicHistoryEntity.getDeptId() != null, "dept_id", kbBasicHistoryEntity.getDeptId());
            wrapper.like(kbBasicHistoryEntity.getPubTime() != null, "pub_time", kbBasicHistoryEntity.getPubTime());
            wrapper.like(kbBasicHistoryEntity.getCategoryId() != null, "category_id", kbBasicHistoryEntity.getCategoryId());
            wrapper.like(kbBasicHistoryEntity.getModelId() != null, "model_id", kbBasicHistoryEntity.getModelId());
            wrapper.like(kbBasicHistoryEntity.getExpTime() != null, "exp_time", kbBasicHistoryEntity.getExpTime());
            wrapper.like(StrUtil.isNotEmpty(kbBasicHistoryEntity.getAttachment()), "attachment", kbBasicHistoryEntity.getAttachment());
            wrapper.like(StrUtil.isNotEmpty(kbBasicHistoryEntity.getLabel()), "label", kbBasicHistoryEntity.getLabel());
            wrapper.like(StrUtil.isNotEmpty(kbBasicHistoryEntity.getModelCode()), "model_code", kbBasicHistoryEntity.getModelCode());
            wrapper.like(StrUtil.isNotEmpty(kbBasicHistoryEntity.getRemark()), "remark", kbBasicHistoryEntity.getRemark());
            wrapper.like(kbBasicHistoryEntity.getSort() != null, "sort", kbBasicHistoryEntity.getSort());
            wrapper.like(StrUtil.isNotEmpty(kbBasicHistoryEntity.getWordCloud()), "word_cloud", kbBasicHistoryEntity.getWordCloud());
            wrapper.like(kbBasicHistoryEntity.getStatus() != null, "status", kbBasicHistoryEntity.getStatus());
            wrapper.like(kbBasicHistoryEntity.getCreateTime() != null, "create_time", kbBasicHistoryEntity.getCreateTime());
            wrapper.like(kbBasicHistoryEntity.getCreateUser() != null, "create_user", kbBasicHistoryEntity.getCreateUser());
            wrapper.like(kbBasicHistoryEntity.getIsDeleted() != null, "is_deleted", kbBasicHistoryEntity.getIsDeleted());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<KbBasicHistoryDto> pageKbBasicHistory(Page<KbBasicHistoryDto> page, KbBasicHistoryDto kbBasicHistoryDto) {
        QueryWrapper<KbBasicHistoryEntity> wrapper = getWrapper(kbBasicHistoryDto);
        Page<KbBasicHistoryEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbBasicHistoryEntity> kbBasicHistoryPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbBasicHistoryDto> pageDto = new Page(kbBasicHistoryPage.getCurrent(), kbBasicHistoryPage.getSize(), kbBasicHistoryPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbBasicHistoryPage.getRecords(), KbBasicHistoryDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbBasicHistoryDto> pageKbBasicHistoryExtends(Page<KbBasicHistoryDto> page, KbBasicHistoryDto kbBasicHistoryDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        KbBasicHistoryEntity kbBasicHistoryEntity = ConvertUtil.transformObj(kbBasicHistoryDto, KbBasicHistoryEntity.class);
        List<KbBasicHistoryEntity> kbBasicHistoryList = baseMapper.extendsList(pageMap, kbBasicHistoryEntity);
        Long total = baseMapper.extendsCount(kbBasicHistoryEntity);
        Page<KbBasicHistoryDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbBasicHistoryList, KbBasicHistoryDto.class));
        return pageDto;
    }

    @Override
    public KbBasicHistoryDto getByIdExtends(Long id) {
        KbBasicHistoryEntity kbBasicHistoryEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbBasicHistoryEntity, KbBasicHistoryDto.class);
    }

    @Override
    public List<KbBasicHistoryDto> listKbBasicHistory(KbBasicHistoryDto kbBasicHistoryDto) {
        QueryWrapper<KbBasicHistoryEntity> wrapper = getWrapper(kbBasicHistoryDto);
        List<KbBasicHistoryEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbBasicHistoryDto.class);
    }

    @Override
    @Async
    public void addARecord(Long userId, KbModelEntity model, KbBasicEntity kbBasic, Map<String, Object> extendDataMap, KbModelAcceptanceDto formDataDto) {
        //构建查询对象
        KbPropertyDesignDto kbPropertyDesignDto = new KbPropertyDesignDto();
        kbPropertyDesignDto.setModelId(model.getId());
        kbPropertyDesignDto.setIsDeleted(2);
        kbPropertyDesignDto.setStatus(1);
        // 查询所有的列
        List<KbPropertyDesignDto> kbPropertyDesignDtos = kbColConfigService.listKbColConfig(kbPropertyDesignDto);
        //筛选出只有附件的类
        List<KbPropertyDesignDto> attachmentDataList = kbPropertyDesignDtos.stream().filter(item -> item.getFormType() == 4).collect(Collectors.toList());
        // 定义标记
        boolean mark = false;
        // 校验基础字段
        List<JSONObject> foundationNewJsonList = JSONObject.parseArray(String.valueOf(kbBasic.getAttachment()), JSONObject.class);
        List<JSONObject> foundationOriginJsonList = JSONObject.parseArray(JSONObject.toJSONString(formDataDto.getAttachments()), JSONObject.class);
        mark = isFileUpdate(foundationOriginJsonList, foundationNewJsonList);
        if (mark) {
            basicInstall(userId, model, kbBasic, extendDataMap, formDataDto);
            return;
        }
        // 校验扩展字段
        if (attachmentDataList.size() > 0) {
            // 获取表单提交的所有的列
            Map<String, Object> cols = formDataDto.getCols();
            for (KbPropertyDesignDto propertyDesignDto : attachmentDataList) {
                Object newData = cols.get(propertyDesignDto.getPropName());
                Object originList = extendDataMap.get(propertyDesignDto.getPropName());
                List<JSONObject> newJsonList = JSONObject.parseArray(JSONObject.toJSONString(newData), JSONObject.class);
                List<JSONObject> originJsonList = JSONObject.parseArray(String.valueOf(originList), JSONObject.class);
                boolean fileUpdate = isFileUpdate(originJsonList, newJsonList);
                if (fileUpdate) {
                    mark = true;
                    break;
                }
            }
        }
        if (mark) {
            basicInstall(userId, model, kbBasic, extendDataMap, formDataDto);
        }
    }

    private void basicInstall(Long userId, KbModelEntity model, KbBasicEntity kbBasic, Map<String, Object> extendDataMap, KbModelAcceptanceDto formDataDto) {
        KbBasicHistoryEntity kbBasicHistoryEntity = ConvertUtil.transformObj(kbBasic, KbBasicHistoryEntity.class);
        kbBasicHistoryEntity.setCreateUser(userId);

        List<HashMap<String, Integer>> mapList = userMapper.findByBid(kbBasic.getId());
        if (mapList != null) {
            for (HashMap<String, Integer> str : mapList) {
                if (str.get("type") == 1) {
                    kbBasicHistoryEntity.setReadCount(Integer.parseInt(str.getOrDefault("count", 0) + ""));
                }
                if (str.get("type") == 3) {
                    kbBasicHistoryEntity.setDownloadCount(Integer.parseInt(str.getOrDefault("count", 0) + ""));
                }
            }
        }
        kbBasicHistoryEntity.setCommentsCount(commentMapper.findByBid(kbBasic.getId()));
        List<String> labelName = kbBasicLabelService.listBybId(kbBasic.getId()).stream().map(KbModelLabelEntity::getName).collect(Collectors.toList());
        kbBasicHistoryEntity.setLabel(JSONObject.toJSONString(labelName));
        isInsertData(model, kbBasicHistoryEntity, extendDataMap);
        // 异步修改png转换表
        kbVideVersionService.tmpUpdate(kbBasicHistoryEntity.getId(), formDataDto);
    }

    @Override
    public KbModelTransmitDto findAllByBasic(Long id) {
        KbModelTransmitDto allByBasic = baseMapper.findAllByBasic(id);
        kbColConfigService.findHistoryColDate(allByBasic, id);
        allByBasic.setAttachments(JSONObject.parseArray(allByBasic.getAttachmentTmp().toString()));
        allByBasic.setKbVideVersionDtos(kbVideVersionMapper.findByUid(id));
        KbPropertyDesignDto kbPropertyDesignDto = new KbPropertyDesignDto();
        kbPropertyDesignDto.setModelId(allByBasic.getModelId());
        kbPropertyDesignDto.setIsDeleted(2);
        kbPropertyDesignDto.setStatus(1);
        List<KbPropertyDesignDto> kbPropertyDesignDtos = kbColConfigService.listKbColConfig(kbPropertyDesignDto);
        List<KbPropertyDesignEntity> list = ConvertUtil.transformObjList(kbPropertyDesignDtos, KbPropertyDesignEntity.class);
        allByBasic.setPropCols(list);
        return allByBasic;
    }

    @Override
    public int deleteByBId(Long id) {
        return baseMapper.deleteByBId(id);
    }

    public boolean isFileUpdate(List<JSONObject> originJsonList, List<JSONObject> newJsonList) {
        if (originJsonList == null || newJsonList == null || originJsonList.size() != newJsonList.size()) {
            return true;
        }
        // 将json 转为list
        List<String> originList = originJsonList.stream().map(item -> String.valueOf(item.get("md5"))).collect(Collectors.toList());
        List<String> newList = newJsonList.stream().map(item -> String.valueOf(item.get("md5"))).collect(Collectors.toList());
        for (String item : newList) {
            if (!originList.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public void isInsertData(KbModelEntity model, KbBasicHistoryEntity kbBasicHistoryEntity, Map<String, Object> extendDataMap) {
        //创建一个版本
        double version = 1.0;
        QueryWrapper<KbBasicHistoryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b_id", kbBasicHistoryEntity.getId());
        queryWrapper.isNull("parent_id");
        KbBasicHistoryEntity before = baseMapper.selectOne(queryWrapper);
        kbBasicHistoryEntity.setBId(kbBasicHistoryEntity.getId());
        kbBasicHistoryEntity.setId(null);
        kbBasicHistoryEntity.setCreateTime(null);
        if (before != null) {
            version += Double.parseDouble(before.getVersion().replace("V", ""));
        }
        kbBasicHistoryEntity.setVersion("V" + version);
        baseMapper.insert(kbBasicHistoryEntity);
        Long id = kbBasicHistoryEntity.getId();
        if (before != null) {
            before.setParentId(id);
            baseMapper.updateById(before);
        }
        extendDataMap.put("data_id", kbBasicHistoryEntity.getId());
        extendDataMap.remove("id");
        // 新增列
        kbModelDataService.saveHistoryData(model, extendDataMap);
    }

    public static void main(String[] args) {
        String v1 = "V10.0";
        String result = v1.replace("v", "");  // 提取字符串中的数字部分
        Double number = Double.parseDouble(result);  // 将提取到的数字转换为整数
        System.out.println(number);
    }
}

