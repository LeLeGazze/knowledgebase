package com.castle.fortress.admin.member.mapper;

import com.castle.fortress.admin.member.dto.FieldTypeMapDto;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberExtendFieldConfigEntity;

import java.util.*;

/**
 * 用户扩展字段配置表Mapper 接口
 *
 * @author whc
 * @since 2022-11-23
 */
public interface MemberExtendFieldConfigMapper extends BaseMapper<MemberExtendFieldConfigEntity> {

    List<MemberExtendFieldConfigEntity> queryList(@Param("map") Map<String, Long> pageMap, @Param("memberExtendFieldConfigEntity") MemberExtendFieldConfigEntity memberExtendFieldConfigEntity);

    /**
     * 查询所有的表名
     *
     * @return
     */
    List<String> allTableNames();

    /**
     * 根据表明查询所有的字段名
     *
     * @param tableName
     * @return
     */
    List<String> allFieldNames(@Param("tableName") String tableName);

    /**
     * 根据表名查询  列名 name=>数据类型type 结构
     *
     * @param tableName
     * @return
     */
    List<FieldTypeMapDto> allFieldMap(@Param("tableName") String tableName);

    /**
     * 根据设置新增 member_extend_field 表
     *
     * @param fieldConfigs
     */
    void createTable(@Param("fields") List<MemberExtendFieldConfigEntity> fieldConfigs);

    /**
     * 根据设置新增字段 到 member_extend_field 表
     *
     * @param fieldConfig
     * @param tableName
     */
    void addField(@Param("field") MemberExtendFieldConfigEntity fieldConfig, @Param("tableName") String tableName);

    /**
     * 根据表名 列名 查询 字段名以及类型
     *
     * @param colName
     * @param tableName
     * @return
     */
    FieldTypeMapDto getField(@Param("colName") String colName, @Param("tableName") String tableName);

    /**
     * 删除字段
     *
     * @param colName
     * @param tableName
     */
    void deleteField(@Param("colName") String colName, @Param("tableName") String tableName);

    HashMap<String, String> getRow(@Param("colName") String colName, @Param("memberId") Long memberId);

    /**
     * 根据memberId 查询是否已经存在扩展记录行
     * @param memberId
     * @return 返回 行 id
     */
    Long existsByMemberId(@Param("memberId") Long memberId);


    /**
     * 根据memberId 创建一行扩展信息
     * @param memberId
     */
    void createByMemberId(@Param("memberId") Long memberId);


    /**
     * 根据memberId 修改扩展字段
     * @param memberId
     * @param params
     */
    void updateByMemberId(@Param("memberId") Long memberId, @Param("params") HashMap<String, String> params);

}
