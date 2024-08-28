package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class ListUserForRoomManageRequest {


    @ApiModelProperty("用户昵称或用户id")
    @NotBlank(message = "用户昵称或用户id不能为空")
    private String userNameAndUserId;
}
