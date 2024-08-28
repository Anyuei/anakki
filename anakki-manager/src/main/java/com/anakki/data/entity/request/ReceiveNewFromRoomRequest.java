package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ReceiveNewFromRoomRequest{

    @ApiModelProperty("房间id")
    @NotNull(message = "房间id为空")
    private Long roomId;

    @ApiModelProperty("客户端最新消息id")
    private Long currentIndex;
}