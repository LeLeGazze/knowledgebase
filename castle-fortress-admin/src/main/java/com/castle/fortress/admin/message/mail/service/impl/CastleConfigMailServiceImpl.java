package com.castle.fortress.admin.message.mail.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.message.mail.dto.CastleConfigMailDto;
import com.castle.fortress.admin.message.mail.entity.CastleConfigMailEntity;
import com.castle.fortress.admin.message.mail.mapper.CastleConfigMailMapper;
import com.castle.fortress.admin.message.mail.service.CastleConfigMailService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 邮件配置表 服务实现类
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Service
public class CastleConfigMailServiceImpl extends ServiceImpl<CastleConfigMailMapper, CastleConfigMailEntity> implements CastleConfigMailService {
    /**
    * 获取查询条件
    * @param castleConfigMailDto
    * @return
    */
    public QueryWrapper<CastleConfigMailEntity> getWrapper(CastleConfigMailDto castleConfigMailDto){
        QueryWrapper<CastleConfigMailEntity> wrapper= new QueryWrapper();
        if(castleConfigMailDto != null){
            CastleConfigMailEntity castleConfigMailEntity = ConvertUtil.transformObj(castleConfigMailDto,CastleConfigMailEntity.class);
            wrapper.like(StrUtil.isNotEmpty(castleConfigMailEntity.getSmtp()),"smtp",castleConfigMailEntity.getSmtp());
            wrapper.like(StrUtil.isNotEmpty(castleConfigMailEntity.getMail()),"mail",castleConfigMailEntity.getMail());
            wrapper.eq(castleConfigMailEntity.getStatus() != null,"status",castleConfigMailEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<CastleConfigMailDto> pageCastleConfigMail(Page<CastleConfigMailDto> page, CastleConfigMailDto castleConfigMailDto) {
        QueryWrapper<CastleConfigMailEntity> wrapper = getWrapper(castleConfigMailDto);
        Page<CastleConfigMailEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleConfigMailEntity> castleConfigMailPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleConfigMailDto> pageDto = new Page(castleConfigMailPage.getCurrent(), castleConfigMailPage.getSize(),castleConfigMailPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleConfigMailPage.getRecords(),CastleConfigMailDto.class));
        return pageDto;
    }

    @Override
    public IPage<CastleConfigMailDto> pageCastleConfigMailExtends(Page<CastleConfigMailDto> page, CastleConfigMailDto castleConfigMailDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        CastleConfigMailEntity castleConfigMailEntity = ConvertUtil.transformObj(castleConfigMailDto,CastleConfigMailEntity.class);
        List<CastleConfigMailEntity> castleConfigMailList=baseMapper.extendsList(pageMap,castleConfigMailEntity);
        Long total = baseMapper.extendsCount(castleConfigMailEntity);
        Page<CastleConfigMailDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(castleConfigMailList,CastleConfigMailDto.class));
        return pageDto;
    }
    @Override
    public CastleConfigMailDto getByIdExtends(Long id){
        CastleConfigMailEntity  castleConfigMailEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(castleConfigMailEntity,CastleConfigMailDto.class);
    }

    @Override
    public List<CastleConfigMailDto> listCastleConfigMail(CastleConfigMailDto castleConfigMailDto){
        QueryWrapper<CastleConfigMailEntity> wrapper = getWrapper(castleConfigMailDto);
        List<CastleConfigMailEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleConfigMailDto.class);
    }

    @Override
    public RespBody checkColumnRepeat(CastleConfigMailDto dto) {
        //校验code
        QueryWrapper<CastleConfigMailEntity> queryWrapper=new QueryWrapper();
        queryWrapper.eq("code",dto.getCode());
        List<CastleConfigMailEntity> list = baseMapper.selectList(queryWrapper);
        if(list != null && !list.isEmpty()){
            //新增
            if(dto.getId() == null){
                return RespBody.fail(BizErrorCode.MAILCODE_EXIST_ERROR);
                //修改
            }else{
                for(CastleConfigMailEntity entity:list){
                    if(!entity.getId().equals(dto.getId())){
                        return RespBody.fail(BizErrorCode.MAILCODE_EXIST_ERROR);
                    }
                }
            }
        }
        return RespBody.data("校验通过");
    }
}

