package com.castle.fortress.admin.system.service;

import com.castle.fortress.common.entity.RespBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * oss 文件存储
 * @author castle
 */
public interface OssService {

    /**
     * 文件上传
     * @param content 字节流
     * @return
     */
    /**
     *
     * @param content
     * @return
     */
    RespBody putFile(byte[] content,String fileName);

    /**
     * 文件上传
     * @param file 文件
     * @return
     */
    RespBody putFile(File file);

    /**
     * 文件上传
     * @param file 文件
     * @return
     */
    RespBody putFile(MultipartFile file);

}
