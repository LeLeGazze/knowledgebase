package com.castle.fortress.admin.check.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.castle.fortress.admin.check.entity.MapKey;
import com.castle.fortress.admin.check.entity.Sentence;
import com.castle.fortress.admin.check.service.FileReadService;
import com.castle.fortress.admin.check.utils.IKUtil;
import com.castle.fortress.admin.es.EsFileDto;
import com.castle.fortress.admin.utils.FortressParseUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class DefaultFileReadServiceImpl implements FileReadService {
    @Override
    public HashMap<MapKey, Object> fileDataHandle(String filePath, int readDataLength) {
        FortressParseUtil fortressParseUtil = new FortressParseUtil();
        try {
            HashMap<MapKey, Object> stringObjectHashMap = fortressParseUtil.parserFileMap(filePath, readDataLength);
            return stringObjectHashMap;
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  synchronized void fileContrastDataHandle(String path, HashMap<String, Map<String, Set<Sentence>>> map, EsFileDto esFileDto, String s) {
        File file = new File(path);
        if (!file.exists() || map.containsKey(SecureUtil.md5(file))) {
            return;
        }
        FortressParseUtil fortressParseUtil = new FortressParseUtil();
        Parser parser = new AutoDetectParser(fortressParseUtil.getConfig());
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream(file);
            parser.parse(inputstream, handler, metadata, pcontext);
            String content = "";
            if (handler != null) {
                content = handler.toString();
            }
            HashMap<String, Set<Sentence>> result = new HashMap<>();
            String[] splitLineData = content.split("\\n");
            for (String line : splitLineData) {
                if (StrUtil.isEmpty(line) || line.length() < 3) continue;
                // 进行分词
                List<String> wordList = IKUtil.divideText(line);
                for (String word : wordList) {
                    Sentence sentence = new Sentence(word);
                    sentence.setDate(DateUtil.formatDate(esFileDto.getFileDate()));
                    sentence.setTitle(esFileDto.getTitle());
                    sentence.setLength(esFileDto.getLength());
                    sentence.setFileName(esFileDto.getFileName());
                    // 添加数据
                    result.computeIfAbsent(word, k -> new HashSet<>()).add(sentence);
                }
            }
            map.put(SecureUtil.md5(file), result);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }
}
