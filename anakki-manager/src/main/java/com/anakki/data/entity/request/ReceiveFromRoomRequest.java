package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ReceiveFromRoomRequest {

    @ApiModelProperty("房间id")
    @NotNull(message = "roomId不能为空")
    private Long roomId;


    @ApiModelProperty("客户端最旧消息id")
    private Long firstIndex;
}
