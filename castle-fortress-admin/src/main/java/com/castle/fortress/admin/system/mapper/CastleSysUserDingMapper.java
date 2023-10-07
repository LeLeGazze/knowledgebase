package com.castle.fortress.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.CastleSysUserDingEntity;
import java.util.Map;
import java.util.List;
/**
 * 用户钉钉信息表Mapper 接口
 *
 * @author Mgg
 * @since 2022-12-13
 */
public interface CastleSysUserDingMapper extends BaseMapper<CastleSysUserDingEntity> {

	List<CastleSysUserDingEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("castleSysUserDingEntity") CastleSysUserDingEntity castleSysUserDingEntity);



}
