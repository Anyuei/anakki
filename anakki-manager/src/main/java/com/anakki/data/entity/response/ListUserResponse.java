package com.anakki.data.entity.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class ListUserResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户状态（COMMON。BAN）")
    private String state;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("管理员昵称")
    private String nickname;

    @ApiModelProperty("用户生日")
    private LocalDate birthday;

    @ApiModelProperty("经验值")
    private Long exp;

    @ApiModelProperty("邮箱")
    private String mail;

    @ApiModelProperty("登录天数")
    private Integer loginDays;
}
