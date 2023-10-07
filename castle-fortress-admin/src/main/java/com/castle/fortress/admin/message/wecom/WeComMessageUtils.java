package com.castle.fortress.admin.message.wecom;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.message.wecom.enums.WeComMessageType;
import com.castle.fortress.admin.message.wecom.enums.WeComReceiveType;
import com.castle.fortress.admin.system.dto.CastleSysUserWeComDto;
import com.castle.fortress.admin.system.entity.CastleSysUserWeComEntity;
import com.castle.fortress.admin.system.service.CastleSysUserWeComService;
import com.castle.fortress.admin.system.service.CastleWeComService;
import com.castle.fortress.admin.system.service.impl.CastleWeComServiceImpl;
import com.castle.fortress.admin.utils.BindApiUtils;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeComMessageUtils {


    @Autowired
    CastleWeComService castleWeComService;
    @Autowired
    private BindApiUtils bindApiUtils;

    @Autowired
    private CastleSysUserWeComService castleSysUserWeComService;
    /**
     * 封装处理接收人方法
     * @param list
     * @return
     */
    public <T> String getReceiver(List<T> list) {
        if (list.size() > 1000) {
            throw new BizException("超出每次可发送的人数数量");
        }
        if (list.get(0) instanceof Long) {
            //传入userIds 返回 企微ID |拼接
            final QueryWrapper<CastleSysUserWeComEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", list).or().in("user_id",list);;
            final List<CastleSysUserWeComEntity> entities = castleSysUserWeComService.list(queryWrapper);
            return entities.stream().map(CastleSysUserWeComEntity::getWeComUserid).collect(Collectors.joining("|"));
        } else if (list.get(0) instanceof CastleSysUserWeComDto) {
            //传入企微实体类 返回企微ID |拼接
            return ConvertUtil.transformObjList(list, CastleSysUserWeComDto.class).stream().map(CastleSysUserWeComDto::getWeComUserid).collect(Collectors.joining("|"));
        } else if (list.get(0) instanceof String) {
            //传入多个企微ID 返回 企微ID |拼接
            return list.stream().map(String::valueOf).collect(Collectors.joining("|"));
        } else {
            throw new BizException("获取接收人异常");
        }
    }

    /**
     * 发送消息
     * @param messageType  消息类型枚举 不同消息类型 需要做不同的验证
     * @param receiver   接收人    需要调用 getReceiver 这个方法
     * @param content   消息内容 具体查看企业微信官方文档传入不同的结构
     */
    public void sendMessage(WeComMessageType messageType, String receiver, HashMap<String, Object> content) {
        this.checkMessageType(messageType, content);
        final JSONObject config = new JSONObject(bindApiUtils.getData("SELF_WECOM").getBindDetail());
        final HttpRequest request = HttpRequest.post(" https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + castleWeComService.getToken());
        final HashMap<String, Object> formMap = new HashMap<>();
        formMap.put("touser", receiver);
        formMap.put("msgtype", messageType.getDesc());
        formMap.put("agentid", config.get("agentId"));
        formMap.put(messageType.getDesc(), content);
        System.out.println(new JSONObject(formMap).toString());
        request.body(new JSONObject(formMap).toString());
        final HttpResponse responseJson = request.execute();
        final JSONObject response = new JSONObject(responseJson);
        if (!"0".equals(response.get("errcode"))){
            throw new BizException("发送企微消息异常:" + response.get("errmsg"));
        }
        System.out.println(responseJson);
    }

    /**
     * 根据不同类型 做数据校验
     * @param messageType
     * @param content
     */
    private void checkMessageType(WeComMessageType messageType, HashMap<String, Object> content) {
        if (WeComMessageType.TEXT.equals(messageType)) {
            if (!content.containsKey("content")) {
                throw new BizException("文本消息体为空");
            }
        } else if (WeComMessageType.GRAPHIC.equals(messageType)) {
            if (!content.containsKey("articles")) {
                throw new BizException("图文消息体为空");
            }
        } else if (WeComMessageType.TEXT_CARD.equals(messageType)) {
            if (!content.containsKey("title")) {
                throw new BizException("文本卡片消息标题为空");
            }
            if (!content.containsKey("description")) {
                throw new BizException("文本卡片消息体为空");
            }
            if (!content.containsKey("url")) {
                throw new BizException("文本卡片消息回调地址为空");
            }
            if (!content.containsKey("btntxt")) {
                throw new BizException("文本卡片消息按钮描述为空");
            }
        }
    }
}
