package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.entity.FeedbackEntity;
import com.castle.fortress.admin.system.dto.FeedbackDto;
import com.castle.fortress.admin.system.service.FeedbackService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;
import javax.servlet.http.HttpServletResponse;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import java.util.List;

/**
 * 意见反馈 控制器
 *
 * @author castle
 * @since 2022-11-01
 */
@Api(tags="意见反馈管理控制器")
@Controller
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    /**
     * 意见反馈的分页展示
     * @param feedbackDto 意见反馈实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("意见反馈分页展示")
    @GetMapping("/system/feedback/page")
    @ResponseBody
    @RequiresPermissions("system:feedback:pageList")
    public RespBody<IPage<FeedbackDto>> pageFeedback(FeedbackDto feedbackDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<FeedbackDto> page = new Page(pageIndex, pageSize);
        IPage<FeedbackDto> pages = feedbackService.pageFeedbackExtends(page, feedbackDto);

        return RespBody.data(pages);
    }

    /**
     * 意见反馈的列表展示
     * @param feedbackDto 意见反馈实体类
     * @return
     */
    @ApiOperation("意见反馈列表展示")
    @GetMapping("/system/feedback/list")
    @ResponseBody
    public RespBody<List<FeedbackDto>> listFeedback(FeedbackDto feedbackDto){
        List<FeedbackDto> list = feedbackService.listFeedback(feedbackDto);
        return RespBody.data(list);
    }

    /**
     * 意见反馈保存
     * @param feedbackDto 意见反馈实体类
     * @return
     */
    @ApiOperation("意见反馈保存")
    @PostMapping("/system/feedback/save")
    @ResponseBody
    @RequiresPermissions("system:feedback:save")
    public RespBody<String> saveFeedback(@RequestBody FeedbackDto feedbackDto){
        if(feedbackDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        FeedbackEntity feedbackEntity = ConvertUtil.transformObj(feedbackDto,FeedbackEntity.class);
        if(feedbackService.save(feedbackEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 意见反馈编辑
     * @param feedbackDto 意见反馈实体类
     * @return
     */
    @ApiOperation("意见反馈编辑")
    @PostMapping("/system/feedback/edit")
    @ResponseBody
    @RequiresPermissions("system:feedback:edit")
    public RespBody<String> updateFeedback(@RequestBody FeedbackDto feedbackDto){
        if(feedbackDto == null || feedbackDto.getId() == null || feedbackDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        FeedbackEntity feedbackEntity = ConvertUtil.transformObj(feedbackDto,FeedbackEntity.class);
        if(feedbackService.updateById(feedbackEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 意见反馈删除
     * @param id
     * @return
     */
    @ApiOperation("意见反馈删除")
    @PostMapping("/system/feedback/delete")
    @ResponseBody
    @RequiresPermissions("system:feedback:delete")
    public RespBody<String> deleteFeedback(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(feedbackService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 意见反馈批量删除
     * @param ids
     * @return
     */
    @ApiOperation("意见反馈批量删除")
    @PostMapping("/system/feedback/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:feedback:deleteBatch")
    public RespBody<String> deleteFeedbackBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(feedbackService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 意见反馈详情
     * @param id 意见反馈id
     * @return
     */
    @ApiOperation("意见反馈详情")
    @GetMapping("/system/feedback/info")
    @ResponseBody
    @RequiresPermissions("system:feedback:info")
    public RespBody<FeedbackDto> infoFeedback(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        FeedbackDto feedbackDto =  feedbackService.getByIdExtends(id);

        return RespBody.data(feedbackDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/system/feedback/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<FeedbackDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<FeedbackDto> list = feedbackService.listFeedback(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
