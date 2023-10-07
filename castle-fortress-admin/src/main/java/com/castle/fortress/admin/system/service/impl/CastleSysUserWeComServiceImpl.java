package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;

import cn.hutool.json.JSONObject;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.CastleWeComService;
import com.castle.fortress.admin.system.service.SysUserService;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.entity.CastleSysUserWeComEntity;
import com.castle.fortress.admin.system.dto.CastleSysUserWeComDto;
import com.castle.fortress.admin.system.mapper.CastleSysUserWeComMapper;
import com.castle.fortress.admin.system.service.CastleSysUserWeComService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户企业微信信息表 服务实现类
 *
 * @author mjj
 * @since 2022-11-30
 */
@Service
public class CastleSysUserWeComServiceImpl extends ServiceImpl<CastleSysUserWeComMapper, CastleSysUserWeComEntity> implements CastleSysUserWeComService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CastleWeComService castleWeComService;

    /**
    * 获取查询条件
    * @param castleSysUserWeComDto
    * @return
    */
    public QueryWrapper<CastleSysUserWeComEntity> getWrapper(CastleSysUserWeComDto castleSysUserWeComDto){
        QueryWrapper<CastleSysUserWeComEntity> wrapper= new QueryWrapper();
        if(castleSysUserWeComDto != null){
            CastleSysUserWeComEntity castleSysUserWeComEntity = ConvertUtil.transformObj(castleSysUserWeComDto,CastleSysUserWeComEntity.class);
            wrapper.like(castleSysUserWeComEntity.getId() != null,"id",castleSysUserWeComEntity.getId());
            wrapper.like(castleSysUserWeComEntity.getUserId() != null,"user_id",castleSysUserWeComEntity.getUserId());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getWeComUserid()),"we_com_userid",castleSysUserWeComEntity.getWeComUserid());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getName()),"name",castleSysUserWeComEntity.getName());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getDepartment()),"department",castleSysUserWeComEntity.getDepartment());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getPosition()),"position",castleSysUserWeComEntity.getPosition());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getMobile()),"mobile",castleSysUserWeComEntity.getMobile());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getEmail()),"email",castleSysUserWeComEntity.getEmail());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getGender()),"gender",castleSysUserWeComEntity.getGender());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getStatus()),"status",castleSysUserWeComEntity.getStatus());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getAvatar()),"avatar",castleSysUserWeComEntity.getAvatar());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getIsleader()),"isleader",castleSysUserWeComEntity.getIsleader());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getExtattr()),"extattr",castleSysUserWeComEntity.getExtattr());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getEnglishName()),"english_name",castleSysUserWeComEntity.getEnglishName());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getTelephone()),"telephone",castleSysUserWeComEntity.getTelephone());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getEnable()),"enable",castleSysUserWeComEntity.getEnable());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getHideMobile()),"hide_mobile",castleSysUserWeComEntity.getHideMobile());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getSort()),"sort",castleSysUserWeComEntity.getSort());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getExternalProfile()),"external_profile",castleSysUserWeComEntity.getExternalProfile());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getMainDepartment()),"main_department",castleSysUserWeComEntity.getMainDepartment());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getQrCode()),"qr_code",castleSysUserWeComEntity.getQrCode());
            wrapper.like(castleSysUserWeComEntity.getAlias() != null,"alias",castleSysUserWeComEntity.getAlias());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getIsLeaderInDept()),"is_leader_in_dept",castleSysUserWeComEntity.getIsLeaderInDept());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getAddress()),"address",castleSysUserWeComEntity.getAddress());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getThumbAvatar()),"thumb_avatar",castleSysUserWeComEntity.getThumbAvatar());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getDirectLeader()),"direct_leader",castleSysUserWeComEntity.getDirectLeader());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getBizMail()),"biz_mail",castleSysUserWeComEntity.getBizMail());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserWeComEntity.getOpenUserid()),"open_userid",castleSysUserWeComEntity.getOpenUserid());
            wrapper.like(castleSysUserWeComEntity.getCreateTime() != null,"create_time",castleSysUserWeComEntity.getCreateTime());
        }
        return wrapper;
    }


    @Override
    public IPage<CastleSysUserWeComDto> pageCastleSysUserWeCom(Page<CastleSysUserWeComDto> page, CastleSysUserWeComDto castleSysUserWeComDto) {
        QueryWrapper<CastleSysUserWeComEntity> wrapper = getWrapper(castleSysUserWeComDto);
        Page<CastleSysUserWeComEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleSysUserWeComEntity> castleSysUserWeComPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleSysUserWeComDto> pageDto = new Page(castleSysUserWeComPage.getCurrent(), castleSysUserWeComPage.getSize(),castleSysUserWeComPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleSysUserWeComPage.getRecords(),CastleSysUserWeComDto.class));
        return pageDto;
    }


    @Override
    public List<CastleSysUserWeComDto> listCastleSysUserWeCom(CastleSysUserWeComDto castleSysUserWeComDto){
        QueryWrapper<CastleSysUserWeComEntity> wrapper = getWrapper(castleSysUserWeComDto);
        List<CastleSysUserWeComEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleSysUserWeComDto.class);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean syncWeCom() {
        // 删除所有旧信息 添加新信息
        baseMapper.removeAll();
        List<SysUser> list = sysUserService.list();
        String token = castleWeComService.getToken();

        List<CastleSysUserWeComEntity> addList =  new ArrayList<>();
        for (SysUser sysUser : list){
            String phone = sysUser.getPhone();
            // 只同步有手机号的用户
            if (StrUtil.isEmpty(sysUser.getPhone())){
                continue;
            }
            try {
                JSONObject jsonObject = castleWeComService.getUserInfoByMobile(phone , token);
                if (jsonObject.getInt("errcode") != 0){
                    continue;
                }
                CastleSysUserWeComEntity entity = new CastleSysUserWeComEntity();
                entity.setUserId(sysUser.getId());
                entity.setWeComUserid(jsonObject.getStr("userid"));
                entity.setName(jsonObject.getStr("name"));
                entity.setDepartment(jsonObject.getStr("department"));
                entity.setPosition(jsonObject.getStr("position"));
                entity.setMobile(jsonObject.getStr("mobile"));
                entity.setGender(jsonObject.getStr("gender"));
                entity.setEmail(jsonObject.getStr("email"));
                entity.setAvatar(jsonObject.getStr("avatar"));
                entity.setStatus(jsonObject.getStr("status"));
                entity.setIsleader(jsonObject.getStr("isleader"));
                entity.setExtattr(jsonObject.getStr("extattr"));
                entity.setEnglishName(jsonObject.getStr("english_name"));
                entity.setTelephone(jsonObject.getStr("telephone"));
                entity.setEnable(jsonObject.getStr("enable"));
                entity.setHideMobile(jsonObject.getStr("hide_mobile"));
                entity.setSort(jsonObject.getStr("order"));
                entity.setExternalProfile(jsonObject.getStr("external_profile"));
                entity.setMainDepartment(jsonObject.getStr("main_department"));
                entity.setQrCode(jsonObject.getStr("qr_code"));
                entity.setAlias(jsonObject.getStr("alias"));
                entity.setIsleader(jsonObject.getStr("is_leader_in_dept"));
                entity.setAddress(jsonObject.getStr("address"));
                entity.setThumbAvatar(jsonObject.getStr("thumb_avatar"));
                entity.setDirectLeader(jsonObject.getStr("direct_leader"));
                entity.setBizMail(jsonObject.getStr("biz_mail"));
                addList.add(entity);


            }catch (Exception e){
                continue;
            }
        }
        if (addList.size() > 0){
            saveBatch(addList);
        }
        return true;
    }


    @Override
    public CastleSysUserWeComDto getByUserId(Long id) {
        CastleSysUserWeComEntity entity = baseMapper.selectByUserId(id);
        return ConvertUtil.transformObj(entity , CastleSysUserWeComDto.class);
    }
}

