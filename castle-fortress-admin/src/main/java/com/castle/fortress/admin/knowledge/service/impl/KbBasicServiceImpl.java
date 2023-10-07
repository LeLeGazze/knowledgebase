package com.castle.fortress.admin.knowledge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.dto.*;
import com.castle.fortress.admin.knowledge.entity.*;
import com.castle.fortress.admin.knowledge.mapper.*;
import com.castle.fortress.admin.knowledge.service.*;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * kb基础字段服务实现类
 *
 * @author sunhr
 * @create 2023/4/19 15:47
 */
@Slf4j
@Service
public class KbBasicServiceImpl extends ServiceImpl<KbBasicMapper, KbBasicEntity> implements KbBasicService {

    @Autowired
    private KbBasicMapper kbBasicMapper;
    @Autowired
    private KbBasicLabelService kbBasicLabelService;
    @Autowired
    private KbModelDataService kbModelDataService;
    @Autowired
    private KbModelService kbModelService;
    @Autowired
    private KbBasicUserService kbBasicUserService;

    @Autowired
    private KbModelLabelService kbModelLabelService;
    @Autowired
    private EsSearchService esSearchService;

    @Autowired
    private KbColConfigService kbColConfigService;

    @Autowired
    private KbVideVersionService kbVideVersionService;
    @Autowired
    private KbBasicUserMapper userMapper;
    // PDF转成png表
    @Autowired
    private KbVideVersionMapper kbVideVersionMapper;
    //模型
    @Autowired
    private KbModelMapper kbModelMapper;

    @Autowired
    private KbBasicLabelMapper kbBasicLabelMapper; //标签中间表

    @Autowired
    private KbCommentMapper commentMapper; // 评论
    @Autowired
    private KbCollectService kbCollectService;
    @Autowired
    private KbBasicTrashMapper kbBasicTrashMapper;
    @Autowired
    private KbBasicLogMapper kbBasicLogMapper;
    @Autowired
    private KbBasicHistoryService kbBasicHistoryService; // 历史表


