package com.castle.fortress.admin.job.task;

import cn.hutool.core.date.StopWatch;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbVideoEntity;
import com.castle.fortress.admin.knowledge.mapper.KbBaseLabelTaskMapper;
import com.castle.fortress.admin.knowledge.mapper.KbBasicLabelMapper;
import com.castle.fortress.admin.knowledge.mapper.KbBasicMapper;
import com.castle.fortress.admin.knowledge.service.KbBasicService;
import com.castle.fortress.admin.knowledge.service.KbModelService;
import com.castle.fortress.admin.knowledge.service.KbVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 每天定时更新es里面的数据
 **/
@Component("KbBaseDraftlTask")
@Slf4j
public class KbBaseDraftlTask implements ITask {

    @Autowired
    private KbBasicService kbBasicService;
    @Autowired
    private KbVideoService kbVideoService;
    @Autowired
    private EsSearchService searchService;

    @Override
    public void runTask(String params) {
        StopWatch stopWatch = new StopWatch("定时任务启动 修改修改过期视频和过期文章");
        stopWatch.start("查询知识过期知识列表");
        // 查询出有哪些过期的知识
        List<KbBasicEntity> kbBasicDBList = kbBasicService.selectByExpireBasic();
        // 修改文章状态
        List<KbBasicEntity> kbBasicList = kbBasicDBList.stream().map(item -> {
            item.setStatus(3);
            return item;
        }).collect(Collectors.toList());
        // 查询视频出有哪些过期的知识
        List<KbVideoEntity> kbVideoDBList = kbVideoService.selectByExpireVideo();
        kbBasicService.updateBatchById(kbBasicList);
        stopWatch.stop();
        stopWatch.start("查询视频过期列表");
        // 修改视频的状态
        List<KbVideoEntity> kbVideoList = kbVideoDBList.stream().map(item -> {
            item.setStatus(3);
            return item;
        }).collect(Collectors.toList());
        kbVideoService.updateBatchById(kbVideoList);
        stopWatch.stop();
        stopWatch.start("删除es存在的视频和知识");
        // 删除es
        List<String> basicId = kbBasicDBList.stream().map(item -> String.valueOf(item.getId())).collect(Collectors.toList());
        List<String> videoId = kbVideoDBList.stream().map(item -> String.valueOf(item.getId())).collect(Collectors.toList());
        basicId.addAll(videoId);
        searchService.deleteByBatchId(basicId);
        stopWatch.stop();
        log.debug(String.valueOf(stopWatch.getTotalTimeMillis()));
        log.debug("总过处理了知识size:[{}] 视频size:[{}] {}", kbBasicDBList.size(), kbVideoDBList.size(), stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }
}