package com.castle.fortress.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.InstructMenuEntity;
import java.util.Map;
import java.util.List;
/**
 * 菜单指令配置表Mapper 接口
 *
 * @author castle
 * @since 2022-08-24
 */
public interface InstructMenuMapper extends BaseMapper<InstructMenuEntity> {

	List<InstructMenuEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("instructMenuEntity") InstructMenuEntity instructMenuEntity);



}
