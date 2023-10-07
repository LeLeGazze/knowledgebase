package com.castle.pdftools.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.pdftools.entity.KbVideVersionEntity;
import com.castle.pdftools.mapper.KbVideVersionMapper;
import com.castle.pdftools.service.KbVideVersionService;
import com.castle.pdftools.utils.FileUtil;
import com.castle.pdftools.utils.PdfUtils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
public class KbVideoVersionServiceImpl extends ServiceImpl<KbVideVersionMapper, KbVideVersionEntity> implements KbVideVersionService {

    @Autowired
    private PdfUtils pdfUtils;
    private boolean taskRun;
    private int maxTaskNum = 3;
    private ConcurrentLinkedQueue<Callable<Boolean>> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    @Override
    @Async
    public void kbVideVersionService(String bid) {
        // 根据bid 查询出有哪些需要转换的
        List<KbVideVersionEntity> kbVideVersionEntityList = baseMapper.selectByBid(bid);
        if (kbVideVersionEntityList == null || kbVideVersionEntityList.size() == 0) {
            System.out.println("没有该转换的内容文章id:" + bid);
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(kbVideVersionEntityList.size());
        //使用的计数器
//        CountDownLatch countDownLatch = new CountDownLatch(kbVideVersionEntityList.size());
        kbVideVersionEntityList.forEach(item -> {
            executorService.execute(() -> {
                try {
                    this.updateToPdf(item);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //计算器减去1
//                    countDownLatch.countDown();
                }
            });
        });
    }

    @Transactional
    public void updateToPdf(KbVideVersionEntity kbVideVersionEntity) {
        try {
            //修改状态
            kbVideVersionEntity.setStatus(2);
            baseMapper.updateById(kbVideVersionEntity);
            String filePath = kbVideVersionEntity.getFileUrl();
            File file = new File(filePath);
            long length = file.length();
            String md5 = SecureUtil.md5(file);
            kbVideVersionEntity.setFId(md5);
            String formatSize = FileUtil.formatSize(length);
            kbVideVersionEntity.setFileSize(formatSize); // 设置文件大小
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
            String pdfName = filePath;
            if (!filePath.endsWith(".pdf")) {
                //校验文件类型如果不是PDF则需要进行转换成PDF在调用
                pdfUtils.toPdf(filePath, filePath.substring(0, filePath.lastIndexOf("/")) + "/pdf");
                pdfName = filePath.substring(0, filePath.lastIndexOf("/")) + "/pdf" + "/" + fileName + ".pdf";
            }
//            int page = pdfUtils.pdf2Image(pdfName,filePath.substring(0, filePath.lastIndexOf("/")) + "/png", 105);
//            List<String> pngUrl = pdfUtils.getPngUrl(pnfName, kbVideVersionEntity.getAccessPath(), page);
//            kbVideVersionEntity.setUrl(JSONUtil.toJsonStr(pngUrl));
            kbVideVersionEntity.setStatus(1);
            kbVideVersionEntity.setDownloadUrl(pdfName);
            kbVideVersionEntity.setUpdateTime(new Date());
            baseMapper.updateById(kbVideVersionEntity);
        } catch (Exception e) {
            e.printStackTrace();
            kbVideVersionEntity.setStatus(3);
            kbVideVersionEntity.setFId(null);
            kbVideVersionEntity.setFileSize(null); // 设置文件大小
            kbVideVersionEntity.setUrl(null);
            kbVideVersionEntity.setUpdateTime(new Date());
            baseMapper.updateById(kbVideVersionEntity);
        }
    }

    @Override
    public void taskAdd(String bid) {
        concurrentLinkedQueue.add(() -> {
            // 根据bid 查询出有哪些需要转换的
            List<KbVideVersionEntity> kbVideVersionEntityList = baseMapper.selectByBid(bid);
            if (kbVideVersionEntityList == null || kbVideVersionEntityList.size() == 0) {
                System.out.println("没有该转换的内容文章id:" + bid);
                return false;
            }
            ExecutorService executorService = Executors.newFixedThreadPool(kbVideVersionEntityList.size());
            //使用的计数器
            CountDownLatch countDownLatch = new CountDownLatch(kbVideVersionEntityList.size());
            kbVideVersionEntityList.forEach(item -> {
                executorService.execute(() -> {
                    try {
                        this.updateToPdf(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //计算器减去1
                        countDownLatch.countDown();
                    }
                });
            });
            try {
                countDownLatch.await();
                executorService.shutdown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
        });
        System.out.println("添加任务成功，" + bid + " 有" + concurrentLinkedQueue.size() + "任务需要处理");
    }

    @Override
    public boolean isTaskRunning() {
        return this.taskRun;
    }


    @Override
    public void taskRun() {
        taskRun = true;
        int runTaskNumber = Math.min(maxTaskNum, concurrentLinkedQueue.size());
        for (int i = 0; i < runTaskNumber; i++) {
            new Thread(() -> {
                while (true) {
                    Callable<Boolean> poll = concurrentLinkedQueue.poll();
                    Boolean call = null;
                    try {
                        if (poll == null) {
                            taskRun = false;
                            System.out.println("所有的任务全部处理完成");
                            break;
                        }
                        call = poll.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("正在处理中......." + Thread.currentThread().getName() + ",还剩下" + concurrentLinkedQueue.size() + "任务需要处理" + call);
                }
            }, "pdfTask" + i).start();
        }

    }

    @Override
    public void taskAddWordPdf(Map<String, String> queryMap) {
        concurrentLinkedQueue.add(() -> {
            String inputPath = queryMap.get("inputPath");
            String outPutDir = queryMap.get("outPutDir");
            pdfUtils.toPdf(inputPath, outPutDir);
            return true;
        });
        System.out.println("添加任务成功，word 转PDF " + queryMap.get("fileName") + " 有" + concurrentLinkedQueue.size() + "任务需要处理");
    }

    /**
     * 程序启动 加载之前未成功或未执行的数据
     */
    @Override
    public void initRun() {
        // 根据bid 查询出有哪些需要转换的
        List<KbVideVersionEntity> kbVideVersionEntityList = baseMapper.selectByStatus(1);
        if (kbVideVersionEntityList != null || kbVideVersionEntityList.size() != 0) {
            kbVideVersionEntityList.forEach(item -> {
                concurrentLinkedQueue.add(() -> {
                    this.updateToPdf(item);
                    return true;
                });
            });
        }
        if (!taskRun && kbVideVersionEntityList.size() != 0) {
            taskRun();
        }
        System.out.println("初始化任务成功，有" + concurrentLinkedQueue.size() + "任务需要处理");
    }

}