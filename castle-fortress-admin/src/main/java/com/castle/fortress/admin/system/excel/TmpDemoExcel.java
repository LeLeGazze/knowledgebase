package com.castle.fortress.admin.system.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.castle.fortress.admin.core.converter.StatusConverter;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻表 实体类
 *
 * @author castle
 * @since 2021-05-15
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class TmpDemoExcel implements Serializable {
	private static final long serialVersionUID = 1L;
	@ExcelProperty(value = "编号", index = 0)
	private Long id;
	@ExcelProperty(value = "名称", index = 1)
	private String name;
	@ExcelProperty(value = "作者", index = 2)
	private String auth;
	@ExcelProperty(value = "时间", index = 3)
	private Date updateTime;
	@ExcelProperty(value = "删除状态", index = 4 ,converter = StatusConverter.class)
	private Integer isDeleted;

}
