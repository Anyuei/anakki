package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ReceiveFromRoomRequest {

    @ApiModelProperty("房间id")
    private Long roomId;


    @ApiModelProperty("客户端最旧消息id")
    private Long firstIndex;
}
