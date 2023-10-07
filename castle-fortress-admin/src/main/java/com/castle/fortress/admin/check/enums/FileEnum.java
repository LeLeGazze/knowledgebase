package com.castle.fortress.admin.check.enums;
import com.castle.fortress.admin.knowledge.utis.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum FileEnum {

    TXT("textFileReadServiceImpl"), // 处理txt类型数据
    WORD("wordFileReadServiceImpl"), // 处理word类型数据
    DEFAULT("defaultFileReadServiceImpl"), // 其他
    ;
    private static final Map<String, FileEnum> FILE_TYPE_MAPPER = new HashMap<>();
    static {
        FILE_TYPE_MAPPER.put("txt", FileEnum.TXT);
        FILE_TYPE_MAPPER.put("docx", FileEnum.WORD);

    }

    private final String instanceName;
    String name;

    FileEnum(String instanceName) {
        this.instanceName = instanceName;
    }

    private static FileEnum to(String fileType) {
        return FILE_TYPE_MAPPER.getOrDefault(fileType, FileEnum.DEFAULT);
    }

    public static FileEnum typeFromFileName(String fileName) {
        String lowerCaseFileType = FileUtil.suffixFromFileName(fileName);
        return FileEnum.to(lowerCaseFileType);
    }

    public String getInstanceName() {
        return instanceName;
    }
}
