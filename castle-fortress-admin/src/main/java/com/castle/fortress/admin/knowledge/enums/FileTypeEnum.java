package com.castle.fortress.admin.knowledge.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    TXT(1, "txt", "word"),
    PDF(2, "pdf", "pdf"),
    DOC(3, "doc", "word"),
    DOCM(4, "docm", "word"),
    DOCX(5, "docx", "word"),
    DOT(6, "dot", "word"),
    DOTX(7, "dotx", "word"),
    MD(9, "md", "word"),
    DOTM(10, "dotm", "word"),
    XLS(11, "xls", "excel"),
    XLSX(12, "xlsx", "excel"),
    XLSM(13, "xlsm", "excel"),
    XLTM(14, "xltx", "excel"),
    XLSB(15, "xlsb", "excel"),
    XLAM(16, "xlam", "excel"),
    PPTX(8, "pptx", "ppt"),
    PPT(17, "ppt", "ppt"),
    PPTM(18, "pptm", "ppt"),
    PPSX(19, "ppsx", "ppt"),
    PPSM(20, "ppsm", "ppt"),
    POTX(21, "potx", "ppt"),
    POTM(22, "potm", "ppt"),
    PPAM(23, "ppam", "ppt"),
    ;
    Integer code;
    String name;
    String desc;
}
