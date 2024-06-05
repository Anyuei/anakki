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
 * @since 2024-04-15
 */
@Getter
@Setter
@TableName("an_gift_log")
@ApiModel(value = "AnGiftLog对象", description = "")
public class AnGiftLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("奖品名称")
    @TableField("gift_name")
    private String giftName;

    @ApiModelProperty("中奖人")
    @TableField("winners")
    private String winners;

    @ApiModelProperty("参与人")
    @TableField("users")
    private String users;

    @ApiModelProperty("开奖时间")
    @TableField("open_time")
    private LocalDateTime openTime;

    @ApiModelProperty("发起人")
    @TableField("initiator")
    private String initiator;
}
