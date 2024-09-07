package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class UserResetPasswordRequest {

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String newPassword;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String verificationCode;
}