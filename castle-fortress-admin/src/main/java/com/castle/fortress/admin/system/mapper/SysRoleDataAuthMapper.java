package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.SysRoleDataAuthEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色数据权限表-细化到部门Mapper 接口
 *
 * @author castle
 * @since 2021-01-04
 */
public interface SysRoleDataAuthMapper extends BaseMapper<SysRoleDataAuthEntity> {
    /**
     * 查询指定用户的部门数据权限
     * @param userId
     * @return
     */
    List<Long> getAuthDeptByUserId(@Param("userId") Long userId);

    void delByRoleId(@Param("roleId")Long roleId);
}

