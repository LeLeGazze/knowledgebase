package com.castle.fortress.admin.utils;

import com.castle.fortress.admin.check.entity.MapKey;
import com.castle.fortress.admin.check.entity.Sentence;
import com.castle.fortress.admin.knowledge.utis.FileUtil;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FortressParseUtil {
    private TikaConfig config;

    public FortressParseUtil() {
        InputStream configStream = this.getClass().getClassLoader().getResourceAsStream("tika-config.xml");
        try {
            this.config = new TikaConfig(configStream);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public TikaConfig getConfig() {
        return config;
    }

    public static void main(String[] args) throws Exception {

//        String fileName = "D:\\明资料\\测试文档\\十四届全国人大一次会议选举产生第十四届全国人大常委会副委员长、秘书长_凤凰网财经_凤凰网.html";
//        String fileName = "D:\\明资料\\测试文档\\2022中国商用服务机器人行业简析-甲子光年-2022.11-32页.pdf";
//        String fileName = "D:\\明资料\\测试文档\\小程序部署方案v1.1.docx";
//        String fileName = "D:\\明资料\\测试文档\\系列产品版本及价格.xlsx";
//        String fileName = "D:\\明资料\\测试文档\\竞聘报告v1.1(1).pptx";
//        String fileName = "D:\\明资料\\测试文档\\pom.xml";
//        String fileName = "D:\\明资料\\测试文档\\123.doc";
//        String fileName = "D:\\明资料\\测试文档\\测试文档.zip";
        String fileName = "https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695882391916.pdf";
        FortressParseUtil fortressParseUtil = new FortressParseUtil();
        String s = fortressParseUtil.parserFile(fileName);
        System.out.println("s = " + s);
        System.out.println("s = " + s.length());

    }

    public List<String> parserFileList(String fileName) throws TikaException, IOException, SAXException {
        Parser parser = new AutoDetectParser(config);
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        FileInputStream inputstream = new FileInputStream(new File(fileName));
        parser.parse(inputstream, handler, metadata, pcontext);
        String content = "";
        if (handler != null) {
            content = handler.toString();
        }
        String[] split = content.split("\\n");
        return Arrays.asList(split);
    }

    public HashMap<MapKey, Object> parserFileMap(String fileName, int readDataLength) throws TikaException, IOException, SAXException {
        HashMap<MapKey, Object> hashMap = new HashMap<>();
        Parser parser = new AutoDetectParser(config);
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        FileInputStream inputstream = new FileInputStream(new File(fileName));
        parser.parse(inputstream, handler, metadata, pcontext);
        String content = "";
        if (handler != null) {
            content = handler.toString();
        }

        String[] split = content.split("\\n");
        List<Sentence> sentenceList = Arrays.asList(split).stream().filter(item -> item.length() > readDataLength).map(item -> new Sentence(item)).collect(Collectors.toList());
        hashMap.put(new MapKey(content.substring(0, 4), 1), sentenceList);
        hashMap.put(new MapKey("length", 0), content.length());
        return hashMap;
    }

    public String parserFile(String fileName) throws TikaException, IOException, SAXException {
        Parser parser = new AutoDetectParser(config);
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        FileInputStream inputstream = null;
        if (fileName.startsWith("http")) {
            try {
                inputstream= FileUtil.convertToFileInputStream( new URL(fileName).openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            inputstream= new FileInputStream(new File(fileName));
        }
        parser.parse(inputstream, handler, metadata, pcontext);
        String content = "";
        if (handler != null) {
            content = handler.toString();
        }
        //剔除空格 回车 制表符
        content = content.replaceAll("\\n", "").replaceAll("\\t", "");
//        System.out.println("Contents of the document:" + content);

//        System.out.println("Metadata of the document:");
//        String[] metadataNames = metadata.names();
//        for(String name : metadataNames) {
//            System.out.println(name + ": " + metadata.get(name));
//        }
        return content;
    }


}
