package com.anakki.data.entity.response;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnNoteResponse extends BaseEntity implements Serializable {

    @ApiModelProperty("笔记类型")
    private String type;

    @ApiModelProperty("发布位置")
    private String location;

    @ApiModelProperty("介绍")
    private String description;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("作者名称")
    private String author;

    @ApiModelProperty("作者ids")
    private String authorIds;

    @ApiModelProperty("作者id")
    private Long createBy;

    @ApiModelProperty("封面图片")
    private String coverImage;

    @ApiModelProperty("图片状态（正常：COMMON，失效 INVALID")
    private String status;

    @ApiModelProperty("浏览量")
    private Long viewCount;

    @ApiModelProperty("登录用户点赞数")
    private Long loginUserLikeCount;

    @ApiModelProperty("登录用户不喜欢数")
    private Long loginUserUnlikeCount;

    @ApiModelProperty("点赞数")
    private Long likeCount;

    @ApiModelProperty("不喜欢数")
    private Long unlikeCount;

    @ApiModelProperty("编辑次数")
    private Integer updateTimes;
}
