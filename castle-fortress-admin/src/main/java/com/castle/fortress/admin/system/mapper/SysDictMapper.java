package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.SysDictEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 系统字典
 * @author castle
 */
public interface SysDictMapper extends BaseMapper<SysDictEntity> {

    int deleteDict(@Param("id") Long id);

    void updateSubCode(@Param("sysDictParent") SysDictEntity sysDictParent);
}
