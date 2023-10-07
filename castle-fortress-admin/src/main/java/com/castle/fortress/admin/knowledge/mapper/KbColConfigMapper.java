package com.castle.fortress.admin.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.castle.fortress.admin.knowledge.entity.KbPropertyDesignEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * kb模型字段配置表Mapper 接口
 *
 * @author sunhr
 * @since 2023-04-19
 */
@Mapper
public interface KbColConfigMapper extends BaseMapper<KbPropertyDesignEntity> {

	/**
	 * 获取所有的表名
	 *
	 * @return
	 */
	List<String> allTableNames();

	/**
	 * 创建表
	 *
	 * @param tbName 表名
	 * @param cols   字段
	 * @return
	 */
	boolean createTable(@Param("tbName") String tbName,@Param("tbComment") String tbComment, @Param("cols") List<KbPropertyDesignEntity> cols);
	boolean createHistoryTable(@Param("tbName") String tbName,@Param("tbComment") String tbComment, @Param("cols") List<KbPropertyDesignEntity> cols);

	/**
	 * 删除表结构
	 *
	 * @param tbName
	 * @return
	 */
	boolean dropTable(@Param("tbName") String tbName);

	/**
	 * 增加表字段
	 *
	 * @param tbName 表名
	 * @param cols   字段
	 * @return
	 */
	boolean alterTableAdd(@Param("tbName") String tbName, @Param("cols") List<KbPropertyDesignEntity> cols);

	/**
	 * 修改表字段
	 *
	 * @param tbName 表名
	 * @param cols   字段
	 * @return
	 */
	boolean alterTableModify(@Param("tbName") String tbName, @Param("cols") List<KbPropertyDesignEntity> cols);

	/**
	 * 删除表字段
	 *
	 * @param tbName 表名
	 * @param cols   字段
	 * @return
	 */
	boolean alterTableDrop(@Param("tbName") String tbName, @Param("cols") List<KbPropertyDesignEntity> cols);

	void alterTable(@Param("tbName") String s, @Param("propName") String propName);

    void removeAll(@Param("modelId") Long id);

    /**
	 * 查询栏目对应的扩展字段信息
	 * @param channelId
	 * @return
	 */
//    List<KbPropertyDesignEntity> listByChannel(@Param("channelId")Long channelId);
}
