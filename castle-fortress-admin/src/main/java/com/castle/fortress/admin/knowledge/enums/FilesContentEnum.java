package com.castle.fortress.admin.knowledge.enums;


import com.castle.fortress.admin.knowledge.utis.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum FilesContentEnum {

    TXT("txtFileContentComparisonImpl"),
    File("txtFileContentComparisonImpl"),
    word("wordContentComparisonImpl"),
    ;
    private static final Map<String, FilesContentEnum> FILE_TYPE_MAPPER = new HashMap<>();
    private static final String[] OFFICE_TYPES = {"docx", "pdf","wps", "doc", "docm", "xls", "xlsx", "csv" ,"xlsm", "ppt", "pptx", "vsd", "rtf", "odt", "wmf", "emf", "dps", "et", "ods", "ots", "tsv", "odp", "otp", "sxi", "ott", "vsdx", "fodt", "fods", "xltx","tga","psd","dotm","ett","xlt","xltm","wpt","dot","xlam","dotx","xla","pages", "eps"};

    static {
        for (String office : OFFICE_TYPES) {
            FILE_TYPE_MAPPER.put(office, FilesContentEnum.word);
        }
        FILE_TYPE_MAPPER.put("txt", FilesContentEnum.TXT);
    }

    private final String instanceName;
    String name;

    FilesContentEnum(String instanceName) {
        this.instanceName = instanceName;
    }

    private static FilesContentEnum to(String fileType) {
        return FILE_TYPE_MAPPER.getOrDefault(fileType, FilesContentEnum.File);
    }

    public static FilesContentEnum typeFromFileName(String fileName) {
        String lowerCaseFileType = FileUtil.suffixFromFileName(fileName);
        return FilesContentEnum.to(lowerCaseFileType);
    }

    public String getInstanceName() {
        return instanceName;
    }
}
