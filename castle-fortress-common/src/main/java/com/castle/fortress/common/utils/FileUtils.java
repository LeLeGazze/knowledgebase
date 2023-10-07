package com.castle.fortress.common.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtils {
	/**
	 * InputStream转 File类型
	 * @param ins
	 * @param file
	 */
	public static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * @param file
	 */
	public static void deleteFile(File file) {
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 文件转换为字节数组
	 * @param file
	 * @return
	 */
	public static byte[] File2byte(File file) {
		byte[] buffer = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try
		{
			fis = new FileInputStream(file);
			bos= new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1)
			{
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer;
	}

		/**
		 * 判断是否是图片
		 * @param file
		 */
		public static boolean isImage(MultipartFile file) {
				String name = file.getOriginalFilename();
				if(StrUtil.isNotEmpty(name)){
						String suffix = name.substring(name.lastIndexOf(".")+1).toUpperCase();
						if("BMP".equals(suffix)||"JPG".equals(suffix)||"JPEG".equals(suffix)||"PNG".equals(suffix)){
								return true;
						}else{
								return false;
						}
				}else{
						return false;
				}
		}

		/**
		 * 通过文件名判断并获取OSS服务文件上传时文件的contentType
		 *
		 * @param fileName 文件名
		 * @return 文件的contentType
		 */
		public static String getContentType(String fileName) {
				if(fileName.lastIndexOf(".") == -1){
						return "application/octet-stream";
				}
				String FilenameExtension = fileName.substring(fileName.lastIndexOf("."));
				if (FilenameExtension.equalsIgnoreCase(".bmp")) {
						return "application/x-bmp";
				}
				if (FilenameExtension.equalsIgnoreCase(".gif")) {
						return "image/gif";
				}
				if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
								FilenameExtension.equalsIgnoreCase(".jpg") ||
								FilenameExtension.equalsIgnoreCase(".png")) {
						return "image/jpeg";
				}
				if (FilenameExtension.equalsIgnoreCase(".html")) {
						return "text/html";
				}
				if (FilenameExtension.equalsIgnoreCase(".txt")) {
						return "text/plain";
				}
				if (FilenameExtension.equalsIgnoreCase(".vsd")) {
						return "application/vnd.visio";
				}
				if (FilenameExtension.equalsIgnoreCase(".pptx") ||
								FilenameExtension.equalsIgnoreCase(".ppt")) {
						return "application/vnd.ms-powerpoint";
				}
				if (FilenameExtension.equalsIgnoreCase(".docx") ||
								FilenameExtension.equalsIgnoreCase(".doc")) {
						return "application/msword";
				}
				if (FilenameExtension.equalsIgnoreCase(".xla") ||
								FilenameExtension.equalsIgnoreCase(".xlc")||
								FilenameExtension.equalsIgnoreCase(".xlm")||
								FilenameExtension.equalsIgnoreCase(".xls")||
								FilenameExtension.equalsIgnoreCase(".xlt")||
								FilenameExtension.equalsIgnoreCase(".xlw")) {
						return "application/vnd.ms-excel";
				}
				if (FilenameExtension.equalsIgnoreCase(".xml")) {
						return "text/xml";
				}
				if (FilenameExtension.equalsIgnoreCase(".pdf")) {
						return "application/pdf";
				}
				if (FilenameExtension.equalsIgnoreCase(".zip")) {
						return "application/zip";
				}
				if (FilenameExtension.equalsIgnoreCase(".tar")) {
						return "application/x-tar";
				}
				if (FilenameExtension.equalsIgnoreCase(".avi")) {
						return "video/avi";
				}
				if (FilenameExtension.equalsIgnoreCase(".mp4")) {
						return "video/mpeg4";
				}
				if (FilenameExtension.equalsIgnoreCase(".mp3")) {
						return "audio/mp3";
				}
				if (FilenameExtension.equalsIgnoreCase(".mp2")) {
						return "audio/mp2";
				}
				return "application/octet-stream";
		}
}
