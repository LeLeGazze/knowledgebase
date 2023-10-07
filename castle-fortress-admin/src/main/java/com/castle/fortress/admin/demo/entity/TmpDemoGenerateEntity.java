package com.castle.fortress.admin.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.DataAuthBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * 代码生成示例表 实体类
 *
 * @author castle
 * @since 2021-11-08
 */
@Data
@TableName("tmp_demo_generate")
@EqualsAndHashCode(callSuper = true)
public class TmpDemoGenerateEntity extends DataAuthBaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 中文
	*/
	private String name;
	/**
	 * 富文本
	*/
	private String content;
	/**
	 * 字母
	*/
	private String auth;
	/**
	 * 手机号
	*/
	private String phone;
	/**
	 * 邮箱
	*/
	private String email;
	/**
	 * 图片
	*/
	private String images;
	/**
	 * 文件
	*/
	private String files;
	/**
	 * 视频
	*/
	private String video;
	/**
	 * 数字
	*/
	private Integer vueNumber;
	/**
	 * 单选
	*/
	private String vueRadio;
	/**
	 * 多行文本
	*/
	private String vueTextarea;
	/**
	 * 复选
	*/
	private String vueCheckbox;
	/**
	 * 下拉
	*/
	private String vueSelect;
	/**
	 * 枚举下拉
	*/
	private String selectEnum;
	/**
	 * url下拉
	*/
	private String selectUrl;
	/**
	 * json下拉
	*/
	private String selectJson;
	/**
	 * 日期
	*/
	private Date vueDate;
	/**
	 * 日期时间
	*/
	private Date vueDatetime;
	/**
	 * 枚举单选
	*/
	private String redioEnum;
	/**
	 * url单选
	*/
	private String radioUrl;
	/**
	 * json单选
	*/
	private String radioJson;
	/**
	 * 枚举复选
	*/
	private String checkboxEnum;
	/**
	 * url复选
	*/
	private String checkboxUrl;
	/**
	 * json复选
	*/
	private String checkboxJson;

}
