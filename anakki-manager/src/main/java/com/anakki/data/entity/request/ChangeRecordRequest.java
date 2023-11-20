package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class ChangeRecordRequest {

    @ApiModelProperty("图文id")
    @NotNull
    private Long id;

    @ApiModelProperty("是否开启")
    @NotNull
    private Boolean isChecked;

}
