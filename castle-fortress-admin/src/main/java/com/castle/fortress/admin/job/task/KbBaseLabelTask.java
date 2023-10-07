package com.castle.fortress.admin.job.task;

import cn.hutool.core.date.StopWatch;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.mapper.KbBaseLabelTaskMapper;
import com.castle.fortress.admin.knowledge.mapper.KbBasicLabelMapper;
import com.castle.fortress.admin.knowledge.mapper.KbModelLabelMapper;
import com.castle.fortress.admin.knowledge.service.KbModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 每天定时更新es里面的数据
 **/
@Component("KbBaseLabelTask")
@Slf4j
public class KbBaseLabelTask implements ITask {

    @Autowired
    private KbBaseLabelTaskMapper kbBaseLabelTaskMapper;
    @Autowired
    private KbBasicLabelMapper kbBasicLabelMapper;
    @Autowired
    private KbModelService kbModelService;
    @Autowired
    private EsSearchService searchService;

    @Override
    public void runTask(String params) {
        StopWatch stopWatch = new StopWatch("定时修改es标签Task");
        stopWatch.start("findAllLabel");
        // 查询所有多少标签需要修改
        List<KbBaseLabelTaskEntity> kbBaseLabelTaskEntities = kbBaseLabelTaskMapper.findByStatus(2);
        if (kbBaseLabelTaskEntities.size() == 0) {
            log.debug("未查询到标签有修改：不需要同步删除：date：【{}】", new Date());
            return;
        }
        stopWatch.stop();
        stopWatch.start("findByLabelIdToBase");
        List<Long> lablelIdList = kbBaseLabelTaskEntities.stream().map(item -> item.getLId()).collect(Collectors.toList());
        List<Map<String, Object>> byLabelIdToBasic = kbBasicLabelMapper.findByLabelIdToBasic(lablelIdList);
        stopWatch.stop();
        stopWatch.start("修改es中的标签数据");
        // 修改任务记录用来标识
        kbBaseLabelTaskMapper.updateByIds(kbBaseLabelTaskEntities);
        HashMap<String, String> LabelMap = new HashMap<>();
        List<String> bidList = byLabelIdToBasic.stream().map(item -> String.valueOf(item.get("bid"))).collect(Collectors.toList());
        byLabelIdToBasic.forEach(item -> LabelMap.put(String.valueOf(item.get("bid")), String.valueOf(item.get("name"))));
        String[] ids = bidList.toArray(new String[bidList.size()]);
        List<Object> objects = searchService.updateByIdToLabel(LabelMap, ids);
        stopWatch.stop();
        stopWatch.start("修改task表并且把成功标签删掉");
        List<Long> libList = kbBaseLabelTaskEntities.stream().map(item -> item.getLId()).collect(Collectors.toList());
//         删除任务表
        kbBaseLabelTaskMapper.deleteByBid(libList);
        // 删除标签中间表lib
        kbBasicLabelMapper.deleteByBid(libList);
        // 删除标签表lib
        kbModelService.removeByIds(libList);
//        for (Object obj : objects) {
//            if (obj instanceof ArrayList<?>) {
//                ArrayList<?> arrayList = (ArrayList<?>) obj;
//                if (arrayList.size() > 0) {
//                    Object element = arrayList.get(0);
//                    if (element instanceof Long) {
//                        // 处理成功的
//                        // 删除任务表
//                        kbBaseLabelTaskMapper.deleteByBid((ArrayList<Long>) arrayList);
//                        // 删除标签中间表lib
//                        kbBasicLabelMapper.deleteByBid((ArrayList<Long>) arrayList);
//                        // 删除标签表lib
//                        kbModelService.removeByIds((ArrayList<Long>) arrayList);
//
//                    } else if (element instanceof KbBaseLabelTaskEntity) {
////                        //处理失败  有一个失败的task表就不能成功
////                        // 根据bid 查询有哪个lid的
////                        List<KbBaseLabelTaskEntity> labelTaskEntities = kbBasicLabelMapper.findByBid((ArrayList<KbBaseLabelTaskEntity>) arrayList);
////                        // 根据lid 获取 task表是否有没有被删除
////                        List<Map<String, Object>> tmpFindLidByMap = kbBaseLabelTaskMapper.findByLid(labelTaskEntities);
////                        Map<Object, Object> findLidByMap = tmpFindLidByMap.stream().collect(Collectors.toMap(item -> item.get("id"), it -> it.get("l_id")));
////                        if (findLidByMap.size() > 0) {
////                            //保存新增数据
////                            List<KbBaseLabelTaskEntity> installList = new ArrayList<>();
////                            //保存修改数据
////                            List<KbBaseLabelTaskEntity> updateList = new ArrayList<>();
////
////                            for (KbBaseLabelTaskEntity kbBaseLabelTaskEntity : (ArrayList<KbBaseLabelTaskEntity>) arrayList) {
////
////                            }
////
////                        }
////                        System.out.println("ArrayList<KbBaseLabelTaskEntity> found");
//                    }
//                }
//            }
//        }
        stopWatch.stop();
        log.debug(String.valueOf(stopWatch.getTotalTimeMillis()));
        log.debug(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }
}