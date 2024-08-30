package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AvatarImgListRequest extends Pagination {
    @ApiModelProperty("通用搜索条件")
    private String searchString;
}
