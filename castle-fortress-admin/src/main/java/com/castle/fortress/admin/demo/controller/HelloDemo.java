package com.castle.fortress.admin.demo.controller;

import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import org.springframework.web.bind.annotation.*;

/**
 * hello demo
 * @author castle
 */
@RestController
public class HelloDemo {

    @GetMapping("/demo/callbackGet")
    @ResponseBody
    public RespBody getString(@RequestParam String body){
        System.out.println("callbackGet:"+body);
        if("123".equals(body)){
            throw new BizException("报错啦报错啦");
        }
        return RespBody.data(body);

    }

    @PostMapping("/demo/callbackPost")
    public void postString(@RequestBody String body){
        System.out.println("callbackPost:"+body);
    }

    @RequestMapping("/demo/callback")
    public void callbackString(@RequestParam String param,@RequestBody String body){
        System.out.println("callbackString:parm:"+param);
        System.out.println("callbackString:body:"+body);
    }
}
