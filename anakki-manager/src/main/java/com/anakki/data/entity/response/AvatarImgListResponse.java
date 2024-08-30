package com.anakki.data.entity.response;

import com.anakki.data.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class AvatarImgListResponse {
    @ApiModelProperty("图片id")
    private Long id;
    @ApiModelProperty("图片路径")
    private String imgUrl;
}
