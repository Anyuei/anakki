package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class UploadRecordForManageRequest {

    @ApiModelProperty("图片发布模块")
    private String type;

    @ApiModelProperty("图片标题")
    private String title;

    @ApiModelProperty("拍摄位置")
    private String location;

    @ApiModelProperty("摄影师")
    private String photoBy;

    @ApiModelProperty("分辨率")
    private String imageSize;

    @ApiModelProperty("图片介绍")
    private String description;

    @ApiModelProperty("文件信息")
    private FileInfoRequest[] files;

    @ApiModelProperty("拍摄时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime photoTime;

    @ApiModelProperty("照片状态(立即发布：COMMON，)")
    private Boolean status;
}
