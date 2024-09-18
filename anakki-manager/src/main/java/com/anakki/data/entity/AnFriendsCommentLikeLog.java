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
 * @since 2024-09-19
 */
@Getter
@Setter
@TableName("an_friends_comment_like_log")
@ApiModel(value = "AnFriendsCommentLikeLog对象", description = "")
public class AnFriendsCommentLikeLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("评论id")
    @TableField("comment_id")
    private Long commentId;

    @ApiModelProperty("用户姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

}
