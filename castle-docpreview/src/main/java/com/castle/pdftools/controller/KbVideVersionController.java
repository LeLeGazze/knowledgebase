package com.castle.pdftools.controller;

import com.castle.pdftools.entity.RespBody;
import com.castle.pdftools.service.KbVideVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * PDF/word 等转换成PDF 控制器
 *
 * @author
 * @since 2023-05-08
 */

@RestController
@RequestMapping()
public class KbVideVersionController {
    @Autowired
    private KbVideVersionService kbVideVersionService;


    @GetMapping("/knowledge/kbVideVersion/toLocalPngBid")
    @ResponseBody
    public RespBody toLocalPngBid(@RequestParam("bid") String bid) {
//        kbVideVersionService.kbVideVersionService(bid);
        kbVideVersionService.taskAdd(bid);
        if (!kbVideVersionService.isTaskRunning()) {
            kbVideVersionService.taskRun(); // 启动任务
        }
        return RespBody.data("调用转换图片成功");
    }

    @PostMapping("/knowledge/kbVideVersion/toWordPdf")
    @ResponseBody
    public RespBody toWordPdf(@RequestBody Map<String, String> queryMap) {

        kbVideVersionService.taskAddWordPdf(queryMap);
        if (!kbVideVersionService.isTaskRunning()) {
            kbVideVersionService.taskRun(); // 启动任务
        }
        return RespBody.data("调用转换图片成功");
    }
}
