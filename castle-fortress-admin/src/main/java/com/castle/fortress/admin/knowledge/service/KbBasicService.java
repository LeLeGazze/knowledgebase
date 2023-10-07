package com.castle.fortress.admin.knowledge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbBasicDto;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.system.entity.SysUser;

import java.util.List;
import java.util.Map;

/**kb基础表 服务类
 * @author sunhr
 * @create 2023/4/19 15:46
 */
public interface KbBasicService extends IService<KbBasicEntity> {


    IPage<KbBaseShowDto> pageKbBaseicWarehouseExtends(Page<KbBaseShowDto> page, KbBaseShowDto kbBasicDto, Long uid, List<Integer> kb_auths);

    KbModelTransmitDto findAllByBasic(Long id);

    void updateDate(KbModelAcceptanceDto formDataDto);

    boolean saveAll(KbModelAcceptanceDto formDataDto);

    KbBasicEntity findByKbBasic(KbBasicDto kbBasicDto);

    /**
     * 根据标签查询知识
     * @param id
     * @return
     */
     List<KbBasicEntity> findKnowByLabel(Long id);

    KbBasicEntity findByIdAuth(Long uid, List<Integer> integers, Long id);

    IPage<KbBaseShowDto> pageKbBaseicWarehouseExtendsAdmin(Page<KbBaseShowDto> page, KbBaseShowDto kbBasicDto);



    int deleteByIdsAdmin(List<Long> ids);

    String deleteByIds(List<Long> kbWarehouseAuthEntityList, List<Long> ids);

    List<KbBaseShowDto> randBasicPageAdmin(Page<KbBaseShowDto> page,String swId);

    List<KbBaseShowDto> randBasicPage(Page<KbBaseShowDto> page, Long uid, List<Integer> integers,String swId);

    List<KbBaseShowDto> newBasicListAdmin();

    List<KbBaseShowDto> newBasicList(Long uid, List<Integer> integers);

    List<KbBaseShowDto> recentPreviewBasicListAdmin(  Long uid,String swId,Integer size);

    List<KbBaseShowDto> recentPreviewBasicList(Long uid, List<Integer> integers,String swId,Integer size);

    /**
     * 我的知识查询   浏览下载上传
     * @param kbBaseShowDto
     * @param page
     * @return
     */
    IPage<KbModelTransmitDto> findBasicByLike(KbBaseShowDto kbBaseShowDto ,Page<KbModelTransmitDto> page,Long userId,List<Integer> integers);

    int findByCategoryId(Long id);

    /**
     * 查询所有知识作者
     * @return
     */
    List<SysUser> findAllAuth();

    List<SysUser> findBaseAuth(List<Integer> asList, Long uid);

    IPage<KbModelTransmitDto> findBasicByLikeAdmin(Page<KbModelTransmitDto> page, KbBaseShowDto kbBaseShowDto);

    IPage<KbModelTransmitDto> findBasicByUploud(Page<KbModelTransmitDto> page, KbBaseShowDto kbBaseShowDto, Long userId, List<Integer> integers);

    List<KbBasicEntity> selectByExpireBasic();

    List<Map<String, Object>> getTheLatest12QuantitiesAdmin();

    List<Map<String, Object>> getTheLatest12Quantities(List<Integer> asList, Long uid);

    List<Map<String, Object>> getKnowledgeBaseTopNAdmin(int num);

    List<Map<String, Object>> getKnowledgeBaseTopN(List<Integer> asList, Long uid, int num);
}
