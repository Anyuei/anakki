package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ChatRoomSettingRequest {

    @ApiModelProperty("邮箱")
    private String email;

}
