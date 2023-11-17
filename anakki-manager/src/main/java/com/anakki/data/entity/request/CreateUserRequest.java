package com.anakki.data.entity.request;

import com.anakki.data.entity.common.ManageToken;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class CreateUserRequest extends ManageToken {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户姓名")
    @NotBlank(message = "姓名不能为空")
    private String userName;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty("用户生日")
    private LocalDateTime birthday;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String mail;

}
