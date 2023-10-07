package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.DataAuthBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * 组件示例表 实体类
 *
 * @author castle
 * @since 2021-06-02
 */
@Data
@TableName("tmp_demo")
@EqualsAndHashCode(callSuper = true)
public class TmpDemoEntity extends DataAuthBaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 输入框
	*/
	private String name;
	/**
	 * 富文本
	*/
	private String content;
	/**
	 * 作者
	*/
	private String auth;
	/**
	 * 手机号
	*/
	private String phone;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 省名
	 */
	private String provinceName;
	/**
	 * 市名
	 */
	private String cityName;
	/**
	 * 区域名
	 */
	private String areaName;
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
	 * 地图-经度
	*/
	private String longitude;
	/**
	 * 地图-纬度
	*/
	private String latitude;
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
	 * 下拉 enum
	 */
	private String selectEnum;
	/**
	 * 下拉 url
	 */
	private String selectUrl;
	/**
	 * 下拉 json
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
	 * 地图数据
	 */
	private String mapData;
	/**
	 * 行业数据
	 */
	private String industryData;
	/**
	 * 人员部门信息
	 */
	private String usersData;

}
