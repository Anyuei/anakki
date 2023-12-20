package com.anakki.data.entity.response;

import com.anakki.data.entity.common.BaseEntity;
import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: ListCommentsRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/14 12:01
 */
@Data
public class List3dModelResponse extends BaseEntity {
    @ApiModelProperty("文件类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("文件标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("文件介绍")
    @TableField("description")
    private String description;

    @ApiModelProperty("模型文件封面")
    @TableField("model_image_url")
    private String modelImageUrl;

    @ApiModelProperty("文件大小（KB）")
    @TableField("file_size")
    private Long fileSize;

    @ApiModelProperty("浏览量")
    @TableField("view_count")
    private Long viewCount;

    @ApiModelProperty("下载量")
    @TableField("download_count")
    private Long downloadCount;

    @ApiModelProperty("登录用户点赞数")
    @TableField("login_user_like_count")
    private Long loginUserLikeCount;

    @ApiModelProperty("登录用户不喜欢数")
    @TableField("login_user_unlike_count")
    private Long loginUserUnlikeCount;

    @ApiModelProperty("点赞数")
    @TableField("like_count")
    private Long likeCount;

    @ApiModelProperty("不喜欢数")
    @TableField("unlike_count")
    private Long unlikeCount;
}
