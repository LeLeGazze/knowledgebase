package com.castle.fortress.admin.message.dingtalk.rest;

import com.castle.fortress.admin.message.dingtalk.DingTalkMessageUtils;
import com.castle.fortress.admin.message.dingtalk.enums.DingTalkMessageType;
import com.castle.fortress.admin.system.dto.CastleSysUserDingDto;
import com.castle.fortress.admin.system.entity.CastleSysUserDingEntity;
import com.castle.fortress.admin.system.service.CastleSysUserDingService;
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
@Api(tags = "钉钉消息API控制器")
@RestController
@RequestMapping("/api/message/dingtalk/")
public class DingTalkMessageController {

    @Autowired
    DingTalkMessageUtils dingTalkMessageUtils;

    @Autowired
    CastleSysUserDingService dingService;

    /**
     * 发送消息demo
     *
     * @param type
     * @param receiver
     * @param content
     * @return
     */
    @GetMapping("/test")
    public RespBody<String> sendMessage(Integer type, String receiver, String content) {
        try {
//
//
            //传入钉钉id
//            final ArrayList<String> dingIds = new ArrayList<>();
//            dingIds.add("025967122026619321");
//            dingIds.add("01093925290038649938");
//            dingIds.add("083113236132428003");
//            final String receiverString = dingTalkMessageUtils.getReceiver(dingIds);
//            System.out.println("receiverString: " + receiverString);
//
            //传入 钉钉用户表id/或者user_id 可混合
//            final ArrayList<Long> userId = new ArrayList<>();
//            userId.add(1375360470249771009L);
//            userId.add(1597922245961515009L);
//            userId.add(1600047267134369794L);
//            final String receiverLong = dingTalkMessageUtils.getReceiver(userId);
//            System.out.println("receiverLong: " + receiverLong);

            final CastleSysUserDingDto castleSysUserDingDto = new CastleSysUserDingDto();
            castleSysUserDingDto.setName("王海臣");
            final List<CastleSysUserDingDto> list = dingService.listCastleSysUserDing(castleSysUserDingDto);
            final String receiverListItem = dingTalkMessageUtils.getReceiver(list);
            System.out.println("receiverListItem: " + receiverListItem);
            if (type == 1) {
                final HashMap<String, Object> contentMap = new HashMap<>();
                contentMap.put("content", content);
                dingTalkMessageUtils.sendMessage(DingTalkMessageType.TEXT, receiverListItem, contentMap);
            } else {
                final HashMap<String, Object> contentMap = new HashMap<>();
                contentMap.put("content", content);
                dingTalkMessageUtils.sendAll(DingTalkMessageType.TEXT, contentMap);
//                final HashMap<String, Object> contentMap = new HashMap<>();
//                contentMap.put("title", "标题");
//                contentMap.put("markdown", content);
//                contentMap.put("single_title", "按钮标题");
//                contentMap.put("single_url", "https://www.baidu.com");
//                dingTalkMessageUtils.sendMessage(DingTalkMessageType.TEXT_CARD, receiver, contentMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBody.data("发送成功");
    }

}
