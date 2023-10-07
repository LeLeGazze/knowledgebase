package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * 组件示例表 实体类
 *
 * @author castle
 * @since 2021-06-02
 */
@Data
@ApiModel(value = "tmpDemo对象", description = "组件示例表")
public class TmpDemoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键ID")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "输入框")
	@JsonProperty("name")
	private String name;
	@ApiModelProperty(value = "富文本")
	@JsonProperty("content")
	private String content;
	@ApiModelProperty(value = "作者")
	@JsonProperty("auth")
	private String auth;
	@ApiModelProperty(value = "手机号")
	@JsonProperty("phone")
	private String phone;
	@ApiModelProperty(value = "邮箱")
	@JsonProperty("email")
	private String email;
	@ApiModelProperty(value = "省")
	@JsonProperty("province")
	private String province;
	@ApiModelProperty(value = "市")
	@JsonProperty("city")
	private String city;
	@ApiModelProperty(value = "区域")
	@JsonProperty("area")
	private String area;
	@ApiModelProperty(value = "图片")
	@JsonProperty("images")
	private String images;
	@ApiModelProperty(value = "文件")
	@JsonProperty("files")
	private String files;
	@ApiModelProperty(value = "视频")
	@JsonProperty("video")
	private String video;
	@ApiModelProperty(value = "地图-经度")
	@JsonProperty("longitude")
	private String longitude;
	@ApiModelProperty(value = "地图-纬度")
	@JsonProperty("latitude")
	private String latitude;
	@ApiModelProperty(value = "数字")
	@JsonProperty("vueNumber")
	private Integer vueNumber;
	@ApiModelProperty(value = "单选")
	@JsonProperty("vueRadio")
	private String vueRadio;
	@ApiModelProperty(value = "多行文本")
	@JsonProperty("vueTextarea")
	private String vueTextarea;
	@ApiModelProperty(value = "复选")
	@JsonProperty("vueCheckbox")
	private String vueCheckbox;
	@ApiModelProperty(value = "下拉")
	@JsonProperty("vueSelect")
	private String vueSelect;
	@ApiModelProperty(value = "下拉-enum")
	@JsonProperty("selectEnum")
	private String selectEnum;
	@ApiModelProperty(value = "下拉-url")
	@JsonProperty("selectUrl")
	private String selectUrl;
	@ApiModelProperty(value = "下拉-json")
	@JsonProperty("selectJson")
	private String selectJson;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty(value = "日期")
	@JsonProperty("vueDate")
	private Date vueDate;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "日期时间")
	@JsonProperty("vueDatetime")
	private Date vueDatetime;
	@ApiModelProperty(value = "状态")
	@JsonProperty("status")
	private Integer status;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	@JsonProperty("createUser")
	private Long createUser;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建部门")
	@JsonProperty("createDept")
	private Long createDept;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建职位")
	@JsonProperty("createPost")
	private Long createPost;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "修改人")
	@JsonProperty("updateUser")
	private Long updateUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "修改时间")
	@JsonProperty("updateTime")
	private Date updateTime;
	@ApiModelProperty(value = "是否删除YesNoEnum")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;
	@ApiModelProperty(value = "创建部门名称")
	@JsonProperty("createDeptName")
	private String createDeptName;
	@ApiModelProperty(value = "创建职位名称")
	@JsonProperty("createPostName")
	private String createPostName;
	@ApiModelProperty(value = "数据权限校验标识")
	@JsonProperty("dataAuthFlag")
	private Boolean dataAuthFlag;
	@ApiModelProperty(value = "省名")
	@JsonProperty("provinceName")
	private String provinceName;
	@ApiModelProperty(value = "市名")
	@JsonProperty("cityName")
	private String cityName;
	@ApiModelProperty(value = "区域名")
	@JsonProperty("areaName")
	private String areaName;
	@ApiModelProperty(value = "地图数据")
	@JsonProperty("mapData")
	private String mapData;
	@ApiModelProperty(value = "行业数据")
	@JsonProperty("industryData")
	private String industryData;
	@ApiModelProperty(value = "人员部门信息")
	@JsonProperty("usersData")
	private String usersData;

}
