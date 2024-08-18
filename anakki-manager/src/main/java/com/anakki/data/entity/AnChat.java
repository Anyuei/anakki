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
 * @since 2024-08-18
 */
@Getter
@Setter
@TableName("an_chat")
@ApiModel(value = "AnChat对象", description = "")
public class AnChat extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("消息类型")
    @TableField("content_type")
    private String contentType;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("消息内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("是否临时消息")
    @TableField("is_temp")
    private Boolean isTemp;

    @ApiModelProperty("是否加密")
    @TableField("is_encrypt")
    private Boolean isEncrypt;

    @ApiModelProperty("聊天室id")
    @TableField("room_id")
    private Long roomId;

    @ApiModelProperty("状态（READ，NORMAL，CLOSED）")
    @TableField("status")
    private String status;
}
