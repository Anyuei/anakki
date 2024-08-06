package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserRequest extends IdNotNullRequest {

    @ApiModelProperty("管理员昵称")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String mail;

}
