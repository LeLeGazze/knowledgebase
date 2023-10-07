package com.castle.fortress.common.entity;

import java.io.Serializable;

/**
 * 返回码接口
 * @author Dawn
 */
public interface IRespCode extends Serializable {
    /**
     * 获取返回码
     * @return
     */
    int getCode();

    /**
     * 获取返回码信息
     * @return
     */
    String getMsg();

    void setMsg(String msg);

}
