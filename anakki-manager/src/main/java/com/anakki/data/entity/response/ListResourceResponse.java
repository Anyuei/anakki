package com.anakki.data.entity.response;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ClassName: ListCommentsRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/14 12:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListResourceResponse extends BaseEntity {

    @ApiModelProperty("上传用户")
    private String uploadUser;
    @ApiModelProperty("资源名称")
    private String resourceName;
    @ApiModelProperty("文件标题")
    private String title;

    @ApiModelProperty("文件介绍")
    private String description;

    @ApiModelProperty("文件大小（KB）")
    private Long fileSize;

    @ApiModelProperty("浏览量")
    private Long viewCount;

    @ApiModelProperty("下载量")
    private Long downloadCount;

    @ApiModelProperty("登录用户点赞数")
    private Long loginUserLikeCount;

    @ApiModelProperty("登录用户不喜欢数")
    private Long loginUserUnlikeCount;

    @ApiModelProperty("点赞数")
    private Long likeCount;

    @ApiModelProperty("不喜欢数")
    private Long unlikeCount;
}
