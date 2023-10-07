package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.knowledge.service.IJcsegService;
import com.castle.fortress.admin.utils.FortressParseUtil;
import org.apache.tika.exception.TikaException;
import org.lionsoul.jcseg.ISegment;
import org.lionsoul.jcseg.IWord;
import org.lionsoul.jcseg.dic.ADictionary;
import org.lionsoul.jcseg.dic.DictionaryFactory;
import org.lionsoul.jcseg.extractor.impl.TextRankKeywordsExtractor;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 智能分词、关键字提取等服务层实现
 *
 * @author dawn
 */

@Service
public class JcsegServiceImpl extends TextRankKeywordsExtractor implements IJcsegService {

    public JcsegServiceImpl(ISegment seg) {
        super(seg);
    }

    public JcsegServiceImpl() {
        super(null);

    }

    public static void main(String[] args) throws TikaException, IOException, SAXException {
        //Identifications
        //1500 1102
        long start = System.currentTimeMillis();
//        System.out.println(new JcsegServiceImpl().getClass().getClassLoader().getResource("").getPath());
        String contents = "<div class=\"wzcon j-fontContent clearfix\">\n" +
                " <p align=\"\" style=\"text-indent: 2em; text-align: justify;\">全基因组关联研究已经确定了与炎症性肠病有关的风险位点。工业化国家炎症性肠病的发病率逐渐增长，迁入疾病高发区的移民患病风险增加，表明环境因素也是决定炎症性肠病易感性和患病程度的重要因素。由于缺乏系统的调查平台，与炎症性肠病相关的环境因素及影响疾病的机制研究一直未深入开展。美国哈佛医学院研究团队揭示促进肠道炎症的环境因素。该成果于近日发表在《Nature》杂志上，题为：Identification of environmental factors that promote intestinal inflammation。</p> \n" +
                " <p align=\"\" style=\"text-indent: 2em; text-align: justify;\">研究人员利用一种综合的系统方法，结合公开的数据库、斑马鱼的化学筛选、机器学习和小鼠临床前模型来确定影响肠道炎症的环境因素。通过研究，他们确定了除草剂戊炔草胺会增加小肠和大肠的炎症风险。此外，结果显示戊炔草胺作用于T细胞和树突状细胞，通过芳香烃受体（AHR）信号轴，促进肠道炎症反应发生。</p> \n" +
                " <p align=\"\" style=\"text-indent: 2em; text-align: justify;\">综上，研究人员开发了一个方法，用于识别炎症性肠病以及潜在的其他炎症性疾病的环境因素和发病机制。</p> \n" +
                " <p align=\"\" style=\"text-indent: 2em; text-align: justify;\">论文链接：</p> \n" +
                " <p align=\"\" style=\"text-indent: 2em; text-align: justify;\">https://www.nature.com/articles/s41586-022-05308-6</p> \n" +
                " <p align=\"\" style=\"text-indent: 2em; text-align: justify;\">注：此研究成果摘自《Nature》杂志，文章内容不代表本网站观点和立场，仅供参考。</p>\n" +
                "</div>";

        String fileName = "C:\\Users\\hcses\\Downloads\\【烈士纪念日】沿着英烈足迹 凝聚奋进力量.docx";
        FortressParseUtil fortressParseUtil = new FortressParseUtil();
        contents = fortressParseUtil.parserFile(fileName);


//        contents = "identification";
//        contents +=contents;
//        contents +=contents;
//        contents +=contents;
//        contents +=contents;
//        contents +=contents;
//        contents +=contents;//96000 10  1740
        System.out.println(contents);
        System.out.println("正文长度:" + contents.length());

        IJcsegService service = new JcsegServiceImpl(null);
        SegmenterConfig config = service.initConfig();
        List<String> keywords = new JcsegServiceImpl(null).keywordsExtractor(config, contents);
        for (String s : keywords) {
            System.out.println(s);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
//        String[] poss= {""};
//        System.out.println(poss.length);
//        System.out.print(poss[0]);
//        System.out.print(poss[0].length());
//        System.out.print(poss[0].charAt(0));
    }

    @Override
    public SegmenterConfig initConfig() {
        //1, 创建Jcseg ISegment分词对象
        SegmenterConfig config = new SegmenterConfig(true);
        //  config.setClearStopwords(false);     //设置关闭过滤停止词
        config.setAppendCJKSyn(false);      //设置关闭同义词追加
        config.setKeepUnregWords(false);    //设置去除不识别的词条
        config.setClearStopwords(true);
        String osName = System.getProperty("os.name");
        if (!osName.contains("Windows")) {
            String[] lexiconPaths = {"/www/wwwroot/zsk.chinahcses.top/jar/lexicon"};
//            String[] lexiconPaths = {"/www/wwwroot/stms.crssg.com/jar/lexicon"};
            System.out.println("字典路径:" + lexiconPaths[0]);
            config.setLexiconPath(lexiconPaths);
        }

        return config;
    }

    @Override
    public List<String> keywordsExtractor(SegmenterConfig config, String contents) {
        return keywordsExtractor(config, contents, 10);
    }

    @Override
    public List<String> keywordsExtractor(SegmenterConfig config, String contents, int keywordsNum) {
        if (StrUtil.isEmpty(contents)) {
            return new ArrayList<>();
        }

        //默认提取10个关键字
        if (keywordsNum < 1) {
            keywordsNum = 10;
        }

//        long start = System.currentTimeMillis();
        ADictionary dic = DictionaryFactory.createSingletonDictionary(config, true);
//        try {
//            dic.load(new File("D:\\hcses\\project\\international-web\\international-web\\ruoyi-modules\\ruoyi-interweb\\src\\main\\resources\\lex-fortress.lex"));
//            dic.isSync();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        ISegment seg = ISegment.COMPLEX.factory.create(config, dic);
        //2, 构建TextRankKeywordsExtractor关键字提取器
        TextRankKeywordsExtractor extractor = new TextRankKeywordsExtractor(seg);
        extractor.setMaxIterateNum(100);        //设置pagerank算法最大迭代次数，非必须，使用默认即可
        extractor.setWindowSize(5);             //设置textRank计算窗口大小，非必须，使用默认即可
        extractor.setKeywordsNum(keywordsNum);           //设置最大返回的关键词个数，默认为10

        //3, 从一个输入reader输入流中获取关键字
        List<String> keywords = null;
        try {

            keywords = extractor.getKeywords(new StringReader(contents));
//            System.out.println("内部耗时："+(System.currentTimeMillis()-start));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //4, output:
        //"分词","方法","分为","标注","相结合","字符串","匹配","过程","大类","单纯"
        return keywords;
    }

    @Override
    public ArrayList<HashMap<String, Object>> getKeywordsWeight(Reader reader) throws IOException {
        SegmenterConfig segmenterConfig = this.initConfig();
        ADictionary dic = DictionaryFactory.createSingletonDictionary(segmenterConfig, true);
        ISegment seg = ISegment.COMPLEX.factory.create(segmenterConfig, dic);
        super.setMaxIterateNum(100);
        super.setWindowSize(5);
        super.setKeywordsNum(50);
        final Map<String, List<String>> winMap = new HashMap<>();
        final List<String> words = new ArrayList<>();
        ArrayList<HashMap<String, Object>> keywordMap = new ArrayList<>();

        //document segment
        IWord w = null;
        seg.reset(reader);
        while ((w = seg.next()) != null) {
            if (!filter(w)) {
                continue;
            }

            String word = w.getValue();
            if (!winMap.containsKey(word)) {
                winMap.put(word, new LinkedList<String>());
            }

            words.add(word);
        }

        //count the neighbour
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            List<String> support = winMap.get(word);

            int sIdx = Math.max(0, i - windowSize);
            int eIdx = Math.min(i + windowSize, words.size() - 1);

            for (int j = sIdx; j <= eIdx; j++) {
                if (j == i) continue;
                support.add(words.get(j));
            }
        }

        // the page rank scores calculation
        final HashMap<String, Float> score = new HashMap<>();
        for (int c = 0; c < maxIterateNum; c++) {
            for (Map.Entry<String, List<String>> entry : winMap.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();

                float sigema = 0F;
                for (String ele : value) {
                    int size = winMap.get(ele).size();
                    if (ele.equals(key) || size == 0) {
                        continue;
                    }

                    float Sy = 0;
                    if (score.containsKey(ele)) {
                        Sy = score.get(ele);
                    }

                    sigema += Sy / size;
                }

                score.put(key, 1 - D + D * sigema);
            }
        }

        //sort the items by score
        final List<Map.Entry<String, Float>> entryList = new ArrayList<>(score.entrySet());
        entryList.sort(new Comparator<Map.Entry<String, Float>>() {
            @Override
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        float tScores = 0F, avgScores = 0F, stdScores = 0F;
        for (Map.Entry<String, Float> entry : entryList) {
            tScores += entry.getValue();
            //System.out.println(entry.getKey()+"="+entry.getValue());
        }

        avgScores = tScores / words.size();
        stdScores = avgScores * (1 + D);

        //return the sublist as the final result
        int len = Math.min(keywordsNum, entryList.size());
        final List<String> keywords = new ArrayList<String>(len);
        for (int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, Float> e = entryList.get(i);
            if (i >= len) break;
            if (autoFilter && e.getValue() < stdScores) break;
            HashMap<String, Object> map = new HashMap<>();
            map.put("word",e.getKey() );
            map.put("weight", e.getValue() );
            keywordMap.add(map);
        }

        //let gc do its work
        winMap.clear();
        words.clear();
        return keywordMap;
    }
}
