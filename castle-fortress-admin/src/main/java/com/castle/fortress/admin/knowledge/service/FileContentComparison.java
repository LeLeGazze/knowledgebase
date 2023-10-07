package com.castle.fortress.admin.knowledge.service;

import java.util.List;

public interface FileContentComparison {

    /**
     * 文件内容对比
     * @param filePath 读取文件的路径
     * @return
     */
    List<String> fileContentHandle(String filePath );
}
