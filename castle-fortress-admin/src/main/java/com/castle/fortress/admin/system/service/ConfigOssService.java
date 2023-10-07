package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.ConfigOssDto;
import com.castle.fortress.admin.system.entity.ConfigOssEntity;
import com.castle.fortress.common.entity.RespBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 文件传输配置服务类
 * @author castle
 */
public interface ConfigOssService extends IService<ConfigOssEntity> {

    /**
     * 分页查询文件传输配置
     * @param page
     * @param ftDto
     * @return
     */
    IPage<ConfigOssDto> pageConfigFt(Page<ConfigOssDto> page, ConfigOssDto ftDto);

    List<ConfigOssDto> selectBySelective(ConfigOssDto ftDto);

    /**
     * 将所有的配置状态置为不启用
     */
    void updataStatusNo();

    /**
     * 文件上传
     * @param content 字节流
     * @return
     */
    RespBody putFile(byte[] content);

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
    /**
     * 文件上传加自动识别
     * @param file 文件
     */
    RespBody putFileRead(MultipartFile file);

    String getFilePathPrefix();
}
