package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysCaptchaDto;
import com.castle.fortress.admin.system.entity.SysCaptchaEntity;

import java.util.List;

/**
 * 手机验证码 服务类
 *
 * @author castle
 * @since 2021-07-13
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {

    /**
     * 分页展示手机验证码列表
     * @param page
     * @param sysCaptchaDto
     * @return
     */
    IPage<SysCaptchaDto> pageSysCaptcha(Page<SysCaptchaDto> page, SysCaptchaDto sysCaptchaDto);


    /**
     * 展示手机验证码列表
     * @param sysCaptchaDto
     * @return
     */
    List<SysCaptchaDto> listSysCaptcha(SysCaptchaDto sysCaptchaDto);

		/**
		 * 通过手机号修改
		 * @param sysCaptchaDto
		 * @return
		 */
		boolean editByPhone(SysCaptchaDto sysCaptchaDto);

		/**
		 * 发送短信验证码
		 * @param phone
		 * @return
		 */
		String initCaptcha(String phone);

		/**
		 * 校验验证码
		 * @param phone
		 * @param captcha
		 * @return
		 */
		String verifySysCaptcha(String phone, String captcha);
}
