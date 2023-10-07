package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.entity.CastleSysUserWeComEntity;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.ConfigApiService;
import com.castle.fortress.admin.system.service.SysUserService;
import com.castle.fortress.admin.utils.BindApiUtils;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.entity.CastleSysUserDingEntity;
import com.castle.fortress.admin.system.dto.CastleSysUserDingDto;
import com.castle.fortress.admin.system.mapper.CastleSysUserDingMapper;
import com.castle.fortress.admin.system.service.CastleSysUserDingService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户钉钉信息表 服务实现类
 *
 * @author Mgg
 * @since 2022-12-13
 */
@Service
public class CastleSysUserDingServiceImpl extends ServiceImpl<CastleSysUserDingMapper, CastleSysUserDingEntity> implements CastleSysUserDingService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BindApiUtils bindApiUtils;
    /**
    * 获取查询条件
    * @param castleSysUserDingDto
    * @return
    */
    public QueryWrapper<CastleSysUserDingEntity> getWrapper(CastleSysUserDingDto castleSysUserDingDto){
        QueryWrapper<CastleSysUserDingEntity> wrapper= new QueryWrapper();
        if(castleSysUserDingDto != null){
            CastleSysUserDingEntity castleSysUserDingEntity = ConvertUtil.transformObj(castleSysUserDingDto,CastleSysUserDingEntity.class);
            wrapper.like(castleSysUserDingEntity.getUserId() != null,"user_id",castleSysUserDingEntity.getUserId());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getDingUnionid()),"ding_unionid",castleSysUserDingEntity.getDingUnionid());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getDingUserid()),"ding_userid",castleSysUserDingEntity.getDingUserid());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getName()),"name",castleSysUserDingEntity.getName());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getDeptIdList()),"dept_id_list",castleSysUserDingEntity.getDeptIdList());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getRoleList()),"role_list",castleSysUserDingEntity.getRoleList());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getMobile()),"mobile",castleSysUserDingEntity.getMobile());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getEmail()),"email",castleSysUserDingEntity.getEmail());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getOrgEmail()),"org_email",castleSysUserDingEntity.getOrgEmail());
            wrapper.like(StrUtil.isNotEmpty(castleSysUserDingEntity.getAvatar()),"avatar",castleSysUserDingEntity.getAvatar());
        }
        return wrapper;
    }


    @Override
    public IPage<CastleSysUserDingDto> pageCastleSysUserDing(Page<CastleSysUserDingDto> page, CastleSysUserDingDto castleSysUserDingDto) {
        QueryWrapper<CastleSysUserDingEntity> wrapper = getWrapper(castleSysUserDingDto);
        Page<CastleSysUserDingEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleSysUserDingEntity> castleSysUserDingPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleSysUserDingDto> pageDto = new Page(castleSysUserDingPage.getCurrent(), castleSysUserDingPage.getSize(),castleSysUserDingPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleSysUserDingPage.getRecords(),CastleSysUserDingDto.class));
        return pageDto;
    }


    @Override
    public List<CastleSysUserDingDto> listCastleSysUserDing(CastleSysUserDingDto castleSysUserDingDto){
        QueryWrapper<CastleSysUserDingEntity> wrapper = getWrapper(castleSysUserDingDto);
        List<CastleSysUserDingEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleSysUserDingDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean syncDing() {
        // 删除所有旧信息 添加新信息
        baseMapper.delete(new QueryWrapper<>());
        List<SysUser> list = sysUserService.list();
        // 先获取配置的企业id和秘钥
        String  accessToken = getAccessToken();
        List<CastleSysUserDingEntity> addList =  new ArrayList<>();
        for (SysUser sysUser : list) {
            String phone = sysUser.getPhone();
            // 只同步有手机号的用户
            if (StrUtil.isEmpty(sysUser.getPhone())){
                continue;
            }
            // 根据手机号获取用户钉钉userID
            String userid = getUserIdByMobile(phone, accessToken);
            if (StrUtil.isEmpty(userid)){
                continue;
            }
            //userid 不为空 根据userid 获取 钉钉用户信息
            JSONObject userInfo =  getUserInfoByUserId(userid,accessToken);
            if (null == userInfo){
                continue;
            }
            CastleSysUserDingEntity entity = new CastleSysUserDingEntity();
            entity.setUserId(sysUser.getId());
            entity.setDingUnionid(userInfo.getStr("unionid"));
            entity.setDingUserid(userInfo.getStr("userid"));
            entity.setName(userInfo.getStr("name"));
            entity.setDeptIdList(userInfo.getStr("dept_id_list"));
            entity.setRoleList(userInfo.getStr("role_list"));
            entity.setMobile(userInfo.getStr("mobile"));
            entity.setEmail(userInfo.getStr("email"));
            entity.setOrgEmail(userInfo.getStr("org_email"));
            entity.setAvatar(userInfo.getStr("avatar"));
            addList.add(entity);
            // 超过50个就写入一次 防止saveBatch时 sql过长
            if (addList.size()%50==0){
                saveBatch(addList);
                addList = new ArrayList<>();
            }
        }
        if (addList.size() > 0){
            saveBatch(addList);
        }
        return true;
    }

    /**
     * 获取access_token
     * @return access_token
     */
    private String getAccessToken(){
        // 获取配置的参数
        ConfigApiDto dto = bindApiUtils.getData("DING_LOGIN");
        if(dto == null){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String,Object> paramMap = dto.getParamMap();
        if(paramMap == null || paramMap.isEmpty()){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String appKey = (String) paramMap.get("appKey");
        String appSecret = (String) paramMap.get("appSecret");
        // 获取access_token的地址
        String accessUrl = "https://oapi.dingtalk.com/gettoken";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("appkey",appKey);
        map.put("appsecret",appSecret);
        // 调用通过地址获取信息
        JSONObject accessJsonObject = getDataByUrl(accessUrl,map);
        // 获取access_token
        String accessToken = accessJsonObject.getStr("access_token");
        return accessToken;
    }

    /**
     * 根据手机号获取钉钉userId
     * @param mobile
     * @param token
     * @return
     */
    private String getUserIdByMobile(String mobile,String token){
        Map<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        String body = JSONUtil.parse(map).toString();
        // 根据手机号获取userId的接口
        String url = "https://oapi.dingtalk.com/topapi/v2/user/getbymobile?access_token="+token;
        String result = HttpRequest.post(url).body(body).execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if("0".equals(jsonObject.getStr("errcode"))){
            String userid = jsonObject.getJSONObject("result").getStr("userid");
            return userid;
        }
        return null;
    }

    /**
     * 根据userId 获取钉钉用户信息
     * @param userid
     * @param token
     * @return
     */
    private JSONObject getUserInfoByUserId(String userid,String token){
        Map<String,String> map=new HashMap<>();
        map.put("userid",userid);
        String body = JSONUtil.parse(map).toString();
        // 根据userId 获取 用户信息的接口
        String url = "https://oapi.dingtalk.com/topapi/v2/user/get?access_token="+token;
        String result = HttpRequest.post(url).body(body).execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if("0".equals(jsonObject.getStr("errcode"))){
            return jsonObject.getJSONObject("result");
        }
        return null;
    }


    /**
     * 重新获取信息
     * @param url
     * @param paramMap
     * @return
     */
    private JSONObject getDataByUrl(String url, Map<String, Object> paramMap) {
        JSONObject myJsonObject = new JSONObject();
        try {
            StringBuilder sb=new StringBuilder(url);
            if(!paramMap.isEmpty()){
                sb.append("?");
            }
            for(String key:paramMap.keySet()){
                sb.append(key+"="+paramMap.get(key)+"&");
            }
            String targetUrl=sb.toString().substring(0,sb.toString().length()-1);
            String result = HttpRequest.get(targetUrl).execute().body();
            // 打印返回结果
//            System.out.println("获取token----" + result);
            myJsonObject = JSONUtil.parseObj(result);
//            ACCESS_ERROR失效
//            40001
//            ACCESS_ERROR参数错误
//            40014
            if("40001".equals(myJsonObject.getStr("errcode") + "")
                    || "40014".equals(myJsonObject.getStr("errcode") + "" )){
                String accessToken=getAccessToken();
                paramMap.put("access_token",accessToken);
                return getDataByUrl(url,paramMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myJsonObject;
    }



}

