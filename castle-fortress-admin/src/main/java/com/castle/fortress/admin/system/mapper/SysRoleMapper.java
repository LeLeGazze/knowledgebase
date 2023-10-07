package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
/**
 * 系统角色
 * @author castle
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 查询用户对应的角色
     * @param userId
     * @return
     */
    List<SysRole> queryListByUser(@Param("userId") Long userId);


    @Select("select r.*,user.real_name as createUserName\n" +
            "from castle_sys_role r left join  castle_sys_user user on user.id =r.create_user where r.id =#{id} ")
    SysRole findById(@Param("id") Long id);
}
