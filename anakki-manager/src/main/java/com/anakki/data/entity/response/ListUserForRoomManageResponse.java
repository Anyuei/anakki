package com.anakki.data.entity.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class ListUserForRoomManageResponse {


    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户id")
    private Long id;
}
