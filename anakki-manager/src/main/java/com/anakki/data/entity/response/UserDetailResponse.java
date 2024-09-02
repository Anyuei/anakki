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
    private String nickname;

    @ApiModelProperty("用户生日")
    private LocalDate birthday;

    @ApiModelProperty("经验值")
    private Long exp;

    @ApiModelProperty("邮箱地址")
    private String mail;

    @ApiModelProperty("登录天数")
    private Integer loginDays;

    @ApiModelProperty("是否开启聊天室邮箱提醒")
    private Boolean isChatroomMailNotice;

    @ApiModelProperty("是否回车发送消息")
    private Boolean isEnterSendMessage;

}
