package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberGoodsCartEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 会员商品购物车Mapper 接口
 *
 * @author Mgg
 * @since 2021-12-03
 */
public interface MemberGoodsCartMapper extends BaseMapper<MemberGoodsCartEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param memberGoodsCartEntity
    * @return
    */
    List<MemberGoodsCartEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("memberGoodsCartEntity") MemberGoodsCartEntity memberGoodsCartEntity);

    /**
    * 扩展信息记录总数
    * @param memberGoodsCartEntity
    * @return
    */
    Long extendsCount(@Param("memberGoodsCartEntity") MemberGoodsCartEntity memberGoodsCartEntity);

    /**
    * 会员商品购物车扩展详情
    * @param id 会员商品购物车id
    * @return
    */
    MemberGoodsCartEntity getByIdExtends(@Param("id")Long id);



}

