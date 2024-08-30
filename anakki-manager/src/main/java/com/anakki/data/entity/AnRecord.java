package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
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
 * @since 2023-11-08
 */
@Getter
@Setter
@TableName("an_record")
@ApiModel(value = "AnRecord对象", description = "")
public class AnRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("图片模块类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("标签")
    @TableField("tag")
    private String tag;

    @ApiModelProperty("图片标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("拍摄位置")
    @TableField("location")
    private String location;

    @ApiModelProperty("图片介绍")
    @TableField("description")
    private String description;

    @ApiModelProperty("图片地址")
    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty("图片拍摄时间")
    @TableField(value = "photo_time")
    private LocalDateTime photoTime;

    @ApiModelProperty("摄影师")
    @TableField("photo_by")
    private String photoBy;

    @ApiModelProperty("图片尺寸")
    @TableField("image_size")
    private String imageSize;

    @ApiModelProperty("图片文件大小 KB")
    @TableField("file_size")
    private Long fileSize;

    @ApiModelProperty("图片状态")
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

    @ApiModelProperty("头像所属人")
    @TableField("avatar_user_id")
    private Long avatarUserId;
}
