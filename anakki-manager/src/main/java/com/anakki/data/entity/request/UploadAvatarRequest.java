package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class UploadAvatarRequest {

    @ApiModelProperty("图片")
    private MultipartFile file;
    @ApiModelProperty("系统图片")
    private String selectedAvatar;
    @ApiModelProperty("系统图片id")
    private Long id;
}
