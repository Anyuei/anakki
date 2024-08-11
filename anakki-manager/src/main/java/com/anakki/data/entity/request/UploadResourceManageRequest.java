package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class UploadResourceManageRequest{

    @ApiModelProperty("资源类型")
    private String type;

    @ApiModelProperty("资源标题")
    private String title;

    @ApiModelProperty("资源介绍")
    private String description;

    @ApiModelProperty("资源是否公开")
    private Boolean isPublic;

    @ApiModelProperty("资源文件列表")
    private FileInfoRequest[] files;

    @ApiModelProperty("有效时间")
    private LocalDateTime availableTime=LocalDateTime.now().plusDays(7);

    @ApiModelProperty("是否是临时文件")
    private Boolean isTemporary=true;

    @ApiModelProperty("文件大小（KB）")
    private Long fileSize;

    @ApiModelProperty("资源名称")
    private String resourceName;
}
