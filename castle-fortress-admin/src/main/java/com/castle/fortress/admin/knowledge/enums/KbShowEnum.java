package com.castle.fortress.admin.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 17725
 * @create 2023/5/5 15:34
 */

@Getter
@AllArgsConstructor
public enum KbShowEnum {
    COMMENTS(1,"评论"),
    UPLOAD(2,"上传"),
    DOWNLOAD(3,"下载"),
    BROWSE(4,"浏览"),
    ;
    Integer code;
    String name;
}

