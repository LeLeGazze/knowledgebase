package com.castle.fortress.admin.utils;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;

import java.io.File;
import java.util.*;

/**
 * cms 资源管理 工具类
 * @author castle
 */
public class ResourceUtil {
	public static String DIR_TYPE= "0";
	public static String FILE_TYPE = "1";
	/**
	 * 获取指定路径下的文件列表(包含文件夹和文件)
	 * @param templateLocation 文件目录
	 * @return
	 */
	public static List<Map<String,String>> getResources(String templateLocation){
		List<Map<String,String>> list = null;
		File location = new File(templateLocation);
		if(location.exists()){
			File[] files=location.listFiles();
			if(files!=null && files.length>0){
				list = new ArrayList<>();
				for(File f:files){
					Map<String,String> map = new HashMap<>();
					map.put("fileName",f.getName());
					map.put("filePath",f.getAbsolutePath());
					map.put("directoryFlag",f.isDirectory()?DIR_TYPE:FILE_TYPE);//0 文件夹 1 文件
					list.add(map);
				}
				//文件夹排序在上面
				if(list.size()>0){
					list.sort(new Comparator<Map<String, String>>() {
						@Override
						public int compare(Map<String, String> o1, Map<String, String> o2) {
							Integer i1 = Integer.valueOf(o1.get("directoryFlag"));
							Integer i2 = Integer.valueOf(o2.get("directoryFlag"));
							return i1-i2;
						}
					});
				}
			}
		}else{
			throw new BizException(BizErrorCode.FILE_NO_EXIST_ERROR);
		}
		return list;
	}

	/**
	 * 获取模板内容
	 * @param templatePath 文件路径
	 * @return
	 */
	public static String getResourceContents(String templatePath){
		String contents = null;
		File file = new File(templatePath);
		if(file.exists()){
			if(file.isDirectory()){
				throw new BizException(BizErrorCode.FILE_TYPE_ERROR);
			}
			FileReader fileReader = new FileReader(templatePath);
			contents = fileReader.readString();
		}else{
			throw new BizException(BizErrorCode.FILE_NO_EXIST_ERROR);
		}
		return contents;
	}

	/**
	 * 重写模板内容
	 * @param templatePath 文件路径
	 * @param contents 新内容
	 * @return
	 */
	public static String coverResourceContents(String templatePath,String contents){
		File file = new File(templatePath);
		if(file.exists()){
			if(file.isDirectory()){
				throw new BizException(BizErrorCode.FILE_TYPE_ERROR);
			}
			FileWriter fileWriter = new FileWriter(templatePath);
			fileWriter.write(contents==null?"":contents);
		}else{
			throw new BizException(BizErrorCode.FILE_NO_EXIST_ERROR);
		}
		return contents;
	}

	/**
	 * 获取指定路径下的过滤文件列表(只包含文件)
	 * @param templateLocation 文件目录
	 * @param filterType 过滤类型 image 图片 video 视频
	 * @return
	 */
	public static List<Map<String,String>> filterResources(String templateLocation,String filterType){
		//获取文件列表
		List<Map<String,String>> fileList = new ArrayList<>();
		List<Map<String,String>> list = getResources(templateLocation);
		if(list==null){
			return fileList;
		}
		for(Map<String,String> m : list){
			if("1".equals(m.get("directoryFlag"))){
				fileList.add(m);
			}
		}
		if(fileList==null){
			return fileList;
		}
		List<Map<String,String>> resultList = null;
		//过滤图片
		if("image".equals(filterType)){
			resultList =new ArrayList<>();
			for(Map<String,String> m : fileList){
				String suffix= getSuffix(m.get("fileName")).toLowerCase(Locale.ROOT);
				if("jpeg".equals(suffix) || "jpg".equals(suffix) || "png".equals(suffix)){
					resultList.add(m);
				}
			}
		//过滤视频
		}else if("video".equals(filterType)){
			resultList =new ArrayList<>();
			for(Map<String,String> m : fileList){
				String suffix= getSuffix(m.get("fileName")).toLowerCase(Locale.ROOT);
				if("avi".equals(suffix) || "mp4".equals(suffix) ){
					resultList.add(m);
				}
			}
		}
		if(resultList!=null){
			fileList = resultList;
		}
		return fileList;
	}

	/**
	 * 获取指定路径下的子文件夹
	 * @param templateLocation 文件目录
	 * @return
	 */
	public static List<Map<String,String>> filterDir(String templateLocation){
		//获取文件列表
		List<Map<String,String>> fileList = new ArrayList<>();
		List<Map<String,String>> list = getResources(templateLocation);
		if(list==null){
			return fileList;
		}
		for(Map<String,String> m : list){
			if(DIR_TYPE.equals(m.get("directoryFlag"))){
				fileList.add(m);
			}
		}
		return fileList;
	}

	public static String getSuffix(String fileName){
		int i = fileName.lastIndexOf(".");
		if(i!=-1 & i<fileName.length()){
			return fileName.substring(i+1);
		}
		return "";
	}

}
