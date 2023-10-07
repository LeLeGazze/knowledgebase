package com.castle.fortress.admin.message.wecom.rest;

import com.castle.fortress.admin.message.wecom.WeComMessageUtils;
import com.castle.fortress.admin.message.wecom.dto.Article;
import com.castle.fortress.admin.message.wecom.enums.WeComMessageType;
import com.castle.fortress.admin.system.dto.CastleSysUserWeComDto;
import com.castle.fortress.admin.system.service.CastleSysUserWeComService;
import com.castle.fortress.common.entity.RespBody;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 临时测试用
 */
@Api(tags = "企业微信消息API控制器")
@RestController
@RequestMapping("/api/message/wecom/")
public class WeComMessageController {

    @Autowired
    WeComMessageUtils weComMessageUtils;

    @Autowired
    CastleSysUserWeComService castleSysUserWeComService;

    /**
     * 发送消息demo
     * @param type
     * @param receiver
     * @param content
     * @return
     */
    @GetMapping("/test")
    public RespBody<String> sendMessage(Integer type, String receiver, String content) {
        try {
            final List<CastleSysUserWeComDto> list = castleSysUserWeComService.listCastleSysUserWeCom(null);
            receiver = weComMessageUtils.getReceiver(list);
            final HashMap<String, Object> contentMap = new HashMap<>();
            if (WeComMessageType.TEXT.getCode().equals(type)) {
                contentMap.put("content", content);
                weComMessageUtils.sendMessage(WeComMessageType.TEXT, receiver, contentMap);
            }
            if (WeComMessageType.TEXT_CARD.getCode().equals(type)) {
                contentMap.put("title", "标题");
                contentMap.put("description", content);
                contentMap.put("url", "www.baidu.com");
                contentMap.put("btntxt", "按钮名");
                weComMessageUtils.sendMessage(WeComMessageType.TEXT_CARD, receiver, contentMap);
            }
            if (WeComMessageType.GRAPHIC.getCode().equals(type)) {
                final ArrayList<Article> articles = new ArrayList<>();
                final Article temp1 = new Article();
                temp1.setTitle("标题1");
                temp1.setDescription("描述");
                temp1.setPicurl("http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png");
                articles.add(temp1);
                contentMap.put("articles", articles);
                weComMessageUtils.sendMessage(WeComMessageType.GRAPHIC, receiver, contentMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBody.data("发送成功");
    }

}
