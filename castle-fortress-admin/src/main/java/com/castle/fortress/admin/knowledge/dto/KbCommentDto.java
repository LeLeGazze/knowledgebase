package com.castle.fortress.admin.knowledge.dto;

import com.castle.fortress.admin.knowledge.entity.KbCommentEntity;
import com.castle.fortress.admin.system.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 知识评论管理表 实体类
 *
 * @author sunhr
 * @since 2023-05-09
 */
@Data
@ApiModel(value = "kbComment对象", description = "知识评论管理表")
public class KbCommentDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "评价内容")
	@JsonProperty("content")
	private String content;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "对应的知识id")
	@JsonProperty("basicId")
	private Long basicId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "父id")
	@JsonProperty("parentId")
	private Long parentId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户id")
	@JsonProperty("userId")
	private Long userId;
	@ApiModelProperty(value = "最上级id")
	@JsonProperty("oneId")
	private Long oneId;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;
	@ApiModelProperty(value = "1：删除  2：非删除")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "1：有回复  2：无回复")
	@JsonProperty("isReply")
	private Integer isReply;
	@ApiModelProperty(value = "评论知识类型")
	@JsonProperty("status")
	private Integer status;
	@ApiModelProperty(value = "用户昵称")
	@JsonProperty("nickname")
	private String nickname;
	@ApiModelProperty(value = "用户头像")
	@JsonProperty("avatar")
	private String avatar;
	@ApiModelProperty(value = "子评论")
	@JsonProperty("children")
	private List<KbCommentDto> children;

	@ApiModelProperty(value = "回复名称")
	@JsonProperty("parentName")
	private String parentName;
	@ApiModelProperty(value = "回复头像")
	@JsonProperty("parentAvatar")
	private String parentAvatar;
	@ApiModelProperty(value = "点赞次数")
	@JsonProperty("nums")
	private String nums;
	@ApiModelProperty(value = "是否点赞")
	@JsonProperty("upStatus")
	private Integer upStatus;
}

