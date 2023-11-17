package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class UpdateUserRequest extends IdNotNullRequest {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("管理员昵称")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String mail;

}
