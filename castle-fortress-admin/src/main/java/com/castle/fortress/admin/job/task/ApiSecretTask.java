package com.castle.fortress.admin.job.task;

import com.castle.fortress.admin.system.service.ApiSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Api对外开放秘钥定时任务
 * 每天凌晨1点执行
 **/
@Component("apiSecretTask")
public class ApiSecretTask implements ITask{
	@Autowired
	private ApiSecretService apiSecretService;
	@Override
	public void runTask(String params){
		//将当前过期的秘钥状态设置为失效
		//过期时间为空则永不过期
		apiSecretService.expiredSecrets();
	}
}