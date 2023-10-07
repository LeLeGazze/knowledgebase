package com.castle.fortress.admin.system.bindapi.controller;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.system.bindapi.service.BindApiService;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.utils.BindApiUtils;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 绑定的第三方api 控制器
 *
 * @author
 */
@Api(tags="绑定的第三方api")
@Controller
public class BindApiController {
	@Autowired
	private BindApiUtils bindApiUtils;
	@Autowired
	private BindApiService bindApiService;

	/**
	 * 校验api是否已开启
	 * @param apiCode api编码
	 * @return
	 */
	@ApiOperation("校验api是否已开启")
	@GetMapping("/bindapi/isopen")
	@ResponseBody
	public RespBody<Boolean> isopen(@RequestParam String apiCode) {
		if(CommonUtil.verifyParamEmpty(apiCode)){
			throw new BizException(GlobalRespCode.PARAM_MISSED);
		}
		return RespBody.data(bindApiUtils.isOpen(apiCode));
	}

	/**
	 * 关键词提取
	 * @param map
	 * 		key: text: 文本内容 必填 ；num：关键词数量 非必填 默认5
	 * @return
	 */
	@ApiOperation("关键词提取")
	@PostMapping("/bindapi/keywordsExtraction")
	@ResponseBody
	public RespBody<String> keywordsExtraction(@RequestBody Map<String,Object> map){
		if(CommonUtil.verifyParamNull(map,map.get("text"))){
			throw new BizException(GlobalRespCode.PARAM_MISSED);
		}
		if(bindApiUtils.isOpen("API_KEYWORDSEXTRACTION")){
			ConfigApiDto dto = bindApiUtils.getApiPlatform("API_KEYWORDSEXTRACTION");
			if(dto == null){
				throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
			}
			String platform = dto.getBindCode();
			String text = map.get("text").toString();
			Long num =5L;
			try {
				if(map.get("num")!=null){
					num =Long.parseLong(map.get("num").toString());
				}
			} catch (NumberFormatException e) {
			}
			String[] s = bindApiService.keywordsExtraction(platform,text,num);
			StringBuilder sb = new StringBuilder();
			if(s!=null&&s.length>0){
				for(int i=0;i<s.length;i++){
					sb.append(s[i]);
					if(i!=s.length-1){
						sb.append(",");
					}
				}
			}
			return RespBody.data(sb.toString());
		}else{
			throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
		}
	}

	/**
	 * 自动摘要
	 * @param map
	 * 		key: text: 文本内容 必填 ；num：关键词数量 非必填 默认200
	 * @return
	 */
	@ApiOperation("自动摘要")
	@PostMapping("/bindapi/autoSummarization")
	@ResponseBody
	public RespBody<String> autoSummarization(@RequestBody Map<String,Object> map){
		if(CommonUtil.verifyParamNull(map,map.get("text"))){
			throw new BizException(GlobalRespCode.PARAM_MISSED);
		}
		if(bindApiUtils.isOpen("API_AUTOSUMMARIIZATION")){
			ConfigApiDto dto = bindApiUtils.getApiPlatform("API_AUTOSUMMARIIZATION");
			if(dto == null){
				throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
			}
			String platform = dto.getBindCode();
			String text = map.get("text").toString();
			Long num =200L;
			try {
				if(map.get("num")!=null){
					num =Long.parseLong(map.get("num").toString());
				}
			} catch (NumberFormatException e) {
			}
			String s = bindApiService.autoSummarization(platform,text,num);
			return RespBody.data(s);
		}else{
			throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
		}
	}

	/**
	 * 文本纠错
	 * @param map
	 * 		key: text: 文本内容 必填
	 * @return
	 */
	@ApiOperation("文本纠错")
	@PostMapping("/bindapi/textCorrection")
	@ResponseBody
	public RespBody<Map<String,Object>> textCorrection(@RequestBody Map<String,Object> map){
		Map<String,Object> result = new HashMap<>();
		if(CommonUtil.verifyParamNull(map,map.get("text"))){
			throw new BizException(GlobalRespCode.PARAM_MISSED);
		}
		if(bindApiUtils.isOpen("API_TEXTCORRECTION")){
			ConfigApiDto dto = bindApiUtils.getApiPlatform("API_TEXTCORRECTION");
			if(dto == null){
				throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
			}
			String platform = dto.getBindCode();
			String text = map.get("text").toString();
			result = bindApiService.textCorrection(platform,text);
		}else{
			throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
		}
		return RespBody.data(result);
	}

	/**
	 * 文本审核
	 * @param map
	 * 		key: text: 文本内容 必填；detectType 审核场景
	 * @return
	 */
	@ApiOperation("文本审核")
	@PostMapping("/bindapi/textAudit")
	@ResponseBody
	public RespBody<Map<String,Object>> textAudit(@RequestBody Map<String,Object> map){
		Map<String,Object> result = new HashMap<>();
		if(CommonUtil.verifyParamNull(map,map.get("text"))){
			throw new BizException(GlobalRespCode.PARAM_MISSED);
		}
		if(bindApiUtils.isOpen("API_TEXTAUDIT")){
			ConfigApiDto dto = bindApiUtils.getApiPlatform("API_TEXTAUDIT");
			if(dto == null){
				throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
			}
			String platform = dto.getBindCode();
			ConfigApiDto apiDto = bindApiUtils.getData("API_TEXTAUDIT");
			if(apiDto == null || apiDto.getParamMap()==null || apiDto.getParamMap().get("detectType")==null || StrUtil.isEmpty(apiDto.getParamMap().get("detectType").toString())){
				throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
			}
			String text = map.get("text").toString();
			String detectType = apiDto.getParamMap().get("detectType").toString();
			result = bindApiService.textAudit(platform,text,detectType);
		}else{
			throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
		}
		return RespBody.data(result);
	}

	/**
	 * 获取实时语音输入websocket链接 官网文档 https://cloud.tencent.com/document/product/1093/48982
	 * @return
	 */
	@ApiOperation("获取实时语音输入websocket链接")
	@GetMapping("/bindapi/getAsrWs")
	@ResponseBody
	public RespBody<String> getAsrWs(@RequestParam Map<String , String> params){
		String result="";
		if (StrUtil.isEmpty(params.get("engineModelType")) || StrUtil.isEmpty(params.get("voiceFormat"))){
			return RespBody.fail("参数错误");
		}
		String engineModelType = params.get("engineModelType");
		String voiceFormat = params.get("voiceFormat");
		if(bindApiUtils.isOpen("API_ASRWSURL")){
			ConfigApiDto dto = bindApiUtils.getApiPlatform("API_ASRWSURL");
			if(dto == null){
				throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
			}
			String platform = dto.getBindCode();
			result = bindApiService.getAsrWs(platform,engineModelType,voiceFormat);
		}else{
			throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
		}
		return RespBody.data(result);
	}


}
