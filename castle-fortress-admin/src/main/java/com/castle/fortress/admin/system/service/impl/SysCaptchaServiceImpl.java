package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.message.sms.service.SmsService;
import com.castle.fortress.admin.system.dto.SysCaptchaDto;
import com.castle.fortress.admin.system.entity.SysCaptchaEntity;
import com.castle.fortress.admin.system.mapper.SysCaptchaMapper;
import com.castle.fortress.admin.system.service.SysCaptchaService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 手机验证码 服务实现类
 *
 * @author castle
 * @since 2021-07-13
 */
@Service
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaMapper, SysCaptchaEntity> implements SysCaptchaService {
    @Value("${castle.captcha.seconds}")
    private Integer captchaSeconds;
    @Value("${castle.captcha.smsCode}")
    private String captchaSmsCode;
    @Autowired
    private SmsService smsService;
    /**
    * 获取查询条件
    * @param sysCaptchaDto
    * @return
    */
    public QueryWrapper<SysCaptchaEntity> getWrapper(SysCaptchaDto sysCaptchaDto){
        QueryWrapper<SysCaptchaEntity> wrapper= new QueryWrapper();
        if(sysCaptchaDto != null){
            SysCaptchaEntity sysCaptchaEntity = ConvertUtil.transformObj(sysCaptchaDto,SysCaptchaEntity.class);
            wrapper.like(StrUtil.isNotEmpty(sysCaptchaEntity.getPhone()),"phone",sysCaptchaEntity.getPhone());
            wrapper.like(StrUtil.isNotEmpty(sysCaptchaEntity.getCaptcha()),"captcha",sysCaptchaEntity.getCaptcha());
        }
        wrapper.orderByDesc("create_time");
        return wrapper;
    }


    @Override
    public IPage<SysCaptchaDto> pageSysCaptcha(Page<SysCaptchaDto> page, SysCaptchaDto sysCaptchaDto) {
        QueryWrapper<SysCaptchaEntity> wrapper = getWrapper(sysCaptchaDto);
        Page<SysCaptchaEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysCaptchaEntity> sysCaptchaPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysCaptchaDto> pageDto = new Page(sysCaptchaPage.getCurrent(), sysCaptchaPage.getSize(),sysCaptchaPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysCaptchaPage.getRecords(),SysCaptchaDto.class));
        return pageDto;
    }


    @Override
    public List<SysCaptchaDto> listSysCaptcha(SysCaptchaDto sysCaptchaDto){
        QueryWrapper<SysCaptchaEntity> wrapper = getWrapper(sysCaptchaDto);
        List<SysCaptchaEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,SysCaptchaDto.class);
    }

    @Override
    public boolean editByPhone(SysCaptchaDto sysCaptchaDto) {
        if(sysCaptchaDto.getCreateTime() == null){
            sysCaptchaDto.setCreateTime(new Date());
        }
        SysCaptchaDto queryDto = new SysCaptchaDto();
        queryDto.setPhone(sysCaptchaDto.getPhone());
        List<SysCaptchaDto> list = listSysCaptcha(queryDto);
        //该手机号未发送验证码
        if(list==null || list.isEmpty()){
            return save(ConvertUtil.transformObj(sysCaptchaDto,SysCaptchaEntity.class));
        }else{
            SysCaptchaDto oldData = list.get(0);
            //更新验证码
            oldData.setCaptcha(sysCaptchaDto.getCaptcha());
            oldData.setCreateTime(sysCaptchaDto.getCreateTime());
            return updateById(ConvertUtil.transformObj(oldData,SysCaptchaEntity.class));
        }
    }

    @Override
    public String initCaptcha(String phone) {
        SysCaptchaDto sysCaptchaDto = new SysCaptchaDto();
        sysCaptchaDto.setPhone(phone);
        List<SysCaptchaDto> list = listSysCaptcha(sysCaptchaDto);
        String captcha = CommonUtil.getRandomString(6,CommonUtil.RANGE5);
        if(list == null || list.isEmpty()){
            sysCaptchaDto.setCaptcha(captcha);
            sysCaptchaDto.setCreateTime(new Date());
            save(ConvertUtil.transformObj(sysCaptchaDto,SysCaptchaEntity.class));
        }else{
            if(list.size() != 1){
                throw new BizException(GlobalRespCode.DB_DATA_ERROR);
            }
            SysCaptchaDto oldData = list.get(0);
            //验证码过期
            if(DateUtil.between(oldData.getCreateTime(),new Date(), DateUnit.SECOND) > captchaSeconds){
                //更新验证码
                oldData.setCaptcha(captcha);
                oldData.setCreateTime(new Date());
                updateById(ConvertUtil.transformObj(oldData,SysCaptchaEntity.class));
            }else{
                captcha = oldData.getCaptcha();
            }
        }
        //发送验证码
        RespBody rb =  smsService.send(captchaSmsCode,phone,"{\"code\":\""+captcha+"\"}");
        if(rb.isSuccess()){
            return "发送成功";
        }else{
            throw new BizException(BizErrorCode.SMS_SEND_ERROR);
        }
    }

    @Override
    public String verifySysCaptcha(String phone, String captcha) {
        SysCaptchaDto sysCaptchaDto = new SysCaptchaDto();
        sysCaptchaDto.setPhone(phone);
        sysCaptchaDto.setCaptcha(captcha);
        List<SysCaptchaDto> list = listSysCaptcha(sysCaptchaDto);
        if(list==null || list.size() != 1){
            throw new BizException(GlobalRespCode.CAPTCHA_CODE_VERIFY_ERROR);
        }
        if(DateUtil.between(list.get(0).getCreateTime(),new Date(), DateUnit.SECOND) > captchaSeconds){
            throw new BizException(GlobalRespCode.CAPTCHA_CODE_EXPIRED_ERROR);
        }
        return  "校验成功";
    }
}

