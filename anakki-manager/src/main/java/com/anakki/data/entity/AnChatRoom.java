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
@TableName("an_chat_room")
@ApiModel(value = "AnChatRoom对象", description = "")
public class AnChatRoom extends BaseEntity implements Serializable {

    @ApiModelProperty("房间类型")
    @TableField("room_type")
    private String roomType;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("用户id")
    @TableField("room_master_id")
    private Long roomMasterId;

    @ApiModelProperty("房间名称")
    @TableField("room_name")
    private String roomName;

    @ApiModelProperty("介绍")
    @TableField("description")
    private String description;

    @ApiModelProperty("是否临时消息")
    @TableField("is_temp")
    private Boolean isTemp;

    @ApiModelProperty("是否加密")
    @TableField("is_encrypt")
    private Boolean isEncrypt;

    @ApiModelProperty("是否公开可搜索")
    @TableField("is_public_for_search")
    private Boolean isPublicForSearch;

    @ApiModelProperty("1:自由进入，2:密码进入，3:申请进入，4:邀请进入")
    @TableField("enter_type")
    private Integer enterType;

    @ApiModelProperty("加入密码")
    @TableField("enter_password")
    private String enterPassword;

    @ApiModelProperty("状态（NORMAL，CLOSED）")
    @TableField("status")
    private String status;
}
