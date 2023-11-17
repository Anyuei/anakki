package com.anakki.data.entity.request;

import com.anakki.data.entity.common.ManageToken;
import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class CreateManagerRequest extends ManageToken {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户姓名")
    @NotBlank(message = "姓名不能为空")
    private String userName;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("管理员昵称")
    @NotBlank(message = "管理员昵称不能为空")
    private String nickname;

    @ApiModelProperty("管理员生日")
    private LocalDateTime birthday;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String mail;

}
