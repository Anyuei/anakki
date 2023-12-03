package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2023-12-04
 */
@Getter
@Setter
@TableName("an_record_user_operate_log")
@ApiModel(value = "AnRecordUserOperateLog对象", description = "")
public class AnRecordUserOperateLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("图文id")
    @TableField("record_id")
    private Long recordId;

    @ApiModelProperty("用户id（0:未登录用户）")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("点赞：LIKE，不喜欢：UNLIKE")
    @TableField("operate_type")
    private String operateType;

    @ApiModelProperty("访问者ip")
    @TableField("client_ip")
    private String clientIp;

    @ApiModelProperty("访问者地域信息")
    @TableField("country")
    private String country;

    @ApiModelProperty("省")
    @TableField("prov")
    private String prov;

    @ApiModelProperty("市")
    @TableField("city")
    private String city;

    @ApiModelProperty("区")
    @TableField("district")
    private String district;
}
