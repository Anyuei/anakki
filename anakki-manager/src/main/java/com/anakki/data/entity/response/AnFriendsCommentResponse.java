package com.anakki.data.entity.response;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnFriendsCommentResponse extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论类型")
    private String type;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("经验值")
    private Long exp= 0L;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("评论")
    private String comment;

    @ApiModelProperty("喜欢数")
    private Long likeCount;

    @ApiModelProperty("不喜欢数")
    private Long unlikeCount;

    @ApiModelProperty("状态（IN_REVIEW,NORMAL，CLOSED）")
    private String status;
}
