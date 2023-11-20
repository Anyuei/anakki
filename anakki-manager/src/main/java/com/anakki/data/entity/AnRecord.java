package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;

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
    @ApiModelProperty("图片模块类型")
    @TableField("type")
    private String type;

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

    @ApiModelProperty("图片拍摄时间")
    @TableField(value = "photo_time")
    private LocalDateTime photoTime;

    @ApiModelProperty("摄影师")
    @TableField("photo_by")
    private String photoBy;

    @ApiModelProperty("图片尺寸")
    @TableField("image_size")
    private String imageSize;

    @ApiModelProperty("图片状态")
    @TableField("status")
    private String status;
}
