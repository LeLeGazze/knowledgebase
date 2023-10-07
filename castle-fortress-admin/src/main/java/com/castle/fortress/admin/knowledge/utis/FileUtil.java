package com.castle.fortress.admin.knowledge.utis;

import cn.hutool.crypto.SecureUtil;
import com.castle.fortress.admin.knowledge.entity.KbVideVersionEntity;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.apache.commons.io.input.NullInputStream;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FileUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static String formatSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static String getWatermarkPath(String originalPath, String userName) {
        String format = simpleDateFormat.format(new Date());
        String outPutWatermark = SecureUtil.md5(originalPath + userName + format);
        String osName = System.getProperty("os.name");
        String tmpFilePath = "";
        tmpFilePath = originalPath.substring(0, (originalPath.lastIndexOf("/") + 1));
//        if (osName.contains("Windows")) {
//
//        } else {
//            tmpFilePath = originalPath.substring(0, (originalPath.lastIndexOf("/") + 1));
//        }
        return tmpFilePath + outPutWatermark + ".pdf";
    }

    public static boolean isFileExist(String filePath) {
        return new File(filePath).exists();
    }

    public static String getFileName(String filePath) {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            return filePath.substring((filePath.lastIndexOf("\\") + 1));
        } else {
            return filePath.substring((filePath.lastIndexOf("/") + 1));
        }

    }

    public static void downloadFile(HttpServletResponse response, List<String> fileListPath) {
        if (response == null) {
            System.out.println("response 不允许为空");
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        if (fileListPath == null || fileListPath.size() == 0) return;

        if (fileListPath.size() == 1) {
            // 只有一个文件 直接读取返回即可
            String filePath = fileListPath.get(0);
            response.reset();
            response.setContentType("application/octet-stream;charset=utf-8");
            String fileName = getFileName(filePath);
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setHeader("suffix", fileName.substring(fileName.lastIndexOf(".")+1));
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                int len;
                byte[] buff = new byte[1024];
                while ((len = bis.read(buff)) > 0) {
                    bos.write(buff, 0, len);
                }
                bis.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.debug("文件读取失败 error{} , filePath:{}", e.toString(), filePath);
            }
        }
        try {
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("suffix","zip");
            response.setHeader("Content-disposition", "attachment; filename=" + "ss.zip");
            // 使用多线程读取文件
            ExecutorService executorService = Executors.newFixedThreadPool(fileListPath.size());
            ParallelScatterZipCreator parallelScatterZipCreator = new ParallelScatterZipCreator(executorService);
            ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(response.getOutputStream());
            zipArchiveOutputStream.setEncoding("UTF-8");
            for (String filePath : fileListPath) {
                FileInputStream in = new FileInputStream(filePath);
                final InputStreamSupplier inputStreamSupplier = () -> {
                    try {
                        return in;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new NullInputStream(0);
                    }
                };
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(getFileName(filePath));
                zipArchiveEntry.setMethod(ZipArchiveEntry.DEFLATED);
                zipArchiveEntry.setSize(in.available());
                zipArchiveEntry.setUnixMode(UnixStat.FILE_FLAG | 436);
                parallelScatterZipCreator.addArchiveEntry(zipArchiveEntry, inputStreamSupplier);

            }

            parallelScatterZipCreator.writeTo(zipArchiveOutputStream);
            zipArchiveOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("文件读取失败 error{} , filePath:{}", e.toString(), fileListPath);
        }
    }

    public static void main(String[] args) {
        System.out.println(suffixFromFileName("D:\\hcses\\project\\knowledge-base\\knowledge-base-admin\\kbfiles\\test.pdf"));
        System.out.println(getFileName("D:\\hcses\\project\\knowledge-base\\knowledge-base-admin\\kbfiles\\upload/20230718/达州城市通APP项目设计方案.pdf"));
        System.out.println(getWatermarkPath("D:\\hcses\\project\\knowledge-base\\knowledge-base-admin\\kbfiles\\upload/20230718/达州城市通APP项目设计方案.pdf","张三"));
//        getWatermarkPath("D:\\hcses\\project\\knowledge-base\\knowledge-base-admin\\kbfiles\\test.pdf", "1686822525984");
    }

    public static String suffixFromFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}