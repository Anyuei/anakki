package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
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
@TableName("an_statistic")
@ApiModel(value = "AnStatistic对象", description = "")
public class AnStatistic extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("统计类型")
    @TableField("statistic_name")
    private String statisticName;

    @ApiModelProperty("统计值")
    @TableField("statistic_value")
    private String statisticValue;

    @ApiModelProperty("统计类型")
    @TableField("statistic_description")
    private String statisticDescription;
}
