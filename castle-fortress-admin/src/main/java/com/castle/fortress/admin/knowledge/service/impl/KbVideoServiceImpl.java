package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.dto.KbBasicDto;
import com.castle.fortress.admin.knowledge.dto.KbVideoShowDto;
import com.castle.fortress.admin.knowledge.entity.*;
import com.castle.fortress.admin.knowledge.mapper.*;
import com.castle.fortress.admin.knowledge.service.KbBasicUserService;
import com.castle.fortress.admin.knowledge.service.KbCollectService;
import com.castle.fortress.admin.knowledge.service.KbModelDataService;
import com.castle.fortress.admin.system.dto.ConfigOssDto;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.dto.KbVideoDto;
import com.castle.fortress.admin.knowledge.service.KbVideoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.lang.reflect.Array;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * 视频库 服务实现类
 *
 * @author
 * @since 2023-05-13
 */
@Service
@Slf4j
public class KbVideoServiceImpl extends ServiceImpl<KbVideoMapper, KbVideoEntity> implements KbVideoService {

    @Autowired
    private KbModelLabelMapper kbModelLabelMapper;

    @Autowired
    private KbBasicLabelServiceImpl kbBasicLabelService;
    @Autowired
    private KbBasicUserMapper userMapper;

    @Autowired
    private KbCommentMapper commentMapper;

    @Autowired
    private ConfigOssService configOssService;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private EsSearchService esSearchService;
    @Autowired
    private KbBasicUserService kbBasicUserService;
    @Autowired
    private KbCollectService kbCollectService;
    @Autowired
    private KbBasicTrashMapper kbBasicTrashMapper;
    @Autowired
    private KbBasicLogMapper kbBasicLogMapper;

