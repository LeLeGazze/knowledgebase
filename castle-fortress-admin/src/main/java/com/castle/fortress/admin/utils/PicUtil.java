package com.castle.fortress.admin.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * 照片压缩工具类~
 */
public class PicUtil {
    /**
     *
     * @param imageUrl
     * @return
     * @throws IOException
     */
    public static byte[] getByteByPic(String imageUrl) throws IOException{
        File imageFile = new File(imageUrl);
        InputStream inStream = new FileInputStream(imageFile);
        BufferedInputStream bis = new BufferedInputStream(inStream);
        BufferedImage bm = ImageIO.read(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String type = imageUrl.substring(imageUrl.length() - 3);
        ImageIO.write(bm, type, bos);
        bos.flush();
        byte[] data = bos.toByteArray();
        return data;
    }

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] getByteByFile(File file) throws IOException{
        if(file == null){
            return null;
        }
        InputStream inStream = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(inStream);
        BufferedImage bm = ImageIO.read(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String type = file.getName().substring(file.getName().lastIndexOf(".") + 1).toUpperCase();
        ImageIO.write(bm, type, bos);
        bos.flush();
        byte[] data = bos.toByteArray();
        return data;
    }
    /**
     * 将图片压缩到指定大小以内
     *
     * @param srcImgData 源图片数据
     * @param maxSize 目的图片大小
     * @return 压缩后的图片数据
     */
    public static byte[] compressUnderSize(byte[] srcImgData, long maxSize) {
        double scale = 0.9;
        byte[] imgData = Arrays.copyOf(srcImgData, srcImgData.length);

        if (imgData.length > maxSize) {
            do {
                try {
                    imgData = compress(imgData, scale);

                } catch (IOException e) {
                    throw new IllegalStateException("压缩图片过程中出错，请及时联系管理员！", e);
                }

            } while (imgData.length > maxSize);
        }

        return imgData;
    }

    /**
     * 按照 宽高 比例压缩
     *
     * @param scale 压缩刻度
     * @return 压缩后图片数据
     * @throws IOException 压缩图片过程中出错
     */
    public static byte[] compress(byte[] srcImgData, double scale) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
        int width = (int) (bi.getWidth() * scale); // 源图宽度
        int height = (int) (bi.getHeight() * scale); // 源图高度

        Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ImageIO.write(tag, "JPEG", bOut);

        return bOut.toByteArray();
    }

    //byte数组到图片
    public static void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) {
            return;
        }
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
//            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
//            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    /**
     * MultipartFile 转为 File 类型
     * @param multipartFile
     * @return
     */
    public static File transferToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file=File.createTempFile("xxx"+filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     *  将图片压缩到目标值
     * @param picFile 图片文件 只支持 PNG、JPG、JPEG
     * @param threshold 门槛值，大于等于该数值则进行压缩 单位K
     * @param maxSize 目标图片的上限值 单位K
     * @return 文件字节流
     * @throws Exception
     */
    public static byte[] compressPic(File picFile,double threshold,long maxSize) throws Exception {
        if(picFile == null){
            throw new NullPointerException();
        }
        //将照片转为byte[]
        FileInputStream fis  = new FileInputStream(picFile);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        while((len = fis.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        byte[] fileByte = bos.toByteArray();
        //转换为kb
        double fileSize = picFile.length() / 1024.0;
        //文件大于标准值
        if(fileSize >= threshold){
            maxSize = maxSize * 1024;
            fileByte = compressUnderSize(fileByte,maxSize);
        }
        return fileByte;
    }

    /**
     * 将图片压缩到目标值
     * @param multipartFile  图片文件 只支持 PNG、JPG、JPEG
     * @param threshold 门槛值，大于等于该数值则进行压缩 单位K
     * @param maxSize 目标图片的上限值 单位K
     * @return 文件字节流
     * @throws Exception
     */
    public static byte[] compressPic(MultipartFile multipartFile,double threshold,long maxSize)throws Exception {
        if (multipartFile == null) {
            throw new NullPointerException();
        }
        File picFile = transferToFile(multipartFile);
        return compressPic(picFile,threshold,maxSize);
    }

}

