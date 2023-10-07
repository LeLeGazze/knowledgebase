package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbCommentEntity;
import com.castle.fortress.admin.knowledge.dto.KbCommentDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 知识评论管理表 服务类
 *
 * @author 
 * @since 2023-05-09
 */
public interface KbCommentService extends IService<KbCommentEntity> {

    /**
     * 分页展示知识评论管理表列表
     * @param page
     * @param kbCommentDto
     * @return
     */
    IPage<KbCommentDto> pageKbComment(Page<KbCommentDto> page, KbCommentDto kbCommentDto);
    /**
     * 展示知识评论管理表列表
     * @param newsId
     * @return
     */
    List<KbCommentDto> listKbComment(Long newsId);

    /**
     * 查询所有一级评价
     * @param basicId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<KbCommentDto> selectCommentById(Long basicId,Integer pageIndex,Integer pageSize);

    /**
     * 根据父评论找它下面的所有子评论并排序
     * @param comments
     * @return
     */
    List<KbCommentDto> getSons(List<KbCommentDto> comments);

    /**
     * 根据父id找所有子评论
     * @param id -
     * @return  List<KbCommentDto>
     */
    IPage<KbCommentDto> selectSon(Page<KbCommentDto> page,Long id);

    /**
     * 保存
     * @param kbCommentDto
     * @return
     */
    boolean save(KbCommentDto kbCommentDto);

    /**
     * 根据父id找所有子评论id
     * @param id-
     * @return  List<Long>
     */
    List<Long> selectSonId(Long id);

    Integer getCount(Long userId);

    IPage<KbModelTransmitDto> recentComments(Page<KbModelTransmitDto> page, KbBaseShowDto kbBaseShowDto);

    KbCommentDto getCommentOne(Long pId);
}
