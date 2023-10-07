package com.castle.fortress.admin.knowledge.service;

import org.lionsoul.jcseg.segmenter.SegmenterConfig;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 智能分词、关键字提取等服务层
 * @author  dawn
 */
public interface IJcsegService {
    /**
     * 初始化配置文件
     * @return
     */
    public SegmenterConfig initConfig();

    /**
     * 关键字提取
     * @param contents
     * @return
     */
    public List<String> keywordsExtractor(SegmenterConfig config,String contents);

    /**
     * 关键字提取
     * @param contents
     * @param keywordsNum
     * @return
     */
    public List<String> keywordsExtractor(SegmenterConfig config,String contents,int keywordsNum);

    public ArrayList<HashMap<String, Object>>  getKeywordsWeight(Reader reader) throws IOException;
}
