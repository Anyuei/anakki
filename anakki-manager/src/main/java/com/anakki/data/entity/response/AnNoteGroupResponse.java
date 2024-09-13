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
 * @since 2024-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnNoteGroupResponse extends BaseEntity {

    @ApiModelProperty("分组名称")
    private String groupName;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("封面图片")
    private String coverImage;

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

    @ApiModelProperty("笔记总数")
    private Integer noteCount;

}
