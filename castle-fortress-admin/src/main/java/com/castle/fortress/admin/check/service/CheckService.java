package com.castle.fortress.admin.check.service;

import com.castle.fortress.admin.check.dto.KbDuplicateCheckDto;
import com.castle.fortress.admin.check.entity.KbDuplicateCheckEntity;

/**
 * 查重处理类
 */
public interface CheckService {

    /**
     * 查重任务处理类
     */
    void taskProcessing(KbDuplicateCheckDto kbDuplicateCheckDto,Long userId);
}
