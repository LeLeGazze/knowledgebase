package com.castle.fortress.admin.check.service;

import com.castle.fortress.admin.check.entity.MapKey;
import com.castle.fortress.admin.check.entity.Sentence;
import com.castle.fortress.admin.es.EsFileDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FileReadService {

    /**
     * 文件处理
     *
     * @param filePath 文件路径
     * @return 返回一个map 安段出路
     */
    public HashMap<MapKey, Object> fileDataHandle(String filePath, int readDataLength );


       void fileContrastDataHandle(String path,HashMap<String, Map<String, Set<Sentence>>> map, EsFileDto content, String s);
}
