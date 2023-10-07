package com.castle.fortress.admin.message.sms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.message.sms.dto.ConfigSmsDto;
import com.castle.fortress.admin.message.sms.entity.ConfigSmsEntity;
import com.castle.fortress.admin.message.sms.mapper.ConfigSmsMapper;
import com.castle.fortress.admin.message.sms.service.ConfigSmsService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.SmsPlatFormEnum;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 短信配置表 服务实现类
 *
 * @author castle
 * @since 2021-04-12
 */
@Service
public class ConfigSmsServiceImpl extends ServiceImpl<ConfigSmsMapper, ConfigSmsEntity> implements ConfigSmsService {
    /**
    * 获取查询条件
    * @param configSmsDto
    * @return
    */
    public QueryWrapper<ConfigSmsEntity> getWrapper(ConfigSmsDto configSmsDto){
        QueryWrapper<ConfigSmsEntity> wrapper= new QueryWrapper();
        if(configSmsDto != null){
            ConfigSmsEntity configSmsEntity = ConvertUtil.transformObj(configSmsDto,ConfigSmsEntity.class);
            wrapper.eq(StrUtil.isNotEmpty(configSmsDto.getSmsCode()),"sms_code",configSmsEntity.getSmsCode());
            wrapper.eq(configSmsDto.getStatus()!=null,"status",configSmsEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<ConfigSmsDto> pageConfigSms(Page<ConfigSmsDto> page, ConfigSmsDto configSmsDto) {
        QueryWrapper<ConfigSmsEntity> wrapper = getWrapper(configSmsDto);
        Page<ConfigSmsEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<ConfigSmsEntity> configSmsPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<ConfigSmsDto> pageDto = new Page(configSmsPage.getCurrent(), configSmsPage.getSize(),configSmsPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(configSmsPage.getRecords(),ConfigSmsDto.class));
        return pageDto;
    }

    @Override
    public IPage<ConfigSmsDto> pageConfigSmsExtends(Page<ConfigSmsDto> page, ConfigSmsDto configSmsDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        ConfigSmsEntity configSmsEntity = ConvertUtil.transformObj(configSmsDto,ConfigSmsEntity.class);
        List<ConfigSmsEntity> configSmsList=baseMapper.extendsList(pageMap,configSmsEntity);
        Long total = baseMapper.extendsCount(configSmsEntity);
        Page<ConfigSmsDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(configSmsList,ConfigSmsDto.class));
        return pageDto;
    }
    @Override
    public ConfigSmsDto getByIdExtends(Long id){
        ConfigSmsEntity  configSmsEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(configSmsEntity,ConfigSmsDto.class);
    }

    @Override
    public List<ConfigSmsDto> listConfigSms(ConfigSmsDto configSmsDto){
        QueryWrapper<ConfigSmsEntity> wrapper = getWrapper(configSmsDto);
        List<ConfigSmsEntity> list = baseMapper.selectList(wrapper);
        List<ConfigSmsDto> result = ConvertUtil.transformObjList(list,ConfigSmsDto.class);
        for(ConfigSmsDto d:result){
            d.setPlatformName(SmsPlatFormEnum.getNameByCode(d.getPlatform()));
        }
        return result;
    }

    @Override
    public RespBody checkColumnRepeat(ConfigSmsDto configSmsDto) {
        //校验短信编码
        QueryWrapper<ConfigSmsEntity> queryWrapper=new QueryWrapper();
        queryWrapper.eq("sms_code",configSmsDto.getSmsCode());
        List<ConfigSmsEntity> list = baseMapper.selectList(queryWrapper);
        if(list != null && !list.isEmpty()){
            //新增
            if(configSmsDto.getId() == null){
                return RespBody.fail(BizErrorCode.SMSCODE_EXIST_ERROR);
                //修改
            }else{
                for(ConfigSmsEntity entity:list){
                    if(!entity.getId().equals(configSmsDto.getId())){
                        return RespBody.fail(BizErrorCode.SMSCODE_EXIST_ERROR);
                    }
                }
            }
        }
        return RespBody.data("校验通过");
    }
}

