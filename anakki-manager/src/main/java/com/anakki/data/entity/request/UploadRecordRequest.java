package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class UploadRecordRequest extends Pagination {

    @ApiModelProperty("图片类型")
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

    @ApiModelProperty("图片地址")
    private MultipartFile[] file;

    @ApiModelProperty("拍摄时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime photoTime;

    @ApiModelProperty("照片状态(立即发布：COMMON，)")
    private String status;

    @ApiModelProperty("是否原图")
    private Boolean isRaw=true;
}
