package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.entity.BankEntity;
import com.castle.fortress.admin.system.dto.BankDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 银行信息 服务类
 *
 * @author castle
 * @since 2022-11-02
 */
public interface BankService extends IService<BankEntity> {

    /**
     * 分页展示银行信息列表
     * @param page
     * @param bankDto
     * @return
     */
    IPage<BankDto> pageBank(Page<BankDto> page, BankDto bankDto);


    /**
     * 展示银行信息列表
     * @param bankDto
     * @return
     */
    List<BankDto> listBank(BankDto bankDto);

}
