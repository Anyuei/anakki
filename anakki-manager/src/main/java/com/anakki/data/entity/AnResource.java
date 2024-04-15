package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.qcloud.Module.Base;
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
 * @since 2024-03-14
 */
@Getter
@Setter
@TableName("an_resource")
@ApiModel(value = "AnResource对象", description = "")
public class AnResource extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("上传用户id")
    @TableField("upload_user_id")
    private Long uploadUserId;

    @ApiModelProperty("上传用户")
    @TableField("upload_user")
    private String uploadUser;

    @ApiModelProperty("资源名称")
    @TableField("resource_name")
    private String resourceName;

    @ApiModelProperty("文件类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("文件标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("过期时间")
    @TableField("expiration_date")
    private LocalDateTime expirationDate;

    @ApiModelProperty("文件介绍")
    @TableField("description")
    private String description;

    @ApiModelProperty("文件地址")
    @TableField("file_url")
    private String fileUrl;

    @ApiModelProperty("模型状态（正常：COMMON，失效 INVALID")
    @TableField("status")
    private String status;

    @ApiModelProperty("文件大小（KB）")
    @TableField("file_size")
    private Long fileSize;

    @ApiModelProperty("是否公开")
    @TableField("is_public")
    private Boolean isPublic;

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
