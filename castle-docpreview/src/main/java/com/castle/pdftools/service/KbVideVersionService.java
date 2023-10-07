package com.castle.pdftools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.pdftools.entity.KbVideVersionEntity;

import java.util.Map;

public interface KbVideVersionService extends IService<KbVideVersionEntity> {
    void kbVideVersionService(String bid);
    public void updateToPdf(KbVideVersionEntity kbVideVersionEntity);
    public  void taskAdd(String bid);
    public  boolean isTaskRunning();
    public void  taskRun();

    void taskAddWordPdf(Map<String, String> queryMap);

    void initRun();
}
