package com.castle.fortress.admin.knowledge.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 表模型管理Mapper 接口
 *
 * @author castle
 * @since 2021-11-10
 */
@Mapper
public interface KbModelDataMapper {

    /**
     * 存储表数据
     * @param tbName
     * @param propName
     * @param propValue
     * @return
     */
    boolean saveData(@Param("tbName") String tbName, @Param("propName")List<String> propName,@Param("propValue")List<Object> propValue);
    /**
     * 修改数据
     * @param tbName
     * @param id 记录id
     * @param dataMap
     * @return
     */
    boolean updateData(@Param("tbName")String tbName,@Param("id") String id,@Param("dataMap") List<Map<String,Object>> dataMap);

    /**
     * 查询指定表的指定id数据
     * @param tbName
     * @param id
     * @return
     */
	  Map<String, Object> queryById(@Param("tbName")String tbName, @Param("id")String id);

    /**
     * 删除指定表的记录
     * @param tbName
     * @param ids
     * @return
     */
    boolean removeByIds(@Param("tbName")String tbName,@Param("ids") List<Long> ids);

    /**
     * 校验字段数据是否重复
     * @param tbName 表名
     * @param colName 字段名
     * @param colValue 字段值
     * @return
     */
    List<Long> queryIdsRepeat(@Param("tbName")String tbName, @Param("colName")String colName, @Param("colValue")Object colValue);

    Map<String, Object> queryByDataId(@Param("tbName") String code, @Param("id")Long id);

    List<Map<String, String>> selectComment(@Param("tbName")String tbName);

    int deleteByDId(@Param("tableName") String tableName,@Param("dId") Long id);

    int deleteByBIdHis(@Param("tableName") String tableName,@Param("dId") Long id);
}

