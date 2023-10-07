package com.castle.fortress.admin.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.utils.ReflectUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * excel 工具类
 * @author castle
 */
public class ExcelUtils {
    /**
     * 将数据导出excel
     * @param response
     * @param fileName 文件名称（带文件类型后缀），为空时赋值为当前日志.xlsx
     * @param sheetName sheet名，为空时赋值为Sheet
     * @param list excel数据列表
     * @param excelClass excel对象类
     * @param toExcelClassFlag 是否转换为excel对象标识 true转换为excelClass对应的集合，false 则直接将list数据导出
     * @throws IOException
     */
    public static void export(HttpServletResponse response, String fileName, String sheetName, List list,
                              Class excelClass,Boolean toExcelClassFlag) throws IOException {
        if(StrUtil.isEmpty(fileName)){
            fileName = DateUtil.formatDate(new Date())+".xlsx";
        }
        if(StrUtil.isEmpty(sheetName)){
            sheetName = "Sheet";
        }
        List excelList = null;
        if(toExcelClassFlag){
            excelList = ConvertUtil.transformObjList(list,excelClass);
        }else{
            excelList = list;
        }
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("filename",fileName);
        //   Student.class 是按导出类  data()应为数据库查询数据，这里只是模拟
        EasyExcel.write(response.getOutputStream(), excelClass).sheet(sheetName).doWrite(excelList);
    }


    public static void exportDynamic(HttpServletResponse response, String fileName, String sheetName, List list,List<Map<String,String>> headerList,List<List<Object>> dataList) throws IOException {
        if(CommonUtil.verifyParamNull(fileName,headerList)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        sheetName = StrUtil.isEmpty(sheetName)?"sheet":sheetName;
        ExcelWriter excelWriter = null;
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("filename",fileName);
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheetBasicInfo = EasyExcel.writerSheet(0, sheetName).head(headInfo(headerList)).build();
            List<List<Object>> dataBasicInfo =  dataList==null ? dataListInfo(list,headerList):dataList;
            excelWriter.write(dataBasicInfo, writeSheetBasicInfo);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    private static List<List<String>> headInfo(List<Map<String,String>> headerList) {
        List<List<String>> list = new ArrayList<List<String>>();
        for(Map map:headerList){
            List<String> head = new ArrayList<String>();
            head.add(map.get("name").toString());
            list.add(head);
        }
        return list;
    }

    private static List<List<Object>> dataListInfo(List list,List<Map<String,String>> headerList)  {
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        String methodName = "";
        Class c = null;
        for(Object obj:list){
            c = obj.getClass();
            List<Object> data = new ArrayList<Object>();
            for(Map map:headerList){
                methodName = "get"+StrUtil.upperFirst(map.get("code").toString());
                Object d = ReflectUtil.getMethod(c,obj,methodName);
                data.add(d==null?"":d);
            }
            dataList.add(data);
        }
        return dataList;
    }

    public static List<List<Object>> convertToDataList(List list, List<Map<String,String>> headerList, Map<String,Map<String,Object>> convertMap, Set<String> checkColumnSet)  {
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        String methodName = "";
        String[] s=null;
        Class c = null;
        for(Object obj:list){
            c = obj.getClass();
            List<Object> data = new ArrayList<Object>();
            for(Map map:headerList){
                methodName = "get"+StrUtil.upperFirst(map.get("code").toString());
                Object d = ReflectUtil.getMethod(c,obj,methodName);
                //需要转换
                if(d!=null && convertMap!=null && convertMap.get(map.get("code").toString())!=null){
                    Map<String,Object> translateMap = convertMap.get(map.get("code").toString());
                    //该字段是复选字段
                    if(checkColumnSet!=null&&checkColumnSet.contains(map.get("code").toString())){
                        s = d.toString().split(";");
                        StringBuilder sb = new StringBuilder();
                        for(String s1:s){
                            sb.append(translateMap.get(s1.toString())==null?s1:translateMap.get(s1.toString())+";");
                        }
                        d = sb.length()>1?sb.toString().substring(0,sb.length()-1):d;
                    }else{
                        d = translateMap.get(d.toString())==null?d:translateMap.get(d.toString());
                    }
                }
                data.add(d==null?"":d);
            }
            dataList.add(data);
        }
        return dataList;
    }

    /**
     *
     * @param response
     * @param fileName
     * @param sheetName
     * @param list
     * @param headerList
     * @param dataList
     */
    public static void exportFormDynamic(HttpServletResponse response, String fileName, String sheetName, List<Map<String, Object>> list, List<Map<String, String>> headerList, List<List<Object>> dataList) throws IOException{
        if(CommonUtil.verifyParamNull(fileName,headerList)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        sheetName = StrUtil.isEmpty(sheetName)?"sheet":sheetName;
        ExcelWriter excelWriter = null;
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setHeader("filename",fileName);
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheetBasicInfo = EasyExcel.writerSheet(0, sheetName).head(headInfo(headerList)).build();
            List<List<Object>> dataBasicInfo =  formDataListInfo(list,headerList);
            excelWriter.write(dataBasicInfo, writeSheetBasicInfo);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    private static List<List<Object>> formDataListInfo(List<Map<String, Object>> list, List<Map<String, String>> headerList) {
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        for(Map<String, Object> m:list){
            List<Object> data = new ArrayList<Object>();
            for(Map map:headerList){
                data.add(m.get(map.get("code").toString())==null?"":m.get(map.get("code").toString()));
            }
            dataList.add(data);
        }
        return dataList;
    }
}
