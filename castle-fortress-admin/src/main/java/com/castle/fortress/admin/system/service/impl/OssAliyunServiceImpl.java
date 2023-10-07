package com.castle.fortress.admin.system.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.service.OssService;
import com.castle.fortress.admin.utils.OssUtils;
import com.castle.fortress.admin.utils.PicUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * oss 服务实现类
 * @author castle
 */
public class OssAliyunServiceImpl implements OssService {
    private OssPlatFormDto ossPlatFormDto;
    private OSS ossClient;

    public OssAliyunServiceImpl(OssPlatFormDto ossPlatFormDto) {
        this.ossPlatFormDto = ossPlatFormDto;
        this.ossClient = new OSSClientBuilder().build(this.ossPlatFormDto.getAliyunEndpoint(), this.ossPlatFormDto.getAliyunAccessKey(), this.ossPlatFormDto.getAliyunSecretKey());
    }

    @Override
    public RespBody putFile(byte[] content,String fileName) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name",fileName);
        String filePathName = OssUtils.filePathName(this.ossPlatFormDto.getAliyunPathPrefix(),fileName);
        String domain = "";
//        if(this.ossPlatFormDto.getAliyunEndpoint().toLowerCase().indexOf("http://")!= -1){
//            domain = this.ossPlatFormDto.getAliyunEndpoint().replace("http://","http://"+this.ossPlatFormDto.getAliyunBucket()+".");
//        }else if(this.ossPlatFormDto.getAliyunEndpoint().toLowerCase().indexOf("https://")!= -1){
//            domain = this.ossPlatFormDto.getAliyunEndpoint().replace("https://","https://"+this.ossPlatFormDto.getAliyunBucket()+".");
//        }else{
//            domain="http://"+this.ossPlatFormDto.getAliyunBucket()+"."+this.ossPlatFormDto.getAliyunEndpoint();
//        }
        domain = "https://" + this.ossPlatFormDto.getAliyunDomain();
        try {
            //压缩图片
            if(OssUtils.isImage(filePathName) && YesNoEnum.YES.getCode().toString().equals(this.ossPlatFormDto.getCompressFlag())){
                // 将图片转换为webp格式
                filePathName = OssUtils.replaceSuffix(filePathName,".webp");
                PutObjectRequest request = new PutObjectRequest(this.ossPlatFormDto.getAliyunBucket(), filePathName, new ByteArrayInputStream(content));
                request.setProcess("image/resize,w_100/format,webp");
                ossClient.putObject(request);
            }else{
                // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
                ossClient.putObject(this.ossPlatFormDto.getAliyunBucket(), filePathName, new ByteArrayInputStream(content));
            }
            //页面访问路径
            map.put("url", domain+"/"+filePathName);
            //数据库存储路径
            map.put("path", domain+"/"+filePathName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(BizErrorCode.FT_UPLOAD_ERROR);
        } finally {
            if(ossClient!=null){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        return RespBody.data(map);
    }

    @Override
    public RespBody putFile(File file) {

        RespBody rb=null;
        String originalName=file.getName();
        try {
            byte[] bytes= FileUtils.File2byte(file);
            rb = putFile(bytes,originalName);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBody.fail(BizErrorCode.FT_UPLOAD_ERROR);
        }
        return rb;
    }

    @Override
    public RespBody putFile(MultipartFile file) {
        RespBody rb=null;
        String originalName=file.getOriginalFilename();
        try {
            originalName= new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes= null;
            if(com.castle.fortress.common.utils.FileUtils.isImage(file)){
                bytes = PicUtil.compressPic(file,12,350);
            }else{
                bytes = file.getBytes();
            }
            rb = putFile(bytes,originalName);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBody.fail(BizErrorCode.FT_UPLOAD_ERROR);
        }
        return rb;
    }

}