    /**
     * 普通用户查询
     *
     * @param page
     * @param kbBasicDto
     * @param uid
     * @param kb_auths
     * @return
     */
    @Override
    public IPage<KbBaseShowDto> pageKbBaseicWarehouseExtends(Page<KbBaseShowDto> page, KbBaseShowDto kbBasicDto, Long uid, List<Integer> kb_auths) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        String extend = kbBasicDto.getExtend();
        List<JSONObject> extendList = null;
        if (extend != null) {
            extendList = JSONObject.parseArray(extend, JSONObject.class);
        }
        List<KbBaseShowDto> kbBasicEntities = baseMapper.extendsList(pageMap, kbBasicDto, uid, kb_auths, extendList);
        Long total = baseMapper.extendsCount(kbBasicDto, uid, kb_auths, extendList);
        Page<KbBaseShowDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbBasicEntities, KbBaseShowDto.class));
        return pageDto;
    }

    /**
     * 管理员查询
     *
     * @param page
     * @param kbBasicDto
     * @return
     */
    @Override
    public IPage<KbBaseShowDto> pageKbBaseicWarehouseExtendsAdmin(Page<KbBaseShowDto> page, KbBaseShowDto kbBasicDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        String extend = kbBasicDto.getExtend();
        List<JSONObject> extendList = null;
        if (extend != null) {
            extendList = JSONObject.parseArray(extend, JSONObject.class);
        }
        List<KbBaseShowDto> kbBasicEntities = baseMapper.extendsListAdmin(pageMap, kbBasicDto, extendList);

        Long total = baseMapper.extendsCountAdmin(kbBasicDto, extendList);
        Page<KbBaseShowDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbBasicEntities, KbBaseShowDto.class));
        return pageDto;
    }


    @Override

    public int deleteByIdsAdmin(List<Long> ids) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        int res = 1;
        for (Long id : ids) {
            int deleteRes = baseMapper.deleteByid(id);
            esSearchService.updateByIdToIsDelete(String.valueOf(id), "1");  //修改状态
            KbBasicTrashEntity kbBasicTrashEntity = new KbBasicTrashEntity();
            kbBasicTrashEntity.setBasicId(id);
            kbBasicTrashEntity.setUserId(userId);
            kbBasicTrashEntity.setType(1);
            kbBasicTrashEntity.setCreateTime(new Date());
            kbBasicTrashMapper.insert(kbBasicTrashEntity);
            if (deleteRes == 0) {
                res = 0;
            }
            log.debug("delete admin 删除结果【{}】", deleteRes);
        }
        return res;
    }

    /**
     * 删除
     */
    @Transactional()
    public int delete(Long id) {
        //准备删除需要的数据
        KbBasicEntity kbBasicEntity = baseMapper.selectById(id);
        Long modelId = kbBasicEntity.getModelId();
        KbModelEntity kbModelEntity = kbModelMapper.selectById(modelId);
        // 删除知识扩展表数
        String tableName = "kb_model_ks_" + kbModelEntity.getCode();
        try {
            int deleteByDId = kbModelDataService.deleteByDId(tableName, id);

            // 删除标签中间表
            int deleteByLabelBid = kbBasicLabelMapper.delectByIds(id);
            //删除浏览、下载、收藏
            int deleteByBid = userMapper.deleteByBid(id);
            // 删除评论表
            int deleteByCommentBid = commentMapper.deleteByBid(id);
            // 删除pdf 转图片表
            int deleteByVideBid = kbVideVersionMapper.deleteByBid(id);
            // 删除缓存数据es表
            int deleteEsById = esSearchService.deleteByid(id);
            //删除收藏表
            int deleteCollect = kbCollectService.deleteByBid(id);
            // 删除知识表数据
            int base = baseMapper.deleteById(id);
            // 删除历史扩展表
            int deleteByDIdHis = kbModelDataService.deleteByBIdHis("kb_model_ks_history_"+kbModelEntity.getCode(), id);
            // 删除历史表
            int deleteHistory = kbBasicHistoryService.deleteByBId(id);

            log.debug("删除模型扩展表【{}】删除标签中间表数量【{}】 删除收藏表数量【{}】 删除评论表数量【{}】 删除PDF转png表【{}】删除es缓存表【{}】删除知识表【{}】", deleteByDId, deleteByLabelBid, deleteByBid, deleteByCommentBid, deleteByVideBid, deleteEsById, base);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String deleteByIds(List<Long> kbWarehouseAuthEntityList, List<Long> ids) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        StringBuffer resStr = new StringBuffer();
        boolean flag = false;
        resStr.append("无权限删除该分类下的内容【");
        // 循环遍历
        for (Long id : ids) {
            // 查询出知识获取分类id校验权限
            KbBasicEntity kbBasicEntity = baseMapper.selectById(id);
            if (kbBasicEntity == null) {
                break;
            }
            if (kbWarehouseAuthEntityList.contains(kbBasicEntity.getCategoryId())) {
                //有权限执行删除
                int deleteRes = baseMapper.deleteByid(id);
                esSearchService.updateByIdToIsDelete(String.valueOf(id), "1"); // 修改es 状态
                log.debug("delete ordinary 删除结果【{}】", deleteRes);
                KbBasicTrashEntity kbBasicTrashEntity = new KbBasicTrashEntity();
                kbBasicTrashEntity.setBasicId(id);
                kbBasicTrashEntity.setUserId(userId);
                kbBasicTrashEntity.setType(1);
                kbBasicTrashEntity.setCreateTime(new Date());
                kbBasicTrashMapper.insert(kbBasicTrashEntity);
            } else {
                flag = true;
                // 无权限反馈给前段
                resStr.append(kbBasicEntity.getTitle()).append("  ");
            }
        }
        resStr.append("】 ");
        return flag ? resStr.toString() : "刪除成功";
    }

    /**
     * 推荐知识分页查询 管理员
     *
     * @param page
     * @return
     */
    @Override
    public List<KbBaseShowDto> randBasicPageAdmin(Page<KbBaseShowDto> page, String swId) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbBaseShowDto> kbBasicEntities = baseMapper.randBasicPageAdmin(pageMap, swId);
        return kbBasicEntities;
    }

    /**
     * 普通用户 查询推荐知识
     *
     * @param page
     * @param uid
     * @param integers
     * @return
     */
    @Override
    public List<KbBaseShowDto> randBasicPage(Page<KbBaseShowDto> page, Long uid, List<Integer> integers, String swId) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbBaseShowDto> kbBasicEntities = baseMapper.randBasicPage(pageMap, uid, integers, swId);
        return kbBasicEntities;
    }

    @Override
    public List<KbBaseShowDto> newBasicListAdmin() {

        return baseMapper.newBasicListAdmin();
    }

    @Override
    public List<KbBaseShowDto> newBasicList(Long uid, List<Integer> integers) {
        return baseMapper.newBasicList(uid, integers);
    }

    @Override
    public List<KbBaseShowDto> recentPreviewBasicListAdmin(Long uid, String swId, Integer size) {
        return baseMapper.recentPreviewBasicListAdmin(uid, swId, size);
    }

    @Override
    public List<KbBaseShowDto> recentPreviewBasicList(Long uid, List<Integer> integers, String swId, Integer size) {
        return baseMapper.recentPreviewBasicList(uid, integers, swId, size);
    }

    @Override
    public IPage<KbModelTransmitDto> findBasicByLike(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page,
                                                     Long userId, List<Integer> integers) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<Long> categoryIds = kbBaseShowDto.getCategoryIds();
        if (categoryIds == null || categoryIds.size() == 0) {
            kbBaseShowDto.setCategoryIds(null);
        }
        if (kbBaseShowDto.getSwIds() == null || kbBaseShowDto.getSwIds().size() == 0) {
            kbBaseShowDto.setSwIds(null);
        }
        if (kbBaseShowDto.getFromTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(kbBaseShowDto.getFromTime());
                String format = simpleDateFormat.format(parse);
                kbBaseShowDto.setFromTime(format);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        if (kbBaseShowDto.getType() == 1) {
            List<KbModelTransmitDto> basic = kbBasicMapper.findBasicByLike(kbBaseShowDto, pageMap, userId, integers);
            Integer total = kbBasicMapper.findBasicByLikeCount(kbBaseShowDto, userId, integers);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(basic);
            return pageDto;
        } else {
            List<KbModelTransmitDto> basic = kbBasicMapper.findVideoByLike(kbBaseShowDto, pageMap, userId, integers);
            Integer total = kbBasicMapper.findVideoByLikeCount(kbBaseShowDto, userId, integers);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(basic);
            return pageDto;
        }
    }

    @Override
    public int findByCategoryId(Long id) {
        List<Map<String, Integer>> map = baseMapper.selectByIdIsDelete(id);
        if (map.size() == 1) {
            if (map.get(0).get("is_deleted") == 1) {
                return -1;
            }
        }
        return map.size();
    }

    @Override
    public List<SysUser> findAllAuth() {
        return kbBasicMapper.findAllAuth();
    }

    @Override
    public List<SysUser> findBaseAuth(List<Integer> asList, Long uid) {
        return baseMapper.findBaseAuth(asList, uid);
    }

    @Override
    public IPage<KbModelTransmitDto> findBasicByLikeAdmin(Page<KbModelTransmitDto> page, KbBaseShowDto kbBaseShowDto) {
        List<Long> categoryIds = kbBaseShowDto.getCategoryIds();
        if (categoryIds == null || categoryIds.size() == 0) {
            kbBaseShowDto.setCategoryIds(null);
        }
        if (kbBaseShowDto.getSwIds() == null || kbBaseShowDto.getSwIds().size() == 0) {
            kbBaseShowDto.setSwIds(null);
        }
        if (kbBaseShowDto.getFromTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(kbBaseShowDto.getFromTime());
                String format = simpleDateFormat.format(parse);
                kbBaseShowDto.setFromTime(format);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        if (kbBaseShowDto.getType() == 1) {
            Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
            List<KbModelTransmitDto> basic = kbBasicMapper.findBasicByLikeAdmin(kbBaseShowDto, pageMap);
            Integer total = kbBasicMapper.findBasicByLikeAdminCount(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(basic);
            return pageDto;
        } else {
            Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
            List<KbModelTransmitDto> basic = kbBasicMapper.findBasicByVideoAdmin(kbBaseShowDto, pageMap);
            Integer total = kbBasicMapper.findBasicByVideoAdminCount(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(basic);
            return pageDto;
        }
    }

    @Override
    public IPage<KbModelTransmitDto> findBasicByUploud(Page<KbModelTransmitDto> page, KbBaseShowDto kbBaseShowDto, Long userId, List<Integer> integers) {
        List<Long> categoryIds = kbBaseShowDto.getCategoryIds();
        if (categoryIds == null || categoryIds.size() == 0) {
            kbBaseShowDto.setCategoryIds(null);
        }
        if (kbBaseShowDto.getSwIds() == null || kbBaseShowDto.getSwIds().size() == 0) {
            kbBaseShowDto.setSwIds(null);
        }
        if (kbBaseShowDto.getFromTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(kbBaseShowDto.getFromTime());
                String format = simpleDateFormat.format(parse);
                kbBaseShowDto.setFromTime(format);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        if (kbBaseShowDto.getType() == 1) {
            Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
            List<KbModelTransmitDto> basicByUploud = baseMapper.findBasicByUploud(userId, integers, kbBaseShowDto, pageMap);
            Integer total = baseMapper.findBasicByUploudCount(userId, integers, kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(basicByUploud);
            return pageDto;
        } else {
            Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
            List<KbModelTransmitDto> basicByUploud = baseMapper.findBasicByUploudVideo(userId, integers, kbBaseShowDto, pageMap);
            Integer total = baseMapper.findBasicByUploudVideoCount(userId, integers, kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(basicByUploud);
            return pageDto;
        }

    }

    @Override
    public List<KbBasicEntity> selectByExpireBasic() {
        return baseMapper.selectByExpireBasic();
    }

    @Override
    public List<Map<String, Object>> getTheLatest12QuantitiesAdmin() {
        return baseMapper.getTheLatest12QuantitiesAdmin();
    }

    @Override
    public List<Map<String, Object>> getTheLatest12Quantities(List<Integer> asList, Long uid) {
        List<Long> authList = baseMapper.findByAuth(uid, asList);
        if (authList == null || authList.size() == 0) {
            return new ArrayList<Map<String, Object>>();
        }
        return baseMapper.getTheLatest12Quantities(uid, authList);
    }

    @Override
    public List<Map<String, Object>> getKnowledgeBaseTopNAdmin(int num) {
        return baseMapper.getKnowledgeBaseTopNAdmin(num);
    }

    @Override
    public List<Map<String, Object>> getKnowledgeBaseTopN(List<Integer> asList, Long uid, int num) {
        List<Long> authList = baseMapper.findByAuth(uid, asList);
        if (authList == null || authList.size() < 1) {
            return new ArrayList<>();
        }
        return baseMapper.getKnowledgeBaseTopN(authList, uid, num);
    }

    @Override
    public KbModelTransmitDto findAllByBasic(Long id) {
        KbModelTransmitDto allByBasic = kbBasicMapper.findAllByBasic(id);
        KbModelTransmitDto colDate = kbColConfigService.findColDate(allByBasic, id);
        KbPropertyDesignDto kbPropertyDesignDto = new KbPropertyDesignDto();
        kbPropertyDesignDto.setModelId(allByBasic.getModelId());
        kbPropertyDesignDto.setIsDeleted(2);
        kbPropertyDesignDto.setStatus(1);
        List<KbPropertyDesignDto> kbPropertyDesignDtos = kbColConfigService.listKbColConfig(kbPropertyDesignDto);
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        List<HashMap<String, Integer>> mapList = userMapper.findByBid(id);
        if (mapList != null) {
            for (HashMap<String, Integer> str : mapList) {
                if (str.get("type") == 1) {
                    colDate.setReadCount(Integer.parseInt(str.getOrDefault("count", 0) + ""));
                }
                if (str.get("type") == 3) {
                    colDate.setDownloadCount(Integer.parseInt(str.getOrDefault("count", 0) + ""));
                }
            }
        }

        colDate.setKbVideVersionDtos(kbVideVersionMapper.findByUid(id));
        colDate.setCommentsCount(commentMapper.findByBid(id));
        colDate.setAttachments(JSONObject.parseArray(allByBasic.getAttachmentTmp().toString()));
        List<KbPropertyDesignEntity> list = ConvertUtil.transformObjList(kbPropertyDesignDtos, KbPropertyDesignEntity.class);
        KbCollectEntity byid = kbCollectService.findByid(userId, allByBasic.getId());
        if (byid == null) {
            colDate.setCollectStatus(2);
        } else {
            colDate.setCollectId(byid.getId());
            colDate.setCollectStatus(1);
        }
        colDate.setPropCols(list);

        return colDate;
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void updateDate(KbModelAcceptanceDto formDataDto) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        KbBasicEntity kbBasicEntity = ConvertUtil.transformObj(formDataDto, KbBasicEntity.class);
        KbBasicEntity kbBasicEntity1 = kbBasicMapper.selectById(kbBasicEntity.getId());
        KbBasicLogEntity kbBasicLogEntity = new KbBasicLogEntity();
        kbBasicLogEntity.setOldCategory(kbBasicEntity1.getCategoryId());
        kbBasicLogEntity.setNewCategory(kbBasicEntity.getCategoryId());
        kbBasicEntity.setAttachment(JSONObject.toJSONString(formDataDto.getAttachments()));

        //更新基础
        if (updateById(kbBasicEntity)) {
            KbBasicDto kbBasicDto = new KbBasicDto();
            kbBasicDto.setAuth(kbBasicEntity.getAuth());
            kbBasicDto.setOldAuth(kbBasicEntity1.getAuth());
            if (!kbBasicDto.getAuth().equals(kbBasicDto.getOldAuth())) {
                kbBasicUserService.removeBasicUser(kbBasicDto.getAuth(), kbBasicEntity.getId());
            }
        }
        //知识分类变更加入日志
        if (!kbBasicLogEntity.getOldCategory().equals(kbBasicLogEntity.getNewCategory())) {
            List<KbBasicLogEntity> logEntity = kbBasicLogMapper.selectByBasic(formDataDto.getId());
            List<Long> ids = new ArrayList<Long>();
            if (logEntity != null && logEntity.size() != 0) {
                for (KbBasicLogEntity basicLogEntity : logEntity) {
                    ids.add(basicLogEntity.getId());
                }
                if (!kbBasicLogMapper.deleteByIds(ids)) {
                    throw new BizException(GlobalRespCode.OPERATE_ERROR);
                }
            }
            kbBasicLogEntity.setCreateUser(userId);
            kbBasicLogEntity.setBasicId(formDataDto.getId());
            kbBasicLogEntity.setType(1);
            kbBasicLogMapper.insert(kbBasicLogEntity);
        }

        //检查标签是否拥有 没有添加
        formDataDto.setId(kbBasicEntity.getId());
        if (formDataDto.getLabels() != null && formDataDto.getLabels().size() != 0) {
            kbBasicLabelService.saveById(formDataDto);
        } else if ((formDataDto.getLabels() != null && formDataDto.getLabels().size() == 0)) {
            List<KbModelLabelEntity> kbModelLabelEntities = kbBasicLabelMapper.selectLidByBid(kbBasicEntity.getId());
            if (kbModelLabelEntities != null || kbModelLabelEntities.size() != 0) {
                kbBasicLabelMapper.delectByIds(kbBasicEntity.getId());
            }
        }

        formDataDto.setLabel(formDataDto.getLabels().stream().map(item -> item.getName()).collect(Collectors.toList()));
        //获取拓展字段
        Map<String, Object> cols = formDataDto.getCols();
        cols.put("data_id", kbBasicEntity.getId());
        Long modelId = kbBasicEntity.getModelId();
        // 获取扩展信息
        KbModelEntity modelEntity = kbModelService.getById(modelId);
        Map<String, Object> extendDataMap = kbModelDataService.queryByDataId(modelEntity.getCode(), kbBasicEntity.getId());
        //对拓展字段更新
        if (cols.get("data_id") != null) {
            kbModelDataService.saveData(modelId, cols);
        }
        if (formDataDto.getStatus() == 1) {
            //更新同步es
            esSearchService.asyncUpdateES(formDataDto);
        } else {
            esSearchService.deleteByid(formDataDto.getId());
        }
        //异步保存到知识与用户关联表
        kbBasicUserService.saveKnowUser(formDataDto);
        // 异步添加附件历史表
        kbBasicHistoryService.addARecord(userId, modelEntity, kbBasicEntity1, extendDataMap, formDataDto);
    }

    @Override
    public KbBasicEntity findByKbBasic(KbBasicDto kbBasicDto) {
        return baseMapper.findByKbBasic(kbBasicDto);
    }

    @Override
    public List<KbBasicEntity> findKnowByLabel(Long id) {
        List<Long> ids = kbBasicLabelService.listById(id);
        return listByIds(ids);
    }

    @Override
    public KbBasicEntity findByIdAuth(Long uid, List<Integer> integers, Long id) {
        KbBasicEntity byIdAuth = baseMapper.findByIdAuth(uid, integers, id);
        return byIdAuth;
    }


    @Override
    public boolean saveAll(KbModelAcceptanceDto formDataDto) {
        KbModelEntity model = kbModelService.getById(formDataDto.getModelId());
        //分离
        KbBasicEntity kbBasicEntity = ConvertUtil.transformObj(formDataDto, KbBasicEntity.class);
        kbBasicEntity.setAttachment(JSONObject.toJSONString(formDataDto.getAttachments()));
        kbBasicEntity.setModelCode(model.getCode());
        //保存基础
        boolean save = save(kbBasicEntity);
        try {
            //保存标签
            formDataDto.setId(kbBasicEntity.getId());
            if (formDataDto.getLabels() != null && formDataDto.getLabels().size() != 0) {
                kbModelLabelService.saveLabel(formDataDto);
            }
            Map<String, Object> cols = formDataDto.getCols();
            cols.put("data_id", kbBasicEntity.getId());
            //保存拓展
            boolean b = false;
            if (formDataDto.getCols() != null) {
                b = kbModelDataService.saveData(formDataDto.getModelId(), formDataDto.getCols());
            } else {
                b = true;
            }
            if (formDataDto.getStatus() == 1) {
                // 只有发布才往es插入
                esSearchService.asyncSaveES(formDataDto);
            }
            //插入知识与用户关联表
            kbBasicUserService.saveKnowUser(formDataDto);
            // 往图片转换表插入
            kbVideVersionService.tmpSave(formDataDto);
            return save && b;
        } catch (Exception e) {
            delete(kbBasicEntity.getId());
            e.printStackTrace();
            return false;
        }

    }

}
