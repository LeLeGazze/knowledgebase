package com.castle.fortress.admin.check.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;

public class PDFUtil {
    public static void main(String[] args) {
        String[] files = {"D:\\hcses\\project\\knowledge-base\\knowledge-base-admin\\kbfiles\\upload\\20230816\\7d68fd111716ae5dcac102b2b53ca192.pdf", "D:\\hcses\\project\\knowledge-base\\knowledge-base-admin\\kbfiles\\upload\\20230816\\pdf\\1692154517724.pdf"};
        String savepath = "D:\\hcses\\project\\knowledge-base\\knowledge-base-admin\\kbfiles\\upload\\20230816\\temp.pdf";
        mergePdfFiles(files, savepath);
    }


    public static boolean mergePdfFiles(String[] files, String newfile) {
        System.out.println("文件开始合并");
        HashSet<String> fileSet = new HashSet<>();
        A:
        while (true) {
            for (String filePath : files) {
                File file = new File(filePath);
                if (fileSet.size() == files.length) {
                    break A;
                }
                if (file.exists()) {
                    fileSet.add(file.getName());
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        boolean retValue = false;
        Document document = null;
        try {
            document = new Document(new PdfReader(files[0]).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
            document.open();
            for (int i = 0; i < files.length; i++) {
                PdfReader reader = new PdfReader(files[i]);
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }
            retValue = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        System.out.println("文件合并完成");
        return retValue;
    }


}
