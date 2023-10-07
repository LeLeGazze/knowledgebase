package com.castle.fortress.admin.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.utils.BindApiUtils;
import com.castle.fortress.admin.utils.CaptchaUtils;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 验证码 控制器
 *
 * @author
 */
@Api(tags="验证码控制器")
@Controller
public class CaptchaController {

		@Value("${castle.imageCaptcha.imagePath}")
		private String imagePath;
		@Autowired
		private RedisUtils redisUtils;
		@Value("${castle.imageCaptcha.isOpen}")
		private Boolean isOpen;
		@Value("${castle.imageCaptcha.imageType}")
		private String imageType;
		@Autowired
		private BindApiUtils bindApiUtils;

		/**
		 * @param @return 参数说明
		 * @return BaseRestResult 返回类型
		 * @Description: 生成滑块拼图验证码
		 */
		@ApiOperation("获取验证码")
		@GetMapping("/captcha/get")
		@ResponseBody
		public RespBody<Map<String, Object>> getImageVerifyCode() {
				Map<String, Object> resultMap = new HashMap<>();
				//读取本地路径下的图片,随机选一条
				File file = new File(imagePath);
				File[] files = file.listFiles();
				int n = new Random().nextInt(files.length);
				File imageUrl = files[n];
				CaptchaUtils.createImage(imageUrl, resultMap);
				//读取网络图片
				//ImageUtil.createImage("http://hbimg.b0.upaiyun.com/7986d66f29bfeb6015aaaec33d33fcd1d875ca16316f-2bMHNG_fw658",resultMap);
				String id = IdUtil.simpleUUID();
				redisUtils.set(id,resultMap.get("xWidth").toString(),3L, TimeUnit.MINUTES);
				resultMap.remove("xWidth");
				resultMap.put("captchaId",id);
				return RespBody.data(resultMap);
		}

		/**
		 * 校验滑块拼图验证码
		 * @param captchaId 验证码id
		 * @param moveLength 移动距离
		 * @return
		 */
		@ApiOperation("校验验证码")
		@GetMapping("/captcha/verify")
		@ResponseBody
		public RespBody verifyImageCode(@RequestParam(value = "captchaId") String captchaId,@RequestParam(value = "moveLength") String moveLength) {
				if(CommonUtil.verifyParamNull(captchaId,moveLength)){
						throw new BizException(GlobalRespCode.PARAM_MISSED);
				}
				Double dMoveLength = Double.valueOf(moveLength);
				try {
						Integer xWidth = redisUtils.get(captchaId)==null?null:Integer.valueOf(redisUtils.get(captchaId).toString());
						if (xWidth == null) {
								return RespBody.fail(GlobalRespCode.CAPTCHA_EXPIRED_ERROR);
						}
						if (Math.abs(xWidth - dMoveLength) > 10) {
								return RespBody.fail(GlobalRespCode.CAPTCHA_VERIFY_ERROR);
						}
				} catch (Exception e) {
						return RespBody.fail(GlobalRespCode.FAIL);
				} finally {
					redisUtils.remove(captchaId);
				}
				return RespBody.data("验证通过");
		}

	/**
	 * @param @return 参数说明
	 * @return BaseRestResult 返回类型
	 * @Description: 生成数字图形验证码
	 */
	@ApiOperation("获取数字图形验证码")
	@GetMapping("/captcha/getNumber")
	@ResponseBody
	public RespBody<Map<String, Object>> getImageNumberVerifyCode() {
		Map<String, Object> resultMap = new HashMap<>();

		//定义图形验证码的长、宽、验证码字符数、干扰元素个数
		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
		//验证图形验证码的有效性，返回boolean值
		String code = captcha.getCode();
		String image = captcha.getImageBase64();
		String id = IdUtil.simpleUUID();
		redisUtils.set(id,code,3L, TimeUnit.MINUTES);//3分钟有效
		resultMap.put("captchaId",id);
		resultMap.put("image",image);
		return RespBody.data(resultMap);
	}

