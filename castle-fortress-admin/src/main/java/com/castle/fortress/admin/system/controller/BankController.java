package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.entity.BankEntity;
import com.castle.fortress.admin.system.dto.BankDto;
import com.castle.fortress.admin.system.service.BankService;
import com.castle.fortress.common.enums.BankCardTypeEnum;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.respcode.BizErrorCode;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 银行信息 控制器
 *
 * @author castle
 * @since 2022-11-02
 */
@Api(tags="银行信息管理控制器")
@Controller
public class BankController {
    @Autowired
    private BankService bankService;

    /**
     * 银行信息的分页展示
     * @param bankDto 银行信息实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="银行类型分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("银行信息分页展示")
    @GetMapping("/system/bank/page")
    @ResponseBody
    @RequiresPermissions("system:bank:pageList")
    public RespBody<IPage<BankDto>> pageBank(BankDto bankDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<BankDto> page = new Page(pageIndex, pageSize);
        IPage<BankDto> pages = bankService.pageBank(page, bankDto);

        return RespBody.data(pages);
    }

    /**
     * 银行信息的列表展示
     * @param bankDto 银行信息实体类
     * @return
     */
    @CastleLog(operLocation="银行类型",operType= OperationTypeEnum.QUERY)
    @ApiOperation("银行信息列表展示")
    @GetMapping("/system/bank/list")
    @ResponseBody
    public RespBody<List<BankDto>> listBank(BankDto bankDto){
        List<BankDto> list = bankService.listBank(bankDto);
        return RespBody.data(list);
    }

    /**
     * 银行信息保存
     * @param bankDto 银行信息实体类
     * @return
     */
    @CastleLog(operLocation="银行类型保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("银行信息保存")
    @PostMapping("/system/bank/save")
    @ResponseBody
    @RequiresPermissions("system:bank:save")
    public RespBody<String> saveBank(@RequestBody BankDto bankDto){
        if(bankDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        BankEntity bankEntity = ConvertUtil.transformObj(bankDto,BankEntity.class);
        if(bankService.save(bankEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 银行信息编辑
     * @param bankDto 银行信息实体类
     * @return
     */
    @CastleLog(operLocation="银行类型编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("银行信息编辑")
    @PostMapping("/system/bank/edit")
    @ResponseBody
    @RequiresPermissions("system:bank:edit")
    public RespBody<String> updateBank(@RequestBody BankDto bankDto){
        if(bankDto == null || bankDto.getId() == null || bankDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        BankEntity bankEntity = ConvertUtil.transformObj(bankDto,BankEntity.class);
        if(bankService.updateById(bankEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 银行信息删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="银行类型删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("银行信息删除")
    @PostMapping("/system/bank/delete")
    @ResponseBody
    @RequiresPermissions("system:bank:delete")
    public RespBody<String> deleteBank(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(bankService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 银行信息批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="银行类型批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("银行信息批量删除")
    @PostMapping("/system/bank/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:bank:deleteBatch")
    public RespBody<String> deleteBankBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(bankService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 银行信息详情
     * @param id 银行信息id
     * @return
     */
    @CastleLog(operLocation="银行类型详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("银行信息详情")
    @GetMapping("/system/bank/info")
    @ResponseBody
    @RequiresPermissions("system:bank:info")
    public RespBody<BankDto> infoBank(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        BankEntity bankEntity = bankService.getById(id);
        BankDto bankDto = ConvertUtil.transformObj(bankEntity,BankDto.class);

        return RespBody.data(bankDto);
    }


    /**
     * 根据银行卡号获取所属银行等信息
     * @param cardNum 银行信息id
     * @return
     */
    @CastleLog(operLocation="银行类型详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("银行信息详情")
    @GetMapping("/system/bank/getByNum")
    @ResponseBody
    public RespBody<BankDto> getByNum(@RequestParam String cardNum){
        if(StrUtil.isEmpty(cardNum)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //调用支付宝接口获取 所属银行简码等信息
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo="+cardNum+"&cardBinCheck=true";
        String result = HttpUtil.get(url);
        System.err.println(result);
        JSONObject json = JSONUtil.parseObj(result);
        System.err.println(json.get("validated",Boolean.class));
        if (json.get("validated",Boolean.class)){
            System.err.println("true");
            String cardType = (String) json.get("cardType");
            String bankCode = (String) json.get("bank");
//            根据bank 获取所属银行信息
            QueryWrapper<BankEntity> wrapper= new QueryWrapper();
            wrapper.eq("bank_code",bankCode);
            wrapper.last("limit 0,1");
            BankEntity entity = bankService.getOne(wrapper);
            BankDto bankDto = ConvertUtil.transformObj(entity,BankDto.class);
            bankDto.setCardType(BankCardTypeEnum.getCodeByDesc(cardType));
            return RespBody.data(bankDto);
        }
        else{
            return RespBody.fail(BizErrorCode.NO_BANK_DATA);
        }




    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="银行类型导出",operType= OperationTypeEnum.EXPORT)
	@PostMapping("/system/bank/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<BankDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<BankDto> list = bankService.listBank(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}

}
