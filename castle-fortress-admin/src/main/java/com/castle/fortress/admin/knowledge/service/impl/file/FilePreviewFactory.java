package com.castle.fortress.admin.knowledge.service.impl.file;

import com.castle.fortress.admin.knowledge.service.FileContentComparison;
import com.castle.fortress.admin.knowledge.service.FilePreview;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class FilePreviewFactory {

    private final ApplicationContext context;

    public FilePreviewFactory(ApplicationContext context) {
        this.context = context;
    }

    public FileContentComparison get(String beanName) {
        return context.getBean(beanName, FileContentComparison.class);
    }

    public FilePreview getPreview(String beanName) {
        return context.getBean(beanName, FilePreview.class);
    }
}
