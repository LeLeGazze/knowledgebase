package com.castle.pdftools.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 依赖libreoffice实现PDF转化
 *
 * @author Dawn
 */
@Component
public class PdfUtils {


    /**
     * @param filePath     原文件路径 本地路径
     * @param targetFolder 目标文件夹
     */
    public void toPdf(String filePath, String targetFolder) {
        long start = System.currentTimeMillis();
        String srcPath = filePath, desPath = targetFolder;
        System.out.println("源文件：" + filePath);
        System.out.println("目标文件夹：" + targetFolder);
        String command = "";
        String osName = System.getProperty("os.name");
        System.out.println("系统名称：" + osName);
        if (osName.contains("Windows")) {
            command = "soffice --headless --convert-to pdf " + srcPath + " --outdir " + desPath;
            System.out.println(command);
            boolean b = windowExec(command);
            System.out.println("res:" + b);
        } else {
            String path = desPath + srcPath.substring(srcPath.lastIndexOf("/"));
//            File file = new File(path);
//            if (file.exists()) file.delete();
            command = "libreoffice7.4 --convert-to pdf:writer_pdf_Export " + srcPath + " --outdir " + desPath;
            LinuxExec(command);
        }
        long end = System.currentTimeMillis();
        System.out.println("转换文件耗时：" + (end - start) + "毫秒");
    }


    /**
     * 获取pdf文件的访问URL
     *
     * @param fileUrl
     * @return
     */
    public String getPDFUrl(String fileUrl) {
        int dotIndex = fileUrl.lastIndexOf(".");
        return fileUrl.substring(0, dotIndex) + ".pdf";
    }

    /**
     * 获取png文件的访问URL
     *
     * @param fileUrl
     * @return
     */
    public List<String> getPngUrl(String fileUrl, String accessPath, int pages) {
        if (pages < 1) {
            return null;
        }
//        originalFilePath
        String substring = fileUrl.substring(0, fileUrl.lastIndexOf("upload") - 1);
        int dotIndex = fileUrl.lastIndexOf(".");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            String url = fileUrl.substring(0, dotIndex) + "_" + (i + 1) + ".png";
            list.add(url.replace(substring, accessPath));
        }
        return list;
    }

    /**
     * 获取文件名 不带后缀
     *
     * @param fileUrl
     * @return
     */
    public static String fileName(String fileUrl) {
        int dotIndex = fileUrl.lastIndexOf(".");
        int lastSeparator = fileUrl.lastIndexOf(File.separator);
        if (lastSeparator == -1) {
            return fileUrl.substring(0, dotIndex);
        } else {
            return fileUrl.substring(lastSeparator + 1, dotIndex);
        }
    }

    private boolean windowExec(String command) {
        Process process;// Process可以控制该子进程的执行或获取该子进程的信息
        try {
            process = Runtime.getRuntime().exec(command);// exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
            // 下面两个可以获取输入输出流
            InputStream errorStream = process.getErrorStream();
            InputStream inputStream = process.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        int exitStatus = 0;
        try {
            exitStatus = process.waitFor();// 等待子进程完成再往下执行，返回值是子线程执行完毕的返回值,返回0表示正常结束
            // 第二种接受返回值的方法
            int i = process.exitValue(); // 接收执行完毕的返回值
            if (i != 0) {
                //重试
                windowExec(command);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        process.destroy(); // 销毁子进程
        process = null;
        return true;
    }

    private void LinuxExec(String cmd) {
        System.out.println(cmd);
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            exec.waitFor();
            int i = exec.exitValue();
            if (i != 0) {
                //重试
                System.out.println("转换异常了 正在重新转换 转换结果" + i);
                LinuxExec(cmd);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    /***
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath pdf文件路径 物理路径
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢 dpi为96,100,105,120,150,200中,105显示效果较为清晰,体积稳定,dpi越高图片体积越大,一般电脑显示分辨率为96
     * @return
     */
    public int pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
        int pages = 1;
        long start = System.currentTimeMillis();
        PDDocument pdDocument;
        try {
            System.out.println("PdfFilePath = " + PdfFilePath);
            File file = new File(PdfFilePath);
            int count = 10;
            while (!file.exists() && System.currentTimeMillis() - start < 600000) {
                System.out.println("文件未生成，等待1秒");
                Thread.sleep(5000);
            }
            pdDocument = PDDocument.load(file);
            int dot = PdfFilePath.lastIndexOf('.');
            int lastSep = PdfFilePath.lastIndexOf("/") + 1;
            String imagePDFName = PdfFilePath.substring(lastSep, dot); // 获取图片文件名
            File file1 = new File(dstImgFolder);
            if (!file1.isDirectory()) {
                file1.mkdir();
            }
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* dpi越大转换后越清晰，相对转换速度越慢 */
            pages = pdDocument.getNumberOfPages();
            StringBuffer imgFilePath = null;
            for (int i = 0; i < pages; i++) {
                String imgFilePathPrefix = dstImgFolder + "/" + imagePDFName;
                imgFilePath = new StringBuffer();
                imgFilePath.append(imgFilePathPrefix);
                imgFilePath.append("_");
                imgFilePath.append(String.valueOf(i + 1));
                imgFilePath.append(".png");
                File dstFile = new File(imgFilePath.toString());
                BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                ImageIO.write(image, "png", dstFile);
            }
            System.out.println("PDF文档转PNG图片成功！");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return pages;
    }
}
