package com.castle.fortress.admin.knowledge.service.impl;

import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbBasicLogEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicLogDto;
import com.castle.fortress.admin.knowledge.mapper.KbBasicLogMapper;
import com.castle.fortress.admin.knowledge.service.KbBasicLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 知识移动日志表 服务实现类
 *
 * @author sunhr
 * @since 2023-06-02
 */
@Slf4j
@Service
public class KbBasicLogServiceImpl extends ServiceImpl<KbBasicLogMapper, KbBasicLogEntity> implements KbBasicLogService {
    @Autowired
    private KbBasicLogMapper kbBasicLogMapper;

    /**
     * 获取查询条件
     *
     * @param kbBasicLogDto
     * @return
     */
    public QueryWrapper<KbBasicLogEntity> getWrapper(KbBasicLogDto kbBasicLogDto) {
        QueryWrapper<KbBasicLogEntity> wrapper = new QueryWrapper();
        if (kbBasicLogDto != null) {
            KbBasicLogEntity kbBasicLogEntity = ConvertUtil.transformObj(kbBasicLogDto, KbBasicLogEntity.class);
            wrapper.like(kbBasicLogEntity.getId() != null, "id", kbBasicLogEntity.getId());
            wrapper.like(kbBasicLogEntity.getBasicId() != null, "basic_id", kbBasicLogEntity.getBasicId());
            wrapper.like(kbBasicLogEntity.getOldCategory() != null, "old_category", kbBasicLogEntity.getOldCategory());
            wrapper.like(kbBasicLogEntity.getNewCategory() != null, "new_category", kbBasicLogEntity.getNewCategory());
            wrapper.like(kbBasicLogEntity.getCreateUser() != null, "create_user", kbBasicLogEntity.getCreateUser());
            wrapper.like(kbBasicLogEntity.getType() != null, "type", kbBasicLogEntity.getType());
            wrapper.like(kbBasicLogEntity.getCreateTime() != null, "create_time", kbBasicLogEntity.getCreateTime());
        }
        return wrapper;
    }


    @Override
    public IPage<KbModelTransmitDto> pageKbBasicLog(Page<KbBasicLogDto> page, KbBaseShowDto kbBaseShowDto, SysUser sysUser) throws ParseException {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        //日期格式转换
        if (kbBaseShowDto.getFromTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(kbBaseShowDto.getFromTime());
                String format = simpleDateFormat.format(parse);
                kbBaseShowDto.setFromTime(format);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        Long userId = sysUser.getId();
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            userId = null;
        }
        if (kbBaseShowDto.getType() == 1) {

            List<KbModelTransmitDto> kbBaiscLogEntities = kbBasicLogMapper.selectLog(pageMap, kbBaseShowDto,userId);
            Integer total = kbBasicLogMapper.selectLogCount(kbBaseShowDto,userId);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(kbBaiscLogEntities);
            return pageDto;
        } else {
            List<KbModelTransmitDto> kbBaiscLogEntities = kbBasicLogMapper.selectLogVideo(pageMap, kbBaseShowDto,userId);
            Integer total = kbBasicLogMapper.selectLogVideoCount(kbBaseShowDto,userId);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(kbBaiscLogEntities);
            return pageDto;
        }
    }


    @Override
    public List<KbBasicLogDto> listKbBasicLog(KbBasicLogDto kbBasicLogDto) {
        QueryWrapper<KbBasicLogEntity> wrapper = getWrapper(kbBasicLogDto);
        List<KbBasicLogEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbBasicLogDto.class);
    }
}

