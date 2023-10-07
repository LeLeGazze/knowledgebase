package com.castle.fortress.admin.knowledge.utis;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler extends JacksonTypeHandler {

    public JsonHandler (Class<?> type) {
        super(type);
    }


    @Override
    protected List<Object> parse(String json) {
        List<Object> jsons = new ArrayList<>();
        try {
            jsons = JSONObject.parseArray(json);
        } catch (JSONException e) {
            jsons.add(JSONObject.parseObject(json));
        }
        return  jsons;
    }
}