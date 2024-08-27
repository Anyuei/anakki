package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TurnOnOffRequest {

    @ApiModelProperty("状态")
    private Boolean status;

}
