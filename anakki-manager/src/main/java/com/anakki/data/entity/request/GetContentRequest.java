package com.anakki.data.entity.request;

import com.anakki.data.entity.common.PageEntity;
import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class GetContentRequest extends Pagination {

    @ApiModelProperty("图片类型")
    private String imageType;
}
