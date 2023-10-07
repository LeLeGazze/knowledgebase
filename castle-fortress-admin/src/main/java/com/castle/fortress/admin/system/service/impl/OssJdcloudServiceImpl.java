package com.castle.fortress.admin.system.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.service.OssService;
import com.castle.fortress.admin.utils.OssUtils;
import com.castle.fortress.admin.utils.PicUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * oss 服务实现类
 * @author castle
 */
public class OssJdcloudServiceImpl implements OssService {
    private OssPlatFormDto ossPlatFormDto;
    private AmazonS3 s3;

    public OssJdcloudServiceImpl(OssPlatFormDto ossPlatFormDto) {
        this.ossPlatFormDto = ossPlatFormDto;
        ClientConfiguration config = new ClientConfiguration();
        AwsClientBuilder.EndpointConfiguration endpointConfig =
                new AwsClientBuilder.EndpointConfiguration(this.ossPlatFormDto.getJdcloudEndpoint(), this.ossPlatFormDto.getJdcloudRegion());

        AWSCredentials awsCredentials = new BasicAWSCredentials(this.ossPlatFormDto.getJdcloudAccessKey(),this.ossPlatFormDto.getJdcloudSecretKey());
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

        this.s3 = AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfig)
                .withClientConfiguration(config)
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .build();
    }

    @Override
    public RespBody putFile(byte[] content,String fileName) {
        String file_path = OssUtils.filePathName(this.ossPlatFormDto.getJdcloudPathPrefix(),fileName);
        String contentType = FileUtils.getContentType(fileName);
        String domain = "";
        ByteArrayInputStream in=null;
        try {
            in = new ByteArrayInputStream(content);
            if(this.ossPlatFormDto.getJdcloudEndpoint().toLowerCase().indexOf("http://")!= -1){
                domain = this.ossPlatFormDto.getJdcloudEndpoint().replace("http://","http://"+this.ossPlatFormDto.getJdcloudBucket()+".");
            }else if(this.ossPlatFormDto.getJdcloudEndpoint().toLowerCase().indexOf("https://")!= -1){
                domain = this.ossPlatFormDto.getJdcloudEndpoint().replace("https://","https://"+this.ossPlatFormDto.getJdcloudBucket()+".");
            }else{
                domain="http://"+this.ossPlatFormDto.getJdcloudBucket()+"."+this.ossPlatFormDto.getJdcloudEndpoint();
            }
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentLength(in.available());
            s3.putObject(this.ossPlatFormDto.getJdcloudBucket(), file_path,in, objectMetadata);
            System.out.format("Uploading %s to OSS bucket %s...\n", file_path, this.ossPlatFormDto.getJdcloudBucket());
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("name",fileName);
        //页面访问路径
        map.put("url", domain+"/"+file_path);
        //数据库存储路径
        map.put("path", domain+"/"+file_path);
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
