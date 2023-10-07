package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.mapper.KbModelDataMapper;
import com.castle.fortress.admin.knowledge.service.KbModelDataService;
import com.castle.fortress.admin.knowledge.service.KbModelService;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 表数据管理 服务实现类
 *
 * @author castle
 * @since 2021-11-10
 */
@Service
public class KbModelDataServiceImpl implements KbModelDataService {
    @Autowired
    private KbModelDataMapper kbModelDataMapper;
    @Autowired
    private KbModelService kbModelService;

    @Override
    public boolean saveData(Long modelId, Map<String, Object> dataMap) {
        boolean result = false;
        if (modelId == null || dataMap == null || dataMap.isEmpty()) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelEntity modelEntity = kbModelService.getById(modelId);
        String tbName = "kb_model_ks_" + modelEntity.getCode();
        List<String> propName = new ArrayList<>();
        List<Object> propValue = new ArrayList<>();
        List<Map<String, Object>> updateList = new ArrayList<>();
        String id = dataMap.get("id").toString();
        for (String key : dataMap.keySet()) {
            Map<String, Object> m = new HashMap<>();
            if (dataMap.get(key) instanceof List) {
                List l = (List) dataMap.get(key);
                if (l.size() > 0 && l.get(0) instanceof Map) {
                    propValue.add(new JSONArray((List) dataMap.get(key)).toString());
                    m.put("value", new JSONArray((List) dataMap.get(key)).toString());
                } else {
                    propValue.add(CollectionUtil.join((List) dataMap.get(key), ","));
                    m.put("value", CollectionUtil.join((List) dataMap.get(key), ","));
                }
            } else if (dataMap.get(key) instanceof Map) {
                propValue.add(new JSONObject(dataMap.get(key)).toString());
                m.put("value", new JSONObject(dataMap.get(key)).toString());
            } else {
                propValue.add(dataMap.get(key));
                m.put("value", dataMap.get(key));
            }
            propName.add(key);
            m.put("name", key);
            updateList.add(m);
        }
        if (!propName.isEmpty()) {
            Map<String, Object> data = queryById(modelEntity.getCode(), id);
            if (data != null) {

                kbModelDataMapper.updateData(tbName, id, updateList);
                result = true;
            } else {
                int index = propName.indexOf("id");
                propName.removeIf("id"::equals);
                propValue.remove(index);
                kbModelDataMapper.saveData(tbName, propName, propValue);
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean saveHistoryData(KbModelEntity model, Map<String, Object> dataMap) {
        String tbName = "kb_model_ks_history_" + model.getCode();
        List<String> propName = new ArrayList<>();
        List<Object> propValue = new ArrayList<>();
        for (String item : dataMap.keySet()) {
            propName.add(item);
            propValue.add(dataMap.get(item));
        }
        kbModelDataMapper.saveData(tbName, propName, propValue);
        return false;
    }

    @Override
    public Map<String, Object> queryById(String code, String id) {
        Map<String, Object> data = kbModelDataMapper.queryById("kb_model_ks_" + code, id);

        return data;
    }

    @Override
    public Map<String, Object> queryByDataId(String code, Long id) {
        return kbModelDataMapper.queryByDataId("kb_model_ks_" + code, id);
    }

    @Override
    public List<Map<String, String>> selectComment(String code) {
        return kbModelDataMapper.selectComment(code);
    }

    @Override
    public int deleteByDId(String tableName, Long id) {
        return kbModelDataMapper.deleteByDId(tableName, id);
    }

    @Override
    public Map<String, Object> queryByHistoryDataId(String modelCode, Long id) {
        return kbModelDataMapper.queryByDataId("kb_model_ks_history_" + modelCode, id);    }

    @Override
    public int deleteByBIdHis(String tableName, Long bid) {
        return kbModelDataMapper.deleteByBIdHis(tableName, bid);
    }
}

