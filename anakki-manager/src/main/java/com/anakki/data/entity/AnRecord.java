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
 * @since 2023-11-08
 */
@Getter
@Setter
@TableName("an_record")
@ApiModel(value = "AnRecord对象", description = "")
public class AnRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("图片标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("拍摄位置")
    @TableField("location")
    private String location;

    @ApiModelProperty("图片介绍")
    @TableField("description")
    private String description;

    @ApiModelProperty("图片地址")
    @TableField("img_url")
    private String imgUrl;

}
