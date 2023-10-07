package com.castle.fortress.admin.knowledge.service;

import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import java.util.List;
import java.util.Map;

/**
 * cms表模型数据管理 服务类
 *
 * @author castle
 * @since 2021-11-10
 */
public interface KbModelDataService {

    /**
     * 新增、修改表数据
     * @param modelId
     * @param dataMap
     * @return
     */
    boolean saveData(Long modelId,  Map<String,Object> dataMap);
    boolean saveHistoryData(KbModelEntity model, Map<String,Object> dataMap);

    /**
     * 查询指定表指定id的数据
     * @param code 模型编码
     * @param id
     * @return
     */
    Map<String, Object> queryById(String code, String id);

    Map<String, Object> queryByDataId(String code, Long id);

    List<Map<String, String>> selectComment(String code);

    int deleteByDId(String tableName, Long id);

    Map<String, Object> queryByHistoryDataId(String modelCode, Long id);

    int deleteByBIdHis(String tableName, Long bid);
}
