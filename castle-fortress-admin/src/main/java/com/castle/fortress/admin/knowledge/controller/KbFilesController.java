package com.castle.fortress.admin.knowledge.controller;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.es.EsFileDto;
import com.castle.fortress.admin.es.EsFileRepository;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 知识库索引记录 控制器
 *
 * @author castle
 * @since 2023-03-22
 */
@Api(tags = "知识库索引记录管理控制器")
@Controller
public class KbFilesController {
    @Autowired
    private EsFileRepository esFileRepository;
    @Autowired
    private EsSearchService esSearchService;

    /**
     * 知识库文件表的列表展示
     *
     * @param queryMap 查询参数map表
     * @return
     */
    @CastleLog(operLocation = "知识库文件表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库文件表列表展示")
    @GetMapping("/knowledge/es/search")
    @ResponseBody
    public RespBody<SearchHits<EsFileDto>> searchEsFiles(@RequestParam Map<String, String> queryMap) {
        SearchHits<EsFileDto> list = esSearchService.queryByMap(queryMap);
        return RespBody.data(list);
    }

    /**
     * 知识库文件表的列表展示
     *
     * @return
     */
    @CastleLog(operLocation = "知识库文件表", operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库文件表列表展示")
    @GetMapping("/knowledge/es/searchAll")
    @ResponseBody
    public RespBody<List<EsFileDto>> searchAllEsFiles() {
        List<EsFileDto> list = new ArrayList<>();
        Iterable<EsFileDto> iterable = esFileRepository.findAll();
        iterable.forEach(esFileDto -> {
            list.add(esFileDto);
        });
        return RespBody.data(list);
    }

    /**
     * 知识库文件表保存
     *
     * @param fileDto 知识库文件表实体类
     * @return
     */
    @CastleLog(operLocation = "知识库文件表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识库文件表保存")
    @PostMapping("/knowledge/es/save")
    @ResponseBody
    public RespBody<String> saveEsFiles(@RequestBody EsFileDto fileDto) {
        if (fileDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        fileDto.setFileDate(new Date());
        esFileRepository.save(fileDto);
        return RespBody.data("保存成功");
    }

    /**
     * 知识库文件表保存
     *
     * @param kbModelAcceptanceDto 知识库文件表实体类
     * @return
     */
    @CastleLog(operLocation = "知识库文件表", operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识库文件表保存")
    @PostMapping("/knowledge/es/saveTest")
    @ResponseBody
    public RespBody<String> saveTestEsFiles(@RequestBody KbModelAcceptanceDto kbModelAcceptanceDto) {
        if (kbModelAcceptanceDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        esSearchService.asyncSaveES(kbModelAcceptanceDto);
        return RespBody.data("保存成功");
    }

    /**
     * 知识库文件表编辑
     *
     * @param fileDto 知识库文件表实体类
     * @return
     */
    @CastleLog(operLocation = "知识库文件表", operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识库文件表编辑")
    @PostMapping("/knowledge/es/edit")
    @ResponseBody
    public RespBody<String> updateEsFiles(@RequestBody EsFileDto fileDto) {
        if (fileDto == null || StrUtil.isEmpty(fileDto.getId())) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        esFileRepository.delete(fileDto);
        esFileRepository.save(fileDto);
        return RespBody.data("保存成功");
    }

    /**
     * 知识库文件表删除
     *
     * @param id
     * @return
     */
    @CastleLog(operLocation = "知识库文件表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库文件表删除")
    @PostMapping("/knowledge/es/delete")
    @ResponseBody
    public RespBody<String> deleteEsFiles(@RequestParam String id) {
        if (StrUtil.isEmpty(id)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        esFileRepository.deleteById(id);
        return RespBody.data("保存成功");
    }

    /**
     * 知识库文件表删除
     *
     * @return
     */
    @CastleLog(operLocation = "知识库文件表", operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库文件表删除")
    @PostMapping("/knowledge/es/deleteAll")
    @ResponseBody
    public RespBody<String> deleteEsAllFiles() {
        esFileRepository.deleteAll();
        return RespBody.data("保存成功");
    }
}
