package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class CodeVerifyRequest {

    @ApiModelProperty("电话号码")
    @NotBlank
    private String telephone;

    @ApiModelProperty("验证码")
    @NotBlank
    private String code;
}
