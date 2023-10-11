package com.castle.fortress.admin.knowledge.utis;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
public class PdfBoxUtil {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public void watermarkPDF(String originalPath, String userName, String watermarkPath) throws Exception {
        File tmpPDF;
        PDDocument doc;
        tmpPDF = new File(watermarkPath);
        if (originalPath.startsWith("http")){
            doc= PDDocument.load(new URL(originalPath).openStream());
        }else {
            File fileStored = new File(originalPath);
            doc = PDDocument.load(fileStored);
        }

        // 加载中文字体
        InputStream chineseFontPath = this.getClass().getClassLoader().getResource("simhei.ttf").openStream();
        System.out.println(chineseFontPath);
        PDFont font = PDType0Font.load(doc, chineseFontPath);
        doc.setAllSecurityToBeRemoved(true);
        String ts = userName + simpleDateFormat.format(new Date());
        for (PDPage page : doc.getPages()) {
            PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
            float fontSize = 55.0f;
            PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
            // 透明度
            r0.setNonStrokingAlphaConstant(0.2f);
            r0.setAlphaSourceFlag(true);
            cs.setGraphicsStateParameters(r0);
            cs.setNonStrokingColor(150, 0, 0);//Red
            cs.beginText();
            cs.setFont(font, fontSize);
            // 获取旋转实例
            cs.setTextMatrix(Matrix.getRotateInstance(120, 100f, 450f));
            cs.showText(ts);
            cs.endText();
            cs.close();
        }
        log.debug("文件添加水印 " + watermarkPath + " user:" + userName);
        doc.save(tmpPDF);
    }

    public static void main(String[] args) throws Exception {
        new PdfBoxUtil().watermarkPDF("D:\\1682334609153.pdf", "超级管理员", "D:\\dfdf.pdf");
    }
}
