package com.anakki.data.entity.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/***
 * @author pei
 * @date 2024/8/11 2:36
 */
@Data
public class CurrentManagerLoginInfoResponse {
    @ApiModelProperty("管理员状态（COMMON。BAN）")
    private String state;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("管理员昵称")
    private String nickname;

    @ApiModelProperty("管理员生日")
    private LocalDate birthday;

    @ApiModelProperty("经验值")
    private Long exp;

    @ApiModelProperty("邮箱")
    private String mail;

    @ApiModelProperty("登录天数")
    private Integer loginDays;
}
