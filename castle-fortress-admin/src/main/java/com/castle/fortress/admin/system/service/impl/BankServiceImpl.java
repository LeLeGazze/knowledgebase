package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.entity.BankEntity;
import com.castle.fortress.admin.system.dto.BankDto;
import com.castle.fortress.admin.system.mapper.BankMapper;
import com.castle.fortress.admin.system.service.BankService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 银行信息 服务实现类
 *
 * @author castle
 * @since 2022-11-02
 */
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, BankEntity> implements BankService {
    /**
    * 获取查询条件
    * @param bankDto
    * @return
    */
    public QueryWrapper<BankEntity> getWrapper(BankDto bankDto){
        QueryWrapper<BankEntity> wrapper= new QueryWrapper();
        if(bankDto != null){
            BankEntity bankEntity = ConvertUtil.transformObj(bankDto,BankEntity.class);
            wrapper.like(StrUtil.isNotEmpty(bankEntity.getBankName()),"bank_name",bankEntity.getBankName());
            wrapper.like(StrUtil.isNotEmpty(bankEntity.getBankCode()),"bank_code",bankEntity.getBankCode());
        }
        return wrapper;
    }


    @Override
    public IPage<BankDto> pageBank(Page<BankDto> page, BankDto bankDto) {
        QueryWrapper<BankEntity> wrapper = getWrapper(bankDto);
        Page<BankEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<BankEntity> bankPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<BankDto> pageDto = new Page(bankPage.getCurrent(), bankPage.getSize(),bankPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(bankPage.getRecords(),BankDto.class));
        return pageDto;
    }


    @Override
    public List<BankDto> listBank(BankDto bankDto){
        QueryWrapper<BankEntity> wrapper = getWrapper(bankDto);
        List<BankEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,BankDto.class);
    }
}

