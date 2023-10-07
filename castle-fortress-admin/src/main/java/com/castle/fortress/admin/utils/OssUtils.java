package com.castle.fortress.admin.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

public class OssUtils {

    /**
     * 获取文件的路径及名称
     * @param pathPrefix 路径前缀
     * @param fileName 文件名称
     * @return
     */
    public static String filePathName(String pathPrefix,String fileName){
        if(StrUtil.isEmpty(fileName)){
            throw new BizException(BizErrorCode.FT_UPLOAD_ERROR);
        }
        Integer suffixIndex = fileName.lastIndexOf(".");
        String suffix = "";
        if(suffixIndex!= -1){
            //获取文件的后缀名 .jpg
            suffix = fileName.substring(suffixIndex);
        }
        String prefixPath ="";
        if(StrUtil.isNotEmpty(pathPrefix)){
            if(pathPrefix.lastIndexOf("/") != (pathPrefix.length()-1)){
                prefixPath = pathPrefix+"/";
            }else{
                prefixPath = pathPrefix;
            }
        }
        String filePath = DateUtil.format(new Date(),"YYYYMMdd")+"/"+System.currentTimeMillis()+suffix;
        return prefixPath+filePath;

    }


    /**
     * 依据文件路径获取随机文件名
     * @param filePathName
     * @return
     */
    public static String getRandomFileName(String filePathName){
        int biasIndex = filePathName.lastIndexOf("/");
        int dotIndex = filePathName.lastIndexOf(".");
        if(dotIndex != -1){
            return filePathName.substring(biasIndex+1,dotIndex);
        }else{
            return filePathName.substring(biasIndex+1);
        }
    }

    /**
     * 替换后缀名
     * @param filePathName
     * @param suffix 以点开头
     * @return
     */
    public static String replaceSuffix(String filePathName, String suffix){
        int dotIndex = filePathName.lastIndexOf(".");
        if(dotIndex != -1){
            return filePathName.substring(0,dotIndex)+suffix;
        }else{
            return filePathName+suffix;
        }

    }

    public static boolean isImage(String filePathName){
        Integer suffixIndex = filePathName.lastIndexOf(".");
        String suffix = "";
        if(suffixIndex!= -1){
            //获取文件的后缀名 .jpg
            suffix = filePathName.substring(suffixIndex+1);
        }
        if(StrUtil.isNotEmpty(suffix)){
            String low = suffix.toLowerCase(Locale.ROOT);
            if(low.equals("jpg") || low.equals("png") || low.equals("jpeg") || low.equals("bmp") || low.equals("gif")
                    || low.equals("tpg") || low.equals("heif") || low.equals("avif")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    public static MultipartFile transferToM(File file){
        FileItem fileItem = new DiskFileItemFactory().createItem("file", MediaType.MULTIPART_FORM_DATA_VALUE,true,file.getName());
        try{
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            org.apache.tomcat.util.http.fileupload.IOUtils.copy(input,os);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid file:"+e,e);
        }
        return  new CommonsMultipartFile(fileItem);
    }
}
