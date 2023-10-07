package com.castle.fortress.admin.system.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.service.OssService;
import com.castle.fortress.admin.utils.OssUtils;
import com.castle.fortress.admin.utils.PicUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.FileUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * oss 服务实现类
 * @author castle
 */
public class OssTencentCloudServiceImpl implements OssService {
    private OssPlatFormDto ossPlatFormDto;
    private COSClient cosClient;

    public OssTencentCloudServiceImpl(OssPlatFormDto ossPlatFormDto) {
        this.ossPlatFormDto = ossPlatFormDto;
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台进行查看和管理
        String secretId = this.ossPlatFormDto.getCosSecretId();
        String secretKey = this.ossPlatFormDto.getCosSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(this.ossPlatFormDto.getCosRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        this.cosClient = new COSClient(cred, clientConfig);
    }
    //https://cloud.tencent.com/document/product/436/10199
    @Override
    public RespBody putFile(byte[] content,String fileName) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name",fileName);
        String filePathName = OssUtils.filePathName(this.ossPlatFormDto.getCosPathPrefix(),fileName);
        try {
            //压缩图片
            if(OssUtils.isImage(filePathName) && YesNoEnum.YES.getCode().toString().equals(this.ossPlatFormDto.getCompressFlag())){
                String urlPath = putFileWithCompress(content,filePathName);
                //页面访问路径
                map.put("url", this.ossPlatFormDto.getCosEndpoint() + "/" + urlPath);
                //数据库存储路径
                map.put("path", this.ossPlatFormDto.getCosEndpoint() + "/" + urlPath);
            }else{
                InputStream inputStream = new ByteArrayInputStream(content);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(inputStream.available());
                String bucketName = this.ossPlatFormDto.getCosBucketName() +"-"+ this.ossPlatFormDto.getCosAppId();
                PutObjectRequest request = new PutObjectRequest(bucketName, filePathName, inputStream, metadata);
                PutObjectResult result = this.cosClient.putObject(request);
                if(result.getETag() == null){
                    throw new BizException(BizErrorCode.FT_UPLOAD_ERROR);
                }
                //页面访问路径
                map.put("url", this.ossPlatFormDto.getCosEndpoint() + "/" + filePathName);
                //数据库存储路径
                map.put("path", this.ossPlatFormDto.getCosEndpoint() + "/" + filePathName);
            }
        } catch (IOException e) {
            throw new BizException(BizErrorCode.FT_UPLOAD_ERROR);
        }finally {
            if(this.cosClient!=null){
                this.cosClient.shutdown();
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
            if(FileUtils.isImage(file)){
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


    public static String buildPostObjectBody(String boundary, Map<String, String> formFields,
                                             String filename, String contentType) {
        StringBuffer stringBuffer = new StringBuffer();
        for(Map.Entry entry: formFields.entrySet()) {
            // 添加boundary行,行首以--开头
            stringBuffer.append("--").append(boundary).append("\r\n");
            // 字段名
            stringBuffer.append("Content-Disposition: form-data; name=\""
                    + entry.getKey() + "\"\r\n\r\n");
            // 字段值
            stringBuffer.append(entry.getValue() + "\r\n");
        }
        // 添加boundary行,行首以--开头
        stringBuffer.append("--").append(boundary).append("\r\n");
        // 文件名
        stringBuffer.append("Content-Disposition: form-data; name=\"file\"; "
                + "filename=\"" + filename + "\"\r\n");
        // 文件类型
        stringBuffer.append("Content-Type: " + contentType + "\r\n\r\n");
        return stringBuffer.toString();
    }

    /**
     * WebP 压缩
     * @return
     */
    private String putFileWithCompress(byte[] content,String fileName){
        String path ="/"+fileName;
        String url = "http://" + this.ossPlatFormDto.getCosBucketName()
                + "-"+this.ossPlatFormDto.getCosAppId() +".cos."+this.ossPlatFormDto.getCosRegion()+".myqcloud.com" + path;
        JSONObject rule = new JSONObject();
        rule.set("fileid", OssUtils.getRandomFileName(fileName)+".webp");
        rule.set("rule","imageMogr2/format/webp");
        JSONArray rules = new JSONArray();
        rules.put(rule);
        JSONObject operations = new JSONObject();
        operations.set("is_pic_info",1);
        operations.set("rules",rules);
        String result = HttpRequest.put(url)
                .header("Authorization",getXMlAuthorization(HttpMethodName.PUT,path))
                .header("Pic-Operations",operations.toString())
                .body(content).execute().body();
        JSONObject jsonObject = JSONUtil.xmlToJson(result);
        if(jsonObject.getJSONObject("Error") != null){
            throw new BizException(jsonObject.getJSONObject("Error").getStr("Code"));
        }
        return jsonObject.getJSONObject("UploadResult").getJSONObject("ProcessResults").getJSONObject("Object").getStr("Key");

    }

    /**
     * 获取腾讯云对象存储 COS 的 XML API签名
     * @return
     */
    public String getXMlAuthorization(HttpMethodName methodName , String resoucePath ){
        COSSigner cosSigner = new COSSigner();
        Map<String, String> headerMap   = new HashMap<>();
        Map<String, String> paramMap  = new HashMap<>();
        BasicCOSCredentials cred  = new BasicCOSCredentials(this.ossPlatFormDto.getCosSecretId(), this.ossPlatFormDto.getCosSecretKey());// 参数信息 appid等
        Date startTime  = new Date();// 签名有效开始时间
        Date expiredTime  = new Date(startTime.getTime() + 600*1000) ;// 签名有效结束时间
        String signStr =  cosSigner.buildAuthorizationStr(methodName,resoucePath,headerMap,paramMap,cred,expiredTime);
        return signStr;
    };

}
