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
@TableName("an_statistic_log")
@ApiModel(value = "AnStatisticLog对象", description = "")
public class AnStatisticLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("统计类型")
    @TableField("statistic_name")
    private String statisticName;

    @ApiModelProperty("统计描述")
    @TableField("statistic_description")
    private String statisticDescription;

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
