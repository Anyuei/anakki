package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
public class UploadResourceRequest extends Pagination {

    @ApiModelProperty("资源类型")
    private String type;

    @ApiModelProperty("资源标题")
    private String title;

    @ApiModelProperty("资源介绍")
    private String description;

    @ApiModelProperty("资源是否公开")
    private Boolean isPublic;

    @ApiModelProperty("资源文件")
    private MultipartFile[] files;

    @ApiModelProperty("有效时间")
    private LocalDateTime availableTime=LocalDateTime.now().plusDays(7);

    @ApiModelProperty("是否是临时文件")
    private Boolean isTemporary=true;
}
