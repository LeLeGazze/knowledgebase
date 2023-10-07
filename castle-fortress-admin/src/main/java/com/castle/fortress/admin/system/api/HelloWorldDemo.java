package com.castle.fortress.admin.system.api;

import com.castle.fortress.common.entity.RespBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * hello demo
 */
@RestController
@RequestMapping("/openapi/sys")
public class HelloWorldDemo {

    @GetMapping("/hello")
    public String getString(String name){
        System.out.println("123");
        return "helloworld:"+name;
    }

    @PostMapping("/postString")
    public RespBody<String> postString(@RequestBody Map map){
        System.out.println("postString");
        return RespBody.data("helloworld:"+map.get("name"));
    }

    @PostMapping("/postFile")
    public RespBody<String> postFile(MultipartFile media){
        System.out.println("postString");
        return RespBody.data("helloworld:"+media.getOriginalFilename());
    }
}
