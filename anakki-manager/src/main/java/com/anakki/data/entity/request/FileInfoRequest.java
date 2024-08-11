package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
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
public class FileInfoRequest {

    @ApiModelProperty("资源地址")
    private String url;

    @ApiModelProperty("资源类型")
    private String resourceType;

    @ApiModelProperty("资源标题")
    private String name;

    @ApiModelProperty("文件大小（KB）")
    private Long size;
}
