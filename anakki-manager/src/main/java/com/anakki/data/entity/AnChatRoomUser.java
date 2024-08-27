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
 * @since 2024-08-28
 */
@Getter
@Setter
@TableName("an_chat_room_user")
@ApiModel(value = "AnChatRoomUser对象", description = "")
public class AnChatRoomUser extends BaseEntity implements Serializable {

    @ApiModelProperty("房间id		")
    @TableField("room_id")
    private Long roomId;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    @TableField("user_nickname")
    private String userNickname;

    @ApiModelProperty("用户数据是否加密")
    @TableField("user_encryption")
    private Boolean userEncryption;

    @ApiModelProperty("用户聊天室背景")
    @TableField("background_img")
    private String backgroundImg;

    @ApiModelProperty("角色（1：房主，2：管理员，3：成员，4：游客）")
    @TableField("role")
    private String role;

    @ApiModelProperty("用户最后读取的消息ID，通常用于追踪用户的已读状态。")
    @TableField("last_read_message_id")
    private Long lastReadMessageId;

    @ApiModelProperty("用户是否启用了该聊天室的通知功能")
    @TableField("notifications_enabled")
    private Boolean notificationsEnabled;

    @ApiModelProperty("状态（NORMAL，CLOSED）")
    @TableField("status")
    private String status;
}
