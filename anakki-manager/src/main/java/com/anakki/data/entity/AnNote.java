package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@Getter
@Setter
@TableName("an_note")
@ApiModel(value = "AnNote对象", description = "")
public class AnNote extends BaseEntity implements Serializable {

    @ApiModelProperty("笔记类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("发布位置")
    @TableField("location")
    private String location;

    @ApiModelProperty("介绍")
    @TableField("description")
    private String description;
    @ApiModelProperty("标题")
    @TableField("title")
    private String title;
    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("作者名称")
    @TableField("author")
    private String author;

    @ApiModelProperty("作者id")
    @TableField("create_by")
    private Long createBy;

    @ApiModelProperty("封面图片")
    @TableField("cover_image")
    private String coverImage;

    @ApiModelProperty("图片状态（正常：COMMON，失效 INVALID")
    @TableField("status")
    private String status;

    @ApiModelProperty("浏览量")
    @TableField("view_count")
    private Long viewCount;

    @ApiModelProperty("登录用户点赞数")
    @TableField("login_user_like_count")
    private Long loginUserLikeCount;

    @ApiModelProperty("登录用户不喜欢数")
    @TableField("login_user_unlike_count")
    private Long loginUserUnlikeCount;

    @ApiModelProperty("点赞数")
    @TableField("like_count")
    private Long likeCount;

    @ApiModelProperty("不喜欢数")
    @TableField("unlike_count")
    private Long unlikeCount;

    @ApiModelProperty("编辑次数")
    @TableField("update_times")
    private Integer updateTimes;
}
