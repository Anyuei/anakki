package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2024-09-14
 */
@Getter
@Setter
@TableName("an_note_group")
@ApiModel(value = "AnNoteGroup对象", description = "")
public class AnNoteGroup extends BaseEntity implements Serializable {

    @ApiModelProperty("分组名称")
    @TableField("group_name")
    private String groupName;

    @ApiModelProperty("介绍")
    @TableField("description")
    private String description;

    @ApiModelProperty("封面图片")
    @TableField("cover_image")
    private String coverImage;

    @ApiModelProperty("分组状态")
    @TableField("status")
    private String status;

    @ApiModelProperty("公开范围（0：全站可见，1：仅某人可见，2：自己可见）")
    @TableField("scope_access")
    private Integer scopeAccess;

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

    @ApiModelProperty("笔记总数")
    @TableField("note_count")
    private Integer noteCount;

}
