package com.anakki.data.entity.response;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class AnBgResourceResponse extends BaseEntity implements Serializable {

    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("图片标题")
    private String title;

    @ApiModelProperty("图片地址")
    private String imgUrl;

    @ApiModelProperty("图片尺寸")
    @TableField("image_size")
    private String imageSize;

    @ApiModelProperty("图片文件大小 KB")
    private Long fileSize;

    @ApiModelProperty("浏览量")
    private Long viewCount;

    @ApiModelProperty("点赞数")
    @TableField("like_count")
    private Long likeCount;
}
