package com.castle.fortress.admin.check.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.castle.fortress.admin.check.entity.MapKey;
import com.castle.fortress.admin.check.entity.Sentence;
import com.castle.fortress.admin.check.service.FileReadService;
import com.castle.fortress.admin.check.utils.IKUtil;
import com.castle.fortress.admin.es.EsFileDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WordFileReadServiceImpl implements FileReadService {

    @Override
    public HashMap<MapKey, Object> fileDataHandle(String filePath, int readDataLength) {
        HashMap<MapKey, Object> dataMap = getStringObjectHashMap(filePath, readDataLength);
        return dataMap;
    }

    @Override
    public synchronized void fileContrastDataHandle(String path, HashMap<String, Map<String, Set<Sentence>>> map, EsFileDto content, String s) {
        File file = new File(path);
        if (!file.exists() || map.containsKey(SecureUtil.md5(file))) {
            return;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Set<Sentence>> result = new HashMap<>();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (StrUtil.isEmpty(paragraph.getText()) || paragraph.getText().length() < 3) {
                continue;
            }
            List<String> strings = IKUtil.divideText(paragraph.getText());
            for (String string : strings) {
                Sentence sentence = new Sentence(paragraph.getText());
                sentence.setDate(DateUtil.formatDate(content.getFileDate()));
                sentence.setTitle(content.getTitle());
                sentence.setLength(content.getLength());
                sentence.setFileName(content.getFileName());
                result.computeIfAbsent(string, k -> new HashSet<>()).add(sentence);
            }
        }
        map.put(SecureUtil.md5(file), result);
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\hcses\\Desktop\\11f508ba-24a7-40fa-a987-aca720ad44d2.docx");
        XWPFDocument document = new XWPFDocument(fis);
        List<XWPFTable> tables = document.getTables();
        if (tables != null && tables.size() > 0) {
            for (XWPFTable table : tables) {
                for (XWPFTableRow row : table.getRows()) {
                    // 遍历行的单元格
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        // 读取单元格内容
                        String cellText = cell.getText();
                        System.out.print(cellText);
                    }
                }
            }
        }
    }

    private static HashMap<MapKey, Object> getStringObjectHashMap(String filePath, int readDataLength) {
        HashMap<MapKey, Object> dataMap = new HashMap<>();
        dataMap.put(new MapKey("length",0), 0);
        try {
            int index = 1;
            // 加载Word文档
            log.debug(filePath);
            FileInputStream fis = new FileInputStream(filePath);
            XWPFDocument document = new XWPFDocument(fis);
            List<Sentence> sentenceList = new ArrayList<>();
            String tmp = "";
            int count = 0;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (StrUtil.isEmpty(paragraph.getText()) || paragraph.getText().length() < 3) continue;
                // 检查段落是否为标题
                if (paragraph.getStyleID() != null) {
                    int level = 4;
                    try {
                        level = Integer.parseInt(paragraph.getStyleID());
                    } catch (Exception e) {
                    }
//                    System.out.println(level+" " +paragraph.getText());
                    if (level < 4 && sentenceList.size() > 0) {
                        String str = "";
                        if (sentenceList.get(0).getText().length() > 6) {
                            str = sentenceList.get(0).getText().substring(0, 5);
                        } else {
                            str = sentenceList.get(0).getText();
                        }
                        if (StrUtil.isEmpty(tmp.trim()) && StrUtil.isEmpty(str.trim())) {
                            tmp = paragraph.getText();
                        } else {
                            dataMap.put(new MapKey(StrUtil.isEmpty(tmp) ? str.trim() : tmp.trim(),index++), sentenceList);
                            sentenceList = new ArrayList<>();
                            tmp = paragraph.getText();
                        }
                    } else if (level < 4) {
                        tmp = paragraph.getText();
                    } else {
                        if (paragraph.getText().length() > readDataLength) {
                            count += paragraph.getText().length();
                            sentenceList.add(new Sentence(paragraph.getText()));
                        }
                    }
                } else {
                    if (paragraph.getText().length() > readDataLength) {
                        count += paragraph.getText().length();
                        sentenceList.add(new Sentence(paragraph.getText()));
                    }
                }
            }

            if (sentenceList.size() > 0) {
                if (StrUtil.isEmpty(tmp)) {
                    if (sentenceList.get(0).getText().length() > 5) {
                        tmp = sentenceList.get(0).getText().substring(0, 5);
                    } else {
                        tmp = sentenceList.get(0).getText();
                    }
                }
                dataMap.put(new MapKey(tmp.trim(),index++), sentenceList);
            }
            // 读取表格数据

            List<XWPFTable> tables = document.getTables();
            if (tables != null && tables.size() > 0) {
                sentenceList = new ArrayList<>();
                for (XWPFTable table : tables) {
                    for (XWPFTableRow row : table.getRows()) {
                        // 遍历行的单元格
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            // 读取单元格内容
                            String cellText = cell.getText();
                            if (StrUtil.isNotEmpty(cellText) || cellText.length() > 3) {
                                count += cellText.length();
                                sentenceList.add(new Sentence(cellText));
                            }
                        }
                    }
                }
                dataMap.put(new MapKey(tmp.trim(),index++), sentenceList);
            }
            dataMap.put(new MapKey("length",0), count);


        } catch (IOException e) {
        }
        return dataMap;
    }
}
