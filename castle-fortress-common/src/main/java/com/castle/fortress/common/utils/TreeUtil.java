package com.castle.fortress.common.utils;


import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 转换工具类
 * @author castle
 */
public class TreeUtil {

    /**
     * 查询当前节点及所有子节点的集合
     * @param treeList 树形结构的列表
     * @param currentObjId 当前节点id
     * @param <T>
     * @return 对象集合
     */
    public static <T> Set<T> findSelfAndChildren(List<T> treeList,Long currentObjId){
        Set<T> resultSet= null;
        try {
            if(treeList==null || treeList.size()<1 || currentObjId == null){
               return null;
            }
            Method getChildren = treeList.get(0).getClass().getDeclaredMethod("getChildren");
            Method getId =treeList.get(0).getClass().getDeclaredMethod("getId");
            resultSet = new HashSet<>();
            for(T t:treeList){
                Long id=getId.invoke(t) == null? null: (Long)getId.invoke(t);
                List<T> children =  getChildren.invoke(t) == null? null: (List<T>)getChildren.invoke(t);
                if(currentObjId.equals(id)){
                    resultSet.add(t);
                    findAllChildren(children,resultSet);
                    break;
                }else if(children!=null && !children.isEmpty()){
                    Set<T> tempSet = findSelfAndChildren(children,currentObjId);
                    if(tempSet != null){
                        resultSet.addAll(tempSet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultSet=null;
        }
        return resultSet;
    }

    /**
     * 查询所有的子节点集合
     * @param children
     * @param <T>
     * @return
     */
    private static <T> void findAllChildren(List<T> children,Set<T> objSet){
        try {
            if(children!=null && children.size()>0){
                Method getChildren = children.get(0).getClass().getDeclaredMethod("getChildren");
                for(T childObj:children){
                    List<T> childrenList =  getChildren.invoke(childObj) == null? null: (List<T>)getChildren.invoke(childObj);
                    objSet.add(childObj);
                    if(childrenList !=null && !childrenList.isEmpty()){
                        findAllChildren(childrenList,objSet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
