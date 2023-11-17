package com.anakki.data.entity.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class UserDetailResponse {

    @ApiModelProperty("用户状态（COMMON。BAN）")
    private String state;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("用户生日")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("经验值")
    @TableField("exp")
    private Long exp;

    @ApiModelProperty("登录天数")
    @TableField("login_days")
    private Integer loginDays;
}
