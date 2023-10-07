#!/bin/bash

if [[ $# < 1 ]]; then
	echo "请输入jar包的名字"
else
	jar_name=$1
	OLD_IFS="$IFS" 
	IFS="-" 
	arr=($jar_name) 
	IFS="$OLD_IFS" 
	last_name=${arr[${#arr[@]}-1]}

	IFS="." 
	log_name=($last_name) 
	IFS="$OLD_IFS"
	log_name=${log_name[0]}	
	if [[ $# > 1 ]]; then
		profile_name=$2
	else
		profile_name=prod	
	fi
fi
echo "启动的jar包为：${jar_name},profiles:${profile_name},输出日志：log-${log_name}.log"
ps -ef |grep ${jar_name} |grep -v 'grep\|tail\|less\|more'|grep -v 'run' | grep -v 'rdc_deploy_command' | awk '{print $2}' | xargs kill -9
nohup java -jar ${jar_name} --spring.profiles.active=${profile_name} > log-${log_name}.log &
 