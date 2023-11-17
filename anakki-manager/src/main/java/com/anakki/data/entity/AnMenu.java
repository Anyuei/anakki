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
 * @since 2023-11-15
 */
@Getter
@Setter
@TableName("an_menu")
@ApiModel(value = "AnMenu对象", description = "")
public class AnMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("菜单类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("路径")
    @TableField("path")
    private String path;

    @ApiModelProperty("名称")
    @TableField("menu_name")
    private String menuName;


}
