package com.castle.fortress.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.BankEntity;
import java.util.Map;
import java.util.List;
/**
 * 银行信息Mapper 接口
 *
 * @author castle
 * @since 2022-11-02
 */
public interface BankMapper extends BaseMapper<BankEntity> {

	List<BankEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("bankEntity") BankEntity bankEntity);



}
