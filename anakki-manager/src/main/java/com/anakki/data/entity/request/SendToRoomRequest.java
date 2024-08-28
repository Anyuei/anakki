package com.anakki.data.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class SendToRoomRequest {
    @ApiModelProperty("是否临时消息")
    private Boolean isTemp;

    @ApiModelProperty("房间id")
    @NotNull(message = "roomId为空")
    private Long roomId;

    @ApiModelProperty("消息内容")
    @NotBlank(message = "消息为空")
    private String content;

    @ApiModelProperty("消息内容类型")
    private String contentType;
}
