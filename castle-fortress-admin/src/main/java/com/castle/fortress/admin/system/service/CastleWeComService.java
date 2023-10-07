package com.castle.fortress.admin.system.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysPostDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysPostEntity;

import java.util.List;

/**
 * 企业微信相关接口 服务类
 *
 * @author castle
 * @since 2021-01-04
 */
public interface CastleWeComService  {
    /**
     * 获取企微token
     * @return
     */
    public String getToken();

    /**
     * 根据手机号获取企微用户id
     * @param mobile
     * @return
     */
    public String getUserIdByMobile(String mobile);
    public String getUserIdByMobile(String mobile,String token);

    /**
     * 根据企微用户id获取用户信息
     * @param userId
     * @return
     */
    public JSONObject getUserInfoByUserId(String userId);
    public JSONObject getUserInfoByUserId(String userId , String token);

    /**
     * 根据手机号获取用户信息
     * @param mobile
     * @return
     */
    public JSONObject getUserInfoByMobile(String mobile);
    public JSONObject getUserInfoByMobile(String mobile , String token);
}
