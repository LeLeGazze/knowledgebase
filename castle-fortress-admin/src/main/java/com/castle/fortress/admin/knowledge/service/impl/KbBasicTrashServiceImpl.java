package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbBasicTrashEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicTrashDto;
import com.castle.fortress.admin.knowledge.mapper.KbBasicTrashMapper;
import com.castle.fortress.admin.knowledge.service.KbBasicTrashService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 知识回收表 服务实现类
 *
 * @author 
 * @since 2023-06-01
 */
@Service
public class KbBasicTrashServiceImpl extends ServiceImpl<KbBasicTrashMapper, KbBasicTrashEntity> implements KbBasicTrashService {
    /**
    * 获取查询条件
    * @param kbBasicTrashDto
    * @return
    */
    public QueryWrapper<KbBasicTrashEntity> getWrapper(KbBasicTrashDto kbBasicTrashDto){
        QueryWrapper<KbBasicTrashEntity> wrapper= new QueryWrapper();
        if(kbBasicTrashDto != null){
            KbBasicTrashEntity kbBasicTrashEntity = ConvertUtil.transformObj(kbBasicTrashDto,KbBasicTrashEntity.class);
            wrapper.like(kbBasicTrashEntity.getId() != null,"id",kbBasicTrashEntity.getId());
            wrapper.like(kbBasicTrashEntity.getBasicId() != null,"basic_id",kbBasicTrashEntity.getBasicId());
            wrapper.like(kbBasicTrashEntity.getUserId() != null,"user_id",kbBasicTrashEntity.getUserId());
            wrapper.like(kbBasicTrashEntity.getType() != null,"type",kbBasicTrashEntity.getType());
            wrapper.like(kbBasicTrashEntity.getCreateTime() != null,"create_time",kbBasicTrashEntity.getCreateTime());
        }
        return wrapper;
    }


    @Override
    public IPage<KbModelTransmitDto> pageKbBasicTrashAdmin(Page<KbBasicTrashDto> page, KbBaseShowDto kbBaseShowDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        if (kbBaseShowDto.getCategoryIds() == null || kbBaseShowDto.getCategoryIds().size()==0){
            kbBaseShowDto.setCategoryIds(null);
        }
        if(kbBaseShowDto.getFromTime()!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(kbBaseShowDto.getFromTime());
                String format = simpleDateFormat.format(parse);
                kbBaseShowDto.setFromTime(format);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        if (kbBaseShowDto.getType()==1) {
            List<KbModelTransmitDto> kbBasicTrashPage=baseMapper.selectTrashAdmin(pageMap,kbBaseShowDto);
            Integer total = baseMapper.selectTrashCountAdmin(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(kbBasicTrashPage);
            return pageDto;
        }else {
            List<KbModelTransmitDto> kbBasicTrashPage=baseMapper.selectTrashVideoAdmin(pageMap,kbBaseShowDto);
            Integer total = baseMapper.selectTrashVideoAdminCount(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(kbBasicTrashPage);
            return pageDto;
        }

    }


    @Override
    public List<KbBasicTrashDto> listKbBasicTrash(KbBasicTrashDto kbBasicTrashDto){
        QueryWrapper<KbBasicTrashEntity> wrapper = getWrapper(kbBasicTrashDto);
        List<KbBasicTrashEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbBasicTrashDto.class);
    }

    @Override
    public IPage<KbModelTransmitDto> pageKbBasicTrash(Page<KbBasicTrashDto> page, KbBaseShowDto kbBaseShowDto, Long uid) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        if(kbBaseShowDto.getCategoryIds()==null || kbBaseShowDto.getCategoryIds().size()==0){
            kbBaseShowDto.setCategoryIds(null);
        }
        if (kbBaseShowDto.getType()==1) {
            List<KbModelTransmitDto> kbBasicTrashPage=baseMapper.selectTrash(pageMap,kbBaseShowDto,uid);
            Integer total = baseMapper.selectTrashCount(kbBaseShowDto,uid);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(kbBasicTrashPage);
            return pageDto;
        }else {
            List<KbModelTransmitDto> kbBasicTrashPage=baseMapper.selectTrashVideo(pageMap,kbBaseShowDto,uid);
            Integer total = baseMapper.selectTrashVideoCount(kbBaseShowDto,uid);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(kbBasicTrashPage);
            return pageDto;
        }
    }
}

