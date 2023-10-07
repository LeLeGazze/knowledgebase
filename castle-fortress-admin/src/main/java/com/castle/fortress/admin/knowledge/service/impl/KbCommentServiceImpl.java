package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.mapper.KbBasicMapper;
import com.castle.fortress.admin.knowledge.mapper.KbBasicUserMapper;
import com.castle.fortress.admin.knowledge.service.KbThumbsUpService;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbCommentEntity;
import com.castle.fortress.admin.knowledge.dto.KbCommentDto;
import com.castle.fortress.admin.knowledge.mapper.KbCommentMapper;
import com.castle.fortress.admin.knowledge.service.KbCommentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 知识评论管理表 服务实现类
 *
 * @author
 * @since 2023-05-09
 */
@Service
public class KbCommentServiceImpl extends ServiceImpl<KbCommentMapper, KbCommentEntity> implements KbCommentService {
    @Autowired
    private KbCommentMapper kbCommentMapper;
    @Autowired
    private KbBasicUserMapper userMapper;
    @Autowired
    private KbBasicMapper kbBasicMapper;
    @Autowired
    private KbThumbsUpService kbThumbsUpService;

    /**
     * 获取查询条件
     *
     * @param kbCommentDto
     * @return
     */
    public QueryWrapper<KbCommentEntity> getWrapper(KbCommentDto kbCommentDto) {
        QueryWrapper<KbCommentEntity> wrapper = new QueryWrapper();
        if (kbCommentDto != null) {
            KbCommentEntity kbCommentEntity = ConvertUtil.transformObj(kbCommentDto, KbCommentEntity.class);
            wrapper.like(kbCommentEntity.getId() != null, "id", kbCommentEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbCommentEntity.getContent()), "content", kbCommentEntity.getContent());
            wrapper.like(kbCommentEntity.getBasicId() != null, "basic_id", kbCommentEntity.getBasicId());
            wrapper.like(kbCommentEntity.getParentId() != null, "parent_id", kbCommentEntity.getParentId());
            wrapper.like(kbCommentEntity.getUserId() != null, "user_id", kbCommentEntity.getUserId());
            wrapper.like(kbCommentEntity.getIsDeleted() != null, "is_deleted", kbCommentEntity.getIsDeleted());
        }
        return wrapper;
    }


    @Override
    public IPage<KbCommentDto> pageKbComment(Page<KbCommentDto> page, KbCommentDto kbCommentDto) {
        QueryWrapper<KbCommentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(kbCommentDto.getBasicId() != null, "basic_id", kbCommentDto.getBasicId());
        wrapper.isNull("parent_id");
        Page<KbCommentEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbCommentEntity> kbCommentPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbCommentDto> pageDto = new Page(kbCommentPage.getCurrent(), kbCommentPage.getSize(), kbCommentPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbCommentPage.getRecords(), KbCommentDto.class));
        return pageDto;
    }


    @Override
    public List<KbCommentDto> listKbComment(Long newsId) {
        List<KbCommentDto> allComments = kbCommentMapper.getComments(newsId);
        if (allComments == null || allComments.size() == 0) {
            return new ArrayList<>();
        }
        List<KbCommentDto> comments = new ArrayList<>();
        List<KbCommentDto> parents = new ArrayList<>();
        for (KbCommentDto comment : allComments) {
            if (comment.getParentId() == null) {
                comments.add(comment);
                parents.add(comment);
            } else {
                boolean foundParent = false;
                for (KbCommentDto parent : parents) {
                    if (comment.getParentId().equals(parent.getId())) {
                        if (parent.getChildren() == null) {
                            parent.setChildren(new ArrayList<>());
                        }
                        parent.getChildren().add(comment);
                        parents.add(comment);
                        foundParent = true;
                        //如果对list迭代过程中同时修改list，会报java.util.ConcurrentModificationException 的异常，所以我们需要break,当然break也可以提高算法效率
                        break;
                    }
                }
                if (!foundParent) {
                    throw new RuntimeException("can not find the parent comment");
                }
            }
        }
        return comments;
    }

    @Override
    public List<KbCommentDto> selectCommentById(Long basicId, Integer pageIndex, Integer pageSize) {
        Integer index = (pageIndex - 1) * pageSize;
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        List<KbCommentDto> kbCommentDtos = kbCommentMapper.selectComment(basicId, index, pageSize,userId);
        for (KbCommentDto kbCommentDto : kbCommentDtos) {
            if (kbCommentDto.getUpStatus()==0){
                kbCommentDto.setUpStatus(2);
            }
        }

        return kbCommentDtos;
    }


    //评论分页查询
    @Override
    public List<KbCommentDto> getSons(List<KbCommentDto> comments) {
        if (comments == null || comments.size() == 0) {
            return null;
        }
        for (KbCommentDto parent : comments) {
            Long parentId = parent.getId();
            Long basicId = parent.getBasicId();
            List<KbCommentDto> sonCommentVos = getSons(parentId, basicId);
            //递归找子评论
            parent.setChildren(getSons(sonCommentVos));
        }
        return comments;
    }

    @Override
    public IPage<KbCommentDto> selectSon(Page<KbCommentDto> page,Long id) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbCommentDto> kbCommentDtos = kbCommentMapper.selectSon(pageMap,id,userId);
        for (KbCommentDto kbCommentDto : kbCommentDtos) {
            if (kbCommentDto.getUpStatus()==0){
                kbCommentDto.setUpStatus(2);
            }
        }
        Integer total = kbCommentMapper.selectSonCount(id,userId);
        Page<KbCommentDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(kbCommentDtos);
        return pageDto;
    }

    @Override
    public boolean save(KbCommentDto kbCommentDto) {
        SysUser sysUser = WebUtil.currentUser();
        Long userId = sysUser.getId();

        if (kbCommentDto.getParentId() != null) {
            KbCommentEntity byId = getById(kbCommentDto.getParentId());
            if (byId.getParentId() == null) {
                kbCommentDto.setOneId(byId.getId());
            } else {
                kbCommentDto.setOneId(byId.getOneId());
            }
            byId.setIsReply(1);
            updateById(byId);
        }
        kbCommentDto.setIsReply(2);
        kbCommentDto.setUserId(userId);
        KbCommentEntity kbCommentEntity = ConvertUtil.transformObj(kbCommentDto, KbCommentEntity.class);
        return save(kbCommentEntity);
    }

    @Override
    public List<Long> selectSonId(Long id) {
        return kbCommentMapper.selectSonId(id);
    }

    @Override
    public Integer getCount(Long userId) {
        return kbCommentMapper.getCount(userId);
    }

    @Override
    public IPage<KbModelTransmitDto> recentComments(Page<KbModelTransmitDto> page, KbBaseShowDto kbBaseShowDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<Long> categoryIds = kbBaseShowDto.getCategoryIds();
        if (categoryIds == null || categoryIds.size() == 0) {
            kbBaseShowDto.setCategoryIds(null);
        }
        if(kbBaseShowDto.getSwIds() == null|| kbBaseShowDto.getSwIds().size() == 0){
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
        if(kbBaseShowDto.getType()==1){
            List<KbModelTransmitDto> kbModelTransmitDtos = baseMapper.recentComments(pageMap, kbBaseShowDto);
            Integer total = baseMapper.recentCountComments(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(kbModelTransmitDtos);
            return pageDto;
        }else {
            List<KbModelTransmitDto> kbModelTransmitDtos = baseMapper.recentCommentsVideo(pageMap, kbBaseShowDto);
            Integer total = baseMapper.recentCountCommentsVideo(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(kbModelTransmitDtos);
            return pageDto;
        }

    }

    @Override
    public KbCommentDto getCommentOne(Long pId) {
        return baseMapper.getCommentOne(pId);
    }


    public List<KbCommentDto> getSons(Long parentId, Long basicId) {

        return this.kbCommentMapper.selectDownComment(parentId, basicId);
    }
}

