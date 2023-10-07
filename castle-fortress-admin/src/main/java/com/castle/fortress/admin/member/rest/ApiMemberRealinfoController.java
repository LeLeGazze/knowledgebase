package com.castle.fortress.admin.member.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.castle.fortress.admin.icuapi.service.IcuApiService;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.member.dto.MemberRealinfoDto;
import com.castle.fortress.admin.member.entity.MemberRealinfoEntity;
import com.castle.fortress.admin.member.service.MemberRealinfoService;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 会员真实信息表 api 控制器
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Api(tags="会员真实信息表api管理控制器")
@RestController
@RequestMapping("/api/member/memberRealinfo")
public class ApiMemberRealinfoController {
    @Autowired
    private MemberRealinfoService memberRealinfoService;

    @Autowired
    private IcuApiService icuApiService;

    /**
     * 保存会员实名信息
     * @return
     */
    @ApiOperation("保存会员实名信息")
    @PostMapping("/save")
    @ResponseBody
    public RespBody save(@RequestBody MemberRealinfoDto memberRealinfoDto){
        if(memberRealinfoDto == null
                || StrUtil.isEmpty(memberRealinfoDto.getRealName())
                || StrUtil.isEmpty(memberRealinfoDto.getCardNum())){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberDto memberDto = WebUtil.currentMember();
        if (memberDto == null || memberDto.getId() == null){
            return RespBody.fail("获取登录信息失败,请重新登录");
        }

        // 二要素验证
        RespBody<JSONObject> respBody = icuApiService.idCardVerification(memberRealinfoDto.getRealName() , memberRealinfoDto.getCardNum());
        if (respBody.getCode() == 0){
            if ("0".equals(respBody.getData().getStr("Result"))){
                // 表示验证成功
            }else {
                return RespBody.fail(respBody.getData().getStr("Description"));
            }
        }else {
            return RespBody.fail("身份证验证异常");
        }
        // 查询有无已存在的真实信息
        MemberRealinfoDto realInfo = memberRealinfoService.getByMemberId(memberDto.getId());
        if (realInfo != null) {
            memberRealinfoDto.setId(realInfo.getId());
        }
        memberRealinfoDto.setMemberId(memberDto.getId());
        if (memberRealinfoService.saveOrUpdate(ConvertUtil.transformObj(memberRealinfoDto , MemberRealinfoEntity.class))){
            return RespBody.success("操作成功");
        }else {
            return RespBody.fail("保存失败");
        }


    }

    /**
     * 身份证ocr识别
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "FrontImageBase64", value = "正面图片的 Base64 值 （与FrontImageUrl必传其一，都传则只使用FrontImageUrl）", paramType = "body", required = false, dataType = "String"),
            @ApiImplicitParam(name = "FrontImageUrl", value = "正面图片的 Url 地址（与FrontImageBase64必传其一，都传则只使用FrontImageUrl）", paramType = "body", required = false, dataType = "String"),
            @ApiImplicitParam(name = "BackImageBase64", value = "反面图片的 Base64 值 （与BackImageUrl必传其一，都传则只使用BackImageUrl）", paramType = "body", required = false, dataType = "String"),
            @ApiImplicitParam(name = "BackImageUrl", value = "反面图片的 Url 地址（与BackImageBase64必传其一，都传则只使用BackImageUrl）", paramType = "body", required = false, dataType = "String"),
    })
    @ApiOperation("身份证ocr识别")
    @PostMapping("/icCardOcr")
    @ResponseBody
    public RespBody icCardOcr(@RequestBody Map<String , String> params){
        if(params == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        String frontImageBase64 = params.get("FrontImageBase64");
        String frontImageUrl = params.get("FrontImageUrl");
        String backImageBase64 = params.get("BackImageBase64");
        String backImageUrl = params.get("BackImageUrl");
        if (StrUtil.isEmpty(frontImageBase64)
                && StrUtil.isEmpty(frontImageUrl)
                && StrUtil.isEmpty(backImageBase64)
                && StrUtil.isEmpty(backImageUrl)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (frontImageBase64 == null){
            frontImageBase64 = "";
        }
        if (frontImageUrl == null){
            frontImageUrl = "";
        }
        if (backImageBase64 == null){
            backImageBase64 = "";
        }
        if (backImageUrl == null){
            backImageUrl = "";
        }

        return icuApiService.idCardOcr(frontImageBase64,frontImageUrl,backImageBase64,backImageUrl);
    }



    /**
     * 身份证二要素验证
     * @return
     */
    @ApiOperation("身份证二要素验证")
    @PostMapping("/icCardVerify")
    @ResponseBody
    public RespBody infoMember(@RequestBody Map<String , String> params){
        if(params == null || StrUtil.isEmpty(params.get("name")) || StrUtil.isEmpty(params.get("idCard"))){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        String name = params.get("name");
        String idCard = params.get("idCard");
        return icuApiService.idCardVerification(name , idCard);
    }

}
