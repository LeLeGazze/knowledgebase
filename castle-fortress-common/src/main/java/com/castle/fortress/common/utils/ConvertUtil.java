package com.castle.fortress.common.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 转换工具类
 * @author castle
 */
public class ConvertUtil {
    /**
     * 列表转换为树类型
     * @param list
     * @param <T> 要求该对象有parentId,children成员变量
     * @return
     */
    public static <T> List<T> listToTree(List<T> list){
        List<T> resultList=new ArrayList<>();
        try {
            if(list==null || list.size()<1){
               return list;
            }
            Method getParentId = list.get(0).getClass().getDeclaredMethod("getParentId");
            Method[] methods=list.get(0).getClass().getMethods();
            Method getId =null;
            for(Method m:methods){
                if(m.getName().equals("getId")){
                    getId=m;
                    break;
                }
            }
            //未分配的对象
            List<T> unallocated=new ArrayList<>();
            for(T t:list){
                Long parentId=(Long)getParentId.invoke(t);
                Boolean hasParent=false;
                //顶级菜单
                if(parentId == null || parentId == 0){
                    resultList.add(t);
                    continue;
                }
                //有父级的对象
                for(T parentT:list){
                    Long id=(Long)getId.invoke(parentT);
                    if(parentId.equals(id)){
                        hasParent=true;
                        unallocated.add(t);
                        break;
                    }
                }
                //找不到父级的对象,作为顶级对象
                if(!hasParent){
                    resultList.add(t);
                }
            }
            if(unallocated.size()>0){
                findPosition(resultList,unallocated);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultList=list;
        }
        return resultList;
    }

    /**
     * @param parentList
     * @param children
     * @param <T>
     */
    private static <T> void findPosition(List<T> parentList,List<T> children){
        try {
            if(children!=null && children.size()>0){
                Method getParentId = children.get(0).getClass().getDeclaredMethod("getParentId");
                Method setChildren = children.get(0).getClass().getDeclaredMethod("setChildren",List.class);
                Method getChildren = children.get(0).getClass().getDeclaredMethod("getChildren");
                Method setHasChildren = null;
                Method[] methods=children.get(0).getClass().getMethods();
                Method getId =null;
                for(Method m:methods){
                    if(m.getName().equals("getId")){
                        getId=m;
                    }
                    if (m.getName().equals("setHasChildren")) {
                        setHasChildren = m;
                    }
                }
                for(T parent:parentList){
                    Long id=(Long)getId.invoke(parent);
                    List<T> childernList=getChildren.invoke(parent)==null?new ArrayList<>():(List<T>)getChildren.invoke(parent);
                    //未分配的菜单
                    List<T> unallocated=new ArrayList<>();
                    for(T son:children){
                        Long parentId=(Long)getParentId.invoke(son);
                        if(parentId.equals(id)){
                            childernList.add(son);
                            setChildren.invoke(parent,childernList);
                            if(setHasChildren!=null){
                                setHasChildren.invoke(parent,true);
                            }
                        }else{
                            unallocated.add(son);
                        }
                    }
                    children=unallocated;
                }
                for(T parent:parentList){
                    List<T> childernList=getChildren.invoke(parent)==null?new ArrayList<>():(List<T>)getChildren.invoke(parent);
                    if(childernList.size()>0){
                        findPosition(childernList,children);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象转换
     * @param original 原对象
     * @param target 目标类型
     * @param <T>
     * @return
     */
    public static <T> T transformObj(Object original, Class<T> target){
        if(original == null){
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(original, targetObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return targetObject;
    }

    /**
     * 对象列表转换
     * @param originalList 原对象列表
     * @param target 目标类型
     * @param <T>
     * @return
     */
    public static <T> List<T> transformObjList(Collection<?> originalList, Class<T> target){
        if(originalList == null){
            return null;
        }
        List targetList = new ArrayList<>(originalList.size());
        try {
            for(Object original : originalList){
                targetList.add(transformObj(original,target));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return targetList;
    }

    /**
     * 对象map转换
     * @param originalMap 原对象map
     * @param target 目标类型
     * @param <T>
     * @return
     */
    public static <T> Map<Object,T> transformObjMap(Map<Object,?> originalMap, Class<T> target){
        if(originalMap == null){
            return null;
        }
        Map<Object,T> targetMap = new HashMap<>();
        try {
            for(Object key : originalMap.keySet()){
                targetMap.put(key,transformObj(originalMap.get(key),target));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return targetMap;
    }
}
