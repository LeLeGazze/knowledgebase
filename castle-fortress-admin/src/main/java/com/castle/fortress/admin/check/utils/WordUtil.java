package com.castle.fortress.admin.check.utils;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.check.entity.ParseData;
import com.castle.fortress.admin.check.entity.RestDataList;
import com.castle.fortress.admin.check.entity.TmpParseData;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.bouncycastle.pqc.jcajce.provider.Kyber;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;


public class WordUtil {
    private static String ColorRead = "FF0000";
    private static String ColorBlack = "000000";

    public static void wordHighlighting(List<RestDataList> restDataLists, String inputPath, String outPutPath) {
        HashMap<String, ArrayList<ParseData>> resMap = new HashMap<String, ArrayList<ParseData>>();
        for (RestDataList restDataList : restDataLists) {
            for (TmpParseData tmpParseData : restDataList.getOriginal()) {
                for (ParseData parseDatum : tmpParseData.getParseData()) {
                    resMap.computeIfAbsent(parseDatum.getRawText(), k -> new ArrayList()).add(parseDatum);
                }
            }
        }
        try {

            FileInputStream fis = new FileInputStream(inputPath);
            XWPFDocument document = new XWPFDocument(fis);
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText();
                if (StrUtil.isEmpty(text) || text.length() < 3) continue;
                boolean contains = resMap.keySet().contains(text.toLowerCase());
                if (contains) {
                    for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
                        paragraph.removeRun(i);
                    }
                    ArrayList<ParseData> list = resMap.get(text.toLowerCase());
                    for (int i = list.size() - 1; i >= 0; i--) {
                        ParseData parseData = list.get(i);
                        if (StrUtil.isNotEmpty(parseData.getAfterTest())) {
                            XWPFRun newRun = paragraph.insertNewRun(0);
                            newRun.setText(parseData.getAfterTest());
                            newRun.setColor(ColorBlack);
                        }
                        if (parseData.getText().contains("<font color=red>")) {
                            XWPFRun newRun = paragraph.insertNewRun(0);
                            newRun.setText(parseData.getText().replace("<font color=red>", "").replace("</font>", ""));
                            newRun.setColor(ColorRead);
                        } else {
                            XWPFRun newRun = paragraph.insertNewRun(0);
                            newRun.setText(parseData.getText());
                            newRun.setColor(ColorBlack);
                        }
                        if (StrUtil.isNotEmpty(parseData.getBeforeText())) {
                            XWPFRun newRun = paragraph.insertNewRun(0);
                            newRun.setText(parseData.getBeforeText());
                            newRun.setColor(ColorBlack);
                        }
                    }

                }
            }

            // 处理表格
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        String cellText = cell.getText();
                        if (StrUtil.isEmpty(cellText) || cellText.length() < 3) continue;
                        boolean contains = resMap.keySet().contains(cellText.toLowerCase());
                        if (contains) {
                            cell.getParagraphs().forEach(cellParagraph -> {
                                for (int i = cellParagraph.getRuns().size() - 1; i >= 0; i--) {
                                    cellParagraph.removeRun(i);
                                }
                                ArrayList<ParseData> list = resMap.get(cellText.toLowerCase());
                                for (int i = list.size() - 1; i >= 0; i--) {
                                    ParseData parseData = list.get(i);
                                    if (StrUtil.isNotEmpty(parseData.getAfterTest())) {
                                        XWPFRun newRun = cellParagraph.insertNewRun(0);
                                        newRun.setText(parseData.getAfterTest());
                                        newRun.setColor(ColorBlack);
                                    }
                                    if (parseData.getText().contains("<font color=red>")) {
                                        XWPFRun newRun = cellParagraph.insertNewRun(0);
                                        newRun.setText(parseData.getText().replace("<font color=red>", "").replace("</font>", ""));
                                        newRun.setColor(ColorRead);
                                    } else {
                                        XWPFRun newRun = cellParagraph.insertNewRun(0);
                                        newRun.setText(parseData.getText());
                                        newRun.setColor(ColorBlack);
                                    }
                                    if (StrUtil.isNotEmpty(parseData.getBeforeText())) {
                                        XWPFRun newRun = cellParagraph.insertNewRun(0);
                                        newRun.setText(parseData.getBeforeText());
                                        newRun.setColor(ColorBlack);
                                    }
                                }
                            });
                        }
                    }
                }
            }

            // 保存修改后的文档
            FileOutputStream fos = new FileOutputStream(outPutPath);
            document.write(fos);
            document.close();
            fos.close();
            System.out.println("文本替换成功！" + outPutPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发生异常了！" + e);
        }
    }
}
