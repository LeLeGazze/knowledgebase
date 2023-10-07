package com.castle.fortress.admin.knowledge.controller;

import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelDto;
import com.castle.fortress.admin.knowledge.dto.KbPropertyDesignDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.service.*;
import com.castle.fortress.admin.knowledge.service.impl.KbBasicServiceImpl;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * kb模型数据表 控制器
 *
 * @author sunhr
 * @since 2023-04-24
 */
@Api(tags = "kb模型数据表管理控制器")
@Controller
public class KbModelDataController {
    @Autowired
    private KbModelService kbModelService;
    @Autowired
    private KbColConfigService kbColConfigService;
    @Autowired
    private KbBasicService kbBasicService;
    /**
     * kb模型数据表单信息
     *
     * @param kbModelDto
     * @return
     */
    @ApiOperation("kb模型数据表单信息")
    @GetMapping("/kb/modelData/formInfo")
    @ResponseBody
    @RequiresPermissions("kb:modelData:formInfo")
    public RespBody<KbModelDto> formInfo(KbModelDto kbModelDto) {
        if (kbModelDto == null || kbModelDto.getId() == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelEntity modelEntity = kbModelService.getById(kbModelDto.getId());
        KbPropertyDesignDto kbPropertyDesignDto = new KbPropertyDesignDto();
        kbPropertyDesignDto.setModelId(kbModelDto.getId());
        List<KbPropertyDesignDto> cols = kbColConfigService.listCmsColConfig(kbPropertyDesignDto);
        KbModelDto dto = ConvertUtil.transformObj(modelEntity, KbModelDto.class);
        dto.setCols(cols);
        return RespBody.data(dto);
    }

    /**
     * 数据沉淀
     *
     * @param formDataDto 表模型
     * @return
     */
    @ApiOperation("沉淀数据")
    @PostMapping("/kb/modelData/saveData")
    @ResponseBody
    public RespBody<String> saveData(@RequestBody KbModelAcceptanceDto formDataDto) {
        if (CommonUtil.verifyParamNull(formDataDto, formDataDto.getModelId())) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        formDataDto.setCreateUser(Objects.requireNonNull(WebUtil.currentUser()).getId());
        //保存全部
        boolean basic = kbBasicService.saveAll(formDataDto);
        if (basic) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }

    }


}
