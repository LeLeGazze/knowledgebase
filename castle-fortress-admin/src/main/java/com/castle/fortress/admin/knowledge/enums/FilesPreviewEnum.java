package com.castle.fortress.admin.knowledge.enums;


import com.castle.fortress.admin.knowledge.utis.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum FilesPreviewEnum {

    FILE("filePreviewImpl"),
    MD("macDownFilePreviewImpl"),
    XML("filePreviewImpl"),
    XLSX("execlFilePreviewImpl"),
    ;
    private static final Map<String, FilesPreviewEnum> FILE_TYPE_MAPPER = new HashMap<>();
    static {
        FILE_TYPE_MAPPER.put("txt", FilesPreviewEnum.FILE);
        FILE_TYPE_MAPPER.put("xml", FilesPreviewEnum.XML);
        FILE_TYPE_MAPPER.put("log", FilesPreviewEnum.FILE);
        FILE_TYPE_MAPPER.put("md", FilesPreviewEnum.MD);
        FILE_TYPE_MAPPER.put("xlsx", FilesPreviewEnum.XLSX);
    }

    private final String instanceName;
    String name;

    FilesPreviewEnum(String instanceName) {
        this.instanceName = instanceName;
    }

    private static FilesPreviewEnum to(String fileType) {
        return FILE_TYPE_MAPPER.get(fileType);
    }

    public static FilesPreviewEnum typeFromFileName(String fileName) {
        String lowerCaseFileType = FileUtil.suffixFromFileName(fileName);
        return FilesPreviewEnum.to(lowerCaseFileType);
    }

    public String getInstanceName() {
        return instanceName;
    }
}
