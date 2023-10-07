package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbWarehouseAuthDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * 主题知识仓库权限表Mapper 接口
 *
 * @author
 * @since 2023-04-24
 */
public interface KbWarehouseAuthMapper extends BaseMapper<KbWarehouseAuthEntity> {

    List<KbWarehouseAuthEntity> queryList(@Param("map") Map<String, Long> pageMap, @Param("kbWarehouseAuthEntity") KbWarehouseAuthEntity kbWarehouseAuthEntity);


    void updateByWhId(@Param("wid") Long wid);


    List<KbWarehouseAuthDto> findByWhId(@Param("wid") Long wid);

    void deleteHyWhid(@Param("whId")Long whId);
    void deleteWhid(@Param("whId")Long whId);

    ArrayList<KbWarehouseAuthEntity> findByUid(@Param("uid") Long uid,@Param("cateGory") String  cateGory,@Param("swId")Long swId);


    ArrayList<KbWarehouseAuthEntity> findByUidAndSwid(@Param("uid") Long uid,@Param("swId") Long swId, @Param("cateGory") String cateGory);


    List<KbWarehouseAuthDto> findByUidWarehouseAuth(@Param("uid") Long uid, @Param("whId") Long wh_id,@Param("wcId") Long wc_id);

    List<Long> findByUidCategory(@Param("uid") Long uid,@Param("kb_auths") List<Integer> kb_auths);

    List<KbWarehouseAuthDto> findBySwIdAuth(@Param("swId") Long swId,@Param("category") int category);

    ArrayList<KbWarehouseAuthEntity> findByUidVideo(@Param("uid") Long uid,@Param("category") String category);
}
