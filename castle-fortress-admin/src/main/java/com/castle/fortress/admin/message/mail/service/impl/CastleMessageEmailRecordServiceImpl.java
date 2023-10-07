package com.castle.fortress.admin.message.mail.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.message.mail.dto.CastleMessageEmailRecordDto;
import com.castle.fortress.admin.message.mail.entity.CastleMessageEmailRecordEntity;
import com.castle.fortress.admin.message.mail.mapper.CastleMessageEmailRecordMapper;
import com.castle.fortress.admin.message.mail.service.CastleMessageEmailRecordService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 邮件发送记录表 服务实现类
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Service
public class CastleMessageEmailRecordServiceImpl extends ServiceImpl<CastleMessageEmailRecordMapper, CastleMessageEmailRecordEntity> implements CastleMessageEmailRecordService {
    /**
    * 获取查询条件
    * @param castleMessageEmailRecordDto
    * @return
    */
    public QueryWrapper<CastleMessageEmailRecordEntity> getWrapper(CastleMessageEmailRecordDto castleMessageEmailRecordDto){
        QueryWrapper<CastleMessageEmailRecordEntity> wrapper= new QueryWrapper();
        if(castleMessageEmailRecordDto != null){
            CastleMessageEmailRecordEntity castleMessageEmailRecordEntity = ConvertUtil.transformObj(castleMessageEmailRecordDto,CastleMessageEmailRecordEntity.class);
            wrapper.like(StrUtil.isNotEmpty(castleMessageEmailRecordEntity.getEmailTitle()),"email_title",castleMessageEmailRecordEntity.getEmailTitle());
            wrapper.like(StrUtil.isNotEmpty(castleMessageEmailRecordEntity.getEmailBody()),"email_body",castleMessageEmailRecordEntity.getEmailBody());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<CastleMessageEmailRecordDto> pageCastleMessageEmailRecord(Page<CastleMessageEmailRecordDto> page, CastleMessageEmailRecordDto castleMessageEmailRecordDto) {
        QueryWrapper<CastleMessageEmailRecordEntity> wrapper = getWrapper(castleMessageEmailRecordDto);
        Page<CastleMessageEmailRecordEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleMessageEmailRecordEntity> castleMessageEmailRecordPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleMessageEmailRecordDto> pageDto = new Page(castleMessageEmailRecordPage.getCurrent(), castleMessageEmailRecordPage.getSize(),castleMessageEmailRecordPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleMessageEmailRecordPage.getRecords(),CastleMessageEmailRecordDto.class));
        return pageDto;
    }

    @Override
    public IPage<CastleMessageEmailRecordDto> pageCastleMessageEmailRecordExtends(Page<CastleMessageEmailRecordDto> page, CastleMessageEmailRecordDto castleMessageEmailRecordDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        CastleMessageEmailRecordEntity castleMessageEmailRecordEntity = ConvertUtil.transformObj(castleMessageEmailRecordDto,CastleMessageEmailRecordEntity.class);
        List<CastleMessageEmailRecordEntity> castleMessageEmailRecordList=baseMapper.extendsList(pageMap,castleMessageEmailRecordEntity);
        Long total = baseMapper.extendsCount(castleMessageEmailRecordEntity);
        Page<CastleMessageEmailRecordDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(castleMessageEmailRecordList,CastleMessageEmailRecordDto.class));
        return pageDto;
    }
    @Override
    public CastleMessageEmailRecordDto getByIdExtends(Long id){
        CastleMessageEmailRecordEntity  castleMessageEmailRecordEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(castleMessageEmailRecordEntity,CastleMessageEmailRecordDto.class);
    }

    @Override
    public List<CastleMessageEmailRecordDto> listCastleMessageEmailRecord(CastleMessageEmailRecordDto castleMessageEmailRecordDto){
        QueryWrapper<CastleMessageEmailRecordEntity> wrapper = getWrapper(castleMessageEmailRecordDto);
        List<CastleMessageEmailRecordEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleMessageEmailRecordDto.class);
    }
}

