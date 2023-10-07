package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.common.entity.EnumEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 枚举数据 控制器
 * @author castle
 */
@Api(tags = "枚举列表控制器")
@RestController
@RequestMapping("/system/enums")
public class EnumController {

    /**
     * 获取指定模块下的枚举列表数据
     * @param enumName 枚举类名去除后缀Enum 如 DataPermissionDept等
     * @param moduleName
     * @return
     */
    @ApiOperation("获取指定模块下的枚举列表数据")
    @GetMapping("/list")
    public RespBody<List> queryEnumsList(String enumName,@RequestParam(required = false) String moduleName){
        List<EnumEntity> list=new ArrayList<>();
        String className="com.castle.fortress.common.enums.";
        //通用模块
        if(StrUtil.isNotBlank(moduleName)){
            className="com.castle.fortress.admin."+moduleName+".enums.";
        }
        try {
            Class enumClass = Class.forName(className+enumName+"Enum");
            if (enumClass.isEnum()) {
                //获取所有枚举实例
                Object[] enumConstants = enumClass.getEnumConstants();
                //根据方法名获取方法
                Method code = enumClass.getMethod("getCode");
                Method name = enumClass.getMethod("getName");
                Method desc=null;
                try {
                    desc = enumClass.getMethod("getDesc");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                for (Object enum1 : enumConstants) {
                    //执行枚举方法获得枚举实例对应的值
                    String enumCode = code.invoke(enum1).toString();
                    String enumLabel = name.invoke(enum1).toString();
                    if(desc!=null){
                        String enumDesc = desc.invoke(enum1).toString();
                        EnumEntity enumEntity = new EnumEntity(enumCode,enumLabel,enumDesc);
                        list.add(enumEntity);
                    }else{
                        EnumEntity enumEntity = new EnumEntity(enumCode,enumLabel);
                        list.add(enumEntity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        return RespBody.data(list);
    }
}
