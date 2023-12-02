package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@Getter
@Setter
@TableName("an_user")
@ApiModel(value = "AnUser对象", description = "")
public class AnUser extends BaseEntity  implements Serializable {
    @ApiModelProperty("用户状态（COMMON。BAN）")
    @TableField("state")
    private String state;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("用户姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

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

    @ApiModelProperty("文心一言token")
    @TableField("wxyy_token")
    private String wxyyToken;

    @ApiModelProperty("电话")
    @TableField("telephone")
    private String telephone;

    @ApiModelProperty("电话最后修改时间")
    @TableField("telephone_last_update_time")
    private LocalDateTime telephoneLastUpdateTime;

}
