package com.castle.fortress.admin.core.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.castle.fortress.common.enums.YesNoEnum;

/**
 * excel 状态转化
 * @author castle
 */
public class StatusConverter implements Converter<Integer> {
    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return cellData.getStringValue().equals("正常") ? 1 : 2;
    }

    @Override
    public WriteCellData<String> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        return new WriteCellData(YesNoEnum.YES.getCode().equals(context.getValue()) ? "正常" : "停用");
    }
}
