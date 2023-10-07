package com.castle.fortress.admin.system.mapper;

import com.castle.fortress.admin.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.CastleSysUserWeComEntity;
import java.util.Map;
import java.util.List;
/**
 * 用户企业微信信息表Mapper 接口
 *
 * @author mjj
 * @since 2022-11-30
 */
public interface CastleSysUserWeComMapper extends BaseMapper<CastleSysUserWeComEntity> {

	List<CastleSysUserWeComEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("castleSysUserWeComEntity") CastleSysUserWeComEntity castleSysUserWeComEntity);

    /**
     * 删除所有企业微信信息
     */
    int removeAll();

    /**
     * 根据userId 查询
     * @param id
     * @return
     */
    CastleSysUserWeComEntity selectByUserId(Long id);
}
