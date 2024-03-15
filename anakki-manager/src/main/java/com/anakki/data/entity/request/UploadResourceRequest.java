package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
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

    @ApiModelProperty("资源文件")
    private MultipartFile[] files;
}
