package com.castle.fortress.admin.check.service.impl;

import com.castle.fortress.admin.check.service.FileReadService;
import com.castle.fortress.admin.knowledge.service.FileContentComparison;
import com.castle.fortress.admin.knowledge.service.FilePreview;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service
public class FileReadFactory {

    private final ApplicationContext context;

    public FileReadFactory(ApplicationContext context) {
        this.context = context;
    }

    public FileReadService get(String beanName) {
        return context.getBean(beanName, FileReadService.class);
    }

}
