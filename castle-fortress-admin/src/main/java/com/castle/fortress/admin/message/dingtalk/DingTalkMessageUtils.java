package com.castle.fortress.admin.message.dingtalk;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.message.dingtalk.enums.DingTalkMessageType;
import com.castle.fortress.admin.system.dto.CastleSysUserDingDto;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.entity.CastleSysUserDingEntity;
import com.castle.fortress.admin.system.service.CastleSysUserDingService;
import com.castle.fortress.admin.utils.BindApiUtils;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class DingTalkMessageUtils {
    @Autowired
    private BindApiUtils bindApiUtils;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CastleSysUserDingService castleSysUserDingService;

    /**
     * 获取token
     * 先根据应用id 去缓存里拿 拿不到再获取
     * @return
     */
    public String getAccessToken() {
        // 获取配置的参数
        ConfigApiDto dto = bindApiUtils.getData("DING_LOGIN");
        if (dto == null) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String, Object> map = dto.getParamMap();
        if (map == null || map.isEmpty()) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String appKey = (String) map.get("appKey");
        String appSecret = (String) map.get("appSecret");
        String appIdLogin = (String) map.get("loginAppId");
        String appSecretLogin = (String) map.get("loginAppSecret");
        if (redisUtils.get(appIdLogin) != null) {
            System.out.println("缓存拿到的");
            return redisUtils.get(appIdLogin).toString();
        }
        String accessToken = HttpRequest.get("https://oapi.dingtalk.com/gettoken?appkey=" + appKey + "&appsecret=" + appSecret)
                .execute().body();
        JSONObject json = JSONUtil.parseObj(accessToken);
        accessToken = json.getStr("access_token");
        //需要写入缓存 不能频繁获取不然会有问题
        System.out.println("重新请求的");
        redisUtils.set(appIdLogin, accessToken, 2L, TimeUnit.HOURS);
        return accessToken;
    }

    /**
     * 封装处理接收人方法
     * @param list
     * @param <T>
     * @return
     */
    public <T> String getReceiver(List<T> list) {
        if (list.size() > 1000) {
            throw new BizException("超出每次可发送的人数数量");
        }
        if (list.get(0) instanceof Long) {
            final QueryWrapper<CastleSysUserDingEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("user_id", list).or().in("id", list);
            final List<CastleSysUserDingEntity> entities = castleSysUserDingService.list(queryWrapper);
            return entities.stream().map(CastleSysUserDingEntity::getDingUserid).collect(Collectors.joining(","));
        } else if (list.get(0) instanceof CastleSysUserDingDto) {
            return ConvertUtil.transformObjList(list, CastleSysUserDingDto.class).stream().map(CastleSysUserDingDto::getDingUserid).collect(Collectors.joining(","));
        } else if (list.get(0) instanceof String) {
            return list.stream().map(String::valueOf).collect(Collectors.joining(","));
        } else {
            throw new BizException("获取接收人异常");
        }
    }

    /**
     * 发送消息给所有人
     * @param messageType
     * @param content
     */
    public void sendAll(DingTalkMessageType messageType, HashMap<String, Object> content) {
        final JSONObject config = new JSONObject(bindApiUtils.getData("DING_LOGIN").getBindDetail());
        final HttpRequest request = HttpRequest.post("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token=" + this.getAccessToken());
        final JSONObject body = new JSONObject();
        body.set("to_all_user", true);
        body.set("agent_id", config.get("agentId"));
        final JSONObject msg = new JSONObject();
        msg.set("msgtype", messageType.getDesc());
        msg.set(messageType.getDesc(), new JSONObject(content));
        body.set("msg", msg);
        request.body(body.toString());
        final HttpResponse response = request.execute();
        System.out.println(response.body());
    }

    /**
     * 发送消息
     * @param messageType
     * @param receiver
     * @param content
     */
    public void sendMessage(DingTalkMessageType messageType, String receiver, HashMap<String, Object> content) {
        final JSONObject config = new JSONObject(bindApiUtils.getData("DING_LOGIN").getBindDetail());
        final HttpRequest request = HttpRequest.post("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token=" + this.getAccessToken());
        final JSONObject body = new JSONObject();
        body.set("agent_id", config.get("agentId"));
        body.set("userid_list", receiver);
        final JSONObject msg = new JSONObject();
        msg.set("msgtype", messageType.getDesc());
        msg.set(messageType.getDesc(), new JSONObject(content));
        body.set("msg", msg);
        request.body(body.toString());
        final HttpResponse responseJson = request.execute();
        final JSONObject response = new JSONObject(responseJson.body());
        if (!"0".equals(response.get("errcode"))) {
            throw new BizException("发送钉钉消息异常:" + response.get("errmsg"));
        }
        System.out.println(responseJson.body());
    }
}
