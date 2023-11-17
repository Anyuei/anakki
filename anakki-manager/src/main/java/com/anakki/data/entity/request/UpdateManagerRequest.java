package com.anakki.data.entity.request;

import com.anakki.data.entity.common.ManageToken;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class UpdateManagerRequest extends IdNotNullRequest {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("管理员昵称")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String mail;

}
