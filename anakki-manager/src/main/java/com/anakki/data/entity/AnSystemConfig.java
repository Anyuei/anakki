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
 * @since 2023-11-25
 */
@Getter
@Setter
@TableName("an_system_config")
@ApiModel(value = "AnSystemConfig对象", description = "")
public class AnSystemConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("设置类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("设置标题")
    @TableField("config_key")
    private String configKey;

    @ApiModelProperty("设置值")
    @TableField("config_value")
    private String configValue;

    @ApiModelProperty("设置描述")
    @TableField("description")
    private String description;


}
