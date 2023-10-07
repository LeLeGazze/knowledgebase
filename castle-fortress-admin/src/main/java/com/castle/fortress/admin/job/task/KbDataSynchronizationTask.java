package com.castle.fortress.admin.job.task;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.entity.KbDataSynchronizationTaskEntity;
import com.castle.fortress.admin.knowledge.service.KbBasicService;
import com.castle.fortress.admin.knowledge.service.KbDataSynchronizationTaskService;
import com.castle.fortress.admin.knowledge.utis.ConcurrentStopWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务同步数据发布知识
 */
@Component("KbDataSynchronizationTask")
@Slf4j
public class KbDataSynchronizationTask implements ITask {

    @Autowired
    private KbDataSynchronizationTaskService kbDataSynchronizationTaskService;
    private KbBasicService kbBasicService;

    /**
     * 定时任务
     *
     * @param params 参数，多参数使用JSON数据
     */
    @Override
    public void runTask(String params) {
        // 运行shell 脚本
        if (StrUtil.isEmpty(params)) {
            log.debug("");
            return;
        }
        ConcurrentStopWatch stopWatch = new ConcurrentStopWatch("定时任务同步SqlServer更新数据");
        JSONObject parameter = JSONObject.parseObject(params);
        // 同步数据使用kettle
        stopWatch.start("使用kettle同步数据");
        String kettleSh = parameter.getString("kettleSh");
        shell(kettleSh); // 运行kettle将SqlServer 数据同步到自己的MySQL上
        stopWatch.stop("使用kettle同步数据");


        stopWatch.start("运行shell脚本拷贝文件");
        shell(parameter.getString("command")); // 运行shell脚本 将昨天上传的数据文件拷贝到指定目录 并添加上后缀
        stopWatch.stop("运行shell脚本拷贝文件");


        //获取出task 表有表是需要进行同步的
        stopWatch.start("获取任务数据");
        List<KbDataSynchronizationTaskEntity> kbDataSynchronizationTaskEntitieList = kbDataSynchronizationTaskService.getStatus(1);
        stopWatch.stop("获取任务数据");

        if (kbDataSynchronizationTaskEntitieList.size() == 0) {
            log.debug("没有查询到任务");
            return;
        }
        //创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(kbDataSynchronizationTaskEntitieList.size());
        CountDownLatch countDownLatch = new CountDownLatch(kbDataSynchronizationTaskEntitieList.size());
        for (KbDataSynchronizationTaskEntity kbDataSynchronizationTaskEntity : kbDataSynchronizationTaskEntitieList) {
            executorService.execute(() -> {
                try {
                    stopWatch.start(kbDataSynchronizationTaskEntity.getType());
                    // 数据查询
                    String taskEntitySql = kbDataSynchronizationTaskEntity.getSql();
                    // SQL脚本运行
                    List<Map<String, Object>> data = kbDataSynchronizationTaskService.runSQL(taskEntitySql);
                    for (Map<String, Object> mapItem : data) {
                        //创建对象构建参数
                        KbModelAcceptanceDto kbModelAcceptanceDto = new KbModelAcceptanceDto();
                        kbModelAcceptanceDto.setTitle(String.valueOf(mapItem.get("title")));
                        kbModelAcceptanceDto.setAuth(kbDataSynchronizationTaskEntity.getAuth());
                        kbModelAcceptanceDto.setDeptId(kbDataSynchronizationTaskEntity.getDept());
                        kbModelAcceptanceDto.setPubTime(new Date());
                        kbModelAcceptanceDto.setAttachments(JSONArray.parseArray(String.valueOf(mapItem.get("attachments"))));
                        kbModelAcceptanceDto.setCategoryId(kbDataSynchronizationTaskEntity.getCategoryId());
                        kbModelAcceptanceDto.setRemark(String.valueOf(mapItem.get("title")));
                        kbModelAcceptanceDto.setModelId(kbDataSynchronizationTaskEntity.getModeId());
                        kbModelAcceptanceDto.setLabel(new ArrayList<>());
                        kbModelAcceptanceDto.setStatus(1);
                        JSONObject jsonObject = new JSONObject(true);
                        jsonObject.put("id", "");
                        jsonObject.put("data_id", "");
                        int index = 1;
                        for (Map.Entry<String, Object> entry : mapItem.entrySet()) {
                            String mapKey = entry.getKey();
                            Object mapValue = entry.getValue();
                            if (mapKey.equals("attachments")) {
                                mapValue = null;
                            }
                            jsonObject.put("col_" + (index + 1), mapValue == null ? "" : mapValue);
                            index++;
                        }
                        kbModelAcceptanceDto.setCols(jsonObject);
                        kbBasicService.saveAll(kbModelAcceptanceDto); // 写入数据库
                        System.out.println(JSONObject.toJSON(kbModelAcceptanceDto));
                    }
                    // 备份插入到历史表
                } finally {
                    stopWatch.stop(kbDataSynchronizationTaskEntity.getType());
                    countDownLatch.countDown();

                }
            });
        }
        try {
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }


    /**
     * 运行shell 交脚本
     *
     * @param command 执行文件路径
     */
    public void shell(String command) {
        try {
            // 定义要执行的命令

            // 创建 ProcessBuilder 对象
            ProcessBuilder processBuilder = new ProcessBuilder();

            // 设置命令和工作目录
            processBuilder.command("bash", "-c", command);
            processBuilder.directory(null); // 可选：设置工作目录

            // 启动进程并等待其完成
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 输出命令退出码
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printElapsedTime(long elapsedTime, long totalTime) {
        double percentage = (double) elapsedTime * 100 / totalTime;
        log.debug(String.format("线程运行时间（毫秒）：%d, 百分比：%.2f%%", elapsedTime, percentage));
    }
}
