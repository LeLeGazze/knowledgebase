package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicLabelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

/**
 * 标签管理表Mapper 接口
 *
 * @author
 * @since 2023-04-26
 */
@Mapper
public interface KbModelLabelMapper extends BaseMapper<KbModelLabelEntity> {

	List<KbModelLabelEntity> queryList(@Param("map") Map<String, Long> pageMap, @Param("kbModelLabelEntity") KbModelLabelEntity kbModelLabelEntity);


	List<KbModelLabelEntity> findIsExistName(@Param("names") List<String> labelName);

	List<KbModelLabelDto> listHotWord();

	List<KbModelLabelEntity> findIsExistNames(@Param("labels") List<String> label);

	List<String> findLabelByBid(@Param("bid") Long bid);

	boolean updateBatch(@Param("labels") List<KbModelLabelEntity> labels);

	List<KbModelLabelDto> selectPageLabel(@Param("kbModelLabelDto") KbModelLabelDto kbModelLabelDto, @Param("map") Map<String, Long> pageMap);

	Integer selectPageLabelCount(@Param("kbModelLabelDto") KbModelLabelDto kbModelLabelDto);

	boolean updateLabel(@Param("kbModelLabelEntity") KbModelLabelEntity kbModelLabelEntity);

	boolean removeLabels(@Param("ids") List<Long> ids);


	boolean updateByLabel(Long id);

	@Select("select *\n" +
			"from kb_model_label t1\n" +
			"         left join kb_basic_label t2 on t1.id = t2.l_id\n" +
			"where t1.is_deleted = 2\n" +
			"  and t2.b_id = #{bid}")
	List<KbModelLabelEntity> findByUserId(@Param("bid") Long bid);
}
