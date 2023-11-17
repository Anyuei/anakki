package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-11-15
 */
@Getter
@Setter
@TableName("an_manager")
@ApiModel(value = "AnManager对象", description = "")
public class AnManager extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("管理员状态（COMMON。BAN）")
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

    @ApiModelProperty("管理员昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("管理员生日")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("经验值")
    @TableField("exp")
    private Long exp;

    @ApiModelProperty("邮箱")
    @TableField("mail")
    private String mail;

    @ApiModelProperty("登录天数")
    @TableField("login_days")
    private Integer loginDays;

}
