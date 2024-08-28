package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class ChatRoomSettingRequest {

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱为空")
    private String email;

}
