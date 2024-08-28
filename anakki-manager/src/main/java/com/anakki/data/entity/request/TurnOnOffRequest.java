package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class TurnOnOffRequest {

    @ApiModelProperty("状态")
    @NotNull(message = "状态为空")
    private Boolean status;

}