    /**
     * 获取查询条件
     *
     * @param kbVideoDto
     * @return
     */
    public QueryWrapper<KbVideoEntity> getWrapper(KbVideoDto kbVideoDto) {
        QueryWrapper<KbVideoEntity> wrapper = new QueryWrapper();
        if (kbVideoDto != null) {
            KbVideoEntity kbVideoEntity = ConvertUtil.transformObj(kbVideoDto, KbVideoEntity.class);
            wrapper.like(kbVideoEntity.getId() != null, "id", kbVideoEntity.getId());
            wrapper.like(kbVideoEntity.getWhId() != null, "wh_id", kbVideoEntity.getWhId());
            wrapper.like(StrUtil.isNotEmpty(kbVideoEntity.getTitle()), "title", kbVideoEntity.getTitle());
            wrapper.like(kbVideoEntity.getAuth() != null, "auth", kbVideoEntity.getAuth());
            wrapper.like(kbVideoEntity.getDeptId() != null, "dept_id", kbVideoEntity.getDeptId());
            wrapper.like(kbVideoEntity.getPubTime() != null, "pub_time", kbVideoEntity.getPubTime());
            wrapper.like(kbVideoEntity.getCategoryId() != null, "category_id", kbVideoEntity.getCategoryId());
            wrapper.like(kbVideoEntity.getModelId() != null, "model_id", kbVideoEntity.getModelId());
            wrapper.like(kbVideoEntity.getExpTime() != null, "exp_time", kbVideoEntity.getExpTime());
            wrapper.like(StrUtil.isNotEmpty(kbVideoEntity.getCover()), "cover", kbVideoEntity.getCover());
            wrapper.like(StrUtil.isNotEmpty(kbVideoEntity.getVideoUrl()), "video_url", kbVideoEntity.getVideoUrl());
            wrapper.like(StrUtil.isNotEmpty(String.valueOf(kbVideoEntity.getVideoSrc())), "video_src", kbVideoEntity.getVideoSrc());
            wrapper.like(StrUtil.isNotEmpty(kbVideoEntity.getLabel()), "label", kbVideoEntity.getLabel());
            wrapper.like(StrUtil.isNotEmpty(kbVideoEntity.getModelCode()), "model_code", kbVideoEntity.getModelCode());
            wrapper.like(StrUtil.isNotEmpty(kbVideoEntity.getRemark()), "remark", kbVideoEntity.getRemark());
            wrapper.like(kbVideoEntity.getSort() != null, "sort", kbVideoEntity.getSort());
            wrapper.like(kbVideoEntity.getStatus() != null, "status", kbVideoEntity.getStatus());
            wrapper.like(kbVideoEntity.getCreateTime() != null, "create_time", kbVideoEntity.getCreateTime());
            wrapper.like(kbVideoEntity.getCreateUser() != null, "create_user", kbVideoEntity.getCreateUser());
            wrapper.like(kbVideoEntity.getIsDeleted() != null, "is_deleted", kbVideoEntity.getIsDeleted());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }

    /**
     * 管理员查询列表
     *
     * @param page
     * @param kbVideoShowDto
     * @return
     */
    @Override
    public IPage<KbVideoShowDto> pageKbVideoAdmin(Page<KbVideoDto> page, KbVideoShowDto kbVideoShowDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbVideoShowDto> kbVideoList = baseMapper.pageKbVideoAdmin(pageMap, kbVideoShowDto);
        Long total = baseMapper.pageKbVideoAdminCount(kbVideoShowDto);
        Page<KbVideoShowDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbVideoList, KbVideoShowDto.class));
        return pageDto;
    }

    /**
     * 普通用户列表
     *
     * @param page
     * @param kbVideoShowDto
     * @param uid
     * @param kb_auths
     * @return
     */
    @Override
    public IPage<KbVideoShowDto> pageKbVideo(Page<KbVideoDto> page, KbVideoShowDto kbVideoShowDto, Long uid, List<Integer> kb_auths) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbVideoShowDto> kbVideoList = baseMapper.pageKbVideo(pageMap, kbVideoShowDto, uid, kb_auths);
        Long total = baseMapper.pageKbVideoCount(kbVideoShowDto, uid, kb_auths);
        Page<KbVideoShowDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbVideoList, KbVideoShowDto.class));
        return pageDto;
    }

    @Override
    public KbVideoDto findByIdVideo(Long id) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        KbVideoDto kbVideoDto = baseMapper.findById(id);
        if (kbVideoDto == null) {
            throw new BizException("查询详情传入的id有误，未查找到数据");
        }
        // 获取浏览次数
        Callable<List<HashMap<String, Integer>>> mapListCallable = new Callable<List<HashMap<String, Integer>>>() {
            @Override
            public List<HashMap<String, Integer>> call() throws Exception {
                return userMapper.findByBid(id);
            }
        };
        // 获取评论次数
        Callable<Integer> commentCallable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return commentMapper.findByBid(id);
            }
        };

        // 获取标签列表
        Callable<List<String>> labelCallable = new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return kbModelLabelMapper.findLabelByBid(id);
            }
        };
        FutureTask<List<HashMap<String, Integer>>> mapListFuture = new FutureTask<>(mapListCallable);
        FutureTask<Integer> commentFuture = new FutureTask<>(commentCallable);
        FutureTask<List<String>> labelFuture = new FutureTask<>(labelCallable);
        new Thread(mapListFuture).start();
        new Thread(commentFuture).start();
        new Thread(labelFuture).start();
        try {
            List<HashMap<String, Integer>> mapList = mapListFuture.get();
            if (mapList != null) {
                for (HashMap<String, Integer> str : mapList) {
                    if (str.get("type") == 1) {
                        kbVideoDto.setReadCount(Integer.parseInt(str.getOrDefault("count", 0) + ""));
                    }
                }
            }
            kbVideoDto.setCommentsCount(commentFuture.get());
            kbVideoDto.setLabel(labelFuture.get());
            KbCollectEntity byid = kbCollectService.findByid(userId, kbVideoDto.getId());
            if (byid == null) {
                kbVideoDto.setCollectStatus(2);
            } else {
                kbVideoDto.setCollectStatus(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kbVideoDto;
    }

    @Override
    public KbVideoEntity findByIdAuth(Long uid, List<Integer> integers, Long id) {
        return baseMapper.findByIdAuth(uid, integers, id);
    }

    @Override
    public String findByVidToUrl(String vid) {
        // 获取文件路径
        ConfigOssDto ftDto = new ConfigOssDto();
        ftDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigOssDto> ftDtoList = configOssService.selectBySelective(ftDto);
        ConfigOssDto configOssDto = ftDtoList.get(0);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(configOssDto.getPtConfig(), OssPlatFormDto.class);
        String filePositon = ossPlatFormDto.getLocalFilePosition();
        KbVideoEntity kbVideoEntity = baseMapper.selectById(vid);
        if (kbVideoEntity == null) {
            throw new BizException("传入的id有误【" + vid + " 没有查询到该视频】");
        }
        String videoSrc = String.valueOf(kbVideoEntity.getVideoSrc());
        JSONObject jsonObject = JSONObject.parseArray(videoSrc, JSONObject.class).get(0);
        if (jsonObject != null) {
            String path = jsonObject.getString("path");
            System.out.println(filePositon + "\\" + path);
            return filePositon + "\\" + path;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean editById(KbVideoDto kbVideoDto) {
        //获取当前用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        // 插入视频表
        KbVideoEntity kbVideoEntity = ConvertUtil.transformObj(kbVideoDto, KbVideoEntity.class);
        kbVideoEntity.setVideoSrc(JSONObject.toJSONString(kbVideoDto.getVideoSrc()));
        KbVideoEntity kbVideoEntity1 = baseMapper.selectById(kbVideoDto.getId());
        //插入新老分类Id
        KbBasicLogEntity kbBasicLogEntity = new KbBasicLogEntity();
        kbBasicLogEntity.setOldCategory(kbVideoEntity1.getCategoryId());
        kbBasicLogEntity.setNewCategory(kbVideoEntity.getCategoryId());
        //更新视频知识
        if(baseMapper.updateById(kbVideoEntity)>0){
            KbBasicDto kbBasicDto = new KbBasicDto();
            kbBasicDto.setAuth(kbVideoEntity.getAuth());
            kbBasicDto.setOldAuth(kbVideoEntity1.getAuth());
            if (!kbBasicDto.getAuth().equals(kbBasicDto.getOldAuth())) {
                kbBasicUserService.removeBasicUser(kbBasicDto.getAuth(),kbVideoEntity.getId());
            }
        };

        //视频知识分类变更记录日志
        if (!kbBasicLogEntity.getOldCategory().equals(kbBasicLogEntity.getNewCategory())) {
            List<KbBasicLogEntity> logEntity = kbBasicLogMapper.selectByBasic(kbVideoDto.getId());
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
            kbBasicLogEntity.setBasicId(kbVideoDto.getId());
            kbBasicLogEntity.setType(2);
            kbBasicLogMapper.insert(kbBasicLogEntity);
        }
        // 删除标签中间表的数据
        QueryWrapper<KbBasicLabelEntity> kbBasicLab = new QueryWrapper<>();
        kbBasicLab.eq("b_id", kbVideoEntity.getId());
        kbBasicLabelService.remove(kbBasicLab);
        // 插入标签表
        List<KbModelLabelEntity> labels = kbVideoDto.getLabels();
        if (!labels.isEmpty()) {
            List<String> labelLiST = labels.stream().map(item -> item.getName()).collect(Collectors.toList());
            kbVideoDto.setLabel(labelLiST);
            // 查询哪个标签已经在数据库存在了
            List<KbModelLabelEntity> kbBasicLabelEntityList = kbModelLabelMapper.findIsExistNames(labelLiST);
            Map<String, KbModelLabelEntity> labelMap = new HashMap<>();
            for (KbModelLabelEntity kbModelLabelEntity : kbBasicLabelEntityList) {
                labelMap.put(kbModelLabelEntity.getName(),kbModelLabelEntity);
            }
            if (!labels.isEmpty()) {
                addLabels(kbVideoDto, kbVideoEntity, labels);
            }
        }
//        redisUtils.remove("video:filePath:" + kbVideoEntity.getId()); // 修改数据删除缓存播放地址
        //插入知识操作表
        LambdaQueryWrapper<KbBasicUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KbBasicUserEntity::getUserId, userId);
        wrapper.eq(KbBasicUserEntity::getBId, kbVideoEntity.getId());
        wrapper.eq(KbBasicUserEntity::getType, 2);
        wrapper.eq(KbBasicUserEntity::getStatus, 2);
        kbBasicUserService.remove(wrapper);
        KbBasicUserEntity kbBasicUserEntity = new KbBasicUserEntity();
        kbBasicUserEntity.setUserId(userId);
        kbBasicUserEntity.setBId(kbVideoEntity.getId());
        kbBasicUserEntity.setStatus(2);
        kbBasicUserEntity.setType(2);
        kbBasicUserService.save(kbBasicUserEntity);

        if (kbVideoDto.getStatus() == 1) {
            //  将视频插入到es中
            esSearchService.asyncUpdateESVideo(kbVideoDto);
        } else {
            esSearchService.deleteByid(kbVideoDto.getId());
        }
        kbBasicUserService.addVideoUser(kbVideoDto.getAuth(),kbBasicUserEntity.getId());
        return true;
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
            int deleteRes = baseMapper.deletedByid(id);
            KbBasicTrashEntity kbBasicTrashEntity = new KbBasicTrashEntity();
            kbBasicTrashEntity.setBasicId(id);
            kbBasicTrashEntity.setUserId(userId);
            kbBasicTrashEntity.setType(2);
            kbBasicTrashEntity.setCreateTime(new Date());
            kbBasicTrashMapper.insert(kbBasicTrashEntity);
            if (deleteRes == 0) {
                res = 0;
            }
            log.debug("delete admin 删除结果【{}】", deleteRes);
        }
        return res;
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
            KbVideoEntity kbVideoEntity = baseMapper.selectById(id);
            if (kbVideoEntity == null) {
                continue;
            }
            if (kbWarehouseAuthEntityList.contains(kbVideoEntity.getCategoryId())) {
                //有权限执行删除
                int deleteRes = baseMapper.deletedByid(id);
                KbBasicTrashEntity kbBasicTrashEntity = new KbBasicTrashEntity();
                kbBasicTrashEntity.setBasicId(id);
                kbBasicTrashEntity.setUserId(userId);
                kbBasicTrashEntity.setType(2);
                kbBasicTrashEntity.setCreateTime(new Date());
                kbBasicTrashMapper.insert(kbBasicTrashEntity);
                log.debug("delete ordinary 删除结果【{}】", deleteRes);
            } else {
                flag = true;
                // 无权限反馈给前段
                resStr.append(kbVideoEntity.getTitle()).append("  ");
            }
        }
        resStr.append("】 ");
        return flag ? resStr.toString() : "刪除成功";
    }

    @Override
    public List<KbVideoDto> showListVideoAdmin(Long swId) {
        return baseMapper.showListVideoAdmin(swId);
    }

    @Override
    public List<KbVideoDto> showListVideo(Long swId, List<Integer> integers, Long uid) {
        return baseMapper.showListVideo(swId, integers, uid);
    }

    @Override
    public List<KbVideoDto> randVideoListAdmin() {
        return baseMapper.randVideoListAdmin();
    }

    @Override
    public List<KbVideoDto> randVideoList(List<Integer> integers, Long uid) {
        return baseMapper.randVideoList(integers, uid);
    }

    @Override
    public List<KbVideoEntity> selectByExpireVideo() {
        return baseMapper.selectByExpireVideo();
    }


    @Transactional
    public int delete(Long id) {
        try {
            // 删除标签中间表
            int deleteByLabelBid = kbBasicLabelService.delectByIds(id);
            //删除浏览、下载、收藏
            int deleteByBid = userMapper.deleteByBid(id);
            // 删除评论表
            int deleteByCommentBid = commentMapper.deleteByBid(id);
            // 删除缓存数据es表
            int deleteEsById = esSearchService.deleteByid(id);
            // 删除视频表数据
            int deleteVideo = baseMapper.deleteById(id);
            // 删除视频播放地址redis
//            redisUtils.remove("video:filePath:" + id);
            // 删除es数据
            esSearchService.deleteByid(id);
            log.debug("删除标签中间表数量【{}】 删除收藏表数量【{}】 删除评论表数量【{}】删除es缓存表【{}】删除视频表【{}】", deleteByLabelBid, deleteByBid, deleteByCommentBid, deleteEsById, deleteVideo);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public IPage<KbVideoDto> pageKbVideoExtends(Page<KbVideoDto> page, KbVideoDto kbVideoDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        KbVideoEntity kbVideoEntity = ConvertUtil.transformObj(kbVideoDto, KbVideoEntity.class);
        List<KbVideoEntity> kbVideoList = baseMapper.extendsList(pageMap, kbVideoEntity);
        Long total = baseMapper.extendsCount(kbVideoEntity);
        Page<KbVideoDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbVideoList, KbVideoDto.class));
        return pageDto;
    }

    @Override
    public KbVideoDto getByIdExtends(Long id) {
        KbVideoEntity kbVideoEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbVideoEntity, KbVideoDto.class);
    }

    @Override
    public List<KbVideoDto> listKbVideo(KbVideoDto kbVideoDto) {
        QueryWrapper<KbVideoEntity> wrapper = getWrapper(kbVideoDto);
        List<KbVideoEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbVideoDto.class);
    }

    @Override