	/**
	 * 校验数字图形验证码
	 * @param captchaId 验证码id
	 * @param code 验证码数值
	 * @return
	 */
	@ApiOperation("校验数字图形验证码")
	@GetMapping("/captcha/verifyNumber")
	@ResponseBody
	public RespBody verifyImageNumberCode(@RequestParam(value = "captchaId") String captchaId,@RequestParam(value = "code") String code) {
		if(CommonUtil.verifyParamNull(captchaId,code)){
			throw new BizException(GlobalRespCode.PARAM_MISSED);
		}
		try {
			String verifyCode = redisUtils.get(captchaId)==null?null:redisUtils.get(captchaId).toString();
			if (verifyCode == null) {
				return RespBody.fail(GlobalRespCode.CAPTCHA_EXPIRED_ERROR);
			}
			if (!code.equals(verifyCode)) {
				return RespBody.fail(GlobalRespCode.CAPTCHA_VERIFY_ERROR);
			}
		} catch (Exception e) {
			return RespBody.fail(GlobalRespCode.FAIL);
		} finally {
			redisUtils.remove(captchaId);
		}
		return RespBody.data("验证通过");
	}

	/**
	 * 校验腾讯验证码
	 * @param code 验证码数值
	 * @param request
	 * @return
	 */
	@ApiOperation("校验腾讯验证码")
	@GetMapping("/captcha/verifyTencent")
	@ResponseBody
	public RespBody verifyTencent(@RequestParam(value = "captchaId") String captchaId,@RequestParam(value = "code") String code, HttpServletRequest request) {
		if(CommonUtil.verifyParamEmpty(code)){
			throw new BizException(GlobalRespCode.PARAM_MISSED);
		}
		if(bindApiUtils.isOpen("PLAT_TENCENT") && bindApiUtils.isOpen("SELF_CAPTCHA")){
			ConfigApiDto platTencent = bindApiUtils.getData("PLAT_TENCENT");
			ConfigApiDto selfCaptcha = bindApiUtils.getData("SELF_CAPTCHA");
			Credential cred = new Credential(platTencent.getParamMap().get("secretId").toString(), platTencent.getParamMap().get("secretKey").toString());
			CaptchaClient captchaClient = new CaptchaClient(cred,"");
			try {
				DescribeCaptchaResultRequest req = initTencentCaptchaReq(captchaId,code,selfCaptcha,request);
				DescribeCaptchaResultResponse resp =  captchaClient.DescribeCaptchaResult(req);
				if(resp.getCaptchaCode().equals(1L)){
					return RespBody.data("验证通过");
				}else{
					return RespBody.fail(GlobalRespCode.CAPTCHA_VERIFY_ERROR);
				}
			} catch (TencentCloudSDKException e) {
				return RespBody.fail(GlobalRespCode.CAPTCHA_VERIFY_ERROR);
			}
		}else{
			throw new BizException(GlobalRespCode.DB_DATA_ERROR);
		}
	}

	@ApiOperation("获取验证码配置信息")
	@GetMapping("/captcha/loginConfig")
	@ResponseBody
	public RespBody<Map> loginCaptchaConfig(){
		Map<String,Object> map=new HashMap<>();
		map.put("isOpen",isOpen);
		map.put("imageType",imageType);
		//如果开启了腾讯云的验证码，优先使用腾讯云的验证码
		if(isOpen && bindApiUtils.isOpen("PLAT_TENCENT") && bindApiUtils.isOpen("SELF_CAPTCHA") ){
			map.put("imageType","3");
		}
		RespBody<Map> respBody=RespBody.data(map);
		return respBody;

	}

	private DescribeCaptchaResultRequest initTencentCaptchaReq(String captchaId,String code,ConfigApiDto selfCaptcha,HttpServletRequest request){
		DescribeCaptchaResultRequest req = new DescribeCaptchaResultRequest();
		req.setCaptchaType(9L);
		req.setTicket(code);
		req.setUserIp(WebUtil.getIpAddress(request));
		req.setRandstr(captchaId);
		req.setCaptchaAppId(Long.parseLong(selfCaptcha.getParamMap().get("captchaAppId").toString()));
		req.setAppSecretKey(selfCaptcha.getParamMap().get("appSecretKey").toString());
		return req;
	}
}
