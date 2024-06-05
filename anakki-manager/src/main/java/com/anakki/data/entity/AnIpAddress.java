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
 * @since 2023-11-29
 */
@Getter
@Setter
@TableName("an_ip_address")
@ApiModel(value = "AnIpAddress对象", description = "")
public class AnIpAddress extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ip")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("国家")
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

    @ApiModelProperty("json")
    @TableField("content_json")
    private String contentJson;

    @ApiModelProperty("状态")
    @TableField("state")
    private String state;
}
