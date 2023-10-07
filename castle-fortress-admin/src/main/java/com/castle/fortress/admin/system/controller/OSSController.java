package com.castle.fortress.admin.system.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.ConfigOssDto;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.entity.ConfigOssEntity;
import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.enums.OssEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件传输控制器 控制器
 * @author castle
 */
@Api(tags = "文件传输控制器")
@RestController
public class OSSController {

    @Autowired
    private ConfigOssService configOssService;

    /**
     * 文件传输配置表的分页展示
     * @param configOssDto 文件传输配置表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="文件存储分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("文件传输配置表分页展示")
    @GetMapping("/system/configOss/page")
    @ResponseBody
    @RequiresPermissions("system:configOss:pageList")
    public RespBody<IPage<ConfigOssDto>> pageConfigFtDto(ConfigOssDto configOssDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigOssDto> page = new Page(pageIndex, pageSize);
        IPage<ConfigOssDto> pages = configOssService.pageConfigFt(page, configOssDto);
        return RespBody.data(pages);
    }

    /**
     * 文件传输配置保存
     * @param configOssDto 文件传输配置实体类
     * @return
     */
    @CastleLog(operLocation="文件存储配置保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("文件传输配置保存")
    @PostMapping("/system/configOss/save")
    @ResponseBody
    @RequiresPermissions("system:configOss:save")
    public RespBody<String> saveConfigFt(@RequestBody ConfigOssDto configOssDto){
        if(configOssDto == null || configOssDto.getOssPlatFormDto() == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigOssEntity configOss = ConvertUtil.transformObj(configOssDto, ConfigOssEntity.class);
        if(YesNoEnum.YES.getCode().equals(configOss.getStatus())){
            configOssService.updataStatusNo();
        }
        configOss.setPtConfig(JSONUtil.toJsonStr(configOssDto.getOssPlatFormDto()));
        if(configOssService.save(configOss)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 文件传输配置编辑
     * @param configOssDto 文件传输配置实体类
     * @return
     */
    @CastleLog(operLocation="文件存储配置编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("文件传输配置编辑")
    @PostMapping("/system/configOss/edit")
    @ResponseBody
    @RequiresPermissions("system:configOss:edit")
    public RespBody<String> updateConfigFt(@RequestBody ConfigOssDto configOssDto){
        if(configOssDto == null || configOssDto.getId() == null || configOssDto.getId().equals(0L) || configOssDto.getOssPlatFormDto() == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigOssEntity configOss = ConvertUtil.transformObj(configOssDto, ConfigOssEntity.class);
        if(YesNoEnum.YES.getCode().equals(configOss.getStatus())){
            configOssService.updataStatusNo();
        }
        configOss.setPtConfig(JSONUtil.toJsonStr(configOssDto.getOssPlatFormDto()));
        if(configOssService.updateById(configOss)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 删除该文件传输配置及子集
     * @param id 文件传输配置id
     * @return
     */
    @CastleLog(operLocation="文件存储配置删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("文件传输配置删除")
    @PostMapping("/system/configOss/delete")
    @ResponseBody
    @RequiresPermissions("system:configOss:delete")
    public RespBody<String> deleteConfigFt(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configOssService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 文件传输配置详情
     * @param id 文件传输配置id
     * @return
     */
    @CastleLog(operLocation="文件存储详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("文件传输配置详情")
    @GetMapping("/system/configOss/info")
    @ResponseBody
    @RequiresPermissions("system:configOss:info")
    public RespBody<ConfigOssDto> infoConfigFt(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigOssEntity configOssEntity = configOssService.getById(id);
        ConfigOssDto ftDto = ConvertUtil.transformObj(configOssEntity, ConfigOssDto.class);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(ftDto.getPtConfig(), OssPlatFormDto.class);
        ftDto.setOssPlatFormDto(ossPlatFormDto);
        return RespBody.data(ftDto);
    }


    /**
     * 获取文件访问路径前缀
     * @return
     */
    @CastleLog(operLocation="文件访问路径",operType= OperationTypeEnum.QUERY)
    @ApiOperation("获取文件访问路径前缀")
    @GetMapping("/system/oss/prefixUrl")
    @ResponseBody
    public RespBody<String> prefixFileUrl(){
        ConfigOssDto ftDto = new ConfigOssDto();
        ftDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigOssDto> ftDtoList = configOssService.selectBySelective(ftDto);
        if(ftDtoList ==null || ftDtoList.size() != 1){
            return RespBody.data(null);
        }
        String prefixUrl="";
        ConfigOssDto dto = ftDtoList.get(0);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(dto.getPtConfig(), OssPlatFormDto.class);
        if(OssEnum.SYS_LOCAL.getCode().equals(dto.getPlatform())){
            prefixUrl= ossPlatFormDto.getLocalFileUrl();
        }
        return RespBody.data(prefixUrl);
    }


    /**
     * ckeditor图片上传、粘贴上传
     * @return
     */
    @CastleLog(operLocation="文件粘贴上传",operType= OperationTypeEnum.UPLOAD)
    @ApiOperation("文件上传")
    @PostMapping({"/system/oss/ckupload","/system/oss/ckupload&responseType=json"})
    public String ckupload(@RequestParam("upload") MultipartFile file){
        if (file.isEmpty()) {
            throw new BizException(BizErrorCode.FT_UPLOAD_EMPTY_ERROR);
        }
        JSONObject jb= new JSONObject();
        RespBody<Map<String, Object>> rb = configOssService.putFile(file);
        if(rb.isSuccess()){
            jb.set("uploaded",1);
            jb.set("url",rb.getData().get("url"));
        }else{
            jb.set("uploaded",0);
            JSONObject error= new JSONObject();
            error.set("message",rb.getMsg());
            jb.set("error",error);
        }
        return jb.toString();

    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @CastleLog(operLocation="文件上传",operType= OperationTypeEnum.UPLOAD)
    @ApiOperation("文件上传")
    @PostMapping("/system/oss/upload")
    public RespBody<Map> upload(@RequestParam("upfile") MultipartFile file){
        if (file.isEmpty()) {
            throw new BizException(BizErrorCode.FT_UPLOAD_EMPTY_ERROR);
        }
        return configOssService.putFile(file);

    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @CastleLog(operLocation="文件上传",operType= OperationTypeEnum.UPLOAD)
    @ApiOperation("文件上传")
    @PostMapping("/system/oss/uploadFile")
    public RespBody<Map> uploadVideo(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            throw new BizException(BizErrorCode.FT_UPLOAD_EMPTY_ERROR);
        }
        return configOssService.putFile(file);

    }

    @CastleLog(operLocation="文件上传",operType= OperationTypeEnum.UPLOAD)
    @ApiOperation("文件上传")
    @PostMapping("/system/oss/uploadFileRead")
    public RespBody uploadFileRead(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            throw new BizException(BizErrorCode.FT_UPLOAD_EMPTY_ERROR);
        }
        return configOssService.putFileRead(file);

    }
}
