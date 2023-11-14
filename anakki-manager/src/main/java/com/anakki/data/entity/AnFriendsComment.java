package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
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
 * @since 2023-11-14
 */
@Getter
@Setter
@TableName("an_friends_comment")
@ApiModel(value = "AnFriendsComment对象", description = "")
public class AnFriendsComment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("评论")
    @TableField("comment")
    private String comment;

    @ApiModelProperty("喜欢数")
    @TableField("like_count")
    private Long likeCount;

    @ApiModelProperty("不喜欢数")
    @TableField("unlike_count")
    private Long unlikeCount;

    @ApiModelProperty("状态（NORMAL，CLOSED）")
    @TableField("state")
    private String state;
}
