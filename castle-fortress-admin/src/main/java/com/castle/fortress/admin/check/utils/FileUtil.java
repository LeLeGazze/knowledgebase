package com.castle.fortress.admin.check.utils;

import com.castle.fortress.admin.check.entity.RestData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    public static String sourceFile() throws IOException {
        File file = new File("D:\\查重源文件");
        StringBuffer buffer = new StringBuffer();
        File[] files = file.listFiles();
        for (File file1 : files) {
            List<String> strings = Files.readAllLines(Paths.get(file1.getPath()));
            String join = StringUtils.join(strings, "\n");
            buffer.append(join);
        }
        return buffer.toString();
    }

    public static String file() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("D:\\Java 简介.txt"));
        String join = StringUtils.join(strings, "\n");
        return join;
    }

    public static void outPutFileHtml(RestData restData, String outputPath) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(FileUtil.class, "/templates");
        Template template = cfg.getTemplate("checkTable.ftl");
        // 创建数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("restData", restData);
        // 向模板应用数据模型并执行合并
        StringWriter out = new StringWriter();
        template.process(dataModel, out);
        String output = out.toString();
        FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
        fileOutputStream.write(output.getBytes());
        fileOutputStream.close();
    }
}
