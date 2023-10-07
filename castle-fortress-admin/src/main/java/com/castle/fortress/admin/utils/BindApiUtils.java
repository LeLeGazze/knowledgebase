package com.castle.fortress.admin.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.service.ConfigApiService;
import com.castle.fortress.common.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 框架绑定api工具类
 * @author castle
 */
@Service
public class BindApiUtils {
	@Autowired
	private ConfigApiService configApiService;
	//绑定的接口配置
	private static Map<String, ConfigApiDto> bindDataMap=new HashMap<>();

	/**
	 * 获取配置信息 优先获取缓存中的信息，无则查库并放入缓存
	 * @param code
	 * @return
	 */
	public ConfigApiDto getData(String code){
		ConfigApiDto dto = bindDataMap.get(code);
		if(dto==null){
			ConfigApiDto configApiDto = new ConfigApiDto();
			configApiDto.setBindCode(code);
			List<ConfigApiDto> list = configApiService.listConfigApi(configApiDto);
			if(list != null && list.size() == 1 && list.get(0) != null){
				dto = list.get(0);
				bindDataMap.put(code,dto);
			}
		}
		return dto;
	}

	/**
	 * 全量刷新
	 */
	public void refreshAll(){
		List<ConfigApiDto> list = configApiService.listConfigApi(null);
		bindDataMap.clear();
		for(ConfigApiDto dto:list){
			bindDataMap.put(dto.getBindCode(),dto);
		}
	}

	/**
	 * 部分刷新
	 */
	public void refreshSome(List<ConfigApiDto> list){
		for(ConfigApiDto dto:list){
			if("01".equals(dto.getGroupCode())&&dto.getParamMap()!=null && dto.getParamMap().get("options")!=null){
				Map<String,Object> paramMap = dto.getParamMap();
				paramMap.put("options", JSONUtil.parseArray(JSONUtil.toJsonStr(paramMap.get("options"))));
				dto.setParamMap(paramMap);
			}
			bindDataMap.put(dto.getBindCode(),dto);
		}
	}

	/**
	 * 刷新一个
	 */
	public void refreshOne(ConfigApiDto dto){
		if(!CommonUtil.verifyParamNull(dto,dto.getBindCode(),dto.getParamMap())){
			if("01".equals(dto.getGroupCode())&&dto.getParamMap()!=null && dto.getParamMap().get("options")!=null){
				Map<String,Object> paramMap = dto.getParamMap();
				paramMap.put("options", JSONUtil.parseArray(JSONUtil.toJsonStr(paramMap.get("options"))));
				dto.setParamMap(paramMap);
			}
			bindDataMap.put(dto.getBindCode(),dto);
		}
	}

	/**
	 * 校验接口配置是否启用
	 * @param apiCode
	 * @return
	 */
	public boolean isOpen(String apiCode){
		ConfigApiDto dto = getData(apiCode);
		if(dto ==null ){
			return false;
		}
		Map<String,Object> map = dto.getParamMap();
		if(map == null || map.isEmpty()){
			return false;
		}
		//平台类型
		if("00".equals(dto.getGroupCode())){
			if(CommonUtil.verifyParamEmpty(map.get("secretId"),map.get("secretKey"))){
				return false;
			}
			if("PLAT_TENCENT".equals(dto.getBindCode())){
				if(CommonUtil.verifyParamEmpty(map.get("appId"))){
					return false;
				}
			}
			return true;
		//api接口类型
		}else if("01".equals(dto.getGroupCode())){
			if(CommonUtil.verifyParamEmpty(map.get("platform"),map.get("options"))){
				return false;
			}
			JSONArray ja = (JSONArray)map.get("options");
			boolean isLegal= false;
			for(int i=0;i<ja.size();i++){
				if(map.get("platform").equals(ja.getJSONObject(i).get("code"))){
					return isOpen(map.get("platform").toString());
				}
			}
			return isLegal;
		//自带配置的接口
		}else if("02".equals(dto.getGroupCode())){
			for(String keys:map.keySet()){
				if(CommonUtil.verifyParamEmpty(map.get(keys))){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 获取apiCode的启用平台相关配置信息
	 * @param apiCode
	 * @return
	 */
	public ConfigApiDto getApiPlatform(String apiCode){
		ConfigApiDto apiDto = getData(apiCode);
		if("01".equals(apiDto.getGroupCode())){
			String groupCode = apiDto.getParamMap().get("platform").toString();
			if(isOpen(groupCode)){
				return getData(groupCode);
			}
		}
		return null;
	}



}
