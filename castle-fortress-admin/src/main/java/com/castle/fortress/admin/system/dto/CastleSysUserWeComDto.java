package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
/**
 * 用户企业微信信息表 实体类
 *
 * @author mjj
 * @since 2022-11-30
 */
@Data
public class CastleSysUserWeComDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonProperty("userId")
	private Long userId;
	@JsonProperty("weComUserid")
	private String weComUserid;
	@JsonProperty("name")
	private String name;
	@JsonProperty("department")
	private String department;
	@JsonProperty("position")
	private String position;
	@JsonProperty("mobile")
	private String mobile;
	@JsonProperty("email")
	private String email;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("status")
	private String status;
	@JsonProperty("avatar")
	private String avatar;
	@JsonProperty("isleader")
	private String isleader;
	@JsonProperty("extattr")
	private String extattr;
	@JsonProperty("englishName")
	private String englishName;
	@JsonProperty("telephone")
	private String telephone;
	@JsonProperty("enable")
	private String enable;
	@JsonProperty("hideMobile")
	private String hideMobile;
	@JsonProperty("sort")
	private String sort;
	@JsonProperty("externalProfile")
	private String externalProfile;
	@JsonProperty("mainDepartment")
	private String mainDepartment;
	@JsonProperty("qrCode")
	private String qrCode;
	@JsonProperty("alias")
	private String alias;
	@JsonProperty("isLeaderInDept")
	private String isLeaderInDept;
	@JsonProperty("address")
	private String address;
	@JsonProperty("thumbAvatar")
	private String thumbAvatar;
	@JsonProperty("directLeader")
	private String directLeader;
	@JsonProperty("bizMail")
	private String bizMail;
	@JsonProperty("openUserid")
	private String openUserid;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonProperty("createTime")
	private Date createTime;

}
