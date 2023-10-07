package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.SysOssRecordDto;
import com.castle.fortress.admin.system.entity.SysOssRecordEntity;
import com.castle.fortress.admin.system.mapper.SysOssRecordMapper;
import com.castle.fortress.admin.system.service.SysOssRecordService;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * oss上传记录 服务实现类
 *
 * @author castle
 * @since 2022-03-01
 */
@Service
public class SysOssRecordServiceImpl extends ServiceImpl<SysOssRecordMapper, SysOssRecordEntity> implements SysOssRecordService {
    /**
    * 获取查询条件
    * @param sysOssRecordDto
    * @return
    */
    public QueryWrapper<SysOssRecordEntity> getWrapper(SysOssRecordDto sysOssRecordDto){
        QueryWrapper<SysOssRecordEntity> wrapper= new QueryWrapper();
        if(sysOssRecordDto != null){
            SysOssRecordEntity sysOssRecordEntity = ConvertUtil.transformObj(sysOssRecordDto,SysOssRecordEntity.class);
            wrapper.like(StrUtil.isNotEmpty(sysOssRecordEntity.getResourceName()),"resource_name",sysOssRecordEntity.getResourceName());
            wrapper.like(sysOssRecordEntity.getOssPlatform()!=null,"oss_platform",sysOssRecordEntity.getOssPlatform());
            wrapper.like(sysOssRecordEntity.getCreateUser() != null,"create_user",sysOssRecordEntity.getCreateUser());
            wrapper.like(sysOssRecordEntity.getUserType()!= null,"user_type",sysOssRecordEntity.getUserType());
            if("image".equals(sysOssRecordDto.getResourceType())){
                wrapper.and(w->{
                    w.likeLeft("resource_name",".png").or().likeLeft("resource_name",".jpeg").or().likeLeft("resource_name",".jpg");
                });
            }else if("video".equals(sysOssRecordDto.getResourceType())){
                wrapper.and(w->{
                    w.likeLeft("resource_name",".avi").or().likeLeft("resource_name",".mp4");
                });
            }
        }
        wrapper.orderByDesc("create_time");
        return wrapper;
    }


    @Override
    public IPage<SysOssRecordDto> pageSysOssRecord(Page<SysOssRecordDto> page, SysOssRecordDto sysOssRecordDto) {
        QueryWrapper<SysOssRecordEntity> wrapper = getWrapper(sysOssRecordDto);
        Page<SysOssRecordEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysOssRecordEntity> sysOssRecordPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysOssRecordDto> pageDto = new Page(sysOssRecordPage.getCurrent(), sysOssRecordPage.getSize(),sysOssRecordPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysOssRecordPage.getRecords(),SysOssRecordDto.class));
        return pageDto;
    }


    @Override
    public List<SysOssRecordDto> listSysOssRecord(SysOssRecordDto sysOssRecordDto){
        QueryWrapper<SysOssRecordEntity> wrapper = getWrapper(sysOssRecordDto);
        List<SysOssRecordEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,SysOssRecordDto.class);
    }
    @Async
    @Override
    public void saveAsyn(SysOssRecordDto sysOssRecordDto) {
        if(sysOssRecordDto != null){
            sysOssRecordDto.setCreateTime(new Date());
            baseMapper.insert(ConvertUtil.transformObj(sysOssRecordDto,SysOssRecordEntity.class));
        }
    }
}

