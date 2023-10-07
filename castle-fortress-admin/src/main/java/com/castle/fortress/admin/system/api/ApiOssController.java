package com.castle.fortress.admin.system.api;

import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件传输控制器 控制器
 * @author castle
 */
@Api(tags = "文件传输控制器")
@RestController
@RequestMapping("/api/oss")
public class ApiOssController {

    @Autowired
    private ConfigOssService configOssService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public RespBody<Map> upload(@RequestParam("upfile") MultipartFile file){
        if (file.isEmpty()) {
            throw new BizException(BizErrorCode.FT_UPLOAD_EMPTY_ERROR);
        }
        return configOssService.putFile(file);

    }
}
