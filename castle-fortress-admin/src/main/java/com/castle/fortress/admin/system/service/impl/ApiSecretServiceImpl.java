package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.ApiSecretDto;
import com.castle.fortress.admin.system.entity.ApiSecretEntity;
import com.castle.fortress.admin.system.mapper.ApiSecretMapper;
import com.castle.fortress.admin.system.service.ApiSecretService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 对外开放接口秘钥 服务实现类
 *
 * @author 
 * @since 2021-03-19
 */
@Service
public class ApiSecretServiceImpl extends ServiceImpl<ApiSecretMapper, ApiSecretEntity> implements ApiSecretService {
    /**
    * 获取查询条件
    * @param apiSecretDto
    * @return
    */
    public QueryWrapper<ApiSecretEntity> getWrapper(ApiSecretDto apiSecretDto){
        QueryWrapper<ApiSecretEntity> wrapper= new QueryWrapper();
        if(apiSecretDto != null){
            ApiSecretEntity apiSecretEntity = ConvertUtil.transformObj(apiSecretDto,ApiSecretEntity.class);
            wrapper.like(StrUtil.isNotEmpty(apiSecretEntity.getCustName()),"cust_name",apiSecretEntity.getCustName());
            wrapper.eq(StrUtil.isNotEmpty(apiSecretEntity.getSecretId()),"secret_id",apiSecretEntity.getSecretId());
            wrapper.eq(StrUtil.isNotEmpty(apiSecretEntity.getSecretKey()),"secret_key",apiSecretEntity.getSecretKey());
            wrapper.lt(apiSecretEntity.getExpiredDate() != null,"expired_date",apiSecretEntity.getExpiredDate());
            wrapper.eq(apiSecretEntity.getStatus() != null,"status",apiSecretEntity.getStatus());
        }
        return wrapper;
    }


    @Override
    public IPage<ApiSecretDto> pageApiSecret(Page<ApiSecretDto> page, ApiSecretDto apiSecretDto) {
        QueryWrapper<ApiSecretEntity> wrapper = getWrapper(apiSecretDto);
        Page<ApiSecretEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<ApiSecretEntity> apiSecretPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<ApiSecretDto> pageDto = new Page(apiSecretPage.getCurrent(), apiSecretPage.getSize(),apiSecretPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(apiSecretPage.getRecords(),ApiSecretDto.class));
        return pageDto;
    }


    @Override
    public List<ApiSecretDto> listApiSecret(ApiSecretDto apiSecretDto){
        QueryWrapper<ApiSecretEntity> wrapper = getWrapper(apiSecretDto);
        List<ApiSecretEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,ApiSecretDto.class);
    }

    @Override
    public RespBody<ApiSecretEntity> querySecretKey(String apiSecretId) {
        QueryWrapper<ApiSecretEntity> wrapper= new QueryWrapper();
        wrapper.eq("secret_id",apiSecretId);
        wrapper.eq("status", YesNoEnum.YES.getCode());
        List<ApiSecretEntity> list = baseMapper.selectList(wrapper);
        if(list==null || list.size()!= 1){
            return RespBody.fail(BizErrorCode.SECRET_ERROR);
        }
        ApiSecretEntity apiSecretEntity = list.get(0);
        if(apiSecretEntity.getExpiredDate()!=null && new Date().compareTo(apiSecretEntity.getExpiredDate()) == 1){
            return RespBody.fail(BizErrorCode.SECRET_EXPIRE_ERROR);
        }
        return RespBody.data(apiSecretEntity);
    }

    @Override
    public Integer expiredSecrets() {
        return baseMapper.expiredSecrets();
    }
}

