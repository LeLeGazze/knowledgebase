package com.castle.fortress.admin.check.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.castle.fortress.admin.check.entity.MapKey;
import com.castle.fortress.admin.check.entity.Sentence;
import com.castle.fortress.admin.check.service.FileReadService;
import com.castle.fortress.admin.check.utils.IKUtil;
import com.castle.fortress.admin.es.EsFileDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TextFileReadServiceImpl implements FileReadService {
    @Override
    public HashMap<MapKey, Object> fileDataHandle(String filePath, int readDataLength) {
        HashMap<MapKey, Object> dataMap = new HashMap<>();
        List<String> strings = null;
        try {
            strings = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Sentence> sentenceList = strings.stream().filter(item -> item.length() > readDataLength).map(item -> new Sentence(item)).collect(Collectors.toList());
        String tmp = "txt";
        if (sentenceList.size() > 0) {
            if (sentenceList.get(0).getText().length() > 5) {
                tmp = sentenceList.get(0).getText().substring(0, 5);
            } else {
                tmp = sentenceList.get(0).getText();
            }
        }
        dataMap.put(new MapKey(tmp,1), sentenceList);
        dataMap.put(new MapKey("length",0), StringUtils.join(strings, "\n").length());
        return dataMap;
    }

    @Override
    public synchronized void fileContrastDataHandle(String path, HashMap<String, Map<String, Set<Sentence>>> map, EsFileDto content, String s) {
        File file = new File(path);
        if (!file.exists() || map.containsKey(SecureUtil.md5(file))) {
            return;
        }
        HashMap<String, Set<Sentence>> result = new HashMap<>();
        // 读取数据
        try {
            List<String> datadLineList = Files.readAllLines(Paths.get(path));
            for (String line : datadLineList) {
                if (StrUtil.isEmpty(line) || line.length() < 3) continue;
                // 进行分词
                List<String> wordList = IKUtil.divideText(line);
                for (String word : wordList) {
                    Sentence sentence = new Sentence(word);
                    sentence.setDate(DateUtil.formatDate(content.getFileDate()));
                    sentence.setTitle(content.getTitle());
                    sentence.setLength(content.getLength());
                    sentence.setFileName(content.getFileName());
                    // 添加数据
                    result.computeIfAbsent(word, k -> new HashSet<>()).add(sentence);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        map.put(SecureUtil.md5(file), result);
    }


}