//    @Transactional
    public boolean saveAll(KbVideoDto kbVideoDto) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        // 插入视频表
        KbVideoEntity kbVideoEntity = ConvertUtil.transformObj(kbVideoDto, KbVideoEntity.class);
        kbVideoEntity.setVideoSrc(JSONObject.toJSONString(kbVideoDto.getVideoSrc()));
        baseMapper.insert(kbVideoEntity);
        kbVideoDto.setId(kbVideoEntity.getId());
        // 插入标签表
        List<KbModelLabelEntity> labels = kbVideoDto.getLabels();
        if (!labels.isEmpty()) {
            addLabels(kbVideoDto, kbVideoEntity, labels);

        }
        //插入知识操作表
        KbBasicUserEntity kbBasicUserEntity = new KbBasicUserEntity();
        kbBasicUserEntity.setUserId(userId);
        kbBasicUserEntity.setBId(kbVideoEntity.getId());
        kbBasicUserEntity.setStatus(2);
        kbBasicUserEntity.setType(2);
        kbBasicUserService.save(kbBasicUserEntity);
        if (kbVideoDto.getStatus() == 1) {
            //  将视频插入到es中
            esSearchService.saveEsFileVideo(kbVideoDto);
        }
        kbBasicUserService.addVideoUser(kbVideoDto.getAuth(),kbBasicUserEntity.getId());

        return true;
    }

    private void addLabels(KbVideoDto kbVideoDto, KbVideoEntity kbVideoEntity, List<KbModelLabelEntity> labels) {
        List<String> labelLiST = labels.stream().map(item -> item.getName()).collect(Collectors.toList());
        kbVideoDto.setLabel(labelLiST);
        // 查询哪个标签已经在数据库存在了
        List<KbModelLabelEntity> kbBasicLabelEntityList = kbModelLabelMapper.findIsExistNames(labelLiST);
        Map<String, KbModelLabelEntity> labelMap = new HashMap<>();
        for (KbModelLabelEntity kbModelLabelEntity : kbBasicLabelEntityList) {
            labelMap.put(kbModelLabelEntity.getName()+"_"+kbModelLabelEntity.getStatus(),kbModelLabelEntity);
        }
        ArrayList<KbBasicLabelEntity> kbBasicLabelEntities = new ArrayList<>();
        // 插入标签中间表
        for (KbModelLabelEntity labelName : labels) {
            if (labelMap.get(labelName.getName()+"_"+labelName.getStatus()) == null) {
                // 说明是空的 添加到标签表中
                KbModelLabelEntity kbModelLabelEntity = new KbModelLabelEntity();
                kbModelLabelEntity.setName(labelName.getName());
                //status 2：机选标签 手工是1
                if (labelName.getStatus() == null) {
                    throw new BizException("标签状态不允许为空");
                }
                kbModelLabelEntity.setStatus(labelName.getStatus());
                kbModelLabelEntity.setSort(1);
                kbModelLabelEntity.setHotWord(2);
                kbModelLabelMapper.insert(kbModelLabelEntity);
                //存入标签关联表
                KbBasicLabelEntity kbl = new KbBasicLabelEntity();
                kbl.setLId(kbModelLabelEntity.getId());
                kbl.setBId(kbVideoEntity.getId());
                kbBasicLabelEntities.add(kbl);
            } else {
                // 说明之前已经添加过标签了直接插入中间表就行
                //存入标签关联表
                KbBasicLabelEntity kbl = new KbBasicLabelEntity();
                kbl.setLId(labelMap.get(labelName.getName()+"_"+labelName.getStatus()).getId());
                kbl.setBId(kbVideoEntity.getId());
                kbBasicLabelEntities.add(kbl);
            }
        }
        kbBasicLabelService.saveBatch(kbBasicLabelEntities);
    }

    public boolean updateByVideo(Long basicId) {

        return baseMapper.updateByVideo(basicId);
    }
}

